/*******************************************************************************
 * Copyright (c) 2023 Primetals Technology Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.operations;

import java.io.IOException;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public abstract class AbstractChangeHierarchyOperation extends AbstractOperation {

	protected AbstractChangeHierarchyOperation(final String label) {
		super(label);
	}

	protected static void saveHierarchy(final EObject node, final IProgressMonitor monitor) {
		final Resource eResource = node.eResource();
		if (eResource != null) {
			final WorkspaceJob job = new WorkspaceJob("Save plant hierarchy: " + eResource.getURI().toFileString()) {
				@Override
				public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
					try {
						eResource.save(null);
					} catch (final IOException e) {
						e.printStackTrace();
					}
					return Status.OK_STATUS;
				}
			};
			job.setUser(false);
			job.setSystem(true);
			job.setPriority(Job.SHORT);
			job.setRule(getRuleScope(eResource.getURI()));
			job.schedule();
		}
	}

	private static ISchedulingRule getRuleScope(final URI uri) {
		if (uri.isPlatformResource()) {
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			return root.getFile(new Path(uri.toPlatformString(true)));
		}
		return null;
	}

}