/*******************************************************************************
 * Copyright (c) 2019, 2024 Johannes Kepler University Linz,
 *                          Primetals Technology Austria GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - refactored this class to be the base class of the followConnection handlers
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElement;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElement.GroupTargetInterfaceElement;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElementEditPart;
import org.eclipse.fordiac.ide.application.widgets.OppositeSelectionDialog;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class FollowConnectionHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		return Status.CANCEL_STATUS;
	}

	protected static FBNetwork getFBNetwork(final IEditorPart editor) {
		final FBNetwork network = editor.getAdapter(FBNetwork.class);
		if (null == network) {
			// we have a viewer
			final FBNetworkElement element = editor.getAdapter(FBNetworkElement.class);
			if (element instanceof final SubApp subApp) {
				return subApp.getSubAppNetwork();
			}
			if (element instanceof final CFBInstance cfbInstance) {
				return cfbInstance.getCfbNetwork();
			}
		}
		return network;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		final IEditorPart editor = (IEditorPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);

		if (selection instanceof final IStructuredSelection structSel && structSel.size() == 1) {
			setBaseEnabled(editor != null && isValidSelectedElement(structSel));
		} else {
			setBaseEnabled(false);
		}

	}

	@SuppressWarnings("static-method") // allow subclasses to override this method
	protected boolean isValidSelectedElement(final IStructuredSelection structSel) {
		return structSel.getFirstElement() instanceof InterfaceEditPart;
	}

	protected List<IInterfaceElement> getConnectionOposites(final InterfaceEditPart iep) {
		final IInterfaceElement ie = iep.getModel();
		final EList<Connection> connList = getConnectionList(ie);

		final boolean stepMode = UIPreferenceConstants.STORE.getBoolean(UIPreferenceConstants.P_TOGGLE_JUMP_STEP);

		if (stepMode) {
			if (isLeft()) {
				return connList.stream().map(Connection::getSource).toList();
			}
			return connList.stream().map(Connection::getDestination).toList();
		}

		if (useTargetPins(iep)) {
			return getTargetPins(iep);
		}

		return connList.stream().map(con -> (con.getSource().equals(ie) ? con.getDestination() : con.getSource()))
				.toList();
	}

	protected List<IInterfaceElement> resolveTargetPins(final List<IInterfaceElement> opposites,
			final GraphicalViewer viewer) {
		final List<IInterfaceElement> resolvedOpposites = new ArrayList<>();
		for (final IInterfaceElement element : opposites) {
			final EditPart ep = viewer.getEditPartForModel(element);
			if ((ep instanceof final InterfaceEditPart iep) && isExpandedSubappPin(element)) {
				if (useTargetPins(iep)) {
					resolvedOpposites.addAll(getTargetPins(iep));
				} else {
					resolvedOpposites.addAll(getConnectionOposites(iep));
				}
			} else {
				resolvedOpposites.add(element);
			}
		}
		return resolvedOpposites;
	}

	private boolean useTargetPins(final InterfaceEditPart iep) {
		return !iep.getChildren().isEmpty()
				&& ((iep.getModel().isIsInput() && isLeft()) || (!iep.getModel().isIsInput() && !isLeft()));
	}

	protected abstract boolean isLeft();

	protected abstract EList<Connection> getConnectionList(final IInterfaceElement ie);

	private static List<IInterfaceElement> getTargetPins(final InterfaceEditPart iep) {
		final List<IInterfaceElement> result = new ArrayList<>();
		for (final EditPart ep : iep.getChildren()) {
			switch (ep.getModel()) {
			case final GroupTargetInterfaceElement gtIE -> result.addAll(gtIE.getRefElements());
			case final TargetInterfaceElement targetIE -> result.add(targetIE.getRefElement());
			default -> {
				// we should never get here, even if we do we don't want to do anything
			}
			}
		}
		return result;
	}

	protected static boolean isInsideSubappOrViewer(final IInterfaceElement ie, final FBNetwork fbNetwork) {
		final FBNetworkElement fbnElement = ie.getFBNetworkElement();
		return ((fbnElement instanceof SubApp) || (fbnElement instanceof CFBInstance))
				&& (!fbNetwork.equals(fbnElement.eContainer()));
	}

	private static void showOppositeSelectionDialog(final List<IInterfaceElement> opposites, final ExecutionEvent event,
			final GraphicalViewer viewer, final IInterfaceElement originPin, final IEditorPart editor) {
		selectInterfaceElement(opposites.getFirst(), editor);
		viewer.flush();
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		final IFigure figure = ((InterfaceEditPart) selection.getFirstElement()).getFigure();
		final OppositeSelectionDialog dialog = new OppositeSelectionDialog(opposites, originPin, viewer.getControl(),
				figure, editor);
		dialog.open();
	}

	protected IInterfaceElement getInternalOppositePin(final ISelection selection) {
		final InterfaceEditPart pin = (InterfaceEditPart) ((IStructuredSelection) selection).getFirstElement();

		if (hasOpposites(pin)) {
			if (pin.isEvent()) {
				return getInternalOppositeEventPin(pin);
			}
			if (pin.isVariable()) {
				if (((VarDeclaration) pin.getModel()).isInOutVar()) {
					return getInternalOppositeVarInOutPin(pin);
				}
				return getInternalOppositeVarPin(pin);
			}
			return getInternalOppositePlugOrSocketPin(pin);
		}
		return null;
	}

	protected abstract IInterfaceElement getInternalOppositeEventPin(final InterfaceEditPart pin);

	protected abstract IInterfaceElement getInternalOppositeVarPin(final InterfaceEditPart pin);

	protected abstract IInterfaceElement getInternalOppositeVarInOutPin(final InterfaceEditPart pin);

	protected abstract IInterfaceElement getInternalOppositePlugOrSocketPin(final InterfaceEditPart pin);

	@SuppressWarnings("static-method")
	protected boolean hasOpposites(final InterfaceEditPart pin) {
		return false;
	}

	protected static void selectOpposites(final ExecutionEvent event, final GraphicalViewer viewer,
			final IInterfaceElement originPin, final List<IInterfaceElement> opposites, final IEditorPart editor) {
		if (!opposites.isEmpty()) {
			if (opposites.size() == 1) {
				selectInterfaceElement(opposites.getFirst(), editor);
			} else {
				showOppositeSelectionDialog(opposites, event, viewer, originPin, editor);
			}
		}
	}

	protected static IInterfaceElement calcInternalOppositePin(final EList<? extends IInterfaceElement> source,
			final EList<? extends IInterfaceElement> destination, final InterfaceEditPart pin) {

		final int sourceIndex = source.stream().filter(IInterfaceElement::isVisible).toList().indexOf(pin.getModel());
		final var visibleDestinations = destination.stream().filter(IInterfaceElement::isVisible).toList();

		if (sourceIndex == -1) {
			return visibleDestinations.getFirst();
		}

		if ((visibleDestinations.size() - 1) < sourceIndex) {
			return visibleDestinations.get(visibleDestinations.size() - 1);
		}
		return visibleDestinations.get(sourceIndex);
	}

	protected static void gotoParent(final ExecutionEvent event) throws ExecutionException {
		final GotoParentHandler gotoParentHandler = new GotoParentHandler();
		gotoParentHandler.execute(event);
	}

	protected static boolean isEditorBorderPin(final IInterfaceElement ie, final FBNetwork fbNetwork) {
		final FBNetworkElement fbnElement = ie.getFBNetworkElement();
		FBNetwork containedNetwork = null;
		if (fbnElement instanceof final SubApp subapp) {
			containedNetwork = subapp.getSubAppNetwork();
		} else if (fbnElement instanceof final CFBInstance cfb) {
			containedNetwork = cfb.getCfbNetwork();
		}

		return (containedNetwork != null) && containedNetwork.equals(fbNetwork);
	}

	protected static boolean isExpandedSubappPin(final IInterfaceElement pin) {
		return pin.getFBNetworkElement() instanceof final SubApp subapp && subapp.isUnfolded();
	}

	public static void selectInterfaceElement(final IInterfaceElement element, final IEditorPart editor) {
		final GraphicalViewer currentViewer = HandlerHelper.getViewer(editor);
		if (!HandlerHelper.selectElement(element, currentViewer)) {
			// we have a subappcrossing element
			TargetInterfaceElementEditPart.openInBreadCrumb(element);
		}
	}
}
