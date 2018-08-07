/********************************************************************************
 * Copyright (c) 2008 - 2018  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Martijn Rooker,Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.VarInitialization;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ImportUtils {
	
	private ImportUtils() {
		//empty private constructor so that this utility class can not be insantiated
	}

	/**
	 * This method parses Parameters.
	 * 
	 * @param node
	 *            - the node in the DTD containing the Parameter
	 * 
	 * @return p - the parsed Parameter
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	public static VarDeclaration parseParameter(final Node node) throws TypeImportException {
		Attribute p = LibraryElementFactory.eINSTANCE.createAttribute();
		VarDeclaration var = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		NamedNodeMap map = node.getAttributes();
		Node name = map.getNamedItem(LibraryElementTags.NAME_ATTRIBUTE);
		if (name != null) {
			p.setName(name.getNodeValue());
			var.setName(name.getNodeValue());
		} else {
			throw new TypeImportException(Messages.ImportUtils_ERROR_ParameterNotSet);
		}
		Node value = map.getNamedItem(LibraryElementTags.VALUE_ATTRIBUTE);
		if (value != null) {
			p.setValue(value.getNodeValue());
			Value val = LibraryElementFactory.eINSTANCE.createValue();
			val.setValue(value.getNodeValue());
			var.setValue(val);
		} else {
			throw new TypeImportException(Messages.ImportUtils_ERROR_ParameterValueNotSet);
		}
		Node comment = map.getNamedItem(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (comment != null) {
			p.setComment(comment.getNodeValue());
			var.setComment(comment.getNodeValue());
		}
		return var;
	}

	/**
	 * This method parses InputVariables.
	 * 
	 * @param node
	 *            - the node in the DTD containing the InputVariables
	 * 
	 * @return varDecl - A list containing all the parsed InputVariables
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	public static List<VarDeclaration> parseInputVariables(final Node node) throws TypeImportException {
		NodeList childNodes = node.getChildNodes();
		ArrayList<VarDeclaration> varDecl = new ArrayList<VarDeclaration>();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(LibraryElementTags.VAR_DECLARATION_ELEMENT)) {
				VarDeclaration var = parseVarDeclaration(n);
				((IInterfaceElement) var).setIsInput(true);
				varDecl.add(var);
			}
		}
		return varDecl;
	}

	/**
	 * This method parses OutputVariables.
	 * 
	 * @param node
	 *            - the node in the DTD containing the OutputVariables
	 * 
	 * @return varDecl - A list containing all the parsed OutputVariables
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	public static List<VarDeclaration> parseOutputVariables(final Node node) throws TypeImportException {
		NodeList childNodes = node.getChildNodes();
		ArrayList<VarDeclaration> varDecl = new ArrayList<VarDeclaration>();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			if (n.getNodeName().equals(LibraryElementTags.VAR_DECLARATION_ELEMENT)) {
				VarDeclaration var = parseVarDeclaration(n);
				((IInterfaceElement) var).setIsInput(false);
				varDecl.add(var);
			}
		}
		return varDecl;
	}

	/**
	 * This method parses VariableDeclaration.
	 * 
	 * @param n
	 *            - the node in the DTD containing the VariableDeclaration
	 * 
	 * @return v - the parsed VariableDeclaration
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	public static VarDeclaration parseVarDeclaration(final Node n) throws TypeImportException {
		NamedNodeMap map = n.getAttributes();
		VarDeclaration v = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		Node name = map.getNamedItem(LibraryElementTags.NAME_ATTRIBUTE);
		if (name != null) {
			v.setName(name.getNodeValue());
		} else {
			throw new TypeImportException(Messages.ImportUtils_ERROR_InputVariableNameNotDefined);
		}
		Node type = map.getNamedItem(LibraryElementTags.TYPE_ATTRIBUTE);
		if (type != null) {
			DataType dataType = DataTypeLibrary.getInstance().getType(type.getNodeValue());
			v.setTypeName(type.getNodeValue());
			if (dataType != null) {
				v.setType(dataType);
			}
		} else {
			throw new TypeImportException(Messages.ImportUtils_ERROR_InputVariableTypeNotDefined);
		}
		Node arraySize = map.getNamedItem(LibraryElementTags.ARRAYSIZE_ATTRIBUTE);
		if (arraySize != null) {
			try {
				v.setArraySize(Integer.parseInt(arraySize.getNodeValue()));
			} catch (NumberFormatException nfe) {
				throw new TypeImportException(Messages.ImportUtils_ERROR_Arraysize_NumberFormat);
			}
		} else {
			v.setArraySize(-1);
		}

		Node initialValue = map.getNamedItem(LibraryElementTags.INITIALVALUE_ATTRIBUTE);
		if (initialValue != null) {
			VarInitialization varInitialization = DataFactory.eINSTANCE.createVarInitialization();
			varInitialization.setInitialValue(initialValue.getNodeValue());
			v.setVarInitialization(varInitialization);
		}
		Node comment = map.getNamedItem(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (comment != null) {
			v.setComment(comment.getNodeValue());
		}
		return v;
	}

	/**
	 * This method parsed Events.
	 * 
	 * @param n
	 *            - the node in the DTD containing the Event
	 * 
	 * @return e - the parsed Event
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	public static Event parseEvent(final Node n) throws TypeImportException {
		NamedNodeMap map = n.getAttributes();
		Event e = LibraryElementFactory.eINSTANCE.createEvent();
		e.setType(EventTypeLibrary.getInstance().getType(null));
		Node name = map.getNamedItem(LibraryElementTags.NAME_ATTRIBUTE);
		if (name != null) {
			e.setName(name.getNodeValue());
		} else {
			throw new TypeImportException(Messages.ImportUtils_ERROR_InputEventNameNotDefined);
		}
		Node comment = map.getNamedItem(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (comment != null) {
			e.setComment(comment.getNodeValue());
		}
		return e;
	}

	/**
	 * Convert coordinate.
	 * 
	 * @param value
	 *            the value
	 * 
	 * @return the int value
	 */
	public static int convertCoordinate(final double value) {
		double lineHeight = 20;  //TODO make this resolution dependant and font size dependant
		return (int)(lineHeight / 100.0 * value);
	}

	/**
	 * returns an valid dx, dy integer value
	 * 
	 * @param value
	 * @return if value is valid the converted int of that otherwise 0
	 */
	public static int parseConnectionValue(String value) {
		try {
			return ImportUtils.convertCoordinate(Double.parseDouble(value));
		} catch (Exception ex) {
			return 0;
		}
	}

	public static AdapterEvent createAdapterEvent(Event event, AdapterDeclaration a) {
		AdapterEvent ae = LibraryElementFactory.eINSTANCE.createAdapterEvent();
		ae.setName(event.getName());
		ae.setComment(event.getComment());
		ae.setAdapterDeclaration(a);

		return ae;
	}

	/**
	 * A helper method to get a platform independent regex for the split method.
	 * 
	 * @return a "file.separator" regex
	 */
	public static String getSeperatorRegex() {
		String regex = File.separator;
		if (regex.equals("\\")) { //$NON-NLS-1$
			regex = "\\\\"; //$NON-NLS-1$
		}
		return regex;
	}

	/**
	 * copies a file from in to out.
	 * 
	 * @param in
	 *            source File
	 * @param out
	 *            destination File
	 * 
	 * @throws Exception
	 * *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyFile(final File in, final File out) throws IOException {
		Files.copy(in.toPath(), out.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	public static void copyFile(final File in, final org.eclipse.core.resources.IFile out)
			throws IOException, CoreException {
		if (!out.getParent().exists()) {
			// create folder if does not exist
			((IFolder) out.getParent()).create(true, true, null);
			out.getParent().refreshLocal(IResource.DEPTH_ZERO, null);
		}

		Files.copy(in.toPath(), out.getLocation().toFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
		
		try {
			out.getParent().refreshLocal(IResource.DEPTH_ONE, null);
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

}
