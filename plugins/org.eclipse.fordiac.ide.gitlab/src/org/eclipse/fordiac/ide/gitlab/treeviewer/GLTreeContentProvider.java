/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gitlab.treeviewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.fordiac.ide.gitlab.Package;
import org.eclipse.fordiac.ide.gitlab.Project;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class GLTreeContentProvider implements ITreeContentProvider {

	private Map<Project, List<Package>> projectsAndPackages;
	private final Map<String, List<LeafNode>> packagesAndLeaves;

	public GLTreeContentProvider(final Map<String, List<LeafNode>> packagesAndLeaves) {
		this.packagesAndLeaves = packagesAndLeaves;
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof HashMap) {
			projectsAndPackages = (HashMap<Project, List<Package>>) inputElement;
			return projectsAndPackages.keySet().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof Project) {
			return ((HashMap<Project, List<Package>>) projectsAndPackages).get(parentElement).stream()
					.filter(distinctByPackageName(Package::name)).toArray();
		}
		if (parentElement instanceof final Package pack) {
			return packagesAndLeaves.get(pack.name()).toArray();
		}
		return new String[0];
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof Package) {
			final Object[] parent = new Object[1]; // To surpass the final/effectively final issue
			projectsAndPackages.forEach((key, value) -> {
				if (value.contains(element)) {
					parent[0] = key;
				}
			});
			return parent[0];
		}
		if (element instanceof final LeafNode leafNode) {
			return leafNode.getPackage();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		return (element instanceof Project && !projectsAndPackages.get(element).isEmpty())
				|| (element instanceof final Package pack && !packagesAndLeaves.get(pack.name()).isEmpty());
	}

	// Needed to filter the packages based on names instead their equals()
	private static <T> Predicate<T> distinctByPackageName(final Function<? super T, ?> nameExtractor) {
		final Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(nameExtractor.apply(t));
	}

}
