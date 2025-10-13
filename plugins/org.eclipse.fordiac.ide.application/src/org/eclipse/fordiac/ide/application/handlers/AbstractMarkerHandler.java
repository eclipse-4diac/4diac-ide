/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.util.marker.MarkerDescriptor;
import org.eclipse.fordiac.util.marker.UtilityMarkerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

public abstract class AbstractMarkerHandler extends AbstractHandler implements IElementUpdater {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		final GraphicalEditPart selectedElement = getValidSelectedElement(selection);

		if (selectedElement != null) {
			if (isMarked(selectedElement)) {
				removeElementMarker(selectedElement);
			} else {
				if (getDescriptor().isUnique()) {
					// remove old element
					removeElementMarker(selectedElement);
				}
				markElement(selectedElement);
			}
		}
		return null;
	}

	protected abstract MarkerDescriptor getDescriptor();

	protected abstract GraphicalEditPart getValidSelectedElement(final ISelection selection);

	protected void markElement(final GraphicalEditPart ep) {
		if (ep.getModel() instanceof final EObject target) {
			UtilityMarkerHelper.addElementMarker(getDescriptor(), getRootResource(ep), target);
		}
	}

	protected void removeElementMarker(final GraphicalEditPart ep) {
		UtilityMarkerHelper.deleteElementMarker(getDescriptor(), getRootResource(ep));
	}

	protected static IResource getRootResource(final GraphicalEditPart ep) {
		if ((ep.getModel() instanceof final FBNetworkElement fbne) && (fbne.getFbNetwork().getAutomationSystem()
				.getTypeEntry().getFile() instanceof final IResource res)) {
			return res;
		}
		if (ep.getModel() instanceof final IInterfaceElement elem && elem.getBlockFBNetworkElement() != null
				&& elem.getBlockFBNetworkElement().getFbNetwork().getAutomationSystem().getTypeEntry()
						.getFile() instanceof final IResource res) {
			return res;
		}
		return null;
	}

	protected boolean isMarked(final GraphicalEditPart ep) {
		final IResource resource = getRootResource(ep);
		if (resource == null) {
			return false;
		}
		final EObject markedElement = UtilityMarkerHelper.getMarkedElement(getDescriptor(), resource);
		return markedElement != null && EcoreUtil.equals(markedElement, (EObject) ep.getModel());
	}

	@Override
	public void updateElement(final UIElement element, final Map parameters) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);
			if (viewer == null || viewer.getSelectedEditParts().isEmpty()) {
				return;
			}

			final EditPart editPart = viewer.getSelectedEditParts().getFirst();

			if ((editPart instanceof final GraphicalEditPart gep)) {
				if (isMarked(gep)) {
					element.setText(MessageFormat.format(Messages.FBMarker_RemoveMarker, getDescriptor().name()));
				} else {
					element.setText(MessageFormat.format(Messages.FBMarker_MarkAs, getDescriptor().name()));
				}
			}
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final IEditorPart editor = (IEditorPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		if (editor != null) {
			final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
					ISources.ACTIVE_CURRENT_SELECTION_NAME);
			setBaseEnabled(getValidSelectedElement(selection) != null);
		} else {
			setBaseEnabled(false);
		}
	}

}
