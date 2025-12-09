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
package org.eclipse.fordiac.ide.deployment.debug;

import java.util.Map;

import org.eclipse.fordiac.ide.debug.IEvaluatorDebugTarget;
import org.eclipse.fordiac.ide.deployment.debug.watch.IWatch;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;

public interface IDeploymentDebugTarget extends IEvaluatorDebugTarget {

	@Override
	String getName();

	/**
	 * Get the automation system
	 *
	 * @return The system
	 */
	AutomationSystem getSystem();

	/**
	 * Get the current watches
	 *
	 * @return The watches
	 */
	Map<String, IWatch> getWatches();
}
