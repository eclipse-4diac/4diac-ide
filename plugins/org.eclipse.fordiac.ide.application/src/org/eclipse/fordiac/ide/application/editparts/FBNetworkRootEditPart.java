/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 * 				            Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Filip Andren,
 *   Monika Wenger
 *       - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed copy/paste handling
 *               - added inplace fb instance creation
 *               - extracted FBNetworkRootEditPart from FBNetworkEditor
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.tools.AdvancedMarqueeDragTracker;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.ui.IWorkbenchPartSite;

public class FBNetworkRootEditPart extends ZoomScalableFreeformRootEditPart {

	public static class FBNetworkMarqueeDragTracker extends AdvancedMarqueeDragTracker {

		@Override
		protected Collection<? extends GraphicalEditPart> calculateMarqueeSelectedEditParts() {
			final Collection<? extends GraphicalEditPart> marqueeSelectedEditParts = super.calculateMarqueeSelectedEditParts();
			// only report connections and fbelements, isMarqueeslectable can not be used
			// for that as it affects connection selection in the wrong way
			return marqueeSelectedEditParts.stream().filter(ep -> ep instanceof ConnectionEditPart && ep.isSelectable()
					|| ep.getModel() instanceof FBNetworkElement).collect(Collectors.toSet());
		}

	}

	private final FBNetwork fbNetwork;

	public FBNetworkRootEditPart(final FBNetwork fbNetwork, final IWorkbenchPartSite site,
			final ActionRegistry actionRegistry) {
		super(site, actionRegistry);
		this.fbNetwork = fbNetwork;
	}

	@Override
	public DragTracker getDragTracker(final Request req) {
		final FBNetworkMarqueeDragTracker dragTracker = new FBNetworkMarqueeDragTracker();
		dragTracker.setMarqueeBehavior(MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED_AND_RELATED_CONNECTIONS);
		return dragTracker;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == FBNetwork.class) {
			return adapter.cast(fbNetwork);
		}
		return super.getAdapter(adapter);
	}

}