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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.alg.libavoid.options.LibavoidMetaDataProvider;
import org.eclipse.elk.alg.libavoid.server.LibavoidServerException;
import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.Direction;
import org.eclipse.elk.core.options.PortConstraints;
import org.eclipse.elk.core.util.NullElkProgressMonitor;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.application.utilities.GetEditPartFromGraficalViewerHelper;
import org.eclipse.fordiac.ide.elk.commands.BlockLayoutCommand;
import org.eclipse.fordiac.ide.elk.commands.ConnectionLayoutCommand;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.gef.GraphicalViewer;
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
		cmd.add(performLayoutRun(ep, engine, isBlockLayout)); // initial run, e.g. application

		if (ep instanceof UnfoldedSubappContentEditPart) { // have to look for further subapps
			final List<UnfoldedSubappContentEditPart> subapps = new ArrayList<>();
			collectSubapps(ep, subapps);
			Collections.reverse(subapps);
			subapps.stream().forEach(subapp -> cmd.add(performLayoutRun(subapp, engine, isBlockLayout)));
		}

		return cmd;
	}

	private static Command performLayoutRun(final AbstractFBNetworkEditPart ep, final RecursiveGraphLayoutEngine engine,
			final boolean isBlockLayout) {
		final FordiacLayoutMapping mapping = new FordiacLayoutMapping(ep);

		FordiacGraphBuilder.build(mapping);

		if (isBlockLayout) {
			configureBlockLayoutGraph(mapping.getLayoutGraph());
			engine.layout(mapping.getLayoutGraph(), new NullElkProgressMonitor());

			mapping.getLayoutGraph().getAllProperties().clear();
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

	private static void configureBlockLayoutGraph(final ElkGraphElement graph) {
		graph.setProperty(CoreOptions.INTERACTIVE, Boolean.TRUE)
				.setProperty(CoreOptions.ALGORITHM, "org.eclipse.elk.layered") //$NON-NLS-1$
				.setProperty(CoreOptions.DIRECTION, Direction.RIGHT)
				.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_POS)
				.setProperty(CoreOptions.PADDING, new ElkPadding(80.0))
				.setProperty(CoreOptions.SPACING_NODE_NODE, Double.valueOf(50))
				.setProperty(LayeredMetaDataProvider.SPACING_NODE_NODE_BETWEEN_LAYERS, Double.valueOf(50))
				.setProperty(LayeredMetaDataProvider.THOROUGHNESS, Integer.valueOf(10));
	}

	private static void configureConnectionLayoutGraph(final ElkGraphElement graph) {
		graph.setProperty(CoreOptions.ALGORITHM, "org.eclipse.elk.alg.libavoid") //$NON-NLS-1$
				.setProperty(LibavoidMetaDataProvider.SHAPE_BUFFER_DISTANCE, Double.valueOf(10))
				.setProperty(LibavoidMetaDataProvider.IDEAL_NUDGING_DISTANCE, Double.valueOf(5))
				.setProperty(LibavoidMetaDataProvider.CROSSING_PENALTY, Double.valueOf(10000))
				// .setProperty(LibavoidMetaDataProvider.CLUSTER_CROSSING_PENALTY,
				// Double.valueOf(10))
				.setProperty(LibavoidMetaDataProvider.NUDGE_SHARED_PATHS_WITH_COMMON_END_POINT, Boolean.valueOf(false))
				.setProperty(LibavoidMetaDataProvider.ENABLE_HYPEREDGES_FROM_COMMON_SOURCE, Boolean.valueOf(true))
				.setProperty(LibavoidMetaDataProvider.IMPROVE_HYPEREDGE_ROUTES_MOVING_ADDING_AND_DELETING_JUNCTIONS,
						Boolean.valueOf(true));
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
