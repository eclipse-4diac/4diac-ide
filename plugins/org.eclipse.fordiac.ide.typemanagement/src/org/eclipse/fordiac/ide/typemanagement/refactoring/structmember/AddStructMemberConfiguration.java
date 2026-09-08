/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.typemanagement.refactoring.structmember;

import java.util.Objects;

public record AddStructMemberConfiguration(String memberName, String comment, String memberTypeName,
		String arraySize, String insertBefore) {

	public AddStructMemberConfiguration {
		memberName = Objects.requireNonNull(memberName).strip();
		comment = Objects.requireNonNullElse(comment, ""); //$NON-NLS-1$
		memberTypeName = Objects.requireNonNull(memberTypeName).strip();
		arraySize = Objects.requireNonNullElse(arraySize, ""); //$NON-NLS-1$
		insertBefore = insertBefore == null || insertBefore.isBlank() ? null : insertBefore;
	}
}
