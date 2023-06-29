/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.emf;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.util.EcoreUtil;

public final class FordiacMetaData {

	public static final String ANNOTATION_URI = "http:///org/eclipse/fordiac/ide/model/MetaData"; //$NON-NLS-1$

	public static final String SYNTHETIC_KEY = "synthetic"; //$NON-NLS-1$

	public static boolean isSynthetic(final EModelElement element) {
		return element != null && Boolean.parseBoolean(EcoreUtil.getAnnotation(element, ANNOTATION_URI, SYNTHETIC_KEY));
	}

	private FordiacMetaData() {
		throw new UnsupportedOperationException();
	}
}
