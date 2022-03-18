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

import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType

interface AnyMagnitudeValue extends AnyElementaryValue {
	override AnyMagnitudeType getType()

	def byte byteValue()

	def short shortValue()

	def int intValue()

	def long longValue()

	def float floatValue()

	def double doubleValue()
}
