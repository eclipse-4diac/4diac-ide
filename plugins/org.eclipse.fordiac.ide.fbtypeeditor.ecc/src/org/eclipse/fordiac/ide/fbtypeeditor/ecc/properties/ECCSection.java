/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - cleaned command stack handling for property sections
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors.IAlgorithmEditorCreator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECCRootEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECTransitionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;

public class ECCSection {

	public static List<String> getLanguages() {
		List<String> languages = new ArrayList<>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("org.eclipse.fordiac.ide.fbtypeeditor.ecc.algorithmEditor"); //$NON-NLS-1$
		IExtension[] extensions = point.getExtensions();
		for (IExtension extension : extensions) {
			IConfigurationElement[] elements = extension.getConfigurationElements();
			for (IConfigurationElement element : elements) {
				try {
					Object obj = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (obj instanceof IAlgorithmEditorCreator) {
						String lang = element.getAttribute("language"); //$NON-NLS-1$
						languages.add(lang);
					}
				} catch (Exception e) {
				}
			}
		}
		return languages;
	}

	public static Object getECCInputType(Object input) {
		if (input instanceof ECCRootEditPart) {
			return ((ECCRootEditPart) input).getCastedECCModel().getBasicFBType();
		}
		if (input instanceof ECC) {
			return ((ECC) input).getBasicFBType();
		}
		if (input instanceof ECActionAlgorithmEditPart) {
			return ((ECActionAlgorithmEditPart) input).getAction().getECState().getECC().getBasicFBType();
		}
		if (input instanceof ECActionOutputEventEditPart) {
			ECAction action = ((ECActionOutputEventEditPart) input).getAction();
			if ((null != action) && (null != action.getECState())) {
				return action.getECState().getECC().getBasicFBType();
			}
		}
		if (input instanceof ECTransitionEditPart) {
			return ((ECTransitionEditPart) input).getModel().getECC().getBasicFBType();
		}
		if (input instanceof ECStateEditPart) {
			return ((ECStateEditPart) input).getModel().getECC().getBasicFBType();
		}
		return null;
	}

	private ECCSection() {
		throw new UnsupportedOperationException();
	}
}
