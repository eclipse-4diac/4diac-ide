/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.swt.graphics.Color;

/**
 * The Class SubAppForFbNetworkFigure.
 */
public class SubAppForFbNetworkFigure extends FBNetworkElementFigure {

	public SubAppForFbNetworkFigure(SubApp model, SubAppForFBNetworkEditPart editPart) {
		super(model, editPart);
	}

	@Override
	protected void setupTypeNameAndVersion(FBNetworkElement model, Figure container, Color borderColor) {
		super.setupTypeNameAndVersion(model, container, borderColor);
		updateTypeLabel((SubApp) model);
	}

	public void updateTypeLabel(SubApp model) {
		getTypeLabel().setText(null != model.getType() ? model.getTypeName() : ""); //$NON-NLS-1$
	}
}
