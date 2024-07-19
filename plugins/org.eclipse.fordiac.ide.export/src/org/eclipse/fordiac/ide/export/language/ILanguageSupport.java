/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.export.language;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public interface ILanguageSupport {

	/**
	 * A option providing a {@code Map<Object, Object>} cache map.
	 */
	String OPTION_CACHE = "CACHE"; //$NON-NLS-1$

	/** Prepare for export */
	boolean prepare();

	/** Generate the template contents. */
	CharSequence generate(Map<?, ?> options) throws ExportException;

	/** Get the required dependencies */
	Set<INamedElement> getDependencies(Map<?, ?> options);

	/** Return the errors. */
	List<String> getErrors();

	/** Return the warnings. */
	List<String> getWarnings();

	/** Return the infos. */
	List<String> getInfos();

	@SuppressWarnings("unchecked")
	static Map<Object, Object> getCacheOption(final Map<?, ?> options) {
		return (Map<Object, Object>) options.get(OPTION_CACHE);
	}

	@SuppressWarnings("unchecked")
	static <K, V> V computeCached(final Map<Object, Object> cache, final K key, final Function<K, V> function) {
		if (cache != null) {
			return (V) cache.computeIfAbsent(key, k -> function.apply((K) k));
		}
		return function.apply(key);
	}
}
