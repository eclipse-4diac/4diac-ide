/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber - altered for instance comment
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class InstanceComment {

	private final INamedElement refElement;

	public InstanceComment(final INamedElement refElement) {
		this.refElement = refElement;
	}

	public INamedElement getRefElement() {
		return refElement;
	}

	public String getInstanceComment() {
		return getRefElement().getComment();
	}
}
