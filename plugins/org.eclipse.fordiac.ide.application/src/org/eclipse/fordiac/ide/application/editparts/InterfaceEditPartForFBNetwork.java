/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, fortiss GmbH,
 *  						Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Waldemar Eisenmenger, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber, Bianca Wiesmayr - connection methods for unfolded subapp
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.policies.AdapterNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.EventNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.VariableNodeEditPolicy;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles.AnnotationBorder;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerDataTypeImpl;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.ui.IEditorPart;

/**
 * The edit part for interface elements of FBs and Subapps shown in FBNetwork
 * editors
 */
public class InterfaceEditPartForFBNetwork extends InterfaceEditPart {

	public static class VarInputConnAnchor extends FixedAnchor {

		private final InterfaceEditPartForFBNetwork ieEP;
		private IFigure valueFigure;

		public VarInputConnAnchor(final InterfaceEditPartForFBNetwork ieEP) {
			super(ieEP.getFigure(), true);
			this.ieEP = ieEP;
		}

		@Override
		public Point getLocation(final Point reference) {
			if (valueHasAnnotation()) {
				final IFigure fig = getValueFigure();
				if (fig != null) {
					final Rectangle bounds = fig.getBounds().getCopy();
					fig.translateToAbsolute(bounds);
					return bounds.getLeft();
				}
			}
			return super.getLocation(reference);
		}

		private boolean valueHasAnnotation() {
			final IFigure fig = getValueFigure();
			if (fig != null) {
				return fig.getBorder() instanceof AnnotationBorder;
			}
			return false;
		}

		private Value getValue() {
			return ((VarDeclaration) ieEP.getModel()).getValue();
		}

		IFigure getValueFigure() {
			if (valueFigure == null) {
				final EditPart ep = ieEP.getViewer().getEditPartForModel(getValue());
				if (ep instanceof final GraphicalEditPart graphicalEditPart) {
					valueFigure = graphicalEditPart.getFigure();
				}
			}
			return valueFigure;
		}
	}

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		if (isEvent()) {
			return new EventNodeEditPolicy();
		}
		if (isAdapter()) {
			return new AdapterNodeEditPolicy();
		}
		if (isVariable()) {
			return new VariableNodeEditPolicy();
		}
		return null;
	}

	@Override
	protected List<?> getModelSourceConnections() {
		final List<Object> connections = new ArrayList<>(super.getModelSourceConnections());
		if (isUnfoldedSubapp()) {
			if (isInput()) {
				connections.addAll(getModel().getOutputConnections());
			}
			return connections;
		}
		return connections;
	}

	@Override
	protected List<?> getModelTargetConnections() {
		final List<Object> connections = new ArrayList<>(super.getModelTargetConnections());
		if (isUnfoldedSubapp()) {
			if (!isInput()) {
				connections.addAll(getModel().getInputConnections());
			}
			return connections;
		}
		return connections;
	}

	@Override
	public void performRequest(final Request request) {
		if ((request.getType() == RequestConstants.REQ_OPEN) && canGoInto()) {
			// REQ_OPEN -> doubleclick
			goInto();
			return;
		}
		super.performRequest(request);
	}

	private boolean canGoInto() {
		final FBNetworkElement element = getModel().getFBNetworkElement();
		return ((element instanceof SubApp) || (element instanceof CFBInstance));
	}

	protected void goInto() {
		FBNetworkElement element = getModel().getFBNetworkElement();
		IInterfaceElement selectionElement = getModel();
		if ((element instanceof final SubApp subApp) && (needsOppositeSubapp(subApp))) {
			// we are mapped and the mirrored subapp located in the resource, get the one
			// from the application
			element = element.getOpposite();
			selectionElement = element.getInterfaceElement(selectionElement.getName());
		}
		final IEditorPart newEditor = OpenListenerManager.openEditor(element);
		final GraphicalViewer viewer = newEditor.getAdapter(GraphicalViewer.class);
		HandlerHelper.selectElement(selectionElement, viewer);
	}

	private static boolean needsOppositeSubapp(final SubApp subapp) {
		// if a subapp is mapped and we are at the resource side we would like to get
		// the opposite subapp
		return (subapp.isMapped() && EcoreUtil.isAncestor(subapp.getResource(), subapp));
	}

	protected boolean isUnfoldedSubapp() {
		return (getModel().getFBNetworkElement() instanceof final SubApp subApp && subApp.isUnfolded());
	}

	@Override
	protected FixedAnchor createTargetConAnchor() {
		if (getModel() instanceof VarDeclaration && getModel().isIsInput()) {
			return new VarInputConnAnchor(this);
		}
		return super.createTargetConAnchor();
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == ErrorMarkerDataTypeImpl.class) {
			final Adapter a = getContentAdapter();
			if (a.getTarget() instanceof final VarDeclaration vd
					&& vd.getType() instanceof final ErrorMarkerDataTypeImpl em) {
				return key.cast(em);
			}
		}

		return super.getAdapter(key);
	}

}
