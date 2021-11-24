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
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.xtext.resource.XtextResource;

public class AlgorithmStXMI {

	public static final String ALGORITHM_PREFIX = "algorithm_"; //$NON-NLS-1$
	public final ResourceSet reset;

	public AlgorithmStXMI(final ResourceSet reset) {
		this.reset = reset;
	}

	public Resource createXtextResourceFromAlgorithmSt(final String algorithmSt) {
		final var resource = reset.createResource(URI.createURI(ALGORITHM_PREFIX + reset.getResources().size()
				+ "." + AbstractXMIParser.ST_URI_EXTENSION //$NON-NLS-1$
				));
		final var inputStream = new ByteArrayInputStream(algorithmSt.getBytes());
		final var loadOptions = reset.getLoadOptions();
		loadOptions.put(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		try {
			resource.load(inputStream, loadOptions);
		} catch (final IOException e) {
			FordiacLogHelper.logError(e.getMessage());
		}
		return resource;
	}
}
