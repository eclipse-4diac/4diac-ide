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
package org.eclipse.fordiac.ide.model.dataimport;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

class ResDevFBNetworkImporter extends SubAppNetworkImporter {
	
	private final EList<VarDeclaration> varInputs;
	
	ResDevFBNetworkImporter(Palette palette, EList<VarDeclaration> varInputs){
		super(palette);
		this.varInputs = varInputs;
	}
	
	ResDevFBNetworkImporter(Palette palette, FBNetwork fbNetwork, EList<VarDeclaration> varInputs){
		super(palette, fbNetwork);
		this.varInputs = varInputs;
	}
	
	@Override
	protected IInterfaceElement getContainingInterfaceElement(String interfaceElement) {
		for (VarDeclaration var : varInputs) {
			if(var.getName().equals(interfaceElement)){
				return var;
			}
		}
		return null;
	}

}
