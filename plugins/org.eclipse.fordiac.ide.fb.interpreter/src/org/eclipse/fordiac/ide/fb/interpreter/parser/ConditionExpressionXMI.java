/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResource;

public class ConditionExpressionXMI {

	public static final String EXPR_PREFIX = "expression_"; //$NON-NLS-1$
	public final ResourceSet reset;

	public ConditionExpressionXMI(ResourceSet reset) {
		this.reset = reset;
	}

	public Resource createXtextResourceFromConditionExp(String condExpression) {
		final var resource = reset.createResource(URI.createURI(EXPR_PREFIX + reset.getResources().size()
				+ "." + AbstractXMIParser.EXPR_URI_EXTENSION //$NON-NLS-1$
				));
		final var in = new ByteArrayInputStream(condExpression.getBytes());
		final var loadOptions = reset.getLoadOptions();
		loadOptions.put(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		try {
			resource.load(in, loadOptions);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return resource;
	}

}
