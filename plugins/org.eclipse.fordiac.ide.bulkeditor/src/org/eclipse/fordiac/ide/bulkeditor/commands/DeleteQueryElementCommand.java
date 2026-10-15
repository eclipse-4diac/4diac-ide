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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

/** Removes an element from a bulk editor query. */
public class DeleteQueryElementCommand extends EMFCommandWrapper {

	public DeleteQueryElementCommand(final EObject element) {
		super(createRemoveCommand(element));
	}

	private static Command createRemoveCommand(final EObject element) {
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
		final EReference containment = element.eContainmentFeature();
		if (containment != null && !containment.isMany()) {
			return SetCommand.create(editingDomain, element.eContainer(), containment, SetCommand.UNSET_VALUE);
		}
		return RemoveCommand.create(editingDomain, element);
	}
}
