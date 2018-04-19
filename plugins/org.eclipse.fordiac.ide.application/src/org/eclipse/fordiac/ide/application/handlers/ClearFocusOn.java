/*******************************************************************************
 * Copyright (c) 2011, 2016 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 */
public class ClearFocusOn extends AbstractHandler {
	private static final int NOT_TRANSPARENT = 255;

	/**
	 * The constructor.
	 */
	public ClearFocusOn() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart part = HandlerUtil.getActiveEditor(event);
		if (part instanceof FBNetworkEditor) {
			GraphicalViewer viewer = ((FBNetworkEditor) part).getViewer();
			Map<?,?> map = viewer.getEditPartRegistry();
			for (Entry<?, ?> entry  : map.entrySet()) {
				Object obj = entry.getKey();
				if (obj instanceof FB) {
					Object editPartAsObject = entry.getValue();
					if (editPartAsObject instanceof AbstractViewEditPart) {
						((AbstractViewEditPart) editPartAsObject).setTransparency(NOT_TRANSPARENT);
					}
				} else if (obj instanceof Connection) {
					Object editPartAsObject = entry.getValue();
					if (editPartAsObject instanceof ConnectionEditPart) {
						((ConnectionEditPart) editPartAsObject).setTransparency(NOT_TRANSPARENT);
					}
				}
			}
		}
		return null;
	}
}
