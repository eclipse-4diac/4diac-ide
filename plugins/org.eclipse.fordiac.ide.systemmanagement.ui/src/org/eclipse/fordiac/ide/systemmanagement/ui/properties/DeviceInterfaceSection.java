/*******************************************************************************
 * Copyright (c) 2017 -2018 fortiss GmbH, Johannes Kepler University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Monika Wenger, Alois Zoitl 
 *      - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractDeviceInterfaceSection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

public class DeviceInterfaceSection extends AbstractDeviceInterfaceSection {
	@Override
	protected Device getInputType(Object input) {
		if(input instanceof Device){
			profile.removeAll();
			profile.add(((Device)input).getProfile());
			profile.select(0);
			return 	(Device) input;
		}
		return null;
	}
}
