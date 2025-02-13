/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.hierarchymanager.build;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.shared.SharedStateModule;
import org.eclipse.xtext.util.Modules2;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("static-method")
public class HierarchyBuildPlugin extends AbstractUIPlugin {

	private static final Logger logger = Logger.getLogger(HierarchyBuildPlugin.class);

	private static HierarchyBuildPlugin instance;

	private final Map<String, Injector> injectors = Collections.synchronizedMap(HashMap.newHashMap(2));

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		instance = this; // NOSONAR
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		injectors.clear();
		instance = null; // NOSONAR
		super.stop(context);
	}

	public static HierarchyBuildPlugin getInstance() {
		return instance;
	}

	public Injector getInjector(final String language) {
		return injectors.computeIfAbsent(language, this::createInjector);
	}

	protected Injector createInjector(final String language) {
		try {
			final com.google.inject.Module runtimeModule = getRuntimeModule(language);
			final com.google.inject.Module sharedStateModule = getSharedStateModule();
			final com.google.inject.Module uiModule = getUiModule(language);
			final com.google.inject.Module mergedModule = Modules2.mixin(runtimeModule, sharedStateModule, uiModule);
			return Guice.createInjector(mergedModule);
		} catch (final Exception e) {
			logger.error("Failed to create injector for " + language); //$NON-NLS-1$
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Failed to create injector for " + language, e); //$NON-NLS-1$ // NOSONAR
		}
	}

	protected com.google.inject.Module getRuntimeModule(final String grammar) {
		if (HierarchyLanguageConstants.ORG_ECLIPSE_FORDIAC_IDE_HIERARCHYMANAGER_HIERARCHY.equals(grammar)) {
			return new HierarchyRuntimeModule();
		}
		throw new IllegalArgumentException(grammar);
	}

	protected com.google.inject.Module getUiModule(final String grammar) {
		if (HierarchyLanguageConstants.ORG_ECLIPSE_FORDIAC_IDE_HIERARCHYMANAGER_HIERARCHY.equals(grammar)) {
			return new HierarchyUiModule(this);
		}
		throw new IllegalArgumentException(grammar);
	}

	protected com.google.inject.Module getSharedStateModule() {
		return new SharedStateModule();
	}
}
