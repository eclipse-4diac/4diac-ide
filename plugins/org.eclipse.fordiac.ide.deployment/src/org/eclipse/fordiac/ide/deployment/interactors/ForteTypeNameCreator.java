/*******************************************************************************
 * Copyright (c) 2007, 2024 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
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
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;

public class ForteTypeNameCreator {

	public static String getForteTypeName(final FBNetworkElement fb) {
		if (fb != null && fb.getTypeEntry() != null) {
			if (fb instanceof final ConfigurableFB confFB) {
				return getConfigureFBType(confFB);
			}
			return getTypeName(fb);
		}
		return ""; //$NON-NLS-1$
	}

	private static String getTypeName(final FBNetworkElement fb) {
		return convertFullTypeNameToFORTE(fb.getFullTypeName());
	}

	private static String getConfigureFBType(final ConfigurableFB confFB) {
		DataType dt = null;
		if (confFB instanceof final ConfigurableMoveFB move) {
			dt = move.getDataType();
		}
		if (confFB instanceof final StructManipulator structMan) {
			dt = structMan.getStructType();
		}
		String typeName = getTypeName(confFB);
		if (dt != null) {
			// the _1 is needed for 4diac FORTE to separate type name from configuration
			// part
			typeName += "_1" + convertFullTypeNameToFORTE(PackageNameHelper.getFullTypeName(dt)); //$NON-NLS-1$
		}
		return typeName;
	}

	private static String convertFullTypeNameToFORTE(final String fullTypeName) {
		return fullTypeName.replace(':', '_');
	}

	private ForteTypeNameCreator() {
		throw new UnsupportedOperationException("Helper class should not be instantiated!"); //$NON-NLS-1$
	}
}
