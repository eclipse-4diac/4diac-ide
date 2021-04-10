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
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors.IAlgorithmEditorCreator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECCRootEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECTransitionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;

public final class ECCSection {

	public static List<String> getLanguages() {
		final List<String> languages = new ArrayList<>();
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint point = registry.getExtensionPoint("org.eclipse.fordiac.ide.fbtypeeditor.ecc.algorithmEditor"); //$NON-NLS-1$
		final IExtension[] extensions = point.getExtensions();
		for (final IExtension extension : extensions) {
			final IConfigurationElement[] elements = extension.getConfigurationElements();
			for (final IConfigurationElement element : elements) {
				try {
					final Object obj = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (obj instanceof IAlgorithmEditorCreator) {
						final String lang = element.getAttribute("language"); //$NON-NLS-1$
						languages.add(lang);
					}
				} catch (final Exception e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}
		}
		return languages;
	}

	public static Object getECCInputType(final Object input) {
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
			final ECAction action = ((ECActionOutputEventEditPart) input).getAction();
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
