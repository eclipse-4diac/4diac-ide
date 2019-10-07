/**
 * Copyright (c) 2016 fortiss GmbH.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * Contributors:
 * Alois Zoitl, Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext.ui;

import com.google.inject.Provider;
import org.eclipse.fordiac.ide.model.structuredtext.ide.contentassist.antlr.ExpressionParser;
import org.eclipse.fordiac.ide.model.structuredtext.ui.StructuredTextUiModule;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ide.editor.contentassist.antlr.IContentAssistParser;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.ui.codetemplates.ui.preferences.TemplatesLanguageConfiguration;
import org.eclipse.xtext.ui.codetemplates.ui.registry.LanguageRegistrar;
import org.eclipse.xtext.ui.codetemplates.ui.registry.LanguageRegistry;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;
import org.eclipse.xtext.ui.editor.templates.XtextTemplatePreferencePage;

@SuppressWarnings("all")
public class ExpressionUiModule extends StructuredTextUiModule {
  public static class ExpresionSourceViewerFactory extends XtextSourceViewer.DefaultFactory {
    @Override
    public XtextSourceViewer createSourceViewer(final Composite parent, final IVerticalRuler ruler, final IOverviewRuler overviewRuler, final boolean showsAnnotationOverview, final int styles) {
      return super.createSourceViewer(parent, null, null, false, (SWT.BORDER | SWT.SINGLE));
    }
  }
  
  public ExpressionUiModule(final AbstractUIPlugin plugin) {
    super(plugin);
  }
  
  @Override
  public Class<? extends IContentAssistParser> bindIContentAssistParser() {
    return ExpressionParser.class;
  }
  
  public Class<? extends XtextSourceViewer.Factory> bindXtextSourceViewer$Factory() {
    return ExpressionUiModule.ExpresionSourceViewerFactory.class;
  }
  
  @Override
  public Provider<? extends TemplatesLanguageConfiguration> provideTemplatesLanguageConfiguration() {
    return null;
  }
  
  @Override
  public Provider<? extends LanguageRegistry> provideLanguageRegistry() {
    return null;
  }
  
  @SingletonBinding(eager = true)
  @Override
  public Class<? extends LanguageRegistrar> bindLanguageRegistrar() {
    return null;
  }
  
  @Override
  public Class<? extends XtextTemplatePreferencePage> bindXtextTemplatePreferencePage() {
    return null;
  }
}
