/*******************************************************************************
 * Copyright (c) 2019, 2021 Johannes Kepler University Linz,
 * 				            Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - added check if subapp interface is selected and mark that in
 *                 parent
 *   Daniel Lindhuber - MoveElementsFromSubappCommand integration
 *   				  - adjusted for unfolded subapps
 *   Bianca Wiesmayr, Alois Zoitl, Michael Oberlehner - refactor for breadcrumb
 ********************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.commands.MoveElementFromSubAppCommand;
import org.eclipse.fordiac.ide.application.commands.MoveElementFromSubAppCommand.MoveOperation;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditorInput;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubAppNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubApplicationEditorInput;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.gef.handlers.FordiacHandler;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class MoveToParentHandler extends FordiacHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		if (null != editor) {
			final ISelection selection = HandlerUtil.getCurrentSelection(event);
			final List<FBNetworkElement> fbelements = getSelectedFBNElements(selection);

			if (!fbelements.isEmpty()) {
				final Rectangle bounds = getParentSubappBounds(editor, fbelements);

				final CompoundCommand cmd = new CompoundCommand();
				fbelements.forEach(
						fbel -> cmd.add(new MoveElementFromSubAppCommand(fbel, bounds, MoveOperation.CONTEXT_MENU)));
				getCommandStack(editor).execute(cmd);
				preventFBPiling(cmd.getCommands());
			}
		}
		return Status.OK_STATUS;
	}

	private static Rectangle getParentSubappBounds(final IEditorPart editor,
			final List<FBNetworkElement> fbelements) {
		final FBNetwork fbnetwork = getFBNetwork(editor);
		final FBNetwork subappNetwork = getParent(fbelements.get(0));
		final GraphicalViewer viewer = getSubappParentViewer(fbnetwork, subappNetwork, editor);
		final GraphicalEditPart ep = (GraphicalEditPart) viewer.getEditPartRegistry()
				.get(subappNetwork.eContainer());
		return ep.getFigure().getBounds();
	}

	private static GraphicalViewer getSubappParentViewer(FBNetwork fbnetwork, FBNetwork subappNetwork,
			IEditorPart parent) {
		if (fbnetwork.equals(subappNetwork)) {
			// source subapp editor, subapp content is opened in editor
			return getViewer(openParentEditor((SubApp) subappNetwork.eContainer()));
		}
		return getViewer(parent);
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);

		final List<FBNetworkElement> fbelements = getSelectedFBNElements(selection);
		if (!fbelements.isEmpty()) {
			final FBNetwork parent = getParent(fbelements.get(0));
			if (null != parent) {
				final boolean sameParentNetwork = fbelements.stream()
						.allMatch(el -> parent.equals(el.getFbNetwork()));
				setBaseEnabled(sameParentNetwork);
				return;
			}
		}

		setBaseEnabled(false);
	}

	private static List<FBNetworkElement> getSelectedFBNElements(ISelection selection) {
		if (!selection.isEmpty() && (selection instanceof IStructuredSelection)) {
			final IStructuredSelection sel = (IStructuredSelection) selection;
			return (List<FBNetworkElement>) sel.toList().stream()
					.filter(o -> o instanceof AbstractFBNElementEditPart)
					.map(ep -> ((AbstractFBNElementEditPart) ep).getModel())
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	private static FBNetwork getParent(FBNetworkElement fbNetworkElement) {
		return fbNetworkElement.getFbNetwork().eContainer() instanceof SubApp
				? fbNetworkElement.getFbNetwork()
						: null;
	}

	private static IEditorPart openParentEditor(SubApp subApp) {
		final EObject model = subApp.eContainer().eContainer();
		return EditorUtils.openEditor(getEditorInput(model), getEditorId(model));
	}

	// prevents the FBs from lying on top of one another
	private static void preventFBPiling(List<MoveElementFromSubAppCommand> commands) {
		final int OFFSET = 90;
		int left = 0;
		int right = 0;
		int below = 0;
		for (final MoveElementFromSubAppCommand cmd : commands) {
			switch (cmd.getSide()) {
			case LEFT:
				cmd.getElement().setY(cmd.getElement().getY() + (left * OFFSET));
				left++;
				break;
			case RIGHT:
				cmd.getElement().setY(cmd.getElement().getY() + (right * OFFSET));
				right++;
				break;
			default:
				cmd.getElement().setY(cmd.getElement().getY() + (below * OFFSET));
				below++;
			}
		}
	}

	private static IEditorInput getEditorInput(EObject model) {
		if (model instanceof SubApp) {
			return new SubApplicationEditorInput((SubApp) model);
		}
		if (model instanceof Application) {
			return new ApplicationEditorInput((Application) model);
		}
		return null;
	}

	private static String getEditorId(EObject model) {
		if (model instanceof SubApp) {
			return SubAppNetworkEditor.class.getName();
		}
		if (model instanceof Application) {
			return FBNetworkEditor.class.getName();
		}
		return null;
	}

}
