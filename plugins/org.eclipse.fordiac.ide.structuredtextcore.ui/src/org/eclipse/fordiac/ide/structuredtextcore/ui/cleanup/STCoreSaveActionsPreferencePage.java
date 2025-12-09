/*******************************************************************************
 * Copyright (c) 2022, 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.cleanup;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.IPreferencePageContainer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.preferences.PropertyAndPreferencePage;

import com.google.inject.Inject;
import com.google.inject.name.Named;

@SuppressWarnings("restriction")
public class STCoreSaveActionsPreferencePage extends PropertyAndPreferencePage {

	@Inject
	private IPreferenceStoreAccess preferenceStoreAccess;

	@Inject
	private STCoreSaveActionsConfigurationBlock saveActionsConfigurationBlock;

	@Inject
	@Named(Constants.LANGUAGE_NAME)
	private String languageName;

	@Override
	public void createControl(final Composite parent) {
		final IWorkbenchPreferenceContainer container = (IWorkbenchPreferenceContainer) getContainer();
		final IPreferenceStore preferenceStore = preferenceStoreAccess.getWritablePreferenceStore(getProject());
		saveActionsConfigurationBlock.setProject(getProject());
		saveActionsConfigurationBlock.setPreferenceStore(preferenceStore);
		saveActionsConfigurationBlock.setWorkbenchPreferenceContainer(container);
		saveActionsConfigurationBlock.setStatusChangeListener(getNewStatusChangedListener());
		super.createControl(parent);
	}

	@Override
	protected Control createPreferenceContent(final Composite composite,
			final IPreferencePageContainer preferencePageContainer) {
		return saveActionsConfigurationBlock.createContents(composite);
	}

	@Override
	protected boolean hasProjectSpecificOptions(final IProject project) {
		return saveActionsConfigurationBlock.hasProjectSpecificOptions(project);
	}

	@Override
	protected String getPreferencePageID() {
		return languageName + ".saveActions.preferencePage"; //$NON-NLS-1$
	}

	@Override
	protected String getPropertyPageID() {
		return languageName + ".saveActions.propertyPage"; //$NON-NLS-1$
	}

	@Override
	public void dispose() {
		if (saveActionsConfigurationBlock != null) {
			saveActionsConfigurationBlock.dispose();
		}
		super.dispose();
	}

	@Override
	protected void enableProjectSpecificSettings(final boolean useProjectSpecificSettings) {
		super.enableProjectSpecificSettings(useProjectSpecificSettings);
		if (saveActionsConfigurationBlock != null) {
			saveActionsConfigurationBlock.useProjectSpecificSettings(useProjectSpecificSettings);
		}
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		if (saveActionsConfigurationBlock != null) {
			saveActionsConfigurationBlock.performDefaults();
		}
	}

	@Override
	public boolean performOk() {
		if (saveActionsConfigurationBlock != null && !saveActionsConfigurationBlock.performOk()) {
			return false;
		}
		return super.performOk();
	}

	@Override
	public void performApply() {
		if (saveActionsConfigurationBlock != null) {
			saveActionsConfigurationBlock.performApply();
		}
	}

	@Override
	public void setElement(final IAdaptable element) {
		super.setElement(element);
		setDescription(null); // no description for property page
	}
}
