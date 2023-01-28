/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view.actions;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.debug.EvaluatorProcess;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchEventQueue;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;

public final class RepeatEventAction extends Action {

	private FBLaunchEventQueue eventQueue;

	public RepeatEventAction() {
		super("Repeat event", IAction.AS_CHECK_BOX);
		setImage();
		setEnabled(false);
	}

	@Override
	public void run() {
		if (eventQueue != null) {
			eventQueue.setRepeat(isChecked());
		}
	}

	public void updateEvaluator(final EvaluatorProcess evaluator) {
		final FBLaunchEventQueue newEventQueue = getEventQueue(evaluator);
		if (eventQueue != newEventQueue) {
			eventQueue = newEventQueue;
			if (newEventQueue != null) {
				setChecked(newEventQueue.isRepeat());
			}
			setEnabled(eventQueue != null);
		}
	}

	private static FBLaunchEventQueue getEventQueue(final EvaluatorProcess evaluator) {
		if(evaluator != null) {
			final var queue = ((FBEvaluator<?>) evaluator.getEvaluator()).getEventQueue();
			if (queue instanceof FBLaunchEventQueue) {
				return (FBLaunchEventQueue) queue;
			}
		}
		return null;
	}

	private void setImage() {
		final Bundle bundle = Platform.getBundle("org.eclipse.debug.ui"); //$NON-NLS-1$
		final URL fullPathString = FileLocator.find(bundle, new Path("icons/full/elcl16/restart_co.png"), null); //$NON-NLS-1$
		setImageDescriptor(ImageDescriptor.createFromURL(fullPathString));
	}
}