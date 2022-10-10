/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.tools.ant.BuildException;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ExportLibElements extends AbstractExportLibElements {

	private static final String ANT_CONVERT_TASK_DIRECTORY_NAME = "converted_FBs"; //$NON-NLS-1$

	private static final String SIMPLEFB_XML_ATTRIBUTE = "SimpleFB"; //$NON-NLS-1$
	private static final String BASICFB_XML_ATTRIBUTE = "BasicFB"; //$NON-NLS-1$
	private static final String COMPOSITEFB_XML_ATTRIBUTE = "FBNetwork"; //$NON-NLS-1$

	@Override
	protected String getExportDirectoryDefault() {
		return ANT_CONVERT_TASK_DIRECTORY_NAME;
	}

	@Override
	protected void convertFiles(final List<File> files)
			throws BuildException {
		if (!files.isEmpty()) {

			try {
				final DocumentBuilder builder = getDocumentBuilder();
				final Transformer transformer = getTransformer();

				for (final File file : files) {
					log("converted file: " + file.getName()); //$NON-NLS-1$
					if (file.getName().toUpperCase().endsWith(TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT)
							|| file.getName().toUpperCase().endsWith(TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT)) {

						Document document = builder.parse(file);
						document = removeAllAlgorithms(document);

						final DOMSource src = new DOMSource(document);
						final StreamResult res = new StreamResult(
								new File(new File(exportDirectory) + File.separator + file.getName()));
						transformer.transform(src, res);

					}
				}
			} catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
				// rethrow the caught exception as build exception to inform build system about problems.
				throw new BuildException(e);
			}
		}

	}

	private static Document removeAllAlgorithms(final Document doc) {
		NodeList algo = doc.getElementsByTagName(SIMPLEFB_XML_ATTRIBUTE);
		for (int i = 0; i < algo.getLength(); i++) {
			final Node parent = algo.item(i).getParentNode();
			parent.removeChild(algo.item(i));
		}

		algo = doc.getElementsByTagName(BASICFB_XML_ATTRIBUTE);
		for (int i = 0; i < algo.getLength(); i++) {
			final Node parent = algo.item(i).getParentNode();
			parent.removeChild(algo.item(i));
		}

		algo = doc.getElementsByTagName(COMPOSITEFB_XML_ATTRIBUTE);
		for (int i = 0; i < algo.getLength(); i++) {
			final Node parent = algo.item(i).getParentNode();
			parent.removeChild(algo.item(i));
		}

		return doc;
	}

	private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// set all dtd and validation stuff to false
		factory.setFeature("http://xml.org/sax/features/namespaces", false); //$NON-NLS-1$
		factory.setFeature("http://xml.org/sax/features/validation", false); //$NON-NLS-1$
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false); //$NON-NLS-1$
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); //$NON-NLS-1$
		return factory.newDocumentBuilder();
	}

	private static Transformer getTransformer() throws TransformerConfigurationException {
		final TransformerFactory tf = TransformerFactory.newInstance();
		tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); //$NON-NLS-1$
		tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, ""); //$NON-NLS-1$
		return tf.newTransformer();
	}

}
