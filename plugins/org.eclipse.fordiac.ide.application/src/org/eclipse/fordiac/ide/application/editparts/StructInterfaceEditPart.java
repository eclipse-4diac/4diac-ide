/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.fordiac.ide.application.policies.VariableNodeEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;

/**
 * The edit part for struct manipulator interface elements shown in FBNetwork
 * editors
 */
public class StructInterfaceEditPart extends InterfaceEditPart {
	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		if (isVariable()) {
			return new VariableNodeEditPolicy();
		}
		return null;
	}
}
