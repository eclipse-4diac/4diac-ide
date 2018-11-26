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
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractDevResInterfaceSection;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.ResourceEditPart;

public class ResourceInterfaceSection extends AbstractDevResInterfaceSection {
	@Override
	protected Resource getInputType(Object input) {
		if(input instanceof ResourceEditPart){
			return ((ResourceEditPart) input).getModel();
		}
		return null;
	}
}
