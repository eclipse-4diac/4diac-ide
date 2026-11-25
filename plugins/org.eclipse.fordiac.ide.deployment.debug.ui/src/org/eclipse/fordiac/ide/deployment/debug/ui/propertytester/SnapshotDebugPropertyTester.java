/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.propertytester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.fordiac.ide.deployment.debug.snapshot.FBSnapshotValidator;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

/** Tests whether a selected element can be debugged with a snapshot. */
public class SnapshotDebugPropertyTester extends PropertyTester {

	public static final String IS_SNAPSHOT_DEBUGGABLE = "isSnapshotDebuggable"; //$NON-NLS-1$

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args,
			final Object expectedValue) {
		if (!IS_SNAPSHOT_DEBUGGABLE.equals(property)) {
			return false;
		}
		// a warning means that internal function blocks need a runtime, which cannot
		// be started yet
		return FBSnapshotValidator.validate(Adapters.adapt(receiver, FBNetworkElement.class)).isOK();
	}
}
