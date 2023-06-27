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

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.fordiac.ide.gitlab.Project;
import org.eclipse.fordiac.ide.gitlab.Package;

public class GLTreeContentProvider implements ITreeContentProvider {

	private Map<Project, List<Package>> projectsAndPackages;
	private Map<String, List<LeafNode>> packagesAndLeaves;
	
	public GLTreeContentProvider(Map<String, List<LeafNode>> packagesAndLeaves) {
		this.packagesAndLeaves = packagesAndLeaves;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof HashMap) {
			projectsAndPackages = (HashMap<Project, List<Package>>) inputElement;
			return projectsAndPackages.keySet().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Project) {
			return ((HashMap<Project, List<Package>>) projectsAndPackages).get(parentElement)
					.stream()
					.filter(distinctByPackageName(Package::getName))
					.toArray();
		} else if (parentElement instanceof Package pack) {
			return packagesAndLeaves.get(pack.getName()).toArray();
		}
		return new String[0] ;
 	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Package) {
			Object[] parent = new Object[1]; // To surpass the final/effectively final issue
			projectsAndPackages.forEach((key, value) -> {
				if(value.contains(element)) {
					parent[0] = key;
				}
			});
			return parent[0];
		} else if (element instanceof LeafNode leafNode) {
			return leafNode.getPackage();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return (element instanceof Project && !projectsAndPackages.get(element).isEmpty()) || 
				(element instanceof Package pack && !packagesAndLeaves.get(pack.getName()).isEmpty());
	}
	
	// Needed to filter the packages based on names instead their equals()
	private static <T> Predicate<T> distinctByPackageName(Function<? super T, ?> nameExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(nameExtractor.apply(t));
	}

}
