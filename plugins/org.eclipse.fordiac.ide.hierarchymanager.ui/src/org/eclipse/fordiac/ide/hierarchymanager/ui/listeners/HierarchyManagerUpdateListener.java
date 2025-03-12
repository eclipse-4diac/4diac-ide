/*******************************************************************************
 * Copyright (c) 2024 Primetals Technology Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.ui.handlers.AbstractHierarchyHandler;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.AbstractChangeHierarchyOperation;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.AddLeafOperation;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.DeleteNodeOperation;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.UpdateLeafRefOperation;
import org.eclipse.fordiac.ide.hierarchymanager.ui.util.HierarchyManagerRefactoringUtil;
import org.eclipse.fordiac.ide.hierarchymanager.ui.util.HierarchyManagerUtil;
import org.eclipse.fordiac.ide.model.commands.QualNameChange;
import org.eclipse.fordiac.ide.model.commands.QualNameChangeListener;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class HierarchyManagerUpdateListener extends QualNameChangeListener {

	private final HashMap<String, List<AbstractOperation>> undoDeleteOperations = new HashMap<>();

	@Override
	public List<AbstractOperation> constructExecutableUndoOperations(final QualNameChange change, final Object o) {
		return constructOperation(change, o, true);
	}

	@Override
	protected List<AbstractOperation> constructExecutableOperations(final QualNameChange qualNameChange,
			final Object rootLevel) {
		return constructOperation(qualNameChange, rootLevel, false);
	}

	protected List<AbstractOperation> constructOperation(final QualNameChange qualNameChange, final Object rootLevel,
			final boolean isUndo) {

		if (qualNameChange.state() == QualNameChangeState.DELETE_UNDO) {
			return undoDeleteOperations.remove(qualNameChange.oldQualName());
		}

		final String identifier = isUndo ? qualNameChange.newQualName() : qualNameChange.oldQualName();

		final List<AbstractOperation> result = new ArrayList<>();
		final List<Leaf> leafs = HierarchyManagerUtil.searchLeaf((RootLevel) rootLevel,
				leaf -> leaf.getRef().contains(identifier));

		if (leafs == null || leafs.isEmpty()) {
			return Collections.emptyList(); // leaf may have been deleted in the meantime
		}

		final String newRef = isUndo ? qualNameChange.oldQualName() : qualNameChange.newQualName();

		for (final Leaf leaf : leafs) {
			if (qualNameChange.state() == QualNameChangeState.DELETE
					|| qualNameChange.state() == QualNameChangeState.DELETE_REDO) {
				result.add(new DeleteNodeOperation(leaf));
				storeUndoDeleteOperations(qualNameChange.oldQualName(), leaf);
			} else {
				result.add(new UpdateLeafRefOperation(leaf, newRef, identifier));
			}
		}

		return result;
	}

	@Override
	protected Object getReceiver(final TypeEntry key) {
		final IProject project = key.getFile().getProject();
		if (!HierarchyManagerRefactoringUtil.plantHierachyExists(project)) {
			return null;
		}

		return HierarchyManagerRefactoringUtil.getPlantHierarchy(project);
	}

	@Override
	protected void executeOperation(final AbstractOperation op) {
		AbstractHierarchyHandler.executeOperation((AbstractChangeHierarchyOperation) op);
	}

	@Override
	protected boolean isEnabled(final INamedElement element) {
		return element instanceof UntypedSubApp;
	}

	protected void storeUndoDeleteOperations(final String qualName, final Leaf leaf) {
		final AddLeafOperation op = new AddLeafOperation((Level) leaf.eContainer(), leaf);
		undoDeleteOperations.computeIfAbsent(qualName, k -> new ArrayList<>()).add(op);
	}

}
