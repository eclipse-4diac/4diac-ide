/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester;

import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.ISetValueListener;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.ITriggerEventListener;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.swt.widgets.Composite;


public interface IFBTestConfiguratonCreator extends ISetValueListener,
		ITriggerEventListener {

	IFBTestConfiguration createConfigurationPage(Composite parent);

	void setType(FBType type);
	
	void setGroup(PaletteGroup group);

}
