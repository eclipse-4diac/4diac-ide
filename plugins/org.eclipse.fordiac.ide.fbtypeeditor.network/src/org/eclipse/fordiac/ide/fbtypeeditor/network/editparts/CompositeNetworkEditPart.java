/*******************************************************************************
 * Copyright (c) 2008 - 2012, 2014, 2016, 2017  Profactor GmbH, fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
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
 *   Alois Zoitl - Moved code to common base class
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.editparts;

import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

/**
 * Edit Part for the visualization of Composite Networks.
 */
public class CompositeNetworkEditPart extends EditorWithInterfaceEditPart {

	/** The adapter. */
	private Adapter adapter;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(getContentAdapter());
			getModel().eContainer().eAdapters().add(getContentAdapter());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(getContentAdapter());
			getModel().eContainer().eAdapters().remove(getContentAdapter());
		}
	}

	/**
	 * Gets the content adapter.
	 *
	 * @return the content adapter
	 */
	public Adapter getContentAdapter() {
		if (null == adapter) {
			adapter = new EContentAdapter() {
				@Override
				public void notifyChanged(final Notification notification) {
					super.notifyChanged(notification);
					// if the notification is from the out_mapped In Out vars we are in the middle
					// of an in out var change, ignore it!
					if (notification.getFeatureID(
							InterfaceList.class) != LibraryElementPackage.INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS) {
						switch (notification.getEventType()) {
						case Notification.ADD, Notification.ADD_MANY:
							refreshChildren();
							break;
						case Notification.MOVE:
							if (notification.getNewValue() instanceof IInterfaceElement) {
								refreshChildren();
							}
							break;
						case Notification.REMOVE, Notification.REMOVE_MANY:
							refreshChildren();
							break;
						default:
							break;
						}
					}
				}
			};
		}
		return adapter;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<?> getModelChildren() {
		final List<Object> modelChildren = (List<Object>) super.getModelChildren();
		if (getModel() != null) {
			modelChildren.addAll(getInterfaceList().getInOutVars());
		}
		return modelChildren;
	}

	/**
	 * Creates the EditPolicies used for this EditPart.
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// // handles constraint changes of model elements and creation of new
		// // model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new CompositeFBNetworkLayoutEditPolicy());

	}

	private CompositeFBType getFbType() {
		return (CompositeFBType) getModel().eContainer();
	}

	@Override
	protected boolean isVarVisible(final EditPart childEditPart) {
		return !(childEditPart.getModel() instanceof AdapterDeclaration);
	}

	@Override
	protected InterfaceList getInterfaceList() {
		return getFbType().getInterfaceList();
	}
}
