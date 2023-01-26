/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class DefaultParserXMI extends AbstractXMIParser {

	private Resource createBasicResource(final FBType fbType){
		final var fbResource = resourceSet.createResource(computeUnusedUri(resourceSet,FB_URI_EXTENSION));
		fbResource.getContents().add(fbType);
		fbType.getInterfaceList().getSockets().forEach(adp -> createAdapterResource(resourceSet, adp));
		fbType.getInterfaceList().getPlugs().forEach(adp -> createAdapterResource(resourceSet, adp));
		fbType.getInterfaceList().getInputVars().forEach( v -> createStructResource(resourceSet, v));
		fbType.getInterfaceList().getOutputVars().forEach(v -> createStructResource(resourceSet, v));
		return fbResource;
	}

	@Override
	public Resource createFBResource(final FBType fbType) {
		// create resource for function block and add copy
		return createBasicResource(fbType);
	}

	@Override
	public Resource createFBResource(final BasicFBType basicFbType) {
		final var res = createBasicResource(basicFbType);
		basicFbType.getInternalVars().forEach(v -> createStructResource(resourceSet, v));
		basicFbType.getInternalConstVars().forEach(v -> createStructResource(resourceSet, v));
		return res;
	}
}
