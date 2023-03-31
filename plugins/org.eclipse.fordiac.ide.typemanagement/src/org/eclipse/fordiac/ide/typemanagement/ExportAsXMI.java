/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
 *               2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 * 			     - update XMI exporter for FUNCTION and new STAlgorithm grammar
 *   Martin Jobst
 *     - refactor export
 *     - add comments export
 *   Fabio Gandolfi
 *     - moved export in new class
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.resource.STAlgorithmResourceSetInitializer;
import org.eclipse.fordiac.ide.structuredtextcore.parsetree.reconstr.STCoreCommentAssociater;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

/** @deprecated Use org.eclipse.fordiac.ide.export.xmi.XMIExportFilter instead. */
@Deprecated(forRemoval = true)
public class ExportAsXMI {
	static final String XMI_EXTENSION = "xmi"; //$NON-NLS-1$

	public void export(final IFile file) {
		final URI uri = URI.createPlatformResourceURI(file.getParent().getFullPath().toString(), true);

		try {
			export(file, uri);
		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	public void export(final IFile file, final URI saveLocation) throws IOException {
		final URI xmiUri = saveLocation.appendSegment(file.getName()).trimFileExtension()
				.appendFileExtension(XMI_EXTENSION);
		final XtextResourceSet resourceSet = new XtextResourceSet();
		new STAlgorithmResourceSetInitializer().initialize(resourceSet, file.getProject());
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		final Resource resource = resourceSet
				.getResource(URI.createPlatformResourceURI(file.getFullPath().toString(), true),
						true);

		if (resource instanceof XtextResource) {
			final STSource source = (STSource) resource.getContents().get(0);
			if (source instanceof STSource) {
				final var commentAssociater = ((XtextResource) resource).getResourceServiceProvider()
						.get(STCoreCommentAssociater.class);
				if (commentAssociater != null) {
					source.getComments().addAll(commentAssociater.associateComments(source));
				}
			}
		}

		final ResourceSetImpl xmiResourceSet = new ResourceSetImpl();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().putIfAbsent(XMI_EXTENSION,
				new XMIResourceFactoryImpl());
		final Resource xmiRessource = xmiResourceSet.createResource(xmiUri);
		xmiRessource.getContents().addAll(EcoreUtil.copyAll(resource.getContents()));

		try {
			final HashMap<String, Object> options = new HashMap<>();
			options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
			options.put(XMLResource.OPTION_SKIP_ESCAPE_URI, Boolean.FALSE);
			xmiRessource.save(options);
		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			System.out.println(e.getMessage() + " " + e); // log it in console for ANT Tasks //$NON-NLS-1$
		}
	}

}
