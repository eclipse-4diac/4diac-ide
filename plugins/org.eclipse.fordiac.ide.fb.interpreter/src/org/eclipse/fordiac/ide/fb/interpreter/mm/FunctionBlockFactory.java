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
package org.eclipse.fordiac.ide.fb.interpreter.mm;

import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

public class FunctionBlockFactory implements Factory {

	public FunctionBlockFactory() {
		// Do nothing
	}

	@Override
	public Resource createResource(URI uri) {
		final XMLResource fbtResource = new XMLResourceImpl(uri);
		addExtendedFunctionBlockMetadata(fbtResource);
		return fbtResource;
	}

	public void addExtendedFunctionBlockMetadata(XMLResource fbtResource) {
		//Add the Extended Meta-Data
		final var extendedMetaData = new BasicExtendedMetaData(new EPackageRegistryImpl(EPackage.Registry.INSTANCE));
		extendedMetaData.putPackage(null, LibraryElementPackage.eINSTANCE);
		//Ignore DTD
		final var parserFeatures = new HashMap<>();
		parserFeatures.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
				Boolean.FALSE);
		parserFeatures.put("http://xml.org/sax/features/validation", Boolean.FALSE); //$NON-NLS-1$
		fbtResource.getDefaultLoadOptions().put(XMLResource.OPTION_PARSER_FEATURES, parserFeatures);
		//Configure Load Options
		fbtResource.getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);
		//Configure Save Options
		fbtResource.getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);
	}
}
