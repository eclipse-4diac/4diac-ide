/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, fortiss GmbH,
 *                           Johannes Kepler University Linz
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
 *   Alois Zoitl - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *               - extracted common FB shape for interface and fbn editors
 *               - hide middle part when expanded
 *               - show comment part on top of the subapp figure
 *               - rework expanded subapp figure to have subapp content spann
 *                 across events, data, and plugs/sockets.
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.figures.FBShapeShadowBorder;
import org.eclipse.fordiac.ide.gef.figures.RoundedRectangleShadowBorder;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.edit.providers.ResultListLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.swt.SWT;

/**
 * The Class SubAppForFbNetworkFigure.
 */
public class SubAppForFbNetworkFigure extends FBNetworkElementFigure {

	private InstanceCommentFigure commentFigure;
	private RoundedRectangle expandedMainFigure;

	public SubAppForFbNetworkFigure(final SubApp model, final SubAppForFBNetworkEditPart editPart) {
		super(model, editPart);
		updateTypeLabel(model);
		updateExpandedFigure();
	}

	public InstanceCommentFigure getCommentFigure() {
		return commentFigure;
	}

	public void updateTypeLabel(final SubApp model) {
		getTypeLabel().setText(model.isTyped() ? model.getTypeName() : ""); //$NON-NLS-1$
		if (!model.isTyped()) {
			getTypeLabel().setIcon(FordiacImage.ICON_SUB_APP.getImage());
		} else {
			getTypeLabel().setIcon(ResultListLabelProvider.getTypeImage(model.getType()));
		}
	}

	@Override
	protected SubApp getModel() {
		return (SubApp) super.getModel();
	}

	public RoundedRectangle getExpandedMainFigure() {
		return expandedMainFigure;
	}

	public void refreshComment() {
		if (commentFigure != null) {
			commentFigure.setText(getModel().getComment());
		}
	}

	public final void updateExpandedFigure() {
		if (getModel().isUnfolded()) {
			if (expandedMainFigure == null) {
				transformToExpandedSubapp();
			}
		} else {
			if (expandedMainFigure != null) {
				transformToCollapsedSubapp();
			}
		}
	}

	private void transformToExpandedSubapp() {
		setBorder(null);
		getFbFigureContainer().setBorder(new RoundedRectangleShadowBorder());

		final GridData layoutConstraint = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		setConstraint(getFbFigureContainer(), layoutConstraint);

		createExpandedMainFigure();
		removeTopMiddleBotom();
		addComment();
		createExpandedInputs();
		crateExpandedOutputs();
		// ensure recalculation of the pins as they now have two connection endpoints
		getFbFigureContainer().invalidateTree();
	}

	private void transformToCollapsedSubapp() {
		getFbFigureContainer().setBorder(null);
		setConstraint(getFbFigureContainer(), createDefaultFBContainerLayoutData());
		setBorder(new FBShapeShadowBorder());
		getFbFigureContainer().remove(expandedMainFigure);
		expandedMainFigure = null;
		addTop();
		addTopIOs();
		addMiddle();
		addBottom();
		addBottomIOs();
		// ensure recalculation of the pins as they now have only one connection ednpoint
		getFbFigureContainer().invalidateTree();
	}

	private void createExpandedMainFigure() {
		expandedMainFigure = new RoundedRectangle();
		expandedMainFigure.setOutline(false);
		expandedMainFigure
				.setCornerDimensions(new Dimension(DiagramPreferences.CORNER_DIM, DiagramPreferences.CORNER_DIM));
		expandedMainFigure.setLayoutManager(createTopBottomLayout());
		final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		getFbFigureContainer().add(expandedMainFigure, gridData, -1);
	}

	private void createExpandedInputs() {
		final Figure inputFigure = new Figure();
		inputFigure.setLayoutManager(createExpandedIOLayout());
		expandedMainFigure.add(inputFigure, createExpandedIOLayoutData(), -1);
		inputFigure.add(getEventInputs(), createExpandedIOLayoutData(), -1);
		inputFigure.add(getBottomInputArea(), createExpandedIOLayoutData(), -1);
	}

	private void crateExpandedOutputs() {
		final Figure outputFigure = new Figure();
		outputFigure.setLayoutManager(createExpandedIOLayout());
		expandedMainFigure.add(outputFigure, createExpandedIOLayoutData(), -1);
		outputFigure.add(getEventOutputs(), createExpandedOutputLayoutData(), -1);
		outputFigure.add(getBottomOutputArea(), createExpandedOutputLayoutData(), -1);
	}

	private static GridData createExpandedIOLayoutData() {
		final GridData gridData = new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		gridData.verticalAlignment = SWT.TOP;
		return gridData;
	}

	protected static GridData createExpandedOutputLayoutData() {
		final GridData outputsLayoutData = createExpandedIOLayoutData();
		outputsLayoutData.horizontalAlignment = GridData.END;
		return outputsLayoutData;
	}

	private void removeTopMiddleBotom() {
		getFbFigureContainer().remove(getTop());
		getFbFigureContainer().remove(getMiddleContainer());
		getFbFigureContainer().remove(getBottom());
	}

	private void addComment() {
		commentFigure = new InstanceCommentFigure();
		expandedMainFigure.add(commentFigure, createCommentLayoutData(), 0);
		commentFigure.setBorder(new MarginBorder(2, 2, 2, 2));
		refreshComment();
	}

	private static GridData createCommentLayoutData() {
		final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		gridData.horizontalSpan = 3;
		return gridData;
	}

	protected static GridLayout createExpandedIOLayout() {
		final GridLayout topLayout = new GridLayout(1, false);
		topLayout.marginHeight = 0;
		topLayout.marginWidth = 0;
		topLayout.verticalSpacing = 0;
		topLayout.horizontalSpacing = 0;
		return topLayout;
	}
}
