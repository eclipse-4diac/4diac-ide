/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *   Sebastian Hollersbacher - added support for multiple content
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui;

import java.util.Stack;

public class FordiacClipboard {

	public static final FordiacClipboard INSTANCE = new FordiacClipboard();
	private static final int MAX_SIZE = 10;

	private final Stack<Object> graphicalStack = new Stack<>();

	public void setGraphicalContents(final Object obj) {
		if (graphicalStack.size() > MAX_SIZE) {
			graphicalStack.clear();
		}
		graphicalStack.add(obj);
	}

	public Object getGraphicalContents() {
		return graphicalStack.isEmpty() ? null : graphicalStack.peek();
	}

	private FordiacClipboard() {
		// nothing to do here
	}
}
