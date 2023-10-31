/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.AttributeTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.AttributeTypeImporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class AttributeTypeEntryImpl extends AbstractTypeEntryImpl implements AttributeTypeEntry {

	@Override
	public synchronized AttributeDeclaration getType() {
		final LibraryElement type = super.getType();
		if (type instanceof final AttributeDeclaration attributeDeclaration) {
			return attributeDeclaration;
		}
		return null;
	}

	@Override
	public synchronized AttributeDeclaration getTypeEditable() {
		final LibraryElement type = super.getTypeEditable();
		if (type instanceof final AttributeDeclaration attributeDeclaration) {
			return attributeDeclaration;
		}
		return null;
	}

	@Override
	public synchronized void setType(final LibraryElement type) {
		if (type instanceof AttributeDeclaration) {
			super.setType(type);
		} else {
			super.setType(null);
			if (null != type) {
				FordiacLogHelper.logError("tried to set no AttributeDeclaration as type entry for AttributeTypeEntry"); //$NON-NLS-1$
			}
		}
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new AttributeTypeImporter(getFile());
	}

	@Override
	protected AbstractTypeExporter getExporter() {
		return new AttributeTypeExporter(getTypeEditable());
	}
}
