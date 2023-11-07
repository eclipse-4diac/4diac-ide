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
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.jdt.annotation.NonNull;

public class ConnectionAnnotations {

	public static boolean validateMappedVarInOutsDoNotCrossResourceBoundaries(@NonNull final Connection connection,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		final var source = connection.getSource();
		final var destination = connection.getDestination();
		if (source instanceof final VarDeclaration sourceVar && sourceVar.isInOutVar()
				&& source.getFBNetworkElement().isMapped() && destination instanceof final VarDeclaration destinationVar
				&& destinationVar.isInOutVar() && destination.getFBNetworkElement().isMapped()) {
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

	private ConnectionAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
