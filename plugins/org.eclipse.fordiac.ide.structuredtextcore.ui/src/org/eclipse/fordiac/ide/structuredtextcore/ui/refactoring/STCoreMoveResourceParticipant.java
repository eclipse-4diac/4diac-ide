/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.xtext.ui.refactoring.ui.SyncUtil;
import org.eclipse.xtext.ui.refactoring2.participant.XtextMoveResourceParticipant;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreMoveResourceParticipant extends XtextMoveResourceParticipant {

	@Inject
	private SyncUtil syncUtil;

	@Override
	protected boolean initialize(final Object element) {
		try {
			syncUtil.totalSync(true, true, false);
		} catch (final InvocationTargetException e) {
			return false;
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			return false;
		}
		return super.initialize(element);
	}
}
