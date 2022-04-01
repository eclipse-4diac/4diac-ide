/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 * 				 2018, 2020 Johannes Keppler University
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - extracted this helper class from the CommonElementExporter
 *              - changed exporting the Saxx cursor api
 *  Alois Zoitl, Bianca Wiesmayr - extracted code to this common base class
 *  Benjamin Muttenthaler - extracted saveType to this base class, so it can be used by the DataTypeExporter too
 ********************************************************************************/

package org.eclipse.fordiac.ide.model.dataexport;

import java.io.OutputStream;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.DataTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public abstract class AbstractTypeExporter extends CommonElementExporter {
	private final LibraryElement type;

	protected AbstractTypeExporter(final LibraryElement type) {
		super();
		this.type = type;
	}

	protected AbstractTypeExporter(final CommonElementExporter parent) {
		super(parent);
		type = null;
	}

	protected LibraryElement getType() {
		return type;
	}

	protected void createXMLEntries() throws XMLStreamException {
		createNamedElementEntry(getType(), getRootTag());
		addIdentification(getType());
		addVersionInfo(getType());
		createTypeSpecificXMLEntries();
		addEndElement();
	}

	public static void saveType(final PaletteEntry entry) {
		final AbstractTypeExporter exporter = getTypeExporter(entry);

		if (null != exporter) {
			try {
				exporter.createXMLEntries();
			} catch (final XMLStreamException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}

			final WorkspaceJob job = new WorkspaceJob("Save type file: " + entry.getFile().getName()) {
				@Override
				public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
					exporter.writeToFile(entry.getFile(), monitor);
					// "reset" the modification timestamp in the PaletteEntry to avoid reload - as for this timestamp it
					// is not necessary as the data is in memory
					entry.setLastModificationTimestamp(entry.getFile().getModificationStamp());
					// make the edit result available for the reading entities
					entry.setType(EcoreUtil.copy(entry.getTypeEditable()));
					return Status.OK_STATUS;
				}
			};
			job.setRule(getRuleScope(entry));
			job.schedule();
		}
	}

	//Save the model using the Outputstream
	public static void saveType(final PaletteEntry entry, final OutputStream outputStream) {
		final AbstractTypeExporter exporter = getTypeExporter(entry);
		if (exporter != null) {
			try {
				exporter.createXMLEntries();
			} catch (final XMLStreamException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
			exporter.writeToFile(outputStream);
			entry.setLastModificationTimestamp(entry.getFile().getModificationStamp());
		}
	}

	/** Search for the first directory parent which is existing. If none can be found we will return the workspace root.
	 * This directory is then used as scheduling rule for locking the workspace. The direct parent of the entry's file
	 * can not be used as it may need to be created.
	 *
	 *
	 * @param entry the palette entry for which we need the scope
	 * @return the current folder or workspace root */
	private static IContainer getRuleScope(final PaletteEntry entry) {
		IContainer parent = entry.getFile().getParent();
		while(parent != null && !parent.exists()) {
			parent = parent.getParent();
		}
		return (parent != null) ? parent : ResourcesPlugin.getWorkspace().getRoot();
	}

	private static AbstractTypeExporter getTypeExporter(final PaletteEntry entry) {
		if (entry instanceof FBTypePaletteEntry) {
			return new FbtExporter((FBTypePaletteEntry) entry);
		} else if (entry instanceof AdapterTypePaletteEntry) {
			return new AdapterExporter((AdapterTypePaletteEntry) entry);
		} else if (entry instanceof SubApplicationTypePaletteEntry) {
			return new SubApplicationTypeExporter((SubApplicationTypePaletteEntry) entry);
		} else if (entry instanceof DataTypePaletteEntry) {
			return new DataTypeExporter((AnyDerivedType) entry.getTypeEditable());
		}
		return null;
	}

	protected abstract String getRootTag();

	protected abstract void createTypeSpecificXMLEntries() throws XMLStreamException;

	protected void addCompilerInfo(final CompilerInfo compilerInfo) throws XMLStreamException {
		if (null != compilerInfo) {
			addStartElement(LibraryElementTags.COMPILER_INFO_ELEMENT);
			if ((null != compilerInfo.getHeader()) && !"".equals(compilerInfo.getHeader())) { //$NON-NLS-1$
				getWriter().writeAttribute(LibraryElementTags.HEADER_ATTRIBUTE, compilerInfo.getHeader());
			}
			if ((null != compilerInfo.getClassdef()) && !"".equals(compilerInfo.getClassdef())) { //$NON-NLS-1$
				getWriter().writeAttribute(LibraryElementTags.CLASSDEF_ATTRIBUTE, compilerInfo.getClassdef());
			}
			for (final org.eclipse.fordiac.ide.model.libraryElement.Compiler compiler : compilerInfo.getCompiler()) {
				addCompiler(compiler);
			}
			addEndElement();
		}
	}

	/** Adds the compiler.
	 *
	 * @param compiler the compiler
	 * @throws XMLStreamException */
	private void addCompiler(final org.eclipse.fordiac.ide.model.libraryElement.Compiler compiler)
			throws XMLStreamException {
		addEmptyStartElement(LibraryElementTags.COMPILER_ELEMENT);
		getWriter().writeAttribute(LibraryElementTags.LANGUAGE_ATTRIBUTE,
				(null != compiler.getLanguage()) ? compiler.getLanguage().getName() : ""); //$NON-NLS-1$
		getWriter().writeAttribute(LibraryElementTags.VENDOR_ATTRIBUTE,
				(null != compiler.getVendor()) ? compiler.getVendor() : ""); //$NON-NLS-1$
		getWriter().writeAttribute(LibraryElementTags.PRODUCT_ATTRIBUTE,
				(null != compiler.getProduct()) ? compiler.getProduct() : ""); //$NON-NLS-1$
		getWriter().writeAttribute(LibraryElementTags.VERSION_ATTRIBUTE,
				(null != compiler.getVersion()) ? compiler.getVersion() : ""); //$NON-NLS-1$
	}

	/** Adds the variable.
	 *
	 * @param varDecl the var decl
	 * @throws XMLStreamException */
	protected void addVarDeclaration(final VarDeclaration varDecl) throws XMLStreamException {
		final boolean hasAttributes = !varDecl.getAttributes().isEmpty();
		if (hasAttributes) {
			addStartElement(LibraryElementTags.VAR_DECLARATION_ELEMENT);
		} else {
			addEmptyStartElement(LibraryElementTags.VAR_DECLARATION_ELEMENT);
		}

		addNameTypeCommentAttribute(varDecl, varDecl.getType());
		if (varDecl.isArray()) {
			getWriter().writeAttribute(LibraryElementTags.ARRAYSIZE_ATTRIBUTE,
					Integer.toString(varDecl.getArraySize()));
		}
		if ((null != varDecl.getValue()) && (!varDecl.getValue().getValue().isEmpty())) {
			getWriter().writeAttribute(LibraryElementTags.INITIALVALUE_ATTRIBUTE, varDecl.getValue().getValue());
		}

		if (hasAttributes) {
			addAttributes(varDecl.getAttributes());
			addEndElement();
		}
	}

}
