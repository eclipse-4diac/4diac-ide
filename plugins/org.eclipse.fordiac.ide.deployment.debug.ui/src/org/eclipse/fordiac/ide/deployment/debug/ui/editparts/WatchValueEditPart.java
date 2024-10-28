/*******************************************************************************
 * Copyright (c) 2012, 2024 Profactor GmbH, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH,
 *                		    Primetals Technologies Austria GmbH,
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Lukas Wais - implemented hex conversion for AnyBit types
 *   Alois Zoitl - added value validation for direct edit of values
 *   Daniel Lindhuber - multi-line struct editing
 *   Martin Jobst - adopt new ST editor for values
 *                - rewrite based on MonitoringEditPart
 *                  for new deployment monitoring framework
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.editparts;

import static org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants.DIAGRAM_FONT;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.deployment.debug.preferences.DeploymentDebugPreferences;
import org.eclipse.fordiac.ide.deployment.debug.ui.DeploymentDebugModelPresentation;
import org.eclipse.fordiac.ide.deployment.debug.watch.IVarDeclarationWatch;
import org.eclipse.fordiac.ide.deployment.debug.watch.IWatch;
import org.eclipse.fordiac.ide.gef.draw2d.SetableAlphaLabel;
import org.eclipse.fordiac.ide.gef.editparts.FigureCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;

public class WatchValueEditPart extends AbstractWatchValueEditPart {

	public static final int MONITORING_VALUE_LR_MARGIN = 5;

	@Override
	protected IFigure createFigure() {
		final SetableAlphaLabel figure = new SetableAlphaLabel();
		figure.setOpaque(true);
		if (getInterfaceElement().isIsInput()) {
			figure.setLabelAlignment(PositionConstants.RIGHT);
			figure.setTextAlignment(PositionConstants.RIGHT);
		} else {
			figure.setTextAlignment(PositionConstants.LEFT);
			figure.setLabelAlignment(PositionConstants.LEFT);
		}
		figure.setBorder(new MarginBorder(0, MONITORING_VALUE_LR_MARGIN, 0, MONITORING_VALUE_LR_MARGIN));
		figure.setText(Messages.MonitoringEditPart_Not_Available);
		figure.setMinimumSize(new Dimension(50, 1));
		figure.setAlpha(DeploymentDebugPreferences.getMonitoringValueTransparency());
		return figure;
	}

	@Override
	public Label getFigure() {
		return (Label) super.getFigure();
	}

	@Override
	protected Dimension calculateSize() {
		final InterfaceEditPart host = getHost();
		final int width = Math.clamp(getFigure().getPreferredSize().width, 40, getMaxWidth());
		final int height = host != null ? host.getFigure().getSize().height
				: FigureUtilities.getFontMetrics(JFaceResources.getFontRegistry().get(DIAGRAM_FONT)).getHeight();
		return new Dimension(width, height);
	}

	@Override
	public void activate() {
		super.activate();
		showPinValues(false);
	}

	@Override
	public void deactivate() {
		showPinValues(true);
		super.deactivate();
	}

	private void showPinValues(final boolean show) {
		if (getInterfaceElement() instanceof final VarDeclaration varDeclaration && getViewer() != null
				&& getViewer().getEditPartForModel(varDeclaration.getValue()) instanceof final ValueEditPart valueEP) {
			valueEP.setVisible(show);
		}
	}

	@Override
	protected void createEditPolicies() {
		if (getModel().getElement() instanceof VarDeclaration) {
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new WatchValueDirectEditPolicy());
		}
	}

	@Override
	public boolean understandsRequest(final Request request) {
		if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT)
				|| (request.getType() == RequestConstants.REQ_OPEN)) {
			return getModel() instanceof IVarDeclarationWatch;
		}
		return super.understandsRequest(request);
	}

	@Override
	public void performRequest(final Request request) {
		if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT)
				|| (request.getType() == RequestConstants.REQ_OPEN)) {
			performDirectEdit();
		} else {
			super.performRequest(request);
		}
	}

	protected void performDirectEdit() {
		if (getModel().getWatch() instanceof final IVarDeclarationWatch varDeclarationWatch) {
			new WatchValueDirectEditManager(this, new FigureCellEditorLocator(getFigure()), varDeclarationWatch).show();
		}
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		getFigure().setText(getModel().getText());
		getFigure().setBackgroundColor(getWatchColor());
		showPinValues(false);
	}

	protected Color getWatchColor() {
		final IWatch watch = getModel().getWatch();
		if (!watch.isAlive()) {
			return DeploymentDebugModelPresentation.getWatchErrorColor();
		}
		if (watch instanceof final IVarDeclarationWatch variableWatch && variableWatch.isForced()) {
			return DeploymentDebugModelPresentation.getForceColor();
		}
		return DeploymentDebugModelPresentation.getWatchColor();
	}

	private static int maxLabelWidth = -1;

	protected static int getMaxWidth() {
		if (maxLabelWidth == -1) {
			final IPreferenceStore preferenceStore = org.eclipse.fordiac.ide.gef.Activator.getDefault()
					.getPreferenceStore();
			final int maxLabelSize = preferenceStore.getInt(DiagramPreferences.MAX_VALUE_LABEL_SIZE);
			final FontMetrics fm = FigureUtilities.getFontMetrics(JFaceResources.getFontRegistry().get(DIAGRAM_FONT));
			maxLabelWidth = (int) ((maxLabelSize + 2) * fm.getAverageCharacterWidth()) + 2 * MONITORING_VALUE_LR_MARGIN;
		}
		return maxLabelWidth;
	}
}
