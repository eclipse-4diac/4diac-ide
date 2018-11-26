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
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDiagramEditPart;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
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
			((Notifier) getModel()).eAdapters().add(getContentAdapter());
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((Notifier) getModel()).eAdapters().remove(getContentAdapter());
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
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								refreshChildren();
							}
						});
						break;
					case Notification.SET:
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

	public FBType getCastedFBTypeModel() {
		return (FBType) getModel();
	}


	public CommentTypeField getCommentField(IInterfaceElement element) {
		return commentMapping.get(element);
	}

	@Override
	protected List<?> getModelChildren() {
		ArrayList<Object> children = new ArrayList<Object>();
		children.add(getCastedFBTypeModel());
		InterfaceList il = getCastedFBTypeModel().getInterfaceList();
		if (il != null) {
			for (Iterator<VarDeclaration> iterator = il.getInputVars().iterator(); iterator.hasNext();) {
				VarDeclaration varDecl =  iterator.next();
				CommentTypeField field = new CommentTypeField(varDecl);
				children.add(field);
				commentMapping.put(varDecl, field);
			}
			for (Iterator<Event> iterator = il.getEventInputs().iterator(); iterator
					.hasNext();) {
				Event varDecl =  iterator.next();
				CommentTypeField field = new CommentTypeField(varDecl);
				children.add(field);
				commentMapping.put(varDecl, field);
			}
			for (Iterator<VarDeclaration> iterator = il.getOutputVars().iterator(); iterator
					.hasNext();) {
				VarDeclaration varDecl =  iterator.next();
				CommentTypeField field = new CommentTypeField(varDecl);
				children.add(field);
				commentMapping.put(varDecl, field);
			}
			for (Iterator<Event> iterator = il.getEventOutputs().iterator(); iterator
					.hasNext();) {
				Event varDecl = iterator.next();
				CommentTypeField field = new CommentTypeField(varDecl);
				children.add(field);
				commentMapping.put(varDecl, field);
			}
		}
		return children;
	}
}
