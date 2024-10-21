/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 * 				 2021, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber, Bianca Wiesmayr, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.elk;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.elk.core.service.DiagramLayoutEngine;
import org.eclipse.elk.core.service.IDiagramLayoutConnector;
import org.eclipse.elk.core.service.LayoutConnectorsService;
import org.eclipse.elk.core.service.LayoutMapping;
import org.eclipse.elk.core.util.NullElkProgressMonitor;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.elk.commands.LayoutCommand;
import org.eclipse.fordiac.ide.elk.handlers.ConnectionLayoutHandler;
import org.eclipse.fordiac.ide.elk.helpers.FordiacGraphBuilder;
import org.eclipse.fordiac.ide.elk.helpers.FordiacGraphDataHelper;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.google.inject.Injector;

public class FordiacLayoutConnector implements IDiagramLayoutConnector {

	@Override
	public LayoutMapping buildLayoutGraph(final IWorkbenchPart workbenchPart, final Object diagramPart) {
		final FordiacLayoutMapping mapping = new FordiacLayoutMapping(workbenchPart, true);

		if (mapping.hasNetwork()) {
			FordiacGraphBuilder.build(mapping);
		}

		return mapping;
	}

	@Override
	public void applyLayout(final LayoutMapping mapping, final IPropertyHolder settings) {
		final var fordiacMapping = (FordiacLayoutMapping) mapping;
		FordiacGraphDataHelper.calculate(fordiacMapping);
		fordiacMapping.getCommandStack().execute(new LayoutCommand(fordiacMapping.getLayoutData()));

		// schedule as async to ensure the changes from the layout command have been
		// processed
		Display.getDefault().asyncExec(() -> ConnectionLayoutHandler
				.getLayoutCommandNonHierarchical(fordiacMapping.getWorkbenchPart()).execute());
	}

	public static void executeManually(final List<SubAppForFBNetworkEditPart> editParts) {
		final var workbenchPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		final Injector injector = LayoutConnectorsService.getInstance().getInjector(workbenchPart, null);
		final DiagramLayoutEngine engine = injector.getInstance(DiagramLayoutEngine.class);

		for (final var ep : editParts) {
			final FordiacLayoutMapping mapping = new FordiacLayoutMapping(workbenchPart, true,
					(AbstractContainerContentEditPart) ep.getContentEP());
			if (mapping.hasNetwork()) {
				FordiacGraphBuilder.build(mapping);
			}
			final IStatus status = engine.layout(mapping, new NullElkProgressMonitor(),
					new DiagramLayoutEngine.Parameters());
			if (status.isOK()) {
				FordiacGraphDataHelper.calculate(mapping);
				mapping.getCommandStack().execute(new LayoutCommand(mapping.getLayoutData()));
			}

			Display.getDefault().asyncExec(() -> ConnectionLayoutHandler
					.getLayoutCommand((UnfoldedSubappContentEditPart) ep.getContentEP()).execute());
		}
	}

}