/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ChangeGroupBoundsCommand extends Command {

	final Group group;
	final int dw;
	final int dh;
	int oldWidth;
	int newWidth;
	int oldHeight;
	int newHeight;
	final CompoundCommand updatePositions;

	public ChangeGroupBoundsCommand(final Group group, final int dx, final int dy, final int dw, final int dh) {
		this.group = group;
		this.dw = dw;
		this.dh = dh;
		this.updatePositions = createSetPosCommand(group, dx, dy);
	}

	@Override
	public void execute() {
		oldWidth = group.getWidth();
		oldHeight = group.getHeight();
		newWidth = oldWidth + dw;
		newHeight = oldHeight + dh;
		updateSize(newWidth, newHeight);
		if (updatePositions != null) {
			updatePositions.execute();
		}
	}

	@Override
	public void undo() {
		if (updatePositions != null) {
			updatePositions.undo();
		}
		updateSize(oldWidth, oldHeight);
	}

	@Override
	public void redo() {
		if (updatePositions != null) {
			updatePositions.redo();
		}
		updateSize(newWidth, newHeight);
	}

	private final CompoundCommand createSetPosCommand(final Group group, final int dx, final int dy) {
		if (dx != 0 || dy != 0) {
			final CompoundCommand cmd = new CompoundCommand();
			cmd.add(new SetPositionCommand(group, dx, dy));
			// ensure that the group elements stay at their position when the group grows or shrinks on the left/top
			// side
			// check if the lower right corner would be moved and adjust the position accordingly, this happens when the
			// min size of the group is reached and the group is just moved and resized
			final int effDx = ((dx + dw) != 0) ? -dw : dx;
			final int effDy = ((dy + dh) != 0) ? -dh : dy;
			group.getGroupElements().forEach(el -> cmd.add(new SetPositionCommand(el, -effDx, -effDy)));
			return cmd;
		}
		return null;
	}

	private void updateSize(final int width, final int height) {
		group.setWidth(width);
		group.setHeight(height);
	}

}
