/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;

public class ChangeDirectlyDerivedBaseTypeCommand extends AbstractChangeDirectlyDerivedTypeCommand {
	private final DataType dataType;
	private DataType oldDataType;

	private ChangeDirectlyDerivedBaseTypeCommand(final DirectlyDerivedType directlyDerivedType,
			final DataType dataType) {
		super(directlyDerivedType);
		this.dataType = dataType;
	}

	public static ChangeDirectlyDerivedBaseTypeCommand forTypeName(final DirectlyDerivedType directlyDerivedType,
			final String typeName) {
		final DataType dataType = IecTypes.ElementaryTypes.getTypeByName(typeName);
		return ChangeDirectlyDerivedBaseTypeCommand.forDataType(directlyDerivedType, dataType);
	}

	public static ChangeDirectlyDerivedBaseTypeCommand forDataType(final DirectlyDerivedType directlyDerivedType,
			final DataType dataType) {
		return new ChangeDirectlyDerivedBaseTypeCommand(directlyDerivedType,
				Objects.requireNonNullElse(dataType, ElementaryTypes.STRING));
	}

	@Override
	public void doExecute() {
		oldDataType = getDirectlyDerivedType().getBaseType();
		getDirectlyDerivedType().setBaseType(dataType);
	}

	@Override
	public void doUndo() {
		getDirectlyDerivedType().setBaseType(oldDataType);
	}

	@Override
	public void doRedo() {
		getDirectlyDerivedType().setBaseType(dataType);
	}
}
