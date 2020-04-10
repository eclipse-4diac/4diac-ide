/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.editors;

import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;

/**
 * The Class ResourceDiagramEditorContextMenuProvider.
 */
public class ResourceDiagramEditorContextMenuProvider extends FBNetworkContextMenuProvider {

	public ResourceDiagramEditorContextMenuProvider(final ResourceDiagramEditor editor, final ActionRegistry registry,
			final ZoomManager zoomManager, Palette palette) {
		super(editor, registry, zoomManager, palette);
		// Nothing that we need to do here
	}
}
