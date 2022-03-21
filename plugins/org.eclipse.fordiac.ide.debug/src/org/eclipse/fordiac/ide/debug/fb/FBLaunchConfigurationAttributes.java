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
package org.eclipse.fordiac.ide.debug.fb;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public interface FBLaunchConfigurationAttributes extends LaunchConfigurationAttributes {
	String EVENT = "org.eclipse.fordiac.ide.debug.event";
	String ARGUMENTS = "org.eclipse.fordiac.ide.debug.arguments";

	static Event getEvent(final ILaunchConfiguration configuration, final FBType type, final Event defaultEvent) throws CoreException {
		final String eventAttribute = configuration.getAttribute(EVENT, "");
		if (eventAttribute != null && !eventAttribute.isEmpty()) {
			final var event = type.getInterfaceList().getEvent(eventAttribute);
			if (event != null && event.isIsInput()) {
				return event;
			}
		}
		return defaultEvent;
	}

	static List<Variable> getArguments(final ILaunchConfiguration configuration, final List<Variable> defaultArguments)
			throws CoreException {
		final var argumentsAttribute = configuration.getAttribute(ARGUMENTS, Collections.emptyMap());
		if (argumentsAttribute != null && defaultArguments != null) {
			defaultArguments.forEach(arg -> {
				final var argumentValue = argumentsAttribute.get(arg.getName());
				try {
					if (argumentValue != null) {
						arg.setValue(argumentValue);
					}
				} catch (final Exception e) {
					// ignore
				}
			});
		}
		return defaultArguments;
	}
}
