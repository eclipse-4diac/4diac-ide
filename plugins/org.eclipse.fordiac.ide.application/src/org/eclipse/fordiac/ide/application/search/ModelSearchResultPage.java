/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.search;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IMemento;

public class ModelSearchResultPage extends AbstractTextSearchViewPage {

	private final String ID = "org.eclipse.fordiac.ide.application.search.ModelSearchResultPage"; //$NON-NLS-1$
	private ModelSearchTableContentProvider contentProvider;

	public ModelSearchResultPage() {
		super(AbstractTextSearchViewPage.FLAG_LAYOUT_FLAT); // FLAG_LAYOUT_FLAT = table layout
	}

	@Override
	public void restoreState(final IMemento memento) {
		// Nothing to do here yet
	}

	@Override
	public void saveState(final IMemento memento) {
		// Nothing to do here yet
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public String getLabel() {
		return "IEC 61499 Model Search";
	}

	@Override
	protected void elementsChanged(final Object[] objects) {
		if (contentProvider != null) {
			contentProvider.elementsChanged(objects);
		}
	}

	@Override
	protected void clear() {
		if (contentProvider != null) {
			contentProvider.clear();
		}
	}

	@Override
	protected void configureTreeViewer(final TreeViewer viewer) {
		throw new IllegalStateException("Doesn't support tree mode."); //$NON-NLS-1$
	}

	// This method may be called if the page was constructed with the flag FLAG_LAYOUT_FLAT (see constructor)
	@Override
	protected void configureTableViewer(final TableViewer viewer) {
		contentProvider = new ModelSearchTableContentProvider(this);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(new ColumnLabelProvider());

		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

	@Override
	public StructuredViewer getViewer() {
		return super.getViewer();
	}

}
