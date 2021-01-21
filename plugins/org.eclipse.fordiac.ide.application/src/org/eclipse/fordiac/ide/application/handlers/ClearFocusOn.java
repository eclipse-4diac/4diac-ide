/*******************************************************************************
 * Copyright (c) 2011, 2016 Profactor GmbH, fortiss GmbH
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

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.gef.handlers.FordiacHandler;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ClearFocusOn extends FordiacHandler {
	private static final int NOT_TRANSPARENT = 255;

	/**
	 * the command has been executed, so extract the needed information from the
	 * application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart part = HandlerUtil.getActiveEditor(event);
		final GraphicalViewer viewer = getViewer(part);
		final Map<?, ?> map = viewer.getEditPartRegistry();
		for (final Entry<?, ?> entry : map.entrySet()) {
			final Object obj = entry.getKey();
			if (obj instanceof FB) {
				final Object editPartAsObject = entry.getValue();
				if (editPartAsObject instanceof AbstractViewEditPart) {
					((AbstractViewEditPart) editPartAsObject).setTransparency(NOT_TRANSPARENT);
				}
			} else if (obj instanceof Connection) {
				final Object editPartAsObject = entry.getValue();
				if (editPartAsObject instanceof ConnectionEditPart) {
					((ConnectionEditPart) editPartAsObject).setTransparency(NOT_TRANSPARENT);
				}
			}
		}
		return null;
	}
}
