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
package org.eclipse.fordiac.ide.bulkeditor.query.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.commands.DeleteQueryElementCommand;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/** Deletes query elements except the query and its mandatory elements. */
public class DeleteQueryNodeEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(final GroupRequest deleteRequest) {
		final EObject element = (EObject) getHost().getModel();
		if (element.eResource() == null || element.eContainer() == null || QueryModelHelper.isMandatoryChild(element)) {
			return null;
		}
		return new DeleteQueryElementCommand(element);
	}
}
