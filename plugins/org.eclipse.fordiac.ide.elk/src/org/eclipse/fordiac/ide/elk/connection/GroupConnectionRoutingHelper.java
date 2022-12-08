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
package org.eclipse.fordiac.ide.elk.connection;

import java.util.Map;
import java.util.Objects;

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Group;

public class GroupConnectionRoutingHelper extends AbstractConnectionRoutingHelper {

	public static final GroupConnectionRoutingHelper INSTANCE = new GroupConnectionRoutingHelper();

	private Map<Integer, ConnectionEditPart> groupToGroup;
	private GroupEditPart currentGroup;

	@Override
	protected void processGroup(final ConnectionLayoutMapping mapping, final GroupEditPart group) {
		if (group.equals(currentGroup)) {
			flattenGroup(mapping, group);
		} else {
			super.processGroup(mapping, group);
		}
	}

	@Override
	protected void saveConnections(final ConnectionLayoutMapping mapping, final InterfaceEditPart ie) {
		for (final Object obj : ie.getTargetConnections()) {
			final ConnectionEditPart conn = (ConnectionEditPart) obj;
			final Group sourceGroup = getGroupFromModel(conn.getSource().getParent().getModel());
			final Group targetGroup = getGroupFromModel(conn.getTarget().getParent().getModel());

			if (sourceGroup != null && targetGroup != null && (sourceGroup != targetGroup)) {
				final int hash = Objects.hash(sourceGroup, targetGroup);
				groupToGroup.computeIfAbsent(hash, val -> conn);
			} else if (currentGroup.getModel().equals(sourceGroup) || currentGroup.getModel().equals(targetGroup)) {
				mapping.getConnections().add(conn);
			}
		}
	}

	public void setCurrentGroup(final GroupEditPart group) {
		currentGroup = group;
	}

	public void setGroupTupleMap(final Map<Integer, ConnectionEditPart> groupToGroup) {
		this.groupToGroup = groupToGroup;
	}

}
