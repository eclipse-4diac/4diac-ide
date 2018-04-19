/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateSubAppInstanceCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class MapToCommand extends Command {
	protected final FBNetworkElement srcElement;
	protected final Resource resource;
	private UnmapCommand unmappFromExistingTarget;
	private FBNetworkElement targetElement;
	private Mapping mapping = LibraryElementFactory.eINSTANCE.createMapping();
	private CompoundCommand createdConnections = new CompoundCommand();

	public MapToCommand(final FBNetworkElement srcElement, final Resource resource) {
		this.srcElement = srcElement;
		this.resource = resource;
	}

	@Override
	public boolean canExecute() {
		if (srcElement == null) {
			return false;
		}
		if ((srcElement.isMapped()) && (srcElement.getOpposite().getFbNetwork().equals(resource.getFBNetwork()))) {
			Abstract4DIACUIPlugin.getCurrentActiveEditor().getEditorSite().getActionBars().getStatusLineManager()
						.setErrorMessage(Messages.MapToCommand_STATUSMessage_AlreadyMapped);
				return false; // already mapped to this resource -> nothing to do -> mapping not possible!
		}

		boolean supports = deviceSupportsType();

		if (supports) {
			Abstract4DIACUIPlugin.getCurrentActiveEditor().getEditorSite().getActionBars()
					.getStatusLineManager().setErrorMessage(null);
		} else {
			Abstract4DIACUIPlugin.getCurrentActiveEditor().getEditorSite().getActionBars()
					.getStatusLineManager().setErrorMessage(Messages.MapToCommand_STATUSMessage_TypeNotSupported);
		}
		return supports;
	}

	private boolean deviceSupportsType() {
		return true;
	}

	/**  Steps needed for the mapping command:
	 *  1. If already mapped create unmapp command and execute it
	 *  2. Create FB in target FBNetwork (use FBnetwork create command)
	 *  3. Create Mapping entry
	 *  4. Determine list of connections that need to be created in target FBNetwork
	 *  5. Execute the create connection command for these
	 */
	@Override
	public void execute(){
		if(srcElement.isMapped()){
			unmappFromExistingTarget = new UnmapCommand(srcElement.getOpposite());
			unmappFromExistingTarget.execute();
		}

		createTargetElement();
		
		mapping.setFrom(srcElement);
		mapping.setTo(targetElement);
		srcElement.setMapping(mapping);
		targetElement.setMapping(mapping);
		getAutomationSystem().getMapping().add(mapping);
		
		checkConnections();		
		createdConnections.execute();
	}

	/** Steps
	 *  1. handle broken and unbroken connections
	 *  2. for each connection create command -> execute undo
	 *  3. for FBcreate command -> execute undo
	 *  4. remove mapping entry
	 *  5. if unmapp command is not null -> execute undo
	 */
	@Override
	public void undo() {
		createdConnections.undo();
		
		srcElement.setMapping(null);  //mapping should be removed first so that all notifiers checking for mapped state will not invalily afterwards use the resource
		targetElement.setMapping(null);
		getTargetFBNetwork().getNetworkElements().remove(targetElement);
		getAutomationSystem().getMapping().remove(mapping);
		
		if (null != unmappFromExistingTarget) {
			unmappFromExistingTarget.undo();
		}
	}

	/** Steps
	 *  1. if unmapp command is not null -> execute redo
	 *  2. for FBcreate command -> execute redo
	 *  3. readd mapping entry
	 *  3. for each connection create command -> execute redo
	 *  4. handle broken and unbroken connections
	 */
	@Override
	public void redo() {		
		if (null != unmappFromExistingTarget) {
			unmappFromExistingTarget.redo();
		}		
		getTargetFBNetwork().getNetworkElements().add(targetElement);		
		srcElement.setMapping(mapping);
		targetElement.setMapping(mapping);
		getAutomationSystem().getMapping().add(mapping);		
		createdConnections.redo();
	}
	
	protected void createTargetElement() {
		if(srcElement instanceof FB){
			targetElement = createTargetFB();
		}else if (srcElement instanceof SubApp){
			if(null != srcElement.getPaletteEntry()){
				targetElement = createTargetTypedSubApp();
			} else{
				targetElement = createTargetUntypedSubApp();
			}
		}
		targetElement.setName(srcElement.getName());
		transferFBParams();
	}

	private FBNetworkElement createTargetFB() {
		FBCreateCommand targetCreateFB = new FBCreateCommand((FBTypePaletteEntry)srcElement.getPaletteEntry(), getTargetFBNetwork(), srcElement.getX(), srcElement.getY());
		targetCreateFB.execute();
		return targetCreateFB.getFB();
	}

	private FBNetworkElement createTargetTypedSubApp() {
		CreateSubAppInstanceCommand cmd = new CreateSubAppInstanceCommand((SubApplicationTypePaletteEntry)srcElement.getPaletteEntry(), getTargetFBNetwork(), srcElement.getX(), srcElement.getY());
		cmd.execute();
		return cmd.getSubApp();
	}
	
	private FBNetworkElement createTargetUntypedSubApp() {
		SubApp element = LibraryElementFactory.eINSTANCE.createSubApp();
		element.setX(srcElement.getX());
		element.setY(srcElement.getY());
		element.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		element.setInterface(EcoreUtil.copy(srcElement.getInterface()));
		for (IInterfaceElement ie : element.getInterface().getAllInterfaceElements()) {
			ie.getInputConnections().clear();
			ie.getOutputConnections().clear();
		}
		getTargetFBNetwork().getNetworkElements().add(element);
		return element;
	}

	private void transferFBParams(){
		List<VarDeclaration> destInputs = targetElement.getInterface().getInputVars();
		List<VarDeclaration> srcInputs = srcElement.getInterface().getInputVars();
		
		for(int i = 0; i < destInputs.size(); i++){
			VarDeclaration srcVar = srcInputs.get(i);
			VarDeclaration dstVar = destInputs.get(i);
			
			if(null != srcVar.getValue()){
				if(null != dstVar.getValue()){
					dstVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
				}
				dstVar.getValue().setValue(srcVar.getValue().getValue());
			}			
		}
	}
		
		

	private AutomationSystem getAutomationSystem() {
		return srcElement.getFbNetwork().getApplication().getAutomationSystem();
	}

	private void checkConnections() {
		for (IInterfaceElement interfaceElement : srcElement.getInterface().getAllInterfaceElements()) {
			if(interfaceElement.isIsInput()){
				for (Connection connection : interfaceElement.getInputConnections()) {
					Resource res = connection.getSourceElement().getResource();
					if(resource.equals(res)){
						//we need to create a connection in the target resource
						addConnectionCreateCommand(connection.getSourceElement().getOpposite().getInterfaceElement(connection.getSource().getName()),
								targetElement.getInterfaceElement(interfaceElement.getName()));
					}
				}
			}else{
				for (Connection connection : interfaceElement.getOutputConnections()) {
					Resource res = connection.getDestinationElement().getResource();
					if(resource.equals(res)){
						//we need to create a connection in the target resource
						IInterfaceElement destination = connection.getDestinationElement().getOpposite().getInterfaceElement(connection.getDestination().getName()); 
						addConnectionCreateCommand(targetElement.getInterfaceElement(interfaceElement.getName()),
								destination);
						if(destination instanceof AdapterDeclaration || destination instanceof VarDeclaration){
							checkForDeleteConnections(destination);
						}
					}
				}
			}
		}
	}

	private void addConnectionCreateCommand(IInterfaceElement source, IInterfaceElement destination) {
		AbstractConnectionCreateCommand cmd = getConnectionCreatCMD(source);
		cmd.setSource(source);
		cmd.setDestination(destination);
		createdConnections.add(cmd);
	}
	
	private void checkForDeleteConnections(IInterfaceElement destination) {
		//TODO model refactoring - determine also connection in the target resource which maybe have to be deleted		
	}

	private AbstractConnectionCreateCommand getConnectionCreatCMD(IInterfaceElement interfaceElement) {
		if(interfaceElement instanceof Event){
			return new EventConnectionCreateCommand(resource.getFBNetwork());
		}else if (interfaceElement instanceof AdapterDeclaration){
			return new AdapterConnectionCreateCommand(resource.getFBNetwork());
		}else if (interfaceElement instanceof VarDeclaration){
			return new DataConnectionCreateCommand(resource.getFBNetwork());
		}
		return null;
	}
	
	private FBNetwork getTargetFBNetwork() {
		return resource.getFBNetwork();
	}

	/*This code is here to serve as template for handleing the connections to be deleted

	public void execute() {
		boolean deletedConnections = false;

		uiResourceEditor.getResourceElement().getFBNetwork().getMappedFBs()
				.add(mappedFBView.getFb());

		for (Iterator<InterfaceElementView> iterator = fbView
				.getInterfaceElements().iterator(); iterator.hasNext();) {
			InterfaceElementView interfaceElement = iterator.next();

			for (Iterator<ConnectionView> iterator2 = interfaceElement
					.getInConnections().iterator(); iterator2.hasNext();) {
				ConnectionView connectionView = iterator2.next();
				if (connectionView.getSource().eContainer() instanceof FBView) {
					FBView sourceFBView = ((FBView) connectionView.getSource()
							.eContainer()).getMappedFB();
					if (sourceFBView != null
							&& sourceFBView.getFb().getResource().equals(
									uiResourceEditor.getResourceElement()
											.getFBNetwork())) {
						ConnectionView newConnection = UiFactory.eINSTANCE
								.createConnectionView();
						newConnection.setConnectionElement(connectionView
								.getConnectionElement());
						newConnection.setDestination(connectionView
								.getDestination().getMappedInterfaceElement());
						newConnection.setSource(connectionView.getSource()
								.getMappedInterfaceElement());
						uiResourceEditor.getConnections().add(newConnection);
						ConnectionUtil.addConnectionToResource(newConnection
								.getConnectionElement(), uiResourceEditor
								.getResourceElement());
						connectionView.getConnectionElement()
								.setBrokenConnection(false);
						System.out.println("notBroken: " + connectionView);

						for (Iterator<ConnectionView> iterator3 = connectionView.getSource()
								.getMappedInterfaceElement()
								.getOutConnections().iterator(); iterator3
								.hasNext();) {
							ConnectionView temp = (ConnectionView) iterator3
									.next();
							System.out.println("Is Resource Connection "
									+ temp.getConnectionElement()
											.isResourceConnection());
							if (temp.getConnectionElement()
									.isResourceConnection()) {
								DeleteConnectionCommand deleteCMD = new DeleteConnectionCommand(
										temp);
								deleteCMD.execute();
								deletedConnections = true;
							}
						}

					} else {
						System.out.println("isBroken: " + connectionView);
						connectionView.getConnectionElement()
								.setBrokenConnection(true);
						// nothing to do
					}
				} 			
				
			}
		}
		if (deletedConnections) {
			MessageBox informUser = new MessageBox(Display.getDefault()
					.getActiveShell());
			informUser.setText("Warning");
			informUser
					.setMessage("Remapping required deletion of Connections added within the Resource - please check your network");
			informUser.open();
			// TODO check whether markers could be used!
		}
	}

*/
	
}
