/*******************************************************************************
 * Copyright (c) 2007, 2025 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 * 							Johannes Kepler University,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl - Extracted from DeploymentExecutor and extended for Configurable move
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.interactors;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.util.LibraryElementHashException;

public class ForteTypeNameCreator implements TypeNameCreator {

	public static final TypeNameCreator TYPE_NAME_CREATOR = new ForteTypeNameCreator();

	@Override
	public String getTypeName(final TypeEntry entry) {
		return convertFullTypeNameToFORTE(entry.getFullTypeName());
	}

	@Override
	public String getTypeName(final FBNetworkElement fb) {
		if (fb != null && fb.getTypeEntry() != null) {
			if (fb instanceof final ConfigurableFB confFB) {
				return getConfigureFBType(confFB);
			}
			return getForteTypeName(fb);
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public String getTypeNameWithHash(final TypeEntry entry) throws LibraryElementHashException {
		final String hash = entry.getTypeHash();
		if (hash.isEmpty()) {
			return getTypeName(entry);
		}
		return getTypeName(entry) + '#' + hash;
	}

	private static String getForteTypeName(final FBNetworkElement fb) {
		return convertFullTypeNameToFORTE(fb.getFullTypeName());
	}

	private static String getConfigureFBType(final ConfigurableFB confFB) {
		final DataType dt = confFB.getDataType();
		String typeName = getForteTypeName(confFB);
		if (dt != null) {
			// The _1 is needed for 4diac FORTE to separate type name from configuration
			// part
			typeName += "_1" + convertFullTypeNameToFORTE(PackageNameHelper.getFullTypeName(dt)); //$NON-NLS-1$
		}
		return typeName;
	}

	private static String convertFullTypeNameToFORTE(final String fullTypeName) {
		return fullTypeName;
	}

}
