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
package org.eclipse.fordiac.ide.structuredtextalgorithm.validation

import org.eclipse.emf.ecore.EValidator
import org.eclipse.emf.ecore.impl.EValidatorRegistryImpl
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.DiagnosticChain
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EDataType

class STAlgorithmValidatorRegistry extends EValidatorRegistryImpl implements EValidator.Registry {

	public static final STAlgorithmValidatorRegistry INSTANCE = new STAlgorithmValidatorRegistry

	new() {
		super(EValidator.Registry.INSTANCE)
	}

	override Object delegatedGet(Object key) {
		if(key === null) NullValidator.INSTANCE else super.delegatedGet(key)
	}

	static class NullValidator implements EValidator {
		public static final NullValidator INSTANCE = new NullValidator

		override validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
			true
		}

		override validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
			true
		}

		override validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
			true
		}
	}
}
