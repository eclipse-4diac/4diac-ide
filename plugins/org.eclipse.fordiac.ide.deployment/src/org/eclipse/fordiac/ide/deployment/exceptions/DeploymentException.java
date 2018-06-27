/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.exceptions;

/** Typed exception for indicating deployment issues 
 *
 */
public class DeploymentException extends Exception {

	private static final long serialVersionUID = -8125405669586970659L;

	public DeploymentException(String msg) {
		super(msg);
	}

	public DeploymentException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
