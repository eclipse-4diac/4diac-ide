/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.data;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class ConnectionDeploymentData{
	public final String sourcePrefix;       
	public final IInterfaceElement source;
	public final String destinationPrefix;       
	public final IInterfaceElement destination;
	
	public ConnectionDeploymentData(final String sourcePrefix, final IInterfaceElement source, final String destinationPrefix,
			final IInterfaceElement destination) {
		this.sourcePrefix = sourcePrefix;
		this.source = source;
		this.destinationPrefix = destinationPrefix;
		this.destination = destination;
	}
	
}