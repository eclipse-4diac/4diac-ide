/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.editor;

import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;
import org.eclipse.xtext.ui.internal.XtextPluginImages;

import com.google.inject.Inject;
import com.google.inject.MembersInjector;

public class STCoreSourceViewer extends XtextSourceViewer {

	public static class STCoreSourceViewerFactory implements Factory {

		@Inject
		private MembersInjector<XtextSourceViewer> membersInjector;

		@Override
		public XtextSourceViewer createSourceViewer(final Composite parent, final IVerticalRuler ruler,
				final IOverviewRuler overviewRuler, final boolean showsAnnotationOverview, final int styles) {
			final STCoreSourceViewer result = new STCoreSourceViewer(parent, ruler, overviewRuler,
					showsAnnotationOverview, styles);
			membersInjector.injectMembers(result);
			return result;
		}

	}

	public STCoreSourceViewer(final Composite parent, final IVerticalRuler ruler, final IOverviewRuler overviewRuler,
			final boolean showsAnnotationOverview, final int styles) {
		super(parent, ruler, overviewRuler, showsAnnotationOverview, styles);
	}

	@Override
	protected void createControl(final Composite parent, final int styles) {
		// ensure XtextPluginImages is initialized in UI thread to avoid exception
		// during computing code minings, which runs outside an UI thread
		XtextPluginImages.get(null);
		super.createControl(parent, styles);
	}

	@Override
	public int modelLine2WidgetLine(final int modelLine) {
		try {
			return super.modelLine2WidgetLine(modelLine);
		} catch (final IllegalStateException e) {
			// catch IllegalStateException sporadically thrown by
			// ProjectionMapping.toImageLine(int)
			// see bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=532513
			return -1;
		}
	}

	@Override
	protected int getClosestWidgetLineForModelLine(final int modelLine) {
		try {
			return super.getClosestWidgetLineForModelLine(modelLine);
		} catch (final IllegalStateException e) {
			// catch IllegalStateException sporadically thrown by
			// ProjectionMapping.toImageLine(int)
			// see bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=532513
			return -1;
		}
	}
}
