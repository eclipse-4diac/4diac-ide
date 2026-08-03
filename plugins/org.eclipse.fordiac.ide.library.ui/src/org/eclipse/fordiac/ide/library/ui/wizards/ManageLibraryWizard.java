/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.fordiac.ide.library.LibraryChange;
import org.eclipse.fordiac.ide.library.LibraryResolver;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class ManageLibraryWizard extends Wizard {
	private final IProject project;
	private final LibraryPlanningPage planningPage;
	private final LibraryChangePreviewPage previewPage;
	private List<LibraryChange> changesIncludingTransitive = List.of();

	public static int openDialog(final IProject project, final Shell shell) {
		if (project != null) {
			final ManageLibraryWizard wizard = new ManageLibraryWizard(project);
			wizard.setWindowTitle("Manage Linked Libraries"); //$NON-NLS-1$
			final WizardDialog dialog = new WizardDialog(shell, wizard);
			dialog.setBlockOnOpen(false);
			dialog.setModal(false);
			dialog.create();
			return dialog.open();
		}
		return Window.CANCEL;
	}

	public ManageLibraryWizard(final IProject project) {
		this.project = project;
		this.planningPage = new LibraryPlanningPage(Messages.ManageLibraryWizard_PlannigPage_Titel, project);
		this.previewPage = new LibraryChangePreviewPage(Messages.ManageLibraryWizard_PreviewPage_Titel);

	}

	@Override
	public boolean performFinish() {
		final IStatus status = planningPage.getResolveResult().status();
		if (!status.isOK()) {
			return false;
		}

		final WorkspaceModifyOperation libraryChangeOperation = new WorkspaceModifyOperation() {
			@Override
			protected void execute(final IProgressMonitor monitor) throws CoreException {
				LibraryChange.performChanges(changesIncludingTransitive, project, monitor);
			}
		};

		try {
			getContainer().run(false, true, libraryChangeOperation);
		} catch (final InvocationTargetException e) {
			FordiacLogHelper.logError("Major error while applying library changes", e); //$NON-NLS-1$
			return false;
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			return false;
		}

		return true;
	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(planningPage);
		addPage(previewPage);
	}

	@Override
	public IWizardPage getNextPage(final IWizardPage page) {
		if (page == planningPage) {
			changesIncludingTransitive = getChangesIncludingTransitive();
			previewPage.setInput(changesIncludingTransitive);
		}
		return super.getNextPage(page);
	}

	private List<LibraryChange> getChangesIncludingTransitive() {
		final Stream<LibraryChange> importSet = LibraryResolver
				.deriveImportSet(planningPage.getResolveResult(), planningPage.getPlannedLinkedLibraries())
				.map(desc -> LibraryChange.createAdd(desc.symbolicName(), desc.version().toString()));
		final Stream<LibraryChange> removeSet = LibraryResolver
				.deriveRemoveSet(planningPage.getResolveResult(), planningPage.getPlannedLinkedLibraries())
				.map(f -> LibraryChange.createRemove(f.symbolicName(), f.version().toString()));
		return Stream.concat(planningPage.getChanges().stream(), Stream.concat(importSet, removeSet)).toList();
	}

}
