/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, fortiss GmbH, 
 * 							 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked deployment to detect if monitoring was enabled  
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.DeviceDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.ResourceDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.ResourceDeploymentData.ParameterData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor.IDeviceManagementInteractorCloser;
import org.eclipse.fordiac.ide.deployment.monitoringbase.AbstractMonitoringManager;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
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

class DownloadRunnable implements IRunnableWithProgress {
	
	private final List<DeviceDeploymentData>  deploymentData;
	private final IDeviceManagementCommunicationHandler overrideDevMgmCommHandler;
	private final IDeploymentListener outputView;
	private final String profile;
	private IProgressMonitor curMonitor;
	
	/** flag indicating if an existing resource should automatically be overriden or if the user should be asked
	 */
	private boolean overrideAll = false;
	
	/** set of automation systems where monitoring was active during deployment.
	 * 
	 * For these automation systems monitoring was disabled and need to be renabled after deployment.
	 */
	private final Set<AutomationSystem> monitoredSystems = new HashSet<>();  

	/**
	 * DownloadRunnable constructor.
	 * 
	 * @param deploymentData			the collection of elements to deploy
	 * @param overrideDevMgmCommHandler if not null this device management
	 *                                  communication should be used instead the one
	 *                                  derived from the device profile.
	 * @param outputView				the view showing the download information
	 * @param profile					if not null the profile to be used instead of the device's profile							                                 
	 */
	public DownloadRunnable(final List<DeviceDeploymentData>  deploymentData,
			IDeviceManagementCommunicationHandler overrideDevMgmCommHandler, IDeploymentListener outputView, 
			String profile) {
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
		
		for(DeviceDeploymentData devData : deploymentData) {
			if (monitor.isCanceled()) {
				throw new InterruptedException(Messages.DeploymentCoordinator_LABEL_DownloadAborted);
			}
			deployDevice(devData);			
		}
		reenableMonitoring();
		monitor.done();
	}

	private void deployDevice(DeviceDeploymentData devData) throws InvocationTargetException, InterruptedException {
		IDeviceManagementInteractor executor = DeviceManagementInteractorFactory.INSTANCE.getDeviceManagementInteractor(devData.getDevice(), overrideDevMgmCommHandler, profile);
		if (executor != null) {	
			checkMonitoring(devData.getDevice().getAutomationSystem());
			addDeploymentListener(executor);
			try(IDeviceManagementInteractorCloser closer = executor::disconnect){
				executor.connect();
				deployResources(devData, executor);
				deployDeviceData(devData, executor);					
			} catch (final DeploymentException e) {
				showDeploymentErrorDialog(devData.getDevice(), e);					
			} finally {
				removeDeploymentListener(executor);
			}
			
		} else {
			DeploymentCoordinator.printUnsupportedDeviceProfileMessageBox(devData.getDevice(), null);
		}
	}

	private void deployResources(DeviceDeploymentData devData, IDeviceManagementInteractor executor) throws InterruptedException, DeploymentException {
		Set<String> resources = executor.queryResources().stream().map(res -> res.getName()).collect(Collectors.toSet());
		
		for (ResourceDeploymentData resData : devData.getResData()) {
			if (curMonitor.isCanceled()) {
				throw new InterruptedException(Messages.DeploymentCoordinator_LABEL_DownloadAborted);
			}
			if(resources.contains(resData.res.getName())) {
				//the resource is in the device
				if(overrideAll) {
					executor.deleteResource(resData.res.getName());
				}else {
					if(askOverrideForResource(resData.res)){
						executor.deleteResource(resData.res.getName());
					}else {
						//the user has canceled to override this resource
						continue;
					}
				}
			} 			
			deployResource(resData, executor);
		}
	}

	private void deployDeviceData(DeviceDeploymentData devData, IDeviceManagementInteractor executor) throws DeploymentException {
		Device device = devData.getDevice();
		
		for(VarDeclaration var : devData.getSelectedDevParams()) {
			String value = DeploymentHelper.getVariableValue(var, device.getAutomationSystem());
			if (null != value) {
				executor.writeDeviceParameter(device, var.getName(), value);
			}				
			curMonitor.worked(1);
		}						
	}

	private static void showDeploymentErrorDialog(Device device, DeploymentException e) {
		Display.getDefault().asyncExec(() -> {
			final Shell shell = Display.getDefault().getActiveShell();
			MessageDialog.openError(shell, Messages.DownloadRunnable_MajorDownloadError,
					MessageFormat.format(Messages.DownloadRunnable_DownloadErrorDetails, device.getName(), DeploymentHelper.getMgrID(device), e.getMessage()));
		});		
	}

	private void addDeploymentListener(IDeviceManagementInteractor executor) {
		if (null != outputView) { 
			executor.addDeploymentListener(outputView);
		}
	}
	
	private void removeDeploymentListener(IDeviceManagementInteractor executor) {
		if (null != outputView) { 
			executor.removeDeploymentListener(outputView);
		}
	}

	private int calculateWorkAmount() {
		int retVal = deploymentData.size(); 
		
		for (DeviceDeploymentData devData : deploymentData) {
			retVal += devData.getSelectedDevParams().size();
			retVal += devData.getResData().size();
			for (ResourceDeploymentData resDepData : devData.getResData()) {
				retVal += countResourceParams(resDepData.res);
				retVal += resDepData.getFbs().size() + resDepData.getConnections().size() + resDepData.getParams().size();
				// TODO count variables of Fbs
			}
		}
		return retVal;
	}

	private static int countResourceParams(final Resource res) {
		int work = 0;
		for (VarDeclaration varDecl : res.getVarDeclarations()) {
			if (varDecl.getValue() != null && varDecl.getValue().getValue() != null
					&& varDecl.getValue().getValue().equals("")) { //$NON-NLS-1$
				work++;
			}
		}
		return work;
	}

	protected void deployResource(final ResourceDeploymentData resDepData,
			IDeviceManagementInteractor executor) throws DeploymentException {
		Resource res = resDepData.res;
		if (!res.isDeviceTypeResource()) {
			executor.createResource(res);
			curMonitor.worked(1);
			for (VarDeclaration varDecl : res.getVarDeclarations()) {
				String val = DeploymentHelper.getVariableValue(varDecl, res.getAutomationSystem());
				if (null != val) {
					executor.writeResourceParameter(res, varDecl.getName(), val);
					curMonitor.worked(1);
				}
			}
			createFBInstance(resDepData, executor);
			deployParamters(resDepData, executor); // this needs to be done before the connections are created
			deployConnections(resDepData, executor);
			executor.startResource(resDepData.res);
		}
	}

	private void deployParamters(ResourceDeploymentData resDepData, IDeviceManagementInteractor executor) throws DeploymentException {
		for (ParameterData param : resDepData.getParams()) {
			executor.writeFBParameter(resDepData.res, param.getValue(),
					new FBDeploymentData(param.getPrefix(), (FB) param.getVar().getFBNetworkElement()), param.getVar());
			curMonitor.worked(1);
		}
	}

	private void deployConnections(final ResourceDeploymentData resDepData,
			final IDeviceManagementInteractor executor) throws DeploymentException {
		for (ConnectionDeploymentData con : resDepData.getConnections()) {
			executor.createConnection(resDepData.res, con);
			curMonitor.worked(1);
			if (curMonitor.isCanceled()) {
				break;
			}
		}
	}

	private void createFBInstance(final ResourceDeploymentData resDepData, final IDeviceManagementInteractor executor) throws DeploymentException {
		Resource res = resDepData.res;
		for (FBDeploymentData fbDepData : resDepData.getFbs()) {
			if (!fbDepData.fb.isResourceTypeFB()) {
				executor.createFBInstance(fbDepData, res);
				curMonitor.worked(1);
				InterfaceList interfaceList = fbDepData.fb.getInterface();
				if (interfaceList != null) {
					for (VarDeclaration varDecl : interfaceList.getInputVars()) {
						String val = DeploymentHelper.getVariableValue(varDecl, res.getAutomationSystem());
						if (null != val) {
							executor.writeFBParameter(res, val, fbDepData, varDecl);
							curMonitor.worked(1);
						}
					}
				}
			}
		}
	}

	private void checkMonitoring(AutomationSystem automationSystem) throws InvocationTargetException, InterruptedException {
		if(!monitoredSystems.contains(automationSystem)) {
			AbstractMonitoringManager monitoringManager = AbstractMonitoringManager.getMonitoringManager();
			if(monitoringManager.isSystemMonitored(automationSystem)) {
				monitoringManager.disableSystemSynch(automationSystem, curMonitor);
				monitoredSystems.add(automationSystem);
			}
		}		
	}

	private void reenableMonitoring() throws InvocationTargetException, InterruptedException {
		AbstractMonitoringManager monitoringManager = AbstractMonitoringManager.getMonitoringManager();
		for (AutomationSystem system : monitoredSystems) {
			monitoringManager.enableSystemSynch(system, curMonitor);
		}
	}
	
	private boolean askOverrideForResource(Resource res) throws InterruptedException {	
		final AtomicInteger result = new AtomicInteger();
		Display.getDefault().syncExec(() -> {
				final Shell shell = Display.getDefault().getActiveShell();
				MessageDialog dialog = new MessageDialog(shell, 
						Messages.DownloadRunnable_ResourceAlreadyExists, null, 
						MessageFormat.format(Messages.DownloadRunnable_ResourceOverrideQuestion, res.getName(), res.getDevice().getName()), MessageDialog.QUESTION, 
						new String[] { Messages.DownloadRunnable_Replace,  SWT.getMessage("SWT_Abort"), SWT.getMessage("SWT_Cancel"), Messages.DownloadRunnable_ReplaceAll }, 0);  //$NON-NLS-1$ //$NON-NLS-2$
				result.set(dialog.open());
			});
				
		switch(result.get()) {
		case 0:  // replace
			return true; //override the resource
		case 1:  // abort
			throw new InterruptedException(Messages.DeploymentCoordinator_LABEL_DownloadAborted);
		case 3: // replace all
			reasureOverrideAll();
			
			return true;
		default:
			break;
		}
		
		return false;  //in the default case we don't want to override the resoruce
	}

	private void reasureOverrideAll() throws InterruptedException {
		final StringBuilder message = new StringBuilder(Messages.DownloadRunnable_ReassureOveride);
		
		for (DeviceDeploymentData data : deploymentData) {
			message.append("\n\t- ");  //$NON-NLS-1$
			message.append(data.getDevice().getName());
			message.append(":"); //$NON-NLS-1$
			for(ResourceDeploymentData resData: data.getResData()) {
				message.append("\n\t\t- ");  //$NON-NLS-1$
				message.append(resData.res.getName());
			}
		}
		
		final AtomicInteger result = new AtomicInteger();
		Display.getDefault().syncExec(() -> {
			final Shell shell = Display.getDefault().getActiveShell();
			MessageDialog dialog = new MessageDialog(shell, 
					Messages.DownloadRunnable_ReassureOverideHeader, null, 
					message.toString(), MessageDialog.QUESTION, 
					new String[] { Messages.DownloadRunnable_ReplaceAll,  SWT.getMessage("SWT_Abort")}, 0);  //$NON-NLS-1$
			result.set(dialog.open());
		});
		
		if(1 == result.get()) {
			throw new InterruptedException(Messages.DeploymentCoordinator_LABEL_DownloadAborted);
		}
		//if the user selects ReplaceAll we don't need to do anything special
		overrideAll = true;
	}


}