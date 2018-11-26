/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.IAlgorithmEditorCreator;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractECSection extends AbstractSection {
	
	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
        Object  obj = part.getAdapter(CommandStack.class);
        if(null != obj){
          return (CommandStack)obj;
        }
		return null;
	}

	public static List<String> getLanguages() {
		List<String> languages = new ArrayList<>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("org.eclipse.fordiac.ide.fbtypeeditor.ecc.algorithmEditor"); //$NON-NLS-1$
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] elements = extension.getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				IConfigurationElement element = elements[j];
				try {
					Object obj = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (obj instanceof IAlgorithmEditorCreator) {
						String lang = element.getAttribute("language"); //$NON-NLS-1$
						languages.add(lang);
					}
				} catch (Exception e) {
				}
			}
		}
		return languages;
	}
}
