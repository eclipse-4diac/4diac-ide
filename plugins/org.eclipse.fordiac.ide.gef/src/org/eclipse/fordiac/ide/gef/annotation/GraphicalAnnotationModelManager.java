/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.gef.annotation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;

public class GraphicalAnnotationModelManager {

	private static final String GRAPHICAL_ANNOTATION_PROVIDER_EXTENSION_POINT_ID = "org.eclipse.fordiac.ide.gef.graphicalAnnotationProvider"; //$NON-NLS-1$

	private static final GraphicalAnnotationModelManager INSTANCE = new GraphicalAnnotationModelManager();

	private final List<GraphicalAnnotationProviderDescriptor> providers = loadProviders();

	public List<GraphicalAnnotationProvider> getProviders(final GraphicalAnnotationModel model, final Object context) {
		return providers.stream().filter(desc -> desc.isEnabled(context))
				.map(GraphicalAnnotationProviderDescriptor::createProvider).filter(Objects::nonNull)
				.filter(provider -> SafeRunner.run(() -> Boolean.valueOf(provider.initialize(model, context)))
						.booleanValue())
				.toList();
	}

	private static List<GraphicalAnnotationProviderDescriptor> loadProviders() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint point = registry.getExtensionPoint(GRAPHICAL_ANNOTATION_PROVIDER_EXTENSION_POINT_ID);
		return Stream.of(point.getExtensions()).map(IExtension::getConfigurationElements).flatMap(Stream::of)
				.map(GraphicalAnnotationProviderDescriptor::new).toList();
	}

	public static GraphicalAnnotationModelManager getInstance() {
		return INSTANCE;
	}
}
