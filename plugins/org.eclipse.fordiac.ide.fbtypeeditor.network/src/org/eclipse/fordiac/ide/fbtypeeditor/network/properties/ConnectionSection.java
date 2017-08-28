/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.network.properties;

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeContentOutline;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeEditor;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.contentoutline.ContentOutline;

public class ConnectionSection extends org.eclipse.fordiac.ide.gef.properties.ConnectionSection {

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		if(part instanceof FBTypeEditor){
			return ((FBTypeEditor)part).getCommandStack();
		}
		if(part instanceof ContentOutline){
			return ((FBTypeContentOutline) ((ContentOutline)part).getCurrentPage()).getCommandStack();
		}
		return null;
	}

	@Override
	protected Connection getInputType(Object input) {
		if(input instanceof ConnectionEditPart){
			return ((ConnectionEditPart) input).getModel();
		}
		return null;
	}

	@Override
	protected void setInputCode() {
	}

	@Override
	protected void setInputInit() {
	}
}
