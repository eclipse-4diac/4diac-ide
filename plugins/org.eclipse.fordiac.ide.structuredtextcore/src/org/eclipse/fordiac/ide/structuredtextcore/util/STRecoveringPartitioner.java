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

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;

public abstract class STRecoveringPartitioner<E extends INamedElement> extends STAbstractCorePartitioner<E> {
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

	protected void handleLostAndFound(final ICompositeNode rootNode, final List<? extends EObject> source,
			final List<E> result) {
		var lastOffset = 0;
		for (int index = 0; index < source.size(); index++) {
			final var element = source.get(index);
			final var node = NodeModelUtils.findActualNodeFor(element);
			final int totalOffset = node.getTotalOffset();
			if (totalOffset > lastOffset) {
				handleLostAndFound(rootNode, index, lastOffset, totalOffset, result);
			}
			lastOffset = node.getTotalEndOffset();
		}
		final int totalEndOffset = rootNode.getTotalEndOffset();
		if (totalEndOffset > lastOffset) {
			if (result.isEmpty()) {
				handleLostAndFound(rootNode, result.size(), lastOffset, totalEndOffset, result);
			} else {
				appendText(result.get(result.size() - 1), rootNode.getText().substring(lastOffset, totalEndOffset));
			}
		}
	}

	protected void handleLostAndFound(final ICompositeNode rootNode, final int index, final int start, final int end,
			final List<E> result) {
		final String text = rootNode.getText().substring(start, end);
		if (!text.isBlank()) {
			result.add(index, createLostAndFound(text, index));
		}
	}

	protected static String generateLostAndFoundName(final int index) {
		return LOST_AND_FOUND_NAME_PATTERN.formatted(Integer.valueOf(index));
	}

	protected static String generateLostAndFoundComment(final int index) {
		return LOST_AND_FOUND_COMMENT_PATTERN.formatted(Integer.valueOf(index));
	}

	protected abstract STCorePartition createEmergencyPartition(final String originalSource);

	protected abstract E createLostAndFound(final String text, final int index);

	protected abstract void appendText(final E element, final String text);
}
