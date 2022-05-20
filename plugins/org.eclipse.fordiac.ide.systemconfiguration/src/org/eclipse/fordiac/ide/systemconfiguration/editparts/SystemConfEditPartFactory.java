/*******************************************************************************
 * Copyright (c) 2008, 2012 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import org.eclipse.fordiac.ide.gef.editparts.Abstract4diacEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public class SystemConfEditPartFactory extends Abstract4diacEditPartFactory {

	public SystemConfEditPartFactory(final GraphicalEditor editor) {
		super(editor);
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement instanceof SystemConfiguration) {
			return new SystemNetworkEditPart();
		}
		if (modelElement instanceof Link) {
			return new LinkEditPart();
		}
		if (modelElement instanceof Device) {
			return new DeviceEditPart();
		}
		if (modelElement instanceof VarDeclaration) {
			return new DeviceInterfaceEditPart();
		}
		if (modelElement instanceof Segment) {
			return new SegmentEditPart();
		}
		if (modelElement instanceof Resource) {
			return new ResourceEditPart();
		}
		if (modelElement instanceof Value) {
			return new DeviceValueEditPart();
		}
		if (modelElement instanceof ResourceContainer) {
			return new ResourceContainerEditPart();
		}
		throw createEditpartCreationException(modelElement);

	}

}
