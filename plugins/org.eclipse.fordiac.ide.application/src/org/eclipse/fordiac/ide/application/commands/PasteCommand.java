/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Andren, Matthias Plasch 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.util.ElementSelector;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.graphics.Point;

/**
 * The Class PasteCommand.
 */
public class PasteCommand extends Command {

	@SuppressWarnings("rawtypes")
	private final List templates;
	private final FBNetwork destination;
	
	private final Map<String, FBNetworkElement> copiedElements = new HashMap<>();
	
	private final List<FBNetworkElement> elementsToCopy = new ArrayList<>();
	
	private final Set<Connection> connectionsToCopy = new HashSet<>();
	
	private final CompoundCommand connCreateCmds = new CompoundCommand();
	
	private Point sourceRefPos;
	private Point pasteRefPos;
	

	/**
	 * Instantiates a new paste command.
	 * 
	 * @param templates 
	 * 			  the elements that should be copied to the destination
	 * @param destination
	 *            the destination fbnetwork where the elements should be copied to
	 * @param pasteRefPos
	 *            the reference position for pasting the elements           
	 */
	@SuppressWarnings("rawtypes")
	public PasteCommand(List templates, FBNetwork destination, Point pasteRefPos) {
		this.templates = templates;
		this.destination = destination;
		this.pasteRefPos = pasteRefPos;
	}
	
	@Override
	public boolean canExecute() {
		return null != templates && null != destination;
	}

	@Override
	public void execute() {
		if (destination != null) {			
			gatherCopyData();			
			copyFBs();
			copyConnections();
			(new ElementSelector()).selectViewObjects(copiedElements.values());
		}
	}
	
	@Override
	public void undo() {
		connCreateCmds.undo();
		destination.getNetworkElements().removeAll(copiedElements.values());		
		(new ElementSelector()).selectViewObjects(templates);
	}
	
	@Override
	public void redo() {
		destination.getNetworkElements().addAll(copiedElements.values());
		connCreateCmds.redo();
		(new ElementSelector()).selectViewObjects(copiedElements.values());		
	}


	private void gatherCopyData() {
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;
		
		for (Object object : templates) {
			if (object instanceof FBNetworkElement) {	
				FBNetworkElement element = (FBNetworkElement)object; 
				elementsToCopy.add(element);
				x = Math.min(x, element.getX());
				y = Math.min(y, element.getY());
			}else if(object instanceof Connection){
				connectionsToCopy.add((Connection)object);
			}
		}
		
		sourceRefPos = new Point(x, y);
		if(null == pasteRefPos){
			pasteRefPos = new Point(x + 20, y + 20);
		}
	}
		
	private void copyFBs() {
		for (FBNetworkElement element : elementsToCopy) {
			FBNetworkElement copiedElement = createElementCopyFB(element);	
			copiedElements.put(element.getName(), copiedElement);
			destination.getNetworkElements().add(copiedElement);
			copiedElement.setName(NameRepository.createUniqueName(copiedElement, element.getName()));
		}
	}

	private FBNetworkElement createElementCopyFB(FBNetworkElement element) {
		FBNetworkElement copiedElement = EcoreUtil.copy(element);
		//clear the connection references
		for (IInterfaceElement ie : copiedElement.getInterface().getAllInterfaceElements()) {
			if(ie.isIsInput()){
				ie.getInputConnections().clear();
			}else {
				ie.getOutputConnections().clear();
			}
		}
		Point pastePos = calculatePastePos(element); 
		copiedElement.setX(pastePos.x);
		copiedElement.setY(pastePos.y);
		
		copiedElement.setMapping(null);
		
		return copiedElement;
	}

	private void copyConnections() {
		for (Connection conn : connectionsToCopy) {
			FBNetworkElement  dst = conn.getDestinationElement(); 
			FBNetworkElement  src = conn.getSourceElement();
			
			if (null != copiedElements.get(src.getName()) || (null != copiedElements.get(dst.getName()))){
				AbstractConnectionCreateCommand cmd = null;
				if(conn instanceof EventConnection){
					cmd = new EventConnectionCreateCommand(destination);
				}else if(conn instanceof DataConnection){
					if(null != copiedElements.get(dst.getName())){
						//in order to avoid data fan-in data connections are only copied if also destination is part of the copy
						cmd = new DataConnectionCreateCommand(destination);
					}
				}else if(conn instanceof AdapterConnection){
					if((null != copiedElements.get(dst.getName())) && (null != copiedElements.get(src.getName()))){
						//in order to avoid data fan-in / fan-out adapter connections are only copied if also destination is part of the copy
						cmd = new AdapterConnectionCreateCommand(destination);
					}
				}
				if(null != cmd){
					copyConnection(conn, cmd);
					if(cmd.canExecute()){
						connCreateCmds.add(cmd);
					}
				}
			}
		}	
		connCreateCmds.execute();
	}
	

	private void copyConnection(Connection conn, AbstractConnectionCreateCommand cmd) {
		IInterfaceElement source = checkForCopiedInterfaceElement(copiedElements.get(conn.getSourceElement().getName()), 
				conn.getSource());
		IInterfaceElement destination = checkForCopiedInterfaceElement(copiedElements.get(conn.getDestinationElement().getName()), 
				conn.getDestination());
		
		cmd.setSource(source);
		cmd.setDestination(destination);		
		cmd.setArrangementConstraints(conn.getDx1(), conn.getDx2(), conn.getDy());
	}

	private IInterfaceElement checkForCopiedInterfaceElement(FBNetworkElement targetElement, IInterfaceElement orig) {
		Assert.isNotNull(targetElement);
	    return targetElement.getInterfaceElement(orig.getName());
	}

	private Point calculatePastePos(FBNetworkElement element) {
		int xDelta = element.getX() - sourceRefPos.x;
		int yDelta = element.getY() - sourceRefPos.y;		
		return new Point(pasteRefPos.x + xDelta, pasteRefPos.y + yDelta);
	}

}
