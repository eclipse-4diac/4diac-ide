/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 3
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractAttributeSection;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.DeviceEditPart;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.SegmentEditPart;

public class AttributeSection extends AbstractAttributeSection {
	@Override
	protected ConfigurableObject getInputType(Object input) {
		if(input instanceof DeviceEditPart){
			return ((DeviceEditPart) input).getModel();
		}
		if(input instanceof SegmentEditPart) {
			return ((SegmentEditPart) input).getModel();
		}
		return null;
	}

	@Override
	protected ConfigurableObject getType() {
		if(type instanceof Device){
			return (Device) type;
		}
		if(type instanceof Segment){
			return (Segment) type;
		}
		return null;
	}
}
