/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *   Alois Zoitl - Fixed adapter type list problem
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDiagramEditPart;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.widgets.Display;

public class FBTypeRootEditPart extends AbstractDiagramEditPart {

	private Adapter adapter;

	@Override
	protected ConnectionRouter createConnectionRouter(final IFigure figure) {
		return new ShortestPathConnectionRouter(figure);
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

	public Adapter getContentAdapter() {
		if (null == adapter) {
			adapter = new EContentAdapter() {
				@Override
				public void notifyChanged(final Notification notification) {
					super.notifyChanged(notification);
					final int type = notification.getEventType();
					switch (type) {
					case Notification.ADD:
					case Notification.ADD_MANY:
					case Notification.REMOVE:
					case Notification.REMOVE_MANY:
						Display.getDefault().asyncExec(FBTypeRootEditPart.this::refreshChildren);
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

	@Override
	protected List<?> getModelChildren() {
		final ArrayList<Object> children = new ArrayList<>();
		children.add(getModel());

		getModel().getInterfaceList().getAllInterfaceElements().forEach(interfaceElement -> {
			final CommentTypeField field = new CommentTypeField(interfaceElement);
			children.add(field);
		});
		return children;
	}
}
