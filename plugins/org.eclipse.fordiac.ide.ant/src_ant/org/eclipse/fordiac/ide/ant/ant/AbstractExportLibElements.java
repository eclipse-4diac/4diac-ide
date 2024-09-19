/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
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
 *   Ernst Blecha - refactoring of base classes for ant tasks
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.io.IOException;

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
import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.export.ExportFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class AbstractExportLibElements extends AbstractFBTask {
	private static final String ANT_CONVERT_TASK_DIRECTORY_NAME = "converted_FBs"; //$NON-NLS-1$

	private static final String SIMPLEFB_XML_ATTRIBUTE = "SimpleFB"; //$NON-NLS-1$
	private static final String BASICFB_XML_ATTRIBUTE = "BasicFB"; //$NON-NLS-1$
	private static final String COMPOSITEFB_XML_ATTRIBUTE = "FBNetwork"; //$NON-NLS-1$
	private static final String FUNCTIONBODY_XML_ATTRIBUTE = "FunctionBody"; //$NON-NLS-1$

	private final DocumentBuilder builder = getDocumentBuilder();
	private final Transformer transformer = getTransformer();

	@Override
	protected String getExportDirectoryDefault() {
		return ANT_CONVERT_TASK_DIRECTORY_NAME;
	}

	@Override
	protected ExportFilter getExportFilter() {
		return null; // This class does not use an ExportFilter
	}

	@Override
	protected void exportFile(final File folder, final IFile file) throws BuildException {
		log(file.toString());

		Document document;
		try {
			document = builder.parse(file.getLocation().toFile());
		} catch (IOException | SAXException e) {
			throw new BuildException(e);
		}
		removeAllAlgorithms(document);

		final DOMSource src = new DOMSource(document);
		final StreamResult res = new StreamResult(new File(folder + File.separator + file.getName()));
		try {
			transformer.transform(src, res);
		} catch (final TransformerException e) {
			throw new BuildException(e);
		}
	}

	private static void removeAllAlgorithms(final Document doc) {
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

		algo = doc.getElementsByTagName(FUNCTIONBODY_XML_ATTRIBUTE);
		for (int i = 0; i < algo.getLength(); i++) {
			final Node parent = algo.item(i).getParentNode();
			parent.removeChild(algo.item(i));
		}

	}

	private static DocumentBuilder getDocumentBuilder() throws BuildException {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// set all dtd and validation stuff to false
		try {
			factory.setFeature("http://xml.org/sax/features/namespaces", false); //$NON-NLS-1$
			factory.setFeature("http://xml.org/sax/features/validation", false); //$NON-NLS-1$
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false); //$NON-NLS-1$
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); //$NON-NLS-1$
			return factory.newDocumentBuilder();
		} catch (final ParserConfigurationException e) {
			throw new BuildException(e);
		}
	}

	private static Transformer getTransformer() throws BuildException {
		final TransformerFactory tf = TransformerFactory.newInstance();
		tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); //$NON-NLS-1$
		tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, ""); //$NON-NLS-1$
		try {
			return tf.newTransformer();
		} catch (final TransformerConfigurationException e) {
			throw new BuildException(e);
		}
	}
}
