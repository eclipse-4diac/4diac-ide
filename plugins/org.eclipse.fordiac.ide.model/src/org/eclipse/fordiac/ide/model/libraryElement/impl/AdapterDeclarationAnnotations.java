/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst, Huzaifa Abdul Rehman
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 AND CC0-1.0
 *
 * AI Disclosure: The connection validation additions were generated with OpenAI Codex.
 * The AI-generated portions are made available under CC0-1.0.
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Huzaifa Abdul Rehman - adapter connection validation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public final class AdapterDeclarationAnnotations {

	public static boolean validateMultipleInputConnections(final AdapterDeclaration adapterDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (adapterDeclaration.getInputConnections().size() > 1) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.ADAPTER_DECLARATION__VALIDATE_MULTIPLE_INPUT_CONNECTIONS,
						Messages.AdapterDeclarationAnnotations_MultipleInputConnections,
						FordiacMarkerHelper.getDiagnosticData(adapterDeclaration,
								LibraryElementPackage.Literals.IINTERFACE_ELEMENT__INPUT_CONNECTIONS)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateMultipleOutputConnections(final AdapterDeclaration adapterDeclaration,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		if (adapterDeclaration.getOutputConnections().size() > 1) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.ADAPTER_DECLARATION__VALIDATE_MULTIPLE_OUTPUT_CONNECTIONS,
						Messages.AdapterDeclarationAnnotations_MultipleOutputConnections,
						FordiacMarkerHelper.getDiagnosticData(adapterDeclaration,
								LibraryElementPackage.Literals.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS)));
			}
			return false;
		}
		return true;
	}

	static Stream<INamedElement> findBySimpleName(final AdapterDeclaration root, final String name) {
		final AdapterFB adapterFB = root.getInterfaceOnlyAdapterFB();
		if (adapterFB != null) {
			return Stream.concat(NamedElementAnnotations.findBySimpleName(root, name)
					.filter(Predicate.not(AdapterFB.class::isInstance)), adapterFB.findBySimpleName(name));
		}
		return NamedElementAnnotations.findBySimpleName(root, name);
	}

	private AdapterDeclarationAnnotations() {
		throw new UnsupportedOperationException();
	}
}
