/*******************************************************************************
 * Copyright (c) 2008, 2012 - 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * The Class TextEditor.
 */
public class TextEditor extends SourceViewer implements IAlgorithmEditor {

	private final Document document = new Document();

	/**
	 * Instantiates a new text editor.
	 *
	 * @param parent                  the parent
	 * @param verticalRuler           the vertical ruler
	 * @param overviewRuler           the overview ruler
	 * @param showAnnotationsOverview the show annotations overview
	 * @param styles                  the styles
	 */
	public TextEditor(final Composite parent, final IVerticalRuler verticalRuler, final IOverviewRuler overviewRuler) {
		super(parent, verticalRuler, overviewRuler, false, SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		setDocument(document);
	}

	@Override
	public void addDocumentListener(final IDocumentListener listener) {
		document.addDocumentListener(listener);
	}

	@Override
	public void removeDocumentListener(final IDocumentListener listener) {
		document.removeDocumentListener(listener);
	}

	@Override
	public void setAlgorithmText(final String text) {
		document.set(text);
	}

	@Override
	public String getAlgorithmText() {
		return document.get();
	}

	@Override
	public boolean isDocumentValid() {
		return true;
	}
}