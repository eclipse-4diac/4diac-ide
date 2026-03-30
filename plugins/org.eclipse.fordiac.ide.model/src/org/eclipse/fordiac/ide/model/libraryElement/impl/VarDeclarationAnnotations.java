/*******************************************************************************
 * Copyright (c) 2023, 2026 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst      - add validations
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.annotations.HiddenElementAnnotations;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.VarInOutHelper;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.fordiac.ide.model.validation.ValidationPreferences;

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

	public static boolean validateDestinationTypeMismatch(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (!varDeclaration.isInOutVar() && GenericTypes.isAnyType(varDeclaration.getType())
				&& varDeclaration.getOutputConnections().size() > 1) {
			final List<IInterfaceElement> destinations = varDeclaration.getOutputConnections().stream()
					.map(Connection::getDestination).toList();
			if (!allTypesMatch(destinations)) {
				if (diagnostics != null) {
					final String destinationsString = destinations.stream()
							.map(v -> MessageFormat.format(
									Messages.VarDeclarationAnnotations_DestinationTypeMismatchDestination,
									v.getQualifiedName(), v.getFullTypeName()))
							.collect(Collectors
									.joining(Messages.VarDeclarationAnnotations_DestinationTypeMismatchSeparator));
					final String destinationTypesString = destinations.stream()
							.map(v -> MessageFormat.format(
									Messages.VarDeclarationAnnotations_DestinationTypeMismatchDestinationType,
									v.getFullTypeName()))
							.collect(Collectors
									.joining(Messages.VarDeclarationAnnotations_DestinationTypeMismatchSeparator));
					diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
							LibraryElementValidator.VAR_DECLARATION__VALIDATE_DESTINATION_TYPE_MISMATCH,
							MessageFormat.format(Messages.VarDeclarationAnnotations_DestinationTypeMismatch,
									varDeclaration.getFullTypeName(), destinationTypesString,
									varDeclaration.getQualifiedName(), destinationsString),
							FordiacMarkerHelper.getDiagnosticData(varDeclaration,
									LibraryElementPackage.Literals.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS)));
				}
				return false;
			}
		}
		return true;
	}

	private static boolean allTypesMatch(final List<IInterfaceElement> elements) {
		final DataType firstType = LinkConstraints.getFullDataType(elements.getFirst());
		for (int i = 1; i < elements.size(); i++) {
			final DataType elementType = LinkConstraints.getFullDataType(elements.get(i));
			if (!elementType.isAssignableFrom(firstType) || !firstType.isAssignableFrom(elementType)) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateNoValueForGenericTypeVariable(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (GenericTypes.isAnyType(varDeclaration.getType()) && varDeclaration.hasValue()
				&& varDeclaration.getBlockFBNetworkElement() == null) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(
						ValidationPreferences.getDiagnosticSeverity(
								ValidationPreferences.NO_VALUE_FOR_GENERIC_TYPE_VARIABLE, Diagnostic.OK,
								varDeclaration),
						LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.VAR_DECLARATION__VALIDATE_NO_VALUE_FOR_GENERIC_TYPE_VARIABLE,
						Messages.VarDeclarationAnnotations_ShouldNotSpecifyValueForGenericVariableInType,
						FordiacMarkerHelper.getDiagnosticData(varDeclaration)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateIllegalVariableLengthArrayVariable(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.isArray() && !varDeclaration.isInOutVar()
				&& TypeDeclarationParser.isVariableArrayBounds(varDeclaration.getArraySize().getValue())
				&& !(varDeclaration.getFBType() instanceof FunctionFBType)
				&& !(varDeclaration.eContainer() instanceof Method)) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.VAR_DECLARATION__VALIDATE_ILLEGAL_VARIABLE_LENGTH_ARRAY_VARIABLE,
						Messages.VarDeclarationAnnotations_IllegalVariableLengthArray,
						FordiacMarkerHelper.getDiagnosticData(varDeclaration)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateNoValueForVariableLengthArrayVariable(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.isArray() && varDeclaration.hasValue()
				&& TypeDeclarationParser.isVariableArrayBounds(varDeclaration.getArraySize().getValue())) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.VAR_DECLARATION__VALIDATE_NO_VALUE_FOR_VARIABLE_LENGTH_ARRAY_VARIABLE,
						Messages.VarDeclarationAnnotations_MustNotSpecifyValueForVariableWithVariableArrayBounds,
						FordiacMarkerHelper.getDiagnosticData(varDeclaration)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateValueForGenericInstanceVariable(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.isIsInput() && varDeclaration.getInputConnections().isEmpty()
				&& GenericTypes.isAnyType(varDeclaration.getType()) && !varDeclaration.hasValue()) {
			final BlockFBNetworkElement blockFBNetworkElement = varDeclaration.getBlockFBNetworkElement();
			final VarDeclaration typeVarDeclaration = varDeclaration.findInTypeInterface();
			if (blockFBNetworkElement != null
					&& blockFBNetworkElement
							.eContainingFeature() != LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS
					&& (!blockFBNetworkElement.isMapped()
							|| blockFBNetworkElement.getMapping().getFrom() == blockFBNetworkElement)
					&& (typeVarDeclaration == null || !typeVarDeclaration.hasValue())) {
				if (diagnostics != null) {
					diagnostics.add(new BasicDiagnostic(
							ValidationPreferences.getDiagnosticSeverity(
									ValidationPreferences.VALUE_FOR_GENERIC_INSTANCE_VARIABLE, Diagnostic.WARNING,
									varDeclaration),
							LibraryElementValidator.DIAGNOSTIC_SOURCE,
							LibraryElementValidator.VAR_DECLARATION__VALIDATE_VALUE_FOR_GENERIC_INSTANCE_VARIABLE,
							Messages.VarDeclarationAnnotations_ShouldSpecifyValueForGenericVariableInInstance,
							FordiacMarkerHelper.getDiagnosticData(varDeclaration)));
				}
				return false;
			}
		}
		return true;
	}

	public static boolean validateValueOverriddenBySubAppInput(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.isIsInput() && varDeclaration.hasValue() && !varDeclaration.getInputConnections().isEmpty()
				&& varDeclaration.getInputConnections().getFirst().getSourceElement() instanceof SubApp
				&& varDeclaration.getInputConnections().getFirst().getSource().isIsInput()) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.WARNING, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.VAR_DECLARATION__VALIDATE_VALUE_OVERRIDDEN_BY_SUB_APP_INPUT,
						Messages.VarDeclarationAnnotations_ValueOverriddenBySubAppInput,
						FordiacMarkerHelper.getDiagnosticData(varDeclaration)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateVarInOutSourceTypeIsWellDefined(final VarDeclaration varDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (varDeclaration.isInOutVar() && varDeclaration.isIsInput()
				&& varDeclaration.getBlockFBNetworkElement() != null && varDeclaration.getInputConnections().isEmpty()
				&& ((varDeclaration.isArray()
						&& TypeDeclarationParser.isVariableArrayBounds(varDeclaration.getArraySize().getValue()))
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
				&& varDeclaration.getBlockFBNetworkElement() == null && !isSubappTypeInterface(varDeclaration)) {
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
		if (varDeclaration.isInOutVar() && varDeclaration.getBlockFBNetworkElement() instanceof SubApp
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

	static boolean isSubappTypeInterface(final VarDeclaration varDeclaration) {
		return varDeclaration.eContainer() instanceof final InterfaceList interfaceList
				&& interfaceList.eContainer() instanceof SubAppType;
	}

	static boolean isUntypedSubappInterface(final VarDeclaration varDeclaration) {
		return varDeclaration.getBlockFBNetworkElement() instanceof final SubApp subapp && !subapp.isTyped();
	}

	static boolean hasAnyInputConnections(final VarDeclaration varDeclaration) {
		if (varDeclaration == null) {
			return false;
		}
		if (!varDeclaration.getInputConnections().isEmpty()) {
			return true;
		}
		final VarDeclaration typeIE = findInTypeNetwork(varDeclaration);
		return typeIE != null && !typeIE.getInputConnections().isEmpty();
	}

	static boolean hasAnyOutputConnections(final VarDeclaration varDeclaration) {
		if (varDeclaration == null) {
			return false;
		}
		if (!varDeclaration.getOutputConnections().isEmpty()) {
			return true;
		}
		final VarDeclaration typeIE = findInTypeNetwork(varDeclaration);
		return typeIE != null && !typeIE.getOutputConnections().isEmpty();
	}

	static VarDeclaration findInTypeNetwork(final VarDeclaration element) {
		final BlockFBNetworkElement blockFbnEl = element.getBlockFBNetworkElement();
		if (blockFbnEl == null) {
			return null;
		}

		// we need the _full_ type here, since we need to check the input/output
		// connections of the inner network
		final FBType type = blockFbnEl.getType();
		if (type == null) {
			return null;
		}

		final VarDeclaration typeIE = type.getInterfaceList().getVariable(element.getName());
		if (typeIE.isInOutVar() && !element.isIsInput()) {
			// if the type pin is a varinout and the searched element is an output we need
			// to get the output opposite
			return typeIE.getInOutVarOpposite();
		}

		return typeIE;
	}

	static void setVisible(final VarDeclaration varDecl, final boolean visible) {
		if (varDecl.eContainer() instanceof VarDeclaration) {
			// member access pins treat the visible attribute in opposite to HiddenElements
			if (visible) {
				HiddenElementAnnotations.setVisible(varDecl, Boolean.toString(visible));
			} else {
				// if we are not visible the attribute can be removed
				varDecl.deleteAttribute(LibraryElementTags.ELEMENT_VISIBLE);
			}
			return;
		}

		if (varDecl.isInOutVar() && !varDecl.isIsInput()) {
			// varDecl is the mapped VarInOut-Output
			final VarDeclaration varInOutOriginal = varDecl.getInOutVarOpposite();
			if (visible) {
				varInOutOriginal.deleteAttribute(LibraryElementTags.ELEMENT_INOUTVISIBLEOUT);
			} else {
				varInOutOriginal.setAttribute(InternalAttributeDeclarations.INOUT_VISIBLE_OUT,
						Boolean.toString(visible), ""); //$NON-NLS-1$
			}
			return;
		}

		HiddenElementAnnotations.setVisible(varDecl, visible);
	}

	public static boolean isVisible(final VarDeclaration varDecl) {
		if (varDecl.eContainer() instanceof VarDeclaration) {
			// member access pins treat the visible attribute in opposite to HiddenElements
			final String visibleAttribute = varDecl.getAttributeValue(LibraryElementTags.ELEMENT_VISIBLE);
			return "true".equalsIgnoreCase(visibleAttribute); //$NON-NLS-1$
		}

		if (varDecl.isInOutVar() && !varDecl.isIsInput()) {
			// varDecl is the mapped VarInOut-Output
			final String visibleAttribute = varDecl.getInOutVarOpposite()
					.getAttributeValue(LibraryElementTags.ELEMENT_INOUTVISIBLEOUT);
			return !"false".equalsIgnoreCase(visibleAttribute); //$NON-NLS-1$
		}

		return HiddenElementAnnotations.isVisible(varDecl);
	}

	private VarDeclarationAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
