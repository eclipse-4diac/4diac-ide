/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.fordiac.ide.model.commands.change.ChangeContractCommand;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class InstanceContract {

	public static final String CONTRACT_ATTRIBUTE_NAME = ChangeContractCommand.CONTRACT_ATTRIBUTE_NAME;
	private final SubApp subApp;

	public InstanceContract(final SubApp subApp) {
		this.subApp = subApp;
	}

	public SubApp getSubApp() {
		return subApp;
	}

	public String getContract() {
		return getSubApp().getAttributeValue(CONTRACT_ATTRIBUTE_NAME);
	}
}
