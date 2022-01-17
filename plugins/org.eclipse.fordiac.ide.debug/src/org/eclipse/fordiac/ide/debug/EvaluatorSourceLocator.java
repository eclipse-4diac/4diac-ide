/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.sourcelookup.IPersistableSourceLocator2;

public class EvaluatorSourceLocator implements IPersistableSourceLocator2 {

	@Override
	public Object getSourceElement(IStackFrame stackFrame) {
		if (stackFrame instanceof EvaluatorDebugStackFrame) {
			return ((EvaluatorDebugStackFrame) stackFrame).getEvaluator().getSourceElement();
		}
		return null;
	}

	@Override
	public String getMemento() throws CoreException {
		return null;
	}

	@Override
	public void initializeFromMemento(String memento) throws CoreException {
	}

	@Override
	public void initializeDefaults(ILaunchConfiguration configuration) throws CoreException {
	}

	@Override
	public void initializeFromMemento(String memento, ILaunchConfiguration configuration) throws CoreException {
	}

	@Override
	public void dispose() {
	}
}
