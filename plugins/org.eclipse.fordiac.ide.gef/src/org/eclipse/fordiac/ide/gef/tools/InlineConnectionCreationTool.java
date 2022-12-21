/*******************************************************************************
 * Copyright (c) 2020, 2022 Primetals Technologies Germany GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - extracted from FBNetworkPanningSelectionTool to be used for
 *                 connection duplication as well
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.tools;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.swt.events.MouseEvent;

public final class InlineConnectionCreationTool extends FordiacConnectionDragCreationTool {

	private static final int LEFT_MOUSE = 1;

	private final EditPart sourcePart;
	private EditPart lastConnTarget;
	private boolean startup = true;

	public static InlineConnectionCreationTool createInlineConnCreationTool(final EditPart originalSource,
			final EditDomain editDomain, final EditPartViewer editPartViewer, final Point location) {
		final InlineConnectionCreationTool connectionCreationTool = new InlineConnectionCreationTool(originalSource);
		connectionCreationTool.setViewer(editPartViewer);
		connectionCreationTool.setEditDomain(editDomain);
		connectionCreationTool.startup(location);
		return connectionCreationTool;
	}

	private InlineConnectionCreationTool(final EditPart sourcePart) {
		super();
		this.sourcePart = sourcePart;
	}

	private void startup(final Point point) {
		startup = true;
		activate();
		super.handleButtonDown(LEFT_MOUSE);
		startup = false;
		handleDragStarted();
		getCurrentInput().setMouseLocation(point.x, point.y);
		handleMove();
	}

	@Override
	public void mouseUp(final MouseEvent me, final EditPartViewer viewer) {
		super.mouseUp(me, viewer);
		lastConnTarget = getTargetEditPart();
		startup(new Point(me.x, me.y));
	}

	@Override
	protected EditPart getTargetEditPart() {
		if (startup) {
			return sourcePart;
		}
		EditPart part = super.getTargetEditPart();
		if ((null != part) && (part.equals(lastConnTarget))) {
			// don't use the last part
			part = null;
		}
		return part;
	}

}