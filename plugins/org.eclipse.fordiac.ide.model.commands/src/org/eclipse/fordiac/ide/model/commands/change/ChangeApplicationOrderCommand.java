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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.Application;

public class ChangeApplicationOrderCommand extends AbstractChangeListElementOrderCommand<Application> {

	public ChangeApplicationOrderCommand(final Application selectedApp, final boolean moveUp) {
		super(selectedApp, moveUp, selectedApp.getAutomationSystem().getApplication());
	}

}
