/**
 * Copyright (c) 2023, 2025 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval.fb;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.fordiac.ide.model.libraryElement.Event;

public interface FBEvaluatorCountingEventQueue extends FBEvaluatorEventQueue {
	/**
	 * Get the number of events processed by this queue
	 */
	AtomicInteger getCount(final Event event);

	/**
	 * Get the total number of input events processed by this queue
	 */
	AtomicLong getTotalInputCount();
}
