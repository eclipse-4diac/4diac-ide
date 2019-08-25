/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Monika Wenger - initial API and implementation and/or initial documentation
 *    Bianca Wiesmayr - moved FBInfoGroup to ResourceSection (only used there)
 * *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractDevResInterfaceSection extends AbstractInterfaceSection {
	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		if (part instanceof DiagramEditorWithFlyoutPalette) {
			return ((DiagramEditorWithFlyoutPalette) part).getCommandStack();
		}
		return null;
	}

	@Override
	protected void setInputInit() {
		// currently nothing to do here
	}

	@Override
	protected void setInputCode() {
		// currently nothing to do here
	}
}
