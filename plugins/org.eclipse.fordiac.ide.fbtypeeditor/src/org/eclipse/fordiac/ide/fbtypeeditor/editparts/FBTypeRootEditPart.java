/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Fixed adapter type list problem  
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDiagramEditPart;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.widgets.Display;

public class FBTypeRootEditPart extends AbstractDiagramEditPart {

	private final Map<IInterfaceElement, CommentTypeField> commentMapping = new HashMap<>();
	private EContentAdapter adapter;

	@Override
	protected IFigure createFigure() {
		IFigure figure = super.createFigure();
		// Create the static router for the connection layer
		ConnectionLayer connLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		connLayer.setConnectionRouter(new ShortestPathConnectionRouter(figure));
		return figure;
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(getContentAdapter());
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(getContentAdapter());
		}
	}

	public EContentAdapter getContentAdapter() {
		if (adapter == null) {
			adapter = new EContentAdapter() {
				@Override
				public void notifyChanged(final Notification notification) {
					int type = notification.getEventType();
					switch (type) {
					case Notification.ADD:
					case Notification.ADD_MANY:
					case Notification.REMOVE:
					case Notification.REMOVE_MANY:
						Display.getDefault().asyncExec(() -> refreshChildren());
						break;
					case Notification.SET:
						break;
					default:
						break;
					}
				}
			};
		}
		return adapter;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
	}

	@Override
	public FBType getModel() {
		return (FBType) super.getModel();
	}

	public CommentTypeField getCommentField(IInterfaceElement element) {
		return commentMapping.get(element);
	}

	@Override
	protected List<?> getModelChildren() {
		ArrayList<Object> children = new ArrayList<>();
		children.add(getModel());

		getModel().getInterfaceList().getAllInterfaceElements().forEach(interfaceElement -> {
			CommentTypeField field = new CommentTypeField(interfaceElement);
			children.add(field);
			commentMapping.put(interfaceElement, field);
		});
		return children;
	}
}
