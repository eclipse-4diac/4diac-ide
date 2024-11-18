/*******************************************************************************
 * Copyright (c) 2013, 2017 AIT, fortiss GmbH
 * 				 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Filip Adren, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - added doubleclickevent for pin jumps
 *   Martin Schwarz - Build fixes
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public final class ElementSelector {
	/**
	 * Selects the provided objects in the current editor
	 *
	 * @param viewObjects list with objects to select
	 */
	public static void selectViewObjects(final Collection<? extends Object> viewObjects) {
		final IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart();
		final GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
		if (viewer != null) {
			viewer.flush();
			final List<EditPart> editParts = getSelectableEditParts(viewer, viewObjects);
			if (!editParts.isEmpty()) {
				viewer.setSelection(new StructuredSelection(editParts));
				if (viewer instanceof final AdvancedScrollingGraphicalViewer asViewer) {
					asViewer.revealEditPart(editParts.get(0));
				} else {
					viewer.reveal(editParts.get(0));
				}
			}
		}

	}

	private static List<EditPart> getSelectableEditParts(final GraphicalViewer viewer,
			final Collection<?> viewObjects) {
		final List<EditPart> selectableChildren = new ArrayList<>();
		for (final Object view : viewObjects) {
			if ((viewer.getEditPartForModel(view) instanceof final EditPart ep) && ep.getModel().equals(view)) {
				selectableChildren.add(ep);
			}
		}
		return selectableChildren;
	}

	public static void jumpToPinFromDoubleClickEvent(final DoubleClickEvent event) {

		if (!((StructuredSelection) event.getSelection()).isEmpty()) {
			IInterfaceElement selElement = null;

			// if event gives us pin as output
			final Object selection = ((StructuredSelection) event.getSelection()).getFirstElement();
			if (selection instanceof final IInterfaceElement ie) {
				selElement = ie;
			}

			// if event gives us connection as output
			if (selection instanceof final Connection conn) {
				final TableViewer tableViewer = (TableViewer) event.getSource();
				if (tableViewer.getInput().equals(((Connection) selection).getSource())) {
					selElement = conn.getDestination();
				} else {
					selElement = conn.getSource();
				}
			}

			if (selElement != null && selElement.getFBNetworkElement() != null) {
				final IEditorPart editor = OpenListenerManager
						.openEditor(selElement.getFBNetworkElement().eContainer().eContainer());
				HandlerHelper.selectElement(selElement, HandlerHelper.getViewer(editor));
			}
		}
	}

	private ElementSelector() {
		throw new UnsupportedOperationException("do not instantiate this class"); //$NON-NLS-1$
	}
}
