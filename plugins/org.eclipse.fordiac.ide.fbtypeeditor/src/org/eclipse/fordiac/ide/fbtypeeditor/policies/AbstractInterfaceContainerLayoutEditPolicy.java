/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.policies;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.AbstractContainerElement;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceContainerEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;

public abstract class AbstractInterfaceContainerLayoutEditPolicy extends FlowLayoutEditPolicy {

	protected AbstractContainerElement getModel(){
		return ((InterfaceContainerEditPart)getHost()).getModel();
	}
	
	protected FBType getFBType(){
		return getModel().getFbType();
	}
	
	@Override
	protected Command createAddCommand(final EditPart child, final EditPart after) {
		return null;
	}
}
