/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.sources;

import java.util.Objects;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public record LibrarySourceUIComponents(ITreeContentProvider contentProvider, ILabelProvider labelProvider,
		Object input, Object context) {
	public LibrarySourceUIComponents(final ITreeContentProvider contentProvider, final ILabelProvider labelProvider,
			final Object input, final Object context) {
		this.contentProvider = Objects.requireNonNull(contentProvider);
		this.labelProvider = Objects.requireNonNull(labelProvider);
		this.input = input;
		this.context = context;
	}
}