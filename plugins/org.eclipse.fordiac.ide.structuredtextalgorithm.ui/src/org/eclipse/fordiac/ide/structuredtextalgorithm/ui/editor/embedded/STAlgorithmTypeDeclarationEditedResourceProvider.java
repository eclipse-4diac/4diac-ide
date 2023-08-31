/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.ParserRule;

public class STAlgorithmTypeDeclarationEditedResourceProvider extends STAlgorithmEditedResourceProvider {
	public STAlgorithmTypeDeclarationEditedResourceProvider(final INamedElement element) {
		super(EcoreUtil2.getContainerOfType(element, LibraryElement.class));
	}

	@Override
	protected ParserRule getEntryPoint() {
		return getParser().getGrammarAccess().getSTTypeDeclarationRule();
	}
}
