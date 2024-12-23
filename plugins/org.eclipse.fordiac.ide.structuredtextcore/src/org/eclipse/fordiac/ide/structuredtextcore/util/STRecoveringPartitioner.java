/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SequencedMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.xtext.documentation.IEObjectDocumentationProviderExtension;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.ITextRegion;

public abstract class STRecoveringPartitioner<S extends EObject, E extends INamedElement>
		extends STAbstractCorePartitioner<E> {
	public static final String LOST_AND_FOUND_NAME = "LOST_AND_FOUND"; //$NON-NLS-1$
	protected static final String LOST_AND_FOUND_NAME_PATTERN = LOST_AND_FOUND_NAME + "_%s"; //$NON-NLS-1$
	public static final String LOST_AND_FOUND_COMMENT = "lost+found"; //$NON-NLS-1$
	protected static final String LOST_AND_FOUND_COMMENT_PATTERN = LOST_AND_FOUND_COMMENT + " %s"; //$NON-NLS-1$

	protected Optional<STCorePartition> emergencyPartition(final XtextResource resource) {
		if (resource.getParseResult() != null && resource.getParseResult().getRootNode() != null) {
			final String text = resource.getParseResult().getRootNode().getText();
			return Optional.of(createEmergencyPartition(text));
		}
		return Optional.empty();
	}

	protected Optional<STCorePartition> emergencyPartition(final STSource source) {
		final ICompositeNode node = NodeModelUtils.getNode(source);
		if (node != null) {
			return Optional.of(createEmergencyPartition(node.getText()));
		}
		return Optional.empty();
	}

	protected List<E> convertSourceElements(final ICompositeNode rootNode, final EList<S> elements) {
		final SequencedMap<S, E> mapping = new LinkedHashMap<>();
		for (final S element : elements) {
			final E converted = convertSourceElement(element);
			if (converted != null) {
				mapping.put(element, converted);
			}
		}
		final List<E> result = handleLostAndFound(rootNode, mapping);
		handleDuplicates(result);
		return result;
	}

	protected List<E> handleLostAndFound(final ICompositeNode rootNode, final SequencedMap<S, E> elements) {
		int lastOffset = 0;
		final List<E> result = new ArrayList<>(elements.size());
		for (final Map.Entry<S, E> entry : elements.entrySet()) {
			final ITextRegion region = getTextRegionWithComment(entry.getKey());
			if (region.getOffset() > lastOffset) {
				handleLostAndFound(rootNode, lastOffset, region.getOffset(), result);
			}
			lastOffset = region.getOffset() + region.getLength();
			result.add(entry.getValue());
		}
		final int totalEndOffset = rootNode.getTotalEndOffset();
		if (totalEndOffset > lastOffset) {
			handleLostAndFound(rootNode, lastOffset, totalEndOffset, result);
		}
		return result;
	}

	private ITextRegion getTextRegionWithComment(final EObject element) {
		final INode node = NodeModelUtils.getNode(element);
		if (getDocumentationProvider() instanceof final IEObjectDocumentationProviderExtension documentationProviderExtension) {
			return documentationProviderExtension.getDocumentationNodes(element).stream().map(INode::getTextRegion)
					.reduce(node.getTextRegion(), ITextRegion::merge);
		}
		return node.getTotalTextRegion();
	}

	protected void handleLostAndFound(final ICompositeNode rootNode, final int start, final int end,
			final List<E> result) {
		final String text = rootNode.getText().substring(start, end);
		if (!text.isBlank()) {
			result.add(createLostAndFound(text.trim(), result.size()));
		}
	}

	protected static String generateLostAndFoundName(final int index) {
		return LOST_AND_FOUND_NAME_PATTERN.formatted(Integer.valueOf(index));
	}

	protected static String generateLostAndFoundComment(final int index) {
		return LOST_AND_FOUND_COMMENT_PATTERN.formatted(Integer.valueOf(index));
	}

	protected static void appendBlockComment(final INamedElement element, final StringBuilder builder) {
		final String comment = element.getComment();
		if (comment != null && !comment.isBlank()) {
			builder.append("(*"); //$NON-NLS-1$
			builder.append(CommonElementExporter.LINE_END);
			for (final String s : comment.trim().split(CommonElementExporter.LINE_END)) {
				builder.append(" * "); //$NON-NLS-1$
				builder.append(s);
				builder.append(CommonElementExporter.LINE_END);
			}
			builder.append(" *)"); //$NON-NLS-1$
			builder.append(CommonElementExporter.LINE_END);
		}
	}

	protected static void appendText(final String text, final StringBuilder builder) {
		if (!text.startsWith(CommonElementExporter.LINE_END)) {
			builder.append(CommonElementExporter.LINE_END);
		}
		builder.append(text);
		if (!text.endsWith(CommonElementExporter.LINE_END)) {
			builder.append(CommonElementExporter.LINE_END);
		}
	}

	protected static String getText(final INode node) {
		return node.getRootNode().getText().substring(node.getOffset(), node.getEndOffset());
	}

	protected static String getTotalText(final INode node) {
		// rewind begin until after non-hidden node
		INode begin = node;
		while (begin.getPreviousSibling() instanceof final ILeafNode leafNode && leafNode.isHidden()) {
			begin = leafNode;
		}
		// forward end until before non-hidden node
		INode end = node;
		while (end.getNextSibling() instanceof final ILeafNode leafNode && leafNode.isHidden()) {
			end = leafNode;
		}
		return node.getRootNode().getText().substring(begin.getTotalOffset(), end.getTotalEndOffset());
	}

	protected abstract STCorePartition createEmergencyPartition(final String originalSource);

	protected abstract E convertSourceElement(final S function);

	protected abstract E createLostAndFound(final String text, final int index);

	protected abstract void appendText(final E element, final String text);
}
