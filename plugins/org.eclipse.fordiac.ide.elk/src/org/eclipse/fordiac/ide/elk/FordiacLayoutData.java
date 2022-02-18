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
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Position;

public class FordiacLayoutData {
	
	private final Map<FBNetworkElement, Position> positions = new HashMap<>();
	private final Map<Connection, PointList> connPoints = new HashMap<>();
	private final Map<IInterfaceElement, Integer> pins = new HashMap<>();
	private final Map<Group, Entry<Integer, Integer>> groups = new HashMap<>();
	
	public Map<FBNetworkElement, Position> getPositions() {
		return positions;
	}

	public Map<Connection, PointList> getConnectionPoints() {
		return connPoints;
	}

	public Map<IInterfaceElement, Integer> getPins() {
		return pins;
	}

	public Map<Group, Entry<Integer, Integer>> getGroups() {
		return groups;
	}

	public void addPosition(final FBNetworkElement element, final Position position) {
		positions.put(element, position);
	}
	
	public void addConnectionPoints(final Connection connection, final PointList pointList) {
		connPoints.put(connection, pointList);
	}
	
	public void addPin(final IInterfaceElement pin, final Integer padding) {
		pins.put(pin, padding);
	}
	
	public void addGroup(final Group group, final int height, final int width) {
		groups.put(group, new SimpleEntry<>(Integer.valueOf(height), Integer.valueOf(width)));
	}
	
}
