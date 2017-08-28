/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.ui.linkhelpers;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditor;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditorInput;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

public class SystemConfigurationEditorLinkHelper extends AbstractEditorLinkHelper {

	@Override
	public IStructuredSelection findSelection(IEditorInput anInput) {		
		if(anInput instanceof SystemConfigurationEditorInput){
			SystemConfigurationEditorInput sysConfInput = (SystemConfigurationEditorInput)anInput;
			return new StructuredSelection(sysConfInput.getSystemConfiguration());
		}
		return StructuredSelection.EMPTY;
	}

	@Override
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {
		if (aSelection == null || aSelection.isEmpty()){
			return;
		}
		
		SystemConfiguration sysConf = null;
		Device refDev = null;
		if (aSelection.getFirstElement() instanceof SystemConfiguration) {
			sysConf = (SystemConfiguration)aSelection.getFirstElement();
		}else if(aSelection.getFirstElement() instanceof Device){
			refDev = (Device)aSelection.getFirstElement();
			sysConf = refDev.getSystemConfiguration();
		}		
		
		if (null != sysConf) {
			IEditorInput sysConfInput = new SystemConfigurationEditorInput(sysConf);
			IEditorPart editor = activateEditor(aPage, sysConfInput);
			if ((null != editor) && (editor instanceof SystemConfigurationEditor) && (null != refDev)){
				((SystemConfigurationEditor)editor).selectDevice(refDev);
			}
		}
		
	}

}
