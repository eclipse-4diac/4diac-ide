/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class IntervalVerifyListener implements Listener {
	private final Text verifiableText;

	public IntervalVerifyListener(final Text verifiableText) {
		this.verifiableText = verifiableText;
	}

	@Override
	public void handleEvent(final org.eclipse.swt.widgets.Event event) {
		if (event.keyCode == SWT.BS || event.keyCode == SWT.DEL || event.keyCode == SWT.ARROW_LEFT
				|| event.keyCode == SWT.ARROW_RIGHT) {
			event.doit = true;
			return;
		}
		final String text = verifiableText.getText();
		if (text.isEmpty() && (event.character == '[') || Character.isDigit(event.character)) {
			event.doit = true;
			return;
		}
		if (!text.isEmpty() && text.contains(",") && Character.isDigit(text.charAt(text.length() - 1)) //$NON-NLS-1$
				&& event.character == ']') {
			event.doit = true;
			return;
		}
		if (text.length() > 1 && text.charAt(0) == '[' && Character.isDigit(text.charAt(1)) && !text.contains(",") //$NON-NLS-1$
				&& event.character == ',') {
			event.doit = true;
			return;
		}
		if (!text.isEmpty() && Character.isDigit(text.charAt(0)) && Character.isDigit(event.character)) {
			event.doit = true;
			return;
		}
		event.doit = false;

	}

}