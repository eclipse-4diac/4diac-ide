/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.search.types;

import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class SearchChildrenProviderHelper {
	private SearchChildrenProviderHelper() {
	}

	public static Stream<? extends EObject> getFBTypeChildren(final FBType fbType) {
		Stream<? extends EObject> retval = getInterfaceListChildren(fbType.getInterfaceList());
		if (fbType instanceof final BaseFBType baseFBType) {
			retval = Stream.concat(retval, baseFBType.getInternalVars().stream());
			retval = Stream.concat(retval, baseFBType.getInternalConstVars().stream());
		}
		if (fbType instanceof final SubAppType subappType) {
			// we may have untyped subapps inside
			retval = Stream.concat(retval, subappType.getFBNetwork().getNetworkElements().stream());
			retval = Stream.concat(retval, subappType.getFBNetwork().getAdapterConnections().stream());
			retval = Stream.concat(retval, subappType.getFBNetwork().getDataConnections().stream());
			retval = Stream.concat(retval, subappType.getFBNetwork().getEventConnections().stream());
		}

		return retval;
	}

	public static Stream<? extends EObject> getUntypedSubappChildren(final UntypedSubApp untypedSubapp) {
		Stream<? extends EObject> retval = getInterfaceListChildren(untypedSubapp.getInterface());
		retval = Stream.concat(retval, untypedSubapp.getSubAppNetwork().getNetworkElements().stream());
		retval = Stream.concat(retval, untypedSubapp.getAttributes().stream());
		return retval;
	}

	public static Stream<? extends EObject> getInterfaceListChildren(final InterfaceList interfaceList) {
		Stream<? extends EObject> retval = Stream.concat(interfaceList.getInputVars().stream(),
				interfaceList.getOutputVars().stream());
		retval = Stream.concat(retval, interfaceList.getInOutVars().stream());
		return retval;
	}

	public static Stream<? extends EObject> getAttributeDeclChildren(final AttributeDeclaration attrdecl) {
		if (attrdecl.getType() instanceof final StructuredType structType) {
			return getStructChildren(structType);
		}
		if (attrdecl.getType() instanceof final DirectlyDerivedType directType) {
			return Stream.of(directType.getBaseType());
		}
		return null;
	}

	public static Stream<VarDeclaration> getStructChildren(final StructuredType structType) {
		return structType.getMemberVariables().stream();
	}
}
