/*******************************************************************************
 * Copyright (c) 2014, 2021 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - updated bootfile exporting for new project layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.bootfile.wizard;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.deployment.bootfile.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.ui.widget.DirectoryChooserControl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.dialogs.WizardExportResourcesPage;

public class CreateBootFilesWizardPage extends WizardExportResourcesPage {

	private static final String SETTING_CURRENT_DIR = "currentDir"; //$NON-NLS-1$
	private final IStructuredSelection selection;
	private DirectoryChooserControl dcc;
	private DownloadSelectionTree systemTree;

	public CreateBootFilesWizardPage(final IStructuredSelection selection) {
		this(Messages.FordiacCreateBootfilesWizard_PageName, selection);
	}

	protected CreateBootFilesWizardPage(final String pageName, final IStructuredSelection selection) {
		super(pageName, createResourceSelection(selection));
		this.selection = selection;
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setDescription(Messages.FordiacCreateBootfilesWizard_PageDESCRIPTION);
		setTitle(Messages.FordiacCreateBootfilesWizard_PageTITLE);
		checkSelectedElements();
		updatePageCompletion();
	}

	public Object[] getSelectedElements() {
		return systemTree.getCheckedElements();
	}

	@Override
	protected void createOptionsGroup(final Composite parent) {
		// don't show the option group for this wizard page
	}

	@Override
	protected void createDestinationGroup(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setFont(parent.getFont());

		createSystemsContainer(composite);
		createDestinationDirectorySelection(composite);
	}

	private void createSystemsContainer(final Composite composite) {
		systemTree = new DownloadSelectionTree(composite,
				SWT.FULL_SELECTION | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		systemTree.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		systemTree.addCheckStateListener(event -> updatePageCompletion());
	}

	private void createDestinationDirectorySelection(final Composite composite) {

		final GridData stretch = new GridData();
		stretch.grabExcessHorizontalSpace = true;
		stretch.horizontalAlignment = SWT.FILL;

		dcc = new DirectoryChooserControl(composite, SWT.NONE, Messages.CreateBootFilesWizardPage_Destination);
		dcc.addChooserValueChangedListener(newDirectory -> updatePageCompletion());
		dcc.setLayoutData(stretch);
	}

	@Override
	protected void updateWidgetEnablements() {
		// will be called when the resource group changes selection
		checkSelectedResources();
		super.updateWidgetEnablements();
	}

	private void checkSelectedResources() {
		systemTree.setInput(getSelectedSystems());
	}

	private List<AutomationSystem> getSelectedSystems() {
		return ((List<IFile>) getSelectedResources()).stream().map(TypeLibraryManager.INSTANCE::getTypeEntryForFile)
				.filter(SystemEntry.class::isInstance).map(SystemEntry.class::cast).map(SystemEntry::getSystem)
				.filter(Objects::nonNull).toList();
	}

	@Override
	protected boolean validateSourceGroup() {
		if (getSelectedSystems().isEmpty()) {
			setErrorMessage(Messages.CreateBootFilesWizardPage_NoSystemSelected);
			return false;
		}
		return super.validateSourceGroup();
	}

	@Override
	protected boolean validateDestinationGroup() {
		if (dcc.getValue() == null || "".equals(dcc.getValue())) { //$NON-NLS-1$
			setErrorMessage(Messages.CreateBootFilesWizardPage_DestinationNotSelected);
			return false;
		}

		if (0 == systemTree.getCheckedElements().length) {
			setErrorMessage(Messages.CreateBootFilesWizardPage_NothingSelectedForBootFileGeneration);
			return false;
		}
		return super.validateDestinationGroup();
	}

	@Override
	protected void restoreWidgetValues() {
		super.restoreWidgetValues();
		// Loads cached directory, if available.
		if (getDialogSettings() != null) {
			final String cachedDir = getDialogSettings().get(SETTING_CURRENT_DIR);
			if (cachedDir != null) {
				setDirectory(cachedDir);
			}
		}
	}

	@Override
	protected void internalSaveWidgetValues() {
		super.internalSaveWidgetValues();
		getDialogSettings().put(SETTING_CURRENT_DIR, getDirectory());
	}

	@Override // this override is needed to make it public for access by the wizard
	public void saveWidgetValues() {
		super.saveWidgetValues();
	}

	/**
	 * Sets the directory.
	 *
	 * @param dir the new directory
	 */
	public void setDirectory(final String dir) {
		dcc.setValue(dir);
	}

	public String getDirectory() {
		return dcc.getValue();
	}

	private void checkSelectedElements() {
		// first expand all selected elements
		for (final Object obj : selection.toArray()) {
			if (obj instanceof AutomationSystem) {
				expandSystem(obj);
			} else if (obj instanceof final Device dev) {
				expandDevice(dev);
			} else if (obj instanceof final Resource res) {
				expandResource(res);
			}
		}

		// second select them and then check them.
		systemTree.setSelection(selection);
		systemTree.setCheckedElements(selection.toArray());
	}

	private void expandResource(final Resource obj) {
		expandDevice(obj.getDevice());
	}

	private void expandDevice(final Device obj) {
		expandSystem(obj.getAutomationSystem());
		systemTree.setExpandedState(obj, true);
	}

	private void expandSystem(final Object obj) {
		systemTree.setExpandedState(obj, true);
	}

	@Override
	public void handleEvent(final Event event) {
		// currently nothing to be done here
	}

	private static IStructuredSelection createResourceSelection(final IStructuredSelection selection) {
		final Set<IFile> selectedASFiles = new HashSet<>();
		selection.toList().forEach(obj -> {
			if (obj instanceof final IFile file
					&& TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING.equalsIgnoreCase(file.getFileExtension())) {
				selectedASFiles.add(file);
			}
			if (obj instanceof final EObject eo) {
				final EObject root = EcoreUtil.getRootContainer(eo);
				if (root instanceof final LibraryElement libEl) {
					selectedASFiles.add(libEl.getTypeEntry().getFile());
				}
			}
		});

		return new StructuredSelection(selectedASFiles.toArray());
	}

}
