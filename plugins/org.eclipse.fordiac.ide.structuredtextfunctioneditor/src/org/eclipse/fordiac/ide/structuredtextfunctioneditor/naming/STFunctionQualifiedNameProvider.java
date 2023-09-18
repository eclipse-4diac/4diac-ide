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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.naming;

import org.eclipse.fordiac.ide.structuredtextcore.naming.STCoreQualifiedNameProvider;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.resource.STFunctionResource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.naming.QualifiedName;

public class STFunctionQualifiedNameProvider extends STCoreQualifiedNameProvider {

	protected QualifiedName qualifiedName(final STFunctionSource source) {
		if (source.eResource() instanceof final STFunctionResource resource && resource.getLibraryElement() != null) {
			return getFullyQualifiedName(resource.getLibraryElement());
		}
		return null;
	}
}
