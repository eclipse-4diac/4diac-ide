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
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.preferences;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.editors.IDialogFieldListener;
import org.eclipse.fordiac.ide.ui.editors.SelectionButtonDialogField;
import org.eclipse.jface.dialogs.ControlEnableState;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Base for project property and preference pages
 *
 * adapted from Eclipse JDT
 */
public abstract class FordiacPropertyPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage, IWorkbenchPropertyPage {
	private ControlEnableState fBlockEnableState;
	private Link fChangeWorkspaceSettings;
	private SelectionButtonDialogField fUseProjectSettings;
	private Composite fParentComposite;
	protected IProject fProject;
	private Map<String, Object> fData; // page data

	public static final String DATA_NO_LINK = "PropertyAndPreferencePage.nolink"; //$NON-NLS-1$

	protected PreferenceStoreProvider provider;
	private final String qualifier;

	protected FordiacPropertyPreferencePage(final int style, final String qualifier) {
		super(style);
		this.qualifier = qualifier;
		fBlockEnableState = null;
		fProject = null;
		fData = null;
	}

	protected abstract String getPreferencePageID();

	protected abstract String getPropertyPageID();

	protected boolean supportsProjectSpecificOptions() {
		return getPropertyPageID() != null;
	}

	protected boolean offerLink() {
		return fData == null || !Boolean.TRUE.equals(fData.get(DATA_NO_LINK));
	}

	@Override
	protected Label createDescriptionLabel(final Composite parent) {
		// adapted from Eclipse JDT
		// org.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage
		fParentComposite = parent;
		if (isProjectPreferencePage()) {
			final Composite composite = new Composite(parent, SWT.NONE);
			composite.setFont(parent.getFont());
			final GridLayout layout = new GridLayout();
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			layout.numColumns = 2;
			composite.setLayout(layout);
			composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			final IDialogFieldListener listener = field -> {
				final boolean enabled = ((SelectionButtonDialogField) field).isSelected();
				enableProjectSpecificSettings(enabled);

				if (enabled && getData() != null) {
					applyData(getData());
				}
			};

			fUseProjectSettings = new SelectionButtonDialogField(SWT.CHECK);
			fUseProjectSettings.setDialogFieldListener(listener);
			fUseProjectSettings.setLabelText(FordiacMessages.PropertyAndPreferencePage_useprojectsettings_label);
			fUseProjectSettings.doFillIntoGrid(composite, 1);
			// inlined LayoutUtil.setHorizontalGrabbing
			final Object obj = fUseProjectSettings.getSelectionButton(null).getLayoutData();
			if (obj instanceof final GridData gd) {
				gd.grabExcessHorizontalSpace = true;
			}

			if (offerLink()) {
				fChangeWorkspaceSettings = createLink(composite,
						FordiacMessages.PropertyAndPreferencePage_useworkspacesettings_change);
				fChangeWorkspaceSettings.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
			} else {
				// inlined LayoutUtil.setHorizontalSpan with span = 2
				final Control control = fUseProjectSettings.getSelectionButton(null);
				final Object ld = control.getLayoutData();
				if (ld instanceof final GridData gd) {
					gd.horizontalSpan = 2;
				} else {
					final GridData gd = new GridData();
					gd.horizontalSpan = 2;
					control.setLayoutData(gd);
				}

			}

			final Label horizontalLine = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
			horizontalLine.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 2, 1));
			horizontalLine.setFont(composite.getFont());
		} else if (supportsProjectSpecificOptions() && offerLink()) {
			fChangeWorkspaceSettings = createLink(parent,
					FordiacMessages.PropertyAndPreferencePage_showprojectspecificsettings_label);
			fChangeWorkspaceSettings.setLayoutData(new GridData(SWT.END, SWT.CENTER, true, false));
		}

		return super.createDescriptionLabel(parent);
	}

	@Override
	protected Control createContents(final Composite parent) {
		final Control control = super.createContents(parent);
		if (isProjectPreferencePage()) {
			enableProjectSpecificSettings(provider.isProjectStoreActive());
		}
		return control;
	}

	private Link createLink(final Composite composite, final String text) {
		final Link link = new Link(composite, SWT.NONE);
		link.setFont(composite.getFont());
		link.setText("<A>" + text + "</A>"); //$NON-NLS-1$//$NON-NLS-2$
		link.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				doLinkActivated((Link) e.widget);
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				doLinkActivated((Link) e.widget);
			}
		});
		return link;
	}

	protected boolean useProjectSettings() {
		return isProjectPreferencePage() && fUseProjectSettings != null && fUseProjectSettings.isSelected();
	}

	protected boolean isProjectPreferencePage() {
		return fProject != null;
	}

	protected IProject getProject() {
		return fProject;
	}

	/**
	 * Handle link activation.
	 *
	 * @param link the link
	 */
	final void doLinkActivated(final Link link) {
		Map<String, Object> data = getData();
		if (data == null) {
			data = new HashMap<>();
		}
		data.put(DATA_NO_LINK, Boolean.TRUE);

		if (isProjectPreferencePage()) {
			openWorkspacePreferences(data);
		} else {
			final List<IProject> projects = new LinkedList<>();
			for (final IProject proj : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
				try {
					if (proj.hasNature("org.eclipse.fordiac.ide.systemmanagement.FordiacNature")) { //$NON-NLS-1$
						projects.add(proj);
					}
				} catch (final CoreException e) {
					// empty
				}
			}
			final ElementListSelectionDialog dialog = new ElementListSelectionDialog(getShell(),
					new WorkbenchLabelProvider());
			dialog.setTitle(FordiacMessages.PropertyAndPreferencePage_ChooseProjectDialog_title);
			dialog.setMessage(FordiacMessages.PropertyAndPreferencePage_ChooseProjectDialog_description);
			dialog.setElements(projects.toArray());
			dialog.setHelpAvailable(false);
			if (dialog.open() == Window.OK) {
				final IProject res = (IProject) dialog.getFirstResult();
				openProjectProperties(res, data);
			}
		}
	}

	protected final void openWorkspacePreferences(final Object data) {
		final String id = getPreferencePageID();
		PreferencesUtil.createPreferenceDialogOn(getShell(), id, new String[] { id }, data).open();
	}

	protected final void openProjectProperties(final IProject project, final Object data) {
		final String id = getPropertyPageID();
		if (id != null) {
			PreferencesUtil.createPropertyDialogOn(getShell(), project, id, new String[] { id }, data).open();
		}
	}

	protected void enableProjectSpecificSettings(final boolean useProjectSpecificSettings) {
		fUseProjectSettings.setSelection(useProjectSpecificSettings);
		provider.setProjectStoreActive(useProjectSpecificSettings);
		enablePreferenceContent(useProjectSpecificSettings);
		updateLinkVisibility();
	}

	private void updateLinkVisibility() {
		if (fChangeWorkspaceSettings == null || fChangeWorkspaceSettings.isDisposed()) {
			return;
		}

		if (isProjectPreferencePage()) {
			fChangeWorkspaceSettings.setEnabled(!useProjectSettings());
		}
	}

	protected void enablePreferenceContent(final boolean enable) {
		if (enable) {
			if (fBlockEnableState != null) {
				fBlockEnableState.restore();
				fBlockEnableState = null;
			}
		} else if (fBlockEnableState == null) {
			fBlockEnableState = ControlEnableState.disable(getFieldEditorParent());
		}
	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {
		if (useProjectSettings()) {
			enableProjectSpecificSettings(false);
		}
		super.performDefaults();
	}

	@Override
	public void init(final IWorkbench workbench) {
		provider = new PreferenceStoreProvider(qualifier, null);
	}

	@Override
	public IAdaptable getElement() {
		return fProject;
	}

	@Override
	public void setElement(final IAdaptable element) {
		final IResource resource = element.getAdapter(IResource.class);
		fProject = resource != null ? resource.getProject() : null;
		provider = new PreferenceStoreProvider(qualifier, fProject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void applyData(final Object data) {
		if (data instanceof Map) {
			fData = (Map<String, Object>) data;
		}
		if ((fChangeWorkspaceSettings != null) && !offerLink()) {
			fChangeWorkspaceSettings.dispose();
			fParentComposite.layout(true, true);
		}
	}

	protected Map<String, Object> getData() {
		return fData;
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		if (isProjectPreferencePage()) {
			return provider.getProjectStore();
		}
		return provider.getInstanceStore();
	}
}
