/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.commands.Command;

public class RemoveElementsFromGroup extends Command {

	private final Group sourceGroup;
	private final Point offset;
	private final List<FBNetworkElement> elements;


	public RemoveElementsFromGroup(final Collection<FBNetworkElement> elements, final Point offset) {
		this.elements = new ArrayList<>(elements);
		this.sourceGroup = getGroup(this.elements);
		this.offset = offset;
	}

	public RemoveElementsFromGroup(final Collection<FBNetworkElement> elements) {
		this.elements = new ArrayList<>(elements);
		this.sourceGroup = getGroup(this.elements);
		this.offset = getOffsetFromGroup(sourceGroup);
	}

	@Override
	public boolean canExecute() {
		return (null != sourceGroup) && allElementsFromSameGroup();
	}

	@Override
	public void execute() {
		performRemove();
	}
	@Override
	public void undo() {
		elements.forEach(el -> el.setGroup(sourceGroup));
		FBNetworkHelper.moveFBNetworkByOffset(elements, -offset.x, -offset.y);
	}

	@Override
	public void redo() {
		performRemove();
	}

	private void performRemove() {
		elements.forEach(el -> el.setGroup(null));
		FBNetworkHelper.moveFBNetworkByOffset(elements, offset.x, offset.y);
	}

	private static Group getGroup(final List<FBNetworkElement> elements) {
		if (!elements.isEmpty()) {
			return elements.get(0).getGroup();
		}
		return null;
	}

	private boolean allElementsFromSameGroup() {
		return elements.stream().allMatch(el -> sourceGroup.equals(el.getGroup()));
	}

	private static Point getOffsetFromGroup(final Group sourceGroup) {
		if (sourceGroup != null) {
			final Position position = sourceGroup.getPosition();
			return new Point(position.getX(), position.getY());
		}
		return new Point();
	}

}
