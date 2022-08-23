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

import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;

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

	public void setTableContents(final Object obj) {
		final org.eclipse.swt.dnd.Clipboard cb = new org.eclipse.swt.dnd.Clipboard(null);
		cb.setContents(new Object[] { obj }, new Transfer[] { TextTransfer.getInstance() });
		cb.dispose();
	}

	public void setMultibleTableContents(final Object obj) {
		final org.eclipse.swt.dnd.Clipboard cb = new org.eclipse.swt.dnd.Clipboard(null);
		if (obj instanceof String[] && ((String[]) obj).length != 0) {
			final String[] strings = ((String[]) obj);
			for (int i = 0; i < strings.length; i++) {
				if (strings[i].isEmpty()) {
					strings[i] = " ";
				}
			}
			cb.setContents(new Object[] { obj }, new Transfer[] { FileTransfer.getInstance() });
		}
		cb.dispose();
	}

	public Object getGraphicalContents() {
		return graphicalStack.isEmpty() ? null : graphicalStack.peek();
	}

	public Object getTableContents() {
		final org.eclipse.swt.dnd.Clipboard cb = new org.eclipse.swt.dnd.Clipboard(null);
		final Object contents = cb.getContents(TextTransfer.getInstance());
		cb.dispose();
		return contents;
	}

	public Object getMultibleTableContents() {
		final org.eclipse.swt.dnd.Clipboard cb = new org.eclipse.swt.dnd.Clipboard(null);
		final Object contents = cb.getContents(FileTransfer.getInstance());
		cb.dispose();
		return contents;
	}

	private FordiacClipboard() {
		// nothing to do here
	}

}
