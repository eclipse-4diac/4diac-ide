/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class Import4diacProject extends Task {

	@Override
	public void execute() throws BuildException {
		System.out.println("Test Message");

	}
}
