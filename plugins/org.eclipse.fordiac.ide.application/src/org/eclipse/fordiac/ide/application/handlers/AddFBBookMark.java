/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *               2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Jobst - refactor marker handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.text.MessageFormat;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.undo.CreateMarkersOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;

public class AddFBBookMark extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final FBNetworkElement element = getSelectedFBElement(event);
		if (null != element) {
			final String description = getDescription(element, event);
			if (null != description) {
				final CreateMarkersOperation op = new CreateMarkersOperation(IMarker.BOOKMARK,
						ErrorMarkerBuilder.createErrorMarkerBuilder(description).setSeverity(IMarker.SEVERITY_INFO)
						.setPriority(IMarker.PRIORITY_NORMAL).setTarget(element).getAttributes(),
						getFile(element), Messages.AddFBBookMark_AddBookmark);
				try {
					PlatformUI.getWorkbench().getOperationSupport().getOperationHistory().execute(op, null,
							WorkspaceUndoUtil.getUIInfoAdapter(HandlerUtil.getActiveShell(event)));
				} catch (final ExecutionException e) {
					FordiacLogHelper.logError("Could not create bookmark", e); //$NON-NLS-1$
				}
				return Status.OK_STATUS;
			}
		}
		return Status.CANCEL_STATUS;
	}

	private static String getDescription(final FBNetworkElement element, final ExecutionEvent event) {
		final InputDialog descriptiondialog = new InputDialog(HandlerUtil.getActiveShell(event),
				MessageFormat.format(Messages.AddFBBookMark_AddBookMarkTitle, element.getName()),
				Messages.AddFBBookMark_EnterBookmarkName, element.getName(), null) {

			@Override
			protected void createButtonsForButtonBar(final Composite parent) {
				super.createButtonsForButtonBar(parent);
				final Button okButton = getOkButton();
				okButton.setText(Messages.AddFBBookMark_AddBookmark);
			}
		};

		if (descriptiondialog.open() != Window.CANCEL) {
			String description = descriptiondialog.getValue();
			description = description.trim();
			if (!description.isEmpty()) {
				return description;
			}
		}

		return null;
	}

	private static IResource getFile(final FBNetworkElement element) {
		final EObject container = element.eContainer().eContainer();
		if (container instanceof FBType) {
			return ((FBType) container).getTypeEntry().getFile();
		}
		// if we are here we are in a app or subapp
		return element.getFbNetwork().getAutomationSystem().getSystemFile();
	}

	private static FBNetworkElement getSelectedFBElement(final ExecutionEvent event) {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof StructuredSelection) {
			Object selObj = ((StructuredSelection) selection).getFirstElement();
			if (selObj instanceof EditPart) {
				selObj = ((EditPart) selObj).getModel();
			}
			if (selObj instanceof FBNetworkElement) {
				return (FBNetworkElement) selObj;
			}
		}
		return null;
	}

}
