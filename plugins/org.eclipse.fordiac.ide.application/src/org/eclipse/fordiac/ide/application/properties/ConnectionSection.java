/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alois Zoitl, Monika Wenger - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;

public class ConnectionSection extends org.eclipse.fordiac.ide.gef.properties.ConnectionSection {

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		if(part instanceof DiagramEditorWithFlyoutPalette){
			return ((DiagramEditorWithFlyoutPalette)part).getCommandStack();
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
	protected void setInputCode() {}

	@Override
	protected void setInputInit() {
	}
}
