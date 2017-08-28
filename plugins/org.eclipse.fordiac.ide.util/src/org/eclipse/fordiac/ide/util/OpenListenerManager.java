/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2014 Profactor GbmH, fortiss GmbH
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
package org.eclipse.fordiac.ide.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

/**
 * The Class OpenListenerManager.
 */
public class OpenListenerManager {

	private static OpenListenerManager instance;

	/**
	 * Gets the single instance of OpenListenerManager.
	 * 
	 * @return single instance of OpenListenerManager
	 */
	public static OpenListenerManager getInstance() {
		if (instance == null) {
			instance = new OpenListenerManager();
		}
		return instance;
	}

	private OpenListenerManager() {

	}

	/**
	 * Gets the open listener.
	 * 
	 * @param libElement the lib element
	 * @param elementToOpen the element to open
	 * 
	 * @return the open listener
	 */
	public List<IOpenListener> getOpenListener(
			final Class<? extends I4DIACElement> libElement,
			final Object elementToOpen) {
		ArrayList<IOpenListener> listener = new ArrayList<IOpenListener>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				org.eclipse.fordiac.ide.util.Activator.PLUGIN_ID, "openListener"); //$NON-NLS-1$
		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IOpenListener) {
					IOpenListener openListener = (IOpenListener) object;
					if (openListener.supportsObject(libElement)) {
						openListener.selectionChanged(null, new StructuredSelection(
								elementToOpen));

						listener.add(openListener);
					}
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError(corex.getMessage(), corex);
			}
		}
		return listener;
	}

	/**
	 * Gets the open listener.
	 * 
	 * @param libElement the lib element
	 * 
	 * @return the open listener
	 */
	public List<String> getOpenListenerID(
			final Class<? extends I4DIACElement> libElement) {
		ArrayList<String> listener = new ArrayList<String>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				org.eclipse.fordiac.ide.util.Activator.PLUGIN_ID, "openListener"); //$NON-NLS-1$
		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IOpenListener) {
					IOpenListener openListener = (IOpenListener) object;
					if (openListener.supportsObject(libElement)) {
						listener.add(element.getAttribute("id"));
					}
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError(corex.getMessage(), corex);
			}
		}
		return listener;
	}

	/**
	 * Sets the default open listener.
	 * 
	 * @param libElement the lib element
	 * @param id the id
	 */
	public void setDefaultOpenListener(
			final Class<? extends I4DIACElement> libElement, final String id) {
		IPreferenceStore ps = Activator.getDefault().getPreferenceStore();

		ps.setValue(libElement.getName(), id);

	}

	/**
	 * Gets the default open listener.
	 * 
	 * @param libElement the lib element
	 * @param elementToOpen the element to open
	 * 
	 * @return the default open listener
	 */
	public IOpenListener getDefaultOpenListener(
			final Class<? extends I4DIACElement> libElement,
			final Object elementToOpen) {

		IPreferenceStore ps = Activator.getDefault().getPreferenceStore();

		String value = ps.getString(libElement.getName());

		Hashtable<IOpenListener, String> listener = new Hashtable<IOpenListener, String>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				org.eclipse.fordiac.ide.util.Activator.PLUGIN_ID, "openListener"); //$NON-NLS-1$
		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IOpenListener) {
					IOpenListener openListener = (IOpenListener) object;
					if (openListener.supportsObject(libElement)) {
						openListener.selectionChanged(null, new StructuredSelection(
								elementToOpen));
						if ("".equals(value)) {
							return openListener;
						} else if (value.equals(element.getAttribute("id"))) {
							return openListener;
						}
						listener.put(openListener, element.getAttribute("id"));
					}
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError(corex.getMessage(), corex);
			}
		}

		return null;
	}
	
	/**
	 * Opens the Editor for the specified application
	 * 
	 * @param app
	 *            the application which should be opened
	 * @return the Editor if opening worked otherwise null
	 */
	public static IEditorPart openEditor(I4DIACElement element) {
		IOpenListener openListener = OpenListenerManager.getInstance()
				.getDefaultOpenListener(element.getClass(), element);
		if (openListener != null) {
			openListener.run(null);
			return openListener.getOpenedEditor();
		}
		return null;
	}
}
