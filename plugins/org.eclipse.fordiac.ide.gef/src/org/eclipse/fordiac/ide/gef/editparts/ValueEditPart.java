/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GbmH, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added preference driven max width for value edit parts
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.annotation.AnnotableGraphicalEditPart;
import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelEvent;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles.AnnotationBorder;
import org.eclipse.fordiac.ide.gef.figures.ValueToolTipFigure;
import org.eclipse.fordiac.ide.gef.policies.ValueEditPartChangeEditPolicy;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueRefreshJob;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.ui.IEditorPart;

public class ValueEditPart extends AbstractGraphicalEditPart implements NodeEditPart, AnnotableGraphicalEditPart {

	private InterfaceEditPart parentPart;
	private InitialValueRefreshJob refreshJob;

	private static int maxWidth = -1;

	public static int getMaxWidth() {
		if (-1 == maxWidth) {
			final IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
			final int maxLabelSize = pf.getInt(DiagramPreferences.MAX_VALUE_LABEL_SIZE);
			final FontMetrics fm = FigureUtilities
					.getFontMetrics(JFaceResources.getFontRegistry().get(PreferenceConstants.DIAGRAM_FONT));
			maxWidth = (int) ((maxLabelSize + 2) * fm.getAverageCharacterWidth());
		}
		return maxWidth;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		super.activate();
		getModel().eAdapters().add(contentAdapter);
		// also add the adapter to parent to refresh the init value after type change
		getModel().getParentIE().eAdapters().add(contentAdapter);
		if (getViewer().getEditPartForModel(getModel().getParentIE()) instanceof final InterfaceEditPart iep) {
			parentPart = iep;
			final IFigure parentFigure = parentPart.getFigure();
			parentFigure.addAncestorListener(new AncestorListener() {

				@Override
				public void ancestorRemoved(final IFigure ancestor) {
					// Nothing to do here
				}

				@Override
				public void ancestorMoved(final IFigure ancestor) {
					if (parentFigure == ancestor) {
						refreshVisuals();
					}
				}

				@Override
				public void ancestorAdded(final IFigure ancestor) {
					// Nothing to do here
				}
			});
		}
		refreshVisuals();
		refreshValue();
	}

	protected Point calculatePos() {
		if (parentPart != null) {
			final Rectangle bounds = parentPart.getFigure().getBounds();
			int x = 0;
			if (isInput()) {
				x = bounds.x - 2 - calculateWidth();
			} else {
				x = (bounds.x + bounds.width + 2);
			}
			final int y = bounds.y;
			return new Point(x, y);
		}
		return new Point(0, 0);
	}

	protected void refreshPosition() {
		if (getParent() != null) {
			Rectangle bounds = null;
			final Point p = calculatePos();
			bounds = new Rectangle(p.x, p.y, calculateWidth(), -1);
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	private int calculateWidth() {
		int width = getFigure().getPreferredSize().width;
		width = Math.max(40, width);
		width = Math.min(width, getMaxWidth());
		return width;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		super.deactivate();
		if (refreshJob != null) {
			refreshJob.cancel();
			refreshJob = null;
		}
		getModel().eAdapters().remove(contentAdapter);
		if (getModel().getParentIE() != null) {
			getModel().getParentIE().eAdapters().remove(contentAdapter);
		}
	}

	private final Adapter contentAdapter = new AdapterImpl() {

		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getValue_Value().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_Type().equals(feature)) {
				refreshValue();
				refreshPosition();
			}
			super.notifyChanged(notification);
		}
	};

	/** The Class ValueFigure. */
	private static class ValueFigure extends Label {

		/** Instantiates a new value figure. */
		public ValueFigure(final boolean input) {
			setOpaque(false);
			if (input) {
				setLabelAlignment(PositionConstants.RIGHT);
				setTextAlignment(PositionConstants.CENTER);
				setTextPlacement(PositionConstants.EAST);
			} else {
				setLabelAlignment(PositionConstants.LEFT);
				setTextAlignment(PositionConstants.LEFT);
			}
		}

		@Override
		protected String getTruncationString() {
			return "\u2026"; //$NON-NLS-1$
		}
	}

	@Override
	public void updateAnnotations(final GraphicalAnnotationModelEvent event) {
		GraphicalAnnotationStyles.updateAnnotationFeedback(getFigure(), getModel(), event);
		getFigure().setToolTip(new ValueToolTipFigure(getIInterfaceElement(), getModel(), event.getModel()));
		refreshValue(); // necessary since annotations determine if value is shown for connected pins
	}

	/** Refresh value. */
	void refreshValue() {
		if (getModel().getValue() != null) {
			setVisible(true);
			if (getOuterConnections().isEmpty() || valueHasAnnotation()) {
				if (!getModel().getValue().isBlank()) {
					getFigure().setText(getModel().getValue());
					getFigure().setFont(null);
					getFigure().setForegroundColor(ColorConstants.menuForeground);
				} else {
					getFigure().setText(FordiacMessages.ComputingPlaceholderValue);
					final InitialValueRefreshJob valueRefresh = getRefreshJob();
					if (valueRefresh != null) {
						valueRefresh.refresh();
					}
					getFigure().setFont(JFaceResources.getFontRegistry().getItalic(PreferenceConstants.DIAGRAM_FONT));
					getFigure().setForegroundColor(ColorConstants.gray);
				}
			} else {
				getFigure().setText(""); //$NON-NLS-1$
			}
		} else {
			setVisible(false);
		}
		getFigure().setToolTip(new ValueToolTipFigure(getIInterfaceElement(), getModel(),
				FordiacAnnotationUtil.getAnnotationModel(this)));
	}

	private boolean valueHasAnnotation() {
		return getFigure().getBorder() instanceof AnnotationBorder;
	}

	protected void updateDefaultValue(final String value) {
		if (isActive() && FordiacMessages.ComputingPlaceholderValue.equals(getFigure().getText())) {
			if (value.length() <= DiagramPreferences.getMaxDefaultValueLength()) {
				getFigure().setText(value);
			} else {
				getFigure().setText(FordiacMessages.ValueTooLarge);
			}
		}
	}

	protected IInterfaceElement getDefaultValueContext() {
		return getIInterfaceElement();
	}

	private EList<Connection> getOuterConnections() {
		final IInterfaceElement model = getIInterfaceElement();
		if (model.isIsInput()) {
			return model.getInputConnections();
		}
		return model.getOutputConnections();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		refreshValue();
		refreshPosition();
	}

	@Override
	public Label getFigure() {
		return (Label) super.getFigure();
	}

	/**
	 * Sets the visible.
	 *
	 * @param visible the new visible
	 */
	public void setVisible(final boolean visible) {
		getFigure().setVisible(visible);
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshPosition();
	}

	@Override
	protected void createEditPolicies() {
		// EditPolicy which allows the direct edit of the value
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new ValueEditPartChangeEditPolicy());
	}

	@Override
	public Value getModel() {
		return (Value) super.getModel();
	}

	/**
	 * Checks if is input.
	 *
	 * @return true, if is input
	 */
	public boolean isInput() {
		final IInterfaceElement interfaceElement = getIInterfaceElement();
		if (interfaceElement != null) {
			return interfaceElement.isIsInput();
		}
		return false;
	}

	private IInterfaceElement getIInterfaceElement() {
		return getModel().getParentIE();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.
	 * ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		return new FixedAnchor(getFigure(), isInput());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.
	 * Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new FixedAnchor(getFigure(), isInput());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.
	 * ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return new FixedAnchor(getFigure(), isInput());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.
	 * Request)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	protected IFigure createFigure() {
		return new ValueFigure(isInput());
	}

	/**
	 * Gets the manager.
	 *
	 * @return the manager
	 */
	public DirectEditManager createDirectEditManager() {
		final IInterfaceElement interfaceElement = getIInterfaceElement();
		if (interfaceElement instanceof final VarDeclaration varDecl) {
			return new InitialValueDirectEditManager(this, new FigureCellEditorLocator(getFigure()), varDecl,
					getFigure().getText());
		}
		return new LabelDirectEditManager(this, getFigure());
	}

	/** performs the directEdit. */
	public void performDirectEdit() {
		createDirectEditManager().show();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef.
	 * Request)
	 */
	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick

		final FBNetworkElement fb = getModel().getParentIE().getFBNetworkElement();

		final IEditorPart editor = EditorUtils.getCurrentActiveEditor();

		if (!isTypedInstance(fb) && editor.getAdapter(FBNetwork.class) != null
				&& ((request.getType() == RequestConstants.REQ_DIRECT_EDIT)
						|| (request.getType() == RequestConstants.REQ_OPEN))) {
			performDirectEdit();

		} else {
			super.performRequest(request);
		}
	}

	private static boolean isTypedInstance(final FBNetworkElement fb) {
		return (fb != null) && fb.isContainedInTypedInstance();
	}

	private InitialValueRefreshJob getRefreshJob() {
		if (refreshJob == null && isActive()) {
			refreshJob = new InitialValueRefreshJob(getDefaultValueContext(), this::updateDefaultValue);
		}
		return refreshJob;
	}

}
