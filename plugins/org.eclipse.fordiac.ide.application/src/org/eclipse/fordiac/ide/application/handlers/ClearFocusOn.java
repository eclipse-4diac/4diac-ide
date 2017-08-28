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
			for (Object obj : map.keySet()) {
				if (obj instanceof FB) {
					Object editPartAsObject = map.get(obj);
					if (editPartAsObject instanceof AbstractViewEditPart) {
						((AbstractViewEditPart) editPartAsObject).setTransparency(255);
					}
				} else if (obj instanceof Connection) {
					Object editPartAsObject = map.get(obj);
					if (editPartAsObject instanceof ConnectionEditPart) {
						((ConnectionEditPart) editPartAsObject).setTransparency(255);
					}
				}
			}
		}
		return null;
	}
}
