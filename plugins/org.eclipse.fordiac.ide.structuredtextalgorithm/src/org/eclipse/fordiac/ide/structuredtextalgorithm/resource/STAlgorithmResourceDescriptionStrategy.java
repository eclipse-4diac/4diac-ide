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
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethodBody;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.util.IAcceptor;

public class STAlgorithmResourceDescriptionStrategy extends STCoreResourceDescriptionStrategy {

	@Override
	public boolean createEObjectDescriptions(final EObject eObject, final IAcceptor<IEObjectDescription> acceptor) {
		return switch (eObject) {
		// do not export library element from resource with query:
		case final LibraryElement libraryElement when libraryElement.eResource().getURI().hasQuery() -> false;
		// do not export algorithms or methods:
		case final STAlgorithm unused -> false;
		case final STMethod unused -> false;
		case final STAlgorithmBody unused -> false;
		case final STMethodBody unused -> false;
		// do not export anything inside an FB network,
		// except for the subapp hierarchy (needed for plant hierarchy):
		// - untyped subapps (incl. contents)
		// - typed subapps (excl. contents)
		// - no other FBNE inside FBNs
		// - no attributes of subapp instances
		// - no interface of subapp instances
		// - no connections
		case final UntypedSubApp untypedSubApp -> super.createEObjectDescriptions(untypedSubApp, acceptor);
		case final TypedSubApp typedSubApp -> {
			super.createEObjectDescriptions(typedSubApp, acceptor); // export subapp
			yield false; // exclude contents
		}
		case final FBNetworkElement fbne when fbne.eContainer() instanceof FBNetwork -> false;
		case final Attribute attribute when attribute.eContainer() instanceof SubApp -> false;
		case final InterfaceList interfaceList when interfaceList.eContainer() instanceof SubApp -> false;
		case final Connection unused -> false;
		// export by default:
		default -> super.createEObjectDescriptions(eObject, acceptor);
		};
	}
}
