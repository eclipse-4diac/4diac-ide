/*******************************************************************************
 * Copyright (c) 2024, 2025 Primetals Technologies Austria GmbH, HR Agrartechnik GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation
 *   Franz Höpfinger - added E_MOVE configurable support
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public final class BlockInstanceFactory {

	public static BlockFBNetworkElement createBlockInstanceForTypeEntry(final TypeEntry entry) {
		if (entry == null) {
			return LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement();
		}
		if (entry instanceof SubAppTypeEntry) {
			return LibraryElementFactory.eINSTANCE.createTypedSubApp();
		}
		if (entry instanceof AdapterTypeEntry) {
			return LibraryElementFactory.eINSTANCE.createAdapterFB();
		}
		if (entry instanceof final FBTypeEntry fbEntry) {
			return createFBInstanceForTypeEntry(fbEntry);
		}
		return null;
	}

	public static FB createFBInstanceForTypeEntry(final FBTypeEntry entry) {
		if (entry == null || entry.hasError()) {
			return LibraryElementFactory.eINSTANCE.createFB();
		}
		if (entry.getTypeName().startsWith(LibraryElementTags.FB_TYPE_COMM_MESSAGE)) {
			return LibraryElementFactory.eINSTANCE.createCommunicationChannel();
		}
		if (LibraryElementTags.TYPENAME_MUX.equals(entry.getTypeName())
				&& matchesPackageName(entry, LibraryElementTags.PACKAGE_NAME_MUXERS)) {
			return LibraryElementFactory.eINSTANCE.createMultiplexer();
		}
		if (LibraryElementTags.TYPENAME_DEMUX.equals(entry.getTypeName())
				&& matchesPackageName(entry, LibraryElementTags.PACKAGE_NAME_MUXERS)) {
			return LibraryElementFactory.eINSTANCE.createDemultiplexer();
		}
		if (LibraryElementTags.TYPENAME_FMOVE.equals(entry.getTypeName())
				&& matchesPackageName(entry, LibraryElementTags.PACKAGE_NAME_FMOVE)) {
			return LibraryElementFactory.eINSTANCE.createConfigurableMoveFB();
		}
		if (LibraryElementTags.TYPENAME_EMOVE.equals(entry.getTypeName())
				&& matchesPackageName(entry, LibraryElementTags.PACKAGE_NAME_EMOVE)) {
			return LibraryElementFactory.eINSTANCE.createConfigurableMoveFB();
		}
		if (LibraryElementPackage.Literals.COMPOSITE_FB_TYPE.equals(entry.getTypeEClass())) {
			return LibraryElementFactory.eINSTANCE.createCFBInstance();
		}
		return LibraryElementFactory.eINSTANCE.createFB();
	}

	private static boolean matchesPackageName(final TypeEntry entry, final String packageName) {
		return entry.getPackageName().isEmpty() || entry.getPackageName().equals(packageName);
	}

	private BlockInstanceFactory() {
		throw new UnsupportedOperationException();
	}
}
