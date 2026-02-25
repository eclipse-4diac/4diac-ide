/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.validation;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		final IEclipsePreferences preferences = DefaultScope.INSTANCE.getNode(ValidationPreferences.QUALIFIER);

		preferences.put(ValidationPreferences.COLLISION_SEVERITY, ValidationPreferences.SEVERITY_WARNING);
		preferences.put(ValidationPreferences.RIGHT_INTERFACE_BAR_COLLISION_SEVERITY,
				ValidationPreferences.SEVERITY_WARNING);
		preferences.put(ValidationPreferences.PACKAGENAME_MISMATCH_FOLDER, ValidationPreferences.SEVERITY_IGNORE);
		preferences.put(ValidationPreferences.NO_VALUE_FOR_GENERIC_TYPE_VARIABLE,
				ValidationPreferences.SEVERITY_IGNORE);
		preferences.put(ValidationPreferences.VALUE_FOR_GENERIC_INSTANCE_VARIABLE,
				ValidationPreferences.SEVERITY_WARNING);
	}

}
