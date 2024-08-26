/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.outline;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSourceElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.ui.outline.STCoreOutlineTreeProvider;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.util.ITextRegion;

@SuppressWarnings({ "static-method", "java:S100" })
public class STAlgorithmOutlineTreeProvider extends STCoreOutlineTreeProvider {

	private static final Pattern HEADING_PATTERN = Pattern.compile("//\\s*\\$h(\\d+)\\s+(.+)\\R"); //$NON-NLS-1$

	protected boolean _isLeaf(final STAlgorithmSourceElement modelElement) {
		final ICompositeNode node = NodeModelUtils.findActualNodeFor(modelElement);
		if (node != null) {
			return !hasHeadings(node);
		}
		return true;
	}

	protected void _createChildren(final IOutlineNode parentNode, final STAlgorithmSourceElement modelElement) {
		final ICompositeNode node = NodeModelUtils.findActualNodeFor(modelElement);
		if (node != null) {
			createHeadingNodes(parentNode, node);
		}
	}

	protected boolean hasHeadings(final ICompositeNode node) {
		for (final ILeafNode leaf : node.getLeafNodes()) {
			if (isSingleLineComment(leaf) && HEADING_PATTERN.matcher(leaf.getText()).matches()) {
				return true;
			}
		}
		return false;
	}

	protected void createHeadingNodes(IOutlineNode parentNode, final ICompositeNode node) {
		for (final ILeafNode leaf : node.getLeafNodes()) {
			if (isSingleLineComment(leaf)) {
				final Matcher matcher = HEADING_PATTERN.matcher(leaf.getText());
				if (matcher.matches()) {
					final int level = Integer.parseInt(matcher.group(1));
					final String text = matcher.group(2);
					parentNode = findHeadingParent(parentNode, level);
					parentNode = createHeadingNode(parentNode, level, text, leaf.getTextRegion());
				}
			}
		}
	}

	protected IOutlineNode findHeadingParent(IOutlineNode parentNode, final int level) {
		// move up until parent has higher level
		while (getLevel(parentNode) >= level) {
			parentNode = parentNode.getParent();
		}
		// create missing intermediate nodes
		while (getLevel(parentNode) < level - 1) {
			parentNode = createHeadingNode(parentNode, getLevel(parentNode) + 1,
					Messages.STAlgorithmOutlineTreeProvider_MissingHeading, null);
		}
		return parentNode;
	}

	protected OutlineHeadingNode createHeadingNode(final IOutlineNode parentNode, final int level, final String text,
			final ITextRegion region) {
		final OutlineHeadingNode node = new OutlineHeadingNode(parentNode, level, text);
		if (region != null) {
			node.setTextRegion(region);
		}
		return node;
	}

	protected static boolean isSingleLineComment(final ILeafNode node) {
		return node.isHidden() && node.getGrammarElement() instanceof final AbstractRule abstractRule
				&& "SL_COMMENT".equals(abstractRule.getName()); //$NON-NLS-1$
	}

	protected int getLevel(final IOutlineNode node) {
		if (node instanceof final OutlineHeadingNode heading) {
			return heading.getLevel();
		}
		return 0;
	}
}
