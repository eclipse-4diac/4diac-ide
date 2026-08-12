/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.views.graph;

import org.eclipse.fordiac.ide.model.graph.FBNetworkTopologyGraph;
import org.eclipse.jface.viewers.ColumnLabelProvider;

public class FBNetworkGraphSequenceNumberLabelProvider extends ColumnLabelProvider {

	@Override
	public String getText(final Object object) {
		return switch (object) {
		case final FBNetworkTopologyGraph<?>.TopologyNode node when node
				.getSequenceNumber() != FBNetworkTopologyGraph.NULL_SEQUENCE_NUMBER ->
			Integer.toString(node.getSequenceNumber());
		case null, default -> ""; //$NON-NLS-1$
		};
	}
}
