/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 * 				 2021, 2022 Primetals Technologies Austria GmbH
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

import static org.eclipse.fordiac.ide.elk.FordiacLayoutMapping.COMMAND_STACK;

import org.eclipse.elk.core.service.IDiagramLayoutConnector;
import org.eclipse.elk.core.service.LayoutMapping;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.fordiac.ide.elk.commands.LayoutCommand;
import org.eclipse.fordiac.ide.elk.helpers.FordiacGraphDataHelper;
import org.eclipse.fordiac.ide.elk.helpers.FordiacGraphBuilder;
import org.eclipse.ui.IWorkbenchPart;

public class FordiacLayoutConnector implements IDiagramLayoutConnector {

	@Override
	public LayoutMapping buildLayoutGraph(final IWorkbenchPart workbenchPart, final Object diagramPart) {
		final FordiacLayoutMapping mapping = FordiacLayoutMapping.create(workbenchPart);

		if (mapping.hasNetwork()) {
			FordiacGraphBuilder.build(mapping);
		}

		return mapping;
	}

	@Override
	public void applyLayout(final LayoutMapping mapping, final IPropertyHolder settings) {
		final FordiacLayoutData data = FordiacGraphDataHelper.calculate(mapping);
		mapping.getProperty(COMMAND_STACK).execute(new LayoutCommand(data));
	}

}