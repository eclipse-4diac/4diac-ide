/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;

public class DeleteFBTypeParticipant extends DeleteParticipant {

	List<String> typeNames = new ArrayList<>();
	
	@Override
	protected boolean initialize(Object element) {
		return (element instanceof IFile);
	}

	@Override
	public String getName() {
		return "Delete IEC 61499 Type";
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		ResourceChangeChecker resChecker = context.getChecker(ResourceChangeChecker.class);
		IResourceChangeDescriptionFactory deltaFactory = resChecker.getDeltaFactory();
		IResourceDelta[] affectedChildren = deltaFactory.getDelta().getAffectedChildren();

		return verifyAffectedChildren(affectedChildren);
	}
	
	private RefactoringStatus verifyAffectedChildren(IResourceDelta[] affectedChildren) {
		for (IResourceDelta resourceDelta : affectedChildren) {
			if(resourceDelta.getResource() instanceof IFile){
				Palette palette = SystemManager.INSTANCE.getPalette(resourceDelta.getResource().getProject());
				
				typeNames.clear();
				
				String typeNameToDelete = TypeLibrary.getTypeNameFromFile((IFile)resourceDelta.getResource());
				checkTypeContainment(palette.getRootGroup(), typeNameToDelete);
				
				if(!typeNames.isEmpty()){
					return RefactoringStatus.createWarningStatus("FB type " + typeNameToDelete + 
							" is used in the following types: " + typeNames.toString());
				}					
			}else{
				return verifyAffectedChildren(resourceDelta.getAffectedChildren());
			}
		}
		return new RefactoringStatus();
	}

	private void checkTypeContainment(PaletteGroup rootGroup,
			String searchTypeName) {
		
		for (PaletteEntry entry : rootGroup.getEntries()) {
			FBNetwork network = null;
			if(entry.getType() instanceof CompositeFBType){
				network = ((CompositeFBType)entry.getType()).getFBNetwork();
			}else if(entry.getType() instanceof ResourceType){
				network = ((ResourceType)entry.getType()).getFBNetwork();
			}else if(entry.getType() instanceof SubAppType){
				network = ((SubAppType)entry.getType()).getFBNetwork();
			}
			
			if(null != network && containsElementWithType(searchTypeName, network)){
				typeNames.add(entry.getLabel());
			}
		}
		
		for(PaletteGroup group: rootGroup.getSubGroups()){
			checkTypeContainment(group, searchTypeName);
		}
		
	}

	private static boolean containsElementWithType(String searchTypeName, FBNetwork network) {
		for (FBNetworkElement element : network.getNetworkElements()) {
			if(searchTypeName.equals(element.getTypeName())){
				return true;
			}
		}
		return false;
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return null;
	}

}
