/*******************************************************************************
 * Copyright (c) 2008, 2012, 2014, 2016, 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH,  
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.SetPositionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.commands.DeviceCreateCommand;
import org.eclipse.fordiac.ide.systemconfiguration.commands.SegmentCreateCommand;
import org.eclipse.fordiac.ide.systemconfiguration.commands.SegmentSetConstraintCommand;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.SegmentEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.handles.ResizeHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

public class SystemConfXYLayoutEditPolicy extends XYLayoutEditPolicy {
	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		if (child instanceof SegmentEditPart) {
			return new ResizableEditPolicy() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				protected List createSelectionHandles() {
					List list = new ArrayList();
					list.add(new ResizeHandle((GraphicalEditPart) getHost(),
							PositionConstants.EAST));
					list.add(new ResizeHandle((GraphicalEditPart) getHost(),
							PositionConstants.WEST));
					list.add(new ModifiedMoveHandle((GraphicalEditPart) getHost(),
							new Insets(0, 2, 0, 2), 20));
					return list;
				}
			};
		} 
		return new ModifiedNonResizeableEditPolicy();
	}

	@Override
	protected Command getDeleteDependantCommand(final Request request) {
		return null;
	}

	@Override
	protected Command createChangeConstraintCommand(
			final ChangeBoundsRequest request, final EditPart child,
			final Object constraint) {
		if(constraint instanceof Rectangle){
			Rectangle rec = new Rectangle((Rectangle) constraint);
			if (child instanceof SegmentEditPart) {
				SegmentEditPart seg = (SegmentEditPart) child;
				return new SegmentSetConstraintCommand(seg.getModel(), rec, request);
			}
			// return a command that can move a "PositionableElement"
			if (child instanceof AbstractViewEditPart 
						&& child.getModel() instanceof PositionableElement){
				PositionableElement temp = (PositionableElement) child.getModel();
				return new SetPositionCommand(temp, request, rec);
			}
		}
		return null;
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if (request == null) {
			return null;
		}
		Object childClass = request.getNewObjectType();
		Rectangle constraint = (Rectangle) getConstraintFor(request);
		if (childClass instanceof org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry) {
			org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry type = (org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry) request
					.getNewObjectType();
			if (getHost().getModel() instanceof SystemConfiguration) {
				return new DeviceCreateCommand(type, (SystemConfiguration) getHost()
						.getModel(), new Rectangle(constraint.getLocation().x, constraint
						.getLocation().y, -1, -1));
			}
		}
		if (childClass instanceof org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry) {
			org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry type = (org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry) request
					.getNewObjectType();
			if (getHost().getModel() instanceof SystemConfiguration) {
				return new SegmentCreateCommand(type, (SystemConfiguration) getHost().getModel(), constraint); 
			}
		}
		return null;
	}

	@Override
	protected Command getAddCommand(final Request generic) {
		return null;
	}
}
