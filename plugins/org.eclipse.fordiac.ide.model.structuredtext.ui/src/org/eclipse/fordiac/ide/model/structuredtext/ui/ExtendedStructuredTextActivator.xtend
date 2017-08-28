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

import org.eclipse.fordiac.ide.model.structuredtext.ExpressionRuntimeModule
import org.eclipse.fordiac.ide.model.structuredtext.ui.internal.StructuredtextActivator
import org.osgi.framework.BundleContext

/** 
 * An extended activator which extends the standard generated xtext activator for 
 * expressions as sublanguage
 */
class ExtendedStructuredTextActivator extends StructuredtextActivator {
	public static final String ORG_FORDIAC_IDE_MODEL_STRUCTUREDTEXT_EXPRESSION = "org.eclipse.fordiac.ide.model.structuredtext.Expression"
	static StructuredtextActivator INSTANCE

	override start(BundleContext context) throws Exception {
		super.start(context)
		INSTANCE = this
	}

	override stop(BundleContext context) throws Exception {
		INSTANCE = null
		super.stop(context)
	}

	def static getInstance() {
		return INSTANCE
	}

	override getRuntimeModule(String grammar) {
		if (ORG_FORDIAC_IDE_MODEL_STRUCTUREDTEXT_EXPRESSION.equals(grammar)) {
			return new ExpressionRuntimeModule()
		}
		return super.getRuntimeModule(grammar)
	}

	override getUiModule(String grammar) {
		if (ORG_FORDIAC_IDE_MODEL_STRUCTUREDTEXT_EXPRESSION.equals(grammar)) {
			return new ExpressionUiModule(this)
		}
		return super.getUiModule(grammar)
	}
}
