/*******************************************************************************
 * Copyright (c) 2012, 2023 AIT, fortiss GmbH, Profactor GmbH
 * 				            Johannes Kepler University
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Filip Andren, Alois Zoitl, Gerhard Ebenhofer, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked update fb type to accept also supapps
 *   Alois Zoitl - fixed issues in maintaining FB parameters
 *   Martin Melik Merkumians - preserves VarConfig and Visible attributes at type update
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.List;

import org.eclipse.fordiac.ide.model.helpers.BlockInstanceFactory;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

/** UpdateFBTypeCommand triggers an update of the type for an FB instance */
public class UpdateFBTypeCommand extends AbstractUpdateFBNElementCommand {

	public UpdateFBTypeCommand(final FBNetworkElement fbnElement, final TypeEntry entry) {
		super(fbnElement);
		if ((entry instanceof FBTypeEntry) || (entry instanceof SubAppTypeEntry)) {
			this.entry = entry;
		} else {
			this.entry = fbnElement.getTypeEntry();
		}
	}

	public UpdateFBTypeCommand(final FBNetworkElement fbnElement) {
		this(fbnElement, null);
	}

	@Override
	public boolean canExecute() {
		if ((null == entry) || (null == oldElement) || (null == network)) {
			return false;
		}
		return FBNetworkHelper.isTypeInsertionSafe((FBType) entry.getType(), network);
	}

	protected void setEntry(final TypeEntry entry) {
		this.entry = entry;
	}

	protected TypeEntry getEntry() {
		return entry;
	}

	@Override
	protected FBNetworkElement createCopiedFBEntry(final FBNetworkElement srcElement) {
		entry = reloadTypeEntry(entry, srcElement);

		final FBNetworkElement copy = BlockInstanceFactory.createBlockInstanceForTypeEntry(entry);
		if (srcElement instanceof final AdapterFB adp) {
			((AdapterFB) copy).setAdapterDecl(adp.getAdapterDecl());
		}
		copy.setTypeEntry(entry);
		return copy;
	}

	private static TypeEntry reloadTypeEntry(final TypeEntry entry, final FBNetworkElement context) {
		if (entry == null || isValidTypeEntry(entry)) {
			return entry;
		}

		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(context);
		if (typeLibrary == null) {
			FordiacLogHelper.logWarning("Cannot get type library for current FB network element"); //$NON-NLS-1$
			return entry;
		}

		final TypeEntry reloadedTypeEntry = typeLibrary.find(entry.getFullTypeName());
		if (isValidTypeEntry(reloadedTypeEntry)) {
			return reloadedTypeEntry;
		}

		final List<TypeEntry> candidates = typeLibrary.findUnqualified(entry.getTypeName());
		if (candidates.size() == 1 && isValidTypeEntry(candidates.get(0))) {
			return candidates.get(0);
		}

		if (entry instanceof SubAppTypeEntry) {
			return typeLibrary.createErrorTypeEntry(entry.getFullTypeName(),
					LibraryElementPackage.eINSTANCE.getSubAppType());
		}
		if (entry instanceof FBTypeEntry) {
			return typeLibrary.createErrorTypeEntry(entry.getFullTypeName(),
					LibraryElementPackage.eINSTANCE.getFBType());
		}
		FordiacLogHelper.logWarning("Unsupported error type entry for " + entry.getClass().getName()); //$NON-NLS-1$
		return entry;
	}

	private static boolean isValidTypeEntry(final TypeEntry entry) {
		// check that entry is still current in the type library (i.e., we can find the
		// entry using its file)
		return entry != null && entry.getFile() != null && entry.getFile().exists() && entry.getTypeLibrary() != null
				&& entry.getTypeLibrary().getTypeEntry(entry.getFile()) == entry;
	}
}
