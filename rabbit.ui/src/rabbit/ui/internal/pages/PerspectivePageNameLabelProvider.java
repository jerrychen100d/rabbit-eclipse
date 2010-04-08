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
package rabbit.ui.internal.pages;

import rabbit.data.access.model.PerspectiveDataDescriptor;
import rabbit.ui.internal.util.UndefinedPerspectiveDescriptor;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.PerspectiveLabelProvider;

/**
 * Label provider for providing labels for a {@link PerspectivePage}'s name
 * column.
 */
public class PerspectivePageNameLabelProvider extends
    DateStyledCellLabelProvider {

  private final Color gray;
  private final PerspectiveLabelProvider provider;
  private final PerspectivePageContentProvider content;

  /**
   * Constructor.
   * 
   * @param content The content provider for the page.
   */
  public PerspectivePageNameLabelProvider(PerspectivePageContentProvider content) {
    checkNotNull(content);

    gray = PlatformUI.getWorkbench().getDisplay().getSystemColor(
        SWT.COLOR_DARK_GRAY);

    provider = new PerspectiveLabelProvider(false);
    this.content = content;
  }

  @Override
  public void dispose() {
    provider.dispose();
    super.dispose();
  }

  @Override
  public void update(ViewerCell cell) {
    super.update(cell);

    Object element = cell.getElement();

    if (element instanceof IPerspectiveDescriptor) {
      cell.setText(provider.getText(element));
      cell.setImage(provider.getImage(element));

    } else if (element instanceof PerspectiveDataDescriptor) {
      IPerspectiveDescriptor des = content
          .getPerspective((PerspectiveDataDescriptor) element);
      cell.setText(provider.getText(des));
      cell.setImage(provider.getImage(des));
    }

    if (element instanceof UndefinedPerspectiveDescriptor)
      cell.setForeground(gray);
    else
      cell.setForeground(null);
  }

  /**
   * Gets the foreground color for undefined perspectives.
   */
  public Color getUndefinedPerspectiveForeground() {
    return gray;
  }
}
