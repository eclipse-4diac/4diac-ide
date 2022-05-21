/*******************************************************************************
 * Copyright (c) 2008, 2021 Profactor GbmH, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - cleanup
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.views;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.deployment.ui.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.AnnotationModel;

/**
 * The listener interface for receiving log events. The class that is interested
 * in processing a log event implements this interface, and the object created
 * with that class is registered with a component using the component's
 * <code>addLogListener<code> method. When the log event occurs, that object's
 * appropriate method is invoked.
 *
 * @see LogEvent
 */
public class LogListener implements IDocumentListener {

	/** The annotation model. */
	private final AnnotationModel annotationModel;

	/**
	 * The Constructor.
	 *
	 * @param annotationModel the annotation model
	 */
	public LogListener(final AnnotationModel annotationModel) {
		this.annotationModel = annotationModel;
	}

	@Override
	public void documentAboutToBeChanged(final DocumentEvent event) {
		// not used
	}

	@Override
	public void documentChanged(final DocumentEvent event) {
		try {
			final IDocument doc = event.getDocument();
			if (event.getDocument().getLength() > 0) {
				final int offset = event.getOffset();
				final FindReplaceDocumentAdapter search = new FindReplaceDocumentAdapter(doc);
				processErrors(doc, search, offset, "Reason"); //$NON-NLS-1$
				processErrors(doc, search, offset, "Error"); //$NON-NLS-1$
				processWarnings(doc, search, offset);
			}
		} catch (final BadLocationException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	public void processErrors(final IDocument doc, final FindReplaceDocumentAdapter search, int offset,
			final String errorIdentifer)
					throws BadLocationException {
		IRegion region;
		do {
			// find beginning of "Error" Argument in Mgr-Response, if any
			region = search.find(offset, errorIdentifer, true, true, true, false);
			if (region != null) {
				annotationModel.addAnnotation(createErrorAnnotation(doc, search, region),
						new Position(region.getOffset(), region.getLength()));
				offset = region.getOffset() + 6;
			}
		} while (region != null);
	}

	public void processWarnings(final IDocument doc, final FindReplaceDocumentAdapter search, int offset)
			throws BadLocationException {
		IRegion region;
		do {
			// find beginning of "Warning" Argument in Mgr-Response, if any
			region = search.find(offset, "Warning", true, true, true, //$NON-NLS-1$
					false);
			if (region != null) {
				annotationModel.addAnnotation(createWarningAnnotation(doc, search, region),
						new Position(region.getOffset(), region.getLength()));
				offset = region.getOffset() + 7;
			}
		} while (region != null);
	}

	public static AbstractDeploymentAnnotations createErrorAnnotation(final IDocument doc,
			final FindReplaceDocumentAdapter search, final IRegion region) throws BadLocationException {
		final IRegion msg = extractMessage(search, region);
		if (msg != null) {
			// extract Reason Code from Mgr-Response
			final String s = doc.get(region.getOffset() + 8, msg.getOffset() - region.getOffset() - 10);
			return new ErrorAnnotation(doc.getLineOfOffset(region.getOffset()),
					MessageFormat.format(Messages.LogListener_ReturnedError, s));
		}
		return new ErrorAnnotation(doc.getLineOfOffset(region.getOffset()), Messages.LogListener_ErrorAnnotation);
	}

	private static AbstractDeploymentAnnotations createWarningAnnotation(final IDocument doc,
			final FindReplaceDocumentAdapter search, final IRegion region) throws BadLocationException {
		final IRegion msg = extractMessage(search, region);
		if (msg != null) {
			// extract Reason Code from Mgr-Response
			final String s = doc.get(region.getOffset(), msg.getOffset() - region.getOffset() - 10);
			return new WarningAnnotation(doc.getLineOfOffset(region.getOffset()),
					MessageFormat.format(Messages.LogListener_ReturnedError, s));
		}
		return new WarningAnnotation(doc.getLineOfOffset(region.getOffset()), Messages.LogListener_MalformedError);
	}

	public static IRegion extractMessage(final FindReplaceDocumentAdapter search, final IRegion region)
			throws BadLocationException {
		// find end of "Reason" Argument in Mgr-Response = end of "Response" tag
		return search.find(region.getOffset(), "/", true, true, false, true); //$NON-NLS-1$
	}

}