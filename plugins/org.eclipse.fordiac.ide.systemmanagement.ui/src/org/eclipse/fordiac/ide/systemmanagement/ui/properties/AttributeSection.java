/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 3
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractAttributeSection;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;

public class AttributeSection extends AbstractAttributeSection {
	@Override
	protected ConfigurableObject getInputType(Object input) {
		if(input instanceof Device){
			return (Device) input;
		}
		if(input instanceof Segment) {
			return (Segment) input;
		}
		if(input instanceof Application) {
			return (Application) input;
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
		if(type instanceof Application){
			return (Application) type;
		}
		return null;
	}
}
