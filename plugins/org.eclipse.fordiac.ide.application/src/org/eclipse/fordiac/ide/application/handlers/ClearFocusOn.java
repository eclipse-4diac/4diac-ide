/*******************************************************************************
 * Copyright (c) 2011, 2024 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ClearFocusOn extends AbstractHandler {
	private static final int NOT_TRANSPARENT = 255;

	/**
	 * the command has been executed, so extract the needed information from the
	 * application context.
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart part = HandlerUtil.getActiveEditor(event);
		final GraphicalViewer viewer = HandlerHelper.getViewer(part);
		final Map<Object, EditPart> map = viewer.getEditPartRegistry();
		for (final Entry<?, ?> entry : map.entrySet()) {
			final Object obj = entry.getKey();
			if (obj instanceof FB) {
				if (entry.getValue() instanceof final AbstractViewEditPart viewEP) {
					viewEP.setTransparency(NOT_TRANSPARENT);
				}
			} else if ((obj instanceof Connection) && (entry.getValue() instanceof final ConnectionEditPart connEP)) {
				connEP.setTransparency(NOT_TRANSPARENT);
			}
		}
		return null;
	}
}
