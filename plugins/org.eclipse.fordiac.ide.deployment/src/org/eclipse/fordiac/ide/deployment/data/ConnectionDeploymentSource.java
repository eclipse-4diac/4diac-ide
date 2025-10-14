/*******************************************************************************
 * Copyright (c) 2017, 2025 fortiss GmbH, Martin Erich Jobst
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Jobst - extract from ResourceDeploymentData and change to record
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.data;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public record ConnectionDeploymentSource(String prefix, String suffix, IInterfaceElement source) {

	public ConnectionDeploymentData toConnectionData(final String destinationPrefix,
			final IInterfaceElement destination) {
		return new ConnectionDeploymentData(prefix, suffix, source, destinationPrefix, destination);
	}
}
