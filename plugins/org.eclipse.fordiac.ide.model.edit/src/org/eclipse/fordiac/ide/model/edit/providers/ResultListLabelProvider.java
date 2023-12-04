/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - added search highlighting
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.edit.providers;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

public class ResultListLabelProvider extends LabelProvider implements IStyledLabelProvider {

	private String[] searchString;

	public ResultListLabelProvider(final String searchString) {
		setSearchString(searchString);
	}

	public ResultListLabelProvider() {
		setSearchString(""); //$NON-NLS-1$
	}

	@Override
	public StyledString getStyledText(final Object element) {
		StyledString styledString = null;
		if (element instanceof final TypeEntry entry) {
			styledString = new StyledString(entry.getTypeName());
			styledString.append(" - " + entry.getType().getComment(), //$NON-NLS-1$
					StyledString.QUALIFIER_STYLER);

			int lastIndex = 0;
			for (final String searchStringElement : searchString) {
				final int offset = styledString.toString().toUpperCase().indexOf((searchStringElement.toUpperCase()),
						lastIndex);
				if (offset >= 0) {
					styledString.setStyle(offset, searchStringElement.length(), BoldStyler.INSTANCE_DEFAULT);
					lastIndex = offset + searchStringElement.length();
				}
			}

		} else {
			styledString = new StyledString(element.toString());
		}
		return styledString;
	}

	@Override
	public Image getImage(final Object element) {
		if (element instanceof final TypeEntry entry) {
			return getTypeImage(entry.getType());
		}
		return null;
	}

	public static Image getTypeImage(final LibraryElement type) {
		if (type instanceof SubAppType) {
			return FordiacImage.ICON_SUB_APP_TYPE.getImage();
		}
		if (type instanceof BasicFBType) {
			return FordiacImage.ICON_BASIC_FB.getImage();
		}
		if (type instanceof SimpleFBType) {
			return FordiacImage.ICON_SIMPLE_FB.getImage();
		} else if (type instanceof CompositeFBType) {
			return FordiacImage.ICON_COMPOSITE_FB.getImage();
		} else if (type instanceof StructuredType) {
			return FordiacImage.ICON_DATA_TYPE.getImage();
		} else if (type instanceof AdapterFBType) {
			return FordiacImage.ICON_ADAPTER_TYPE.getImage();
		} else if (type instanceof FunctionFBType) {
			return FordiacImage.ICON_FUNCTION.getImage();
		} else {
			return FordiacImage.ICON_SIFB.getImage();
		}
	}

	@Override
	public String getText(final Object element) {
		return (element instanceof final TypeEntry entry) ? String.format("%s - %s", getStyledText(element).toString(), //$NON-NLS-1$
				entry.getFile().getFullPath()) : "-"; //$NON-NLS-1$
	}

	public void setSearchString(final String searchString) {
		this.searchString = new String[] { searchString };
		validateSearchString();
	}

	private void validateSearchString() {
		searchString = searchString[0].split("[\\*\\?]+", -1); //$NON-NLS-1$
		for (int i = 0; i < searchString.length; i++) {
			searchString[i] = searchString[i].replaceAll("\\W", ""); //$NON-NLS-1$//$NON-NLS-2$
		}
	}

}
