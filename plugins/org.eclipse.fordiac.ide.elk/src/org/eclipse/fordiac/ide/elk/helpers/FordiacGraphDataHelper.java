/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 * 				 2021, 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber, Bianca Wiesmayr, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk.helpers;

import org.eclipse.elk.graph.ElkNode;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.elk.FordiacLayoutMapping;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.GraphicalEditPart;

public class FordiacGraphDataHelper {

	private static final int INSTANCE_COMMENT_OFFSET = 8;

	public static void calculate(final FordiacLayoutMapping mapping) {
		if (mapping.getNetworkEditPart() instanceof final UnfoldedSubappContentEditPart subapp) {
			final int y = subapp.getParent().getFigure().getBounds().y;
			final int input = subapp.getParent().getInterfacePositionMap().getInputDirectEndWithoutEvents() - y;
			final int output = subapp.getParent().getInterfacePositionMap().getOutputDirectEndWithoutEvents() - y;
			calculateNodePositionsRecursively(mapping, mapping.getLayoutGraph(), 0,
					Math.max(Math.max(input, output), 0));
		} else {
			calculateNodePositionsRecursively(mapping, mapping.getLayoutGraph(), 0, INSTANCE_COMMENT_OFFSET);
		}
	}

	private static void calculateNodePositionsRecursively(final FordiacLayoutMapping mapping, final ElkNode node,
			final double parentX, final double parentY) {
		final GraphicalEditPart ep = (GraphicalEditPart) mapping.getGraphMap().get(node);
		final int calculatedX = (int) (node.getX() + parentX);
		final int calculatedY = (int) (node.getY() + parentY);

		if (ep != mapping.getNetworkEditPart()) {
			setPosition(mapping, node, ep, calculatedX, calculatedY);
		}

		// position inside of group -> relative
		// outside -> absolute
		if (ep instanceof GroupEditPart) {
			final int groupCommentHeight = (int) node.getLabels().get(0).getHeight();
			node.getChildren()
					.forEach(child -> calculateNodePositionsRecursively(mapping, child, 0, -groupCommentHeight));
		} else {
			node.getChildren()
					.forEach(child -> calculateNodePositionsRecursively(mapping, child, calculatedX, calculatedY));
		}
	}

	private static void setPosition(final FordiacLayoutMapping mapping, final ElkNode node, final GraphicalEditPart ep,
			final int calculatedX, final int calculatedY) {
		final boolean isContainer = ep instanceof AbstractContainerContentEditPart;
		final boolean isNoNetwork = !(ep instanceof AbstractFBNetworkEditPart);
		// holds for FBs, groups and unfolded subapps(currently deactivated)
		if (isContainer || isNoNetwork) {
			final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
			pos.setX(calculatedX);
			pos.setY(calculatedY);
			mapping.getLayoutData().addPosition((FBNetworkElement) ep.getModel(), pos);

			if (ep instanceof GroupEditPart) {
				mapping.getLayoutData().addGroup((Group) ep.getModel(), (int) node.getHeight(), (int) node.getWidth());
			}
		}
	}

	private FordiacGraphDataHelper() {
		// nothing to do here
	}

}
