/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.delete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class RootNodeChange extends CompositeChange {
	private final List<Change> updateChanges = new ArrayList<>();

	public RootNodeChange(final EObject node) {
		super(Messages.SafeStructDeletionChange_RootNodeChangeText + getName(node) + getFileEnding(node));
	}

	private static String getFileEnding(final EObject node) {
		if (node instanceof final LibraryElement le && le.getTypeEntry() != null) {
			return "." + le.getTypeEntry().getFile().getFileExtension();
		}
		return null;
	}

	private static String getName(final EObject eObj) {
		INamedElement node = null;
		if (!(eObj instanceof final INamedElement n)) {
			return "";
		}
		node = n;

		if (node instanceof final IInterfaceElement iel && iel.getFBNetworkElement() != null) {
			return iel.getFBNetworkElement().getQualifiedName() + iel.getQualifiedName();
		}
		if (node instanceof VarDeclaration && node.eContainer() instanceof final StructuredType struct) {
			return struct.getName() + "." + node.getName(); //$NON-NLS-1$
		}
		return node.getQualifiedName();
	}

	public void addUpdate(final Change change) {
		updateChanges.add(change);
	}

	@Override
	public Change[] getChildren() {
		final ArrayList<Change> list = new ArrayList<>();
		list.addAll(Arrays.asList(super.getChildren()));
		list.addAll(updateChanges);
		return list.toArray(new Change[0]);
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		// add update changes to the end of the list for correct execution order
		addAll(updateChanges.toArray(new Change[0]));
		return super.perform(pm);
	}
}
