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
 *   Martin Jobst - change to record
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.data;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public record ConnectionDeploymentData(String sourcePrefix, String sourceSuffix, IInterfaceElement source,
		String destinationPrefix, IInterfaceElement destination) {
}
