/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.MappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;

public class CommunicationChannelEditPart extends FBEditPart {
	@Override
	protected List<Object> getModelChildren() {
		final List<Object> elements = super.getModelChildren();
		elements.remove(getInstanceName());
		return elements;
	}

	@Override
	protected Segment findDevice() {
		MappingTarget tgt = null;
		if ((null != getModel()) && getModel().isMapped()) {
			tgt = (MappingTarget) getModel().getOpposite().eContainer();
		}
		return (null != tgt) ? (Segment) tgt.eContainer().eContainer() : null;
	}

}