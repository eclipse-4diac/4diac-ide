/*******************************************************************************
 * Copyright (c) 2020, 2021 Johannes Kepler University Linz
 *                          Primetals Technologies Austria GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Muddasir Shakil - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - reworked for Breadcrumb Editor
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class UnmapAllHandler extends UnmapHandler {
	@Override
	protected List<?> getSelectedElements(Object evaluationContext) {
		final IEditorPart editor = (IEditorPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		if (editor != null) { // check needed for command quick search
			final FBNetwork fbnetwork = editor.getAdapter(FBNetwork.class);
			if (null != fbnetwork) {
				return fbnetwork.getNetworkElements();
			}
		}
		return Collections.emptyList();
	}
}
