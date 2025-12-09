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
 * 	Alois Zoitl - initial API and implementation and/or initial documentation
 * *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.ConnectionLayoutTagger;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public abstract class AbstractChangeContainerBoundsCommand extends Command
		implements ConnectionLayoutTagger, ScopedCommand {

	private final int dx;
	private final int dy;
	private final double oldWidth;
	private final double oldHeight;
	private final double newWidth;
	private final double newHeight;
	private final PositionableElement target;
	private CompoundCommand updatePositions;

	protected AbstractChangeContainerBoundsCommand(final PositionableElement target, final int dx, final int dy,
			final int dw, final int dh, final double oldWidth, final double oldHeight) {
		this.target = Objects.requireNonNull(target);
		this.dx = dx;
		this.dy = dy;
		this.oldWidth = oldWidth;
		this.oldHeight = oldHeight;
		newWidth = calcNewSize(dx, dw, oldWidth);
		newHeight = calcNewSize(dy, dh, oldHeight);
	}

	private static double calcNewSize(final int deltaPos, int deltaSize, final double oldSize) {
		if (deltaSize == 0) {
			return oldSize;
		}
		if (deltaPos == 0) {
			// snap to grid gives us always one pixel to much in size
			deltaSize--;
		}
		// use screen coordinates for the new size calculation to reduce rounding
		// artefact issues
		return CoordinateConverter.INSTANCE
				.screenToIEC61499(CoordinateConverter.INSTANCE.iec61499ToScreen(oldSize) + deltaSize);
	}

	@Override
	public void execute() {
		updatePositions = createSetPosCommand();
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

	public PositionableElement getTarget() {
		return target;
	}

	private CompoundCommand createSetPosCommand() {
		if (dx != 0 || dy != 0) {
			final CompoundCommand cmd = new CompoundCommand();
			cmd.add(new SetPositionCommand(target, dx, dy));
			// Ensure that the children stay at their position when the group grows or
			// shrinks on the left/top side. If the child is in a group we must only
			// consider it if the group the child is contained in itself is changed.
			getChildren().stream().filter(el -> !el.isInGroup() || target.equals(el.getGroup()))
					.forEach(el -> cmd.add(new SetPositionCommand(el, -dx, -dy)));
			return cmd;
		}
		return null;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(target);
	}

	protected abstract void updateSize(double width, double height);

	protected abstract List<FBNetworkElement> getChildren();
}