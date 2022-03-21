/**
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
 */
package org.eclipse.fordiac.ide.debug.st;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;

public interface STLaunchConfigurationAttributes extends LaunchConfigurationAttributes {
	String ID = "org.eclipse.fordiac.ide.debug.stFunctionLaunch"; //$NON-NLS-1$

	String FUNCTION = "org.eclipse.fordiac.ide.debug.st.function"; //$NON-NLS-1$

	static STFunction getFunction(final ILaunchConfiguration configuration, final STFunctionSource source,
			final STFunction defaultFunction) throws CoreException {
		final String functionAttribute = configuration.getAttribute(FUNCTION, ""); //$NON-NLS-1$
		return source.getFunctions().stream().filter(func -> functionAttribute.equals(func.getName())).findFirst()
				.orElse(defaultFunction);
	}
}
