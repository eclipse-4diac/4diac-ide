/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;


public class ModelDeserializer {

	private final ResourceSet resourceSet;

	public ModelDeserializer() {
		// register XMI resource factory for .fsa extension
		final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		final Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl()); //$NON-NLS-1$

		// create a new resource set
		resourceSet = new ResourceSetImpl();

		// register FSA metamodel
		// only required in the development Eclipse instance
		resourceSet.getPackageRegistry().put(LibraryElementPackage.eINSTANCE.getNsURI(), LibraryElementPackage.eINSTANCE);
	}

	public EObject loadModel(final String uri) {
		// create file URI, always use File.getAbsolutePath()
		final URI fileUri = URI.createURI(getModelFile(uri));
		// create resource
		final Resource resource = resourceSet.getResource(fileUri, true);
		// retrieve first EObject in the resource
		return resource.getContents().get(0);
	}

	public String getModelFile(final String resourceUri) {
		try {
			final Bundle bundle = FrameworkUtil.getBundle(getClass());
			final URL featureFile = FileLocator.toFileURL(bundle.getResource(resourceUri));
			return featureFile.toString();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
