/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.core.resources.IProject;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.swt.widgets.Composite;

public class FBTypePaletteViewerProvider extends PaletteViewerProvider {
	/** the project for which the type palette viewer should be created
	 * 
	 */
	private final IProject project;
	private final String navigatorId;

	public FBTypePaletteViewerProvider(IProject project, EditDomain graphicalViewerDomain, String navigatorId) {
		super(graphicalViewerDomain);
		this.project = project;
		this.navigatorId = navigatorId;
	}

	@Override
	public PaletteViewer createPaletteViewer(Composite parent) {
		FBPaletteViewer pViewer = new FBPaletteViewer(navigatorId);
		pViewer.createTypeLibTreeControl(parent, project);
		configurePaletteViewer(pViewer);
		hookPaletteViewer(pViewer);
		return pViewer;
	}
}