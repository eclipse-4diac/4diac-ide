/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.handlers;

import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.typemanagement.RenameType;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class RenameSystemParticipant extends RenameType {

	@Override
	protected void getWrongIdentifierErrorStatus(final RefactoringStatus result) {
		result.addFatalError(Messages.SystemNameNotValid);
	}

}
