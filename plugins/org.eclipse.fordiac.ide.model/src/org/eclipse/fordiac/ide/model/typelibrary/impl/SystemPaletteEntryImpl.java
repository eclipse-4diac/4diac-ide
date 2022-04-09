/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 *    Alois Zoitl  - turned the Palette model into POJOs
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class SystemPaletteEntryImpl extends AbstractTypeEntryImpl implements SystemEntry {

	@Override
	public AutomationSystem getSystem() {
		final LibraryElement type = getType();
		if(type instanceof AutomationSystem){
			return (AutomationSystem)type;
		}
		return null;
	}

	@Override
	public void setSystem(final LibraryElement system) {
		if(system instanceof AutomationSystem){
			super.setType(system);
		}else{
			super.setType(null);
			if(null != type){
				FordiacLogHelper .logError("tried to set no AutomationSystem as type entry for SystemPaletteEntry");//$NON-NLS-1$
			}
		}
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new SystemImporter(getFile());
	}

}
