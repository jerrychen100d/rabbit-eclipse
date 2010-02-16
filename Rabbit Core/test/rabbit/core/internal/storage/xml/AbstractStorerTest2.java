package rabbit.core.internal.storage.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import rabbit.core.events.DiscreteEvent;
import rabbit.core.internal.storage.xml.DatatypeConverter;
import rabbit.core.internal.storage.xml.schema.events.EventGroupType;

public abstract class AbstractStorerTest2<E extends DiscreteEvent, T, S extends EventGroupType>
		extends AbstractStorerTest<E, T, S> {

	@Override
	public void testInsertCollection() {
		try {
			Collection<S> data = getDataField(storer);

			assertEquals(0, data.size());

			E e = null;
			T type = null;
			List<E> list = new ArrayList<E>();

			{// Insert a new event:
				e = createEvent();
				list.add(e);
				storer.insert(list);

				assertEquals(1, data.size());

				assertEquals(1, getEventTypes(data.iterator().next()).size());

				type = getEventTypes(data.iterator().next()).iterator().next();

				assertTrue(isEqual(type, e));
			}

			{// Insert collection with two elements:

				// Make a new event with the same id to be merged:
				E eWithSameId = createEvent();

				// Make a new event with a different id:
				E eNew = createEvent2();

				list.clear();
				list.add(eWithSameId);
				list.add(eNew);
				storer.insert(list);

				assertEquals(1, data.size());
				assertEquals(2, getEventTypes(data.iterator().next()).size());

				type = getEventTypes(data.iterator().next()).get(0);
				mergeValue(eWithSameId, e);
				assertTrue(isEqual(type, eWithSameId));

				type = getEventTypes(data.iterator().next()).get(1);
				assertTrue(isEqual(type, eNew));
			}

			{// Insert event of a different date:
				list.clear();
				e = createEvent();
				Calendar cal = e.getTime();
				int day = cal.get(Calendar.DAY_OF_MONTH);
				day = (day < 15) ? day + 1 : day - 1;
				cal.set(Calendar.DAY_OF_MONTH, day);

				list.add(e);
				storer.insert(list);

				assertEquals(2, data.size());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	@Override
	public void testInsert() {

		try {
			Collection<S> data = getDataField(storer);

			assertEquals(0, data.size());

			// Insert a new event:

			E e = createEvent();
			storer.insert(e);

			assertEquals(1, data.size());
			assertEquals(1, getEventTypes(data.iterator().next()).size());

			T type = getEventTypes(data.iterator().next()).get(0);
			assertTrue(isEqual(type, e));

			// Insert an event with the same partId and perspectiveId:

			e = createEvent();
			storer.insert(e);

			assertEquals(1, data.size());
			assertEquals(1, getEventTypes(data.iterator().next()).size());

			type = getEventTypes(data.iterator().next()).get(0);
			mergeValue(e, e);
			assertTrue(isEqual(type, e));

			// Insert an new and different event:

			e = createEvent2();
			storer.insert(e);

			assertEquals(1, data.size());
			assertEquals(2, getEventTypes(data.iterator().next()).size());

			type = getEventTypes(data.iterator().next()).get(1);
			assertTrue(isEqual(type, e));

			e = createEvent();
			Calendar cal = e.getTime();
			int day = cal.get(Calendar.DAY_OF_MONTH);
			day = (day < 15) ? day + 1 : day - 1;
			cal.set(Calendar.DAY_OF_MONTH, day);

			storer.insert(e);

			assertEquals(2, data.size());

		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	@Override
	public void testCommit() {

		try {
			E e = createEvent();
			storer.insert(e);
			storer.commit();
			assertTrue(dataFile.exists());

			List<S> allEvents = storer.getXmlTypeCategories(storer.getDataStore().read(dataFile));
			assertEquals(1, allEvents.size());

			S list = allEvents.get(0);
			assertEquals(1, getEventTypes(list).size());

			T type = getEventTypes(list).get(0);
			assertTrue(isEqual(type, e));

			assertTrue(getDataField(storer).isEmpty());

			// ...

			e = createEvent();
			storer.insert(e);
			storer.commit();

			allEvents = storer.getXmlTypeCategories(storer.getDataStore().read(dataFile));
			assertEquals(1, allEvents.size());

			list = allEvents.get(0);
			assertEquals(1, getEventTypes(list).size());

			type = getEventTypes(list).get(0);
			mergeValue(e, e);
			assertTrue(isEqual(type, e));

			// ...

			// Insert an new and different event:

			E e2 = createEvent2();
			storer.insert(e2);
			storer.commit();

			allEvents = storer.getXmlTypeCategories(storer.getDataStore().read(dataFile));
			assertEquals(1, allEvents.size());

			list = allEvents.get(0);
			assertEquals(2, getEventTypes(list).size());

			T newType = getEventTypes(list).get(0);
			if (storer.hasSameId(type, newType)) {
				newType = getEventTypes(list).get(1);
				type = getEventTypes(list).get(0);
			} else {
				type = getEventTypes(list).get(0);
			}

			assertTrue(isEqual(newType, e2));
			assertTrue(isEqual(type, e));

			// ..

			e = createEvent();
			Calendar cal = e.getTime();
			int day = cal.get(Calendar.DAY_OF_MONTH);
			day = (day < 15) ? day + 1 : day - 1;
			cal.set(Calendar.DAY_OF_MONTH, day);
			storer.insert(e);
			storer.commit();

			allEvents = storer.getXmlTypeCategories(storer.getDataStore().read(dataFile));
			assertEquals(2, allEvents.size());

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Override
	public void testMerge_listTypeAndlistType() {

		E e = createEvent();
		T x = storer.newXmlType(e);

		S main = storer.newXmlTypeHolder(null);
		storer.merge(main, e);

		S tmp = storer.newXmlTypeHolder(null);
		storer.merge(tmp, e);

		long totalDuration = getValue(x) * 2;
		storer.merge(main, tmp);
		assertEquals(1, getEventTypes(main).size());
		assertEquals(totalDuration, getValue(getEventTypes(main).get(0)));
	}

	@Override
	public void testMerge_listTypeAndEvent() {

		E e = createEvent();
		T x = storer.newXmlType(e);

		S main = storer.newXmlTypeHolder(getCalendar());
		getEventTypes(main).add(x);

		long totalDuration = getValue(x) * 2;
		storer.merge(main, e);
		assertEquals(1, getEventTypes(main).size());
		assertEquals(totalDuration, getValue(getEventTypes(main).get(0)));
	}

	@Override
	public void testMerge_typeAndEvent() {

		E e = createEvent();
		T main = storer.newXmlType(e);

		long totalDuration = getValue(e) + getValue(main);
		storer.merge(main, e);
		assertEquals(totalDuration, getValue(main));
	}

	@Override
	public void testMerge_typeAndType() {

		E e = createEvent();
		T main = storer.newXmlType(e);
		T tmp = storer.newXmlType(e);

		long totalDuration = getValue(main) + getValue(tmp);
		storer.merge(main, tmp);
		assertEquals(totalDuration, getValue(main));
	}

	@Override
	public void testNewXmlTypeT() {

		E e = createEvent();
		T xml = storer.newXmlType(e);

		assertTrue(storer.hasSameId(xml, e));
		assertEquals(getValue(xml), getValue(e));
	}

	@Override
	public void testNewXmlTypeHolderXMLGregorianCalendar() {

		XMLGregorianCalendar cal = getCalendar();
		S type = storer.newXmlTypeHolder(cal);
		assertEquals(cal, type.getDate());
	}

	private XMLGregorianCalendar getCalendar() {
		return DatatypeConverter.toXMLGregorianCalendarDate(Calendar.getInstance());
	}

	/** Gets the value (or usage) of the given type. */
	protected abstract long getValue(T type);

	/** Gets the value (or usage) of the given event. */
	protected abstract long getValue(E event);

	/** Gets the list of events form the parameter. */
	protected abstract List<T> getEventTypes(S type);

	/** Returns true if the parameters contains the same values. */
	protected abstract boolean isEqual(T type, E event);

	/** Merges the value form the second parameter into the first. */
	protected abstract void mergeValue(E main, E tmp);

}
