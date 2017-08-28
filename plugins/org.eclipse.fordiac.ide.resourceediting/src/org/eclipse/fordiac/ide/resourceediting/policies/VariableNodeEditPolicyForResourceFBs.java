/*******************************************************************************
 * Copyright (c) 2008, 2010, 2012 - 2014, 2016 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.policies;

import org.eclipse.fordiac.ide.application.policies.VariableNodeEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.resourceediting.editparts.FBNetworkContainerEditPart;
import org.eclipse.gef.EditPart;

/**
 * An EditPolicy which allows drawing Connections between FBs and/or SubApps.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class VariableNodeEditPolicyForResourceFBs extends VariableNodeEditPolicy {
	
	@Override
	protected FBNetwork getParentNetwork() {
		EditPart parent = getHost().getParent();
		while (parent != null && !(parent instanceof FBNetworkContainerEditPart)) {
			parent = parent.getParent();
		}
		if (parent instanceof FBNetworkContainerEditPart) { 
			// also means that parent != null
			return ((FBNetworkContainerEditPart) parent).getModel();
		}
		return null;
	}

}
