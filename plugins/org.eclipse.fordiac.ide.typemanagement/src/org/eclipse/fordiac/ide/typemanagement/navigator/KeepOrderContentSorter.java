/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/** A simple content sorter that will keep the order as given to it.
 * 
 * For the type navigator it needs to be a ViewerSorter!!
 */
@SuppressWarnings("deprecation")
public class KeepOrderContentSorter extends ViewerSorter {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		//we want to keep the original order for resources
		return 1;
	}

	@Override
	public void sort(Viewer viewer, Object[] elements) {
		//wehn we do nothing here the order as stored in the model should be provided. 
		//This is what we want for resouces.
	}

	

}
