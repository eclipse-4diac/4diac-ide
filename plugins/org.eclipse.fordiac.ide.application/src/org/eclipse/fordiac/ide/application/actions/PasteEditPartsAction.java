/*******************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 *                          Johannes Kepler University,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Andren
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed copy/paste handling
 *   Bianca Wiesmayr - fixed copy/paste position
 *   Bianca Wiesmayr, Daniel Lindhuber, Lukas Wais - fixed ctrl+c, ctrl+v, ctrl+v
 *   Fabio Gandolfi - growing frame for copy in group & subapp
 *   Alois Zoitl - harmonized cut and copy paste
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.commands.CopyElementsToGroupCommand;
import org.eclipse.fordiac.ide.application.commands.PasteCommand;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.application.utilities.GetEditPartFromGraficalViewerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.ui.FordiacClipboard;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/** The Class PasteEditPartsAction. */
public class PasteEditPartsAction extends SelectionAction {

	private Point pasteRefPosition;

	/**
	 * Instantiates a new paste edit parts action.
	 *
	 * @param editor the editor
	 */
	public PasteEditPartsAction(final IWorkbenchPart editor) {
		super(editor);
	}

	@Override
	protected boolean calculateEnabled() {
		final FBNetwork fbNetwork = getFBNetwork();
		return (null != fbNetwork) && !getClipboardContents().isEmpty();
	}

	protected Command createPasteCommand() {
		final FBNetwork fbNetwork = getFBNetwork();

		if (null != fbNetwork) {
			final AbstractContainerContentEditPart editPart = GetEditPartFromGraficalViewerHelper
					.findAbstractContainerContentEditPartAtPosition((IEditorPart) getWorkbenchPart(),
							new org.eclipse.draw2d.geometry.Point(pasteRefPosition.x, pasteRefPosition.y), fbNetwork);
			if (editPart != null) {
				Command cmd = null;
				if ((editPart instanceof final GroupContentEditPart groupEP)
						&& getClipboardContents().elements().stream().noneMatch(Group.class::isInstance)) {
					cmd = createPasteCommandForGroup(groupEP);
				}

				if (editPart instanceof final UnfoldedSubappContentEditPart subAppEP) {
					cmd = createPasteCommandForSubApp(subAppEP);
				}
				if (cmd != null) {
					return new ResizeGroupOrSubappCommand(editPart, cmd);
				}
			}
			final GraphicalViewer graphicalViewer = getWorkbenchPart().getAdapter(GraphicalViewer.class);
			if (graphicalViewer.getEditPartForModel(fbNetwork) instanceof final GraphicalEditPart graphicalEditPart) {
				pasteRefPosition = getSnapCorrectPastePos(graphicalEditPart);
			}
			return new PasteCommand(getClipboardContents(), fbNetwork, pasteRefPosition);
		}
		return new CompoundCommand();
	}

	private Command createPasteCommandForSubApp(final UnfoldedSubappContentEditPart subAppContentEP) {
		final org.eclipse.draw2d.geometry.Point subAppOffset = getOffsetPosition(subAppContentEP);
		final Point pastePointInSubApp = getSnapCorrectPastePos(subAppContentEP);
		pastePointInSubApp.x -= subAppOffset.x;
		pastePointInSubApp.y -= subAppOffset.y;
		return new PasteCommand(getClipboardContents(), subAppContentEP.getModel(), pastePointInSubApp);
	}

	private Command createPasteCommandForGroup(final GroupContentEditPart group) {
		final PasteCommand pasteCommand = new PasteCommand(getClipboardContents(),
				group.getModel().getGroup().getFbNetwork(), getSnapCorrectPastePos(group));
		return new CopyElementsToGroupCommand(group.getModel().getGroup(), pasteCommand, getOffsetPosition(group));
	}

	private Point getSnapCorrectPastePos(final GraphicalEditPart gep) {
		final SnapToHelper helper = gep.getAdapter(SnapToHelper.class);
		if (helper != null) {
			final org.eclipse.draw2d.geometry.Point refPoint = new org.eclipse.draw2d.geometry.Point(pasteRefPosition.x,
					pasteRefPosition.y);
			gep.getFigure().translateToAbsolute(refPoint);
			final PrecisionPoint preciseLocation = new PrecisionPoint(refPoint);
			final PrecisionPoint result = new PrecisionPoint(refPoint);
			helper.snapPoint(null, PositionConstants.HORIZONTAL | PositionConstants.VERTICAL, preciseLocation, result);
			gep.getFigure().translateToRelative(result);
			return new Point(result.x, result.y);
		}
		return pasteRefPosition;
	}

	private static org.eclipse.draw2d.geometry.Point getOffsetPosition(final GraphicalEditPart gep) {
		return gep.getFigure().getClientArea().getTopLeft();
	}

	private static CopyPasteData getClipboardContents() {
		final Object obj = FordiacClipboard.INSTANCE.getGraphicalContents();
		if (obj instanceof final CopyPasteData copyPasteMessage) {
			return copyPasteMessage;
		}
		return CopyPasteData.EMPTY_COPY_PASTE_DATA;
	}

	@Override
	protected void init() {
		setId(ActionFactory.PASTE.getId());
		setText(Messages.PasteEditPartsAction_Text);
		final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
	}

	@Override
	public void runWithEvent(final Event event) {
		if (event.widget instanceof FigureCanvas) {
			// handles insertion via copy&paste
			setMouseLocationAsPastePos(event);
		}
		super.runWithEvent(event);
	}

	public void setMouseLocationAsPastePos(final Event event) {
		final FigureCanvas figureCanvas = (FigureCanvas) event.widget;
		final org.eclipse.draw2d.geometry.Point viewLocation = figureCanvas.getViewport().getViewLocation();
		org.eclipse.swt.graphics.Point mouseLocation = Display.getCurrent().getCursorLocation();
		mouseLocation = figureCanvas.toControl(mouseLocation.x, mouseLocation.y);

		if (figureCanvas.getBounds().contains(mouseLocation.x, mouseLocation.y)) {
			final ZoomManager zoomManager = ((FBNetworkEditor) getWorkbenchPart()).getZoomManger();
			mouseLocation.x += viewLocation.x;
			mouseLocation.y += viewLocation.y;
			setPastRefPosition(new org.eclipse.draw2d.geometry.Point(mouseLocation.x, mouseLocation.y)
					.scale(1.0 / zoomManager.getZoom()));
		} else {
			final Dimension visibleArea = figureCanvas.getViewport().getSize();
			setPastRefPosition(
					new Point(viewLocation.x + (visibleArea.width / 2), viewLocation.y + (visibleArea.height / 2)));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		final FordiacClipboard clipboard = FordiacClipboard.INSTANCE;
		if (clipboard.getGraphicalContents() instanceof CopyPasteData) {
			execute(createPasteCommand());
		}
		pasteRefPosition = null;
	}

	private FBNetwork getFBNetwork() {
		if (getWorkbenchPart() instanceof IEditorPart) {
			return getWorkbenchPart().getAdapter(FBNetwork.class);
		}
		return null;
	}

	public void setPastRefPosition(final Point pt) {
		pasteRefPosition = pt;
	}

	public void setPastRefPosition(final org.eclipse.draw2d.geometry.Point point) {
		setPastRefPosition(new Point(point.x, point.y));
	}

}
