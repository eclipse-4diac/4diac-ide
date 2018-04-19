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
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
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

	private static final int DEFAULT_DELTA = 20;
	@SuppressWarnings("rawtypes")
	private final List templates;
	private final FBNetwork dstFBNetwork;
	
	private final Map<String, FBNetworkElement> copiedElements = new HashMap<>();
	
	private final List<FBNetworkElement> elementsToCopy = new ArrayList<>();
	
	private final Set<Connection> connectionsToCopy = new HashSet<>();
	
	private final CompoundCommand connCreateCmds = new CompoundCommand();
	
	private int xDelta;
	private int yDelta;
	private boolean calcualteDelta = false;
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
		this.dstFBNetwork = destination;
		this.pasteRefPos = pasteRefPos;
		calcualteDelta = true;
	}
	
	@SuppressWarnings("rawtypes")
	public PasteCommand(List templates, FBNetwork destination, int copyDeltaX, int copyDeltaY) {
		this.templates = templates;
		this.dstFBNetwork = destination;
		xDelta = copyDeltaX;
		yDelta = copyDeltaY;
	}
	
	@Override
	public boolean canExecute() {
		return null != templates && null != dstFBNetwork;
	}

	@Override
	public void execute() {
		if (dstFBNetwork != null) {			
			gatherCopyData();			
			copyFBs();
			copyConnections();
			(new ElementSelector()).selectViewObjects(copiedElements.values());
		}
	}
	
	@Override
	public void undo() {
		connCreateCmds.undo();
		dstFBNetwork.getNetworkElements().removeAll(copiedElements.values());		
		(new ElementSelector()).selectViewObjects(templates);
	}
	
	@Override
	public void redo() {
		dstFBNetwork.getNetworkElements().addAll(copiedElements.values());
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
		
		if(calcualteDelta) {
			if(null != pasteRefPos){
				xDelta = pasteRefPos.x - x;
				yDelta = pasteRefPos.y - y;
			}else {
				xDelta = DEFAULT_DELTA;
				yDelta = DEFAULT_DELTA;
			}
		}
	}
		
	private void copyFBs() {
		for (FBNetworkElement element : elementsToCopy) {
			FBNetworkElement copiedElement = createElementCopyFB(element);	
			copiedElements.put(element.getName(), copiedElement);
			dstFBNetwork.getNetworkElements().add(copiedElement);
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
			FBNetworkElement copiedSrc = copiedElements.get(conn.getSourceElement().getName());
			FBNetworkElement copiedDest = copiedElements.get(conn.getDestinationElement().getName());
			
			if (null != copiedSrc || null != copiedDest){
				//Only copy if one end of the connection is copied as well otherwise we will get a duplicate connection
				AbstractConnectionCreateCommand cmd = null;
				if(conn instanceof EventConnection){
					cmd = new EventConnectionCreateCommand(dstFBNetwork);
				}else if(conn instanceof DataConnection){
					if(null != copiedDest){
						//in order to avoid data fan-in data connections are only copied if also destination is part of the copy
						cmd = new DataConnectionCreateCommand(dstFBNetwork);
					}
				}else if(conn instanceof AdapterConnection){
					if(null != copiedSrc && null != copiedDest){
						//in order to avoid fan-in / fan-out adapter connections are only copied if also destination is part of the copy
						cmd = new AdapterConnectionCreateCommand(dstFBNetwork);
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
		FBNetworkElement copiedSrc = copiedElements.get(conn.getSourceElement().getName());
		FBNetworkElement copiedDest = copiedElements.get(conn.getDestinationElement().getName());
		//if the copied src or copied destination are null use the original 
		IInterfaceElement source = checkForCopiedInterfaceElement((null != copiedSrc) ? copiedSrc : conn.getSourceElement(),
				conn.getSource());
		IInterfaceElement destination = checkForCopiedInterfaceElement((null != copiedDest) ? copiedDest : conn.getDestinationElement(), 
				conn.getDestination());
		
		cmd.setSource(source);
		cmd.setDestination(destination);		
		cmd.setArrangementConstraints(conn.getDx1(), conn.getDx2(), conn.getDy());
	}

	private static IInterfaceElement checkForCopiedInterfaceElement(FBNetworkElement targetElement, IInterfaceElement orig) {
		Assert.isNotNull(targetElement);
	    return targetElement.getInterfaceElement(orig.getName());
	}

	private Point calculatePastePos(FBNetworkElement element) {
		return new Point(element.getX() + xDelta, element.getY() + yDelta);
	}

}
