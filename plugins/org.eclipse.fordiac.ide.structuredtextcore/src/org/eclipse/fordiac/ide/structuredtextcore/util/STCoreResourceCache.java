/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.util;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.OnChangeEvictingCache;

import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class STCoreResourceCache extends OnChangeEvictingCache {

	@Override
	public void clear(final Resource resource) {
		if (resource instanceof XtextResource) {
			super.clear(resource);
		}
	}

	@Override
	public <T> T get(final Object key, final Resource resource, final Provider<T> provider) {
		if (resource instanceof XtextResource) {
			return super.get(key, resource, provider);
		}
		return provider.get();
	}

	@Override
	public CacheAdapter getOrCreate(final Resource resource) {
		if (resource instanceof XtextResource) {
			return super.getOrCreate(resource);
		}
		throw new UnsupportedOperationException("Not supported for non-Xtext resources"); //$NON-NLS-1$
	}
}
