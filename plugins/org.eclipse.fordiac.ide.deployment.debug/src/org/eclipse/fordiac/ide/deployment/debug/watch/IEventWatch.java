/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.watch;

import org.eclipse.debug.core.DebugException;
import org.eclipse.fordiac.ide.model.eval.value.EventValue;
import org.eclipse.fordiac.ide.model.libraryElement.Event;

public interface IEventWatch extends IInterfaceElementWatch, IVariableWatch {

	/**
	 * {@inheritDoc}
	 */
	@Override
	Event getWatchedElement();

	/**
	 * {@inheritDoc}
	 */
	@Override
	EventValue getInternalValue();

	/**
	 * Trigger an event
	 *
	 * @throws DebugException if there was a problem triggering the event
	 */
	void triggerEvent() throws DebugException;
}
