/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
 *               2022 Martin Erich Jobst
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
 *   Martin Jobst
 *     - refactor export
 *     - add comments export
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.handlers

import java.io.IOException
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.core.resources.IFile
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.resource.STAlgorithmResourceSetInitializer
import org.eclipse.fordiac.ide.ui.FordiacLogHelper
import org.eclipse.jface.viewers.ISelection
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.ui.ISources
import org.eclipse.ui.handlers.HandlerUtil
import org.eclipse.xtext.linking.lazy.LazyLinkingResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreCommentAssociater

class ExportXMIHandler extends AbstractHandler {

	static final String STFUNC_FILE_EXTENSION = "stfunc" // $NON-NLS-1$
	static final String XMI_EXTENSION = "xmi" // $NON-NLS-1$

	override Object execute(ExecutionEvent event) throws ExecutionException {
		val selection = HandlerUtil::getCurrentSelection(event) as IStructuredSelection
		val file = selection?.firstElement as IFile
		val uri = URI.createPlatformResourceURI(file.fullPath.toString, true)
		val xmiUri = uri.trimFileExtension.appendFileExtension(XMI_EXTENSION)

		val resourceSet = new XtextResourceSet
		new STAlgorithmResourceSetInitializer().initialize(resourceSet, file.project)
		resourceSet.addLoadOption(LazyLinkingResource.OPTION_RESOLVE_ALL, Boolean.TRUE)
		val resource = resourceSet.getResource(uri, true)

		if (resource instanceof XtextResource) {
			val source = resource.contents.get(0)
			if (source instanceof STSource) {
				val commentAssociater = resource.resourceServiceProvider.get(STCoreCommentAssociater)
				if (commentAssociater !== null) {
					source.comments.addAll(commentAssociater.associateComments(source))
				}
			}
		}

		val xmiResourceSet = new ResourceSetImpl
		Resource.Factory.Registry.INSTANCE.extensionToFactoryMap.putIfAbsent(XMI_EXTENSION, new XMIResourceFactoryImpl)
		val xmiRessource = xmiResourceSet.createResource(xmiUri)
		xmiRessource.contents.addAll(EcoreUtil.copyAll(resource.contents))

		try {
			xmiRessource.save(
				#{XMLResource.OPTION_PROCESS_DANGLING_HREF -> XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD})
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
				val entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fbFile)
				if (null !== entry) {
					setBaseEnabled(entry.getType() instanceof SimpleFBType)
					return;
				}
			}
		}
		setBaseEnabled(false)
	}
}
