/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *               2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *     - add clauses for handling error markers
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.edit.helper;

import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public final class InitialValueHelper {

	private InitialValueHelper() {
		throw new UnsupportedOperationException("Helper class InitialValueHelper should not be instantiated!"); //$NON-NLS-1$
	}

	public static String getInitialOrDefaultValue(final Object element) {
		if (element instanceof final VarDeclaration varDec) {
			if (hasInitalValue(element)) {
				return varDec.getValue().getValue();
			}

			final String typeInitialValue = getTypeInitialValue(varDec);
			if (!typeInitialValue.isBlank()) {
				return typeInitialValue;
			}

			if (hasDataTypeInitialValue(varDec)) {
				return getMemberVarValue(varDec);
			}
			return getDefaultValue(element);
		}
		if ((element instanceof final ErrorMarkerInterface marker) && hasInitalValue(element)) {
			return marker.getValue().getValue();
		}
		if (element instanceof final Attribute attr) {
			if (hasInitalValue(element)) {
				return attr.getValue();
			}
			return getDefaultValue(element);
		}
		// no default value for error markers
		return ""; //$NON-NLS-1$
	}

	private static String getTypeInitialValue(final VarDeclaration varDec) {
		final FBNetworkElement fbnEl = varDec.getFBNetworkElement();
		if (fbnEl != null && fbnEl.getType() != null) {
			// we have a typed parent
			final IInterfaceElement typeVar = fbnEl.getType().getInterfaceList().getInterfaceElement(varDec.getName());
			if (typeVar instanceof final VarDeclaration typeVarDecl && typeVarDecl.getValue() != null) {
				return typeVarDecl.getValue().getValue();
			}
		}
		return ""; //$NON-NLS-1$
	}

	private static boolean hasDataTypeInitialValue(final VarDeclaration varDec) {
		if (varDec.getFBNetworkElement() instanceof StructManipulator) {
			return !getMemberVarValue(varDec).isBlank();
		}
		return false;
	}

	private static String getMemberVarValue(final VarDeclaration v) {
		if ((v != null) && (v.getName() != null) && (v.getFBNetworkElement() instanceof final StructManipulator muxer)
				&& (muxer.getDataType() instanceof final StructuredType structType)) {
			final VarDeclaration matchingMember = structType.getMemberVariables().stream()
					.filter(member -> v.getName().equals(member.getName())).findFirst().orElse(null);
			if ((matchingMember != null) && (matchingMember.getValue() != null)) {
				return matchingMember.getValue().getValue();
			}
		}

		return ""; //$NON-NLS-1$
	}

	public static String getDefaultValue(final Object element) {
		if ((element instanceof final VarDeclaration varDec)
				&& ((varDec.getType() instanceof AnyType) && !IecTypes.GenericTypes.isAnyType(varDec.getType()))) {
			try {
				return VariableOperations.newVariable(varDec).toString();
			} catch (final Exception exc) {
				// fall though (NO LOGGING NECESSARY!!!)
			}
		}
		if ((element instanceof final Attribute attr)
				&& ((attr.getType() instanceof AnyType) && !IecTypes.GenericTypes.isAnyType(attr.getType()))) {
			try {
				return VariableOperations.newVariable(attr).toString();
			} catch (final Exception exc) {
				// fall though (NO LOGGING NECESSARY!!!)
			}
		}
		return ""; //$NON-NLS-1$
	}

	public static Color getForegroundColor(final Object element) {
		if ((element instanceof VarDeclaration || element instanceof Attribute) && !hasInitalValue(element)) {

			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
		}
		return null;
	}

	public static boolean hasInitalValue(final Object element) {
		if (element instanceof final VarDeclaration varDec) {
			return (varDec.getValue() != null) && (varDec.getValue().getValue() != null)
					&& !varDec.getValue().getValue().isEmpty();
		}
		if (element instanceof final ErrorMarkerInterface marker) {
			return (marker.getValue() != null) && (marker.getValue().getValue() != null)
					&& !marker.getValue().getValue().isEmpty();
		}
		if (element instanceof final Attribute attr) {
			return (attr.getValue() != null && !attr.getValue().equals("")); //$NON-NLS-1$
		}
		return false;
	}

}
