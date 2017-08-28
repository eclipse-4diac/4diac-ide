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
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.ruler;

import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.swt.widgets.Composite;

public class FordiacRulerComposite extends RulerComposite {

	public FordiacRulerComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void setGraphicalViewer(ScrollingGraphicalViewer primaryViewer) {
		RulerProvider rpV = new FordiacRulerProvider();
		RulerProvider rpH = new FordiacRulerProvider();
		
		primaryViewer.setProperty(RulerProvider.PROPERTY_VERTICAL_RULER,rpV);
		primaryViewer.setProperty(RulerProvider.PROPERTY_HORIZONTAL_RULER, rpH);

		super.setGraphicalViewer(primaryViewer);		
	}
}
