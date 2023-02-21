/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval.plugin

import org.eclipse.core.runtime.IConfigurationElement
import org.eclipse.core.runtime.Platform
import org.eclipse.core.runtime.RegistryFactory
import org.eclipse.emf.common.CommonPlugin
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory

class EvaluatorFactoryRegistryReader {
	static final String FACTORY_ID = "factory"
	static final String ATT_CLASS = "class"
	static final String ATT_VARIANT = "variant"
	static final String ATT_TYPE = "type"
	static final String CHILD_SOURCE = "source"

	def void readRegistry() {
		val config = RegistryFactory.registry.getConfigurationElementsFor(EvaluatorPlugin.PLUGIN_ID, FACTORY_ID)
		config.forEach [
			val factory = try {
					createExecutableExtension(ATT_CLASS) as EvaluatorFactory
				} catch (Exception e) {
					log("The factory instance could not be created", e)
					null
				}
			if (factory !== null) {
				val variant = getAttribute(ATT_VARIANT)
				val sources = getChildren(CHILD_SOURCE)
				sources.forEach [
					val type = getAttribute(ATT_TYPE)
					val sourceClass = try {
							CommonPlugin.loadClass(declaringExtension.contributor.name, type)
						} catch (ClassNotFoundException e) {
							log('''The factory source type attribute does not specify a valid class''', e)
							null
						}
					val previousFactory = EvaluatorFactory.Registry.INSTANCE.registerFactory(variant, sourceClass,
						factory)
					if (previousFactory !== null) {
						log('''A factory «previousFactory.class.name» was already registered for the variant «variant» and source type «sourceClass.name»''')
					}
				]
			}
		]
	}

	def private void log(IConfigurationElement element, String message) {
		Platform.getLog(class).
			error('''«message» in extension «element.declaringExtension.extensionPointUniqueIdentifier» from plugin «element.declaringExtension.contributor.name»''')
	}

	def private void log(IConfigurationElement element, String message, Throwable t) {
		Platform.getLog(class).
			error('''«message» in extension «element.declaringExtension.extensionPointUniqueIdentifier» from plugin «element.declaringExtension.contributor.name»''',
				t)
	}
}
