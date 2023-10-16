/**
 * Copyright (c) 2022 Martin Erich Jobst
 *               2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst 
 * 	 - initial API and implementation and/or initial documentation
 *   Ulzii Jargalsaikhan
 *   	 - add quick fixes for suggesting similar variables
 */
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.quickfix

import com.google.common.collect.Iterables
import com.google.inject.Inject
import java.text.MessageFormat
import javax.swing.text.BadLocationException
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmFactory
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.Messages
import org.eclipse.fordiac.ide.structuredtextalgorithm.validation.STAlgorithmValidator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.STCoreQuickfixProvider
import org.eclipse.gef.commands.Command
import org.eclipse.gef.commands.CommandStack
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.diagnostics.Diagnostic
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.IURIEditorOpener
import org.eclipse.xtext.ui.editor.quickfix.Fix
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor
import org.eclipse.xtext.ui.editor.quickfix.ReplaceModification
import org.eclipse.xtext.validation.Issue

class STAlgorithmQuickfixProvider extends STCoreQuickfixProvider {
	@Inject
	IURIEditorOpener editorOpener

	@Fix(STAlgorithmValidator.NO_ALGORITHM_FOR_INPUT_EVENT)
	def void fixNoAlgorithmForInputEvent(Issue issue, IssueResolutionAcceptor acceptor) {
		val String eventName = issue.getData().get(0)
		acceptor.accept(issue,
			MessageFormat.format(Messages.STAlgorithmQuickfixProvider_Add_missing_algorithm, eventName),
			MessageFormat.format(Messages.STAlgorithmQuickfixProvider_Add_missing_algorithm_for_input_event, eventName),
			null) [ element, context |
			if (element instanceof STAlgorithmSource) {
				element.elements += STAlgorithmFactory.eINSTANCE.createSTAlgorithm => [
					name = eventName
					body = STAlgorithmFactory.eINSTANCE.createSTAlgorithmBody
				]
			}
		]
	}

	@Fix(STAlgorithmValidator.NO_INPUT_EVENT_FOR_ALGORITHM)
	def void fixNoInputEventForAlgorithm(Issue issue, IssueResolutionAcceptor acceptor) {
		val String name = issue.getData().get(0)
		acceptor.accept(issue, MessageFormat.format(Messages.STAlgorithmQuickfixProvider_Remove_unused_algorithm, name),
			MessageFormat.format(Messages.STAlgorithmQuickfixProvider_Remove_unused_algorithm_for_input_event, name),
			null) [ element, context |
			val container = element.eContainer
			if (container instanceof STAlgorithmSource) {
				container.elements.remove(element)
			}
		]
		acceptor.accept(issue, Messages.STAlgorithmQuickfixProvider_Remove_all_unused_algorithms,
			Messages.STAlgorithmQuickfixProvider_Remove_all_unused_algorithms, null) [ element, context |
			val container = element.eContainer
			if (container instanceof STAlgorithmSource) {
				val resource = container.eResource
				if (resource instanceof STAlgorithmResource) {
					val libraryElement = resource.libraryElement
					if (libraryElement instanceof SimpleFBType) {
						container.elements.removeIf [ sourceElement |
							sourceElement instanceof STAlgorithm && !libraryElement.interfaceList.eventInputs.exists [ event |
								event.name == sourceElement.name
							]
						]
					}
				}
			}
		]
	}

	@Fix(Diagnostic.LINKING_DIAGNOSTIC)
	def void suggestSimilarVariable(Issue issue, IssueResolutionAcceptor acceptor) throws BadLocationException{
		val modificationContext = modificationContextFactory.createModificationContext(issue)
		val xtextDocument = modificationContext.xtextDocument
		if (xtextDocument !== null) {
			val resolvedElement = xtextDocument.readOnly([ XtextResource resource |
				offsetHelper.resolveContainedElementAt(resource, issue.offset)
			])
			val EObject varContainer = EcoreUtil2.getContainerOfType(resolvedElement, typeof(STAlgorithm)) ?:
				EcoreUtil2.getContainerOfType(resolvedElement, typeof(STMethod))

			val issueString = xtextDocument.get(issue.offset, issue.length)

			EcoreUtil2.getAllContentsOfType(varContainer, typeof(STVarDeclaration)).filter [
				similarityMatcher.isSimilar(issueString, it.name)
			].forEach [
				val name = it.name
				acceptor.accept(issue, "Change to existing variable " + "\'" + name + "\'",
					"Change to existing variable" + "\'" + name + "\'", null, new ReplaceModification(issue, it.name))
			]

			val resource = varContainer.eResource
			val libraryElement = resource instanceof STAlgorithmResource ? resource.libraryElement : null // As we are in an Algorithm editor, we are always in a BaseFBType (Simple or Basic)
			if (libraryElement instanceof BaseFBType) {
				val iList = libraryElement.interfaceList
				val fbVarCandidates = Iterables.concat(iList.inputVars, iList.outputVars, libraryElement.internalVars)
				fbVarCandidates.filter[similarityMatcher.isSimilar(issueString, it.name)].forEach [
					val name = it.name
					acceptor.accept(issue, "Change to existing variable " + "\'" + name + "\'",
						"Change to existing variable" + "\'" + name + "\'", null,
						new ReplaceModification(issue, it.name))
				]

			}
		}
	}

	override protected void createImportProposal(Issue issue, String label, String importedNamespace,
		IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, label, label, null, [ element, context |
			val resource = element.eResource
			if(resource instanceof STAlgorithmResource) {
				executeCommand(issue, new AddNewImportCommand(resource.libraryElement, importedNamespace))
			}
		], 100);
	}
	
	def protected void executeCommand(Issue issue, Command command) {
		executeCommand(issue.uriToProblem, command)
	}
	
	def protected void executeCommand(URI uri, Command command) {
		val editor = editorOpener.open(uri, false);
		val commandStack = editor.getAdapter(CommandStack)
		if (commandStack !== null) {
			commandStack.execute(command)
		}
	}
}
