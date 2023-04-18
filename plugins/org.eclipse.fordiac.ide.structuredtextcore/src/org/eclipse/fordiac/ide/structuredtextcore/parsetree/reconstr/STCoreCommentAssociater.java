/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.parsetree.reconstr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCommentPosition;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreFactory;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parsetree.reconstr.impl.DefaultCommentAssociater;

public class STCoreCommentAssociater extends DefaultCommentAssociater {

	public List<STComment> associateComments(final EObject model) {
		return associateCommentsWithSemanticEObjects(model).entrySet().stream()
				.sorted(Comparator.comparingInt(entry -> entry.getKey().getOffset()))
				.map(entry -> newComment(entry.getKey(), entry.getValue())).toList();
	}

	public Map<ILeafNode, EObject> associateCommentsWithSemanticEObjects(final EObject model) {
		final var node = NodeModelUtils.findActualNodeFor(model);
		if (node != null) {
			return associateCommentsWithSemanticEObjects(model, Set.of(node.getRootNode()));
		}
		return Collections.emptyMap();
	}

	protected static STComment newComment(final ILeafNode commentNode, final EObject semanticObject) {
		final var comment = STCoreFactory.eINSTANCE.createSTComment();
		comment.setContext(semanticObject);
		comment.setText(commentNode.getText());
		comment.setPosition(computeRelativePosition(commentNode, semanticObject));
		return comment;
	}

	protected static STCommentPosition computeRelativePosition(final ILeafNode commentNode,
			final EObject semanticObject) {
		return computeRelativePosition(commentNode, NodeModelUtils.findActualNodeFor(semanticObject));
	}

	protected static STCommentPosition computeRelativePosition(final ILeafNode commentNode,
			final ICompositeNode semanticNode) {
		if (semanticNode != null) {
			if (isCommentBefore(commentNode, semanticNode)) {
				return STCommentPosition.BEFORE;
			} else if (isCommentAfter(commentNode, semanticNode)) {
				return STCommentPosition.AFTER;
			} else if (isCommentInner(commentNode, semanticNode)) {
				return STCommentPosition.INNER;
			}
		}
		return STCommentPosition.UNKNOWN;
	}

	@Override
	protected void associateCommentsWithSemanticEObjects(final Map<ILeafNode, EObject> mapping,
			final ICompositeNode rootNode) {
		EObject currentEObject = null;
		final List<ILeafNode> currentComments = new ArrayList<>();
		for (final ILeafNode node : rootNode.getLeafNodes()) {
			if (tokenUtil.isCommentNode(node)) {
				currentComments.add(node);
			} else if (!node.isHidden()) {
				final EObject nextEObject = tokenUtil.getTokenOwner(node);
				if (nextEObject != null) {
					if (currentEObject == null) {
						addMapping(mapping, currentComments, nextEObject);
					} else {
						addMapping(mapping, currentComments, currentEObject, nextEObject);
					}
					currentEObject = nextEObject;
				}
			}
		}
		if (!currentComments.isEmpty()) {
			if (currentEObject != null) {
				addMapping(mapping, currentComments, currentEObject);
			} else {
				final EObject objectForRemainingComments = getEObjectForRemainingComments(rootNode);
				if (objectForRemainingComments != null) {
					addMapping(mapping, currentComments, objectForRemainingComments);
				}
			}
		}
	}

	@SuppressWarnings("static-method") // should be overridable
	protected void addMapping(final Map<ILeafNode, EObject> mapping, final List<ILeafNode> currentComments,
			final EObject previousEObject, final EObject nextEObject) {
		final ICompositeNode previousNode = NodeModelUtils.findActualNodeFor(previousEObject);
		final ICompositeNode nextNode = NodeModelUtils.findActualNodeFor(nextEObject);
		for (final ILeafNode commentNode : currentComments) {
			// associate comments with (in order of preference):
			// - previous object if on the same line and AFTER previous
			// - next object if BEFORE next
			// - previous object if AFTER previous
			// - previous object if on the same line
			// - next object
			final boolean sameLine = isCommentSameLine(commentNode, previousNode);
			final boolean afterPrevious = isCommentAfter(commentNode, previousNode);
			final boolean beforeNext = isCommentBefore(commentNode, nextNode);
			if ((sameLine && afterPrevious) || (!beforeNext && (afterPrevious || sameLine))) {
				mapping.put(commentNode, previousEObject);
			} else {
				mapping.put(commentNode, nextEObject);
			}
		}
		currentComments.clear();
	}

	protected static boolean isCommentSameLine(final ILeafNode commentNode, final ICompositeNode semanticNode) {
		return commentNode.getStartLine() == semanticNode.getEndLine();
	}

	protected static boolean isCommentBefore(final ILeafNode commentNode, final ICompositeNode semanticNode) {
		return commentNode.getEndOffset() <= semanticNode.getOffset();
	}

	protected static boolean isCommentAfter(final ILeafNode commentNode, final ICompositeNode semanticNode) {
		return commentNode.getOffset() >= semanticNode.getEndOffset();
	}

	protected static boolean isCommentInner(final ILeafNode commentNode, final ICompositeNode semanticNode) {
		return commentNode.getOffset() > semanticNode.getOffset()
				&& commentNode.getEndOffset() < semanticNode.getEndOffset();
	}
}
