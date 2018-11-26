/*******************************************************************************
 * Copyright (c) 2014, 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.subapptypeeditor.editors;

import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBInterfaceEditor;
import org.eclipse.fordiac.ide.subapptypeeditor.editparts.SubAppInterfaceEditPartFactory;
import org.eclipse.gef.EditPartFactory;

public class SubAppInterfaceEditor extends FBInterfaceEditor{

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new SubAppInterfaceEditPartFactory(this, palette, getZoomManger());
	}
}
