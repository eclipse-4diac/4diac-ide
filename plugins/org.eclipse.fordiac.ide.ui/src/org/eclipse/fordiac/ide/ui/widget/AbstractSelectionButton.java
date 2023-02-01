/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Dunja Životin - extracted the createTreeLabelProvider() and createTreeContentProvider() from DataTypeDropdown
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public abstract class AbstractSelectionButton extends TextCellEditor {
	
	protected Map<String, List<String>> elements;
	protected Button button;

	protected AbstractSelectionButton(final Map<String, List<String>> elements) {
		super();
		this.elements = elements;

		final SimpleContentProposalProvider proposalProvider = new SimpleContentProposalProvider();
		proposalProvider.setFiltering(true);
		enableContentProposal(new TextContentAdapter(), proposalProvider, null, NatTableWidgetFactory.getActivationChars());
	}

	protected void refreshProposal() {
		final List<String> proposals = new ArrayList<>();
		elements.forEach((k, v) -> proposals.addAll(v));
		((SimpleContentProposalProvider) proposalProvider).setProposals(proposals.toArray(new String[0]));
	}

	@Override
	protected Text createEditorControl(final Composite parent, final int style) {
		focusListener = new FocusAdapter() {
			// remove focus Listener for button popup
		};
		return super.createEditorControl(parent, style);
	}
	
	@Override
	public Rectangle calculateControlBounds(final Rectangle cellBounds) {
		button.setBounds(cellBounds.x + cellBounds.width - 25, cellBounds.y, 25, cellBounds.height);
		cellBounds.width = cellBounds.width - 25;
		return super.calculateControlBounds(cellBounds);
	}

	@Override
	public void close() {
		super.close();
		button.dispose();
	}

}