/*******************************************************************************
 * Copyright (c) 2008, 2012 - 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * The Class TextEditor.
 */
public class TextEditor extends SourceViewer implements IAlgorithmEditor {

	Document document = new Document();
	
	/**
	 * Instantiates a new text editor.
	 * 
	 * @param parent
	 *            the parent
	 * @param verticalRuler
	 *            the vertical ruler
	 * @param overviewRuler
	 *            the overview ruler
	 * @param showAnnotationsOverview
	 *            the show annotations overview
	 * @param styles
	 *            the styles
	 */
	public TextEditor(final Composite parent,
			final IVerticalRuler verticalRuler,
			final IOverviewRuler overviewRuler,
			final boolean showAnnotationsOverview, final int styles) {
		super(parent, verticalRuler, overviewRuler, showAnnotationsOverview, styles);
		setDocument(document);
	}

	@Override
	public void addDocumentListener(IDocumentListener listener) {
		document.addDocumentListener(listener);
	}

	@Override
	public void removeDocumentListener(IDocumentListener listener) {
		document.removeDocumentListener(listener);
	}

	@Override
	public void setAlgorithmText(String text) {
		document.set(text);
	}
	
	@Override
	public String getAlgorithmText(){
		return document.get();
	}

	@Override
	public boolean isDocumentValid() {
		return true;
	}
}