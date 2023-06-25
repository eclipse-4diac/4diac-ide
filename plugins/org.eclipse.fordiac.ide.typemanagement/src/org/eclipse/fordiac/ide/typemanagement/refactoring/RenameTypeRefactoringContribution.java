/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Map;

import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;

public class RenameTypeRefactoringContribution extends RefactoringContribution {

	@Override
	public RefactoringDescriptor createDescriptor(final String id, final String project, final String description,
			final String comment, final Map arguments, final int flags) {
		return null;
	}

	@Override
	public Map retrieveArgumentMap(final RefactoringDescriptor descriptor) {
		return super.retrieveArgumentMap(descriptor);
	}
}