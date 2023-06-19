/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
 *                          Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 * 	 Christoph Binder - Extracted code from STAlgorithmDocumentFBTypeUpdater, to enable possibility to reuse this class for multiple xtexteditors
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.document

import com.google.inject.Inject
import com.google.inject.name.Named
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.util.EContentAdapter
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.structuredtextcore.FBTypeXtextResource
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.Constants

class FBTypeXtextDocumentUpdater extends Job{
	final FBTypeChangeAdapter changeAdapter

	@Accessors(PUBLIC_GETTER) FBTypeXtextDocument document
	@Accessors(PUBLIC_GETTER) volatile boolean paused = false
	@Accessors long delay = 500

	@Inject
	new(@Named(Constants.LANGUAGE_NAME) String name) {
		super(name)
		this.changeAdapter = new FBTypeChangeAdapter(this)
		setPriority(Job.SHORT)
		setSystem(true)
	}

	override protected run(IProgressMonitor monitor) {
		if(monitor.canceled || paused) return Status.CANCEL_STATUS;
		document?.internalModify [ state |
			doRun(state as FBTypeXtextResource, monitor)
			null
		]
		if(monitor.canceled) return Status.CANCEL_STATUS;
		return Status.OK_STATUS;
	}

	def protected void doRun(FBTypeXtextResource resource, IProgressMonitor monitor) {
		val fbType = resource.fbType
		if (fbType !== null) {
			val typeEntry = fbType.typeEntry
			if (typeEntry !== null) {
				val libraryElement = typeEntry.typeEditable
				if (libraryElement instanceof FBType) {
					resource.fbType = libraryElement
				}
			}
		}
	}

	def void install(FBTypeXtextDocument document) {
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
	static class FBTypeChangeAdapter extends EContentAdapter {
		final FBTypeXtextDocumentUpdater reconciler

		override notifyChanged(Notification notification) {
			super.notifyChanged(notification)
			reconciler.handleFBTypeChanged
		}

		def void install(FBTypeXtextDocument document) {
			document?.readOnly [ state |
				if (state instanceof FBTypeXtextResource) {
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

		def void uninstall(FBTypeXtextDocument document) {
			document?.readOnly [ state |
				if (state instanceof FBTypeXtextResource) {
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
