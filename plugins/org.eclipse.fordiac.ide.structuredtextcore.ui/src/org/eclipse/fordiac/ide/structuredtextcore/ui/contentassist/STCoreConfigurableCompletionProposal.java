/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.link.LinkedModeModel;
import org.eclipse.jface.text.link.LinkedModeUI;
import org.eclipse.jface.text.link.LinkedPosition;
import org.eclipse.jface.text.link.LinkedPositionGroup;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.TextRegion;

public class STCoreConfigurableCompletionProposal extends ConfigurableCompletionProposal {

	private boolean customLinkedMode;
	private ITextViewer viewer;
	private List<ITextRegion> positions = Collections.emptyList();
	private int exitPositionOffset = 0;
	private char[] exitChars;

	public STCoreConfigurableCompletionProposal(final String replacementString, final int replacementOffset,
			final int replacementLength, final int cursorPosition, final Image image, final StyledString displayString,
			final IContextInformation contextInformation, final String additionalProposalInfo) {
		super(replacementString, replacementOffset, replacementLength, cursorPosition, image, displayString,
				contextInformation, additionalProposalInfo);
	}

	public void setCustomLinkedMode(final ITextViewer viewer, final List<ITextRegion> positions,
			final int exitPositionOffset, final char... exitChars) {
		setSimpleLinkedMode(viewer, exitChars);
		this.customLinkedMode = true;
		this.viewer = viewer;
		this.positions = positions;
		this.exitPositionOffset = exitPositionOffset;
		this.exitChars = exitChars;
	}

	@Override
	protected void setUpLinkedMode(final IDocument document) {
		if (customLinkedMode) {
			try {
				final LinkedModeModel model = new LinkedModeModel();
				for (final var position : positions) {
					final LinkedPositionGroup group = new LinkedPositionGroup();
					group.addPosition(new LinkedPosition(document, position.getOffset(), position.getLength()));
					model.addGroup(group);
				}
				model.forceInstall();

				final LinkedModeUI ui = new LinkedModeUI(model, viewer);
				ui.setExitPolicy(new ExitPolicy(exitChars));
				ui.setExitPosition(viewer, exitPositionOffset, 0, Integer.MAX_VALUE);
				ui.enter();
			} catch (final BadLocationException e) {
				FordiacLogHelper.logWarning(e.getMessage(), e);
			}
		} else {
			super.setUpLinkedMode(document);
		}
	}

	@Override
	public void shiftOffset(final int deltaLength) {
		super.shiftOffset(deltaLength);
		positions = positions.stream()
				.<ITextRegion>map(region -> new TextRegion(region.getOffset() + deltaLength, region.getLength()))
				.toList();
		exitPositionOffset += deltaLength;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return super.equals(obj);
	}
}
