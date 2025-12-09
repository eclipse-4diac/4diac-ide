/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Martin Jobst - refactor to generic implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;

public interface IChangeableRowDataProvider<T> extends IRowDataProvider<T> {

	void setInput(List<T> list);
}