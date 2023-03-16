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

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Group;

public class GroupToGroupConnectionRoutingHelper extends AbstractConnectionRoutingHelper {

	public static final GroupToGroupConnectionRoutingHelper INSTANCE = new GroupToGroupConnectionRoutingHelper();

	private Group group1;
	private Group group2;

	@Override
	protected void processGroup(final ConnectionLayoutMapping mapping, final GroupEditPart group) {
		if (group.getModel().equals(group1) || group.getModel().equals(group2)) {
			flattenGroup(mapping, group);
		} else {
			super.processGroup(mapping, group);
		}
	}

	@Override
	protected void saveConnection(final ConnectionLayoutMapping mapping, final ConnectionEditPart con) {
		final Group sourceGroup = getGroupFromModel(con.getSource().getParent().getModel());
		final Group targetGroup = getGroupFromModel(con.getTarget().getParent().getModel());

		if ((group1.equals(sourceGroup) && group2.equals(targetGroup))
				|| (group2.equals(sourceGroup) && group1.equals(targetGroup))) {
			mapping.getConnections().add(con);
		}
	}

	public void setGroup1(final Group group1) {
		this.group1 = group1;
	}

	public void setGroup2(final Group group2) {
		this.group2 = group2;
	}

}
