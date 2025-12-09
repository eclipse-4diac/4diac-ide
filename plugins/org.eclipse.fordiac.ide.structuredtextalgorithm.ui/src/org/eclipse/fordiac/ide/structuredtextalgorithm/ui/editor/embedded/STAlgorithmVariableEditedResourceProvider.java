/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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

import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STResource;
import org.eclipse.xtext.ParserRule;

public class STAlgorithmVariableEditedResourceProvider extends STAlgorithmEditedResourceProvider {
	private final Variable<?> element;

	public STAlgorithmVariableEditedResourceProvider(final Variable<?> element) {
		super(null);
		this.element = element;
	}

	@Override
	public STAlgorithmResource createResource() {
		final STAlgorithmResource resource = super.createResource();
		resource.getDefaultLoadOptions().put(STResource.OPTION_EXPECTED_TYPE, element.getType());
		return resource;
	}

	@Override
	protected ParserRule getEntryPoint() {
		return getParser().getGrammarAccess().getSTInitializerExpressionSourceRule();
	}
}
