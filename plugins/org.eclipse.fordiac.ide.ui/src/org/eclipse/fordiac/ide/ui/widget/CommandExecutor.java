/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 	
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alois Zoitl - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.gef.commands.Command;

public interface CommandExecutor {
	
	void executeCommand(Command cmd);

}
