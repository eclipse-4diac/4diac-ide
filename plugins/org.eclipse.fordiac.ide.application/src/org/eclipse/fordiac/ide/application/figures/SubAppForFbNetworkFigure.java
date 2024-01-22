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

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedLineBorder;
import org.eclipse.fordiac.ide.gef.figures.BorderedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.figures.FBShapeShadowBorder;
import org.eclipse.fordiac.ide.gef.figures.RoundedRectangleShadowBorder;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.edit.providers.ResultListLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

/** The Class SubAppForFbNetworkFigure. */
public class SubAppForFbNetworkFigure extends FBNetworkElementFigure {

	private InstanceCommentFigure commentFigure;
	private RoundedRectangle expandedMainFigure;
	private Shape expandedInputFigure;
	private IFigure expandedContentArea;
	private Shape expandedOutputFigure;

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

	@Override
	public void setBackgroundColor(final Color bg) {
		setInterfaceBarAlpha(bg != null ? 100 : 255);
		super.setBackgroundColor(bg);
	}

	public RoundedRectangle getExpandedMainFigure() {
		return expandedMainFigure;
	}

	public IFigure getExpandedContentArea() {
		return expandedContentArea;
	}

	public IFigure getExpandedInputFigure() {
		return expandedInputFigure;
	}

	public IFigure getExpandedOutputFigure() {
		return expandedOutputFigure;
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
		} else if (expandedMainFigure != null) {
			transformToCollapsedSubapp();
		}
	}

	private void transformToExpandedSubapp() {
		setBorder(null);

		final GridData layoutConstraint = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		setConstraint(getFbFigureContainer(), layoutConstraint);

		createExpandedMainFigure();
		removeTopMiddleBottom();
		addComment();
		expandedInputFigure = createInterfaceBar(expandedMainFigure);
		createContentContainer();
		expandedOutputFigure = createInterfaceBar(expandedMainFigure);
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
		// ensure recalculation of the pins as they now have only one connection
		// ednpoint
		getFbFigureContainer().invalidateTree();
	}

	private void createExpandedMainFigure() {
		expandedMainFigure = new BorderedRoundedRectangle();
		expandedMainFigure.setOutline(false);
		expandedMainFigure.setOpaque(false);
		expandedMainFigure
				.setCornerDimensions(new Dimension(DiagramPreferences.CORNER_DIM, DiagramPreferences.CORNER_DIM));
		expandedMainFigure.setBorder(new RoundedRectangleShadowBorder());
		expandedMainFigure.setLayoutManager(createExpandedMainFigureLayout());
		final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		getFbFigureContainer().add(expandedMainFigure, gridData);
	}

	private void createContentContainer() {
		expandedContentArea = new Figure();
		expandedContentArea.setLayoutManager(new GridLayout());
		final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		expandedMainFigure.add(expandedContentArea, gridData);
	}

	private static Shape createInterfaceBar(final IFigure parent) {
		final RoundedRectangle interfaceBar = new RoundedRectangle();
		interfaceBar.setOutline(false);
		interfaceBar.setBackgroundColor(EditorWithInterfaceEditPart.INTERFACE_BAR_BG_COLOR);
		interfaceBar.setLayoutManager(createInterfaceBarLayout());
		parent.add(interfaceBar, new GridData(SWT.BEGINNING, SWT.FILL, false, true));

		createToolbarLayoutContainer(interfaceBar);

		return interfaceBar;
	}

	private static void createToolbarLayoutContainer(final RoundedRectangle interfaceBar) {
		final IFigure container = new Figure();
		final ToolbarLayout layout = new ToolbarLayout(false);
		layout.setStretchMinorAxis(true);
		layout.setSpacing(2);
		layout.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		container.setLayoutManager(layout);
		interfaceBar.add(container, createInterfaceBarGroupLayoutData());
	}

	private void removeTopMiddleBottom() {
		getFbFigureContainer().remove(getTop());
		getFbFigureContainer().remove(getMiddleContainer());
		getFbFigureContainer().remove(getBottom());
	}

	private void addComment() {
		final Figure commentContainer = new Figure();
		commentContainer.setLayoutManager(new ToolbarLayout());
		expandedMainFigure.add(commentContainer, createCommentLayoutData(), 0);
		// create a grid layout to get the default margin which is also used in groups
		final GridLayout marginGetter = new GridLayout();
		commentContainer.setBorder(new MarginBorder(marginGetter.marginHeight));

		commentFigure = new InstanceCommentFigure();
		commentFigure.setBorder(new AdvancedLineBorder(PositionConstants.SOUTH));
		commentFigure.setCursor(Cursors.SIZEALL);
		commentContainer.add(commentFigure);
		refreshComment();
	}

	private static GridData createCommentLayoutData() {
		final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		gridData.horizontalSpan = 3;
		return gridData;
	}

	private static GridLayout createInterfaceBarLayout() {
		final GridLayout topLayout = new GridLayout(1, false);
		topLayout.marginHeight = EditorWithInterfaceEditPart.getInterfaceBarTopPadding();
		topLayout.marginWidth = 0;
		topLayout.verticalSpacing = 0;
		topLayout.horizontalSpacing = 0;
		return topLayout;
	}

	public int getExpandedIOHeight() {
		if (getModel().isUnfolded()) {
			return Math.max(expandedInputFigure.getPreferredSize().height,
					expandedOutputFigure.getPreferredSize().height);
		}
		return -1;
	}

	private void setInterfaceBarAlpha(final int alpha) {
		if (expandedInputFigure != null) {
			expandedInputFigure.setAlpha(alpha);
		}
		if (expandedOutputFigure != null) {
			expandedOutputFigure.setAlpha(alpha);
		}
	}

	protected static GridLayout createExpandedMainFigureLayout() {
		final GridLayout topLayout = new GridLayout(3, false);
		topLayout.marginHeight = 1;
		topLayout.marginWidth = 0;
		topLayout.verticalSpacing = 0;
		topLayout.horizontalSpacing = 0;
		return topLayout;
	}

	protected static GridData createInterfaceBarGroupLayoutData() {
		return new GridData(SWT.FILL, SWT.TOP, true, false);
	}
}
