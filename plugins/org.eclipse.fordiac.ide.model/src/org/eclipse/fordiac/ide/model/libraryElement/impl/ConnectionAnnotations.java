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
import java.util.SequencedSet;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.VarInOutHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;

public class ConnectionAnnotations {

	public static boolean validateMissingSource(final Connection connection, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (connection.getSourceElement() instanceof final ErrorMarkerFBNElement element) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_MISSING_SOURCE,
						MessageFormat.format(Messages.ConnectionAnnotations_SourceElementMissing, element.getName()),
						FordiacMarkerHelper.getDiagnosticData(connection,
								LibraryElementPackage.Literals.CONNECTION__SOURCE, element.getQualifiedName())));
			}
			return false;
		}
		return true;
	}

	public static boolean validateMissingSourceEndpoint(final Connection connection, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (connection.getSource() instanceof final ErrorMarkerInterface endpoint) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_MISSING_SOURCE_ENDPOINT,
						MessageFormat.format(Messages.ConnectionAnnotations_SourceEndpointMissing, endpoint.getName()),
						FordiacMarkerHelper.getDiagnosticData(connection,
								LibraryElementPackage.Literals.CONNECTION__SOURCE, endpoint.getQualifiedName())));
			}
			return false;
		}
		return true;
	}

	public static boolean validateMissingDestination(final Connection connection, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (connection.getDestinationElement() instanceof final ErrorMarkerFBNElement element) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_MISSING_DESTINATION,
						MessageFormat.format(Messages.ConnectionAnnotations_DestinationElementMissing,
								element.getName()),
						FordiacMarkerHelper.getDiagnosticData(connection,
								LibraryElementPackage.Literals.CONNECTION__DESTINATION, element.getQualifiedName())));
			}
			return false;
		}
		return true;
	}

	public static boolean validateMissingDestinationEndpoint(final Connection connection,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (connection.getDestination() instanceof final ErrorMarkerInterface endpoint) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_MISSING_DESTINATION_ENDPOINT,
						MessageFormat.format(Messages.ConnectionAnnotations_DestinationEndpointMissing,
								endpoint.getName()),
						FordiacMarkerHelper.getDiagnosticData(connection,
								LibraryElementPackage.Literals.CONNECTION__DESTINATION, endpoint.getQualifiedName())));
			}
			return false;
		}
		return true;
	}

	public static boolean validateDuplicate(final Connection connection, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (isDuplicateConnection(connection)) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_DUPLICATE,
						MessageFormat.format(Messages.ConnectionAnnotations_DuplicateConnection,
								connection.getSource().getQualifiedName(),
								connection.getDestination().getQualifiedName()),
						FordiacMarkerHelper.getDiagnosticData(connection)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateTypeMismatch(final Connection connection, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		final IInterfaceElement src = connection.getSource();
		final IInterfaceElement dest = connection.getDestination();
		if (src == null || dest == null) {
			return true; // ignore incomplete connections
		}
		// basic type check
		if (!LinkConstraints.typeCheck(src, dest)) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_TYPE_MISMATCH,
						MessageFormat.format(Messages.ConnectionAnnotations_TypeMismatch, src.getQualifiedName(),
								src.getFullTypeName(), dest.getQualifiedName(), dest.getFullTypeName()),
						FordiacMarkerHelper.getDiagnosticData(connection)));
			}
			return false;
		}
		// extended type check for InOuts
		if (src instanceof final VarDeclaration srcVar && srcVar.isInOutVar()
				&& dest instanceof final VarDeclaration destVar && !typeCheckInOut(srcVar, destVar)) {
			if (diagnostics != null) {
				final SequencedSet<VarDeclaration> path = VarInOutHelper.getDefiningVarInOutDeclarationPath(destVar);
				final VarDeclaration definingVar = path.removeLast();
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_TYPE_MISMATCH,
						MessageFormat.format(Messages.ConnectionAnnotations_TypeMismatchInOut,
								definingVar.getQualifiedName(), definingVar.getFullTypeName(), dest.getQualifiedName(),
								dest.getFullTypeName(), path.isEmpty() ? "" : //$NON-NLS-1$
										path.stream()
												.map(v -> MessageFormat.format(
														Messages.ConnectionAnnotations_TypeMismatchInOutIntermediate,
														v.getQualifiedName(), v.getFullTypeName()))
												.collect(Collectors.joining(
														Messages.ConnectionAnnotations_TypeMismatchInOutSeparator,
														Messages.ConnectionAnnotations_TypeMismatchInOutVia, ""))), //$NON-NLS-1$
						FordiacMarkerHelper.getDiagnosticData(connection)));
			}
			return false;
		}
		// check generic endpoints
		if (GenericTypes.isAnyType(src.getType()) && GenericTypes.isAnyType(connection.getDestination().getType())
				&& !isContainerPin(src.eContainer().eContainer(), dest.eContainer().eContainer())) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_TYPE_MISMATCH,
						MessageFormat.format(Messages.ConnectionAnnotations_GenericEndpoints, src.getQualifiedName(),
								src.getFullTypeName(), dest.getQualifiedName(), dest.getFullTypeName()),
						FordiacMarkerHelper.getDiagnosticData(connection)));
			}
			return false;
		}
		return true;
	}

	private static boolean typeCheckInOut(final VarDeclaration source, final VarDeclaration destination) {
		// check defining source for InOut sources
		// get defining declaration (determines the _actual_ type)
		final VarDeclaration definingVar = VarInOutHelper.getDefiningVarInOutDeclaration(destination);
		if (definingVar == null) { // cannot determine defining source (we have a loop)
			return true; // ignore
		}
		// get the defining and destination types
		final DataType definingType = LinkConstraints.getFullDataType(definingVar);
		final DataType destinationType = LinkConstraints.getFullDataType(destination);
		// check if destination is also an InOut variable
		if (destination instanceof final VarDeclaration destVar && destVar.isInOutVar()) {
			// connections between InOut variables must be mutually assignable
			// special exception for generic destination variables, which adapt to the
			// source
			// note that the defining source can never be generic (not well-defined)
			return destinationType.isAssignableFrom(definingType)
					&& (GenericTypes.isAnyType(destinationType) || definingType.isAssignableFrom(destinationType));
		}
		// destination is a simple input (simple type check)
		return destinationType.isAssignableFrom(definingType);
	}

	private static boolean isContainerPin(final EObject srcParent, final EObject destParent) {
		if ((srcParent instanceof FBType) || (destParent instanceof FBType)) {
			// one of the pins is on the type so we are fine
			return true;
		}

		if ((srcParent instanceof final FBNetworkElement srcElem
				&& destParent instanceof final FBNetworkElement destElem)
				&& (srcParent instanceof SubApp || srcParent instanceof CFBInstance || destParent instanceof SubApp
						|| destParent instanceof CFBInstance)) {
			// if at least one of the parents is an untyped subapp the connection will need
			// to cross the interface, i.e., the two FBNetworkElement must not be in the
			// same network
			return (srcElem.getFbNetwork() != destElem.getFbNetwork());
		}

		return false;
	}

	public static boolean validateMappedVarInOutsDoNotCrossResourceBoundaries(final Connection connection,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		final var source = connection.getSource();
		final var destination = connection.getDestination();
		if (isInOutVarOfMappedFB(source) && isInOutVarOfMappedFB(destination)) {
			final var sourceResource = source.getFBNetworkElement().getResource();
			final var destinationResource = destination.getFBNetworkElement().getResource();
			if (sourceResource != destinationResource) { // Error, VarInOut crosses boundaries
				if (diagnostics != null) {
					diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
							LibraryElementValidator.CONNECTION__VALIDATE_MAPPED_VAR_IN_OUTS_DO_NOT_CROSS_RESOURCE_BOUNDARIES,
							Messages.ConnectionValidator_VarInOutConnectionCrossesResourceBoundaries,
							FordiacMarkerHelper.getDiagnosticData(connection)));
				}
				return false;
			}
		}
		return true;
	}

	private static boolean isInOutVarOfMappedFB(final IInterfaceElement ie) {
		return ie instanceof final VarDeclaration varDecl && varDecl.isInOutVar() && ie.getFBNetworkElement() != null
				&& ie.getFBNetworkElement().isMapped();
	}

	public static boolean validateVarInOutArraySizesAreCompatible(final Connection connection,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (connection.getDestination() instanceof final VarDeclaration connectionDestinationVar
				&& connectionDestinationVar.isInOutVar()) { // If the destination is a VAR_IN_OUT the source is also one
			final VarDeclaration definingVarDeclaration = VarInOutHelper
					.getDefiningVarInOutDeclaration(connectionDestinationVar);
			if ((definingVarDeclaration != null && definingVarDeclaration.isArray()
					&& connectionDestinationVar.isArray())
					&& !definingVarDeclaration.getArraySize().getValue()
							.equals(connectionDestinationVar.getArraySize().getValue())
					&& !connectionDestinationVar.getArraySize().getValue().contains("*")) { //$NON-NLS-1$
				if (diagnostics != null) {
					diagnostics.add(new BasicDiagnostic(Diagnostic.WARNING, LibraryElementValidator.DIAGNOSTIC_SOURCE,
							LibraryElementValidator.CONNECTION__VALIDATE_VAR_IN_OUT_ARRAY_SIZES_ARE_COMPATIBLE,
							MessageFormat.format(Messages.ConnectionValidator_VarInOutArraySizeMismatch,
									connectionDestinationVar.getFullTypeName(),
									definingVarDeclaration.getFullTypeName()),
							FordiacMarkerHelper.getDiagnosticData(connectionDestinationVar)));
				}
				return false;
			}
		}
		return true;
	}

	public static boolean validateVarInOutStringLengthsMatch(final Connection connection,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (connection.getDestination() instanceof final VarDeclaration connectionDestinationVar
				&& connectionDestinationVar.isInOutVar()) { // If the destination is a VAR_IN_OUT the source is also one
			final VarDeclaration definingVarDeclaration = VarInOutHelper
					.getDefiningVarInOutDeclaration(connectionDestinationVar);
			if (definingVarDeclaration != null
					&& definingVarDeclaration.getType() instanceof final AnyStringType sourceType
					&& connectionDestinationVar.getType() instanceof final AnyStringType destinationType
					&& sourceType.getMaxLength() != destinationType.getMaxLength()) {
				if (diagnostics != null) {
					diagnostics.add(new BasicDiagnostic(Diagnostic.WARNING, LibraryElementValidator.DIAGNOSTIC_SOURCE,
							LibraryElementValidator.CONNECTION__VALIDATE_VAR_IN_OUT_STRING_LENGTHS_MATCH,
							MessageFormat.format(Messages.ConnectionValidator_VarInOutStringSizeMismatch,
									Integer.valueOf(destinationType.getMaxLength()),
									Integer.valueOf(sourceType.getMaxLength())),
							FordiacMarkerHelper.getDiagnosticData(connectionDestinationVar)));
				}
				return false;
			}
		}
		return true;
	}

	public static boolean validateVarInOutsAreNotConnectedToOuts(final Connection connection,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (connection.getDestination() instanceof final VarDeclaration destinationVar && destinationVar.isInOutVar()
				&& connection.getSource() instanceof final VarDeclaration sourceVar && !sourceVar.isInOutVar()) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_VAR_IN_OUTS_ARE_NOT_CONNECTED_TO_OUTS,
						Messages.ConnectionValidator_OutputsCannotBeConnectedToVarInOuts,
						FordiacMarkerHelper.getDiagnosticData(connection)));
			}
			return false;

		}
		return true;
	}

	public static boolean validateVarInOutConnectionsFormsNoLoop(final Connection connection,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (connection.getDestination() instanceof final VarDeclaration destinationVar && destinationVar.isInOutVar()
				&& VarInOutHelper.getDefiningVarInOutDeclaration(destinationVar) == null) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_VAR_IN_OUT_CONNECTIONS_FORMS_NO_LOOP,
						Messages.ConnectionValidator_VarInOutConnectionsIsALoop,
						FordiacMarkerHelper.getDiagnosticData(connection)));
			}
			return false;
		}
		return true;
	}

	private static boolean isDuplicateConnection(final Connection connection) {
		return connection.getSource().getOutputConnections().stream().anyMatch(
				candidate -> candidate != connection && candidate.getDestination() == connection.getDestination());
	}

	static String getConnectionQualifiedName(final Connection connection) {
		final String sourceName = connection.getSource().getQualifiedName();
		final String destinationName = connection.getDestination().getQualifiedName();
		final INamedElement namedContainer = NamedElementAnnotations.getNamedContainer(connection);
		if (namedContainer != null) {
			final String containerName = namedContainer.getQualifiedName();
			final String sourceRelativeName = NamedElementAnnotations.removeQualifiedNamePrefix(sourceName,
					containerName);
			final String destinationRelativeName = NamedElementAnnotations.removeQualifiedNamePrefix(destinationName,
					containerName);
			return containerName + NamedElementAnnotations.QUALIFIED_NAME_DELIMITER + "(" + sourceRelativeName + "->" //$NON-NLS-1$ //$NON-NLS-2$
					+ destinationRelativeName + ")"; //$NON-NLS-1$
		}
		return "(" + sourceName + "->" + destinationName + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	private ConnectionAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
