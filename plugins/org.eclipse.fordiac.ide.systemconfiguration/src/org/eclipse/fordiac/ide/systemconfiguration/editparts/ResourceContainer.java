/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
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
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.Device;

public class ResourceContainer {

	private final Device dev;

	public ResourceContainer(Device dev) {
		this.dev = dev;
	}
	
	public Device getDevice() {
		return dev;
	}
		
}
