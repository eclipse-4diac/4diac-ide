/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.outline;

import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.AbstractOutlineNode;

public class OutlineHeadingNode extends AbstractOutlineNode {

	private final int level;

	public OutlineHeadingNode(final IOutlineNode parent, final int level, final String text) {
		super(parent, FordiacImage.ICON_DOCUMENTATION_EDITOR.getImageDescriptor(), text, true);
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
