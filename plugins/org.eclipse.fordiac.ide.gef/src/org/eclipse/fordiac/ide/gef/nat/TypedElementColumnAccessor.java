/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.gef.nat;

import java.util.List;
import java.util.Objects;

import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;

public class TypedElementColumnAccessor<T extends ITypedElement>
		extends AbstractColumnAccessor<T, TypedElementTableColumn> {

	private static final String NULL_DEFAULT = ""; //$NON-NLS-1$

	protected TypedElementColumnAccessor(final CommandExecutor commandExecutor) {
		this(commandExecutor, TypedElementTableColumn.DEFAULT_COLUMNS);
	}

	protected TypedElementColumnAccessor(final CommandExecutor commandExecutor,
			final List<TypedElementTableColumn> columns) {
		super(commandExecutor, columns);
	}

	@Override
	public Object getDataValue(final T rowObject, final TypedElementTableColumn column) {
		return switch (column) {
		case NAME -> rowObject.getName();
		case TYPE -> rowObject.getFullTypeName();
		case COMMENT -> CommentHelper.getInstanceComment(rowObject);
		default -> throw new IllegalArgumentException("Unexpected value: " + column); //$NON-NLS-1$
		};
	}

	@Override
	public Command createCommand(final T rowObject, final TypedElementTableColumn column, final Object newValue) {
		return switch (column) {
		case NAME -> ChangeNameCommand.forName(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		case TYPE -> throw new UnsupportedOperationException("Cannot change type"); //$NON-NLS-1$
		case COMMENT -> new ChangeCommentCommand(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		default -> throw new IllegalArgumentException("Unexpected value: " + column); //$NON-NLS-1$
		};
	}
}
