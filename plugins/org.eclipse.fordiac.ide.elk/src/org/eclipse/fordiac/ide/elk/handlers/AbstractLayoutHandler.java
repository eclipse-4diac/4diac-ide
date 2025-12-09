/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Aloios Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeNetworkViewerEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractLayoutHandler extends AbstractHandler {

	@Override
	public void setEnabled(final Object evaluationContext) {
		final IWorkbenchPart part = (IWorkbenchPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		if (part != null) {
			final GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
			setBaseEnabled(null != viewer);
		} else {
			setBaseEnabled(false);
		}
	}

	protected static boolean canLayout(final StructuredSelection selection) {
		final var elem = selection.getFirstElement();
		// @formatter:off
		return elem instanceof FBNetworkEditPart
				|| elem instanceof UnfoldedSubappContentEditPart
				|| (elem instanceof EditorWithInterfaceEditPart && !(elem instanceof CompositeNetworkViewerEditPart)) ;
		// @formatter:on
	}

}