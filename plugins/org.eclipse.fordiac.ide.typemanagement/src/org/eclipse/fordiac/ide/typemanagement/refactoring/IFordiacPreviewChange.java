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
 *   Dario Romano
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.EnumSet;

import org.eclipse.fordiac.ide.typemanagement.Messages;

public interface IFordiacPreviewChange {

	public enum ChangeState {
		DELETE(Messages.DeleteStructChange_DeleteChoice), CHANGE_TO_ANY(Messages.DeleteStructChange_ChangeToAnyStruct);

		private final String descriptor;

		ChangeState(final String desc) {
			this.descriptor = desc;
		}

		@Override
		public String toString() {
			return this.descriptor;
		}
	}

	EnumSet<ChangeState> getState();

	void addState(final ChangeState newState);
}
