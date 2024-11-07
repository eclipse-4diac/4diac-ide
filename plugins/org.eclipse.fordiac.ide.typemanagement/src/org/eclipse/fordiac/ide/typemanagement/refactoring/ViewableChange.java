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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.typemanagement.Messages;

public abstract class ViewableChange<T extends EObject> extends AbstractCommandChange<T> {

	private final EnumSet<ChangeState> state;

	protected ViewableChange(final String name, final URI elementURI, final Class elementClass) {
		super(name, elementURI, elementClass);
		this.state = getDefaultSelection();
		initEnablement();
	}

	protected ViewableChange(final URI elementURI, final Class elementClass) {
		super(elementURI, elementClass);
		this.state = getDefaultSelection();
		initEnablement();
	}

	private void initEnablement() {
		if (getDefaultSelection().contains(ChangeState.NO_CHANGE)) {
			setEnabled(false);
		}
	}

	public enum ChangeState {
		NO_SELECTION(""), DELETE(Messages.PreviewChange_DeleteChoice),
		CHANGE_TO_ANY(Messages.PreviewChange_ChangeToAnyStruct),
		REPLACE_WITH_MARKER(Messages.PreviewChange_ReplaceWithMarker), NO_CHANGE(Messages.PreviewChange_NoChange),
		RECONNECT(Messages.IFordiacPreviewChange_Reconnect0);

		private final String descriptor;

		ChangeState(final String desc) {
			this.descriptor = desc;
		}

		@Override
		public String toString() {
			return this.descriptor;
		}
	}

	public EnumSet<ChangeState> getState() {
		return state;
	}

	public abstract EnumSet<ChangeState> getAllowedChoices();

	public void addState(final ChangeState newState) {
		state.add(newState);
	}

	public EnumSet<ChangeState> getDefaultSelection() {
		return EnumSet.of(ChangeState.NO_CHANGE);
	}
}
