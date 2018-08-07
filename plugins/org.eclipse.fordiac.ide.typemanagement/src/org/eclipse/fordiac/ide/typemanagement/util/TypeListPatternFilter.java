/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
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
package org.eclipse.fordiac.ide.typemanagement.util;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.provider.TransientBasicFBTypeListItemProvider;
import org.eclipse.fordiac.ide.model.libraryElement.provider.TransientInterfaceListItemProvider;
import org.eclipse.fordiac.ide.model.libraryElement.provider.TransientLibraryElementItemProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.dialogs.PatternFilter;

public class TypeListPatternFilter extends PatternFilter {
	
	public TypeListPatternFilter() {
		super();
		setIncludeLeadingWildcard(true);
	}

	@Override
	protected boolean isParentMatch(Viewer viewer, Object element){
		//prevent children filtering if is a file, avoids type loading on filtering
		if((element instanceof IFolder) ||
				(element instanceof IProject)){
			return super.isParentMatch(viewer, element);
		}
		
		return false;
	}
	
	@Override
	protected boolean isLeafMatch(Viewer viewer, Object element){
		if((element instanceof EObject) ||
		   (element instanceof TransientInterfaceListItemProvider) ||
		   (element instanceof TransientBasicFBTypeListItemProvider) ||
		   (element instanceof TransientLibraryElementItemProvider)){
			//do not filter on type content
			return true;
		}
		//TODO add also matching for other elements e.g., description ev. in is parent Match einbauen
		return super.isLeafMatch(viewer, element);
	}

}
