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
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class ChangeVarDeclarationTypeCommand extends ChangeDataTypeCommand {
	private static final Pattern ARRAY_TYPE_DECLARATION_PATTERN = Pattern.compile("ARRAY\\s*\\[(.*)\\]\\s*OF\\s+(.+)"); //$NON-NLS-1$

	private final String newArraySize;
	private String oldArraySize;

	protected ChangeVarDeclarationTypeCommand(final VarDeclaration varDeclaration, final DataType dataType,
			final String arraySize) {
		super(varDeclaration, dataType);
		newArraySize = arraySize;
	}

	@Override
	protected void doExecute() {
		super.doExecute();
		final VarDeclaration variable = getInterfaceElement();
		oldArraySize = variable.getArraySize();
		variable.setArraySize(newArraySize);
	}

	@Override
	protected void doUndo() {
		final VarDeclaration variable = getInterfaceElement();
		variable.setArraySize(oldArraySize);
		super.doUndo();
	}

	@Override
	protected void doRedo() {
		super.doRedo();
		final VarDeclaration variable = getInterfaceElement();
		variable.setArraySize(newArraySize);
	}

	@Override
	public VarDeclaration getInterfaceElement() {
		return (VarDeclaration) super.getInterfaceElement();
	}

	public static ChangeVarDeclarationTypeCommand forTypeDeclaration(final VarDeclaration varDeclaration,
			final String typeDeclaration) {
		final Matcher matcher = ARRAY_TYPE_DECLARATION_PATTERN.matcher(typeDeclaration.trim());
		final String arraySize;
		final String dataTypeName;
		if (matcher.matches()) {
			arraySize = matcher.group(1);
			dataTypeName = matcher.group(2);
		} else {
			arraySize = null;
			dataTypeName = typeDeclaration;
		}
		final DataType dataType = getTypeLibrary(varDeclaration).getDataTypeLibrary().getType(dataTypeName);
		return new ChangeVarDeclarationTypeCommand(varDeclaration, dataType, arraySize);
	}

	protected static TypeLibrary getTypeLibrary(final VarDeclaration varDeclaration) {
		if (EcoreUtil.getRootContainer(varDeclaration) instanceof final LibraryElement libraryElement) {
			return libraryElement.getTypeLibrary();
		}
		throw new IllegalArgumentException(
				"Could not determine type library for variable " + varDeclaration.getQualifiedName()); //$NON-NLS-1$
	}
}

