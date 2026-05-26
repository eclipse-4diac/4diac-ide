/*******************************************************************************
 * Copyright (c) 2008 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                    Johannes Kepler University,
 *                    Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *   			   - initial API and implementation and/or initial documentation
 *   Monika Wenger - extracted the model helper methods into this annotations
 *                   class
 *                 - introduced IEC 61499 attribute support into the model
 *   Alois Zoitl   - reworked model helper functions for better mapping and
 *                   sub-app support
 *   			   - extracted from annotations class and extended with group
 *   			     functions
 *   Martin Erich Jobst - add unused validation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.validation.ValidationPreferences;

public final class BlockFBNetworkElementAnnotations {

	public static void checkConnections(final BlockFBNetworkElement fbne) {
		fbne.getInterface().getAllInterfaceElements().forEach(element -> {
			element.getInputConnections().forEach(Connection::checkIfConnectionBroken);
			element.getOutputConnections().forEach(Connection::checkIfConnectionBroken);
		});
	}

	public static boolean validateUnused(final BlockFBNetworkElement fbne, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (fbne.getFbNetwork() == null || (fbne.isMapped() && fbne.getMapping().getTo() == fbne)) {
			return true; // avoid internal FBs or duplicate on mapped instance
		}
		if (isUnused(fbne) && (!fbne.isMapped() || isUnused(fbne.getOpposite()))) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(
						ValidationPreferences.getDiagnosticSeverity(ValidationPreferences.UNUSED_INSTANCE,
								Diagnostic.WARNING, fbne),
						LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.BLOCK_FB_NETWORK_ELEMENT__VALIDATE_UNUSED,
						MessageFormat.format(Messages.BlockFBNetworkElementAnnotations_Unused, fbne.getQualifiedName()),
						FordiacMarkerHelper.getDiagnosticData(fbne,
								LibraryElementPackage.Literals.INAMED_ELEMENT__NAME)));
			}
			return false;
		}
		return true;
	}

	private static boolean isUnused(final BlockFBNetworkElement fbne) {
		return isUnused(fbne.getInterface().getEventInputs());
	}

	private static boolean isUnused(final Collection<? extends Event> events) {
		return !events.isEmpty() && events.stream().allMatch(BlockFBNetworkElementAnnotations::isUnused);
	}

	private static boolean isUnused(final Event event) {
		return event.getInputConnections().isEmpty() && !EventTypeLibrary.EINIT.equalsIgnoreCase(event.getTypeName());
	}

	private BlockFBNetworkElementAnnotations() {
		throw new UnsupportedOperationException();
	}
}
