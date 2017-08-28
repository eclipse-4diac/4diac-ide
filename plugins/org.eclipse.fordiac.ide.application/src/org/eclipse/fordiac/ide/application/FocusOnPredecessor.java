/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class FocusOnPredecessor implements IObjectActionDelegate {

	private FBEditPart selectedFB = null;
	IWorkbenchPart workbench = null;
	
	public FocusOnPredecessor() {
	}

	@Override
	public void run(IAction action) {
		if (selectedFB != null) {
			FB fb = selectedFB.getModel();
			ArrayList<FB> fbsToHighlight = new ArrayList<>();
			ArrayList<Connection> connectionsToHighlight = new ArrayList<>();
			fbsToHighlight.add(fb);

			addPredecessorFBs(fb, fbsToHighlight, connectionsToHighlight);
			
			if (workbench instanceof FBNetworkEditor) {
				GraphicalViewer viewer = ((FBNetworkEditor) workbench).getViewer();
				Map<?, ?> map = viewer.getEditPartRegistry();
				for (Object obj : map.keySet()) {
					Object editPartAsObject = map.get(obj);
					if (obj instanceof Connection) {
						if (connectionsToHighlight.contains(obj)) {
							if (editPartAsObject instanceof ConnectionEditPart) {
								// if previously the transparency was set to a value lower than 255
								((ConnectionEditPart) editPartAsObject).setTransparency(255);
							}
						} else {
							if (editPartAsObject instanceof ConnectionEditPart) {
								((ConnectionEditPart) editPartAsObject).setTransparency(50);
							}
						}
					}
					if (obj instanceof FB) {
						if (editPartAsObject != null && fbsToHighlight.contains(obj)) {
							if (editPartAsObject instanceof AbstractViewEditPart) {
								// if previously the transparency was set to a value lower than 255
								((AbstractViewEditPart) editPartAsObject).setTransparency(255);
							}
						} else {
							if (editPartAsObject instanceof AbstractViewEditPart) {
								((AbstractViewEditPart) editPartAsObject).setTransparency(50);
							}
						}
					}
				}
			}
			
		}
	}

	private void addPredecessorFBs(FB fb, ArrayList<FB> fbsToHighlight, ArrayList<Connection> connectionsToHighlight) {
		List<VarDeclaration> inputs = fb.getInterface().getInputVars();
		for (VarDeclaration varDeclaration : inputs) {
			for (Connection con : varDeclaration.getInputConnections()) {
				IInterfaceElement source = con.getSource();
				if (source != null) {
					EObject sourceContainer = source.eContainer();
					if (sourceContainer instanceof InterfaceList) {
						EObject sourceFBEObject = sourceContainer.eContainer();
						if (sourceFBEObject instanceof FB) {
							connectionsToHighlight.add(con);
							if (!fbsToHighlight.contains(sourceFBEObject)) {
								fbsToHighlight.add((FB)sourceFBEObject);
								addPredecessorFBs((FB)sourceFBEObject, fbsToHighlight, connectionsToHighlight);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			if (((IStructuredSelection) selection).getFirstElement() instanceof FBEditPart) {
				selectedFB  = (FBEditPart)((IStructuredSelection) selection).getFirstElement();
			}
		}

	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.workbench = targetPart;
	}

}
