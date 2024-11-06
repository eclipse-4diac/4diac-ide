/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl    - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - added Application
 *   Alois Zoitl    - copied from InstanceCommentDecorator and adjusted for
 *                    levels
 *   Sebastian Hollersbacher - added leaves
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.decorator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.ui.util.HierarchyManagerUtil;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

public class NodeCommentDecorator implements ILightweightLabelDecorator {

	@Override
	public void addListener(final ILabelProviderListener listener) {
		// currently nothing to do here
	}

	@Override
	public void dispose() {
		// currently nothing to do here
	}

	@Override
	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	@Override
	public void removeListener(final ILabelProviderListener listener) {
		// currently nothing to do here

	}

	@Override
	public void decorate(final Object element, final IDecoration decoration) {
		String comment = null;

		if (element instanceof final Level level) {
			comment = level.getComment();
		} else if (element instanceof final Leaf leaf
				&& EcoreUtil.getRootContainer(leaf) instanceof final RootLevel root) {
			final Resource resource = root.eResource();

			final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(resource.getURI().segment(1));
			final EObject obj = HierarchyManagerUtil.getElementReferencedbyLeaf(leaf, project);
			if (obj instanceof final SubApp subapp) {
				comment = subapp.getComment();
			} else {
				comment = "SubApp not found"; //$NON-NLS-1$
			}
		}

		if (null != comment && !comment.isBlank()) {
			decoration.addSuffix(" [" + checkComment(comment) + "]"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private static String checkComment(final String comment) {
		final int i = comment.indexOf(System.getProperty("line.separator")); //$NON-NLS-1$
		if (i != -1) {
			// we have a multi-line comment only return first line
			return comment.substring(0, i) + "..."; //$NON-NLS-1$
		}
		return comment;
	}

}
