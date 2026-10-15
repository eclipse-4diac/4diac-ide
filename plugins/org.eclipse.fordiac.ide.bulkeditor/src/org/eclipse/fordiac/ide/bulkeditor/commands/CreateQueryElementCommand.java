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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

/** Adds a new element of the given type to an element of a query. */
public class CreateQueryElementCommand extends EMFCommandWrapper {

	public CreateQueryElementCommand(final EObject parent, final EReference reference, final EClass type) {
		super(createAddCommand(parent, reference, type.getEPackage().getEFactoryInstance().create(type)));
	}

	private static Command createAddCommand(final EObject parent, final EReference reference, final EObject child) {
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(parent);
		if (reference.isMany()) {
			return AddCommand.create(editingDomain, parent, reference, child);
		}
		return SetCommand.create(editingDomain, parent, reference, child);
	}
}
