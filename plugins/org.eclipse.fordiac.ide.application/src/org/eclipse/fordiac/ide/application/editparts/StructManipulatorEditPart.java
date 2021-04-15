/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;

public abstract class StructManipulatorEditPart extends AbstractFBNElementEditPart {
	public StructManipulatorEditPart() {
		super();
	}

	@Override
	protected IFigure createFigureForModel() {
		return new FBNetworkElementFigure(getModel(), this);
	}

	@Override
	public StructManipulator getModel() {
		return (StructManipulator) super.getModel();
	}

	@Override
	protected Adapter createInterfaceAdapter() {
		return new EContentAdapter() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				switch (notification.getEventType()) {
				case Notification.ADD:
				case Notification.ADD_MANY:
				case Notification.MOVE:
				case Notification.REMOVE:
					refresh();
					break;
				default:
					break;
				}
			}
		};
	}

}
