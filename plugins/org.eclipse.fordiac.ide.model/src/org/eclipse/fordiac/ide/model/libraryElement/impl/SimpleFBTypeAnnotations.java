/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.text.MessageFormat;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public class SimpleFBTypeAnnotations {

	static boolean validateEventUsage(final SimpleFBType fbType, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {

		final Set<Event> usedEventOutputs = fbType.getSimpleECStates().stream()
				.flatMap(state -> state.getSimpleECActions().stream()).map(SimpleECAction::getOutput)
				.filter(Objects::nonNull).collect(Collectors.toSet());
		return addUnusedEventOutputWarnings(fbType, usedEventOutputs, diagnostics,
				Messages.BaseFBTypeAnnotations_UnusedSimpleFBOutputEvent);
	}

	private static boolean addUnusedEventOutputWarnings(final SimpleFBType fbType, final Set<Event> usedEventOutputs,
			final DiagnosticChain diagnostics, final String messagePattern) {
		final Set<Event> unusedEventOutputs = new LinkedHashSet<>(fbType.getInterfaceList().getEventOutputs());
		unusedEventOutputs.removeAll(usedEventOutputs);
		return addEventWarnings(unusedEventOutputs, diagnostics, messagePattern);
	}

	private static boolean addEventWarnings(final Set<Event> events, final DiagnosticChain diagnostics,
			final String messagePattern) {
		if (diagnostics != null) {
			events.forEach(event -> diagnostics
					.add(new BasicDiagnostic(Diagnostic.WARNING, LibraryElementValidator.DIAGNOSTIC_SOURCE,
							LibraryElementValidator.SIMPLE_FB_TYPE__VALIDATE_EVENT_USAGE,
							MessageFormat.format(messagePattern, event.getName()), FordiacMarkerHelper
									.getDiagnosticData(event, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME))));
		}
		return events.isEmpty();
	}

}
