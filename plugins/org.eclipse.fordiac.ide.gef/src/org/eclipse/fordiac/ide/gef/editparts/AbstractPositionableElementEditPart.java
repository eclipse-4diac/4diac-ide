/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.gef.GraphicalEditPart;

public abstract class AbstractPositionableElementEditPart extends AbstractViewEditPart {

	private final Adapter contentAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getPositionableElement_X().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getPositionableElement_Y().equals(feature)) {
				refreshPosition();
			}
		}

	};

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshPosition();
	}

	protected void refreshPosition() {
		final Rectangle bounds = new Rectangle(getPositionableElement().getX(), getPositionableElement().getY(), -1, -1);
		if (getParent() != null) {
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	protected abstract PositionableElement getPositionableElement();

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			final PositionableElement posElement = getPositionableElement();
			if (null != posElement) {
				posElement.eAdapters().add(contentAdapter);
			}
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			final PositionableElement posElement = getPositionableElement();
			if (null != posElement) {
				posElement.eAdapters().remove(contentAdapter);
			}
		}
	}

}
