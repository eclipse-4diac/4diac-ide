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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.core.refactoring.CreateChangeOperation;
import org.eclipse.ltk.core.refactoring.PerformChangeOperation;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ui.handlers.HandlerUtil;

public class IntroduceAdapterProxyOnConnectionHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection sel = HandlerUtil.getActiveMenuSelection(event);
		if (!(sel instanceof final IStructuredSelection s) || s.isEmpty()) {
			return null;
		}

		final Object first = s.getFirstElement();
		final AdapterConnection ac = unwrapAdapterConnection(first);
		if (ac == null) {
			return null;
		}

		final String adapterTypeName = getAdapterTypeName(ac);
		final String proxyTypeName = adapterTypeName + "_Proxy";
		final LibraryElement elem = (LibraryElement) EcoreUtil.getRootContainer(ac);
		final URI connURI = EcoreUtil.getURI(ac);
		final IFile file = elem.getTypeEntry().getFile();
		final IFolder folder = file.getProject().getFolder("Type Library");

		final Refactoring refactoring = new IntroduceAdapterRefactoring(connURI, proxyTypeName, folder,
				adapterTypeName);

		final CreateChangeOperation create = new CreateChangeOperation(refactoring);
		final PerformChangeOperation perform = new PerformChangeOperation(create);

		try {
			ResourcesPlugin.getWorkspace()
					.run(monitor -> perform.run(monitor != null ? monitor : new NullProgressMonitor()), null);
		} catch (final Exception e) {
			throw new ExecutionException("Failed to perform refactoring", e); //$NON-NLS-1$
		}
		return null;
	}

	private static AdapterConnection unwrapAdapterConnection(final Object element) {
		if (element instanceof final ConnectionEditPart cep && cep.getModel() instanceof final AdapterConnection ac) {
			return ac;
		}
		if (element instanceof final AdapterConnection adapterConnection) {
			return adapterConnection;
		}
		return null;
	}

	private static String getAdapterTypeName(final AdapterConnection ac) {
		final IInterfaceElement src = ac.getSource();
		final IInterfaceElement dst = ac.getDestination();
		final AdapterType at = (src != null && src.getType() instanceof AdapterType) ? (AdapterType) src.getType()
				: (dst != null && dst.getType() instanceof AdapterType ? (AdapterType) dst.getType() : null);
		return (at != null) ? at.getName() : null;
	}
}
