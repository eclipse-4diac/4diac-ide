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
package org.eclipse.fordiac.ide.model.emf;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;

public class SingleRecursiveContentAdapter extends EContentAdapter {
	org.eclipse.emf.common.notify.Notifier rootTarget = null;

	@Override
	protected void basicSetTarget(final org.eclipse.emf.common.notify.Notifier target) {
		if (rootTarget == null) {
			rootTarget = target;
		}
		super.basicSetTarget(target);
	}

	@Override
	protected void basicUnsetTarget(final Notifier target) {
		if (target == rootTarget) {
			rootTarget = null;
		}
		super.basicUnsetTarget(target);
	}

	@Override
	protected void addAdapter(final Notifier notifier) {
		if (notifier instanceof final EObject eobject && eobject.eContainer() == rootTarget) {
			super.addAdapter(notifier);
		}
	}

}