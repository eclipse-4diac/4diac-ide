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
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.ui.UtilityMarkerHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
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
		final EObject selectedElement = getValidSelectedElement(getSelectedElement(selection));

		if (selectedElement != null) {
			if (isMarked(selectedElement)) {
				UtilityMarkerHelper.deleteElementMarker(getMarkerId(), getRootResource(selectedElement));
			} else {
				UtilityMarkerHelper.setMarkedElement(getMarkerId(), selectedElement);
			}
		}
		return null;
	}

	protected abstract String getMarkerId();

	protected abstract String getMarkerName();

	protected abstract EObject getValidSelectedElement(final Object selectedObject);

	@Override
	public void updateElement(final UIElement element, final Map parameters) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);
			if (viewer == null || viewer.getSelectedEditParts().isEmpty()) {
				return;
			}

			final EObject selectedElement = getValidSelectedElement(viewer.getSelectedEditParts().getFirst());

			if (selectedElement != null) {
				if (isMarked(selectedElement)) {
					element.setText(MessageFormat.format(Messages.FBMarker_RemoveMarker, getMarkerName()));
				} else {
					element.setText(MessageFormat.format(Messages.FBMarker_MarkAs, getMarkerName()));
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
			setBaseEnabled(getValidSelectedElement(getSelectedElement(selection)) != null);
		} else {
			setBaseEnabled(false);
		}
	}

	private static Object getSelectedElement(final ISelection selection) {
		return (selection instanceof final IStructuredSelection structSel && !structSel.isEmpty())
				? structSel.getFirstElement()
				: null;
	}

	private static IResource getRootResource(final EObject target) {
		final EObject rootContainer = EcoreUtil.getRootContainer(target);
		if (rootContainer instanceof final LibraryElement libEl && libEl.getTypeEntry() != null) {
			return libEl.getTypeEntry().getFile();
		}
		return null;
	}

	private boolean isMarked(final EObject target) {
		final EObject markedElement = UtilityMarkerHelper.getMarkedElement(getMarkerId(), target);
		return markedElement != null && markedElement == target;
	}

}
