/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query.editparts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;

/** Connection from a query element to one of its contained elements. */
public record QueryConnection(EObject source, EObject target) {

	public String getLabel() {
		final EReference containment = target.eContainmentFeature();
		if (containment == QueryPackage.Literals.CONSTRAINT__AND_CONSTRAINT) {
			return "AND"; //$NON-NLS-1$
		}
		if (containment == QueryPackage.Literals.CONSTRAINT__OR_CONSTRAINT) {
			return "OR"; //$NON-NLS-1$
		}
		return null;
	}
}
