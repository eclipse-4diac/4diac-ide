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

import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
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
		if (element instanceof final TypeEntry entry) {
			final StyledString styledString = getTypeEntryStyledText(entry);

			int lastIndex = 0;
			for (final String searchStringElement : searchString) {
				final int offset = styledString.toString().toUpperCase().indexOf((searchStringElement.toUpperCase()),
						lastIndex);
				if (offset >= 0) {
					styledString.setStyle(offset, searchStringElement.length(), BoldStyler.INSTANCE_DEFAULT);
					lastIndex = offset + searchStringElement.length();
				}
			}
			return styledString;
		}
		if (element != null) {
			return new StyledString(element.toString());
		}
		return new StyledString();
	}

	public static StyledString getTypeEntryStyledText(final TypeEntry entry) {
		final StyledString styledString = new StyledString(entry.getTypeName());

		final String packageName = entry.getPackageName();
		if (!packageName.isEmpty()) {
			styledString.append(" - " + packageName, StyledString.DECORATIONS_STYLER); //$NON-NLS-1$
		}
		styledString.append(" - " + entry.getComment(), StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
		return styledString;
	}

	@Override
	public Image getImage(final Object element) {
		if (element instanceof final TypeEntry entry) {
			return TypeImageProvider.getImageForTypeEntry(entry);
		}
		return null;
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
