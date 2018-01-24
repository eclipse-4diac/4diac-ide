/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.IDeploymentExecutor;
import org.eclipse.fordiac.ide.deployment.exceptions.DisconnectException;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractDeploymentCommand extends AbstractHandler {

	protected Device device = null;
	
	
	public static class OnlineDeploymentErrorCheckListener implements IDeploymentListener{

		private AbstractDeploymentCommand currentObject = null;
		private String lastMessage = ""; //$NON-NLS-1$
		private String lastCommand = ""; //$NON-NLS-1$
		
		public void setLastMessage(String lastMessage) {
			this.lastMessage = lastMessage;
		}

		public void setCurrentObject(AbstractDeploymentCommand currentObject) {
			this.currentObject = currentObject;
		}
		
		public void setLastCommand(String lastCommand) {
			this.lastCommand = lastCommand;
		}

		@Override
		public void postCommandSent(String command, String destination) {
			lastCommand = command;
			
		}

		@Override
		public void postCommandSent(String message) {
			lastCommand = message;
			
		}

		@Override
		public void responseReceived(String response, String source) {
			if (response.contains("Reason")){ //$NON-NLS-1$
				showDeploymentError(response.substring(response.lastIndexOf("Reason") + 8,  response.length() - 4), source, currentObject, false);	 //$NON-NLS-1$
			}
		}

		@Override
		public void finished() {
			// nothing to do here			
		}

		@Override
		public void postCommandSent(String info, String destination, String command) {
			lastCommand = command;
			
		}
		
		private static OnlineDeploymentErrorCheckListener instance = null;
		
		public static OnlineDeploymentErrorCheckListener getInstance() {
			if (instance == null) {
				instance = new OnlineDeploymentErrorCheckListener();
			}
			return instance;
		}
		
		public void showDeploymentError(String response, String source, AbstractDeploymentCommand currentElement, boolean showWithConsole){
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IViewPart view = page.findView("org.eclipse.fordiac.ide.deployment.ui.views.Output"); //$NON-NLS-1$
			
			String currentMessage;
			
			if (!lastCommand.equals("")){ 
				currentMessage = currentElement.getCurrentElementName() + "\n" +	
						"Request: " + lastCommand + 
						"\nProblem: "+ response + 
						"\nSource: " + source + 
						"\nCheck the Deplopyment Console for more information";
				
			}else{
				currentMessage = currentElement.getCurrentElementName() + "\n" +		
						"Problem: "+ response + "\nSource: " + source; 
			}

			if (null == view || showWithConsole ){
				if (!lastMessage.equals(currentMessage)){ //when deleting Resources two messages are sent (KILL and delete Resource) and both failed creating two popups with the same information
					Shell shell = Display.getDefault().getActiveShell();	
					MessageDialog.openError(shell, currentElement.getErrorMessageHeader(), 
							currentMessage);
					lastMessage = currentMessage;
				}
 				
			}
		}
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		Object[] list = getObjectArrayFromSelection(selection);
		if (null != list) {
			for (Object currentElement : list) {
				if (prepareParametersToExecute(currentElement)) {
					IDeploymentExecutor executor = DeploymentCoordinator.getInstance().getDeploymentExecutor(device);
					if (null != executor) {
						String mgrid = DeploymentCoordinator.getMGR_ID(device);
						DeploymentCoordinator.getInstance().enableOutput(executor.getDevMgmComHandler());
						OnlineDeploymentErrorCheckListener.getInstance().setCurrentObject(this);
						OnlineDeploymentErrorCheckListener.getInstance().setLastMessage(""); //$NON-NLS-1$
						OnlineDeploymentErrorCheckListener.getInstance().setLastCommand(""); //$NON-NLS-1$
						executor.getDevMgmComHandler().addDeploymentListener(OnlineDeploymentErrorCheckListener.getInstance());
						try {
							executor.getDevMgmComHandler().connect(mgrid);
							executeCommand(executor);
						} catch (Exception e) {
							OnlineDeploymentErrorCheckListener.getInstance().showDeploymentError(e.getMessage(), mgrid, this, true);
						}finally {
							try {
								executor.getDevMgmComHandler().disconnect();
							} catch (DisconnectException e) {
								OnlineDeploymentErrorCheckListener.getInstance().showDeploymentError(e.getMessage(), mgrid, this, true);
							}							
						}
						DeploymentCoordinator.getInstance().flush();
						DeploymentCoordinator.getInstance().disableOutput(executor.getDevMgmComHandler());
					}else{
						manageExecutorError();
					}
				}
			}
		}
		return null;
	}
	
	protected Object[] getObjectArrayFromSelection(ISelection selection){
		Object[] list = null;
		if(selection instanceof StructuredSelection) {
			list = ((StructuredSelection) selection).toArray();
		}
		return list;
	}
	
	protected abstract boolean prepareParametersToExecute(Object element);
	
	protected abstract void executeCommand(IDeploymentExecutor executor) throws Exception;
	
	protected void manageExecutorError(){
		DeploymentCoordinator.printUnsupportedDeviceProfileMessageBox(device, null);
	}
	
	protected abstract String getErrorMessageHeader();
	
	protected abstract String getCurrentElementName();
	
}
