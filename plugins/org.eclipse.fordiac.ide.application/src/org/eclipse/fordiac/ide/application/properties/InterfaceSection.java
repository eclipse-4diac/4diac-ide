/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.properties.AbstractInterfaceSection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;

public class InterfaceSection extends AbstractInterfaceSection {

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		if (part instanceof DiagramEditorWithFlyoutPalette) {
			return ((DiagramEditorWithFlyoutPalette) part).getCommandStack();
		}
		return null;
	}

	@Override
	protected FBNetworkElement getInputType(Object input) {
		if (input instanceof AbstractFBNElementEditPart) {
			return ((AbstractFBNElementEditPart) input).getModel();
		} else if (input instanceof FBNetworkElement) {
			return (FBNetworkElement) input;
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
