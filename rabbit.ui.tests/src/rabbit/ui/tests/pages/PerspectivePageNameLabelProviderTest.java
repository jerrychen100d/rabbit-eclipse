/*
 * Copyright 2010 The Rabbit Eclipse Plug-in Project
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
package rabbit.ui.tests.pages;

import rabbit.data.access.model.PerspectiveDataDescriptor;
import rabbit.ui.internal.pages.PerspectivePage;
import rabbit.ui.internal.pages.PerspectivePageContentProvider;
import rabbit.ui.internal.pages.PerspectivePageNameLabelProvider;
import rabbit.ui.internal.util.UndefinedPerspectiveDescriptor;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

/**
 * @see PerspectivePageNameLabelProvider
 */
@SuppressWarnings("restriction")
public class PerspectivePageNameLabelProviderTest extends
    DateStyledCellLabelProviderTest {

  private static PerspectivePage page;
  private static PerspectivePageContentProvider contents;

  @BeforeClass
  public static void setup() {
    page = new PerspectivePage();
    page.createContents(shell);
    contents = new PerspectivePageContentProvider(page, true);
    page.getViewer().setContentProvider(contents);
    page.getViewer().setLabelProvider(
        new PerspectivePageNameLabelProvider(contents));
  }

  @Before
  public void before() {
    page.getViewer().getTree().removeAll();
  }

  @Override
  protected PerspectivePageNameLabelProvider createLabelProvider() {
    return new PerspectivePageNameLabelProvider(contents);
  }

  @Test
  public void testUpdate_labelOfPerspective_displayByDateDisabled() {
    IPerspectiveDescriptor persp = new UndefinedPerspectiveDescriptor("abc");

    // COnstruct a data descriptor using the perspective Id:
    PerspectiveDataDescriptor data = new PerspectiveDataDescriptor(
        new LocalDate(), 121, persp.getId());

    // Disable it so that there is no tree items created for the date:
    contents.setDisplayByDate(false);
    page.getViewer().setInput(Arrays.asList(data));

    // The only item in the tree:
    TreeItem item = page.getViewer().getTree().getItem(0);
    assertEquals(persp.getLabel(), item.getText());
  }

  @Test
  public void testUpdate_labelOfPerspectiveDataDescriptor_displayByDateEnabled() {
    PerspectiveDataDescriptor des = new PerspectiveDataDescriptor(
        new LocalDate(), 11, "a.b.c");

    // Enable for testing:
    contents.setDisplayByDate(true);
    page.getViewer().setInput(Arrays.asList(des));

    page.getViewer().expandAll();
    // The 0th item is the date because displayByDate is enable, so the child of
    // this item is our perspective:
    TreeItem item = page.getViewer().getTree().getItem(0).getItem(0);
    assertEquals(contents.getPerspective(des).getLabel(), item.getText());
  }
}
