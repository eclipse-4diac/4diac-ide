/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 * 			     - update XMI exporter for FUNCTION and new STAlgorithm grammar
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.handlers

import java.io.File
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.core.resources.IFile
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags
import org.eclipse.fordiac.ide.ui.FordiacLogHelper
import org.eclipse.jface.viewers.ISelection
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.ui.ISources
import org.eclipse.ui.handlers.HandlerUtil
import org.eclipse.xtext.resource.XtextResourceSet

import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*

class ExportXMIHandler extends AbstractHandler {

	static final String SYNTHETIC_URI_NAME = "__synthetic" // $NON-NLS-1$
	static final String URI_SEPERATOR = "." // $NON-NLS-1$
	static final String FB_URI_EXTENSION = "xtextfbt" // $NON-NLS-1$
	static final String STFUNC_FILE_EXTENSION = "stfunc" // $NON-NLS-1$
	static final String XMI_EXTENSION = "xmi" // $NON-NLS-1$
	static final String XMI_EXTENSION_WITH_DOT = "." + XMI_EXTENSION // $NON-NLS-1$

	def protected URI computeUnusedUri(ResourceSet resourceSet, String fileExtension) {
		for (i : 0 ..< Integer.MAX_VALUE) {
			val syntheticUri = URI.createURI(SYNTHETIC_URI_NAME + i + URI_SEPERATOR + fileExtension) // $NON-NLS-1$
			if (resourceSet.getResource(syntheticUri, false) === null) {
				return syntheticUri
			}
		}
		throw new IllegalStateException()
	}

	def void createAdapterResource(XtextResourceSet resourceSet, AdapterDeclaration adapter) {
		val adapterResource = resourceSet.createResource(resourceSet.computeUnusedUri(FB_URI_EXTENSION));
		adapterResource.contents.add(adapter.type.adapterFBType);
	}

	def void createStructResource(XtextResourceSet resourceSet, VarDeclaration variable) {
		if (variable.type instanceof StructuredType) {
			val structResource = resourceSet.createResource(resourceSet.computeUnusedUri(FB_URI_EXTENSION));
			val type = variable.type as StructuredType;
			structResource.contents.add(type);
			type.memberVariables.forEach[v|createStructResource(resourceSet, v)];
		}
	}

	override Object execute(ExecutionEvent event) throws ExecutionException {
		val selection = HandlerUtil::getCurrentSelection(event) as IStructuredSelection
		val fbFile = selection?.firstElement as IFile

		val xmiResourceSet = new ResourceSetImpl
		val uri = URI.createFileURI(new File(fbFile.location.makeAbsolute + XMI_EXTENSION_WITH_DOT).absolutePath)
		val xmiRessource = xmiResourceSet.createResource(uri)
		val registry = Resource.Factory.Registry.INSTANCE
		val m = registry.extensionToFactoryMap
		m.put(XMI_EXTENSION, new XMIResourceFactoryImpl);

		if (fbFile.fileExtension.equalsIgnoreCase(TypeLibraryTags.FB_TYPE_FILE_ENDING)) {
			val entry = TypeLibrary::getTypeEntryForFile(fbFile)
			val simpleType = entry?.getType() as SimpleFBType
			val List<String> errors = new ArrayList
			simpleType.callables.forEach [
				if (it instanceof STAlgorithm) {
					xmiRessource.contents.add(it.parse(errors))
				}
			]
		}

		if (fbFile.fullPath.fileExtension.equals(STFUNC_FILE_EXTENSION)) {
			val resourceSet = new ResourceSetImpl
			val resource = resourceSet.getResource(URI.createPlatformResourceURI(fbFile.getFullPath().toString(), true),
				true)
			xmiRessource.contents.addAll(EcoreUtil.copyAll(resource.contents))
		}

		EcoreUtil.resolveAll(xmiRessource)
		try {
			val optionsMap = new HashMap
			optionsMap.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD)
			xmiRessource.save(optionsMap)
		} catch (IOException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		return null
	}

	override void setEnabled(Object evaluationContext) {
		val selection = (HandlerUtil::getVariable(evaluationContext,
			ISources::ACTIVE_CURRENT_SELECTION_NAME) as ISelection)
		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			val structuredSelection = (selection as IStructuredSelection)
			if (structuredSelection.size() === 1 && (structuredSelection.getFirstElement() instanceof IFile)) {
				val fbFile = (structuredSelection.getFirstElement() as IFile)
				if (fbFile.fullPath.fileExtension.equals(STFUNC_FILE_EXTENSION)) {
					setBaseEnabled(true)
					return
				}
				val entry = TypeLibrary::getTypeEntryForFile(fbFile)
				if (null !== entry) {
					setBaseEnabled(entry.getType() instanceof SimpleFBType)
					return;
				}
			}
		}
		setBaseEnabled(false)
	}
}
