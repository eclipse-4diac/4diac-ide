/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.Event;

public class EventValueEditPart extends AbstractDebugInterfaceValueEditPart {

	@Override
	public EventValueEntity getModel() {
		return (EventValueEntity) super.getModel();
	}

	@Override
	protected Event getInterfaceElement() {
		return getModel().getEvent();
	}

	public void update() {
		if (isActive() && getFigure() != null) {
			getLabelFigure().setText(getModel().getCount().toString());
			refreshVisuals();
		}
	}

}
