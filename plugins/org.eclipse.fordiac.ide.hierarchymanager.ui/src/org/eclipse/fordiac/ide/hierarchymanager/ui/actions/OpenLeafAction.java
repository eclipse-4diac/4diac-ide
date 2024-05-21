/*******************************************************************************
 * Copyright (c) 2020, 2023 Primetals Technology Austria GmbH,
 * 							Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - the searching for ref elements is taken from an old version of
 *                 AutomationSystemEditor where I did a similar search for
 *                 book marks
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.ui.util.HierarchyManagerUtil;
import org.eclipse.fordiac.ide.hierarchymanager.ui.view.PlantHierarchyView;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

public class OpenLeafAction extends BaseSelectionListenerAction {

	public static final String OPEN_LEAF_ACTION_ID = "org.eclipse.fordiac.ide.hierarchymanager.ui.openLeafAction";//$NON-NLS-1$

	final PlantHierarchyView phView;

	public OpenLeafAction(final PlantHierarchyView phView) {
		super(Messages.OpenEditorAction_text);
		setId(OPEN_LEAF_ACTION_ID);
		this.phView = phView;
	}

	@Override
	protected boolean updateSelection(final IStructuredSelection selection) {
		if ((getProject() == null) || (selection.size() != 1)) {
			return false;
		}
		final Object model = getStructuredSelection().getFirstElement();
		return (model instanceof Leaf);
	}

	@Override
	public void run() {
		if (getStructuredSelection().getFirstElement() instanceof final Leaf leaf) {
			final EObject refObj = HierarchyManagerUtil.getElementReferencedbyLeaf(leaf, getProject());
			OpenListenerManager.openEditor(refObj);
		}
	}

	private IProject getProject() {
		return phView.getCurrentProject();
	}

}
