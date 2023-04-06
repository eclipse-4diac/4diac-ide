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

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.gitlab4j.api.models.Package;
import org.gitlab4j.api.models.Project;

public class GLTreeContentProvider implements ITreeContentProvider {

	private Object projectsAndPackages;
	
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof HashMap) {
			projectsAndPackages = inputElement;
			return ((HashMap<Project, List<Package>>) inputElement).keySet().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (projectsAndPackages instanceof HashMap && parentElement instanceof Project) {
			return ((HashMap<Project, List<Package>>) projectsAndPackages).get(parentElement).toArray();
		} else if (parentElement instanceof Package) {
			return new String[] {((Package) parentElement).getVersion()};
		}
		return new String[0] ;
 	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Package) {
			Object[] parent = new Object[1]; // To surpass the final/effectively final issue
			((HashMap<Project, List<Package>>) projectsAndPackages).forEach((key, value) -> {
				if(value.contains(element)) {
					parent[0] = key;
				}
			});
			return parent[0];
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return element instanceof Project || element instanceof Package; // extend the check with hasElements
	}

}
