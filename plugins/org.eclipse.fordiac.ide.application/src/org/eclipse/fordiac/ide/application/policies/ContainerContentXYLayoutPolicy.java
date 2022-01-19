/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Alois Zoitl - initial implementation and/or documentation
 *   Alexander Lumplecker, Bianca Wiesmayr, Alois Zoitl - Bug: 568569
 *   Alois Zoitl - extracted most code into common base class for group
 *                 infrastructure
 *               - extracted this policy from the AbstractContainerContentEditPart
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.model.commands.change.ChangeGroupBoundsCommand;
import org.eclipse.fordiac.ide.model.commands.change.FBNetworkElementSetPositionCommand;
import org.eclipse.fordiac.ide.model.commands.change.SetPositionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class ContainerContentXYLayoutPolicy extends FBNetworkXYLayoutEditPolicy {
	@Override
	protected Command createChangeConstraintCommand(final ChangeBoundsRequest request, final EditPart child,
			final Object constraint) {
		// TODO constraint is the new position, can we somehow extract the a delta position?
		if ((child.getModel() instanceof PositionableElement) && (constraint instanceof Rectangle)) {
			final Rectangle constraintRect = (Rectangle) constraint;
			constraintRect.x += getHost().getOffsetPoint().x;
			constraintRect.y += getHost().getOffsetPoint().y;

			if (child.getModel() instanceof Group) {
				return new ChangeGroupBoundsCommand((Group) child.getModel(), request, (Rectangle) constraint);
			}
			if (child.getModel() instanceof FBNetworkElement) {
				return new FBNetworkElementSetPositionCommand((FBNetworkElement) child.getModel(), request,
						(Rectangle) constraint);
			}
			return new SetPositionCommand((PositionableElement) child.getModel(), request,
					(Rectangle) constraint);
		}
		return null;
	}

	@Override
	public AbstractContainerContentEditPart getHost() {
		return (AbstractContainerContentEditPart) super.getHost();
	}
}