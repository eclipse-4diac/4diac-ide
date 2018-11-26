/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Monika Wenger - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractDevResInterfaceSection;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

public class ResourceInterfaceSection extends AbstractDevResInterfaceSection {
	@Override
	protected Resource getInputType(Object input) {
		if(input instanceof Resource){
			return (Resource) input;
		}
		return null;
	}
}
