/********************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2014, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.dataimport.IDeviceTypeImporter;

/**
 * The Class DEVTypeLibrary.
 * 
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 */
public final class DEVTypeLibrary {
	/**
	 * Instantiates a new dEV type library.
	 */
	private DEVTypeLibrary() {
		// empty constructor
	}

	/** The instance. */
	private static DEVTypeLibrary instance;

	/**
	 * Gets the single instance of DEVTypeLibrary.
	 * 
	 * @return single instance of DEVTypeLibrary
	 */
	public static DEVTypeLibrary getInstance() {
		if (instance == null) {
			instance = new DEVTypeLibrary();
		}
		return instance;
	}

	

	/**
	 * Gets the device type importer.
	 * 
	 * @param type the type
	 * 
	 * @return the device type importer
	 */
	public IDeviceTypeImporter getDeviceTypeImporter(final String type) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		ArrayList<IDeviceTypeImporter> typeImporter = new ArrayList<IDeviceTypeImporter>();

		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				org.eclipse.fordiac.ide.model.Activator.PLUGIN_ID, "deviceTypeLoader"); //$NON-NLS-1$
		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IDeviceTypeImporter) {
					IDeviceTypeImporter iTypeImporter = (IDeviceTypeImporter) object;
					if (iTypeImporter.supportsType(type)) {
						typeImporter.add(iTypeImporter);
					}
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError(corex.getMessage(), corex);
			}
		}

		if (typeImporter.size() > 0) {
			return typeImporter.get(0);
		}
		return null;
	}
}
