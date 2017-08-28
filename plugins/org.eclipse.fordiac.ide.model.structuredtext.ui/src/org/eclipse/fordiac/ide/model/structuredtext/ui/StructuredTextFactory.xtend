/** 
 * Copyright (c) 2016 fortiss GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * Alois Zoitl - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext.ui

import org.eclipse.fordiac.ide.model.structuredtext.ui.internal.StructuredtextActivator
import org.osgi.framework.Bundle
import com.google.inject.Injector

/** 
 * Special factory that takes into account that the extend activator is used which 
 * allows to inject xtext editors for both structured text and expresions
 */
class StructuredTextFactory extends StructuredTextExecutableExtensionFactory {
	override protected Bundle getBundle() {
		return ExtendedStructuredTextActivator::getInstance().getBundle()
	}

	override protected Injector getInjector() {
		return ExtendedStructuredTextActivator::getInstance().getInjector(
			StructuredtextActivator.ORG_ECLIPSE_FORDIAC_IDE_MODEL_STRUCTUREDTEXT_STRUCTUREDTEXT)
	}
}
