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

package org.eclipse.fordiac.ide.export.properties;

import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.fordiac.ide.export.Messages;
import org.eclipse.fordiac.ide.export.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.export.utils.ExportFilterUtil;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class TypeExportPropertyPage extends PropertyPage {

	private DirectoryFieldEditor directoryEditor;
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
		GridDataFactory.fillDefaults().applyTo(composite);
		createEnableCheckbox(composite);
		this.settingsContainer = new Group(composite, SWT.NONE);
		settingsContainer.setText(Messages.TypeExport_Settings);
		GridLayoutFactory.fillDefaults().margins(10, 10).applyTo(settingsContainer);

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
		directoryEditor = new DirectoryFieldEditor(PreferenceConstants.OUTPUT_FOLDER, Messages.TypeExport_OutputFolder,
				directoryEditorContainer);
		directoryEditor.setPreferenceStore(getPreferenceStore());
		directoryEditor.setPage(this);
		directoryEditor.getTextControl(directoryEditorContainer).addModifyListener(e -> {
			directoryEditor.getTextControl(directoryEditorContainer).requestLayout();
		});
	}

	private String getOutputFolder() {
		if (getProject().getFolder(OUTPUT_FOLDER_NAME).exists()) {
			return getProject().getFolder(OUTPUT_FOLDER_NAME).getRawLocation().toOSString();
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
		exporterEditor.store();

		if (!directoryEditor.isValid()) {
			directoryEditor.showErrorMessage();
			FordiacLogHelper.logError(directoryEditor.getErrorMessage());
			return false;
		}
		directoryEditor.store();
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
		directoryEditor.load();
		exporterEditor.load();
		enableSettings(checkboxEditor.getBooleanValue());
	}

	private void enableSettings(final boolean enable) {
		settingsContainer.setEnabled(enable);
		directoryEditor.setEnabled(enable, directoryEditorContainer);
		exporterEditor.setEnabled(enable, exporterEditorContainer);
	}

}