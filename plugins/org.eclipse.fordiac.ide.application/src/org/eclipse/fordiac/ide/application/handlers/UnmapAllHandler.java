/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Muddasir Shakil - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class UnmapAllHandler extends UnmapHandler {
	@Override
	protected List<?> getSelectedElements(Object evaluationContext){
		IWorkbenchPart part = (IWorkbenchPart) HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_EDITOR_NAME);
		if (part instanceof FBNetworkEditor) {
			return	((FBNetworkEditor) part).getModel().getNetworkElements();
		}
		return Collections.emptyList();
	}
}

