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
 *   Antonio Garmendía, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class ModelSerializer {

	private final ResourceSet resourceSet;

	public ModelSerializer() {
		// register XMI resource factory for .fsa extension
		final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		final Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());

		// create a new resource set
		resourceSet = new ResourceSetImpl();
	}

	public void saveModel(String uri, EObject content) throws IOException {
		// create file URI, always use File.getAbsolutePath()
		final URI fileUri = URI.createFileURI(new File(uri).getAbsolutePath());
		// load resource
		final Resource resource = resourceSet.createResource(fileUri);

		// add root object to resource
		resource.getContents().add(content);

		// save resource
		resource.save(Collections.EMPTY_MAP);
	}

}
