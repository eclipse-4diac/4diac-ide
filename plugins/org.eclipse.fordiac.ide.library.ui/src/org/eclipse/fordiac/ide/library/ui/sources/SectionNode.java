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

import java.util.List;

import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.LibGroupNode;

public final class SectionNode {
	final String labelText;
	final List<LibGroupNode> libGroupNodeChildren;

	SectionNode(final String label, final List<LibGroupNode> payload) {
		this.labelText = label;
		this.libGroupNodeChildren = payload;
	}

	public String getLabelText() {
		return labelText;
	}
}