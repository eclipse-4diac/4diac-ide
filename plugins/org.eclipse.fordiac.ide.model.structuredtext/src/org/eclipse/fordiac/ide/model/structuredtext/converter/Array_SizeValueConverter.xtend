/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martin Jobst 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.structuredtext.converter

import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.conversion.ValueConverterException

import static extension java.lang.Integer.valueOf

class Array_SizeValueConverter extends AbstractNullSafeConverter<Integer> {

	override protected internalToString(Integer value) {
		value.toString
	}

	override protected internalToValue(String string, INode node) throws ValueConverterException {
		if (string.nullOrEmpty) {
			throw new ValueConverterException("Couldn't convert empty string to an int value.", node, null)
		}
		val value = string.replace("_", "")
		if (value.nullOrEmpty) {
			throw new ValueConverterException("Couldn't convert input '" + string + "' to an int value.", node, null)
		}
		try {
			value.valueOf
		} catch (NumberFormatException e) {
			throw new ValueConverterException("Couldn't convert '" + string + "' to an int value.", node, e);
		}
	}

}
