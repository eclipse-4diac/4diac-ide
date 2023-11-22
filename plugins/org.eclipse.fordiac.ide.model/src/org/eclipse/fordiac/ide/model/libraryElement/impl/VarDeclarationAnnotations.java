/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.jdt.annotation.NonNull;

public class VarDeclarationAnnotations {

	public static boolean validateMultipleInputConnections(@NonNull final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.isIsInput() && varDeclaration.getInputConnections().size() > 1) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.VAR_DECLARATION__VALIDATE_MULTIPLE_INPUT_CONNECTIONS,
						Messages.VarDeclarationAnnotations_MultipleInputConnections,
						FordiacMarkerHelper.getDiagnosticData(varDeclaration,
								LibraryElementPackage.Literals.IINTERFACE_ELEMENT__INPUT_CONNECTIONS)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateVarInOutSourceTypeIsWellDefined(@NonNull final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.eContainer().eContainer() instanceof FB && varDeclaration.isInOutVar()
				&& varDeclaration.isIsInput() && varDeclaration.getInputConnections().isEmpty()
				&& (varDeclaration.isArray() && varDeclaration.getArraySize().getValue().contains("*"))) { //$NON-NLS-1$
			// We have a VarInOut input on a FB instance which is an array of variable
			// length, so its not well defined
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.VAR_DECLARATION__VALIDATE_VAR_IN_OUT_SOURCE_TYPE_IS_WELL_DEFINED,
						MessageFormat.format(Messages.ConnectionValidator_VarInOutSourceNotWellDefined,
								varDeclaration.getFullTypeName()),
						FordiacMarkerHelper.getDiagnosticData(varDeclaration)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateVarInOutIsWithed(@NonNull final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.isInOutVar() && varDeclaration.getWiths().isEmpty()) {
			if (diagnostics != null) {
				final String typeName = varDeclaration.eContainer().eContainer() instanceof final FB fb ? fb.getName()
						: ((INamedElement) varDeclaration.eContainer().eContainer()).getName();
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.VAR_DECLARATION__VALIDATE_VAR_IN_OUT_IS_WITHED,
						MessageFormat.format(
								varDeclaration.isIsInput() ? Messages.ConnectionValidator_VarInOutInputSideHasNoWith
										: Messages.ConnectionValidator_VarInOutOutputSideHasNoWith,
								varDeclaration.getName(), typeName),
						FordiacMarkerHelper.getDiagnosticData(varDeclaration)));
			}
			return false;
		}
		return true;
	}

	public static VarDeclaration getInOutVarOpposite(@NonNull final VarDeclaration inOutVar) {
		final InterfaceList interfaceList = (InterfaceList) inOutVar.eContainer();
		if (inOutVar.isInOutVar()) {
			if (inOutVar.isIsInput()) {
				return interfaceList.getOutMappedInOutVars().get(interfaceList.getInOutVars().indexOf(inOutVar));
			}
			return interfaceList.getInOutVars().get(interfaceList.getOutMappedInOutVars().indexOf(inOutVar));
		}
		// if no inout var return the given var as backup
		return inOutVar;
	}

	private VarDeclarationAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
