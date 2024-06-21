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
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk.handlers;

import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.elk.FordiacLayoutConnector;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class NestedExpandedSubappHandler extends AbstractLayoutHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final var selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		// @formatter:off
		final var subapps = selection.stream()
			.filter(SubAppForFBNetworkEditPart.class::isInstance)
			.map(SubAppForFBNetworkEditPart.class::cast)
			.filter(subapp -> subapp.getModel().isUnfolded())
			.collect(Collectors.toList());
		// @formatter:on
		FordiacLayoutConnector.executeManually(subapps);
		return Status.OK_STATUS;
	}

}
