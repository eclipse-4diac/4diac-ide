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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xtext.ui.refactoring.ui.SyncUtil;

@SuppressWarnings("restriction")
public class STCoreSyncUtil extends SyncUtil {

	@Override
	public void totalSync(final boolean saveAll, final boolean useProgressDialog, final boolean fork)
			throws InvocationTargetException, InterruptedException {
		// always save all before refactoring
		super.totalSync(true, useProgressDialog, fork);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void waitForBuild(final IProgressMonitor monitor) {
		super.waitForBuild(monitor);
		// also need to wait for auto build jobs to finish or the avoidBuild flag from
		// the auto build job will not be cleared properly, resulting in a missed build
		// after the refactoring completes
		super.waitForAutoBuild(monitor);
	}
}
