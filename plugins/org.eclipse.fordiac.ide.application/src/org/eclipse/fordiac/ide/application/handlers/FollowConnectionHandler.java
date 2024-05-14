/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
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
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.IHandlerService;

public abstract class FollowConnectionHandler extends AbstractHandler {

	private static class OppositeSelectionDialog extends PopupDialog {

		private final List<IInterfaceElement> opposites;
		private final GraphicalViewer viewer;
		private final IInterfaceElement originPin;
		private final Point popupPosition;

		public OppositeSelectionDialog(final Shell parent, final List<IInterfaceElement> opposites,
				final GraphicalViewer viewer, final IInterfaceElement originPin, final Point popupPostion) {
			super(parent, INFOPOPUPRESIZE_SHELLSTYLE, true, false, false, false, false,
					Messages.FBPaletteViewer_SelectConnectionEnd, null);
			this.opposites = opposites;
			this.viewer = viewer;
			this.originPin = originPin;
			this.popupPosition = popupPostion;
		}

		@Override
		protected void adjustBounds() {
			super.adjustBounds();
			final Rectangle rect = getShell().getBounds();
			rect.x = popupPosition.x;
			rect.y = popupPosition.y;
			rect.width += 6;
			rect.height += 6;
			getShell().setBounds(rect);
		}

		@Override
		protected Control createTitleMenuArea(final Composite parent) {
			final Composite titleAreaComposite = (Composite) super.createTitleMenuArea(parent);

			final GridData gdLabel = new GridData(GridData.FILL);
			gdLabel.horizontalIndent = 5;
			titleAreaComposite.setLayoutData(gdLabel);
			return titleAreaComposite;
		}

		private String getPinName(final IInterfaceElement iElem) {
			final StringBuilder sb = new StringBuilder();
			if (iElem == null) {
				return sb.toString();
			}
			if (isInSameNetwork(originPin, iElem)) {
				sb.append(iElem.getFBNetworkElement().getName());
			} else {
				sb.append(iElem.getFBNetworkElement().getQualifiedName());
				sb.delete(0, sb.indexOf(".") + 1); //$NON-NLS-1$
			}
			sb.append('.');
			sb.append(iElem.getName());
			return sb.toString();
		}

		private static boolean isInSameNetwork(final IInterfaceElement src, final IInterfaceElement dest) {
			return src != null && dest != null
					&& src.getFBNetworkElement().getFbNetwork().equals(dest.getFBNetworkElement().getFbNetwork());
		}

		@Override
		protected Control createDialogArea(final Composite parent) {

			final Composite dialogArea = (Composite) super.createDialogArea(parent);

			final ListViewer listViewer = new ListViewer(dialogArea, SWT.SIMPLE);
			listViewer.setContentProvider(new ArrayContentProvider());
			listViewer.setLabelProvider(new LabelProvider() {

				@Override
				public String getText(final Object element) {
					if (element instanceof final IInterfaceElement iElem) {
						return getPinName(iElem);
					}
					return super.getText(element);
				}
			});
			listViewer.setInput(opposites.toArray());

			listViewer.addSelectionChangedListener(event -> selectInterfaceElement(viewer,
					(IInterfaceElement) event.getStructuredSelection().getFirstElement()));

			// on enter close the view
			listViewer.getControl().addKeyListener(new FollowConnectionKeyListener(dialogArea));

			final GridData gd = new GridData(GridData.CENTER);
			gd.horizontalIndent = 3;
			gd.verticalIndent = 2;
			dialogArea.setLayoutData(gd);

			listViewer.setSelection(new StructuredSelection(listViewer.getElementAt(0)), true);
			return dialogArea;
		}

		private static IHandlerService getHandlerService() {
			final IWorkbench wb = PlatformUI.getWorkbench();
			final IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
			final IWorkbenchPage page = window.getActivePage();

			final IWorkbenchPart active = page.getActivePart();
			return active.getSite().getService(IHandlerService.class);

		}

		private static void invokeFollowRightConnectionHandler() {
			try {
				getHandlerService().executeCommand("org.eclipse.fordiac.ide.application.commands.followRightConnection", //$NON-NLS-1$
						null);
			} catch (final Exception e) {
				throw new RuntimeException("followRightConnection.command not found"); //$NON-NLS-1$
			}
		}

		private static void invokeFollowLeftConnectionHandler() {
			try {
				getHandlerService().executeCommand("org.eclipse.fordiac.ide.application.commands.followLeftConnection", //$NON-NLS-1$
						null);
			} catch (final Exception e) {
				throw new RuntimeException("followLeftConnection.command not found"); //$NON-NLS-1$
			}
		}

		private class FollowConnectionKeyListener implements KeyListener {
			private final Composite dialogArea;

			public FollowConnectionKeyListener(final Composite dialogArea) {
				this.dialogArea = dialogArea;
			}

			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.character == SWT.CR) {
					dialogArea.getShell().close();
				}
				if (!opposites.get(0).getInputConnections().isEmpty()) {
					handleRight(e);
				} else {
					handleLeft(e);
				}

			}

			@Override
			public void keyReleased(final KeyEvent e) {
				// do nothing here
			}

			private void handleRight(final KeyEvent e) {
				if ((e.stateMask == SWT.CTRL) && (e.keyCode == SWT.ARROW_LEFT)) {
					selectInterfaceElement(viewer, originPin);
					dialogArea.getShell().close();
				}
				if (e.stateMask == SWT.CTRL && e.keyCode == SWT.ARROW_RIGHT) {
					invokeFollowRightConnectionHandler();
					dialogArea.getShell().close();
				}
			}

			private void handleLeft(final KeyEvent e) {
				if ((e.stateMask == SWT.CTRL) && (e.keyCode == SWT.ARROW_RIGHT)) {
					selectInterfaceElement(viewer, originPin);
					dialogArea.getShell().close();
				}
				if (e.stateMask == SWT.CTRL && e.keyCode == SWT.ARROW_LEFT) {
					invokeFollowLeftConnectionHandler();
					dialogArea.getShell().close();
				}
			}
		}

	}

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
		if (useTargetPins(iep)) {
			return getTargetPins(iep);
		}
		final IInterfaceElement ie = iep.getModel();
		final EList<Connection> connList = getConnectionList(ie);
		return connList.stream().map(con -> (con.getSource().equals(ie) ? con.getDestination() : con.getSource()))
				.toList();
	}

	protected List<IInterfaceElement> resolveTargetPins(final List<IInterfaceElement> opposites,
			final GraphicalViewer viewer) {
		final List<IInterfaceElement> resolvedOpposites = new ArrayList<>();
		for (final IInterfaceElement element : opposites) {
			final EditPart ep = (EditPart) (viewer.getEditPartRegistry().get(element));
			if ((ep instanceof final InterfaceEditPart iep) && isExpandedSubappPin(element)) {
				if (iep.isInput()) {
					resolvedOpposites.addAll(getConnectionOposites(iep));
				} else {
					resolvedOpposites.addAll(getTargetPins(iep));
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
		return iep.getChildren().stream().filter(TargetInterfaceElementEditPart.class::isInstance)
				.map(ep -> (TargetInterfaceElementEditPart) ep).map(ep -> ep.getModel().getRefElement()).toList();
	}

	private static boolean isBorderElement(final InterfaceEditPart iep) {
		return iep.getChildren().stream().anyMatch(TargetInterfaceElementEditPart.class::isInstance); // or is input of
																										// subapp;
	}

	protected static boolean isInsideSubappOrViewer(final IInterfaceElement ie, final FBNetwork fbNetwork) {
		final FBNetworkElement fbnElement = ie.getFBNetworkElement();
		return ((fbnElement instanceof SubApp) || (fbnElement instanceof CFBInstance))
				&& (!fbNetwork.equals(fbnElement.eContainer()));
	}

	private static void showOppositeSelectionDialog(final List<IInterfaceElement> opposites, final ExecutionEvent event,
			final GraphicalViewer viewer, final IInterfaceElement originPin) throws ExecutionException {

		selectInterfaceElement(viewer, opposites.get(0));
		viewer.flush();
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		final IFigure figure = ((InterfaceEditPart) ((IStructuredSelection) selection).getFirstElement()).getFigure();

		final org.eclipse.draw2d.geometry.Point pinLocation = figure.getLocation();
		pinLocation.y += figure.getBounds().height * 1.5f;
		figure.translateToAbsolute(pinLocation);
		final Point pinLocationDisplayCoordinates = viewer.getControl()
				.toDisplay(new Point(pinLocation.x, pinLocation.y));

		final OppositeSelectionDialog dialog = new OppositeSelectionDialog(HandlerUtil.getActiveShellChecked(event),
				opposites, viewer, originPin, pinLocationDisplayCoordinates);
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
			final IInterfaceElement originPin, final List<IInterfaceElement> opposites) throws ExecutionException {
		if (!opposites.isEmpty()) {
			if (opposites.size() == 1) {
				selectInterfaceElement(viewer, opposites.getFirst());
			} else {
				showOppositeSelectionDialog(opposites, event, viewer, originPin);
			}
		}
	}

	protected static IInterfaceElement calcInternalOppositePin(final EList<? extends IInterfaceElement> source,
			final EList<? extends IInterfaceElement> destination, final InterfaceEditPart pin) {

		final int sourceIndex = source.stream().filter(IInterfaceElement::isVisible).toList().indexOf(pin.getModel());
		final var visibleDestinations = destination.stream().filter(IInterfaceElement::isVisible).toList();

		if (sourceIndex == -1) {
			return visibleDestinations.get(0);
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

	protected static void selectInterfaceElement(final GraphicalViewer viewer, final IInterfaceElement element) {
		if (!HandlerHelper.selectElement(element, viewer)) {
			// we have a subappcrossing element
			TargetInterfaceElementEditPart.openInBreadCrumb(element);
		}
	}
}
