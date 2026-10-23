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
package org.eclipse.fordiac.ide.bulkeditor.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

/** Changes the value of a feature of a query element. */
public class ChangeQueryFeatureCommand extends EMFCommandWrapper {

	public ChangeQueryFeatureCommand(final EObject element, final String featureName, final Object value) {
		super(SetCommand.create(AdapterFactoryEditingDomain.getEditingDomainFor(element), element,
				element.eClass().getEStructuralFeature(featureName), value));
	}
}
