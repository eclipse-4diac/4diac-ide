/*******************************************************************************
 * Copyright (c) 2008, 2021 Profactor GbmH, fortiss GmbH,Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH
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
 *               - added code to handle opening of types and checking their
 *                 breadcrumb
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.ui.editors.AbstractBreadCrumbEditor;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * The Class OpenListenerManager.
 */
public enum OpenListenerManager {
	INSTANCE;

	private static final String PLUGIN_ID = "org.eclipse.fordiac.ide.model.ui"; //$NON-NLS-1$

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
		final IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(PLUGIN_ID);
		preferences.put(libElement.getName(), id);
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
		final IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(PLUGIN_ID);
		for (final IOpenListener openListener : getOpenListeners()) {
			if (listenerSupportsElement(openListener, elementToOpen)) {
				final String value = preferences.get(openListener.getHandledClass().getName(), ""); //$NON-NLS-1$
				openListener.selectionChanged(null, new StructuredSelection(elementToOpen));
				if (("".equals(value)) //$NON-NLS-1$
						|| (value.equals(openListener.getOpenListenerID()))) {
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
		if (element instanceof final LibraryElement libEl) {
			return openDefaultEditorForFile(libEl);
		}
		return null;
	}

	static IEditorPart openDefaultEditorForFile(final LibraryElement element) {
		final TypeEntry entry = element.getTypeEntry();
		if (null != entry) {
			final IFile file = entry.getFile();
			final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
					.getDefaultEditor(file.getName());
			try {
				final IEditorPart part = page.openEditor(new FileEditorInput(file), desc.getId());
				checkBreadCrumb(part, element);
				return part;
			} catch (final PartInitException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
		return null;
	}

	static void checkBreadCrumb(final IEditorPart part, final LibraryElement element) {
		if (null != part) {
			final AbstractBreadCrumbEditor breadCrumbEditor = part.getAdapter(AbstractBreadCrumbEditor.class);
			if (null != breadCrumbEditor) {
				breadCrumbEditor.getBreadcrumb().setInput(element);
			}
		}

	}

	private void loadOpenListeners() {
		openListeners = new ArrayList<>();
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elems = registry.getConfigurationElementsFor(PLUGIN_ID, "openListener"); //$NON-NLS-1$
		for (final IConfigurationElement element : elems) {
			try {
				final Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof final IOpenListener openListener) {
					openListeners.add(openListener);
				}
			} catch (final CoreException corex) {
				FordiacLogHelper.logError(corex.getMessage(), corex);
			}
		}

	}

	private static boolean listenerSupportsElement(final IOpenListener listener, final EObject elementtoOpen) {
		return (null != listener.getHandledClass()) && listener.getHandledClass().isInstance(elementtoOpen);
	}

}
