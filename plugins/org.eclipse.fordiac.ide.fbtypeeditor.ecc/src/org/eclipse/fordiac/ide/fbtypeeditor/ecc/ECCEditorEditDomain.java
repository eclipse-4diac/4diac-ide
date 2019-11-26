/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Kirill Dorofeev - extended to support on-the-fly transitions and states creation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateECStateCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateTransitionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ConnCreateDirectEditDragTrackerProxy;
import org.eclipse.fordiac.ide.gef.tools.AdvancedPanningSelectionTool;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.tools.ConnectionDragCreationTool;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.IEditorPart;

final class ECCEditorEditDomain extends FBTypeEditDomain {
	private static class StateCreationTool extends CreationTool {

		private StateCreationFactory stateFactory = new StateCreationFactory();
		private ActionCreationFactory actionFactory = new ActionCreationFactory();

		public StateCreationTool() {
			setFactory(stateFactory);
			setUnloadWhenFinished(false);
		}

		@Override
		protected void handleFinished() {
			super.handleFinished();
			handleMove();
		}

		@Override
		protected boolean updateTargetUnderMouse() {
			boolean changed = super.updateTargetUnderMouse();
			if (changed) {
				if (getTargetEditPart() instanceof ECStateEditPart
						|| getTargetEditPart() instanceof ECActionAlgorithmEditPart
						|| getTargetEditPart() instanceof ECActionOutputEventEditPart) {
					setFactory(actionFactory);
				} else {
					setFactory(stateFactory);
				}
			}
			return changed;
		}

		public void setLocationActivation(Point point) {
			getCurrentInput().setMouseLocation(point.x, point.y);
			handleMove();
		}

	}

	private static class ECCPanningSelectionTool extends AdvancedPanningSelectionTool {
		public Point getLastLocation() {
			return ((LocationRequest) super.getTargetHoverRequest()).getLocation();
		}

		public void changeCursor() {
			if (getDragTracker() instanceof ConnCreateDirectEditDragTrackerProxy) {
				ConnectionDragCreationTool tool = ((ConnCreateDirectEditDragTrackerProxy) getDragTracker())
						.getConnectionTool();
				tool.setDisabledCursor(SharedCursors.CURSOR_TREE_ADD);
			}
		}
	}

	private static class TransitionStateCreationTool extends CreationTool {
		private ECC ecc;
		private Point point;
		private ECState sourceState;

		public TransitionStateCreationTool(ECC ecc) {
			this.ecc = ecc;
			setFactory(new StateCreationFactory());
			setUnloadWhenFinished(false);
		}
		
		public void setSourceState(ECState state) {
			this.sourceState = state;
		}

		public void setLocationActivation(Point point) {
			this.point = point;
			handleMove();
		}

		public void performCreation() {
			ECState destState = (ECState) getFactory().getNewObject();
			destState.setX(point.x);
			destState.setY(point.y);
			CreateECStateCommand createStateCommand = new CreateECStateCommand(destState, point, ecc);
			CreateTransitionCommand createTransitionCommand = new CreateTransitionCommand(sourceState, destState, null);
			CompoundCommand compCom = new CompoundCommand();
			compCom.add(createStateCommand);
			compCom.add(createTransitionCommand);
			setCurrentCommand(compCom);
			performCreation(1);
		}
	}

	private StateCreationTool stateCreationTool = new StateCreationTool();
	private TransitionStateCreationTool transitionStateCreationTool = new TransitionStateCreationTool(
			((ECCEditor) getEditorPart()).getFbType().getECC());
	private boolean transition = false;
	private boolean createTransitionAndState = false;

	ECCEditorEditDomain(IEditorPart editorPart, CommandStack commandStack) {
		super(editorPart, commandStack);
		setDefaultTool(new ECCPanningSelectionTool());
	}

	@Override
	public void keyDown(KeyEvent keyEvent, EditPartViewer viewer) {
		if (keyEvent.keyCode == SWT.MOD1) { // Ctrl or Command key was pressed
			if (transition) {
				createTransitionAndState = true;
				((ECCPanningSelectionTool) getDefaultTool()).changeCursor();
				super.keyDown(keyEvent, viewer);
			} else {
				setActiveTool(stateCreationTool);
				super.keyDown(keyEvent, viewer);
				stateCreationTool.setLocationActivation(((ECCPanningSelectionTool) getDefaultTool()).getLastLocation());
			}
		} else {
			super.keyDown(keyEvent, viewer);
		}
	}

	@Override
	public void keyUp(KeyEvent keyEvent, EditPartViewer viewer) {
		if (keyEvent.keyCode == SWT.MOD1 && !transition) { // Ctrl or Command key was pressed
			setActiveTool(getDefaultTool());
		}
		super.keyUp(keyEvent, viewer);
	}

	@Override
	public void mouseDrag(MouseEvent mouseEvent, EditPartViewer viewer) {
		if (((AdvancedPanningSelectionTool) getDefaultTool()).getTargetEditPart() instanceof ECStateEditPart) {
			transition = true;
			transitionStateCreationTool.setSourceState((ECState) ((ECStateEditPart) (((AdvancedPanningSelectionTool) getDefaultTool())
					.getTargetEditPart())).getModel());
		}
		super.mouseDrag(mouseEvent, viewer);
	}

	@Override
	public void mouseUp(MouseEvent mouseEvent, EditPartViewer viewer) {
		super.mouseUp(mouseEvent, viewer);
		if (transition) {
			if (createTransitionAndState && ((AdvancedPanningSelectionTool) getDefaultTool())
					.getTargetEditPart() instanceof FreeformGraphicalRootEditPart) {
				transitionStateCreationTool
						.setLocationActivation(((ECCPanningSelectionTool) getDefaultTool()).getLastLocation());
				setActiveTool(transitionStateCreationTool);
				transitionStateCreationTool.performCreation();
				setActiveTool(getDefaultTool());
				createTransitionAndState = false;
			}
			transition = false;
			setActiveTool(getDefaultTool());
		}
	}
}
