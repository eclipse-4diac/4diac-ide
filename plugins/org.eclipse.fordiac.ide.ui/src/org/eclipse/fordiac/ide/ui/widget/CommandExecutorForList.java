/*******************************************************************************
 * Copyright (c) 2025 Monika Wenger
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.List;

public interface CommandExecutorForList<T> extends CommandExecutor {

	void executeCommand(final List<T> items, final boolean isCreate);

}
