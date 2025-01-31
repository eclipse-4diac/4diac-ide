/*******************************************************************************
 * Copyright (c) 2023 - 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha - initial implementation and/or documentation
 *   Ernst Blecha - run reconciler and autoformat during saving
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.function.Consumer;

import org.apache.tools.ant.BuildException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResourceSet;

public class SaveBlock extends AbstractBlockModelTask {

	public void setAutoFormat(final boolean enable) {
		this.autoformat = enable;
	}

	@Override
	protected void modifyBlock(final FBType fb) {
		log(MessageFormat.format("Save {0}/{1}", projectname, blockname)); //$NON-NLS-1$
		saveBlock(fb.getTypeEntry(), autoformat, this::log);
	}

	private boolean autoformat = false;

	public static void saveBlock(final TypeEntry te, final boolean autoformat, final Consumer<String> logger) {
		try {
			final IFile typeFile = te.getFile();

			if (typeFile.isLinked(IResource.CHECK_ANCESTORS)) {
				// do not modify files stored outside the project that are only linked in
				return;
			}

			logger.accept(te.getFullTypeName());

			final URI sourceUri = URI.createPlatformResourceURI(typeFile.getFullPath().toString(), true);
			final Resource resource = getResourceSet(te).getResource(sourceUri, true);

			final SaveOptions.Builder optionsBuilder = SaveOptions.newBuilder();
			if (autoformat) {
				optionsBuilder.format();
			}
			resource.save(optionsBuilder.getOptions().toOptionsMap());
		} catch (final IOException e) {
			throw new BuildException(e);
		}
	}

	private static final URI SYNTHETIC_URI_FBT = URI.createURI("__synthetic.stalg"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_FBT = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_FBT);

	private static final URI SYNTHETIC_URI_FCT = URI.createURI("__synthetic.stfunc"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_FCT = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_FCT);

	private static final URI SYNTHETIC_URI_GCF = URI.createURI("__synthetic.globalconsts"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER_GCF = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI_GCF);

	private static ResourceSet getResourceSet(final TypeEntry te) {
		XtextResourceSet resourceSet = null;

		if (te.getFile().getFullPath().getFileExtension().equalsIgnoreCase(TypeLibraryTags.FC_TYPE_FILE_ENDING)) {
			resourceSet = SERVICE_PROVIDER_FCT.get(XtextResourceSet.class);
		}
		if (te.getFile().getFullPath().getFileExtension().equalsIgnoreCase(TypeLibraryTags.GLOBAL_CONST_FILE_ENDING)) {
			resourceSet = SERVICE_PROVIDER_GCF.get(XtextResourceSet.class);
		}
		resourceSet = SERVICE_PROVIDER_FBT.get(XtextResourceSet.class);

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
				TypeLibraryTags.FB_TYPE_FILE_ENDING.toLowerCase(), //
				SERVICE_PROVIDER_FBT.get(IResourceFactory.class));
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
				TypeLibraryTags.FB_TYPE_FILE_ENDING, //
				SERVICE_PROVIDER_FBT.get(IResourceFactory.class));
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
				TypeLibraryTags.FC_TYPE_FILE_ENDING.toLowerCase(), //
				SERVICE_PROVIDER_FCT.get(IResourceFactory.class));
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
				TypeLibraryTags.FC_TYPE_FILE_ENDING, //
				SERVICE_PROVIDER_FCT.get(IResourceFactory.class));
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
				TypeLibraryTags.GLOBAL_CONST_FILE_ENDING.toLowerCase(), //
				SERVICE_PROVIDER_GCF.get(IResourceFactory.class));
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
				TypeLibraryTags.GLOBAL_CONST_FILE_ENDING, //
				SERVICE_PROVIDER_GCF.get(IResourceFactory.class));

		return resourceSet;
	}

}
