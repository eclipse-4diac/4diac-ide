/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 	
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Alois Zoitl - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.gef.commands.Command;

public interface CommandExecutor {

	void executeCommand(Command cmd);

}
