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
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ScrollBar;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.ScrollPaneLayout;
import org.eclipse.draw2d.ScrollPaneSolver;
import org.eclipse.draw2d.ScrollPaneSolver.Result;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.utilities.ExpandedInterfacePositionMap;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedLineBorder;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.figures.BorderedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.figures.FBShapeShadowBorder;
import org.eclipse.fordiac.ide.gef.figures.RoundedRectangleShadowBorder;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/** The Class SubAppForFbNetworkFigure. */
public class SubAppForFbNetworkFigure extends FBNetworkElementFigure {

	private InstanceCommentFigure commentFigure;
	private RoundedRectangle expandedMainFigure;
	private Shape expandedInputFigure;
	private IFigure expandedContentArea;
	private Shape expandedOutputFigure;

	private final ExpandedInterfacePositionMap interfacePositions;

	public SubAppForFbNetworkFigure(final SubApp model, final SubAppForFBNetworkEditPart editPart) {
		super(model);
		interfacePositions = editPart.getInterfacePositionMap();
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
			getTypeLabel().setIcon(FordiacImage.ICON_SUB_APP_TYPE.getImage());
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

	/**
	 * Calls the layout manager for both the input and output interface containers.
	 * It has to be noted that this only affects interface positions but not
	 * connections.
	 */
	public void layoutExpandedInterface() {
		// During updates, especially from expanded to collapsed subapps, or saving as
		// type, we my called here in intermediate states. If we don't have a parent we
		// must not layout.
		if (getParent() != null) {
			interfacePositions.calculate();
			expandedInputFigure.getLayoutManager().layout(expandedInputFigure);
			expandedOutputFigure.getLayoutManager().layout(expandedOutputFigure);
		}
	}

	public final void updateExpandedFigure() {
		if (getModel().isUnfolded()) {
			if (expandedMainFigure == null) {
				transformToExpandedSubapp();
			}
			Display.getDefault().asyncExec(this::layoutExpandedInterface); // initial interface layout
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
		expandedInputFigure = createInterfaceBar(expandedMainFigure, true);
		createContentContainer();
		expandedOutputFigure = createInterfaceBar(expandedMainFigure, false);
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
				.setCornerDimensions(new Dimension(GefPreferenceConstants.CORNER_DIM, GefPreferenceConstants.CORNER_DIM));
		expandedMainFigure.setBorder(new RoundedRectangleShadowBorder());
		expandedMainFigure.setLayoutManager(createExpandedMainFigureLayout());
		final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		getFbFigureContainer().add(expandedMainFigure, gridData);
	}

	private void createContentContainer() {
		expandedContentArea = new Figure();
		final GridLayout expContentLayout = new GridLayout();
		expContentLayout.marginHeight = 0;
		expContentLayout.marginWidth = 0;
		expContentLayout.verticalSpacing = 0;
		expContentLayout.horizontalSpacing = 0;
		expandedContentArea.setLayoutManager(expContentLayout);
		final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		expandedMainFigure.add(expandedContentArea, gridData);
	}

	private Shape createInterfaceBar(final IFigure parent, final boolean isInput) {
		final RoundedRectangle interfaceBar = new RoundedRectangle() {
			@Override
			public Dimension getPreferredSize(final int wHint, final int hHint) {
				final Dimension prefSize = super.getPreferredSize(wHint, hHint);
				// we want to have this container a minimum width
				prefSize.union(getMinimumSize());
				return prefSize;
			}
		};
		interfaceBar.setMinimumSize(new Dimension(getMinExpandedInterfaceBarWidth(), -1));
		interfaceBar.setOutline(false);
		interfaceBar.setBackgroundColor(EditorWithInterfaceEditPart.INTERFACE_BAR_BG_COLOR);

		interfaceBar.setLayoutManager(new ExpandedSubappInterfaceLayout(interfacePositions, isInput));

		final var shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		final var lws = new LightweightSystem(shell);

		final ScrollPane scrollPane = new ScrollPane();
		scrollPane.setLayoutManager(new ExpandedInterfaceScrollPaneLayout(isInput));
		scrollPane.setVerticalScrollBarVisibility(ScrollPane.AUTOMATIC);
		scrollPane.setHorizontalScrollBarVisibility(ScrollPane.NEVER);

		scrollPane.setContents(interfaceBar);
		lws.setContents(scrollPane);

		parent.add(scrollPane, new GridData(SWT.BEGINNING, SWT.FILL, false, true));

		return interfaceBar;
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

		commentFigure = new InstanceCommentFigure();
		final AdvancedLineBorder commentLineSeperator = new AdvancedLineBorder(PositionConstants.SOUTH);
		commentFigure.setBorder(commentLineSeperator);
		commentFigure.setCursor(Cursors.SIZEALL);
		commentContainer.add(commentFigure);
		refreshComment();

		final int lineHeight = (int) CoordinateConverter.INSTANCE.getLineHeight();
		int top = lineHeight / 2;
		final int bottom = top;
		if (top + bottom != lineHeight) {
			// we have a rounding error
			top += lineHeight - (top + bottom);
		}
		commentContainer.setBorder(new MarginBorder(top, 5, bottom, 5));
	}

	private static GridData createCommentLayoutData() {
		final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		gridData.horizontalSpan = 3;
		return gridData;
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
		topLayout.marginHeight = 0;
		topLayout.marginWidth = 0;
		topLayout.verticalSpacing = 0;
		topLayout.horizontalSpacing = 0;
		return topLayout;
	}

	protected static GridData createInterfaceBarGroupLayoutData() {
		return new GridData(SWT.FILL, SWT.TOP, true, false);
	}

	private static int minExpSubappBarWidhtPixels = -1;

	private static int getMinExpandedInterfaceBarWidth() {
		if (minExpSubappBarWidhtPixels == -1) {
			minExpSubappBarWidhtPixels = EditorWithInterfaceEditPart.getMinInterfaceBarWidth()
					+ ConnectorBorder.LR_MARGIN; // we have connectors on both sides
		}
		return minExpSubappBarWidhtPixels;
	}

	private static class ExpandedInterfaceScrollPaneLayout extends ScrollPaneLayout {

		private final boolean isInput;

		public ExpandedInterfaceScrollPaneLayout(final boolean isInput) {
			this.isInput = isInput;
		}

		@Override
		public void layout(final IFigure parent) {
			final ScrollPane scrollpane = (ScrollPane) parent;
			final Viewport viewport = scrollpane.getViewport();
			final ScrollBar hBar = scrollpane.getHorizontalScrollBar();
			final ScrollBar vBar = scrollpane.getVerticalScrollBar();

			if (isInput) {
				layoutInput(parent, scrollpane, viewport, hBar, vBar);
			} else {
				layoutOutput(parent, scrollpane, viewport, hBar, vBar);
			}

			incrementPage(vBar);
		}

		private static void incrementPage(final ScrollBar vBar) {
			final int vStepInc = vBar.getStepIncrement();
			int vPageInc = vBar.getRangeModel().getExtent() - vStepInc;
			if (vPageInc < vStepInc) {
				vPageInc = vStepInc;
			}
			vBar.setPageIncrement(vPageInc);
		}

		private static Result solve(final Rectangle clientArea, final ScrollPane scrollpane, final Viewport viewport,
				final ScrollBar hBar, final ScrollBar vBar) {
			// @formatter:off
			return ScrollPaneSolver.solve(
					clientArea,
					viewport,
					scrollpane.getHorizontalScrollBarVisibility(),
					scrollpane.getVerticalScrollBarVisibility(),
					vBar.getPreferredSize().width,
					hBar.getPreferredSize().height
					);
			// @formatter:on
		}

		private static void layoutOutput(final IFigure parent, final ScrollPane scrollpane, final Viewport viewport,
				final ScrollBar hBar, final ScrollBar vBar) {
			final ScrollPaneSolver.Result result = solve(parent.getClientArea(), scrollpane, viewport, hBar, vBar);

			if (result.showV) {
				vBar.setBounds(new Rectangle(result.viewportArea.right(), result.viewportArea.y, result.insets.right,
						result.viewportArea.height));
			}

			vBar.setVisible(result.showV);
		}

		private static void layoutInput(final IFigure parent, final ScrollPane scrollpane, final Viewport viewport,
				final ScrollBar hBar, final ScrollBar vBar) {
			final Rectangle clientArea = parent.getClientArea().getCopy();
			final ScrollPaneSolver.Result result = solve(clientArea, scrollpane, viewport, hBar, vBar);

			if (result.showV) {
				final int scrollBarWidth = vBar.getPreferredSize().width;
				clientArea.x += scrollBarWidth;

				// need to do another solve in order for the scrollbar to have enough space
				final ScrollPaneSolver.Result scrollResult = solve(clientArea, scrollpane, viewport, hBar, vBar);

				vBar.setBounds(new Rectangle(scrollResult.viewportArea.left() - scrollBarWidth,
						scrollResult.viewportArea.y, scrollResult.insets.right, scrollResult.viewportArea.height));
			}

			vBar.setVisible(result.showV);
		}

	}

}
