/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.ui.widget;

import java.util.Collections;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;

public class ChangeableListDataProvider<T> extends ListDataProvider<T> implements IChangeableRowDataProvider<T> {

	public ChangeableListDataProvider(final IColumnAccessor<T> columnAccessor) {
		this(Collections.emptyList(), columnAccessor);
	}

	public ChangeableListDataProvider(final List<T> list, final IColumnAccessor<T> columnAccessor) {
		super(list, columnAccessor);
	}

	@Override
	public void setInput(final List<T> list) {
		this.list = list;
	}
}
