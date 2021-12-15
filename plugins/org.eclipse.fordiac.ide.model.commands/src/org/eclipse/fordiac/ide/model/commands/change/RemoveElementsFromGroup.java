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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Point;

public class RemoveElementsFromGroup extends Command {

	private final Group sourceGroup;
	private final Point destination;
	private final List<FBNetworkElement> elements;
	private final Map<FBNetworkElement, org.eclipse.fordiac.ide.model.libraryElement.Position> oldPos = new HashMap<>();
	private final Map<FBNetworkElement, org.eclipse.fordiac.ide.model.libraryElement.Position> newPos = new HashMap<>();


	public RemoveElementsFromGroup(final Collection<FBNetworkElement> elements, final Point destination) {
		this.elements = new ArrayList<>(elements);
		this.sourceGroup = getGroup(this.elements);
		this.destination = getTargetPosition(destination);
	}

	@Override
	public boolean canExecute() {
		return (null != sourceGroup) && allElementsFromSameGroup();
	}

	@Override
	public void execute() {
		elements.forEach(el -> {
			oldPos.put(el, el.getPosition());
			el.setGroup(null);
		});
		FBNetworkHelper.moveFBNetworkToDestination(elements, destination);
	}

	@Override
	public void undo() {
		elements.forEach(el -> {
			el.setGroup(sourceGroup);
			newPos.put(el, el.getPosition());
			el.setPosition(oldPos.get(el));
		});
	}

	@Override
	public void redo() {
		elements.forEach(el -> {
			el.setGroup(null);
			el.setPosition(newPos.get(el));
		});
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

	private Point getTargetPosition(final Point destination) {
		Point targetPos = destination;
		if (targetPos == null && sourceGroup != null) {
			final Position position = sourceGroup.getPosition();
			targetPos = new Point(position.getX(), position.getY());
		}
		return targetPos;
	}

}
