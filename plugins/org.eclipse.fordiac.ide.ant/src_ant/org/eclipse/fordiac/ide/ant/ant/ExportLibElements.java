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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
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

	private final String ANT_CONVERT_TASK_DIRECTORY_NAME = "converted_FBs"; //$NON-NLS-1$

	private final String SIMPLEFB_XML_ATTRIBUTE = "SimpleFB"; //$NON-NLS-1$
	private final String BASICFB_XML_ATTRIBUTE = "BasicFB"; //$NON-NLS-1$
	private final String COMPOSITEFB_XML_ATTRIBUTE = "FBNetwork"; //$NON-NLS-1$

	@Override
	protected String getExportDirectoryDefault() {
		return ANT_CONVERT_TASK_DIRECTORY_NAME;
	}

	@Override
	protected void convertFiles(final List<File> files)
			throws BuildException {
		if (!files.isEmpty()) {

			// read in
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// set all dtd and validation stuff to false
			try {
				factory.setFeature("http://xml.org/sax/features/namespaces", false);
				factory.setFeature("http://xml.org/sax/features/validation", false);
				factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
				factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
				final DocumentBuilder builder = factory.newDocumentBuilder();

				// save
				final TransformerFactory tf = TransformerFactory.newInstance();
				final Transformer transformer = tf.newTransformer();

				for (final File file : files) {
					System.out.println("converted file: " + file.getName());
					if (file.getName().toUpperCase().endsWith(TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT)) {

						Document document = builder.parse(file);
						document = removeAllAlgorithms(document);

						final DOMSource src = new DOMSource(document);
						final StreamResult res = new StreamResult(
								new File(new File(exportDirectory) + File.separator + file.getName()));
						transformer.transform(src, res);

					}
				}
			} catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
				// TODO Auto-generated catch block
				throw new BuildException(e);
			}
		}

	}

	private Document removeAllAlgorithms(final Document doc) {
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

}
