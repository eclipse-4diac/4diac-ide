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
package org.eclipse.fordiac.ide.structuredtextcore.serializer;

import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.serializer.impl.Serializer;

@SuppressWarnings("restriction")
public class STCoreSerializer extends Serializer {

	@Override
	protected int calculateReplaceRegionLength(final ICompositeNode node, final String text) {
		if (hiddenNodeFollows(node) && hasTrailingWhitespace(text)) {
			return node.getTotalLength() + getFollowingNode(node).getTotalLength();
		}
		return node.getTotalLength();
	}

	protected static boolean hasTrailingWhitespace(final String text) {
		return !text.isEmpty() && Character.isWhitespace(text.codePointBefore(text.length()));
	}
}
