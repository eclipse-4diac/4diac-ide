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
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.gef.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.fordiac.ide.gef.AdvancedScrollingGraphicalViewer;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IEditorPart;

public abstract class FordiacHandler extends AbstractHandler {
	protected static GraphicalViewer getViewer(final IEditorPart editor) {
		return editor.getAdapter(GraphicalViewer.class);
	}

	protected static CommandStack getCommandStack(final IEditorPart editor) {
		return editor.getAdapter(CommandStack.class);
	}

	protected static FBNetwork getFBNetwork(final IEditorPart editor) {
		return editor.getAdapter(FBNetwork.class);
	}

	protected static void selectElement(final Object element, final GraphicalViewer viewer) {
		final EditPart editPart = (EditPart) viewer.getEditPartRegistry().get(element);
		if (null != editPart) {
			if (viewer instanceof AdvancedScrollingGraphicalViewer) {
				((AdvancedScrollingGraphicalViewer) viewer).selectAndRevealEditPart(editPart);
			} else {
				viewer.select(editPart);
				viewer.reveal(editPart);
			}
		}
	}
}
