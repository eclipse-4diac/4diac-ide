/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.handler;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.EventManagerUtils;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class RecordExecutionTraceHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);

		final Event triggerEvent = getSelectedEvent(selection);
		if (triggerEvent == null) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event), "Incorrect selection",
					"Please select an input event of an FB instance");
			return IStatus.ERROR;
		}
		final EventManager manager = EventManagerFactory.createFrom(triggerEvent,
				triggerEvent.getFBNetworkElement().getFbNetwork());
		EventManagerUtils.processNetwork(manager);
		final Object obj = manager.getTransactions().get(0).getInputEventOccurrence().getEvent().eResource();
		// serialize event manager
		final ResourceSet reset = new ResourceSetImpl();
		final AutomationSystem system = triggerEvent.getFBNetworkElement().getFbNetwork().getAutomationSystem();
		final IProject project = system.getTypeEntry().getFile().getProject();
		final IFolder folder = project.getFolder("network_traces");
		if (!folder.exists()) {
			try {
				folder.create(false, false, null);
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage());
			}
		}
		final IFile file = folder.getFile(
				triggerEvent.getFBNetworkElement().getQualifiedName() + "." + triggerEvent.getName() + ".opsem");  //$NON-NLS-1$//$NON-NLS-2$
		if (!file.exists()) {
			// check for the next free filename
		}
		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		final Resource res = reset.createResource(uri);
		res.getContents().add(manager);
		try {
			res.save(Collections.emptyMap());
		} catch (final IOException e) {
			FordiacLogHelper.logError(e.getMessage());
		}

		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof StructuredSelection) {
			final StructuredSelection structuredSelection = (StructuredSelection) selection;
			// setBaseEnabled((structuredSelection.size() > 1) &&
			// isValidSelection(getSelectedEvent(structuredSelection)));
		}
	}

	private static Event getSelectedEvent(final IStructuredSelection structuredSelection) {
		Object selected = structuredSelection.getFirstElement();
		if (selected instanceof EditPart) {
			selected = ((EditPart) structuredSelection.getFirstElement()).getModel();
		}
		if (selected instanceof Event) {
			return (Event) selected;
		}
		return null;
	}

	private static boolean isValidSelection(final Event event) {
		return event != null;
	}
}
