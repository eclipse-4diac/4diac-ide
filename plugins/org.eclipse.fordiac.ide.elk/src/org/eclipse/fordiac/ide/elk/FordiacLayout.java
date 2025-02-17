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
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.elk.alg.layered.options.ConstraintCalculationStrategy;
import org.eclipse.elk.alg.layered.options.CrossingMinimizationStrategy;
import org.eclipse.elk.alg.layered.options.CuttingStrategy;
import org.eclipse.elk.alg.layered.options.EdgeStraighteningStrategy;
import org.eclipse.elk.alg.layered.options.FixedAlignment;
import org.eclipse.elk.alg.layered.options.GraphCompactionStrategy;
import org.eclipse.elk.alg.layered.options.GreedySwitchType;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.alg.layered.options.LayeringStrategy;
import org.eclipse.elk.alg.layered.options.NodePlacementStrategy;
import org.eclipse.elk.alg.layered.options.NodePromotionStrategy;
import org.eclipse.elk.alg.layered.options.ValidifyStrategy;
import org.eclipse.elk.alg.layered.options.WrappingStrategy;
import org.eclipse.elk.alg.libavoid.options.LibavoidMetaDataProvider;
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
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
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
		executeLayout(part, ep, true);
	}

	public static void connectionLayout(final IEditorPart part, final AbstractFBNetworkEditPart ep) {
		executeLayout(part, ep, false);
	}

	public static Command getConnectionLayoutCommand(final IEditorPart part) {
		return layout(part, findRootEditPart(part), false);
	}

	private static void executeLayout(final IEditorPart part, final AbstractFBNetworkEditPart ep,
			final boolean isBlockLayout) {
		final Command command = layout(part, ep, isBlockLayout);
		part.getAdapter(CommandStack.class).execute(command);
	}

	private static Command layout(final IEditorPart part, final AbstractFBNetworkEditPart ep,
			final boolean isBlockLayout) {
		final RecursiveGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
		final CompoundCommand command = new CompoundCommand();

		// perform initial layout
		command.add(performLayoutRun(part, ep, engine, isBlockLayout));

		// handle subapps if applicable
		if (ep instanceof UnfoldedSubappContentEditPart) {
			final List<UnfoldedSubappContentEditPart> subapps = collectSubapps(ep);
			subapps.forEach(subapp -> command.add(performLayoutRun(part, subapp, engine, isBlockLayout)));
		}

		return command;
	}

	private static Command performLayoutRun(final IEditorPart part, final AbstractFBNetworkEditPart ep,
			final RecursiveGraphLayoutEngine engine, final boolean isBlockLayout) {
		final FordiacLayoutMapping mapping = new FordiacLayoutMapping(part, ep);

		FordiacGraphBuilder.build(mapping);

		if (isBlockLayout) {
			performBlockLayout(mapping, engine);
		}

		performConnectionLayout(mapping, engine);

		FordiacGraphDataHelper.calculate(mapping);

		return createCompoundCommand(mapping, isBlockLayout);
	}

	private static void performBlockLayout(final FordiacLayoutMapping mapping,
			final RecursiveGraphLayoutEngine engine) {
		configureBlockLayoutGraph(mapping.getLayoutGraph());
		engine.layout(mapping.getLayoutGraph(), new NullElkProgressMonitor());
		applySnapToGridIfEnabled(mapping);
	}

	private static void performConnectionLayout(final FordiacLayoutMapping mapping,
			final RecursiveGraphLayoutEngine engine) {
		configureConnectionLayoutGraph(mapping.getLayoutGraph());
		try {
			engine.layout(mapping.getLayoutGraph(), new NullElkProgressMonitor());
		} catch (final LibavoidServerException e) {
			showConnectionLayoutWarning(e);
		}
	}

	private static void applySnapToGridIfEnabled(final FordiacLayoutMapping mapping) {
		if (GefPreferenceConstants.STORE.getBoolean(GefPreferenceConstants.SNAP_TO_GRID)) {
			snapToGrid(mapping);
		}
	}

	private static void snapToGrid(final FordiacLayoutMapping mapping) {
		final GraphicalViewer viewer = mapping.getWorkbenchPart().getAdapter(GraphicalViewer.class);
		final Dimension gridSpacing = (Dimension) viewer.getProperty(SnapToGrid.PROPERTY_GRID_SPACING);
		final double gridSize = gridSpacing.preciseHeight();

		mapping.getLayoutGraph().getChildren().forEach(node -> {
			final double x = Math.round(node.getX() / gridSize) * gridSize;
			final double y = Math.round(node.getY() / gridSize) * gridSize;
			node.setLocation(x, y);
		});
	}

	private static Command createCompoundCommand(final FordiacLayoutMapping mapping, final boolean isBlockLayout) {
		final CompoundCommand command = new CompoundCommand();
		if (isBlockLayout) {
			command.add(new BlockLayoutCommand(mapping.getLayoutData()));
		}
		command.add(new ConnectionLayoutCommand(mapping.getLayoutData()));
		return command;
	}

	private static void configureBlockLayoutGraph(final ElkNode graph) {
		graph.setProperty(CoreOptions.INTERACTIVE, Boolean.FALSE)
				.setProperty(CoreOptions.ALGORITHM, "org.eclipse.elk.layered") //$NON-NLS-1$
				.setProperty(CoreOptions.DIRECTION, Direction.RIGHT)
				.setProperty(CoreOptions.ASPECT_RATIO, Double.valueOf(1.0))
				.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS)
				.setProperty(CoreOptions.PADDING, new ElkPadding(5.0, 20.0, 20.0, 20.0))
				.setProperty(CoreOptions.SPACING_NODE_NODE, Double.valueOf(50))
				.setProperty(LayeredMetaDataProvider.SPACING_NODE_NODE_BETWEEN_LAYERS, Double.valueOf(40))
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
				.setProperty(LayeredMetaDataProvider.WRAPPING_VALIDIFY_STRATEGY, ValidifyStrategy.LOOK_BACK)
				.setProperty(LayeredMetaDataProvider.WRAPPING_CUTTING_STRATEGY, CuttingStrategy.ARD)
				.setProperty(LayeredMetaDataProvider.WRAPPING_STRATEGY, WrappingStrategy.MULTI_EDGE)
				.setProperty(LayeredMetaDataProvider.COMPACTION_POST_COMPACTION_STRATEGY, GraphCompactionStrategy.LEFT)
				.setProperty(LayeredMetaDataProvider.COMPACTION_POST_COMPACTION_CONSTRAINTS,
						ConstraintCalculationStrategy.QUADRATIC);
		// TODO layer unzipping is currently in elk develop, wait for next release
	}

	private static void configureConnectionLayoutGraph(final ElkGraphElement graph) {
		graph.setProperty(CoreOptions.ALGORITHM, "org.eclipse.elk.alg.libavoid") //$NON-NLS-1$
				.setProperty(LibavoidMetaDataProvider.SHAPE_BUFFER_DISTANCE, Double.valueOf(10))
				.setProperty(LibavoidMetaDataProvider.IDEAL_NUDGING_DISTANCE, Double.valueOf(5))
				.setProperty(LibavoidMetaDataProvider.NUDGE_SHARED_PATHS_WITH_COMMON_END_POINT, Boolean.FALSE)
				.setProperty(LibavoidMetaDataProvider.ENABLE_HYPEREDGES_FROM_COMMON_SOURCE, Boolean.TRUE)
				.setProperty(LibavoidMetaDataProvider.IMPROVE_HYPEREDGE_ROUTES_MOVING_ADDING_AND_DELETING_JUNCTIONS,
						Boolean.TRUE)
				.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS)
				.setProperty(CoreOptions.DIRECTION, Direction.RIGHT);
	}

	private static void showConnectionLayoutWarning(final LibavoidServerException e) {
		MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				Messages.ConnectionLayout_TimeoutTitle,
				MessageFormat.format(Messages.ConnectionLayout_TimeoutMessage, e.getMessage()));
	}

	private static List<UnfoldedSubappContentEditPart> collectSubapps(final AbstractFBNetworkEditPart ep) {
		final List<UnfoldedSubappContentEditPart> subapps = new ArrayList<>();
		ep.getChildren().stream().filter(SubAppForFBNetworkEditPart.class::isInstance)
				.map(SubAppForFBNetworkEditPart.class::cast).filter(sub -> sub.getModel().isUnfolded())
				.map(SubAppForFBNetworkEditPart::getContentEP).map(UnfoldedSubappContentEditPart.class::cast)
				.forEach(subapps::add);
		return subapps;
	}

	private static AbstractFBNetworkEditPart findRootEditPart(final IWorkbenchPart workbenchPart) {
		final GraphicalViewer viewer = workbenchPart.getAdapter(GraphicalViewer.class);
		final AbstractFBNetworkEditPart networkEP = (AbstractFBNetworkEditPart) viewer.getRootEditPart().getChildren()
				.get(0);
		final Point point = ((FBNetworkContextMenuProvider) viewer.getContextMenu()).getTranslatedAndZoomedPoint();

		final AbstractContainerContentEditPart containerEP = GetEditPartFromGraficalViewerHelper
				.findAbstractContainerContentEditPartAtPosition((IEditorPart) workbenchPart, point,
						networkEP.getModel());
		return (containerEP != null) ? containerEP : networkEP;
	}
}
