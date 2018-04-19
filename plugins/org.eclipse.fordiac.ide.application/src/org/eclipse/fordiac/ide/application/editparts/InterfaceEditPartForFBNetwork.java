/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Waldemar Eisenmenger, Monika Wenger 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.fordiac.ide.application.policies.AdapterNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.EventNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.VariableNodeEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;

/** The edit part for interface elements of FBs and Subapps shown in FBNetwork editors
 */
public class InterfaceEditPartForFBNetwork extends InterfaceEditPart {
	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		if (isEvent()) {
			return new EventNodeEditPolicy();
		}
		if(isAdapter()){
			return new AdapterNodeEditPolicy();
		}
		if (isVariable()) {
			return new VariableNodeEditPolicy();
		}
		return null;
	}
}
