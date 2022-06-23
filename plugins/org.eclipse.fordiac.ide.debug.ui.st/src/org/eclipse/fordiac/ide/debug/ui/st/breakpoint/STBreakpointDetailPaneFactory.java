/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui.st.breakpoint;

import java.util.Collections;
import java.util.Set;

import org.eclipse.debug.ui.IDetailPane;
import org.eclipse.debug.ui.IDetailPaneFactory;
import org.eclipse.fordiac.ide.debug.breakpoint.EvaluatorLineBreakpoint;
import org.eclipse.fordiac.ide.debug.ui.st.Messages;
import org.eclipse.jface.viewers.IStructuredSelection;

public class STBreakpointDetailPaneFactory implements IDetailPaneFactory {

	@Override
	public Set<String> getDetailPaneTypes(final IStructuredSelection selection) {
		if (selection.size() == 1 && selection.getFirstElement() instanceof EvaluatorLineBreakpoint) {
			return Set.of(STBreakpointDetailPane.ID);
		}
		return Collections.emptySet();
	}

	@Override
	public String getDefaultDetailPane(final IStructuredSelection selection) {
		if (selection.size() == 1 && selection.getFirstElement() instanceof EvaluatorLineBreakpoint) {
			return STBreakpointDetailPane.ID;
		}
		return null;
	}

	@Override
	public IDetailPane createDetailPane(final String paneID) {
		if (STBreakpointDetailPane.ID.equals(paneID)) {
			return new STBreakpointDetailPane();
		}
		return null;
	}

	@Override
	public String getDetailPaneName(final String paneID) {
		if (STBreakpointDetailPane.ID.equals(paneID)) {
			return Messages.EvaluatorBreakpointDetailPane_Name;
		}
		return null;
	}

	@Override
	public String getDetailPaneDescription(final String paneID) {
		if (STBreakpointDetailPane.ID.equals(paneID)) {
			return Messages.EvaluatorBreakpointDetailPane_Description;
		}
		return null;
	}
}
