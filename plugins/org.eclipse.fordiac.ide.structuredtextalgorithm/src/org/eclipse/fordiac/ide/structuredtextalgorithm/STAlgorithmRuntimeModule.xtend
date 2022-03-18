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
package org.eclipse.fordiac.ide.structuredtextalgorithm

import com.google.inject.Binder
import com.google.inject.name.Names
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextcore.converter.STCoreValueConverters
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidatorRegistry
import org.eclipse.xtext.validation.CompositeEValidator

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class STAlgorithmRuntimeModule extends AbstractSTAlgorithmRuntimeModule {
	override bindIValueConverterService() {
		return STCoreValueConverters;
	}

	override bindXtextResource() {
		return STAlgorithmResource
	}
	
	override bindEValidatorRegistry() {
		// ignore dangling reference errors (until Palette vs. Resource issues have been addressed)
		return STCoreValidatorRegistry.INSTANCE
	}

	def void configureCompositeEValidator(Binder binder) {
		// ignore dangling reference errors (until Palette vs. Resource issues have been addressed)
		binder.bindConstant.annotatedWith(Names.named(CompositeEValidator.USE_EOBJECT_VALIDATOR)).to(false)
	}
}
