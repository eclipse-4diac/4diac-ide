/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.adapter;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEditChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.adapter.edits.InsertAdapterProxyOnConnectionEdit;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class IntroduceAdapterRefactoring extends Refactoring {

	private final URI connectionURI;
	private final String proxyTypeName;
	private final IFolder folder;
	private final String adapterName;

	public IntroduceAdapterRefactoring(final URI connectionURI, final String proxyTypeName, final IFolder folder,
			final String adapterName) {
		this.connectionURI = connectionURI;
		this.proxyTypeName = proxyTypeName;
		this.folder = folder;
		this.adapterName = adapterName;
	}

	@Override
	public String getName() {
		return "Insert Adapter Proxy"; //$NON-NLS-1$
	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm) {
		return new RefactoringStatus();
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm) {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) {
		InsertAdapterProxyOnConnectionEdit edit = null;
		if (connectionURI != null) {
			edit = new InsertAdapterProxyOnConnectionEdit(connectionURI);
		}

		final CompositeChange cc = new CompositeChange("Insert Adapter Proxy"); //$NON-NLS-1$

		final IFile proxyFile = folder.getFile(proxyTypeName + ".fbt"); //$NON-NLS-1$

		if (!proxyFile.exists()) {
			cc.add(new CreateAdapterProxyTypeChange(folder, proxyTypeName, adapterName));
		}
		if (edit != null) {
			cc.add(ModelEditChange.fromModelEdits("Replace Connection with Adapter Proxy", List.of(edit))); //$NON-NLS-1$
		}

		return cc;
	}

}
