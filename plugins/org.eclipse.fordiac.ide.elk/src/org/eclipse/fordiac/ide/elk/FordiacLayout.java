/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.elk.alg.layered.options.CrossingMinimizationStrategy;
import org.eclipse.elk.alg.layered.options.EdgeStraighteningStrategy;
import org.eclipse.elk.alg.layered.options.FixedAlignment;
import org.eclipse.elk.alg.layered.options.GreedySwitchType;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.alg.layered.options.LayeringStrategy;
import org.eclipse.elk.alg.layered.options.NodePlacementStrategy;
import org.eclipse.elk.alg.layered.options.NodePromotionStrategy;
import org.eclipse.elk.alg.layered.options.ValidifyStrategy;
import org.eclipse.elk.alg.libavoid.server.LibavoidServerException;
import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.Direction;
import org.eclipse.elk.core.options.PortConstraints;
import org.eclipse.elk.core.util.NullElkProgressMonitor;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.application.utilities.GetEditPartFromGraficalViewerHelper;
import org.eclipse.fordiac.ide.elk.commands.BlockLayoutCommand;
import org.eclipse.fordiac.ide.elk.commands.ConnectionLayoutCommand;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class FordiacLayout {

	public static void blockLayout(final IEditorPart part, final AbstractFBNetworkEditPart ep) {
		part.getAdapter(CommandStack.class).execute(layout(part, ep, true));
	}

	public static void connectionLayout(final IEditorPart part, final AbstractFBNetworkEditPart ep) {
		part.getAdapter(CommandStack.class).execute(layout(part, ep, false));
		getConnectionLayoutCommand(part);
	}

	public static Command getConnectionLayoutCommand(final IEditorPart part) {
		final var networkEP = findRootEditPart(part);
		return layout(part, networkEP, false);
	}

	private static Command layout(final IEditorPart part, final AbstractFBNetworkEditPart ep,
			final boolean isBlockLayout) {
		final var engine = new RecursiveGraphLayoutEngine();
		final var cmd = new CompoundCommand();
		cmd.add(performLayoutRun(part, ep, engine, isBlockLayout)); // initial run, e.g. application

		if (ep instanceof UnfoldedSubappContentEditPart) { // have to look for further subapps
			final List<UnfoldedSubappContentEditPart> subapps = new ArrayList<>();
			collectSubapps(ep, subapps);
			Collections.reverse(subapps);
			subapps.stream().forEach(subapp -> cmd.add(performLayoutRun(part, subapp, engine, isBlockLayout)));
		}

		return cmd;
	}

	private static Command performLayoutRun(final IEditorPart part, final AbstractFBNetworkEditPart ep,
			final RecursiveGraphLayoutEngine engine, final boolean isBlockLayout) {
		final FordiacLayoutMapping mapping = new FordiacLayoutMapping(part, ep);

		FordiacGraphBuilder.build(mapping);

		if (isBlockLayout) {
			configureBlockLayoutGraph(mapping.getLayoutGraph());
			engine.layout(mapping.getLayoutGraph(), new NullElkProgressMonitor());

			if (Activator.getDefault().getPreferenceStore().getBoolean(DiagramPreferences.SNAP_TO_GRID)) {
				snapToGrid(mapping);
			}
		}

		configureConnectionLayoutGraph(mapping.getLayoutGraph());
		try {
			engine.layout(mapping.getLayoutGraph(), new NullElkProgressMonitor());
		} catch (final LibavoidServerException e) {
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					Messages.ConnectionLayout_TimeoutTitle,
					MessageFormat.format(Messages.ConnectionLayout_TimeoutMessage, e.getMessage()));
		}

		FordiacGraphDataHelper.calculate(mapping);

		final var cmd = new CompoundCommand();
		if (isBlockLayout) {
			cmd.add(new BlockLayoutCommand(mapping.getLayoutData()));
		}
		cmd.add(new ConnectionLayoutCommand(mapping.getLayoutData()));
		return cmd;
	}

	private static void snapToGrid(final FordiacLayoutMapping mapping) {
		final var viewer = mapping.getWorkbenchPart().getAdapter(GraphicalViewer.class);
		final var dim = (Dimension) viewer.getProperty(SnapToGrid.PROPERTY_GRID_SPACING);
		final var gridSize = dim.preciseHeight();

		mapping.getLayoutGraph().getChildren().forEach(node -> {
			final double x = Math.round((node.getX()) / gridSize) * gridSize;
			final double y = Math.round((node.getY()) / gridSize) * gridSize;
			node.setLocation(x, y);
		});
	}

	private static void configureBlockLayoutGraph(final ElkNode graph) {
		graph.setProperty(CoreOptions.INTERACTIVE, Boolean.FALSE)
				.setProperty(CoreOptions.ALGORITHM, "org.eclipse.elk.layered") //$NON-NLS-1$
				.setProperty(CoreOptions.DIRECTION, Direction.RIGHT)
				.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS)
				.setProperty(CoreOptions.PADDING, new ElkPadding(5.0, 20.0, 20.0, 20.0))
				.setProperty(CoreOptions.SPACING_NODE_NODE, Double.valueOf(50))
				.setProperty(LayeredMetaDataProvider.SPACING_NODE_NODE_BETWEEN_LAYERS, Double.valueOf(80))
				.setProperty(LayeredMetaDataProvider.THOROUGHNESS, Integer.valueOf(10))
				.setProperty(LayeredMetaDataProvider.LAYERING_STRATEGY, LayeringStrategy.MIN_WIDTH)
				.setProperty(LayeredMetaDataProvider.LAYERING_MIN_WIDTH_UPPER_BOUND_ON_WIDTH, Integer.valueOf(-1))
				.setProperty(LayeredMetaDataProvider.LAYERING_MIN_WIDTH_UPPER_LAYER_ESTIMATION_SCALING_FACTOR,
						Integer.valueOf(-1))
				.setProperty(LayeredMetaDataProvider.LAYERING_NODE_PROMOTION_STRATEGY,
						NodePromotionStrategy.NO_BOUNDARY)
				.setProperty(LayeredMetaDataProvider.NODE_PLACEMENT_STRATEGY, NodePlacementStrategy.BRANDES_KOEPF)
				.setProperty(LayeredMetaDataProvider.NODE_PLACEMENT_BK_FIXED_ALIGNMENT, FixedAlignment.BALANCED)
				.setProperty(LayeredMetaDataProvider.NODE_PLACEMENT_BK_EDGE_STRAIGHTENING,
						EdgeStraighteningStrategy.IMPROVE_STRAIGHTNESS)
				.setProperty(LayeredMetaDataProvider.CROSSING_MINIMIZATION_STRATEGY,
						CrossingMinimizationStrategy.LAYER_SWEEP)
				.setProperty(LayeredMetaDataProvider.CROSSING_MINIMIZATION_GREEDY_SWITCH_TYPE,
						GreedySwitchType.TWO_SIDED)
				.setProperty(LayeredMetaDataProvider.COMPACTION_CONNECTED_COMPONENTS, Boolean.TRUE)
				.setProperty(CoreOptions.SEPARATE_CONNECTED_COMPONENTS, Boolean.TRUE)
				.setProperty(LayeredMetaDataProvider.WRAPPING_VALIDIFY_STRATEGY, ValidifyStrategy.LOOK_BACK);
		// TODO layer unzipping is currently in elk develop, wait for next release
	}

	private static void configureConnectionLayoutGraph(final ElkGraphElement graph) {
		graph.setProperty(CoreOptions.ALGORITHM, "org.eclipse.elk.alg.libavoid") //$NON-NLS-1$
				.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS)
				.setProperty(CoreOptions.DEBUG_MODE, Boolean.TRUE).setProperty(CoreOptions.DIRECTION, Direction.RIGHT);

//				.setProperty(LibavoidMetaDataProvider.SHAPE_BUFFER_DISTANCE, Double.valueOf(10))
//				.setProperty(LibavoidMetaDataProvider.IDEAL_NUDGING_DISTANCE, Double.valueOf(5))
//				.setProperty(LibavoidMetaDataProvider.CLUSTER_CROSSING_PENALTY, Double.valueOf(10));
//				.setProperty(LibavoidMetaDataProvider.NUDGE_SHARED_PATHS_WITH_COMMON_END_POINT, Boolean.FALSE)
//				.setProperty(LibavoidMetaDataProvider.ENABLE_HYPEREDGES_FROM_COMMON_SOURCE, Boolean.TRUE)
//				.setProperty(LibavoidMetaDataProvider.IMPROVE_HYPEREDGE_ROUTES_MOVING_ADDING_AND_DELETING_JUNCTIONS,
//						Boolean.TRUE);
	}

	private static void collectSubapps(final AbstractFBNetworkEditPart ep,
			final List<UnfoldedSubappContentEditPart> subapps) {
		// @formatter:off
		final var children = ep.getChildren().stream()
				.filter(SubAppForFBNetworkEditPart.class::isInstance)
				.map(SubAppForFBNetworkEditPart.class::cast)
				.filter(sub -> sub.getModel().isUnfolded())
				.map(SubAppForFBNetworkEditPart::getContentEP)
				.map(UnfoldedSubappContentEditPart.class::cast)
				.toList();
		// @formatter:on
		subapps.addAll(children);
		children.forEach(sub -> collectSubapps(sub, subapps));
	}

	private static AbstractFBNetworkEditPart findRootEditPart(final IWorkbenchPart workbenchPart) {
		final var networkEP = (AbstractFBNetworkEditPart) workbenchPart.getAdapter(GraphicalViewer.class)
				.getRootEditPart().getChildren().get(0);

		final GraphicalViewer viewer = workbenchPart.getAdapter(GraphicalViewer.class);
		final Point pt = ((FBNetworkContextMenuProvider) viewer.getContextMenu()).getTranslatedAndZoomedPoint();

		final AbstractContainerContentEditPart containerEP = GetEditPartFromGraficalViewerHelper
				.findAbstractContainerContentEditPartAtPosition((IEditorPart) workbenchPart, pt, networkEP.getModel());

		return (containerEP != null) ? containerEP : networkEP;
	}

}
