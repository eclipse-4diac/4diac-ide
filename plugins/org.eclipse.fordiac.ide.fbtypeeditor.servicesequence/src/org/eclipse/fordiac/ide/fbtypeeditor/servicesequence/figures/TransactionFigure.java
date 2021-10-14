/*******************************************************************************
 * Copyright (c) 2008 - 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
 *   Melanie Winter - extracted from TransactionEditPart, updated layout
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.ServiceConstants;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedLineBorder;
import org.eclipse.fordiac.ide.util.ColorManager;
import org.eclipse.swt.SWT;

public final class TransactionFigure extends Figure {
	public TransactionFigure() {
		final FlowLayout layout = new FlowLayout();
		final AdvancedLineBorder border = new AdvancedLineBorder(PositionConstants.NORTH, SWT.LINE_DASH);
		border.setColor(ColorManager.getColor(ServiceConstants.TEXT_BLUE));
		setBorder(border);
		setLayoutManager(layout);
	}
}