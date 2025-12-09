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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parsetree.reconstr.impl.NodeIterator;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.TextRegion;

public final class STCoreRefactoringUtil {

	public static EObject findSelectedSemanticObject(final XtextResource resource, final ITextSelection selection) {
		final IParseResult parseResult = resource.getParseResult();
		if (parseResult != null) {
			final EObject rootSemanticObject = parseResult.getRootASTElement();
			final TreeIterator<EObject> iterator = EcoreUtil.getAllProperContents(rootSemanticObject, true);
			EObject result = null;
			while (iterator.hasNext()) {
				final EObject semanticObject = iterator.next();
				final INode node = NodeModelUtils.findActualNodeFor(semanticObject);
				if (node != null) {
					final ITextRegion nodeRegion = getExtendedTotalTextRegion(node);
					if (!intersects(nodeRegion, selection)) {
						iterator.prune();
					} else if (contains(nodeRegion, selection)) {
						result = semanticObject;
					}
				}
			}
			return result;
		}
		return null;
	}

	public static List<EObject> findSelectedChildSemanticObjects(final EObject container,
			final ITextSelection selection) {
		final List<EObject> result = new ArrayList<>();
		ITextRegion commonRegion = null;
		for (final EObject semanticObject : container.eContents()) {
			final INode node = NodeModelUtils.findActualNodeFor(semanticObject);
			if (node != null) {
				final ITextRegion nodeRegion = getExtendedTotalTextRegion(node);
				if (intersects(nodeRegion, selection)) {
					if (commonRegion == null) {
						commonRegion = nodeRegion;
					} else {
						commonRegion = commonRegion.merge(nodeRegion);
					}
					result.add(semanticObject);
				}
			}
		}
		if (commonRegion != null && contains(commonRegion, selection)) {
			return result;
		}
		return List.of(container);
	}

	public static <T extends EObject> List<T> findSelectedChildSemanticObjectsOfType(final EObject container,
			final ITextSelection selection, final Class<? extends T> filter) {
		final List<EObject> childSemanticObjects = findSelectedChildSemanticObjects(container, selection);
		if (childSemanticObjects.stream().allMatch(filter::isInstance)) {
			return childSemanticObjects.stream().map(filter::cast).collect(Collectors.toList());
		}
		if (filter.isInstance(container)) {
			return List.of(filter.cast(container));
		}
		return Collections.emptyList();
	}

	public static ITextRegion getExtendedTotalTextRegion(final INode node) {
		ITextRegion result = node.getTotalTextRegion();
		// extend with hidden leaf nodes in the same line
		final var iterator = new NodeIterator(node);
		iterator.prune(); // prune current node
		while (iterator.hasNext()) {
			final INode next = iterator.next();
			if (next instanceof final ILeafNode nextLeaf) {
				if (!nextLeaf.isHidden()) {
					break;
				}
				result = result.merge(next.getTextRegion());
			}
		}
		return result;
	}

	public static ITextSelection trimSelection(final ITextSelection selection) {
		final String selectionText = selection.getText();
		int offset = 0;
		int length = selection.getLength();
		while (offset < length && Character.isWhitespace(selectionText.charAt(offset))) {
			offset++;
		}
		while (length > offset && Character.isWhitespace(selectionText.charAt(length - 1))) {
			length--;
		}
		return new TextSelection(selection.getOffset() + offset, length - offset);
	}

	public static ITextRegion trimRegion(final ITextRegion region, final ICompositeNode rootNode) {
		final String selectionText = rootNode.getText().substring(region.getOffset(),
				region.getOffset() + region.getLength());
		int offset = 0;
		int length = region.getLength();
		while (offset < length && Character.isWhitespace(selectionText.charAt(offset))) {
			offset++;
		}
		while (length > offset && Character.isWhitespace(selectionText.charAt(length - 1))) {
			length--;
		}
		return new TextRegion(region.getOffset() + offset, length - offset);
	}

	public static ITextRegion alignRegion(final ITextRegion region, final ICompositeNode rootNode) {
		final ILeafNode firstLeafNode = NodeModelUtils.findLeafNodeAtOffset(rootNode, region.getOffset());
		final ILeafNode lastLeafNode = NodeModelUtils.findLeafNodeAtOffset(rootNode,
				region.getOffset() + (region.getLength() > 0 ? region.getLength() - 1 : 0));
		return new TextRegion(firstLeafNode.getOffset(), lastLeafNode.getEndOffset() - firstLeafNode.getOffset());
	}

	private static boolean contains(final ITextRegion region, final ITextSelection selection) {
		return region.getOffset() <= selection.getOffset()
				&& region.getOffset() + region.getLength() >= selection.getOffset() + selection.getLength();
	}

	private static boolean intersects(final ITextRegion region, final ITextSelection selection) {
		return region.getOffset() < selection.getOffset() + selection.getLength()
				&& region.getOffset() + region.getLength() > selection.getOffset();
	}

	private STCoreRefactoringUtil() {
		throw new UnsupportedOperationException();
	}
}
