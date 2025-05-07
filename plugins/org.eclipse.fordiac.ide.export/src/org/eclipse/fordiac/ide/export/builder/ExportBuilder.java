/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.export.builder;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

public class ExportBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "org.eclipse.fordiac.ide.export.builder"; //$NON-NLS-1$

	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		return new IProject[0];
	}
}
