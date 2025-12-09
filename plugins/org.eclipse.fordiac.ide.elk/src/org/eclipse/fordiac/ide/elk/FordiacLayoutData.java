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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;

public class FordiacLayoutData {

	public record ConnectionLayoutData(Connection con, ConnectionRoutingData routingData) {
		// nothing to do here
	}

	private final Map<FBNetworkElement, Position> positions = new HashMap<>();
	private final List<ConnectionLayoutData> connData = new ArrayList<>();
	private final Map<IInterfaceElement, Integer> pins = new HashMap<>();
	private final Map<Group, Entry<Double, Double>> groups = new HashMap<>();

	public Map<FBNetworkElement, Position> getPositions() {
		return positions;
	}

	public List<ConnectionLayoutData> getConnectionPoints() {
		return connData;
	}

	public Map<IInterfaceElement, Integer> getPins() {
		return pins;
	}

	public Map<Group, Entry<Double, Double>> getGroups() {
		return groups;
	}

	public void addPosition(final FBNetworkElement element, final Position position) {
		positions.put(element, position);
	}

	public void addConnectionPoints(final Connection connection, final PointList pointList) {
		connData.add(new ConnectionLayoutData(connection, createRoutingData(pointList)));
	}

	public void addPin(final IInterfaceElement pin, final Integer padding) {
		pins.put(pin, padding);
	}

	public void addGroup(final Group group, final int height, final int width) {
		groups.put(group, new SimpleEntry<>(Double.valueOf(CoordinateConverter.INSTANCE.screenToIEC61499(height)),
				Double.valueOf(CoordinateConverter.INSTANCE.screenToIEC61499(width))));
	}

	private static ConnectionRoutingData createRoutingData(final PointList pointList) {
		final ConnectionRoutingData routingData = LibraryElementFactory.eINSTANCE.createConnectionRoutingData();
		if (pointList.size() > 2) {
			// 3 segments
			routingData.setDx1(fromScreen(pointList.getPoint(1).x() - pointList.getFirstPoint().x()));
			if (pointList.size() > 4) {
				// 5 segments
				routingData.setDy(fromScreen(pointList.getPoint(2).y() - pointList.getFirstPoint().y()));
				routingData.setDx2(
						fromScreen(pointList.getLastPoint().x() - pointList.getPoint(pointList.size() - 2).x()));
			}
		}
		return routingData;
	}

	private static double fromScreen(final int val) {
		return CoordinateConverter.INSTANCE.screenToIEC61499(val);
	}
}
