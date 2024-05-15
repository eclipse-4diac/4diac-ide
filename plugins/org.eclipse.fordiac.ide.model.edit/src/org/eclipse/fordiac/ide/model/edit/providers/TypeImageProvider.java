/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Erich Jobst
 *               - avoid loading type by using cached meta-information from type entry
 *   Alois Zoitl - extracted from FBTypeLabelProvider for usage in different label
 *                 providers
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit.providers;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.swt.graphics.Image;

public class TypeImageProvider {

	private static final Map<EClass, FordiacImage> TYPE_IMAGES = Map.of( //
			LibraryElementPackage.Literals.ADAPTER_TYPE, FordiacImage.ICON_ADAPTER_TYPE, //
			LibraryElementPackage.Literals.ATTRIBUTE_DECLARATION, FordiacImage.ICON_ATTRIBUTE_DECLARATION,
			LibraryElementPackage.Literals.SUB_APP_TYPE, FordiacImage.ICON_SUB_APP_TYPE,
			LibraryElementPackage.Literals.BASIC_FB_TYPE, FordiacImage.ICON_BASIC_FB,
			LibraryElementPackage.Literals.COMPOSITE_FB_TYPE, FordiacImage.ICON_COMPOSITE_FB,
			LibraryElementPackage.Literals.SIMPLE_FB_TYPE, FordiacImage.ICON_SIMPLE_FB,
			LibraryElementPackage.Literals.SERVICE_INTERFACE_FB_TYPE, FordiacImage.ICON_SIFB,
			LibraryElementPackage.Literals.FUNCTION_FB_TYPE, FordiacImage.ICON_FUNCTION, //
			DataPackage.Literals.ANY_DERIVED_TYPE, FordiacImage.ICON_DATA_TYPE);

	public static FordiacImage get4diacImageForTypeEntry(final TypeEntry typeEntry) {
		if (typeEntry != null) {
			final EClass typeEClass = typeEntry.getTypeEClass();
			return (typeEClass != null) ? TYPE_IMAGES.get(typeEClass) : null;
		}
		return null;
	}

	public static Image getImageForTypeEntry(final TypeEntry typeEntry) {
		final FordiacImage image = get4diacImageForTypeEntry(typeEntry);
		return (image != null) ? image.getImage() : null;
	}

	private TypeImageProvider() {
		throw new UnsupportedOperationException("Helper class shouldn't be instantiated!"); //$NON-NLS-1$
	}
}
