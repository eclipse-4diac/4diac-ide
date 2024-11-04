/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public final class DeploymentHandlerUtil {

	public static IInterfaceElement getInterfaceElement(final ExecutionEvent event) {
		if (HandlerUtil.getCurrentSelection(event) instanceof final StructuredSelection selection) {
			return Adapters.adapt(selection.getFirstElement(), IInterfaceElement.class);
		}
		return null;
	}

	private DeploymentHandlerUtil() {
		throw new UnsupportedOperationException();
	}
}
