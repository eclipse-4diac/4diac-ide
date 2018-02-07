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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

	private static final int HALF_TRANSPERENT = 50;
	private static final int NON_TRANSPARENT = 255;
	private FBEditPart selectedFB = null;
	IWorkbenchPart workbench = null;

	public FocusOnPredecessor() {
	}

	@Override
	public void run(IAction action) {
		if (selectedFB != null) {
			FB fb = selectedFB.getModel();
			Set<Object> elementToHighlight = new HashSet<>();
			elementToHighlight.add(fb);

			addPredecessorFBs(fb, elementToHighlight);

			if (workbench instanceof FBNetworkEditor) {
				GraphicalViewer viewer = ((FBNetworkEditor) workbench).getViewer();
				Map<?, ?> map = viewer.getEditPartRegistry();
				for (Entry<?, ?> entry : map.entrySet()) {
					Object obj = entry.getKey();
					Object editPartAsObject = entry.getValue();
					int transparency = (elementToHighlight.contains(obj)) ? NON_TRANSPARENT : HALF_TRANSPERENT;					
					if (editPartAsObject instanceof AbstractViewEditPart) {
						((AbstractViewEditPart) editPartAsObject).setTransparency(transparency);
					} else if (editPartAsObject instanceof ConnectionEditPart){
						((ConnectionEditPart) editPartAsObject).setTransparency(transparency);
					}					
				}
			}

		}
	}

	private void addPredecessorFBs(FB fb, Set<Object> elementToHighlight) {
		List<VarDeclaration> inputs = fb.getInterface().getInputVars();
		for (VarDeclaration varDeclaration : inputs) {
			for (Connection con : varDeclaration.getInputConnections()) {
				IInterfaceElement source = con.getSource();
				if (source != null) {
					EObject sourceContainer = source.eContainer();
					if (sourceContainer instanceof InterfaceList) {
						EObject sourceFBEObject = sourceContainer.eContainer();
						if (sourceFBEObject instanceof FB) {
							elementToHighlight.add(con);
							if (!elementToHighlight.contains(sourceFBEObject)) {
								elementToHighlight.add(sourceFBEObject);
								addPredecessorFBs((FB) sourceFBEObject, elementToHighlight);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if ((selection instanceof IStructuredSelection) && 
				(((IStructuredSelection) selection).getFirstElement() instanceof FBEditPart)) {
			selectedFB = (FBEditPart) ((IStructuredSelection) selection).getFirstElement();
		}

	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.workbench = targetPart;
	}

}
