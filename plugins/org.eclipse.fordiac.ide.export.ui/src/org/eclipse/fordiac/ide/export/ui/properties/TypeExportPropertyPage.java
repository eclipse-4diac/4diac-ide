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
 *   Michael Oberlehner - add support for additional source directories during type export
 *******************************************************************************/

package org.eclipse.fordiac.ide.export.ui.properties;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.export.builder.ExportBuilder;
import org.eclipse.fordiac.ide.export.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.export.ui.Messages;
import org.eclipse.fordiac.ide.export.utils.AdditionalSourceDirectories;
import org.eclipse.fordiac.ide.export.utils.ExportFilterUtil;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
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

	private OutputDirectoryFieldEditor outputDirectoryEditor;
	private BooleanFieldEditor enableExportEditor;
	private ComboFieldEditor exporterEditor;
	private AdditionalSourceDirectoriesSection additionalSourceDirectoriesSection;

	private Group settingsContainer;
	private Composite outputDirectoryEditorContainer;
	private Composite exporterEditorContainer;

	@Override
	protected Control createContents(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		createEnableCheckbox(composite);

		settingsContainer = new Group(composite, SWT.NONE);
		settingsContainer.setText(Messages.TypeExport_Settings);
		GridLayoutFactory.fillDefaults().margins(10, 10).applyTo(settingsContainer);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(settingsContainer);

		createDirectoryEditor(settingsContainer);
		createDefaultExporterEditor(settingsContainer);
		additionalSourceDirectoriesSection = new AdditionalSourceDirectoriesSection(settingsContainer, this::validatePage);
		loadPreferences();

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
		enableExportEditor = new BooleanFieldEditor(PreferenceConstants.ENABLE_TYPE_EXPORT, Messages.TypeExport_Enable,
				checkboxEditorContainer);
		enableExportEditor.setPreferenceStore(getPreferenceStore());

		if (enableExportEditor.getDescriptionControl(checkboxEditorContainer) instanceof final Button button) {
			button.addListener(SWT.Selection, event -> setSettingsEnabled(enableExportEditor.getBooleanValue()));
		}
	}

	@Override
	protected void performDefaults() {
		outputDirectoryEditor.loadDefault();
		enableExportEditor.loadDefault();
		exporterEditor.loadDefault();
		additionalSourceDirectoriesSection.setDirectories(List.of());
		updateAdditionalSourceDirectoryOutput();
		setSettingsEnabled(enableExportEditor.getBooleanValue());
		super.performDefaults();
	}

	private void createDirectoryEditor(final Composite parent) {
		outputDirectoryEditorContainer = new Composite(parent, SWT.NONE);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(outputDirectoryEditorContainer);
		outputDirectoryEditor = new OutputDirectoryFieldEditor(PreferenceConstants.OUTPUT_FOLDER,
				Messages.TypeExport_OutputFolder, outputDirectoryEditorContainer);

		outputDirectoryEditor.setPreferenceStore(getPreferenceStore());
		outputDirectoryEditor.setPage(this);
		outputDirectoryEditor.getTextControl(outputDirectoryEditorContainer).addModifyListener(_ -> {
			outputDirectoryEditor.getTextControl(outputDirectoryEditorContainer).requestLayout();
			updateAdditionalSourceDirectoryOutput();
			validatePage();
		});
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		final ProjectScope projectScope = new ProjectScope(getProject());
		final ScopedPreferenceStore prefStore = new ScopedPreferenceStore(projectScope,
				PreferenceConstants.EXPORT_PREFERENCES_ID);
		prefStore.setDefault(PreferenceConstants.OUTPUT_FOLDER, PreferenceConstants.DEFAULT_OUTPUT_FOLDER_NAME);
		prefStore.setDefault(PreferenceConstants.ADDITIONAL_SOURCE_DIRECTORIES, ""); //$NON-NLS-1$
		return prefStore;
	}

	@Override
	public boolean performOk() {
		enableExportEditor.store();

		if (enableExportEditor.getBooleanValue()) {
			if (!validatePage()) {
				return false;
			}
			exporterEditor.store();
			outputDirectoryEditor.store();
			getPreferenceStore().setValue(PreferenceConstants.ADDITIONAL_SOURCE_DIRECTORIES,
					AdditionalSourceDirectories.formatPaths(additionalSourceDirectoriesSection.getDirectories()));
		}

		if (getPreferenceStore() instanceof final IPersistentPreferenceStore store && store.needsSaving()) {
			if (!saveAndUpdateExport(store)) {
				return false;
			}
		}
		return super.performOk();
	}

	/**
	 * Preference changes do not create a resource delta, therefore the exported
	 * files have to be regenerated explicitly.
	 */
	private boolean saveAndUpdateExport(final IPersistentPreferenceStore store) {
		try {
			store.save();
		} catch (final IOException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			setErrorMessage(e.getLocalizedMessage());
			return false;
		}
		final IProject project = getProject();
		final Job job = Job.create(Messages.TypeExport_UpdateExportedFiles, (ICoreRunnable) monitor -> project
				.build(IncrementalProjectBuilder.FULL_BUILD, ExportBuilder.BUILDER_ID, null, monitor));
		job.setRule(project.getWorkspace().getRuleFactory().buildRule());
		job.schedule();
		return true;
	}

	protected IProject getProject() {
		return Adapters.adapt(getElement(), IProject.class);
	}

	private static String[][] getExportFilterNames() {
		return Stream.of(ExportFilterUtil.getExportFilters())
				.map(e -> new String[] { e.getAttribute("name"), e.getAttribute("id") }) //$NON-NLS-1$ //$NON-NLS-2$
				.toArray(size -> new String[size][2]);
	}

	private void loadPreferences() {
		enableExportEditor.load();
		outputDirectoryEditor.load();
		exporterEditor.load();
		additionalSourceDirectoriesSection.setDirectories(AdditionalSourceDirectories
				.parsePaths(getPreferenceStore().getString(PreferenceConstants.ADDITIONAL_SOURCE_DIRECTORIES)));
		updateAdditionalSourceDirectoryOutput();
		setSettingsEnabled(enableExportEditor.getBooleanValue());
	}

	private void setSettingsEnabled(final boolean enable) {
		settingsContainer.setEnabled(enable);
		outputDirectoryEditor.setEnabled(enable, outputDirectoryEditorContainer);
		exporterEditor.setEnabled(enable, exporterEditorContainer);
		additionalSourceDirectoriesSection.setEditorEnabled(enable);
		validatePage();
	}

	private IFolder getOutputFolder() {
		return getProject().getFolder(new Path(outputDirectoryEditor.getStringValue().trim()));
	}

	private void updateAdditionalSourceDirectoryOutput() {
		final String outputDirectory = outputDirectoryEditor.getStringValue().trim();
		additionalSourceDirectoriesSection.setOutputFolder(
				isValidOutputDirectory(outputDirectory) ? Optional.of(getOutputFolder()) : Optional.empty());
	}

	private boolean isValidOutputDirectory(final String outputDirectory) {
		return ExportFilterUtil.validateExportPath(outputDirectory, getProject())
				&& AdditionalSourceDirectories.validatePaths(getProject(), new Path(outputDirectory), List.of(), false);
	}

	private boolean validatePage() {
		if (enableExportEditor == null || !enableExportEditor.getBooleanValue()) {
			setErrorMessage(null);
			setValid(true);
			return true;
		}
		if (!outputDirectoryEditor.isValid()) {
			setErrorMessage(Messages.TypeExport_InvalidPath);
			setValid(false);
			return false;
		}
		if (!AdditionalSourceDirectories.validatePaths(getProject(),
				new Path(outputDirectoryEditor.getStringValue().trim()),
				additionalSourceDirectoriesSection.getDirectories(), true)) {
			setErrorMessage(Messages.TypeExport_InvalidSourceDirectories);
			setValid(false);
			return false;
		}
		setErrorMessage(null);
		setValid(true);
		return true;
	}

	/*
	 * Class partly copied from @see DirectoryFieldEditor editor to handle relative
	 * paths
	 */
	private final class OutputDirectoryFieldEditor extends StringButtonFieldEditor {

		private OutputDirectoryFieldEditor(final String name, final String labelText, final Composite parent) {
			init(name, labelText);
			setErrorMessage(Messages.TypeExport_InvalidPath);
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
			String directory = getTextControl().getText();
			directory = directory.trim();
			return isValidOutputDirectory(directory);
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
