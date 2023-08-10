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

import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.Messages;
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

	private ConnectionAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}

}
