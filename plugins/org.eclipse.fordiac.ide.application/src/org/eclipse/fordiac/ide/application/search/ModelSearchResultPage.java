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

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.ISearchResultViewPart;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.part.IPageSite;


public class ModelSearchResultPage extends AbstractTextSearchViewPage {

	private final String ID = "org.eclipse.fordiac.ide.application.search.ModelSearchResultPage"; //$NON-NLS-1$
	private Composite rootComposite;
	private IPageSite pageSite;
	private ISearchResultViewPart viewPart;
	private IContentProvider contentProvider;
	private ISearchResult result; // What the search returns, get it from the setInput()


	@Override
	public IPageSite getSite() {
		return pageSite;
	}

	@Override
	public void init(final IPageSite site) {
		pageSite = site;
	}

	@Override
	public void createControl(final Composite parent) {

		rootComposite = WidgetFactory.composite(SWT.NONE).create(parent);

		final LabelFactory labelFactory = LabelFactory.newLabel(SWT.NONE);
		labelFactory.text("results page").create(rootComposite);

		// GridLayoutFactory.fillDefaults().numColumns(1).margins(LayoutConstants.getMargins()).generateLayout(parent);
		GridLayoutFactory.fillDefaults().numColumns(1).generateLayout(rootComposite);
		// rootComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		rootComposite.setVisible(true);

		System.err.println("createControl from ModelSearchResultPage");
	}

	@Override
	public void dispose() {
		// Nothing to do here
	}

	@Override
	public Control getControl() {
		return rootComposite;
	}

	@Override
	public void setActionBars(final IActionBars actionBars) {
		// Nothing to do here
	}

	@Override
	public void setFocus() {
		rootComposite.setFocus(); // Display is null for the 2nd search, org.eclipse.swt.SWTException:Widget is disposed
	}

	// getUIState has been omitted

	// Sets the search result to be shown in this search results page.
	@Override
	public void setInput(final ISearchResult searchResult, final Object uiState) {
		if (searchResult != null) {
			result = searchResult;
			// TODO: finish
		}
	}

	@Override
	public void setViewPart(final ISearchResultViewPart part) {
		super.setViewPart(part);
		this.viewPart = part;
		// viewPart.createPartControl(rootComposite);
	}

	@Override
	public ISearchResultViewPart getViewPart() {
		return viewPart;
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
		return "blahblah";
	}

	@Override
	protected void elementsChanged(final Object[] objects) {

	}

	@Override
	protected void clear() {
		if (contentProvider != null) {
			// contentProvider.clear();
		}
	}

	@SuppressWarnings("cast")
	@Override
	protected void configureTreeViewer(final TreeViewer viewer) {
		viewer.setUseHashlookup(true);
		viewer.setLabelProvider(new ColumnLabelProvider());
		// viewer.setContentProvider(new ModelSearchTreeContentProvider());
		contentProvider = viewer.getContentProvider(); // Cast into mstreecontprov

		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);

		final TreeViewerColumn vColumn = new TreeViewerColumn(viewer, SWT.NONE);
		vColumn.getColumn().setWidth(300);
		vColumn.getColumn().setText("Dummy results");
		vColumn.setLabelProvider(new ColumnLabelProvider());

		// Set the input -> give the data for parsing
		viewer.setInput(result); // Now it's the "simple" res with only a list of strings (TODO: change later)

		GridLayoutFactory.fillDefaults().generateLayout(rootComposite);
	}

	@SuppressWarnings("cast")
	@Override
	protected void configureTableViewer(final TableViewer viewer) {
		viewer.setUseHashlookup(true);
		// viewer.setContentProvider(new ModelSearchTableContentProvider());
		// The one that's saved in the object is either the table or the tree provider - cast into tablecontprov
		contentProvider = viewer.getContentProvider();
		viewer.setLabelProvider(new ColumnLabelProvider());

		final TableViewerColumn vColumn = new TableViewerColumn(viewer, SWT.NONE);
		vColumn.getColumn().setWidth(300);
		vColumn.getColumn().setText("Dummy data");
		vColumn.setLabelProvider(new ColumnLabelProvider());
		// TODO: add ViewSorter and ViewerFilter for table and tree
		// Give the result and then the TableContentProvider parses it.
		viewer.setInput(result);

		GridLayoutFactory.fillDefaults().generateLayout(rootComposite);
	}

	@Override
	protected StructuredViewer getViewer() {
		return super.getViewer();
	}
}
