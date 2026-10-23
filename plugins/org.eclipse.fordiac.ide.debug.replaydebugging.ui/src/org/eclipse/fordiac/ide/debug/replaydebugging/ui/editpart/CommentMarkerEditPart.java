/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommonConstants;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.CommentIconFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.EventCommentMarker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

public class CommentMarkerEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

	@Override
	public void activate() {
		super.activate();
		getModel().addPropertyChangeListener(this);
	}

	@Override
	public void deactivate() {
		getModel().removePropertyChangeListener(this);
		super.deactivate();
	}

	@Override
	protected IFigure createFigure() {
		return new CommentIconFigure(getModel().getComment());
	}

	@Override
	protected void createEditPolicies() {
		// Selecting the icon must not change how it looks: no selection rectangle or
		// handles.
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new NonResizableEditPolicy() {
			@Override
			protected List<? extends Handle> createSelectionHandles() {
				return Collections.emptyList();
			}
		});
	}

	@Override
	protected void refreshVisuals() {
		getFigure().setComment(getModel().getComment());
	}

	@Override
	public void performRequest(final Request request) {
		if (RequestConstants.REQ_OPEN.equals(request.getType())) {
			try {
				PlatformUI.getWorkbench().getService(IHandlerService.class)
						.executeCommand(CommonConstants.EDIT_EVENT_COMMENT_COMMAND_ID, null);
			} catch (final ExecutionException | NotDefinedException | NotEnabledException | NotHandledException e) {
				ILog.get().error("Could not edit the comment", e); //$NON-NLS-1$
			}
			return;
		}
		super.performRequest(request);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (EventCommentMarker.PROPERTY_COMMENT.equals(evt.getPropertyName())) {
			refreshVisuals();
		}
	}

	@Override
	public EventCommentMarker getModel() {
		return (EventCommentMarker) super.getModel();
	}

	@Override
	public CommentIconFigure getFigure() {
		return (CommentIconFigure) super.getFigure();
	}
}
