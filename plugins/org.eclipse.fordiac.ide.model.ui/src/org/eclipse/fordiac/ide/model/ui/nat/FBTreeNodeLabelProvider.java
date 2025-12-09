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
 *   Sebastian Hollersbacher - Refactored into own class
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.nat;

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog.TreeNodeLabelProvider;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.swt.graphics.Image;

public class FBTreeNodeLabelProvider extends TreeNodeLabelProvider {

	public static final FBTreeNodeLabelProvider INSTANCE = new FBTreeNodeLabelProvider();

	protected FBTreeNodeLabelProvider() {
	}

	@Override
	public Image getImage(final Object element) {
		if (element instanceof final TypeNode node && node.getType() != null) {
			final LibraryElement type = node.getType();

			if (type instanceof BasicFBType) {
				return FordiacImage.ICON_BASIC_FB.getImage();
			}
			if (type instanceof CompositeFBType) {
				return FordiacImage.ICON_COMPOSITE_FB.getImage();
			}
			if (type instanceof SimpleFBType) {
				return FordiacImage.ICON_SIMPLE_FB.getImage();
			}
			if (type instanceof ServiceInterfaceFBType) {
				return FordiacImage.ICON_SIFB.getImage();
			}
		}
		return super.getImage(element);
	}
}
