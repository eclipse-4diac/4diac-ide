/*******************************************************************************
 * Copyright (c) 2008 -2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.ui.wizard;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.export.ui.Activator;
import org.eclipse.fordiac.ide.export.utils.IExportFilter;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.WizardExportResourcesPage;

/**
 * The Class SelectFBTypesWizardPage.
 */
public class SelectFBTypesWizardPage extends WizardExportResourcesPage {

	/** The dcc. */
	private Combo destinationNameField;

	/** The overwrite. */
	private Button overwrite;
	
    // dialog store id constants
    private static final String STORE_DIRECTORY_NAMES_ID = "SelectFBTypesWizardPage.STORE_DIRECTORY_NAMES_ID"; //$NON-NLS-1$

    private static final String STORE_CURRENT_FILTER_SELECTION_ID = "SelectFBTypesWizardPage.STORE_CURRENT_FILTER_SELECTION_ID"; //$NON-NLS-1$


	/**
	 * Instantiates a new SelectFBTypesWizardPage.
	 * 
	 * @param pageName
	 *            the page name
	 */
	protected SelectFBTypesWizardPage(final String pageName,
			IStructuredSelection selection) {
		super(pageName, selection);
	}

	private final List<IExportFilter> exportFilters = new ArrayList<>();
	private Combo filters;

	private void addAvailableExportFilter(final Group group) {
		SortedMap<Integer, IExportFilter> sortedExportFiltersMap = new TreeMap<>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				Activator.PLUGIN_ID, "ExportFilter"); //$NON-NLS-1$
		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IExportFilter) {
					Integer sortIndex = Integer.valueOf(element.getAttribute("sortIndex")); //$NON-NLS-1$
					sortedExportFiltersMap.put(sortIndex,
							(IExportFilter) object);
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError("Error loading Export Filter", corex);
			}
		}

		for (Iterator<IExportFilter> iterator = sortedExportFiltersMap.values()
				.iterator(); iterator.hasNext();) {
			IExportFilter exportFilter = iterator.next();
			exportFilters.add(exportFilter);
		}

		Composite composite = new Composite(group, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.VERTICAL_ALIGN_FILL));

		Label label = new Label(composite, SWT.NONE);
		label.setText("Exporter:");

		filters = new Combo(composite, SWT.NONE);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		filters.setLayoutData(data);

		for (IExportFilter exportFilter : exportFilters) {
			filters.add(exportFilter.getExportFilterName());
		}

		filters.addListener( SWT.Selection, event -> updatePageCompletion());
	}

	/**
	 * Gets the selected export filter.
	 * 
	 * @return the selected export filter
	 */
	public IExportFilter getSelectedExportFilter() {
		return exportFilters.get(filters.getSelectionIndex());
	}

	@Override
	protected boolean validateDestinationGroup() {

		if (getDirectory() == null || getDirectory().equals("") //$NON-NLS-1$
				|| !new File(getDirectory()).isDirectory()) {
			setErrorMessage("Destination directory needs to be choosen!");
			return false;
		}
		return super.validateDestinationGroup();
	}

	@Override
	protected boolean validateOptionsGroup() {
		if (filters.getSelectionIndex() == -1) {
			setErrorMessage("Exportfilter needs to be selected!");
			return false;
		}
		return super.validateOptionsGroup();
	}

	@Override
	protected boolean validateSourceGroup() {
		if (getSelectedResources().isEmpty()) {
			setErrorMessage("No type selected!");
			return false;
		}
		return super.validateSourceGroup();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getSelectedResources() {
		return super.getSelectedResources();
	}

	/**
	 * Overwrite without warning.
	 * 
	 * @return true, if successful
	 */
	public boolean overwriteWithoutWarning() {
		return overwrite.getSelection();
	}

	/**
	 * Gets the directory.
	 * 
	 * @return the directory
	 */
	public String getDirectory() {
		return destinationNameField.getText().trim();
	}

	/**
	 * Sets the directory.
	 * 
	 * @param dir
	 *            the new directory
	 */
	public void setDirectory(final String dir) {
		destinationNameField.setText(dir);
	}

	
	@Override
	protected void restoreWidgetValues() {
		loadTargetDirctories();
		loadLastSelectedFilter();
	}

	/**
	 * Loads cached directory, if available.
	 */
	private void loadTargetDirctories() {
		IDialogSettings settings = getDialogSettings();
        if (settings != null) {
	        String[] directoryNames = settings
	                .getArray(STORE_DIRECTORY_NAMES_ID);
	        if (directoryNames == null) {
				return; // ie.- no settings stored
			}
	
	        // destination
	        setDirectory(directoryNames[0]);
	        for (int i = 0; i < directoryNames.length; i++) {
	        	destinationNameField.add(directoryNames[i]);
			}
        }
	}

	/**
	 * Loads cached filter, if available.
	 */
	private void loadLastSelectedFilter() {
		if (getDialogSettings() != null) {
			String currentFilterSelectionName = getDialogSettings().get(STORE_CURRENT_FILTER_SELECTION_ID);
			if (currentFilterSelectionName != null) {
				for (IExportFilter filter : exportFilters) {
					if (filter.getExportFilterName().equals(
							currentFilterSelectionName)) {
						filters.select(exportFilters.indexOf(filter));
						break;
					}
				}
			}
		}
	}


	@Override
	protected void internalSaveWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			//Saves current directory for next session.
			String[] directoryNames = settings.getArray(STORE_DIRECTORY_NAMES_ID);
			if (directoryNames == null) {
				directoryNames = new String[0];
			}

			directoryNames = addToHistory(directoryNames, getDirectory());
			settings.put(STORE_DIRECTORY_NAMES_ID, directoryNames);

			// Saves current export filter for next session.
			getDialogSettings().put(STORE_CURRENT_FILTER_SELECTION_ID,
					getSelectedExportFilter().getExportFilterName());
		}
	}

	@Override
	protected void createDestinationGroup(Composite parent) {

		Font font = parent.getFont();
		// destination specification group
		Composite destinationSelectionGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		destinationSelectionGroup.setLayout(layout);
		destinationSelectionGroup.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));
		destinationSelectionGroup.setFont(font);

		Label destinationLabel = new Label(destinationSelectionGroup, SWT.NONE);
		destinationLabel.setText("Export Destination:");
		destinationLabel.setFont(font);

		// destination name entry field
		destinationNameField = new Combo(destinationSelectionGroup, SWT.SINGLE
				| SWT.BORDER);
		destinationNameField.addListener(SWT.Modify, this);
		destinationNameField.addListener(SWT.Selection, this);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		destinationNameField.setLayoutData(data);
		destinationNameField.setFont(font);

		// destination browse button
		Button destinationBrowseButton = new Button(destinationSelectionGroup,
				SWT.PUSH);
		destinationBrowseButton.setText("B&rowse...");
		destinationBrowseButton.addListener( SWT.Selection, event -> {
				DirectoryDialog dialog = new DirectoryDialog(getContainer()
						.getShell(), SWT.SAVE | SWT.SHEET);
				dialog.setMessage("Select a directory to export to.");
				dialog.setText("Export to Directory");
				dialog.setFilterPath(getDirectory());
				String selectedDirectoryName = dialog.open();

				if (selectedDirectoryName != null) {
					setErrorMessage(null);
					setDirectory(selectedDirectoryName);
				}
				updatePageCompletion();
			});

		destinationBrowseButton.setFont(font);
		setButtonLayoutData(destinationBrowseButton);

		new Label(parent, SWT.NONE); // vertical spacer
	}

	@Override
	protected void createOptionsGroupButtons(Group optionsGroup) {
		super.createOptionsGroupButtons(optionsGroup);
		addAvailableExportFilter(optionsGroup);

		overwrite = new Button(optionsGroup, SWT.CHECK);
		overwrite.setText("Overwrite without warning");
		GridData twoColumns = new GridData();
		twoColumns.grabExcessHorizontalSpace = true;
		twoColumns.grabExcessVerticalSpace = false;
		twoColumns.horizontalAlignment = SWT.FILL;
		twoColumns.verticalAlignment = SWT.TOP;

		overwrite.setLayoutData(twoColumns);
	}

	@Override
	public void handleEvent(Event event) {
	}
	
	@Override  //this overide is needed to make it public for access by the wizard
	public void saveWidgetValues() {
		super.saveWidgetValues();
	}
}
