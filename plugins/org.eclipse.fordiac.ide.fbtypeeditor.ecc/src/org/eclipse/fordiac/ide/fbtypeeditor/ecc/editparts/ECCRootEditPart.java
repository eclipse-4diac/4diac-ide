/*******************************************************************************
 * Copyright (c) 2008 - 2013 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.ECCXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDiagramEditPart;
import org.eclipse.fordiac.ide.gef.tools.FordiacViewportAutoexposeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.AutoexposeHelper;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

/**
 * The Class ECCRootEditPart.
 */
public class ECCRootEditPart extends AbstractDiagramEditPart {

	/** The adapter. */
	private Adapter adapter;

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			((Notifier) getModel()).eAdapters().add(getContentAdapter());
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
			((Notifier) getModel()).eAdapters().remove(getContentAdapter());
		}
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == AutoexposeHelper.class) {
			return key.cast(new FordiacViewportAutoexposeHelper(this));
		}
		return super.getAdapter(key);
	}

	/**
	 * Gets the content adapter.
	 *
	 * @return the content adapter
	 */
	public Adapter getContentAdapter() {
		if (null == adapter) {
			adapter = new AdapterImpl() {
				@Override
				public void notifyChanged(final Notification notification) {
					final int type = notification.getEventType();
					switch (type) {
					case Notification.ADD:
					case Notification.ADD_MANY:
					case Notification.REMOVE:
					case Notification.REMOVE_MANY:
						refreshChildren();
						break;
					default:
						break;
					}
				}
			};
		}
		return adapter;
	}

	/**
	 * Creates the EditPolicies used for this EditPart.
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes of model elements and creation of new
		// model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ECCXYLayoutEditPolicy());

	}

	/**
	 * returns the model object as <code>ECC</code>.
	 *
	 * @return ECC to be visualized
	 */
	public ECC getCastedECCModel() {
		return (ECC) getModel();
	}

	/**
	 * Returns the children of the FBNetwork.
	 *
	 * @return the list of children s
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<?> getModelChildren() {
		final List<ECState> temp = new ArrayList<>();
		temp.addAll(getCastedECCModel().getECState());
		return temp;
	}

}
