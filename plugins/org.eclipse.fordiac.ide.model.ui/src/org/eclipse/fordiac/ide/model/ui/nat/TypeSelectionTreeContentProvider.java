/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - extracted out of the now AbstractSelectionButton class,
 *   took project finding from the search plug-in.
 *   Martin Erich Jobst - refactored type proposals
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.nat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IPath;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.jface.viewers.ITreeContentProvider;

public abstract class TypeSelectionTreeContentProvider implements ITreeContentProvider {

	protected TypeSelectionTreeContentProvider() {
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof final TypeLibrary typeLibrary) {
			return createTree(typeLibrary).toArray();
		}
		return new Object[0];
	}

	protected abstract List<TypeNode> createTree(final TypeLibrary typeLibrary);

	protected static void addPackageSubtree(final TypeNode node, final Collection<? extends TypeEntry> entries) {
		entries.stream().collect(Collectors.groupingBy(TypeEntry::getPackageName))
				.forEach((packageName, subEntries) -> {
					if (packageName.isEmpty()) {
						subEntries.stream().map(TypeNode::new).forEachOrdered(node::addChild);
					} else {
						final TypeNode subNode = new TypeNode(packageName);
						subEntries.stream().map(TypeNode::new).forEachOrdered(subNode::addChild);
						node.addChild(subNode);
					}
				});
	}

	protected static void addPathSubtree(final TypeNode node, final Collection<? extends TypeEntry> entries) {
		addPathSubtree(node, entries, 1);
	}

	private static void addPathSubtree(final TypeNode node, final Collection<? extends TypeEntry> entries,
			final int level) {
		entries.stream().collect(Collectors.groupingBy(entry -> {
			final IPath path = entry.getFile().getFullPath();
			return path.segmentCount() > level + 1 ? path.segment(level) : ""; //$NON-NLS-1$
		})).forEach((segment, subEntries) -> {
			if (segment.isEmpty()) {
				subEntries.stream().map(TypeNode::new).forEachOrdered(node::addChild);
			} else {
				final TypeNode subNode = new TypeNode(segment);
				addPathSubtree(subNode, subEntries, level + 1);
				node.addChild(subNode);
			}
		});
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof final TypeNode typeNode) {
			return typeNode.getChildren().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof final TypeNode typeNode) {
			return typeNode.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof final TypeNode typeNode) {
			return !typeNode.getChildren().isEmpty();
		}
		return false;
	}
}
