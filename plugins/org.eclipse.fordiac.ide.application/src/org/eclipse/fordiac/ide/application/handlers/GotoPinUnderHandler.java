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
 *   Fabio Gandolfi - implemented Handler for Goto Pin Below command/shortcut
 *   Alois Zoitl    - extracted common Goto Pin Above/Below functions into
 *                    abstract base class
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

public class GotoPinUnderHandler extends AbstractGoToPinHandler {

	public GotoPinUnderHandler() {
		super(BELOW);
	}

}
