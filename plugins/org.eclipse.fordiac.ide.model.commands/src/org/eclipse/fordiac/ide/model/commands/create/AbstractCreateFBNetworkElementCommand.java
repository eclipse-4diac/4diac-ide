/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IEditorPart;

public abstract class AbstractCreateFBNetworkElementCommand extends Command {
	
	private final IEditorPart editor;
	private final FBNetworkElement element;
	private final FBNetwork fbNetwork;
	private int x;
	private int y;
	
	public AbstractCreateFBNetworkElementCommand(FBNetwork fbNetwork, FBNetworkElement element, int x, int y) {
		this.fbNetwork = fbNetwork;
		this.element = element;
		this.x = x;
		this.y = y;
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
	}
	
	@Override
	public boolean canExecute() {
		return fbNetwork != null;
	}
	
	@Override
	public boolean canUndo() {
		return editor.equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());
	}
	
	@Override
	public void execute() {
		setLabel(getLabel() + "(" + (editor != null ? editor.getTitle() : "") + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$		
		element.setInterface(EcoreUtil.copy(getTypeInterfaceList()));
		element.setX(x);
		element.setY(y);	
		createValues();
		fbNetwork.getNetworkElements().add(element);  // as subclasses may not be able to run redo on execute we have to duplicate this here
		element.setName(NameRepository.createUniqueName(element, getInitalInstanceName()));
	}
	
	@Override
	public void redo() {
		fbNetwork.getNetworkElements().add(element);
	}

	@Override
	public void undo() {
		fbNetwork.getNetworkElements().remove(element);
	}
	
	public void updateCreatePosition(int x, int y) {
		this.x = x;
		this.y = y;		
	}
	
	public FBNetworkElement getElement() {
		return element;
	}

	protected String getInitalInstanceName() {
		return element.getTypeName();
	}
	
	protected void createValues() {
		for(VarDeclaration inputVar : element.getInterface().getInputVars()) {
			Value value = LibraryElementFactory.eINSTANCE.createValue();
			inputVar.setValue(value);
		}
	}
	
	protected abstract InterfaceList getTypeInterfaceList();
}
