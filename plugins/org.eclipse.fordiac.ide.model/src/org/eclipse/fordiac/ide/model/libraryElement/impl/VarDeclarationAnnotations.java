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
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.VarInOutHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public class VarDeclarationAnnotations {

	public static boolean validateMultipleInputConnections(final VarDeclaration varDeclaration,
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

	public static boolean validateNoValueForGenericTypeVariable(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (GenericTypes.isAnyType(varDeclaration.getType()) && hasValue(varDeclaration)
				&& varDeclaration.getFBNetworkElement() == null) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.WARNING, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.VAR_DECLARATION__VALIDATE_NO_VALUE_FOR_GENERIC_TYPE_VARIABLE,
						Messages.VarDeclarationAnnotations_ShouldNotSpecifyValueForGenericVariableInType,
						FordiacMarkerHelper.getDiagnosticData(varDeclaration)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateValueForGenericInstanceVariable(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.isIsInput() && varDeclaration.getInputConnections().isEmpty()
				&& GenericTypes.isAnyType(varDeclaration.getType()) && !hasValue(varDeclaration)
				&& varDeclaration.getFBNetworkElement() != null && varDeclaration.getFBNetworkElement()
						.eContainingFeature() != LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.WARNING, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.VAR_DECLARATION__VALIDATE_VALUE_FOR_GENERIC_INSTANCE_VARIABLE,
						Messages.VarDeclarationAnnotations_ShouldSpecifyValueForGenericVariableInInstance,
						FordiacMarkerHelper.getDiagnosticData(varDeclaration)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateVarInOutSourceTypeIsWellDefined(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.isInOutVar() && varDeclaration.isIsInput() && varDeclaration.getFBNetworkElement() != null
				&& varDeclaration.getInputConnections().isEmpty()
				&& ((varDeclaration.isArray() && varDeclaration.getArraySize().getValue().contains("*")) //$NON-NLS-1$
						|| GenericTypes.isAnyType(varDeclaration.getType()))) {
			// We have a VarInOut input on a FB instance which is an array of variable
			// length or an ANY variable, so its not well defined
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

	public static boolean validateVarInOutIsWithed(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.isInOutVar() && varDeclaration.getWiths().isEmpty()
				&& varDeclaration.getFBNetworkElement() == null && !isSubappTypeInterface(varDeclaration)) {
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

	public static boolean validateVarInOutSubappInterface(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.isInOutVar() && varDeclaration.getFBNetworkElement() instanceof SubApp
				&& hasAnyOutputConnections(varDeclaration) && !hasAnyInputConnections(varDeclaration)) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.VAR_DECLARATION__VALIDATE_VAR_IN_OUT_SUBAPP_INTERFACE,
						MessageFormat.format(
								varDeclaration.isIsInput() ? Messages.VarDeclarationAnnotations_VarInOutLeftNotConnected
										: Messages.VarDeclarationAnnotations_VarInOutRightNotConnected,
								varDeclaration.getName()),
						FordiacMarkerHelper.getDiagnosticData(varDeclaration)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateVarInOutSubappNetwork(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.isInOutVar() && !varDeclaration.isIsInput()
				&& (isUntypedSubappInterface(varDeclaration) || isSubappTypeInterface(varDeclaration))
				&& !varDeclaration.getInOutVarOpposite().getOutputConnections().isEmpty()) {
			final VarDeclaration source = VarInOutHelper.getDefiningVarInOutDeclaration(varDeclaration);
			if (source != null && source.eContainer() != varDeclaration.eContainer()) {
				if (diagnostics != null) {
					diagnostics
							.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
									LibraryElementValidator.VAR_DECLARATION__VALIDATE_VAR_IN_OUT_SUBAPP_NETWORK,
									MessageFormat.format(Messages.VarDeclarationAnnotations_VarInOutSubappNetwork,
											varDeclaration.getName()),
									FordiacMarkerHelper.getDiagnosticData(varDeclaration)));
				}
				return false;
			}
		}
		return true;
	}

	public static VarDeclaration getInOutVarOpposite(final VarDeclaration inOutVar) {
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

	static boolean hasValue(final VarDeclaration varDeclaration) {
		return varDeclaration.getValue() != null && varDeclaration.getValue().getValue() != null
				&& !varDeclaration.getValue().getValue().isEmpty();
	}

	static boolean isSubappTypeInterface(final VarDeclaration varDeclaration) {
		return varDeclaration.eContainer() instanceof final InterfaceList interfaceList
				&& interfaceList.eContainer() instanceof SubAppType;
	}

	static boolean isUntypedSubappInterface(final VarDeclaration varDeclaration) {
		return varDeclaration.getFBNetworkElement() instanceof final SubApp subapp && !subapp.isTyped();
	}

	static boolean hasAnyInputConnections(final VarDeclaration varDeclaration) {
		return varDeclaration != null && (!varDeclaration.getInputConnections().isEmpty()
				|| !getTypeVariable(varDeclaration).getInputConnections().isEmpty());
	}

	static boolean hasAnyOutputConnections(final VarDeclaration varDeclaration) {
		return varDeclaration != null && (!varDeclaration.getOutputConnections().isEmpty()
				|| !getTypeVariable(varDeclaration).getOutputConnections().isEmpty());
	}

	public static VarDeclaration getTypeVariable(final VarDeclaration varDeclaration) {
		if (varDeclaration != null) {
			final FBNetworkElement fbne = varDeclaration.getFBNetworkElement();
			if (fbne != null) {
				final FBType type = fbne.getType();
				if (type != null) {
					final VarDeclaration typeVariable = type.getInterfaceList().getVariable(varDeclaration.getName());
					return typeVariable.isInOutVar() && typeVariable.isIsInput() != varDeclaration.isIsInput()
							? typeVariable.getInOutVarOpposite()
							: typeVariable;
				}
			}
		}
		return varDeclaration;
	}

	private VarDeclarationAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
