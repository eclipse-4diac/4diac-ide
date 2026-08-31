/*******************************************************************************
 * Copyright (c) 2011, 2024 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Fixed adapter type list problem
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fbtypeeditor.figures.FBInterfaceRootFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.model.CommentPinProperty;
import org.eclipse.fordiac.ide.fbtypeeditor.model.PinPropertiesCache;
import org.eclipse.fordiac.ide.fbtypeeditor.model.PinProperty;
import org.eclipse.fordiac.ide.fbtypeeditor.model.TypePinProperty;
import org.eclipse.fordiac.ide.fbtypeeditor.model.WithPinProperty;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDiagramEditPart;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.emf.SingleRecursiveContentAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.widgets.Display;

public class FBTypeRootEditPart extends AbstractDiagramEditPart {

	private Adapter interfaceAdapter;
	private final PinPropertiesCache pinPropCache = new PinPropertiesCache();

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof final FBTypeEditPart fbTypeEP) {
			getFigure().getFBColumn().add(fbTypeEP.getFigure());
		} else if (childEditPart.getModel() instanceof final PinProperty pinProp
				&& childEditPart instanceof final GraphicalEditPart gep) {
			getPinPropTargetFigure(pinProp).add(gep.getFigure());
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof final FBTypeEditPart fbTypeEP) {
			getFigure().getFBColumn().remove(fbTypeEP.getFigure());
		} else if (childEditPart.getModel() instanceof final PinProperty pinProp
				&& childEditPart instanceof final GraphicalEditPart gep) {
			getPinPropTargetFigure(pinProp).remove(gep.getFigure());
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	private IFigure getPinPropTargetFigure(final PinProperty pinProp) {
		if (pinProp.isInput()) {
			return switch (pinProp) {
			case final TypePinProperty _ -> getFigure().getInputTypesColumn();
			case final CommentPinProperty _ -> getFigure().getInputCommentsColumn();
			case final WithPinProperty _ -> getFigure().getInputWithColumn();
			};
		}
		return switch (pinProp) {
		case final TypePinProperty _ -> getFigure().getOutputTypesColumn();
		case final CommentPinProperty _ -> getFigure().getOutputCommentsColumn();
		case final WithPinProperty _ -> getFigure().getOutputWithColumn();
		};
	}

	@Override
	protected ConnectionRouter createConnectionRouter(final IFigure figure) {
		return new ShortestPathConnectionRouter(figure);
	}

	@Override
	protected IFigure createFigure() {
		return new FBInterfaceRootFigure(getInputWithColSize(), getOutputWithColSize());
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().getInterfaceList().eAdapters().add(getInterfaceAdapter());
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().getInterfaceList().eAdapters().remove(getInterfaceAdapter());
		}
	}

	public Adapter getInterfaceAdapter() {
		if (null == interfaceAdapter) {
			interfaceAdapter = new SingleRecursiveContentAdapter() {
				@Override
				public void notifyChanged(final Notification notification) {
					super.notifyChanged(notification);
					final int type = notification.getEventType();
					switch (type) {
					case Notification.ADD, Notification.ADD_MANY, Notification.REMOVE, Notification.REMOVE_MANY:
						Display.getDefault().asyncExec(FBTypeRootEditPart.this::refreshChildren);
						break;
					case Notification.SET:
						break;
					default:
						break;
					}

					if (LibraryElementPackage.Literals.VAR_DECLARATION__WITHS == notification.getFeature()
							|| LibraryElementPackage.Literals.EVENT__WITH == notification.getFeature()) {
						getFigure().setInputWithColumnWidth(getInputWithColSize());
						getFigure().setOutputWithColumnWidth(getOutputWithColSize());
					}

				}
			};
		}
		return interfaceAdapter;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
	}

	@Override
	public FBType getModel() {
		return (FBType) super.getModel();
	}

	@Override
	public FBInterfaceRootFigure getFigure() {
		return (FBInterfaceRootFigure) super.getFigure();
	}

	@Override
	protected List<?> getModelChildren() {
		final ArrayList<Object> children = new ArrayList<>();
		children.add(getModel());
		children.addAll(pinPropCache.getCurrentPinProperties(getModel().getInterfaceList()));
		return children;
	}

	@Override
	protected void reorderChild(final EditPart child, final int index) {
		if (child.getModel() instanceof final PinProperty pinProp && child instanceof final GraphicalEditPart gep) {
			// for pin properties we need to ensure that the constraints are preserved
			final IFigure childFigure = gep.getFigure();
			final LayoutManager layout = getPinPropTargetFigure(pinProp).getLayoutManager();
			Object constraint = null;
			if (layout != null) {
				constraint = layout.getConstraint(childFigure);
			}
			super.reorderChild(child, index);
			setLayoutConstraint(child, childFigure, constraint);
		} else {
			super.reorderChild(child, index);
		}
	}

	private int getInputWithColSize() {
		return getWithColSize(getModel().getInterfaceList().getEventInputs());
	}

	private int getOutputWithColSize() {
		return getWithColSize(getModel().getInterfaceList().getEventOutputs());
	}

	private static int getWithColSize(final EList<Event> eList) {
		return (int) (WithAnchor.WITH_DISTANCE * (1 + eList.stream().filter(e -> !e.getWith().isEmpty()).count()));
	}

	public void refreshPinPropertyVisuals(final Object model) {
		getChildren().stream()
				.filter(child -> child.getModel() instanceof final PinProperty pinProp && pinProp.getPin() == model)
				.forEach(GraphicalEditPart::refresh);

	}
}
