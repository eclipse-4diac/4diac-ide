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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPartForFBNetwork;
import org.eclipse.fordiac.ide.application.editparts.SubAppInternalInterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class FollowConnectionHandler extends AbstractHandler {

	private static class OppositeSelectionDialog extends PopupDialog {

		private final List<IInterfaceElement> opposites;
		private final FBNetworkEditor editor;

		public OppositeSelectionDialog(Shell parent, List<IInterfaceElement> opposites, FBNetworkEditor editor) {
			super(parent, INFOPOPUPRESIZE_SHELLSTYLE, true, false, false, false, false,
					Messages.FBPaletteViewer_SelectConnectionEnd, null);
			this.opposites = opposites;
			this.editor = editor;
		}

		@Override
		protected void adjustBounds() {
			super.adjustBounds();
			Point pt = getShell().getDisplay().getCursorLocation();
			Rectangle rect = getShell().getBounds();
			rect.x = pt.x;
			rect.y = pt.y;
			getShell().setBounds(rect);
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite dialogArea = (Composite) super.createDialogArea(parent);
			ListViewer listViewer = new ListViewer(dialogArea, SWT.SINGLE);
			listViewer.setContentProvider(new ArrayContentProvider());
			listViewer.setLabelProvider(new LabelProvider() {

				@Override
				public String getText(Object element) {
					if (element instanceof IInterfaceElement) {
						IInterfaceElement iElem = (IInterfaceElement) element;
						String retVal = ""; //$NON-NLS-1$
						if (null != iElem.getFBNetworkElement()) {
							retVal = iElem.getFBNetworkElement().getName() + "."; //$NON-NLS-1$
						}
						return retVal + iElem.getName();
					}
					return super.getText(element);
				}
			});
			listViewer.setInput(opposites.toArray());

			listViewer.addSelectionChangedListener(
					event -> editor.selectElement(event.getStructuredSelection().getFirstElement()));

			// on enter close the view
			listViewer.getControl().addKeyListener(new KeyListener() {

				@Override
				public void keyReleased(KeyEvent e) {
					// we only want to close the window on enter presses
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.character == SWT.CR) {
						dialogArea.getShell().close();
					}
				}
			});

			return dialogArea;
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FBNetworkEditor editor = (FBNetworkEditor) HandlerUtil.getActiveEditor(event);

		List<IInterfaceElement> opposites = getConnectionOposites(HandlerUtil.getCurrentSelection(event));

		if (!opposites.isEmpty()) {
			if (opposites.size() == 1) {
				editor.selectElement(opposites.get(0));
			} else {
				showOppositeSelectionDialog(opposites, event, editor);
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(!getConnectionOposites(selection).isEmpty());
	}

	private static List<IInterfaceElement> getConnectionOposites(ISelection selection) {
		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if ((structuredSelection.size() == 1)
					&& (structuredSelection.getFirstElement() instanceof InterfaceEditPartForFBNetwork)) {
				// only if only one element is selected
				InterfaceEditPartForFBNetwork editPart = (InterfaceEditPartForFBNetwork) structuredSelection
						.getFirstElement();
				EList<Connection> connList = getConnectionList(editPart);
				return connList.stream().map(
						con -> (con.getSource().equals(editPart.getModel()) ? con.getDestination() : con.getSource()))
						.collect(Collectors.toList());
			}
		}
		return Collections.emptyList();
	}

	private static EList<Connection> getConnectionList(InterfaceEditPartForFBNetwork editPart) {
		if (editPart instanceof SubAppInternalInterfaceEditPart) {
			return editPart.getModel().isIsInput() ? editPart.getModel().getOutputConnections()
					: editPart.getModel().getInputConnections();
		}
		return editPart.isInput() ? editPart.getModel().getInputConnections()
				: editPart.getModel().getOutputConnections();
	}

	private static void showOppositeSelectionDialog(List<IInterfaceElement> opposites, ExecutionEvent event,
			FBNetworkEditor editor) throws ExecutionException {

		OppositeSelectionDialog dialog = new OppositeSelectionDialog(HandlerUtil.getActiveShellChecked(event),
				opposites, editor);

		dialog.open();
	}

}
