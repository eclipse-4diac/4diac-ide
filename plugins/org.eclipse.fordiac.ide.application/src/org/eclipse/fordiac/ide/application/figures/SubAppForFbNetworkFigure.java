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
 *               - extracted common FB shape for interface and fbn editors
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.edit.providers.ResultListLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;

/**
 * The Class SubAppForFbNetworkFigure.
 */
public class SubAppForFbNetworkFigure extends FBNetworkElementFigure {

	public SubAppForFbNetworkFigure(final SubApp model, final SubAppForFBNetworkEditPart editPart) {
		super(model, editPart);
		updateTypeLabel(model);
	}

	public void updateTypeLabel(final SubApp model) {
		getTypeLabel().setText(model.isTyped() ? model.getTypeName() : ""); //$NON-NLS-1$
		if (!model.isTyped()) {
			getTypeLabel().setIcon(FordiacImage.ICON_SUB_APP.getImage());
		} else {
			getTypeLabel().setIcon(ResultListLabelProvider.getTypeImage(model.getType()));
		}
	}

}
