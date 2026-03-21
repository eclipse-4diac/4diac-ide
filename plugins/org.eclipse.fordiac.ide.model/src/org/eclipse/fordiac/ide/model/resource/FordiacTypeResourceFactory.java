/********************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Austria, Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Antonio Garmendia,Bianca Wiesmayr
 *                     - initial API and implementation and/or initial documentation
 *  Martin Erich Jobst - add singleton
 *  Alois Zoitl        - Adjusted to new type resources
 ********************************************************************************/

package org.eclipse.fordiac.ide.model.resource;

import java.text.MessageFormat;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.resource.impl.AdapterTypeResourceImpl;
import org.eclipse.fordiac.ide.model.resource.impl.AttributeTypeResourceImpl;
import org.eclipse.fordiac.ide.model.resource.impl.DataTypeResourceImpl;
import org.eclipse.fordiac.ide.model.resource.impl.DeviceTypeResourceImpl;
import org.eclipse.fordiac.ide.model.resource.impl.FBTypeResourceImpl;
import org.eclipse.fordiac.ide.model.resource.impl.FunctionTypeResourceImpl;
import org.eclipse.fordiac.ide.model.resource.impl.GlobalConstTypeResourceImpl;
import org.eclipse.fordiac.ide.model.resource.impl.ResourceTypeResourceImpl;
import org.eclipse.fordiac.ide.model.resource.impl.SegmentTypeResourceImpl;
import org.eclipse.fordiac.ide.model.resource.impl.SubappTypeResourceImpl;
import org.eclipse.fordiac.ide.model.resource.impl.SystemResourceImpl;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class FordiacTypeResourceFactory implements Factory {

	public static final FordiacTypeResourceFactory INSTANCE = new FordiacTypeResourceFactory();

	@Override
	public LibraryElementResource createResource(final URI uri) {
		final String ext = uri.fileExtension();
		return switch (ext != null ? ext.toUpperCase() : "") { //$NON-NLS-1$
		case TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING -> new AdapterTypeResourceImpl(uri);
		case TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING -> new AttributeTypeResourceImpl(uri);
		case TypeLibraryTags.DATA_TYPE_FILE_ENDING -> new DataTypeResourceImpl(uri);
		case TypeLibraryTags.DEVICE_TYPE_FILE_ENDING -> new DeviceTypeResourceImpl(uri);
		case TypeLibraryTags.FC_TYPE_FILE_ENDING -> new FunctionTypeResourceImpl(uri);
		case TypeLibraryTags.FB_TYPE_FILE_ENDING -> new FBTypeResourceImpl(uri);
		case TypeLibraryTags.GLOBAL_CONST_FILE_ENDING -> new GlobalConstTypeResourceImpl(uri);
		case TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING -> new ResourceTypeResourceImpl(uri);
		case TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING -> new SegmentTypeResourceImpl(uri);
		case TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING -> new SubappTypeResourceImpl(uri);
		case TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING -> new SystemResourceImpl(uri);
		default ->
			throw new IllegalArgumentException(MessageFormat.format(Messages.FordiacTypeResFactory_URINoTypeFile, uri));
		};
	}
}
