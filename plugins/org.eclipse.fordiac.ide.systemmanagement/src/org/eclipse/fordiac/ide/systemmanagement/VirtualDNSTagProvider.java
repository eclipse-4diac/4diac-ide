/*******************************************************************************
 * Copyright (c) 2011, 2014 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSFactory;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement;
import org.eclipse.fordiac.ide.model.virtualDNS.util.VirtualDNSAdapterFactory;
import org.eclipse.fordiac.ide.systemmanagement.extension.ITagProvider;

public class VirtualDNSTagProvider implements ITagProvider {

	private static final String VIRTUAL_DNS_FILE_NAME = "virtualDNS.dns"; //$NON-NLS-1$
	private final ResourceSet virtualResSet;
	private VirtualDNSManagement virtualDNSManagement;

	/** The options. */
	private static Map<String, Object> options = new HashMap<String, Object>();
	/** The Constant ENCODING_UTF_8. */
	private static final String ENCODING_UTF_8 = "UTF-8";//$NON-NLS-1$

	
	public VirtualDNSTagProvider() {
		virtualResSet = new ResourceSetImpl();
		virtualResSet.getAdapterFactories().add(new VirtualDNSAdapterFactory());

		options.put(XMLResource.OPTION_ENCODING, ENCODING_UTF_8);
		options.put(XMLResource.OPTION_DISABLE_NOTIFY, true);
		// TODO check whether the following options are faster
		options.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
				Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF,
				XMLResource.OPTION_PROCESS_DANGLING_HREF_RECORD);
		// options.put(XMLResource.OPTION_ZIP, Boolean.TRUE)
	}

	
	@Override
	public boolean loadTagConfiguration(IPath loadPath) {
		IPath path = loadPath.append(VIRTUAL_DNS_FILE_NAME);
		if (path.toFile().exists()) {

			URI uri = URI.createFileURI(path.toOSString());
			Resource resource = null;
			resource = virtualResSet.getResource(uri, true);
			try {
				resource.load(options);
				EObject rootObject = resource.getContents().get(0);
				if (rootObject instanceof VirtualDNSManagement) {
					virtualDNSManagement = (VirtualDNSManagement) rootObject;
					return true;
				}
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean saveTagConfiguration(IPath savePath) {
		boolean ok = true;
		IPath path = savePath.append(VIRTUAL_DNS_FILE_NAME);
		URI uri = URI.createFileURI(path.toOSString());
		File modelfile = new File(path.toOSString());
		Resource resource = null;
		try {

			if (modelfile.exists()) {
				resource = virtualResSet.getResource(uri, true);
			} else {
				resource = virtualResSet.createResource(uri);
			}

			resource.getContents().clear();
			resource.getContents().add(virtualDNSManagement);
			resource.save(options);
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
			ok = false;
		}
		return ok;
	}


	@Override
	public Object getModelObject() {
		return virtualDNSManagement;
	}


	@Override
	public String getReplacedString(String value) {
		return virtualDNSManagement.getReplacedString(value);
	}


	@Override
	public void initialzeNewTagProvider() {
		if (virtualDNSManagement == null) {
			virtualDNSManagement = VirtualDNSFactory.eINSTANCE.createVirtualDNSManagement();
		}
	}

}
