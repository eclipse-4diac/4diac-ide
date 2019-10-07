/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.data;

import org.eclipse.fordiac.ide.model.libraryElement.FB;

public class FBDeploymentData {
	private final String prefix;
	private final FB fb;
	
	public FBDeploymentData(final String prefix, final FB fb) {
		this.prefix = prefix;
		this.fb = fb;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public FB getFb() {
		return fb;
	}
}
