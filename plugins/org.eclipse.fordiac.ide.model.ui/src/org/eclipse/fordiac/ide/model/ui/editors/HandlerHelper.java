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

package org.eclipse.fordiac.ide.model.ui.editors;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;

public final class HandlerHelper {
	private HandlerHelper() {
		// do not instantiate this class
	}

	public static GraphicalViewer getViewer(final IEditorPart editor) {
		return editor.getAdapter(GraphicalViewer.class);
	}

	public static CommandStack getCommandStack(final IEditorPart editor) {
		return editor.getAdapter(CommandStack.class);
	}

	public static FBNetwork getFBNetwork(final IEditorPart editor) {
		return editor.getAdapter(FBNetwork.class);
	}

	public static boolean selectElement(final Object element, final IEditorPart editor) {
		if (null != editor) {
			final GraphicalViewer viewer = getViewer(editor);
			if (null != viewer) {
				return selectElement(element, viewer);
			}
			// TODO how other editor may want to handle selection
		}
		return false;
	}

	public static boolean selectElement(final Object element, final GraphicalViewer viewer) {
		if (viewer != null) {
			final EditPart editPart = viewer.getEditPartRegistry().get(element);
			if (null != editPart) {
				selectEditPart(viewer, editPart);
				return true;
			}
		}
		return false;
	}

	public static void selectEditPart(final EditPartViewer viewer, final EditPart editPart) {
		viewer.flush(); // ensure that the viewer is ready
		if (viewer instanceof final AdvancedScrollingGraphicalViewer asgv) {
			asgv.selectAndRevealEditPart(editPart);
		} else {
			viewer.select(editPart);
			viewer.reveal(editPart);
		}
	}

	public static IEditorPart openEditor(final EObject model) {
		return OpenListenerManager.openEditor(model);
	}

	public static IEditorPart openParentEditor(final FBNetworkElement model) {
		final EObject parentModel = model.eContainer().eContainer(); // use eContainer here so that it also works for
		// types
		return OpenListenerManager.openEditor(parentModel);
	}

	public static boolean isEditableSubApp(final SubApp subApp) {
		if ((null == subApp) || (subApp.isTyped())) {
			return false;
		}

		EObject obj = subApp;
		while (obj.eContainer() != null) {
			obj = obj.eContainer();
			if (obj instanceof SubAppType) {
				return false;
			}
		}
		return true;
	}

	public static List<FBNetworkElement> getSelectedFBNElements(final ISelection selection) {
		if ((selection instanceof final IStructuredSelection sel) && !sel.isEmpty()) {
			return ((List<?>) sel.toList()).stream().filter(EditPart.class::isInstance).map(EditPart.class::cast)
					.map(EditPart::getModel).filter(FBNetworkElement.class::isInstance)
					.map(FBNetworkElement.class::cast).toList();
		}
		return Collections.emptyList();
	}

	public static void showExpandedSubapp(final SubApp subapp, final IEditorPart editor) {
		Display.getCurrent().asyncExec(() -> {
			// move canvas to show the top-left corner of an expanded subapp
			final GraphicalViewer viewer = HandlerHelper.getViewer(editor);
			if (null != viewer) {
				final EditPart subappEP = viewer.getEditPartRegistry().get(subapp);
				if (subappEP != null) {
					final EditPart root = subappEP.getRoot();
					if (root instanceof final ScalableFreeformRootEditPart gep
							&& gep.getFigure() instanceof final FreeformViewport viewp) {
						final Point pos = ((PositionableElement) subapp).getPosition().toScreenPoint();
						viewp.setHorizontalLocation((int) (pos.x * gep.getZoomManager().getZoom()));
						viewp.setVerticalLocation((int) (pos.y * gep.getZoomManager().getZoom()));
					}
				}
			}
		});
	}
}
