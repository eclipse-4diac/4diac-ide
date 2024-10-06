/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.policies;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.handles.PlusHandle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.Handle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.handles.RelativeHandleLocator;

public abstract class AbstractInterfaceSelectionEditPolicy extends ModifiedNonResizeableEditPolicy {

	protected AbstractInterfaceSelectionEditPolicy(final int cornerDim, final Insets insets) {
		super(cornerDim, insets);
	}

	@Override
	protected void hideSelection() {
		super.hideSelection();
		getInterfaceEditPart().setInOutConnectionsWith(0);
	}

	@Override
	protected void showSelection() {
		super.showSelection();
		getInterfaceEditPart().setInOutConnectionsWith(2);
	}

	@Override
	protected List<Handle> createSelectionHandles() {
		final List<Handle> list = super.createSelectionHandles();
		final InterfaceEditPart part = getInterfaceEditPart();

		final PlusHandle top = createHandle(part, PositionConstants.NORTH);
		list.add(top);
		top.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(final MouseEvent me) {
				createInterfaceElement(false);
			}

			@Override
			public void mouseReleased(final MouseEvent me) {
				// Nothing to be done here
			}

			@Override
			public void mouseDoubleClicked(final MouseEvent me) {
				// Nothing to be done here
			}
		});

		final PlusHandle bottom = createHandle(part, PositionConstants.SOUTH);
		list.add(bottom);
		bottom.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(final MouseEvent me) {
				createInterfaceElement(true);
			}

			@Override
			public void mouseReleased(final MouseEvent me) {
				// Nothing to be done here
			}

			@Override
			public void mouseDoubleClicked(final MouseEvent me) {
				// Nothing to be done here
			}
		});
		return list;
	}

	private InterfaceEditPart getInterfaceEditPart() {
		return (InterfaceEditPart) getHost();
	}

	private IInterfaceElement getModel() {
		return (IInterfaceElement) getInterfaceEditPart().getModel();
	}

	private static PlusHandle createHandle(final InterfaceEditPart part, final int direction) {
		return new PlusHandle(part, new RelativeHandleLocator(part.getFigure(), direction) {
			@Override
			public void relocate(final IFigure target) {
				super.relocate(target);
				// assure that handle is not in the middle of the border but outside of it
				// TODO if this code has proven to work move it to 4diac specific locater in GEF
				// plugin
				final Rectangle targetBounds = target.getBounds();
				final Dimension targetSize = target.getPreferredSize();

				targetBounds.x += ((targetSize.width + 1) / 4) * getXModifcation();

				targetBounds.y += ((targetSize.height + 1) / 4) * getYModifcation();
				target.setBounds(targetBounds);
			}

			private int getYModifcation() {
				return switch (direction & PositionConstants.NORTH_SOUTH) {
				case PositionConstants.NORTH -> -1;
				case PositionConstants.SOUTH -> 1;
				default -> 0;
				};
			}

			private int getXModifcation() {
				return switch (direction & PositionConstants.EAST_WEST) {
				case PositionConstants.WEST -> -1;
				case PositionConstants.EAST -> 1;
				default -> 0;
				};
			}
		});
	}

	private void createInterfaceElement(final boolean after) {
		final List<? extends IInterfaceElement> list = getInterfaceElementList();
		int ref = list.indexOf(getModel());
		if (-1 != ref && after) {
			ref++;
		}
		final Command cmd = getIECreateCommand(getModel().getType(), ref);
		if (null != cmd) {
			AbstractDirectEditableEditPart.executeCommand(cmd);
		}
	}

	protected abstract List<? extends IInterfaceElement> getInterfaceElementList();

	protected abstract Command getIECreateCommand(DataType refElement, int ref);

}
