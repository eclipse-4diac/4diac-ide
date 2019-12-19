/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added diagram font preference
 *               - extracted common FB shape for interface and fbn editors
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.figures;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.fordiac.ide.gef.figures.FBShape;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.gef.editparts.ZoomManager;

public class FBTypeFigure extends FBShape {

	private Label versionInfoLabel;
	private FBType type;

	public FBTypeFigure(final FBType type, ZoomManager zoomManager) {
		super(type, zoomManager);
		this.type = type;

		versionInfoLabel = new Label();
		updateVersionInfoLabel();
		getMiddle().add(versionInfoLabel);
		versionInfoLabel.setTextAlignment(PositionConstants.CENTER);
		getMiddle().setConstraint(versionInfoLabel,
				new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));

	}

	public final void updateVersionInfoLabel() {
		VersionInfo versionInfo = null;
		if (!type.getVersionInfo().isEmpty()) {
			versionInfo = type.getVersionInfo().get(type.getVersionInfo().size() - 1);
		}
		versionInfoLabel.setText(versionInfo != null ? versionInfo.getVersion() : "N/D");
	}
}
