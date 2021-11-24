/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.handlers

import java.io.File
import java.io.IOException
import java.util.Collections
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.core.resources.IFile
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
import org.eclipse.fordiac.ide.ui.FordiacLogHelper
import org.eclipse.jface.viewers.ISelection
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.ui.ISources
import org.eclipse.ui.handlers.HandlerUtil
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.util.LazyStringInputStream

class ExportXMIHandler extends AbstractHandler {

	static final String SYNTHETIC_URI_NAME = "__synthetic" // $NON-NLS-1$
	static final String URI_SEPERATOR = "." // $NON-NLS-1$
	static final String FB_URI_EXTENSION = "xtextfbt" // $NON-NLS-1$
	static final String ST_URI_EXTENSION = "st" // $NON-NLS-1$
	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(URI.createURI(SYNTHETIC_URI_NAME + URI_SEPERATOR + ST_URI_EXTENSION))

	def protected URI computeUnusedUri(ResourceSet resourceSet, String fileExtension) {
		for (i : 0 ..< Integer.MAX_VALUE) {
			val syntheticUri = URI.createURI(SYNTHETIC_URI_NAME + i + URI_SEPERATOR + fileExtension) // $NON-NLS-1$
			if (resourceSet.getResource(syntheticUri, false) === null) {
				return syntheticUri
			}
		}
		throw new IllegalStateException()
	}

	def createFBResource(XtextResourceSet resourceSet, SimpleFBType fbType) {
		// create resource for function block and add copy
		val fbResource = resourceSet.createResource(resourceSet.computeUnusedUri(FB_URI_EXTENSION))
		fbResource.contents.add(fbType)
		fbType.interfaceList.sockets.forEach[adp|createAdapterResource(resourceSet, adp)];
		fbType.interfaceList.plugs.forEach[adp|createAdapterResource(resourceSet, adp)];
		fbType.interfaceList.inputVars.forEach[v|createStructResource(resourceSet, v)];
		fbType.interfaceList.outputVars.forEach[v|createStructResource(resourceSet, v)];
		fbType.internalVars.forEach[v|createStructResource(resourceSet, v)];
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
		val entry = TypeLibrary::getPaletteEntryForFile(fbFile)
		val simpleType = entry?.getType() as SimpleFBType
		val algorithmText = (simpleType?.algorithm as STAlgorithm)?.getText()
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet) as XtextResourceSet
		createFBResource(resourceSet, simpleType)
		// create resource for algorithm
		val resource = resourceSet.createResource(resourceSet.computeUnusedUri(ST_URI_EXTENSION)) as XtextResource
		resource.load(new LazyStringInputStream(algorithmText), #{XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE})
		val stalg = resource.parseResult.rootASTElement as StructuredTextAlgorithm
		stalg.localVariables.forEach[v|createStructResource(resourceSet, v)]

		val registry = Resource.Factory.Registry.INSTANCE
		val m = registry.extensionToFactoryMap
		m.put("xmi", new XMIResourceFactoryImpl);

		val xmiResourceSet = new ResourceSetImpl
		val uri = URI.createFileURI(new File(fbFile.location.makeAbsolute + ".xmi").absolutePath)
		val xmiRessource = xmiResourceSet.createResource(uri)
		xmiRessource.contents.add(simpleType)
		xmiRessource.contents.add((resource.contents.get(0)))
		EcoreUtil.resolveAll(xmiRessource)
		try {
			xmiRessource.save(Collections.EMPTY_MAP)
		} catch (IOException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		return null
	}

	override void setEnabled(Object evaluationContext) {
		var ISelection selection = (HandlerUtil::getVariable(evaluationContext,
			ISources::ACTIVE_CURRENT_SELECTION_NAME) as ISelection)
		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			var IStructuredSelection structuredSelection = (selection as IStructuredSelection)
			if (structuredSelection.size() === 1 && (structuredSelection.getFirstElement() instanceof IFile)) {
				var IFile fbFile = (structuredSelection.getFirstElement() as IFile)
				var PaletteEntry entry = TypeLibrary::getPaletteEntryForFile(fbFile)
				if (null !== entry) {
					setBaseEnabled(entry.getType() instanceof SimpleFBType)
					return;
				}
			}
		}
		setBaseEnabled(false)
	}
}
