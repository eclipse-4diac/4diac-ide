/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;


/** A sorter that will ensure that under system content the order is as follows:
 *   1. Applications
 *   2. System configuration
 *   3. Rest
 *  
 * Applications will be sorted alphabetically.
 */
@SuppressWarnings("deprecation")  //Currently the common navigator framework requires to use ViewerSorter
public class SystemContentSorter extends ViewerSorter {
	
	@Override
    public int compare(Viewer viewer, Object e1, Object e2) {
		int retval = 0;
		
		if(e1 instanceof Application){
			if(e2 instanceof Application){
				Application app1 = (Application)e1;
				Application app2 = (Application)e2;
				retval = app1.getName().compareTo(app2.getName());
			}
		}else if (e1 instanceof SystemConfiguration){
			retval = (e2 instanceof Application) ? 1 : -1;
		}
		
		return retval;
	}

}
