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
package org.eclipse.fordiac.ide.model.eval.value

import org.eclipse.fordiac.ide.model.libraryElement.ECState
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.xtend.lib.annotations.Accessors

@FinalFieldsConstructor
class ECStateValue implements Value {
	@Accessors final ECState state

	override BasicFBType getType() {
		state.ECC?.basicFBType
	}

	override equals(Object object) {
		if (object instanceof ECStateValue) {
			state == object.state
		} else
			false
	}

	override hashCode() {
		state.name.hashCode
	}

	override toString() { state.name }
}
