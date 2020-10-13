/*******************************************************************************
 * Copyright (c) 2014 -2106 fortiss GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.ui.properties;

import org.eclipse.fordiac.ide.gef.properties.TypeInfoSection;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.navigator.CommonNavigator;

public class SystemInfoSection extends TypeInfoSection {

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		if ((part instanceof CommonNavigator) && (input instanceof AutomationSystem)) {
			return ((AutomationSystem) input).getCommandStack();
		}
		return null;
	}

	@Override
	protected LibraryElement getInputType(Object input) {
		if (input instanceof AutomationSystem) {
			return (AutomationSystem) input;
		}
		return null;
	}

}
