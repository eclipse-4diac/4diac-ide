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
 *   Jose Cabral, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.gef.EditPart;

public abstract class AbstractOnlineFBHandler extends AbstractDeploymentCommand {

	private FB fb = null;
	private Resource resource = null;
	private FB resFB = null;

	@Override
	protected boolean prepareParametersToExecute(final Object element) {
		if (element instanceof final EditPart editPart) {
			return prepareParametersToExecute(editPart.getModel());
		}
		if (element instanceof final FB fbElement) {
			this.fb = fbElement;
		}
		if (null != fb && fb.isMapped()) {
			resource = fb.getResource();
			if (null != resource) {
				setDevice(resource.getDevice());
				resFB = (FB) fb.getOpposite();
				return (null != getDevice() && null != resFB);
			}
		}
		return false;
	}

	@Override
	protected String getCurrentElementName() {
		return MessageFormat.format(Messages.AbstractOnlineFBHandler_FunctionBlock, fb.getName());
	}

	public FB getResFB() {
		return resFB;
	}

	public Resource getResource() {
		return resource;
	}

}
