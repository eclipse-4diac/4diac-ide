/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.outline;

import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.action.Action;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;

public class FilterHeadingsContribution extends AbstractFilterOutlineContribution {

	public static final String PREFERENCE_KEY = "ui.outline.filterHeadings"; //$NON-NLS-1$

	@Override
	protected boolean apply(final IOutlineNode node) {
		return !(node instanceof OutlineHeadingNode);
	}

	@Override
	public String getPreferenceKey() {
		return PREFERENCE_KEY;
	}

	@Override
	protected void configureAction(final Action action) {
		action.setText(Messages.FilterHeadingsContribution_Text);
		action.setDescription(Messages.FilterHeadingsContribution_Description);
		action.setToolTipText(Messages.FilterHeadingsContribution_ToolTipText);
		action.setImageDescriptor(FordiacImage.ICON_DOCUMENTATION_EDITOR.getImageDescriptor());
	}
}
