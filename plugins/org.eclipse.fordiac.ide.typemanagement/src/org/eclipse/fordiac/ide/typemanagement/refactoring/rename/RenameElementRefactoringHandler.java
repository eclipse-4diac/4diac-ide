/*******************************************************************************
 * Copyright (c) 2024, 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.typemanagement.refactoring.RefactoringUtil;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * A handler for triggering a rename refactoring on a structured view or editor,
 * such as a property sheet.
 */
public class RenameElementRefactoringHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		try {
			final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
			if (selection.size() == 1) {
				final Object firstElement = selection.getFirstElement();
				final IProject selectedProject = getProject(firstElement);
				final URI elementURI = getElementURI(firstElement);
				final String elementName = getElementName(firstElement);
				if (elementURI != null && !elementName.isEmpty()) {
					RefactoringUtil.saveAllAndBuild(selectedProject);
					final RenameRefactoring refactoring = new RenameRefactoring(
							new RenameElementRefactoringProcessor(elementURI, elementName));
					final RenameElementRefactoringWizard wizard = new RenameElementRefactoringWizard(refactoring);
					final RefactoringWizardOpenOperation openOperation = new RefactoringWizardOpenOperation(wizard);
					openOperation.run(HandlerUtil.getActiveShell(event), refactoring.getName());
				}
			}
		} catch (final OperationCanceledException e) {
			// ignore
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (final Exception e) {
			FordiacLogHelper.logError("Error during refactoring", e); //$NON-NLS-1$
		}
		return null;
	}

	private static IProject getProject(final Object element) {
		if (element instanceof final EditPart editPart) {
			return getProject(editPart.getModel());
		}
		if (element instanceof final IResource resource) {
			return resource.getProject();
		}
		if (element instanceof final EObject eObject) {
			return getProject(eObject);
		}
		return null;
	}

	private static IProject getProject(final EObject eObject) {
		final EObject root = EcoreUtil.getRootContainer(eObject);
		if (root instanceof final LibraryElement libraryElement && libraryElement.getTypeEntry() != null
				&& libraryElement.getTypeEntry().getFile() != null) {
			return libraryElement.getTypeEntry().getFile().getProject();
		}
		return null;
	}

	protected URI getElementURI(final Object element) {
		if (element instanceof final EditPart editPart) {
			return getElementURI(editPart.getModel());
		}
		if (element instanceof final IInterfaceElement interfaceElement) {
			final FBNetworkElement fbNetworkElement = interfaceElement.getBlockFBNetworkElement();
			if (fbNetworkElement != null && fbNetworkElement.getTypeEntry() != null) {
				return getElementURI(interfaceElement.findInTypeInterface());
			}
		}
		if (element instanceof final EObject eObject) {
			return EcoreUtil.getURI(eObject);
		}
		return null;
	}

	protected String getElementName(final Object element) {
		if (element instanceof final EditPart editPart) {
			return getElementName(editPart.getModel());
		}
		if (element instanceof final INamedElement namedElement) {
			return namedElement.getName();
		}
		return ""; //$NON-NLS-1$
	}
}
