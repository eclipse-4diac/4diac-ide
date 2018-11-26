/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.views;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.deployment.ui.Activator;
import org.eclipse.fordiac.ide.deployment.ui.Messages;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.AnnotationModel;

/**
 * The listener interface for receiving log events.
 * The class that is interested in processing a log
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addLogListener<code> method. When
 * the log event occurs, that object's appropriate
 * method is invoked.
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.IDocumentListener#documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent)
	 */
	@Override
	public void documentAboutToBeChanged(final DocumentEvent event) {
		// not used
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse.jface.text.DocumentEvent)
	 */
	@Override
	public void documentChanged(final DocumentEvent event) {
		try {
			IDocument doc = event.getDocument();
			FindReplaceDocumentAdapter search = new FindReplaceDocumentAdapter(
					doc);
			int offset = event.getOffset();
			if (event.getDocument().getLength() > 0) {
				IRegion region;
				do {
					// find beginning of "Reason" Argument in Mgr-Response, if
					// any
					region = search.find(offset, "Reason", true, true, true, //$NON-NLS-1$
							false);
					if (region != null) {
						// find end of "Reason" Argument in Mgr-Response = end
						// of "Response" tag
						IRegion msg = search.find(region.getOffset(), "/", //$NON-NLS-1$
								true, true, false, true);
						String s;
						ErrorAnnotation errorAnnotation;

						if (msg != null) {
							// extract Reason Code from Mgr-Response
							s = doc.get(region.getOffset() + 8, msg.getOffset()
									- region.getOffset() - 10);
							errorAnnotation = new ErrorAnnotation(doc
									.getLineOfOffset(region.getOffset()),
									MessageFormat.format(
											Messages.LogListener_ReturnedError,
											new Object[] { s }));
						} else {
							errorAnnotation = new ErrorAnnotation(doc
									.getLineOfOffset(region.getOffset()),
									"Error");
						}
						annotationModel.addAnnotation(errorAnnotation,
								new Position(region.getOffset(), region
										.getLength()));
						offset = region.getOffset() + 6;
					}
				} while (region != null);
				do {
					// find beginning of "Reason" Argument in Mgr-Response, if
					// any
					region = search.find(offset, "Error", true, true, true, //$NON-NLS-1$
							false);
					if (region != null) {
						// find end of "Reason" Argument in Mgr-Response = end
						// of "Response" tag
						IRegion msg = search.find(region.getOffset(), "/", //$NON-NLS-1$
								true, true, false, true);
						String s;
						ErrorAnnotation errorAnnotation;
						
						if (msg != null) {
							// extract Reason Code from Mgr-Response
							s = doc.get(region.getOffset() + 8, msg.getOffset()
									- region.getOffset() - 10);
							errorAnnotation = new ErrorAnnotation(doc
									.getLineOfOffset(region.getOffset()),
									MessageFormat.format(
											Messages.LogListener_ReturnedError,
											new Object[] { s }));
						} else {
							errorAnnotation = new ErrorAnnotation(doc
									.getLineOfOffset(region.getOffset()),
							"Error");
						}
						annotationModel.addAnnotation(errorAnnotation,
								new Position(region.getOffset(), region
										.getLength()));
						offset = region.getOffset() + 6;
					}
				} while (region != null);
				do {
					// find beginning of "Warning" Argument in Mgr-Response, if
					// any
					region = search.find(offset, "Warning", true, true, true, //$NON-NLS-1$
							false);
					if (region != null) {
						// find end of "Reason" Argument in Mgr-Response = end
						// of "Response" tag
						IRegion msg = search.find(region.getOffset(), "/", //$NON-NLS-1$
								true, true, false, true);
						String s;
						WarningAnnotation warningAnnotation;
						
						if (msg != null) {
							// extract Reason Code from Mgr-Response
							s = doc.get(region.getOffset(), msg.getOffset()
									- region.getOffset() - 10);
							warningAnnotation = new WarningAnnotation(doc
									.getLineOfOffset(region.getOffset()),
									MessageFormat.format(
											Messages.LogListener_ReturnedError,
											new Object[] { s }));
						} else {
							warningAnnotation = new WarningAnnotation(doc
									.getLineOfOffset(region.getOffset()),
									Messages.LogListener_MalformedError);
						}
						annotationModel.addAnnotation(warningAnnotation,
								new Position(region.getOffset(), region
										.getLength()));
						offset = region.getOffset() + 7;
					}
				} while (region != null);
			}
		} catch (BadLocationException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

	}
}