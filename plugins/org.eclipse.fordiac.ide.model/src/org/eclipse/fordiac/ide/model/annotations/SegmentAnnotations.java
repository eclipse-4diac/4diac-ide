/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.annotations;

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;

public class SegmentAnnotations {
	// *** Segment ***//
	public static SystemConfiguration getSystemConfiguration(final Segment s) {
		return (SystemConfiguration) s.eContainer();
	}

	public static AutomationSystem getAutomationSystem(final Segment s) {
		return s.getSystemConfiguration().getAutomationSystem();
	}

	private SegmentAnnotations() {
		throw new UnsupportedOperationException("The utility class Annotations should not be instatiated"); //$NON-NLS-1$
	}
}
