/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.eclipse.ui.views.properties.tabbed.ITabSelectionListener;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabContents;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class SelectionTabbedPropertySheetPage extends TabbedPropertySheetPage {

	private SelectionProvider selectionProvider;

	public SelectionTabbedPropertySheetPage(
			final ITabbedPropertySheetPageContributor tabbedPropertySheetPageContributor) {
		super(tabbedPropertySheetPageContributor);
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		selectionProvider = new SelectionProvider();
		addTabSelectionListener(selectionProvider);
		getSite().setSelectionProvider(selectionProvider);
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		super.selectionChanged(part, selection);
		selectionProvider.tabSelected(getSelectedTab());
	}

	private class SelectionProvider extends SelectionProviderProxy implements ITabSelectionListener {

		@Override
		public void tabSelected(final ITabDescriptor tabDescriptor) {
			final TabContents tabContents = getTabContents(tabDescriptor);
			if (tabContents != null && tabContents
					.getSectionAtIndex(0) instanceof final ISelectionProviderSection selectionProviderSection) {
				setDelegate(selectionProviderSection.getSelectionProvider());
			} else {
				setDelegate(null);
			}
		}
	}
}
