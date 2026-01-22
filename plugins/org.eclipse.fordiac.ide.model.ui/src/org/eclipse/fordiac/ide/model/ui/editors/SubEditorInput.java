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

import java.util.Objects;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;

public class SubEditorInput extends PlatformObject implements ISubEditorInput, IPersistableElement {

	private final IEditorInput parent;
	private final EClass elementClass;
	private final String fragment;

	public SubEditorInput(final IEditorInput parent, final EClass elementClass, final String fragment) {
		this.parent = Objects.requireNonNull(parent);
		this.elementClass = Objects.requireNonNull(elementClass);
		this.fragment = Objects.requireNonNull(fragment);
	}

	public SubEditorInput(final IEditorInput parent, final EObject subElement) {
		this(parent, subElement.eClass(), getFragment(subElement));
	}

	@Override
	public IEditorInput getParent() {
		return parent;
	}

	@Override
	public EClass getElementClass() {
		return elementClass;
	}

	@Override
	public String getFragment() {
		return fragment;
	}

	@Override
	public String getName() {
		return parent.getName() + ":" + fragment; //$NON-NLS-1$
	}

	@Override
	public String getToolTipText() {
		return parent.getToolTipText() + ":" + fragment; //$NON-NLS-1$
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return parent.getImageDescriptor();
	}

	@Override
	public boolean exists() {
		return parent.exists();
	}

	@Override
	public int hashCode() {
		return Objects.hash(elementClass, fragment, parent);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SubEditorInput other = (SubEditorInput) obj;
		return Objects.equals(elementClass, other.elementClass) && Objects.equals(fragment, other.fragment)
				&& Objects.equals(parent, other.parent);
	}

	@Override
	public String toString() {
		return String.format("%s [%s:%s, elementClass=%s]", getClass().getName(), parent, fragment, //$NON-NLS-1$
				elementClass.getName());
	}

	@Override
	public IPersistableElement getPersistable() {
		return getParent().getPersistable() != null ? this : null;
	}

	@Override
	public void saveState(final IMemento memento) {
		SubEditorInputFactory.saveState(memento, this);
	}

	@Override
	public String getFactoryId() {
		return SubEditorInputFactory.ID;
	}

	protected static String getFragment(final EObject subElement) {
		if (subElement instanceof final INamedElement namedElement) {
			return namedElement.getQualifiedName();
		}
		return EcoreUtil.getURI(subElement).fragment();
	}
}
