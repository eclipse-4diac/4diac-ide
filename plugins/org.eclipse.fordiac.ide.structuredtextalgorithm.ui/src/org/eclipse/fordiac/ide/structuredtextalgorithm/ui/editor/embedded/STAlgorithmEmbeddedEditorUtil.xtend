/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded

import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorFactory

final class STAlgorithmEmbeddedEditorUtil {
	static final URI SYNTHETIC_URI = URI.createURI("__synthetic.stalg")
	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(SYNTHETIC_URI)

	private new() {
	}

	def static EmbeddedEditorFactory getEmbeddedEditorFactory() {
		return SERVICE_PROVIDER.get(EmbeddedEditorFactory)
	}
}
