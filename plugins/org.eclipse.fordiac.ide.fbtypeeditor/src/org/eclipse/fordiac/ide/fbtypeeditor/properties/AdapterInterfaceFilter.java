/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.CommentEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.TypeEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.jface.viewers.IFilter;

public class AdapterInterfaceFilter implements IFilter{

	@Override
	public boolean select(Object toTest) {
		if(toTest instanceof InterfaceEditPart && ((InterfaceEditPart) toTest).getCastedModel() instanceof AdapterDeclaration){
			return true;
		}
		if(toTest instanceof TypeEditPart && ((TypeEditPart) toTest).getCastedModel() instanceof AdapterDeclaration){
			return true;
		}
		if(toTest instanceof CommentEditPart && ((CommentEditPart) toTest).getCastedModel() instanceof AdapterDeclaration){
			return true;
		}
		if(toTest instanceof AdapterDeclaration){
			return true;
		}
		return false;
	}

}
