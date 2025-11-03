/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.copy;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.fordiac.ide.typemanagement.refactoring.copy.FordiacCopyProcessor.ExistsResolve;

public interface ICopyRefactoringQueries {

	/**
	 * query how a resource copy should be handled when the destination container is
	 * the same as the origin container
	 *
	 * @param resource    the resource to be copied
	 * @param destination the destination of the copy
	 * @return an enum stating how the situation should be handled
	 */
	ExistsResolve queryOverwriteSelf(final IResource resource, final IContainer destination);

	/**
	 * query how a resource copy should be handled when a resource with the same
	 * name already exists at the destination
	 *
	 * @param resource    the resource to be copied
	 * @param destination the destination of the copy
	 * @return an enum stating how the situation should be handled
	 */
	ExistsResolve queryOverwrite(final IResource resource, final IContainer destination);
}
