/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

	public FBTypePaletteViewerProvider(IProject project, EditDomain graphicalViewerDomain) {
		super(graphicalViewerDomain);
		this.project = project;
	}

	@Override
	public PaletteViewer createPaletteViewer(Composite parent) {
		FBPaletteViewer pViewer = new FBPaletteViewer();
		pViewer.createTypeLibTreeControl(parent, project);
		configurePaletteViewer(pViewer);
		hookPaletteViewer(pViewer);
		return pViewer;
	}
}