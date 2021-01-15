/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2014, 2017 Profactor GbmH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved openEditor helper function to EditorUtils
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.ui.Activator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

/**
 * The Class OpenListenerManager.
 */
public enum OpenListenerManager {
	INSTANCE;

	private List<IOpenListener> openListeners = null;

	List<IOpenListener> getOpenListeners() {
		if (null == openListeners) {
			loadOpenListeners();
		}
		return openListeners;
	}

	/**
	 * Gets the open listener.
	 *
	 * @param elementToOpen the element to open
	 *
	 * @return the open listener
	 */
	public List<IOpenListener> getOpenListener(final EObject elementToOpen) {
		final ArrayList<IOpenListener> listeners = new ArrayList<>();
		for (final IOpenListener openListener : getOpenListeners()) {
			if (listenerSupportsElement(openListener, elementToOpen)) {
				openListener.selectionChanged(null, new StructuredSelection(elementToOpen));
				listeners.add(openListener);
			}
		}
		return listeners;
	}

	/**
	 * Sets the default open listener.
	 *
	 * @param libElement the lib element
	 * @param id         the id
	 */
	public static void setDefaultOpenListener(final Class<? extends EObject> libElement, final String id) {
		final IPreferenceStore ps = Activator.getDefault().getPreferenceStore();
		ps.setValue(libElement.getName(), id);
	}

	/**
	 * Gets the default open listener.
	 *
	 * @param libElement    the lib element
	 * @param elementToOpen the element to open
	 *
	 * @return the default open listener
	 */
	public IOpenListener getDefaultOpenListener(final EObject elementToOpen) {
		final IPreferenceStore ps = Activator.getDefault().getPreferenceStore();
		for (final IOpenListener openListener : getOpenListeners()) {
			if (listenerSupportsElement(openListener, elementToOpen)) {
				final String value = ps.getString(openListener.getHandledClass().getName());
				openListener.selectionChanged(null, new StructuredSelection(elementToOpen));
				if ("".equals(value)) { //$NON-NLS-1$
					return openListener;
				} else if (value.equals(openListener.getOpenListenerID())) {
					return openListener;
				}
			}
		}
		return null;
	}

	/**
	 * Opens the Editor for the specified application
	 *
	 * @param app the application which should be opened
	 * @return the Editor if opening worked otherwise null
	 */
	public static IEditorPart openEditor(final EObject element) {
		final IOpenListener openListener = OpenListenerManager.INSTANCE.getDefaultOpenListener(element);
		if (openListener != null) {
			openListener.run(null);
			return openListener.getOpenedEditor();
		}
		return null;
	}

	private void loadOpenListeners() {
		openListeners = new ArrayList<>();
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elems = registry
				.getConfigurationElementsFor(Activator.PLUGIN_ID, "openListener"); //$NON-NLS-1$
		for (final IConfigurationElement element : elems) {
			try {
				final Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IOpenListener) {
					openListeners.add((IOpenListener) object);
				}
			} catch (final CoreException corex) {
				Activator.getDefault().getLog().error(corex.getMessage(), corex);
			}
		}

	}

	private static boolean listenerSupportsElement(final IOpenListener listener, final EObject elementtoOpen) {
		return (null != listener.getHandledClass()) && listener.getHandledClass().isInstance(elementtoOpen);
	}


}
