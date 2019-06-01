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
package org.eclipse.fordiac.ide.ui.providers;

import org.eclipse.gef.commands.Command;

/** Interface suitable for providing commands via a lambda to some executor
 */
public interface CommandProvider {
	
	/** Provide a command for the given reference element
	 * 
	 * @param refElement reference element for the command
	 * @return the command 
	 */
	Command getCommand(Object refElement);

}
