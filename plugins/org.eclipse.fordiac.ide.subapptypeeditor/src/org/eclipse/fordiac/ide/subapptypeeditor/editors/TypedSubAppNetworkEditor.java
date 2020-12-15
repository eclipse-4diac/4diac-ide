/*******************************************************************************
 * Copyright (c) 2014 - 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.subapptypeeditor.editors;

import org.eclipse.fordiac.ide.fbtypeeditor.network.CompositeNetworkEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPartFactory;
import org.eclipse.fordiac.ide.subapptypeeditor.editparts.SubAppTypeNetworkEditPartFactory;

public class TypedSubAppNetworkEditor extends CompositeNetworkEditor {

	@Override
	protected CompositeNetworkEditPartFactory getEditPartFactory() {
		return new SubAppTypeNetworkEditPartFactory(this);
	}

	@Override
	protected String getPaletteNavigatorID() {
		// for subapp types we want to show the same as for applications. If we wouldn't
		// provide this here
		// we would get the palette of the composite FB type editor
		return "org.eclipse.fordiac.ide.fbpaletteviewer"; //$NON-NLS-1$
	}
}
