/** 
 * Copyright (c) 2016 fortiss GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * Alois Zoitl, Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext.ui

import org.eclipse.jface.text.source.IOverviewRuler
import org.eclipse.jface.text.source.IVerticalRuler
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.plugin.AbstractUIPlugin
import org.eclipse.xtext.service.SingletonBinding
import org.eclipse.xtext.ui.editor.XtextSourceViewer
import org.eclipse.fordiac.ide.model.structuredtext.ide.contentassist.antlr.ExpressionParser

class ExpressionUiModule extends StructuredTextUiModule {
	new(AbstractUIPlugin plugin) {
		super(plugin)
	}

	override bindIContentAssistParser() {
		return ExpressionParser
	}

	def Class<? extends XtextSourceViewer.Factory> bindXtextSourceViewer$Factory() {
		return ExpresionSourceViewerFactory
	}

	static class ExpresionSourceViewerFactory extends XtextSourceViewer.DefaultFactory {
		override XtextSourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler,
			IOverviewRuler overviewRuler, boolean showsAnnotationOverview, int styles) {
			return super.createSourceViewer(parent, null, null, false, SWT::BORDER.bitwiseOr(SWT::SINGLE))
		} 
	}

	override provideTemplatesLanguageConfiguration() {
		return null
	}

	override provideLanguageRegistry() {
		return null
	}

	@SingletonBinding(eager=true) override bindLanguageRegistrar() {
		return null
	}

	override bindXtextTemplatePreferencePage() {
		return null
	}
}
