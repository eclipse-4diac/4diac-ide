/*******************************************************************************
 * Copyright (c) 2009, 2011, 2013 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.application.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.draw2d.Connection;
import org.eclipse.fordiac.ide.gef.router.MoveableRouter;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class RerouteAction implements IObjectActionDelegate {

	ArrayList<Connection> connections = new ArrayList<Connection>();

	public RerouteAction() {
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	@Override
	public void run(IAction action) {
		for (Connection conn : connections) {
			if (conn.getConnectionRouter() instanceof MoveableRouter) {
				((MoveableRouter) conn.getConnectionRouter()).setDeltaX1(conn, 0);
				((MoveableRouter) conn.getConnectionRouter()).setDeltaX2(conn, 0);
				((MoveableRouter) conn.getConnectionRouter()).setDeltaY(conn, 0);
			}
			conn.revalidate();
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		connections.clear();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			for (Iterator iterator = structuredSelection.iterator(); iterator
					.hasNext();) {
				Object obj = iterator.next();
				if (obj instanceof ConnectionEditPart) {
					connections.add((Connection) ((ConnectionEditPart) obj).getFigure());
				}
			}
		}
	}
}
