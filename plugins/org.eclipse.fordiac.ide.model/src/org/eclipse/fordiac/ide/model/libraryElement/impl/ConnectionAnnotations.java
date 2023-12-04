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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.jdt.annotation.NonNull;

public class ConnectionAnnotations {

	public static boolean validateMissingSource(@NonNull final Connection connection, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (connection.getSourceElement() instanceof final ErrorMarkerFBNElement element) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_MISSING_SOURCE,
						MessageFormat.format(Messages.ConnectionAnnotations_SourceElementMissing, element.getName()),
						FordiacMarkerHelper.getDiagnosticData(connection,
								LibraryElementPackage.Literals.CONNECTION__SOURCE)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateMissingSourceEndpoint(@NonNull final Connection connection,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (connection.getSource() instanceof final ErrorMarkerInterface endpoint) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_MISSING_SOURCE_ENDPOINT,
						MessageFormat.format(Messages.ConnectionAnnotations_SourceEndpointMissing, endpoint.getName()),
						FordiacMarkerHelper.getDiagnosticData(connection,
								LibraryElementPackage.Literals.CONNECTION__SOURCE)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateMissingDestination(@NonNull final Connection connection,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (connection.getDestinationElement() instanceof final ErrorMarkerFBNElement element) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_MISSING_DESTINATION,
						MessageFormat.format(Messages.ConnectionAnnotations_DestinationElementMissing,
								element.getName()),
						FordiacMarkerHelper.getDiagnosticData(connection,
								LibraryElementPackage.Literals.CONNECTION__DESTINATION)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateMissingDestinationEndpoint(@NonNull final Connection connection,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (connection.getDestination() instanceof final ErrorMarkerInterface endpoint) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_MISSING_DESTINATION_ENDPOINT,
						MessageFormat.format(Messages.ConnectionAnnotations_DestinationEndpointMissing,
								endpoint.getName()),
						FordiacMarkerHelper.getDiagnosticData(connection,
								LibraryElementPackage.Literals.CONNECTION__DESTINATION)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateDuplicate(@NonNull final Connection connection, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (isDuplicateConnection(connection)) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_MISSING_DESTINATION_ENDPOINT,
						MessageFormat.format(Messages.ConnectionAnnotations_DuplicateConnection,
								connection.getSource().getQualifiedName(),
								connection.getDestination().getQualifiedName()),
						FordiacMarkerHelper.getDiagnosticData(connection)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateTypeMismatch(@NonNull final Connection connection, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (!LinkConstraints.typeCheck(connection.getSource(), connection.getDestination())) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.CONNECTION__VALIDATE_MISSING_DESTINATION_ENDPOINT,
						MessageFormat.format(Messages.ConnectionAnnotations_TypeMismatch,
								connection.getSource().getQualifiedName(), connection.getSource().getFullTypeName(),
								connection.getDestination().getQualifiedName(),
								connection.getDestination().getFullTypeName()),
						FordiacMarkerHelper.getDiagnosticData(connection)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateMappedVarInOutsDoNotCrossResourceBoundaries(@NonNull final Connection connection,
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

	public static boolean validateVarInOutArraySizesAreCompatible(@NonNull final Connection connection,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (connection.getDestination() instanceof final VarDeclaration connectionDestinationVar
				&& connectionDestinationVar.isInOutVar()) { // If the destination is a VAR_IN_OUT the source is also one
			final VarDeclaration definingVarDeclaration = getVarInOutSourceVarDeclaration(connection);
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

	public static boolean validateVarInOutStringLengthsMatch(@NonNull final Connection connection,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (connection.getDestination() instanceof final VarDeclaration connectionDestinationVar
				&& connectionDestinationVar.isInOutVar()) { // If the destination is a VAR_IN_OUT the source is also one
			final VarDeclaration definingVarDeclaration = getVarInOutSourceVarDeclaration(connection);
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
		if (hasConnectionAVarInOutLoop(connection)) {
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

	/**
	 * Get the VarDeclaration of the first FB in a chain of VAR_IN_OUT connections
	 *
	 * @param connection a VAR_IN_OUT connection to start from
	 * @return The source VarDeclaration
	 */
	private static VarDeclaration getVarInOutSourceVarDeclaration(@NonNull final Connection connection) {
		// Reverse search
		if (connection.getSource() instanceof final VarDeclaration sourceVar && sourceVar.isInOutVar()) {
			VarDeclaration outputVarInOut = sourceVar;
			VarDeclaration inputVarInOut = outputVarInOut.getInOutVarOpposite();
			final Set<VarDeclaration> visitedPins = new HashSet<>();
			visitedPins.add(outputVarInOut);
			while (!inputVarInOut.getInputConnections().isEmpty()) { // no fan in possible
				outputVarInOut = (VarDeclaration) inputVarInOut.getInputConnections().get(0).getSource();
				if (!visitedPins.add(outputVarInOut)) {
					return null; // We revisited an already visited pin
				}
				inputVarInOut = outputVarInOut.getInOutVarOpposite();
			}
			return inputVarInOut;
		}
		return null;
	}

	private static boolean hasConnectionAVarInOutLoop(@NonNull final Connection connection) {
		// Reverse search
		if (connection.getSource() instanceof final VarDeclaration sourceVar && sourceVar.isInOutVar()) {
			VarDeclaration outputVarInOut = sourceVar;
			VarDeclaration inputVarInOut = outputVarInOut.getInOutVarOpposite();
			final Set<VarDeclaration> visitedPins = new HashSet<>();
			visitedPins.add(outputVarInOut);
			while (!inputVarInOut.getInputConnections().isEmpty()) { // no fan in possible
				outputVarInOut = (VarDeclaration) inputVarInOut.getInputConnections().get(0).getSource();
				if (!visitedPins.add(outputVarInOut)) {
					return true; // We revisited an already visited pin
				}
				inputVarInOut = outputVarInOut.getInOutVarOpposite();
			}
		}
		return false;
	}

	private static boolean isDuplicateConnection(@NonNull final Connection connection) {
		return connection.getSource().getOutputConnections().stream().anyMatch(
				candidate -> candidate != connection && candidate.getDestination() == connection.getDestination());
	}

	private ConnectionAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
