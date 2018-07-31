/*******************************************************************************
 * Copyright (c) 2008, 2010 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
 * 				 2018 Johannes Kepler University
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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.fordiac.ide.deployment.ui.Messages;
import org.eclipse.fordiac.ide.deployment.ui.xml.XMLConfiguration;
import org.eclipse.fordiac.ide.deployment.ui.xml.XMLPartitionScanner;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationBarHoverManager;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.xml.sax.InputSource;

/**
 * The Class Output.
 */
public class Output extends ViewPart implements IDeploymentListener {

	/** The sourceViewer. */
	private SourceViewer sv;

	/**
	 * Instantiates a new output.
	 */
	public Output() {
		// empty constructor
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(final Composite parent) {
		Composite root = new Composite(parent, SWT.None);
		root.setLayout(new GridLayout());
		createSourceViewer(root);
		contributeToActionBars();
	}

	/**
	 * Contribute to action bars.
	 */
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	/**
	 * Fill local tool bar.
	 * 
	 * @param manager
	 *            the manager
	 */
	private void fillLocalToolBar(final IToolBarManager manager) {
		Action clearAction = new Action(Messages.Output_ClearActionLabel) {
			@Override
			public void run() {
				clearOutput();
			}
		};
		clearAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_CLEAR));
		clearAction.setToolTipText(Messages.Output_ClearTooltip);
		clearAction.setDescription(Messages.Output_ClearDescription);
		manager.add(clearAction);
	}

	/**
	 * The Class ColorCache.
	 */
	static class ColorCache implements ISharedTextColors {

		/** The rgbs. */
		private final Map<RGB, Color> rgbs = new HashMap<>();

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.text.source.ISharedTextColors#getColor(org.eclipse.swt.graphics.RGB)
		 */
		@Override
		public Color getColor(final RGB rgb) {
			if (!rgbs.containsKey(rgb)) {
				rgbs.put(rgb, new Color(Display.getDefault(), rgb));
			}
			return rgbs.get(rgb);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.text.source.ISharedTextColors#dispose()
		 */
		@Override
		public void dispose() {
			for (Entry<RGB, Color> entry : rgbs.entrySet()) {
				if (!entry.getValue().isDisposed()) {
					entry.getValue().dispose();
				}
			}
		}
	}

	/** The ERRO r_ type. */
	private static final String ERROR_TYPE = Messages.Output_DownloadError;
	private static final String WARNIGN_TYPE = Messages.Output_DownloadWarning;
	private static final int OVERVIEW_RULER_WIDTH = 12;
	private static final int ANNOTATION_RULES_COLUMN_WIDTH = 16;

	/** The annotation model. */
	private AnnotationModel fAnnotationModel = new AnnotationModel();

	/**
	 * Creates the source viewer.
	 * 
	 * @param parent
	 *            the parent
	 */
	private void createSourceViewer(final Composite parent) {

		AnnotationMarkerAccess annotationMarker = new AnnotationMarkerAccess();
		//
		ColorCache colorCache = new ColorCache();

		CompositeRuler compositeRuler = new CompositeRuler();
		OverviewRuler overviewRuler = new OverviewRuler(annotationMarker, OVERVIEW_RULER_WIDTH, colorCache);
		AnnotationRulerColumn annotationRuler = new AnnotationRulerColumn(
				fAnnotationModel, ANNOTATION_RULES_COLUMN_WIDTH, annotationMarker);
		compositeRuler.setModel(fAnnotationModel);
		overviewRuler.setModel(fAnnotationModel);

		// annotation ruler is decorating our composite ruler
		compositeRuler.addDecorator(0, annotationRuler);
		//
		// // add what types are show on the different rulers
		annotationRuler.addAnnotationType(ERROR_TYPE);
		annotationRuler.addAnnotationType(WARNIGN_TYPE);
		overviewRuler.addAnnotationType(ERROR_TYPE);
		overviewRuler.addAnnotationType(WARNIGN_TYPE);
		overviewRuler.addHeaderAnnotationType(ERROR_TYPE);
		overviewRuler.addHeaderAnnotationType(WARNIGN_TYPE);
		// // set what layer this type is on
		overviewRuler.setAnnotationTypeLayer(ERROR_TYPE, 3);
		overviewRuler.setAnnotationTypeLayer(WARNIGN_TYPE, 4);
		// // set what color is used on the overview ruler for the type
		overviewRuler.setAnnotationTypeColor(ERROR_TYPE, colorCache
				.getColor(new RGB(255, 0, 0)));
		overviewRuler.setAnnotationTypeColor(WARNIGN_TYPE, colorCache
				.getColor(new RGB(255, 255, 0)));

		sv = new SourceViewer(parent, compositeRuler, overviewRuler, true,
				SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);

		GridData gd = new GridData(GridData.FILL, GridData.FILL, true, true);
		sv.getControl().setLayoutData(gd);

		Document document = new Document();
		sv.setDocument(document);
		sv.setEditable(false);
		document.addDocumentListener(new LogListener(fAnnotationModel));
		IDocumentPartitioner partitioner = new FastPartitioner(new XMLPartitionScanner(),
				new String[] { XMLPartitionScanner.XML_TAG, XMLPartitionScanner.XML_COMMENT });
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);
		sv.setDocument(document, fAnnotationModel);

		sv.configure(new XMLConfiguration());

		// hover manager that shows text when we hover
		AnnotationBarHoverManager fAnnotationHoverManager = new AnnotationBarHoverManager(
				compositeRuler, sv, new AnnotationHover(),
				new AnnotationConfiguration());
		fAnnotationHoverManager.install(annotationRuler.getControl());

		AnnotationPainter ap = new AnnotationPainter(sv, annotationMarker);
		ap.addAnnotationType(ERROR_TYPE);
		ap.setAnnotationTypeColor(ERROR_TYPE, colorCache.getColor(new RGB(255,
				0, 0)));
		// this will draw the squigglies under the text
		sv.addPainter(ap);
	}

	/**
	 * The Class AnnotationHover.
	 */
	class AnnotationHover implements IAnnotationHover, ITextHover {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.text.source.IAnnotationHover#getHoverInfo(org.eclipse.jface.text.source.ISourceViewer,
		 *      int)
		 */
		@Override
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String getHoverInfo(final ISourceViewer sourceViewer,
				final int lineNumber) {

			Map annotations = new HashMap();

			for (Iterator iterator = fAnnotationModel.getAnnotationIterator(); iterator
					.hasNext();) {
				Annotation annotation = (Annotation) iterator.next();
				if (annotation instanceof ErrorAnnotation) {
					ErrorAnnotation err = (ErrorAnnotation) annotation;
					annotations.put(Integer.toString(err.getLine()), err
							.getText());
				}

			}

			return (String) annotations.get(Integer.toString(lineNumber));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.text.ITextHover#getHoverInfo(org.eclipse.jface.text.ITextViewer,
		 *      org.eclipse.jface.text.IRegion)
		 */
		@Override
		public String getHoverInfo(final ITextViewer textViewer,
				final IRegion hoverRegion) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.text.ITextHover#getHoverRegion(org.eclipse.jface.text.ITextViewer,
		 *      int)
		 */
		@Override
		public IRegion getHoverRegion(final ITextViewer textViewer,
				final int offset) {
			return null;
		}
	}

	/**
	 * The Class AnnotationConfiguration.
	 */
	static class AnnotationConfiguration implements IInformationControlCreator {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.text.IInformationControlCreator#createInformationControl(org.eclipse.swt.widgets.Shell)
		 */
		@Override
		public IInformationControl createInformationControl(final Shell shell) {
			return new DefaultInformationControl(shell);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		//nothing to do here	
	}

	/**
	 * Gets the formatted xml.
	 * 
	 * @param command
	 *            the command
	 * 
	 * @return the formatted xml
	 * 
	 * @throws TransformerFactoryConfigurationError
	 *             the transformer factory configuration error
	 */
	private String getFormattedXML(final String command)
			throws TransformerFactoryConfigurationError {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();

			org.w3c.dom.Document doc = documentBuilder.parse(new InputSource(
					new StringReader(command)));

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes"); //$NON-NLS-1$

			transformer.setOutputProperty(
					"{http://xml.apache.org/xalan}indent-amount", "2"); //$NON-NLS-1$ //$NON-NLS-2$

			DOMSource domSource = new DOMSource(doc);

			OutputStream outputStream = new ByteArrayOutputStream();
			StreamResult streamResult = new StreamResult(outputStream);
			transformer.transform(domSource, streamResult);
			return outputStream.toString();
		} catch (Exception e) {
			return "The given command:\n" + command +"\n has an issue. The reason is: " + e.getMessage();
		}
	}

	/** The buffer. */
	private StringBuilder buffer = new StringBuilder();

	@Override
	public void connectionOpened() {
		// nothing to do
	}
	
	@Override
	public void postResponseReceived(final String response, final String source) {
		Display.getDefault().asyncExec(() -> {
			buffer.append("\n");//$NON-NLS-1$ 
			buffer.append(getFormattedXML(response));
		});
	}

	@Override
	public void postCommandSent(String info, String destination, String command) {
		Display.getDefault().asyncExec(() -> {

				String temp = MessageFormat.format(Messages.Output_Comment, info);
				buffer.append("\n\n");//$NON-NLS-1$ 
				buffer.append(temp);
				buffer.append("\n");//$NON-NLS-1$ 
				buffer.append(getFormattedXML(command));
		});
	}
	
	@Override
	public void connectionClosed() {		
		IDocument document = sv.getDocument(); 
		if(null != document){
			Display.getDefault().asyncExec(() -> document.set(buffer.toString()));
		}
	}

	public void clearOutput() {
		sv.getDocument().set(""); //$NON-NLS-1$
		buffer = buffer.delete(0, buffer.length());
	}

}
