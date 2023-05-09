/*******************************************************************************
 * Copyright (c) 2023 Primetals Technology Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.operations;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;

public abstract class AbstractChangeHierarchyOperation extends AbstractOperation {

	public AbstractChangeHierarchyOperation(final String label) {
		super(label);
	}

	protected void saveHierarchy(final EObject node, final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

}