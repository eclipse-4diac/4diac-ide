/*******************************************************************************
 * Copyright (c) 2014 - 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeContentOutline;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeEditor;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.contentoutline.ContentOutline;

public abstract class AbstractServiceSection extends AbstractSection {
	protected CommandStack getCommandStack(IWorkbenchPart part) {
		if(part instanceof FBTypeEditor){
			return ((FBTypeEditor)part).getCommandStack();
		}
		if(part instanceof ContentOutline){
			return ((FBTypeContentOutline) ((ContentOutline)part).getCurrentPage()).getCommandStack();
		}
		return null;
	}
	
	protected boolean isLeftInterface(Primitive primitive){
		if(null != primitive){
			Service service = (Service) primitive.eContainer().eContainer().eContainer();
			if(primitive.getInterface().equals(service.getLeftInterface())){
				return true;
			}
		}
		return false;
	}
}
