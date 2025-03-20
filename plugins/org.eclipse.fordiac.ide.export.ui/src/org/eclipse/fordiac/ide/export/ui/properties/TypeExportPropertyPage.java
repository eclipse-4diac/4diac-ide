/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.export.ui.properties;

import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.export.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.export.ui.Messages;
import org.eclipse.fordiac.ide.export.utils.ExportFilterUtil;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class TypeExportPropertyPage extends PropertyPage {

	private OutputDirectoryFieldEditor directoryEditor;
	private BooleanFieldEditor checkboxEditor;
	private ComboFieldEditor exporterEditor;

	private Group settingsContainer;
	private Composite directoryEditorContainer;
	private Composite exporterEditorContainer;

	private static final String OUTPUT_FOLDER_NAME = "out"; //$NON-NLS-1$

	@Override
	protected Control createContents(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(composite);
		createEnableCheckbox(composite);

		this.settingsContainer = new Group(composite, SWT.NONE);
		settingsContainer.setText(Messages.TypeExport_Settings);
		GridLayoutFactory.fillDefaults().margins(10, 10).applyTo(settingsContainer);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(settingsContainer);

		createDirectoryEditor(settingsContainer);
		createDefaultExporterEditor(settingsContainer);
		refreshEditors();

		return composite;
	}

	private void createDefaultExporterEditor(final Composite parent) {
		exporterEditorContainer = new Composite(parent, SWT.NONE);
		exporterEditor = new ComboFieldEditor(PreferenceConstants.EXPORT_FILTER_ID, Messages.TypeExport_Exporter,
				getExportFilterNames(), exporterEditorContainer);
		exporterEditor.setPreferenceStore(getPreferenceStore());
	}

	private void createEnableCheckbox(final Composite parent) {
		final Composite checkboxEditorContainer = new Composite(parent, SWT.NONE);
		checkboxEditor = new BooleanFieldEditor(PreferenceConstants.ENABLE_TYPE_EXPORT, Messages.TypeExport_Enable,
				checkboxEditorContainer);
		checkboxEditor.setPreferenceStore(getPreferenceStore());

		if (checkboxEditor.getDescriptionControl(checkboxEditorContainer) instanceof final Button button) {
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					enableSettings(checkboxEditor.getBooleanValue());
					super.widgetSelected(e);
				}
			});
		}
	}

	@Override
	protected void performDefaults() {
		directoryEditor.loadDefault();
		checkboxEditor.loadDefault();
		exporterEditor.loadDefault();
		enableSettings(false);
		super.performDefaults();
	}

	private void createDirectoryEditor(final Composite parent) {
		directoryEditorContainer = new Composite(parent, SWT.NONE);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(directoryEditorContainer);
		directoryEditor = new OutputDirectoryFieldEditor(PreferenceConstants.OUTPUT_FOLDER,
				Messages.TypeExport_OutputFolder, directoryEditorContainer);

		directoryEditor.setPreferenceStore(getPreferenceStore());
		directoryEditor.setPage(this);
		directoryEditor.setEmptyStringAllowed(false);
		directoryEditor.getTextControl(directoryEditorContainer)
				.addModifyListener(e -> directoryEditor.getTextControl(directoryEditorContainer).requestLayout());
	}

	private String getOutputFolder() {
		if (getProject().getFolder(OUTPUT_FOLDER_NAME).exists()) {
			return getProject().getFolder(OUTPUT_FOLDER_NAME).getProjectRelativePath().toPortableString();
		}

		return ""; //$NON-NLS-1$
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		final ProjectScope projectScope = new ProjectScope(getProject());
		final ScopedPreferenceStore prefStore = new ScopedPreferenceStore(projectScope,
				PreferenceConstants.EXPORT_PREFERENCES_ID);
		prefStore.setDefault(PreferenceConstants.OUTPUT_FOLDER, getOutputFolder());
		return prefStore;
	}

	@Override
	public boolean performOk() {
		checkboxEditor.store();

		if (checkboxEditor.getBooleanValue()) {
			exporterEditor.store();
			if (!directoryEditor.isValid()) {
				directoryEditor.showErrorMessage();
				return false;
			}
			directoryEditor.store();
		}
		return super.performOk();
	}

	protected IProject getProject() {
		return Adapters.adapt(getElement(), IProject.class);
	}

	private static String[][] getExportFilterNames() {
		return Stream.of(ExportFilterUtil.getExportFilters())
				.map(e -> new String[] { e.getAttribute("name"), e.getAttribute("id") }) //$NON-NLS-1$ //$NON-NLS-2$
				.toArray(size -> new String[size][2]);
	}

	private void refreshEditors() {
		checkboxEditor.load();
		directoryEditor.load();
		exporterEditor.load();
		enableSettings(checkboxEditor.getBooleanValue());
	}

	private void enableSettings(final boolean enable) {
		settingsContainer.setEnabled(enable);
		directoryEditor.setEnabled(enable, directoryEditorContainer);
		exporterEditor.setEnabled(enable, exporterEditorContainer);
	}

	/*
	 * Class partly copied from @see DirectoryFieldEditor editor to handle relative
	 * paths
	 */
	class OutputDirectoryFieldEditor extends StringButtonFieldEditor {

		public OutputDirectoryFieldEditor(final String name, final String labelText, final Composite parent) {
			init(name, labelText);
			setErrorMessage(JFaceResources.getString("DirectoryFieldEditor.errorMessage"));//$NON-NLS-1$
			setChangeButtonText(JFaceResources.getString("openBrowse"));//$NON-NLS-1$
			setValidateStrategy(VALIDATE_ON_FOCUS_LOST);
			createControl(parent);
		}

		@Override
		protected String changePressed() {
			final Optional<IFolder> selectedDirectory = chooseOutputFolder();

			if (selectedDirectory.isPresent() && selectedDirectory.get().exists()) {
				return selectedDirectory.get().getProjectRelativePath().toString();
			}
			// keep old value
			return null;
		}

		@Override
		protected boolean doCheckState() {
			String fileName = getTextControl().getText();
			fileName = fileName.trim();
			if (fileName.isEmpty() && isEmptyStringAllowed()) {
				return true;
			}
			return getProject().getFolder(fileName).exists();
		}

		private Optional<IFolder> chooseOutputFolder() {
			final ElementTreeSelectionDialog fileDialog = new ElementTreeSelectionDialog(getShell(),
					new WorkbenchLabelProvider(), new WorkbenchContentProvider());

			fileDialog.setInput(getProject());
			fileDialog.setAllowMultiple(false);
			fileDialog.setTitle(Messages.TypeExport_OutputFolder);
			fileDialog.setMessage(Messages.TypeExport_FileDialogMessage);
			if (!getTextControl().getText().isEmpty()) {
				final IFolder selectedElement = getProject().getFolder(new Path(getTextControl().getText()));
				if (selectedElement.exists()) {
					fileDialog.setInitialSelection(selectedElement);
				}
			}

			fileDialog.addFilter(new ViewerFilter() {
				@Override
				public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
					return (element instanceof final IFolder folder) && !folder.isVirtual() && !folder.isLinked()
							&& !folder.getName().startsWith("."); //$NON-NLS-1$
				}
			});

			if ((Window.OK == fileDialog.open() && fileDialog.getResult().length == 1)
					&& (fileDialog.getFirstResult() instanceof final IFolder folder)) {
				return Optional.of(folder);
			}

			return Optional.empty();
		}
	}
}