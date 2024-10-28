/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.ui.annotation;

import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.fordiac.ide.deployment.debug.watch.IInterfaceElementWatch;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotation;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class WatchValueAnnotation extends GraphicalAnnotation {

	public static final String ANNOTATION_TYPE = "org.eclipse.fordiac.ide.deployment.debug.ui.annotation.watchValue"; //$NON-NLS-1$

	private final IInterfaceElement element;
	private final IInterfaceElementWatch watch;

	protected WatchValueAnnotation(final FBNetwork target, final IInterfaceElement element,
			final IInterfaceElementWatch watch) {
		super(ANNOTATION_TYPE, target);
		this.element = element;
		this.watch = watch;
	}

	public IInterfaceElement getElement() {
		return element;
	}

	public IInterfaceElementWatch getWatch() {
		return watch;
	}

	@Override
	public String getText() {
		try {
			return watch.getValue().getValueString();
		} catch (final DebugException e) {
			return e.getLocalizedMessage();
		}
	}

	@Override
	public String getLocation() {
		return ((INamedElement) getTarget()).getQualifiedName();
	}

	@Override
	public Object getAttribute(final String attributeName) {
		return null;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return Map.of();
	}
}
