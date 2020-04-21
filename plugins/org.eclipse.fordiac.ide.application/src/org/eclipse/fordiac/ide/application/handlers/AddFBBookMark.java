/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.ApplicationPlugin;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
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
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FBNetworkElement element = getSelectedFBElement(event);
		if (null != element) {
			String description = getDescription(element, event);
			if (null != description) {
				Map<String, String> attrs = new HashMap<>();
				attrs.put(IMarker.MESSAGE, description);
				attrs.put(IMarker.LOCATION, getLocation(element));
				CreateMarkersOperation op = new CreateMarkersOperation(IMarker.BOOKMARK, attrs, getFile(element),
						Messages.AddFBBookMark_AddBookmark);
				try {
					PlatformUI.getWorkbench().getOperationSupport().getOperationHistory().execute(op, null,
							WorkspaceUndoUtil.getUIInfoAdapter(HandlerUtil.getActiveShell(event)));
				} catch (ExecutionException e) {
					ApplicationPlugin.getDefault().logError("Could not create bookmark", e); //$NON-NLS-1$
				}
				return Status.OK_STATUS;
			}
		}
		return Status.CANCEL_STATUS;
	}

	private static String getDescription(FBNetworkElement element, ExecutionEvent event) {
		InputDialog descriptiondialog = new InputDialog(HandlerUtil.getActiveShell(event),
				MessageFormat.format(Messages.AddFBBookMark_AddBookMarkTitle, element.getName()),
				Messages.AddFBBookMark_EnterBookmarkName, element.getName(), null) {

			@Override
			protected void createButtonsForButtonBar(Composite parent) {
				super.createButtonsForButtonBar(parent);
				Button okButton = getOkButton();
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

	private static String getLocation(FBNetworkElement element) {
		EObject container = element.eContainer().eContainer();
		if (container instanceof FBType) {
			return element.getName();
		}
		return createHierarchicalName(element);
	}

	private static IResource getFile(FBNetworkElement element) {
		EObject container = element.eContainer().eContainer();
		if (container instanceof FBType) {
			return ((FBType) container).getPaletteEntry().getFile();
		}
		// if we are here we are in a app or subapp
		return element.getFbNetwork().getAutomationSystem().getSystemFile();
	}

	private static String createHierarchicalName(final FBNetworkElement element) {
		StringBuilder builder = new StringBuilder(element.getName());

		EObject runner = element.getFbNetwork().eContainer();
		while (runner instanceof SubApp) {
			SubApp parent = (SubApp) runner;
			builder.insert(0, '.');
			builder.insert(0, parent.getName());
			runner = parent.getFbNetwork().eContainer();
		}
		if (runner instanceof Application) {
			builder.insert(0, '.');
			builder.insert(0, ((Application) runner).getName());
		}

		return builder.toString();
	}

	private static FBNetworkElement getSelectedFBElement(ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
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
