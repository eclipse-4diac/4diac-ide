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
import java.util.Map;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.util.HierarchyResourceFactoryImpl;
import org.eclipse.fordiac.ide.hierarchymanager.ui.handlers.AbstractHierarchyHandler;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.AbstractChangeHierarchyOperation;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.UpdateLeafRefOperation;
import org.eclipse.fordiac.ide.hierarchymanager.ui.util.HierarchyManagerUtil;
import org.eclipse.fordiac.ide.hierarchymanager.ui.view.PlantHierarchyView;
import org.eclipse.fordiac.ide.model.commands.QualNameChange;
import org.eclipse.fordiac.ide.model.commands.QualNameChangeListener;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class HierachyManagerUpdateListener extends QualNameChangeListener {

	private static EObject loadPlantHierachy(final IProject project) {
		final Map<String, Object> loadOptions = new HashMap<>();
		final ResourceSet hierarchyResouceSet = new ResourceSetImpl();
		hierarchyResouceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
				PlantHierarchyView.PLANT_HIERARCHY_FILE_NAME_EXTENSION, //
				new HierarchyResourceFactoryImpl());
		hierarchyResouceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( //
				PlantHierarchyView.PLANT_HIERARCHY_FILE_NAME_EXTENSION.toLowerCase(), //
				new HierarchyResourceFactoryImpl());
		loadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		final XMLMapImpl map = new XMLMapImpl();
		map.setNoNamespacePackage(HierarchyPackage.eINSTANCE);
		loadOptions.put(XMLResource.OPTION_XML_MAP, map);
		hierarchyResouceSet.getLoadOptions().put(XMLResource.OPTION_XML_MAP, map);
		return PlantHierarchyView.loadHierachyForProject(project, hierarchyResouceSet, loadOptions);
	}

	@Override
	public List<AbstractOperation> constructExecutableUndoOperations(final QualNameChange change, final Object o) {
		return constructOperation(change, o, true);
	}

	@Override
	protected List<AbstractOperation> constructExecutableOperations(final QualNameChange qualNameChange,
			final Object rootLevel) {

		return constructOperation(qualNameChange, rootLevel, false);
	}

	protected static List<AbstractOperation> constructOperation(final QualNameChange qualNameChange,
			final Object rootLevel, final boolean isUndo) {
		final String identifier = isUndo ? qualNameChange.newQualName() : qualNameChange.oldQualName();

		final List<AbstractOperation> result = new ArrayList<>();

		final List<Leaf> leafs = HierarchyManagerUtil.searchLeaf((RootLevel) rootLevel,
				leafRef -> leafRef.contains(identifier));
		if (leafs == null || leafs.isEmpty()) {
			return Collections.emptyList(); // leaf may have been deleted in the meantime
		}

		final String newRef = isUndo ? qualNameChange.oldQualName() : qualNameChange.newQualName();

		for (final Leaf leaf : leafs) {
			result.add(new UpdateLeafRefOperation(leaf, newRef, identifier));
		}

		return result;
	}

	@Override
	protected Object getReceiver(final TypeEntry key) {
		return getPlantHierachy(key);
	}

	@Override
	protected void executeOperation(final AbstractOperation op) {
		AbstractHierarchyHandler.executeOperation((AbstractChangeHierarchyOperation) op);
	}

	private static RootLevel getPlantHierachy(final TypeEntry key) {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (page != null) {
			final PlantHierarchyView view = (PlantHierarchyView) page
					.findView("org.eclipse.fordiac.ide.hierarchymanager.view"); //$NON-NLS-1$
			if (view != null) {
				return (RootLevel) view.getCommonViewer().getInput();
			}
		}
		final IProject project = key.getFile().getProject();
		return (RootLevel) loadPlantHierachy(project);
	}

	@Override
	protected boolean isEnabled(final INamedElement element) {
		return element instanceof UntypedSubApp;
	}

}
