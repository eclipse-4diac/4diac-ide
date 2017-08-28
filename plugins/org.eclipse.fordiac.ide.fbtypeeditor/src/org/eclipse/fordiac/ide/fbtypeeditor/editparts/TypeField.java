/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class TypeField {
	private final IInterfaceElement referencedElement;

	public IInterfaceElement getReferencedElement() {
		return referencedElement;
	}

	public TypeField(IInterfaceElement referencedElement) {
		this.referencedElement = referencedElement;
	}
	
	public String getLabel() {
		String type = ""; //$NON-NLS-1$
		if (getReferencedElement() instanceof Event) {
//			Event event = (Event) getReferencedElement();
			type = "Event"; //$NON-NLS-1$
		} else if (getReferencedElement() instanceof VarDeclaration) {
			VarDeclaration varDecl = (VarDeclaration) getReferencedElement();
			type = varDecl.getTypeName();
		}
		return type;
	}
	
	public String getArrayLabel(){
		String typeLabel = getLabel();
		if (referencedElement instanceof VarDeclaration){
			//if is array append array size
			VarDeclaration varDec =  (VarDeclaration)referencedElement;
			if(varDec.isArray()){
				typeLabel = typeLabel + "[" + varDec.getArraySize() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return typeLabel;
	}
}
