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
 *   Bianca Wiesmayr - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public final class BlockInstanceFactory {

	public static FBNetworkElement createBlockInstanceForTypeEntry(final TypeEntry entry) {
		if (entry == null || entry instanceof ErrorTypeEntry) {
			return LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement();
		}
		if (entry.getTypeName().startsWith(LibraryElementTags.FB_TYPE_COMM_MESSAGE)) {
			return LibraryElementFactory.eINSTANCE.createCommunicationChannel();
		}
		if (entry instanceof SubAppTypeEntry) {
			return LibraryElementFactory.eINSTANCE.createSubApp();
		}
		if (entry instanceof AdapterTypeEntry) {
			return LibraryElementFactory.eINSTANCE.createAdapterFB();
		}
		if (entry.getType() instanceof CompositeFBType) {
			return LibraryElementFactory.eINSTANCE.createCFBInstance();
		}
		if (LibraryElementTags.TYPENAME_MUX.equals(entry.getTypeName())) {
			return LibraryElementFactory.eINSTANCE.createMultiplexer();
		}
		if (LibraryElementTags.TYPENAME_DEMUX.equals(entry.getTypeName())) {
			return LibraryElementFactory.eINSTANCE.createDemultiplexer();
		}
		if (LibraryElementTags.TYPENAME_FMOVE.equals(entry.getTypeName())) {
			return LibraryElementFactory.eINSTANCE.createConfigurableMoveFB();
		}
		return LibraryElementFactory.eINSTANCE.createFB();
	}

	private BlockInstanceFactory() {
		throw new UnsupportedOperationException();
	}
}
