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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

public class ResultListLabelProvider extends LabelProvider implements IStyledLabelProvider {

	private String[] searchString;
	private final boolean showFilePath;

	public ResultListLabelProvider() {
		this(false);
	}

	public ResultListLabelProvider(final boolean showFilePath) {
		setSearchString(""); //$NON-NLS-1$
		this.showFilePath = showFilePath;
	}

	@Override
	public StyledString getStyledText(final Object element) {
		if (element instanceof final TypeEntry entry) {
			final StyledString styledString = getTypeEntryStyledText(entry, showFilePath);

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

	public static StyledString getTypeEntryStyledText(final TypeEntry entry, final boolean showFilePath) {
		final StyledString styledString = new StyledString(entry.getTypeName());

		final String packageName = entry.getPackageName();
		if (!packageName.isEmpty()) {
			styledString.append(" - " + packageName, StyledString.DECORATIONS_STYLER); //$NON-NLS-1$
		}

		if (showFilePath) {
			addFilePath(styledString, entry.getFile(), packageName);
		}

		styledString.append(" [" + entry.getComment() + "]", //$NON-NLS-1$ //$NON-NLS-2$
				showFilePath ? StyledString.DECORATIONS_STYLER : StyledString.QUALIFIER_STYLER);
		return styledString;
	}

	private static void addFilePath(final StyledString styledString, final IFile file, final String packageName) {
		final String pathString;
		final IPath filePath = file.getFullPath();
		final String filePackageName = PackageNameHelper.getPackageNameFromFile(file);

		if (!packageName.isEmpty() && !filePackageName.isEmpty()
				&& filePackageName.toLowerCase().endsWith(packageName.toLowerCase())) {
			final int stripCount = packageName.split(PackageNameHelper.PACKAGE_NAME_DELIMITER).length + 1;
			pathString = filePath.removeLastSegments(stripCount).addTrailingSeparator().toOSString() + "..."; //$NON-NLS-1$
		} else {
			pathString = filePath.removeLastSegments(1).toOSString();
		}
		styledString.append(" - " + pathString, StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
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
