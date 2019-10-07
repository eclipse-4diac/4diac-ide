/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.data;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class ConnectionDeploymentData{
	private final String sourcePrefix;       
	private final IInterfaceElement source;
	private final String destinationPrefix;       
	private final IInterfaceElement destination;
	
	public ConnectionDeploymentData(final String sourcePrefix, final IInterfaceElement source, final String destinationPrefix,
			final IInterfaceElement destination) {
		this.sourcePrefix = sourcePrefix;
		this.source = source;
		this.destinationPrefix = destinationPrefix;
		this.destination = destination;
	}

	public String getSourcePrefix() {
		return sourcePrefix;
	}

	public IInterfaceElement getSource() {
		return source;
	}

	public String getDestinationPrefix() {
		return destinationPrefix;
	}

	public IInterfaceElement getDestination() {
		return destination;
	}
	
}