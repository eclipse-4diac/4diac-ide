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
 *                 - Search partially taken from ModelSearchQuery
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.search.ModelSearchResultPage;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class StructChangedSaveForAll extends MessageDialog {

	private StructChangedSearchResult searchResult;
	private final DataTypeEntry dataTypeEntry;

	private static final int NUMBER_OF_COLLUMNS = 1;
	private static final int WIDGET_WIDTH = 200;

	public StructChangedSaveForAll(final Shell parentShell, final String dialogTitle, final Image dialogTitleImage,
			final String dialogMessage, final int dialogImageType, final String[] dialogButtonLabels,
			final int defaultIndex, final DataTypeEntry dataTypeEntry) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, dialogButtonLabels,
				defaultIndex);
		this.dataTypeEntry = dataTypeEntry;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new GridLayout(NUMBER_OF_COLLUMNS, true));

		final Composite searchResArea = WidgetFactory.composite(NONE).create(parent);
		searchResArea.setLayout(new GridLayout(1, true));
		searchResArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		getAllTypesWithStruct();

		if (searchResult.getStructSearchResults().isEmpty()) {
			// No results - display just the info
			final Label warningLabel = LabelFactory.newLabel(NONE).create(searchResArea);
			warningLabel.setText("No additional function blocks or types have been affected by this change!"); //$NON-NLS-1$
		} else {
			final TableViewer viewer = createTableViewer(searchResArea);
			configureTableViewer(viewer);

			viewer.setInput(searchResult.getStructSearchResults().toArray());

		}
		return parent;

	}

	private static TableViewer createTableViewer(final Composite parent) {
		return TableWidgetFactory.createTableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	}

	private static void configureTableViewer(final TableViewer viewer) {
		viewer.setContentProvider(new ArrayContentProvider());
		final Table table = viewer.getTable();

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(createTableLayout());


		// FB-name column
		final TableViewerColumn colFB = new TableViewerColumn(viewer, SWT.LEAD);
		colFB.getColumn().setText(Messages.FunctionBlock);
		colFB.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof INamedElement) {
					return ((INamedElement) element).getName();
				}
				return super.getText(element);
			}
		});

		// Location name column
		final TableViewerColumn colPath = new TableViewerColumn(viewer, SWT.LEAD);
		colPath.getColumn().setText(Messages.Location);
		colPath.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ModelSearchResultPage.hierarchicalName(element);
			}
		});

	}

	private static TableLayout createTableLayout() {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(WIDGET_WIDTH));
		layout.addColumnData(new ColumnPixelData(WIDGET_WIDTH));
		return layout;
	}




	private List<INamedElement> getAllTypesWithStruct() {
		searchResult = new StructChangedSearchResult();
		final List<AutomationSystem> searchRootSystems = new ArrayList<>();
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		for (final IProject proj : root.getProjects()) {
			if (proj.isOpen()) {
				searchRootSystems.addAll(SystemManager.INSTANCE.getProjectSystems(proj).values());
			}
		}

		for (final AutomationSystem sys : searchRootSystems) {
			searchApplications(sys);
		}

		return searchResult.getStructSearchResults();
	}

	private void searchApplications(final AutomationSystem sys) {
		for (final Application app : sys.getApplication()) {
			searchApplication(app);
		}
	}

	private void searchApplication(final Application app) {
		searchFBNetwork(app.getFBNetwork());
	}

	private void searchFBNetwork(final FBNetwork network) {
		if (network != null) {
			for (final FBNetworkElement fbnetworkElement : network.getNetworkElements()) {
				matchStruct(fbnetworkElement);
				if (fbnetworkElement instanceof SubApp) {
					searchFBNetwork(((SubApp) fbnetworkElement).getSubAppNetwork());
				}
			}
		}
	}

	private void matchStruct(final EObject possibleStruct) {
		if (possibleStruct instanceof StructManipulator) {
			final StructManipulator sm = ((StructManipulator) possibleStruct);
			for (final IInterfaceElement interfaceElem : sm.getInterface().getAllInterfaceElements()) {
				if (interfaceElem.getType().getName().equalsIgnoreCase(dataTypeEntry.getTypeName())) {
					searchResult.addResults(sm); // Adding the actual FB that should be in there
				}
			}
		}
	}

}
