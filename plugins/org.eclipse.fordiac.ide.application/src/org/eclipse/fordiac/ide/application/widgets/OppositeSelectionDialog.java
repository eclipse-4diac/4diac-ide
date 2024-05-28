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
 *   Fabio Gandolfi - refactored this class to be the base class of the
 *                    followConnection handlers
 *   Alois Zoitl - extracted OppositeSelectionDialog from FollowConnectionHandler
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.widgets;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.handlers.FollowConnectionHandler;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

public class OppositeSelectionDialog extends PopupDialog {

	private final List<IInterfaceElement> opposites;
	private final IInterfaceElement originPin;
	private final Point popupPosition;
	private final IEditorPart editor;
	private boolean allowClosing;

	public OppositeSelectionDialog(final List<IInterfaceElement> opposites, final IInterfaceElement originPin,
			final Control viewerControl, final IFigure popupRefFigure, final IEditorPart editor) {
		super(viewerControl.getShell(), INFOPOPUPRESIZE_SHELLSTYLE, true, false, false, false, false,
				Messages.FBPaletteViewer_SelectConnectionEnd, null);
		this.opposites = opposites;
		this.originPin = originPin;
		this.popupPosition = getPopupPosition(viewerControl, popupRefFigure);
		this.editor = editor;
		this.allowClosing = true;
	}

	private static Point getPopupPosition(final Control viewerControl, final IFigure popupRefFigure) {
		final org.eclipse.draw2d.geometry.Point pinLocation = popupRefFigure.getLocation();
		pinLocation.y += popupRefFigure.getBounds().height * 1.5f;
		popupRefFigure.translateToAbsolute(pinLocation);
		return viewerControl.toDisplay(new Point(pinLocation.x, pinLocation.y));
	}

	public void setPopupClosable(final boolean closeable) {
		this.allowClosing = closeable;
	}

	public boolean isCloseable() {
		return allowClosing;
	}

	@Override
	public boolean close() {
		if (isCloseable()) {
			return super.close();
		}
		return false;
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

		listViewer.addSelectionChangedListener(event -> FollowConnectionHandler
				.selectInterfaceElement((IInterfaceElement) event.getStructuredSelection().getFirstElement(), editor));

		listViewer.getControl().addKeyListener(new FollowConnectionKeyListener(dialogArea));

		listViewer.getControl().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(final FocusEvent e) {
				setPopupClosable(false);
				getShell().getDisplay().asyncExec(() -> {
					if (getShell() != null && !getShell().isDisposed()) {
						listViewer.getControl().setFocus();
					}
				});
			}

			@Override
			public void focusGained(final FocusEvent e) {
				setPopupClosable(true);
			}

		});

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

		private void closePopup() {
			setPopupClosable(true);
			dialogArea.getShell().close();
		}

		@Override
		public void keyPressed(final KeyEvent e) {
			if (e.character == SWT.CR || e.character == SWT.ESC) {
				closePopup();
			}
			if (!opposites.getFirst().getInputConnections().isEmpty()) {
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
				FollowConnectionHandler.selectInterfaceElement(originPin, editor);
				closePopup();
			}
			if (e.stateMask == SWT.CTRL && e.keyCode == SWT.ARROW_RIGHT) {
				invokeFollowRightConnectionHandler();
				closePopup();
			}
		}

		private void handleLeft(final KeyEvent e) {
			if ((e.stateMask == SWT.CTRL) && (e.keyCode == SWT.ARROW_RIGHT)) {
				FollowConnectionHandler.selectInterfaceElement(originPin, editor);
				closePopup();
			}
			if (e.stateMask == SWT.CTRL && e.keyCode == SWT.ARROW_LEFT) {
				invokeFollowLeftConnectionHandler();
				closePopup();
			}
		}
	}

}