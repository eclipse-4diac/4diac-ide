/*******************************************************************************
 * Copyright (c) 2008, 2021 Profactor GmbH, fortiss GmbH,
 * 							 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked deployment to detect if monitoring was enabled
 *               - added message dialog informing about error responses from
 *                 devices
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.DeviceDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.ResourceDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.ResourceDeploymentData.ParameterData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor.IDeviceManagementInteractorCloser;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DownloadRunnable implements IRunnableWithProgress, IDeploymentListener {

	private final List<DeviceDeploymentData> deploymentData;
	private final IDeviceManagementCommunicationHandler overrideDevMgmCommHandler;
	private final IDeploymentListener outputView;
	private final String profile;
	private IProgressMonitor curMonitor;
	private IStatus result = Status.OK_STATUS;

	/**
	 * flag indicating if an existing resource should automatically be overriden or
	 * if the user should be asked
	 */
	private boolean overrideAll = false;

	/**
	 * DownloadRunnable constructor.
	 *
	 * @param deploymentData            the collection of elements to deploy
	 * @param overrideDevMgmCommHandler if not null this device management
	 *                                  communication should be used instead the one
	 *                                  derived from the device profile.
	 * @param outputView                the view showing the download information
	 * @param profile                   if not null the profile to be used instead
	 *                                  of the device's profile
	 */
	public DownloadRunnable(final List<DeviceDeploymentData> deploymentData,
			final IDeviceManagementCommunicationHandler overrideDevMgmCommHandler, final IDeploymentListener outputView,
			final String profile) {
		this.deploymentData = deploymentData;
		this.overrideDevMgmCommHandler = overrideDevMgmCommHandler;
		this.outputView = outputView;
		this.profile = profile;
	}

	/**
	 * Runs the check.
	 *
	 * @param monitor the progress monitor
	 *
	 * @throws InvocationTargetException the invocation target exception
	 * @throws InterruptedException      the interrupted exception
	 */
	@Override
	public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		this.curMonitor = monitor;
		monitor.beginTask(Messages.DeploymentCoordinator_LABEL_PerformingDownload, calculateWorkAmount());

		for (final DeviceDeploymentData devData : deploymentData) {
			if (monitor.isCanceled()) {
				throw new InterruptedException(Messages.DeploymentCoordinator_LABEL_DownloadAborted);
			}
			deployDevice(devData);
		}
		monitor.done();
	}

	private void deployDevice(final DeviceDeploymentData devData) throws InterruptedException {
		final IDeviceManagementInteractor executor = DeviceManagementInteractorFactory.INSTANCE
				.getDeviceManagementInteractor(devData.getDevice(), overrideDevMgmCommHandler, profile);
		if (executor != null) {
			addDeploymentListener(executor);
			try (IDeviceManagementInteractorCloser closer = executor::disconnect) {
				executor.connect();
				deployResources(devData, executor);
				deployDeviceData(devData, executor);
			} catch (final DeploymentException e) {
				handleDeploymentException(devData.getDevice(), e);
			} finally {
				removeDeploymentListener(executor);
			}

		} else {
			DeploymentCoordinator.printUnsupportedDeviceProfileMessageBox(devData.getDevice(), null);
		}
	}

	private void deployResources(final DeviceDeploymentData devData, final IDeviceManagementInteractor executor)
			throws InterruptedException, DeploymentException {
		final Set<String> resources = executor.queryResources().stream()
				.map(org.eclipse.fordiac.ide.deployment.devResponse.Resource::getName).collect(Collectors.toSet());

		for (final ResourceDeploymentData resData : devData.getResData()) {
			if (curMonitor.isCanceled()) {
				throw new InterruptedException(Messages.DeploymentCoordinator_LABEL_DownloadAborted);
			}
			if (checkResource(resData.getRes(), resources, executor)) {
				// the resource is ready for deployment
				deployResource(resData, executor);
			}
		}
	}

	/**
	 * Check if the resource exists already in the device and if yes ask the user
	 * how to behave (i.e., abort, override, or override all).
	 *
	 * @param res           the resource that should be deployed
	 * @param resourceNames the names of all resources currently in the device
	 * @param executor      the deployment executor
	 * @return true if the resource can and should be deployed.
	 * @throws DeploymentException
	 * @throws InterruptedException
	 */
	private boolean checkResource(final Resource res, final Set<String> resourceNames,
			final IDeviceManagementInteractor executor) throws DeploymentException, InterruptedException {
		if (resourceNames.contains(res.getName())) {
			// the resource is in the device
			if (overrideAll) {
				executor.deleteResource(res.getName());
			} else if (askOverrideForResource(res)) {
				executor.deleteResource(res.getName());
			} else {
				// the user has canceled to override this resource
				return false;
			}
		}

		return true;
	}

	private void deployDeviceData(final DeviceDeploymentData devData, final IDeviceManagementInteractor executor)
			throws DeploymentException {
		if (!devData.getSelectedDevParams().isEmpty()) {
			final Device device = devData.getDevice();
			for (final VarDeclaration devVar : devData.getSelectedDevParams()) {
				final String value = DeploymentHelper.getVariableValue(devVar);
				if (null != value) {
					executor.writeDeviceParameter(device, devVar.getName(), value);
				}
				curMonitor.worked(1);
			}
			// we have device parameters send start to the device so that
			executor.startDevice(device);
		}
	}

	private void addDeploymentListener(final IDeviceManagementInteractor executor) {
		if (null != outputView) {
			executor.addDeploymentListener(outputView);
		}
		executor.addDeploymentListener(this);
	}

	private void removeDeploymentListener(final IDeviceManagementInteractor executor) {
		if (null != outputView) {
			executor.removeDeploymentListener(outputView);
		}
		executor.removeDeploymentListener(this);
	}

	private int calculateWorkAmount() {
		int retVal = deploymentData.size();

		for (final DeviceDeploymentData devData : deploymentData) {
			retVal += devData.getSelectedDevParams().size();
			retVal += devData.getResData().size();
			for (final ResourceDeploymentData resDepData : devData.getResData()) {
				retVal += countResourceParams(resDepData.getRes());
				retVal += resDepData.getFbs().size() + resDepData.getConnections().size()
						+ resDepData.getParams().size();
				// TODO count variables of Fbs
			}
		}
		return retVal;
	}

	private static int countResourceParams(final Resource res) {
		int work = 0;
		for (final VarDeclaration varDecl : res.getVarDeclarations()) {
			if ((varDecl.getValue() != null) && (!varDecl.getValue().getValue().isEmpty())) {
				work++;
			}
		}
		return work;
	}

	protected void deployResource(final ResourceDeploymentData resDepData, final IDeviceManagementInteractor executor)
			throws DeploymentException {
		final Resource res = resDepData.getRes();
		if (!res.isDeviceTypeResource()) {
			executor.createResource(res);
			curMonitor.worked(1);
			for (final VarDeclaration varDecl : res.getVarDeclarations()) {
				final String val = DeploymentHelper.getVariableValue(varDecl);
				if (null != val) {
					executor.writeResourceParameter(res, varDecl.getName(), val);
					curMonitor.worked(1);
				}
			}
			createFBInstance(resDepData, executor);
			deployParamters(resDepData, executor); // this needs to be done before the connections are created
			deployConnections(resDepData, executor);
			executor.startResource(res);
		}
	}

	private void deployParamters(final ResourceDeploymentData resDepData, final IDeviceManagementInteractor executor)
			throws DeploymentException {
		for (final ParameterData param : resDepData.getParams()) {
			executor.writeFBParameter(resDepData.getRes(), param.getValue(),
					new FBDeploymentData(param.getPrefix(), param.getVar().getFBNetworkElement()), param.getVar());
			curMonitor.worked(1);
		}
	}

	private void deployConnections(final ResourceDeploymentData resDepData, final IDeviceManagementInteractor executor)
			throws DeploymentException {
		for (final ConnectionDeploymentData con : resDepData.getConnections()) {
			executor.createConnection(resDepData.getRes(), con);
			curMonitor.worked(1);
			if (curMonitor.isCanceled()) {
				break;
			}
		}
	}

	private void createFBInstance(final ResourceDeploymentData resDepData, final IDeviceManagementInteractor executor)
			throws DeploymentException {
		final Resource res = resDepData.getRes();
		for (final FBDeploymentData fbDepData : resDepData.getFbs()) {
			if (fbDepData.getFb() instanceof FB && !((FB) fbDepData.getFb()).isResourceTypeFB()) {
				executor.createFBInstance(fbDepData, res);
				curMonitor.worked(1);
				final InterfaceList interfaceList = fbDepData.getFb().getInterface();
				if (interfaceList != null) {
					for (final VarDeclaration varDecl : interfaceList.getInputVars()) {
						final String val = DeploymentHelper.getVariableValue(varDecl);
						if (null != val) {
							executor.writeFBParameter(res, val, fbDepData, varDecl);
							curMonitor.worked(1);
						}
					}
				}
			}
		}
	}

	private boolean askOverrideForResource(final Resource res) throws InterruptedException {
		final AtomicInteger result = new AtomicInteger();
		Display.getDefault().syncExec(() -> {
			final Shell shell = Display.getDefault().getActiveShell();
			final MessageDialog dialog = new MessageDialog(shell, Messages.DownloadRunnable_ResourceAlreadyExists, null,
					MessageFormat.format(Messages.DownloadRunnable_ResourceOverrideQuestion, res.getName(),
							res.getDevice().getName()),
					MessageDialog.QUESTION,
					new String[] { Messages.DownloadRunnable_Replace, SWT.getMessage("SWT_Abort"), //$NON-NLS-1$
							SWT.getMessage("SWT_Cancel"), Messages.DownloadRunnable_ReplaceAll }, //$NON-NLS-1$
					0);
			result.set(dialog.open());
		});

		switch (result.get()) {
		case 0: // replace
			return true; // override the resource
		case 1: // abort
			throw new InterruptedException(Messages.DeploymentCoordinator_LABEL_DownloadAborted);
		case 3: // replace all
			reasureOverrideAll();

			return true;
		default:
			break;
		}

		return false; // in the default case we don't want to override the resoruce
	}

	private void reasureOverrideAll() throws InterruptedException {
		final StringBuilder message = new StringBuilder(Messages.DownloadRunnable_ReassureOveride);

		for (final DeviceDeploymentData data : deploymentData) {
			message.append("\n\t- "); //$NON-NLS-1$
			message.append(data.getDevice().getName());
			message.append(":"); //$NON-NLS-1$
			for (final ResourceDeploymentData resData : data.getResData()) {
				message.append("\n\t\t- "); //$NON-NLS-1$
				message.append(resData.getRes().getName());
			}
		}

		final AtomicInteger result = new AtomicInteger();
		Display.getDefault().syncExec(() -> {
			final Shell shell = Display.getDefault().getActiveShell();
			final MessageDialog dialog = new MessageDialog(shell, Messages.DownloadRunnable_ReassureOverideHeader, null,
					message.toString(), MessageDialog.QUESTION,
					new String[] { Messages.DownloadRunnable_ReplaceAll, SWT.getMessage("SWT_Abort") }, 0); //$NON-NLS-1$
			result.set(dialog.open());
		});

		if (1 == result.get()) {
			throw new InterruptedException(Messages.DeploymentCoordinator_LABEL_DownloadAborted);
		}
		// if the user selects ReplaceAll we don't need to do anything special
		overrideAll = true;
	}

	@Override
	public void connectionOpened(final Device dev) {
		// we don't need to do anything on connection opened
	}

	@Override
	public void postCommandSent(final String info, final String destination, final String command) {
		// we don't need to do anything on command sent
	}

	@Override
	public void postResponseReceived(final String response, final String source) {
		if (response.contains("Reason") && result.isOK()) { //$NON-NLS-1$
			result = Status.error(Messages.DownloadRunnable_DeploymentErrorWarningMessage);
		}
	}

	@Override
	public void connectionClosed(final Device dev) {
		// we don't need to do anything on connection closed
	}

	private void handleDeploymentException(final Device device, final DeploymentException e) {
		result = Status.error(MessageFormat.format(Messages.DownloadRunnable_DownloadErrorDetails, device.getName(),
				DeploymentHelper.getMgrIDSafe(device), e.getMessage()), e);
	}

	public IStatus getResult() {
		return result;
	}
}