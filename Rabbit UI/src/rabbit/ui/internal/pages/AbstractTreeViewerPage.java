package rabbit.ui.internal.pages;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;

import rabbit.ui.CellPainter;
import rabbit.ui.internal.RabbitUI;

public abstract class AbstractTreeViewerPage extends AbstractValueProviderPage {

	private TreeViewer viewer;
	private TreeViewerColumn graphCol;

	public AbstractTreeViewerPage() {
	}

	@Override
	public void createContents(Composite parent) {
		viewer = new TreeViewer(parent, SWT.VIRTUAL | SWT.V_SCROLL | SWT.H_SCROLL);
		viewer.setContentProvider(createContentProvider());
		viewer.setLabelProvider(createLabelProvider());
		viewer.setUseHashlookup(true);
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				saveState();
			}
		});
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent e) {
				IStructuredSelection select = (IStructuredSelection) e.getSelection();
				Object o = select.getFirstElement();
				if (((ITreeContentProvider) viewer.getContentProvider()).hasChildren(o)) {
					viewer.setExpandedState(o, !viewer.getExpandedState(o));
				}
			}
		});

		createColumns(viewer);

		// Special column for painting:
		graphCol = new TreeViewerColumn(viewer, SWT.LEFT);
		graphCol.setLabelProvider(new CellPainter(this));
		graphCol.getColumn().setWidth(100);
		graphCol.getColumn().addSelectionListener(createValueSorterForTree(viewer));

		for (TreeColumn column : viewer.getTree().getColumns()) {
			column.setMoveable(true);
			column.setResizable(true);
		}
		viewer.setComparator(createComparator(viewer));
		restoreState();
	}
	
	@Override
	public boolean shouldPaint(Object element) {
		return true;
	}

	/** Saves the state of the page. */
	protected void saveState() {
		IPreferenceStore store = RabbitUI.getDefault().getPreferenceStore();
		for (TreeColumn column : getViewer().getTree().getColumns()) {
			store.setValue(getWidthPreferenceString(column), column.getWidth());
		}
	}

	/** Restores the state of the page. */
	protected void restoreState() {
		IPreferenceStore store = RabbitUI.getDefault().getPreferenceStore();
		for (TreeColumn column : getViewer().getTree().getColumns()) {
			int width = store.getInt(getWidthPreferenceString(column));
			if (width > 0) {
				column.setWidth(width);
			}
		}
	}

	private String getWidthPreferenceString(TreeColumn column) {
		return getClass().getSimpleName() + '.' + column.getText() + "Width";
	}

	public TreeViewer getViewer() {
		return viewer;
	}

	@Override
	public int getColumnWidth() {
		return graphCol.getColumn().getWidth();
	}

	protected abstract ViewerComparator createComparator(TreeViewer viewer);

	protected abstract void createColumns(TreeViewer viewer);

	protected abstract ITableLabelProvider createLabelProvider();

	protected abstract ITreeContentProvider createContentProvider();

}