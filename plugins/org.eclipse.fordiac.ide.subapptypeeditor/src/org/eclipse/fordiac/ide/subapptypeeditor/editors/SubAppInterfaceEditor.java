/*******************************************************************************
 * Copyright (c) 2014, 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.subapptypeeditor.editors;

import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBInterfaceEditor;
import org.eclipse.fordiac.ide.subapptypeeditor.editparts.SubAppInterfaceEditPartFactory;
import org.eclipse.gef.EditPartFactory;

public class SubAppInterfaceEditor extends FBInterfaceEditor {

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new SubAppInterfaceEditPartFactory(this, getPalette(), getZoomManger());
	}
}
