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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document

import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.util.EContentAdapter
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

class STAlgorithmDocumentFBTypeUpdater extends Job {
	final STAlgorithmFBTypeChangeAdapter changeAdapter

	@Accessors(PUBLIC_GETTER) STAlgorithmDocument document
	@Accessors(PUBLIC_GETTER) volatile boolean paused = false
	@Accessors long delay = 500

	new() {
		super("STAlgorithmDocumentFBTypeUpdater")
		this.changeAdapter = new STAlgorithmFBTypeChangeAdapter(this)
		setPriority(Job.SHORT)
		setSystem(true)
	}

	override protected run(IProgressMonitor monitor) {
		if(monitor.canceled || paused) return Status.CANCEL_STATUS;
		document.internalModify [ state |
			doRun(state as STAlgorithmResource, monitor)
			null
		]
		if(monitor.canceled) return Status.CANCEL_STATUS;
		return Status.OK_STATUS;
	}

	def protected void doRun(STAlgorithmResource resource, IProgressMonitor monitor) {
		val fbType = resource.fbType
		if (fbType !== null) {
			val typeEntry = fbType.typeEntry
			if (typeEntry !== null) {
				val libraryElement = typeEntry.typeEditable
				if (libraryElement instanceof FBType) {
					resource.fbType = libraryElement
					resource.relink
				}
			}
		}
	}

	def void install(STAlgorithmDocument document) {
		if(this.document !== null) uninstall
		this.document = document
		changeAdapter.install(document)
		handleFBTypeChanged
	}

	def void uninstall() {
		changeAdapter.uninstall(document)
		cancel
		this.document = null
	}

	def void pause() {
		paused = true
	}

	def void resume() {
		paused = false
		schedule(delay)
	}

	def protected handleFBTypeChanged() {
		cancel
		if (!paused) {
			schedule(delay)
		}
	}

	@FinalFieldsConstructor
	static class STAlgorithmFBTypeChangeAdapter extends EContentAdapter {
		final STAlgorithmDocumentFBTypeUpdater reconciler

		override notifyChanged(Notification notification) {
			super.notifyChanged(notification)
			reconciler.handleFBTypeChanged
		}

		def void install(STAlgorithmDocument document) {
			document?.readOnly [ state |
				if (state instanceof STAlgorithmResource) {
					val fbType = state.fbType
					if (fbType !== null) {
						val fbTypeAdapters = fbType.eAdapters
						if (!fbTypeAdapters.contains(this)) {
							fbTypeAdapters.add(this)
						}
					}
				}
				null
			]
		}

		def void uninstall(STAlgorithmDocument document) {
			document?.readOnly [ state |
				if (state instanceof STAlgorithmResource) {
					val fbType = state.fbType
					if (fbType !== null) {
						val fbTypeAdapters = fbType.eAdapters
						fbTypeAdapters.remove(this)
					}
				}
				null
			]
		}

		override useRecursion() { false }
	}
}
