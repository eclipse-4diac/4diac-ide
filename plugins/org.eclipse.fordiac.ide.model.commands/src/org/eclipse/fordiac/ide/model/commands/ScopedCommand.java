/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.commands;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

public interface ScopedCommand {

	/**
	 * Get the set of objects affected by this command.
	 *
	 * @return The set of objects
	 */
	Set<EObject> getAffectedObjects();
}
