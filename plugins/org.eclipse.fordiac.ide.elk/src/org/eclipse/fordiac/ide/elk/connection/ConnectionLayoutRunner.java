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

import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.util.BasicProgressMonitor;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.ui.IWorkbenchPart;

public final class ConnectionLayoutRunner {

	public static void runSubapps(final ConnectionLayoutMapping mapping, final FordiacLayoutData data) {
		for (final UnfoldedSubappContentEditPart subapp : mapping.getExpandedSubapps()) {
			final ConnectionLayoutMapping subappMapping = run(subapp);
			final FordiacLayoutData subappData = AbstractConnectionRoutingHelper.calculateConnections(subappMapping);
			ConnectionLayoutRunner.runGroups(subapp, subappMapping, subappData);
			ConnectionLayoutRunner.runSubapps(subappMapping, subappData);

			// combine the recursive subapp runs
			data.getConnectionPoints().putAll(subappData.getConnectionPoints());
		}
	}

	public static void runGroups(final Object parent, final ConnectionLayoutMapping mapping, final FordiacLayoutData data) {
		// separate run for every group
		for (final GroupEditPart group : mapping.getGroups()) {
			final ConnectionLayoutMapping groupMapping = run(parent, group, mapping);
			final FordiacLayoutData groupData = AbstractConnectionRoutingHelper.calculateConnections(groupMapping);

			// combine data
			data.getConnectionPoints().putAll(groupData.getConnectionPoints());
		}

		// group-to-group connections
		for (final ConnectionEditPart conn : mapping.getGroupTuples().values()) {
			final Group sourceGroup = ((FBNetworkElement) conn.getSource().getParent().getModel()).getGroup();
			final Group targetGroup = ((FBNetworkElement) conn.getTarget().getParent().getModel()).getGroup();

			final ConnectionLayoutMapping groupToGroupMapping = run(parent, sourceGroup, targetGroup);
			final FordiacLayoutData groupToGroupData = AbstractConnectionRoutingHelper
					.calculateConnections(groupToGroupMapping);

			// combine data
			data.getConnectionPoints().putAll(groupToGroupData.getConnectionPoints());
		}
	}

	public static ConnectionLayoutMapping run(final Object parent) {
		final ConnectionLayoutMapping mapping = getLayoutMapping(parent);

		if (mapping != null && mapping.hasNetwork()) {
			StandardConnectionRoutingHelper.INSTANCE.buildGraph(mapping);
		}

		layout(mapping);

		return mapping;
	}

	private static ConnectionLayoutMapping run(final Object parent, final Group group1, final Group group2) {
		final ConnectionLayoutMapping mapping = getLayoutMapping(parent);

		if (mapping != null && mapping.hasNetwork()) {
			GroupToGroupConnectionRoutingHelper.INSTANCE.setGroup1(group1);
			GroupToGroupConnectionRoutingHelper.INSTANCE.setGroup2(group2);
			GroupToGroupConnectionRoutingHelper.INSTANCE.buildGraph(mapping);
		}

		layout(mapping);

		return mapping;
	}

	private static ConnectionLayoutMapping run(final Object parent, final GroupEditPart group, final ConnectionLayoutMapping normalMapping) {
		final ConnectionLayoutMapping mapping = getLayoutMapping(parent);

		if (mapping != null && mapping.hasNetwork()) {
			GroupConnectionRoutingHelper.INSTANCE.setCurrentGroup(group);
			GroupConnectionRoutingHelper.INSTANCE.setGroupTupleMap(normalMapping.getGroupTuples());
			GroupConnectionRoutingHelper.INSTANCE.buildGraph(mapping);
		}

		layout(mapping);

		return mapping;
	}

	private static ConnectionLayoutMapping getLayoutMapping(final Object parent) {
		if (parent instanceof IWorkbenchPart) {
			return new ConnectionLayoutMapping((IWorkbenchPart) parent);
		}
		if (parent instanceof UnfoldedSubappContentEditPart) {
			return new ConnectionLayoutMapping((UnfoldedSubappContentEditPart) parent);
		}
		return null;
	}

	private static void layout(final ConnectionLayoutMapping mapping) {
		final RecursiveGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
		engine.layout(mapping.getLayoutGraph(), new BasicProgressMonitor());
	}

	private ConnectionLayoutRunner() {
		throw new UnsupportedOperationException("Helper class sould not be instantiated!"); //$NON-NLS-1$
	}

}
