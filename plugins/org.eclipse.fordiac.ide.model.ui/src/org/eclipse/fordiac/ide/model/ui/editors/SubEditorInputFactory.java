/*******************************************************************************
 * Copyright (c) 2025, 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PlatformUI;

public class SubEditorInputFactory implements IElementFactory {

	public static final String ID = "org.eclipse.fordiac.ide.model.ui.editors.SubEditorInputFactory"; //$NON-NLS-1$

	private static final String TAG_EDITOR_INPUT = "editorInput"; //$NON-NLS-1$
	private static final String TAG_FACTORY_ID = "factoryID"; //$NON-NLS-1$
	private static final String TAG_ELEMENT_CLASS = "elementClass"; //$NON-NLS-1$
	private static final String TAG_FRAGMENT = "fragment"; //$NON-NLS-1$

	@Override
	public IAdaptable createElement(final IMemento memento) {
		final String factoryId = memento.getString(TAG_FACTORY_ID);
		if (factoryId == null) {
			return null;
		}
		final IElementFactory factory = PlatformUI.getWorkbench().getElementFactory(factoryId);
		if (factory == null) {
			return null;
		}
		final IMemento editorInputChild = memento.getChild(TAG_EDITOR_INPUT);
		if (editorInputChild == null) {
			return null;
		}
		final IAdaptable adaptable = factory.createElement(editorInputChild);
		if (!(adaptable instanceof final IEditorInput editorInput)) {
			return null;
		}
		final String fragment = memento.getString(TAG_FRAGMENT);
		if (fragment == null) {
			return null;
		}
		final EClass elementClass = getElementClass(memento);
		if (elementClass == null) {
			return null;
		}
		return new SubEditorInput(editorInput, elementClass, fragment);
	}

	public static void saveState(final IMemento memento, final SubEditorInput input) {
		final IPersistableElement persistable = input.getParent().getPersistable();
		persistable.saveState(memento.createChild(TAG_EDITOR_INPUT));
		memento.putString(TAG_FACTORY_ID, persistable.getFactoryId());
		memento.putString(TAG_ELEMENT_CLASS, EcoreUtil.getURI(input.getElementClass()).toString());
		memento.putString(TAG_FRAGMENT, input.getFragment());
	}

	private static EClass getElementClass(final IMemento memento) {
		final String elementClassString = memento.getString(TAG_ELEMENT_CLASS);
		if (elementClassString != null) {
			final URI elementClassURI = URI.createURI(elementClassString);
			final EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(elementClassURI.trimFragment().toString());
			if (ePackage != null) {
				final Resource eResource = ePackage.eResource();
				if (eResource != null) {
					return (EClass) eResource.getEObject(elementClassURI.fragment());
				}
			}
		}
		return null;
	}
}
