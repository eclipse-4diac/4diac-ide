/**
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.quickfix

import java.text.MessageFormat
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmFactory
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.Messages
import org.eclipse.fordiac.ide.structuredtextalgorithm.validation.STAlgorithmValidator
import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.STCoreQuickfixProvider
import org.eclipse.xtext.ui.editor.quickfix.Fix
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor
import org.eclipse.xtext.validation.Issue

class STAlgorithmQuickfixProvider extends STCoreQuickfixProvider {
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
					val fbType = resource.fbType
					if (fbType instanceof SimpleFBType) {
						container.elements.removeIf [ sourceElement |
							sourceElement instanceof STAlgorithm && !fbType.interfaceList.eventInputs.exists [ event |
								event.name == sourceElement.name
							]
						]
					}
				}
			}
		]
	}
}
