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
package org.eclipse.fordiac.ide.typemanagement.refactoring.adapter.edits;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.adapter.CreateAdapterProxyTypeChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class InsertAdapterProxyOnConnectionEdit extends ModelEdit<AdapterConnection> {

	public InsertAdapterProxyOnConnectionEdit(final URI connectionURI) {
		super("Insert Adapter Proxy", connectionURI, AdapterConnection.class); //$NON-NLS-1$
	}

	@Override
	public void initializeValidationData(final AdapterConnection element, final IProgressMonitor pm) {

	}

	@Override
	public RefactoringStatus isValid(final AdapterConnection element, final IProgressMonitor pm) throws CoreException {
		final RefactoringStatus rs = new RefactoringStatus();

		final AdapterConnection ac = element;
		if (ac == null) {
			rs.addFatalError("Selected adapter connection no longer exists."); //$NON-NLS-1$
			return rs;
		}

		final AdapterType at = inferAdapterType(ac);
		if (at == null || at.getName() == null || at.getName().isBlank()) {
			rs.addFatalError("Cannot determine adapter type from the selected connection."); //$NON-NLS-1$
			return rs;
		}

		final FBTypeEntry proxy = findProxyEntry(ac, at.getName() + "_Proxy"); //$NON-NLS-1$
		if (proxy == null) {
			rs.addWarning("Proxy type '" + at.getName() + "_Proxy.fbt' not found in project. " //$NON-NLS-2$
					+ "The operation will fail at execution."); //$NON-NLS-1$
		}
		return rs;
	}

	@Override
	protected Command createCommand(final AdapterConnection net) {
		final AdapterConnection original = net;
		if (original == null) {
			return null;
		}

		final AdapterDeclaration src = safeCastAdapterDecl(original.getSource());
		final AdapterDeclaration dst = safeCastAdapterDecl(original.getDestination());
		if (src == null || dst == null) {
			return null;
		}

		final AdapterType at = inferAdapterType(original);
		if (at == null || at.getName() == null || at.getName().isBlank()) {
			return null;
		}

		final FBTypeEntry proxyEntry = findProxyEntry(original, at.getName() + "_Proxy"); //$NON-NLS-1$
		if (proxyEntry == null) {
			return null;
		}

		final FBNetworkElement srcFB = src.getBlockFBNetworkElement();
		final FBNetworkElement dstFB = dst.getBlockFBNetworkElement();
		final int midX = avg(xOf(srcFB), xOf(dstFB));
		final int midY = avg(yOf(srcFB), yOf(dstFB));

		final Position position = LibraryElementFactory.eINSTANCE.createPosition();
		position.setX(midX);
		position.setY(midY);

		final FBCreateCommand createProxy = new FBCreateCommand(proxyEntry, srcFB.getFbNetwork(), position);

		final CompoundCommand compound = new CompoundCommand("Insert Adapter Proxy"); //$NON-NLS-1$
		compound.add(createProxy);
		compound.add(new WireAroundProxyAndRemoveOriginal(original, createProxy));
		return compound;
	}

	private static final class WireAroundProxyAndRemoveOriginal extends Command {
		private final AdapterConnection original;
		private final FBCreateCommand createdCmd;
		private List<Command> executed = new ArrayList<>();

		WireAroundProxyAndRemoveOriginal(final AdapterConnection original, final FBCreateCommand createdCmd) {

			this.original = original;
			this.createdCmd = createdCmd;
		}

		@Override
		public void execute() {
			executed = new ArrayList<>();

			final FBNetwork net = original.getFBNetwork();
			final IInterfaceElement src = original.getSource();
			final IInterfaceElement dst = original.getDestination();
			final boolean srcIsPlug = (src instanceof final AdapterDeclaration ad) && !ad.isIsInput();

			final DeleteConnectionCommand del = new DeleteConnectionCommand(original);
			if (del.canExecute()) {
				del.execute();
				executed.add(del);
			}

			final BlockFBNetworkElement proxy = createdCmd.getFB();
			final AdapterDeclaration proxySock = proxy.getInterface().getSockets().stream()
					.filter(a -> a.isIsInput() && CreateAdapterProxyTypeChange.SOCKET1.equals(a.getName())).findFirst()
					.orElseThrow(() -> new IllegalStateException("Proxy SOCKET1 not found")); //$NON-NLS-1$
			final AdapterDeclaration proxyPlug = proxy.getInterface().getPlugs().stream()
					.filter(a -> !a.isIsInput() && CreateAdapterProxyTypeChange.PLUG1.equals(a.getName())).findFirst()
					.orElseThrow(() -> new IllegalStateException("Proxy PLUG1 not found")); //$NON-NLS-1$

			final IInterfaceElement origPlug = srcIsPlug ? src : dst;
			final IInterfaceElement origSocket = srcIsPlug ? dst : src;

			addAndExecuteConn(net, origPlug, proxySock);
			addAndExecuteConn(net, proxyPlug, origSocket);
		}

		@Override
		public void undo() {
			for (int i = executed.size() - 1; i >= 0; --i) {
				executed.get(i).undo();
			}
		}

		private void addAndExecuteConn(final FBNetwork net, final IInterfaceElement src, final IInterfaceElement dst) {
			if (src == null || dst == null) {
				return;
			}

			Command c = AbstractConnectionCreateCommand.createCommand(net, src, dst);
			if (c instanceof final AbstractConnectionCreateCommand acc) {
				acc.setSource(src);
				acc.setDestination(dst);
			}
			if (c != null && c.canExecute()) {
				c.execute();
				executed.add(c);
				return;
			}
			c = AbstractConnectionCreateCommand.createCommand(net, dst, src);
			if (c instanceof final AbstractConnectionCreateCommand acc2) {
				acc2.setSource(dst);
				acc2.setDestination(src);
			}
			if (c != null && c.canExecute()) {
				c.execute();
				executed.add(c);
			}
		}
	}

	private static AdapterDeclaration safeCastAdapterDecl(final IInterfaceElement ie) {
		return (ie instanceof final AdapterDeclaration a) ? a : null;
	}

	private static AdapterType inferAdapterType(final AdapterConnection ac) {
		if (ac.getSource() instanceof final AdapterDeclaration a && a.getType() != null) {
			return a.getType();
		}
		if (ac.getDestination() instanceof final AdapterDeclaration a && a.getType() != null) {
			return a.getType();
		}
		return null;
	}

	private static FBTypeEntry findProxyEntry(final AdapterConnection ac, final String proxyTypeName) {
		final EObject rootContainer = EcoreUtil.getRootContainer(ac);

		final IProject project = ((LibraryElement) rootContainer).getTypeEntry().getFile().getProject();

		final TypeLibrary tl = TypeLibraryManager.INSTANCE.getTypeLibrary(project);

		final TypeEntry te = tl.getFBTypeEntry(proxyTypeName);
		if (te instanceof final FBTypeEntry fb) {
			return fb;
		}

		final FBTypeEntry[] out = new FBTypeEntry[1];
		try {
			project.accept(r -> {
				if (r instanceof final IFile file
						&& TypeLibraryTags.FB_TYPE_FILE_ENDING.equalsIgnoreCase(file.getFileExtension())
						&& file.getName().equals(proxyTypeName + TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT)) {
					final TypeEntry te2 = tl.createTypeEntry(file);
					if (te2 instanceof final FBTypeEntry fb) {
						out[0] = fb;
						return false;
					}
				}
				return true;
			});
		} catch (final Exception e) {
			return null;
		}
		return out[0];
	}

	private static int xOf(final FBNetworkElement e) {
		return (int) ((e != null && e.getPosition() != null) ? e.getPosition().getX() : 0);
	}

	private static int yOf(final FBNetworkElement e) {
		return (int) ((e != null && e.getPosition() != null) ? e.getPosition().getY() : 0);
	}

	private static int avg(final int a, final int b) {
		return a + ((b - a) / 2);
	}

}
