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
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public abstract class AbstractChangeContainerBoundsCommand extends Command
		implements ConnectionLayoutTagger, ScopedCommand {

	private final Position newPos;
	private final double oldWidth;
	private final double oldHeight;
	private final double newWidth;
	private final double newHeight;
	private final PositionableElement target;
	private CompoundCommand updatePositions;

	public static AbstractChangeContainerBoundsCommand getCommandFor(final FBNetworkElement container,
			final Position newPos, final double newWidth, final double newHeight) {
		return switch (container) {
		case final Group group -> new ChangeGroupBoundsCommand(group, newPos, newWidth, newHeight);
		case final SubApp subApp -> new ChangeSubAppBoundsCommand(subApp, newPos, newWidth, newHeight);
		case final Comment comment -> new ChangeCommentBoundsCommand(comment, newPos, newWidth, newHeight);
		default -> null;
		};
	}

	protected AbstractChangeContainerBoundsCommand(final PositionableElement target, final Position newPos,
			final double newWidth, final double newHeight, final double oldWidth, final double oldHeight) {
		this.target = Objects.requireNonNull(target);
		this.newPos = newPos;
		this.newWidth = newWidth;
		this.newHeight = newHeight;
		this.oldWidth = oldWidth;
		this.oldHeight = oldHeight;
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

		final Position oldPos = target.getPosition();
		final double dx = newPos.getX() - oldPos.getX();
		final double dy = newPos.getY() - oldPos.getY();

		if (dx != 0 || dy != 0) {
			final CompoundCommand cmd = new CompoundCommand();
			cmd.add(new SetPositionCommand(target, newPos));
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