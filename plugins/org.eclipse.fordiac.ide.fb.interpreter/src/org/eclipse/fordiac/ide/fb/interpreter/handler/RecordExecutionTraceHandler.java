/*******************************************************************************
 * Copyright (c) 2023, 2025 Johannes Kepler University Linz
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
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.Messages;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.ui.SelectAdapterEventDialog;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
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

		// user should have selected an event pin of an FB
		var selectedPin = getSelectedPin(selection);
		if (selectedPin == null) {
			openSelectionErrorDialog(event);
			return Status.CANCEL_STATUS;
		}

		// identify the FB network associated with this pin
		final FBNetwork network;
		if (selectedPin.getBlockFBNetworkElement() != null) {
			network = selectedPin.getBlockFBNetworkElement().getFbNetwork();
		} else if (selectedPin.getInterfaceList().eContainer() instanceof final CompositeFBType fbt) {
			network = fbt.getFBNetwork();
		} else {
			network = HandlerUtil.getActiveEditor(event).getAdapter(FBNetwork.class);
		}

		if (selectedPin instanceof final AdapterDeclaration aDecl) {
			selectedPin = handleAdapterSelection(aDecl, event);
		}
		if (!(selectedPin instanceof final Event triggerEvent)) {
			openSelectionErrorDialog(event);
			return Status.CANCEL_STATUS;
		}

		// record the trace
		final EventManager manager = EventManagerFactory.createFrom(triggerEvent, network);
		manager.processNetwork();

		// serialize event manager
		final ResourceSet reset = new ResourceSetImpl();

		final EObject rootContainer = EcoreUtil.getRootContainer(network);
		final TypeLibrary typelib = ((LibraryElement) rootContainer).getTypeLibrary();

		if (typelib != null) {
			final IProject project = typelib.getProject();
			typelib.getAllTypes().forEach(type -> addResourceForFile(type, type.getFile(), reset));
			addResourceForEventTypeLib(reset);
			addResourceForDataTypeLib(typelib.getDataTypeLibrary(), reset);

			final IFolder folder = project.getFolder("network_traces"); //$NON-NLS-1$
			if (!folder.exists()) {
				try {
					folder.create(false, false, null);
				} catch (final CoreException e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			}

			final String systemName = switch (rootContainer) {
			case final AutomationSystem system -> system.getName();
			case final FBType type -> type.getName();
			default -> ""; //$NON-NLS-1$
			};

			final IFile file = folder.getFile(systemName + "." + triggerEvent.getQualifiedName() + ".opsem"); //$NON-NLS-1$//$NON-NLS-2$
			final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			final Resource res = reset.createResource(uri);
			res.getContents().add(manager);

			try {
				res.save(Collections.emptyMap());
				openEditorForGeneratedFile(event, file);
			} catch (final IOException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
		return Status.OK_STATUS;
	}

	private static void openSelectionErrorDialog(final ExecutionEvent event) {
		MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
				Messages.RecordExecutionTraceHandler_Incorrect_Selection,
				Messages.RecordExecutionTraceHandler_Select_FB_event);
	}

	private static void addResourceForEventTypeLib(final ResourceSet reset) {
		final var eventtypes = EventTypeLibrary.getInstance().getEventTypes();
		eventtypes.forEach(eventtype -> {
			final URI uri = URI.createPlatformResourceURI("EventType" + eventtype.toString(), true); //$NON-NLS-1$
			final Resource res = reset.createResource(uri);
			res.getContents().add(eventtype);
		});
	}

	private static void addResourceForDataTypeLib(final DataTypeLibrary dataTypeLibrary, final ResourceSet reset) {
		dataTypeLibrary.getDataTypes().forEach(datatype -> {
			final URI uri = URI.createPlatformResourceURI("DataType" + datatype.toString(), true); //$NON-NLS-1$
			final Resource res = reset.createResource(uri);
			res.getContents().add(datatype);
		});
	}

	private static void addResourceForFile(final TypeEntry type, final IFile file, final ResourceSet reset) {
		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		final Resource res = reset.createResource(uri);
		res.getContents().add(type.getType());
	}

	private static IInterfaceElement handleAdapterSelection(final AdapterDeclaration adapter,
			final ExecutionEvent event) {
		final SelectAdapterEventDialog dialog = new SelectAdapterEventDialog(HandlerUtil.getActiveShell(event),
				adapter);
		final int returnCode = dialog.open();
		if (returnCode != -1) {
			return adapter.getAdapterFB().getInterface().getInterfaceElement(List.of(dialog.getSelectedEvent()));
		}
		return null;
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

	private static IInterfaceElement getSelectedPin(final IStructuredSelection structuredSelection) {
		Object selected = structuredSelection.getFirstElement();
		if (selected instanceof final EditPart editPart) {
			selected = editPart.getModel();
		}
		if (selected instanceof final IInterfaceElement pin) {
			return pin;
		}
		return null;
	}

}
