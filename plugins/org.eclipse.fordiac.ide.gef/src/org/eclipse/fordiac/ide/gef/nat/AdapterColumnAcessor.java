/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.gef.nat;


import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAdapterTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;

public class AdapterColumnAcessor implements IColumnAccessor<AdapterDeclaration> {

	private final AbstractSection section;

	public AdapterColumnAcessor(final AbstractSection section) {
		this.section = section;
	}

	@Override
	public Object getDataValue(final AdapterDeclaration rowObject, final int columnIndex) {
		switch (columnIndex) {
		case I4diacNatTableUtil.NAME:
			return rowObject.getName();
		case I4diacNatTableUtil.TYPE:
			return rowObject.getType().getName();
		case I4diacNatTableUtil.COMMENT:
			return rowObject.getComment();
		default:
			return ""; //$NON-NLS-1$
		}
	}

	@Override
	public void setDataValue(final AdapterDeclaration rowObject, final int columnIndex, final Object newValue) {
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
			final AdapterTypeEntry adapterTypeEntry = section.getTypeLibrary().getAdapterTypeEntry(value);
			if (adapterTypeEntry == null) {
				return;
			}
			cmd = new ChangeAdapterTypeCommand(rowObject, adapterTypeEntry.getType());
			break;
		case I4diacNatTableUtil.COMMENT:
			cmd = new ChangeCommentCommand(rowObject, value);
			break;
		default:
			return;
		}

		section.executeCommand(cmd);

	}

	@Override
	public int getColumnCount() {
		return 3;
	}

}
