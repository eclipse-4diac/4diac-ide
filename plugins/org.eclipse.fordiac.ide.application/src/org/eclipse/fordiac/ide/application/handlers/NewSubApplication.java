/*******************************************************************************
 * Copyright (c) 2017, 2024 fortiss GmbH, Johannes Kepler University Linz,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger - initial API and implementation and/or initial
 *                                documentation
 *   Bianca Wiesmayr - fix positioning of subapp, fix unfolded subapp
 *   Bianca Wiesmayr, Alois Zoitl - make newsubapp available for breadcrumb editor
 *   Alois Zoitl - extracted common elements into base class for reuseing it for
 *                 the group creation handler
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.commands.NewSubAppCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class NewSubApplication extends AbstractContainerElementHandler {

	@Override
	protected AbstractCreateFBNetworkElementCommand createContainerCreationCommand(final List<?> selection,
			final FBNetwork network, final Rectangle posSizeRef) {
		return new NewSubAppCommand(network, selection, posSizeRef.x, posSizeRef.y);
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		super.setEnabled(evaluationContext);
		if (isEnabled()) {
			final ISelection sel = (ISelection) HandlerUtil.getVariable(evaluationContext,
					ISources.ACTIVE_CURRENT_SELECTION_NAME);
			boolean maxInOneGroup = false;
			if (sel instanceof final StructuredSelection selection) {
				maxInOneGroup = isMaxInOneGroup(selection);
			}
			setBaseEnabled(maxInOneGroup);
		}
	}

	private static boolean isMaxInOneGroup(final StructuredSelection selection) {
		Group group = null;
		int i = 0;
		for (final Object selElement : selection.toList()) {
			final Object modelElement = getModelElement(selElement);
			if (modelElement instanceof final FBNetworkElement fbel) {
				if (fbel.isInGroup()) {
					if (group == null) {
						if (i != 0) {
							return false;
						}
						group = fbel.getGroup();
					} else if (group != fbel.getGroup()) {
						return false;
					}
				} else if (group != null) {
					return false;
				}
				i++; // only count FBNetworkElements
			}
		}
		return true;
	}

}
