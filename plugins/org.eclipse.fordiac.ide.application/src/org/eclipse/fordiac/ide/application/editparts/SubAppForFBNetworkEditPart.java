/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, AIT, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Filip Andren, Alois Zoitl, Monika Wenger 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.Iterator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.actions.OpenSubApplicationEditorAction;
import org.eclipse.fordiac.ide.application.figures.AbstractFBNetworkElementFigure;
import org.eclipse.fordiac.ide.application.figures.SubAppForFbNetworkFigure;
import org.eclipse.fordiac.ide.application.policies.FBAddToSubAppLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.ZoomManager;

//TODO model refactoring - consder inheriting from fbeditpart when model refatoring is finished
public class SubAppForFBNetworkEditPart extends AbstractFBNElementEditPart {
		
	public SubAppForFBNetworkEditPart(final ZoomManager zoomManager) {
		super(zoomManager);
	}
	
	@Override
	protected IFigure createFigureForModel() {
		return new SubAppForFbNetworkFigure(getModel(), this);
	}

	@Override
	public SubApp getModel() {
		return (SubApp) super.getModel();
	}

	@Override
	public EContentAdapter createContentAdapter() {
		return new EContentAdapter() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getFBNetworkElement_Interface().equals(feature) ||
						LibraryElementPackage.eINSTANCE.getInterfaceList_EventInputs().equals(feature)||
						LibraryElementPackage.eINSTANCE.getInterfaceList_EventOutputs().equals(feature)||
						LibraryElementPackage.eINSTANCE.getInterfaceList_InputVars().equals(feature)||
						LibraryElementPackage.eINSTANCE.getInterfaceList_OutputVars().equals(feature)||
						LibraryElementPackage.eINSTANCE.getInterfaceList_Plugs().equals(feature)||
						LibraryElementPackage.eINSTANCE.getInterfaceList_Sockets().equals(feature)) {
					refresh();
				} else if (LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)) {
					refresh();
				} else if (notification.getFeature() == LibraryElementPackage.eINSTANCE.getFBNetworkElement_Mapping() && 
						notification.getNewValue() instanceof FB) {
					updateDeviceListener();
				}
				refreshToolTip();
				backgroundColorChanged(getFigure());
			}
		};
	}

	@Override
	public void refresh() {
		super.refresh();
		refreshInterfaceLabels();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();			
		// Add policy to handle drag&drop of fbs
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBAddToSubAppLayoutEditPolicy());
	}

	private void refreshInterfaceLabels() {
		for (Iterator<?> iterator = getChildren().iterator(); iterator.hasNext();) {
			EditPart ep = (EditPart) iterator.next();
			if (ep instanceof UntypedSubAppInterfaceElementEditPart) {
				((UntypedSubAppInterfaceElementEditPart) ep).refreshName();
			}
		}
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType().equals(RequestConstants.REQ_OPEN)) {	
			if(null != getModel().getPaletteEntry()){
				// we have a type open the sub-app type editor
				AbstractFBNetworkElementFigure.openTypeInEditor(getModel());
			} else {
				SubApp subApp = getModel();
				if(null == subApp.getSubAppNetwork() && subApp.isMapped()) {
					//we are mapped and the subapp in the resource
					subApp = (SubApp)subApp.getOpposite();
				}				
				new OpenSubApplicationEditorAction(subApp).run();
			}					
		} else {
			super.performRequest(request);
		}
	}
}
