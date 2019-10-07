/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo 
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
import org.eclipse.gef.commands.Command;

public abstract class AbstractCreateFBNetworkElementCommand extends Command {

	private final FBNetworkElement element;
	private final FBNetwork fbNetwork;
	private int x;
	private int y;

	public AbstractCreateFBNetworkElementCommand(FBNetwork fbNetwork, FBNetworkElement element, int x, int y) {
		this.fbNetwork = fbNetwork;
		this.element = element;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean canExecute() {
		return fbNetwork != null;
	}

	@Override
	public void execute() {
		element.setInterface(EcoreUtil.copy(getTypeInterfaceList()));
		element.setX(x);
		element.setY(y);
		createValues();
		fbNetwork.getNetworkElements().add(element); // as subclasses may not be able to run redo on execute we have to
														// duplicate this here
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
		for (VarDeclaration inputVar : element.getInterface().getInputVars()) {
			Value value = LibraryElementFactory.eINSTANCE.createValue();
			inputVar.setValue(value);
		}
	}

	protected abstract InterfaceList getTypeInterfaceList();
}
