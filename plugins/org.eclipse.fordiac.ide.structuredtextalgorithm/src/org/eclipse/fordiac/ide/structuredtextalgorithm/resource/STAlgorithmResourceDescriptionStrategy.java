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
package org.eclipse.fordiac.ide.structuredtextalgorithm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethodBody;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.util.IAcceptor;

public class STAlgorithmResourceDescriptionStrategy extends STCoreResourceDescriptionStrategy {

	@Override
	public boolean createEObjectDescriptions(final EObject eObject, final IAcceptor<IEObjectDescription> acceptor) {
		if (eObject instanceof LibraryElement && eObject.eResource().getURI().hasQuery()) {
			return false; // do not export library element from resource with query
		}
		if (eObject instanceof STAlgorithm || eObject instanceof STMethod || eObject instanceof STAlgorithmBody
				|| eObject instanceof STMethodBody || eObject instanceof FBNetwork) {
			return false; // do not export anything inside of an algorithm, method, or FB network
		}
		return super.createEObjectDescriptions(eObject, acceptor);
	}
}
