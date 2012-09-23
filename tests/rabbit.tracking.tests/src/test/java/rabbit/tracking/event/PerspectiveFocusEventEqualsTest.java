/*
 * Copyright 2012 The Rabbit Eclipse Plug-in Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package rabbit.tracking.event;

import static java.util.Arrays.asList;
import static org.joda.time.Instant.now;
import static rabbit.tracking.event.PerspectiveFocusEventTest.eventWith;
import static rabbit.tracking.event.PerspectiveFocusEventTest.mockPerspective;
import static rabbit.tracking.tests.Instants.epoch;

import java.util.List;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import rabbit.tracking.tests.EqualsTestBase;

@RunWith(Parameterized.class)
public final class PerspectiveFocusEventEqualsTest extends EqualsTestBase {

  private static final IPerspectiveDescriptor PERSPECTIVE = mockPerspective();

  @Parameters public static List<Object[]> data() {
    return asList(new Object[][]{
        {

            eventWith(epoch(), PERSPECTIVE, true),
            eventWith(epoch(), PERSPECTIVE, true),
            eventWith(now(), PERSPECTIVE, true)},
        {

            eventWith(epoch(), PERSPECTIVE, true),
            eventWith(epoch(), PERSPECTIVE, true),
            eventWith(epoch(), mockPerspective(), true)},
        {

            eventWith(epoch(), PERSPECTIVE, true),
            eventWith(epoch(), PERSPECTIVE, true),
            eventWith(epoch(), PERSPECTIVE, false)},
        {
            eventWith(epoch(), PERSPECTIVE, true),
            eventWith(epoch(), PERSPECTIVE, true),
            eventWith(now(), mockPerspective(), false)},
    });
  }

  public PerspectiveFocusEventEqualsTest(Object a1, Object a2, Object b) {
    super(a1, a2, b);
  }

}