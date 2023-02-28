/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeFbTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;

public class FBColumnAccessor implements IColumnAccessor<FB> {

	private final CommandExecutor section;
	private TypeLibrary library;

	public FBColumnAccessor(final CommandExecutor section) {
		this.section = section;
	}

	public FBColumnAccessor(final CommandExecutor section, final TypeLibrary library) {
		this.section = section;
		setTypeLib(library);
	}

	protected CommandExecutor getSection() {
		return section;
	}

	protected TypeLibrary getLibrary() {
		return library;
	}

	private void setLibrary(final TypeLibrary library) {
		this.library = library;
	}

	@Override
	public Object getDataValue(final FB rowObject, final int columnIndex) {
		switch (columnIndex) {
		case I4diacNatTableUtil.NAME:
			return rowObject.getName();
		case I4diacNatTableUtil.TYPE:
			return rowObject.getTypeName();
		case I4diacNatTableUtil.COMMENT:
			return rowObject.getComment();
		default:
			return rowObject;
		}
	}

	@Override
	public void setDataValue(final FB rowObject, final int columnIndex, final Object newValue) {
		final String value = newValue instanceof String ? (String) newValue : null;
		Command cmd = null;
		switch (columnIndex) {
		case I4diacNatTableUtil.NAME:
			if (value == null) {
				return;
			}
			cmd = new ChangeNameCommand(rowObject, value);
			break;
		case I4diacNatTableUtil.TYPE:
			if (!getLibrary().getFbTypes().containsKey(value)) {
				return;
			}
			cmd = new ChangeFbTypeCommand(rowObject, getLibrary().getFbTypes().get(value));
			break;
		case I4diacNatTableUtil.COMMENT:
			cmd = new ChangeCommentCommand(rowObject, value);
			break;
		default:
			return;
		}

		getSection().executeCommand(cmd);
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	public void setTypeLib(final TypeLibrary dataTypeLib) {
		if (getLibrary() == null) {
			setLibrary(dataTypeLib);
		}
	}
}