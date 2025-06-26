/*******************************************************************************
 * Copyright (c) 2024, 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Roithmayr - initial API and implementation and/or initial documentation
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.emf;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.util.ITransformer;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class HashMetaData {
	public static final String ANNOTATION_URI = "http:///org/eclipse/fordiac/ide/model/HashMetaData"; //$NON-NLS-1$

	public static final String IGNORED_KEY = "ignored"; //$NON-NLS-1$
	public static final String TRANSFORMER_KEY = "transformer"; //$NON-NLS-1$

	public static boolean isIgnored(final EModelElement element) {
		if (element == null) {
			return false;
		}
		final String annotation = EcoreUtil.getAnnotation(element, ANNOTATION_URI, IGNORED_KEY);
		return annotation != null && Boolean.parseBoolean(annotation);
	}

	public static Object transform(final EModelElement element, final Object value) {
		if (element == null) {
			return value;
		}
		final String annotation = EcoreUtil.getAnnotation(element, ANNOTATION_URI, TRANSFORMER_KEY);
		if (annotation == null) {
			return value;
		}
		try {
			if (Class.forName(annotation).getConstructor().newInstance() instanceof final ITransformer itransformer) {
				return itransformer.transform(value);
			}
		} catch (final ClassNotFoundException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			FordiacLogHelper.logError("Could not transform model element!", e); //$NON-NLS-1$
		}
		return value;
	}

	private HashMetaData() {
		throw new UnsupportedOperationException();
	}
}
