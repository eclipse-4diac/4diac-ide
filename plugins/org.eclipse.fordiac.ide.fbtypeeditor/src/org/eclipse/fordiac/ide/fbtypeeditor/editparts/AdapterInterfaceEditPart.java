/*******************************************************************************
 * Copyright (c) 2011, 2024 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
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
 *   Alois Zoitl - moved openEditor helper function to EditorUtils
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.DeleteInterfaceEditPolicy;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.draw2d.UnderlineAlphaLabel;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.SWT;

public class AdapterInterfaceEditPart extends InterfaceEditPart {

	private class AdapterInterfaceFigure extends UnderlineAlphaLabel {
		public AdapterInterfaceFigure() {
			setOpaque(false);
			setBorder(new ConnectorBorder(getCastedModel()));
			setText(getINamedElement().getName());
			if (isInput()) {
				setLabelAlignment(PositionConstants.LEFT);
				setTextAlignment(PositionConstants.LEFT);
			} else {
				setLabelAlignment(PositionConstants.RIGHT);
				setTextAlignment(PositionConstants.RIGHT);
			}
			setupMouseListener();
		}

		private void setupMouseListener() {
			addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseDragged(final MouseEvent me) {
					// Nothing to be done here
				}

				@Override
				public void mouseEntered(final MouseEvent me) {
					if (0 != (me.getState() & SWT.CONTROL)) {
						setDrawUnderline(true);
					}
				}

				@Override
				public void mouseExited(final MouseEvent me) {
					setDrawUnderline(false);
				}

				@Override
				public void mouseHover(final MouseEvent me) {
					// currently mouseHover should be the same as mouse moved
					mouseMoved(me);
				}

				@Override
				public void mouseMoved(final MouseEvent me) {
					if (0 != (me.getState() & SWT.CONTROL)) {
						if (!isDrawUnderline()) {
							setDrawUnderline(true);
						}
					} else if (isDrawUnderline()) {
						setDrawUnderline(false);
					}
				}
			});
		}
	}

	@Override
	protected IFigure createFigure() {
		final AdapterInterfaceFigure fig = new AdapterInterfaceFigure();
		fig.addAncestorListener(createAncestorListener());
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		if (isDirectEditable()) {
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy());
		}
		// allow delete of a FB
		if (isInterfaceEditable()) {
			installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteInterfaceEditPolicy());
		}
	}

	@Override
	public void refreshName() {
		((AdapterInterfaceFigure) getFigure()).setText(getINamedElement().getName());
		super.refreshName();
	}

}