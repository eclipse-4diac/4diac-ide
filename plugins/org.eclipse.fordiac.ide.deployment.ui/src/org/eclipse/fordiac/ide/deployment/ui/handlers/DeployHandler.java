/*******************************************************************************
 * Copyright (c) 2018, 2023 Johannes Kepler University
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - add validation for VarInOuts not leaving its resource
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.handlers;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.ui.Messages;
import org.eclipse.fordiac.ide.deployment.ui.views.Output;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class DeployHandler extends AbstractHandler {

	private static final String DEPLOYMENT_CONSOLE_ID = "org.eclipse.fordiac.ide.deployment.ui.views.Output"; //$NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final Collection<EObject> selected = getDeployableObjects(HandlerUtil.getCurrentSelection(event));
		final var errorMarkersFound = selected.stream().map(EObject::eResource).filter(Objects::nonNull)
				.map(org.eclipse.emf.ecore.resource.Resource::getURI).filter(Objects::nonNull)
				.map(uri -> ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true))))
				.anyMatch(file -> {
					try {
						return file.exists() && Arrays.stream(file.findMarkers(null, true, IResource.DEPTH_INFINITE))
								.anyMatch(marker -> marker.getAttribute(IMarker.SEVERITY, 0) == IMarker.SEVERITY_ERROR);
					} catch (final CoreException e) {
						return false; // should not happen since we are checking the resource exists
					}
				});

		final Shell shell = Display.getDefault().getActiveShell();
		final MessageDialog dialog = new MessageDialog(shell, Messages.ErrorAnnotation_DeploymentError, null,
				Messages.DeploymentHandler_ErrorsInAutomationSystem, MessageDialog.QUESTION,
				new String[] { IDialogConstants.ABORT_LABEL, Messages.DeploymentHandler_DeployLabel }, 0);
		int deploy = 1;
		if (errorMarkersFound) {
			deploy = dialog.open();
		}

		if (!selected.isEmpty() && deploy == 1) {
			clearDeploymentConsole();
			DeploymentCoordinator.performDeployment(selected.toArray(new EObject[selected.size()]));
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		boolean needToAdd = false;
		if (HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME) instanceof final ISelection selection) {
			needToAdd = !getDeployableObjects(selection).isEmpty();
		}
		setBaseEnabled(needToAdd);
	}

	private static Collection<EObject> getDeployableObjects(final ISelection selection) {
		final Set<EObject> retVal = new HashSet<>();
		if (selection instanceof final StructuredSelection structuredSelection) {
			for (final Object selectedObject : structuredSelection.toArray()) {
				if (selectedObject instanceof final EObject selectedEObject) {
					addEObject(selectedEObject, retVal);
				} else if (selectedObject instanceof final EditPart selectedEditPart
						&& selectedEditPart.getModel() instanceof final EObject editPartModelEObject) {
					addEObject(editPartModelEObject, retVal);
				} else if (SystemManager.isSystemFile(selectedObject)) {
					addEObject(SystemManager.INSTANCE.getSystem((IFile) selectedObject), retVal);
				}
			}
		}
		return retVal;
	}

	private static void addEObject(final EObject object, final Set<EObject> retVal) {
		if (object instanceof final AutomationSystem automationSystem) {
			for (final Device dev : automationSystem.getSystemConfiguration().getDevices()) {
				addDeviceContent(dev, retVal);
			}
		} else if (object instanceof final SystemConfiguration systemConfiguration) {
			for (final Device dev : systemConfiguration.getDevices()) {
				addDeviceContent(dev, retVal);
			}
		} else if (object instanceof final Device device) {
			addDeviceContent(device, retVal);
		} else if (object instanceof Resource) {
			retVal.add(object);
		}
	}

	private static void addDeviceContent(final Device dev, final Set<EObject> retVal) {
		retVal.add(dev);
		for (final Resource res : dev.getResource()) {
			retVal.add(res);
		}
	}

	private static void clearDeploymentConsole() {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		try {
			final IViewPart viewPart = page.showView(DEPLOYMENT_CONSOLE_ID);
			if (viewPart instanceof final Output output) {
				output.clearOutput();
			}
		} catch (final PartInitException e) {
			FordiacLogHelper.logInfo("Couldn't get the deployment console: " + e.getMessage()); //$NON-NLS-1$
		}

	}

}
