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
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.Messages;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

public class RecordExecutionTraceHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);

		// user should have selected an input event pin of an FB
		final Event triggerEvent = getSelectedEvent(selection);
		if (triggerEvent == null) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					Messages.RecordExecutionTraceHandler_Incorrect_Selection,
					Messages.RecordExecutionTraceHandler_Select_FB_input_event);
			return Status.CANCEL_STATUS;
		}

		// record the trace
		final EventManager manager = EventManagerFactory.createFrom(triggerEvent,
				EcoreUtil.copy(triggerEvent.getFBNetworkElement().getFbNetwork()));
		manager.processNetwork();

		// serialize event manager
		final ResourceSet reset = new ResourceSetImpl();
		final AutomationSystem system = triggerEvent.getFBNetworkElement().getFbNetwork().getAutomationSystem();
		final IProject project = system.getTypeEntry().getFile().getProject();
		final IFolder folder = project.getFolder("network_traces"); //$NON-NLS-1$
		if (!folder.exists()) {
			try {
				folder.create(false, false, null);
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
		final IFile file = folder.getFile(system.getName() + "." + triggerEvent.getQualifiedName() + ".opsem"); //$NON-NLS-1$//$NON-NLS-2$
		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		final Resource res = reset.createResource(uri);
		res.getContents().add(manager);
		try {
			res.save(Collections.emptyMap());

			openEditorForGeneratedFile(event, file);
		} catch (final IOException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

		return Status.OK_STATUS;
	}

	protected static void openEditorForGeneratedFile(final ExecutionEvent event, final IFile file) {
		final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
		final IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		try {
			page.openEditor(new FileEditorInput(file), desc.getId());
		} catch (final PartInitException e) {

			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	private static Event getSelectedEvent(final IStructuredSelection structuredSelection) {
		Object selected = structuredSelection.getFirstElement();
		if (selected instanceof EditPart) {
			selected = ((EditPart) structuredSelection.getFirstElement()).getModel();
		}
		if (selected instanceof final Event selectedEvent) {
			return selectedEvent;
		}
		return null;
	}

}
