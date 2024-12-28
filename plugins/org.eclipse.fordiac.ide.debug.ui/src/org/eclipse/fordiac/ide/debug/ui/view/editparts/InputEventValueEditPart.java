/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view.editparts;

import org.eclipse.draw2d.Button;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.debug.ui.DebugColorProvider;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorEventQueue;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorExternalEventQueue;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RootEditPart;

public class InputEventValueEditPart extends EventValueEditPart {

	@Override
	protected IFigure createFigure() {
		final Button bt = new Button(""); //$NON-NLS-1$
		bt.setSize(50, -1);
		bt.setOpaque(true);
		bt.setBackgroundColor(DebugColorProvider.getWatchColor());
		bt.setForegroundColor(DebugColorProvider.getWatchTextColor());
		bt.addActionListener(e -> triggerEvent());
		return bt;
	}

	@Override
	public Label getLabelFigure() {
		return (Label) getFigure().getChildren().get(0);
	}

	private void triggerEvent() {
		final FBEvaluatorExternalEventQueue eventQueue = getEventQueue();
		if (eventQueue != null) {
			eventQueue.triggerInputEvent(getInterfaceElement());
		}
	}

	private FBDebugViewRootEditPart getDebugViewRoot() {
		final RootEditPart root = getRoot();
		if (root != null) {
			for (final EditPart child : root.getChildren()) {
				if (child instanceof final FBDebugViewRootEditPart fbDebugRootEP) {
					return fbDebugRootEP;
				}
			}
		}
		return null;
	}

	private FBEvaluatorExternalEventQueue getEventQueue() {
		final FBDebugViewRootEditPart debugViewRoot = getDebugViewRoot();
		if (debugViewRoot != null) {
			final FBEvaluatorEventQueue eventQueue = debugViewRoot.getFBEvaluator().getEventQueue();
			if (eventQueue instanceof final FBEvaluatorExternalEventQueue fbQueue) {
				return fbQueue;
			}
		}
		return null;
	}
}
