/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.nattable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.nat.SorterModel;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.nebula.widgets.nattable.NatTable;

record BuiltNatTable<T extends EObject>(NatTable natTable, ChangeableListDataProvider<T> provider,
		SorterModel<T> sorterModel) {
}
