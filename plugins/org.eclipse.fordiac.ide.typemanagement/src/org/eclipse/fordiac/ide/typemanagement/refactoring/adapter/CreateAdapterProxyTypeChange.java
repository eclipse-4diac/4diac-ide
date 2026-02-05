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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.resource.DeleteResourceChange;

public class CreateAdapterProxyTypeChange extends Change {

	public static final String PLUG1 = "PLUG1"; //$NON-NLS-1$
	public static final String SOCKET1 = "SOCKET1"; //$NON-NLS-1$
	public static final String SOCKET_PREFIX = "SOCKET_"; //$NON-NLS-1$
	public static final String PLUG_PREFIX = "PLUG_"; //$NON-NLS-1$
	private final IFolder libFolder;
	private final String typeName;
	private final String adapterTypeName;

	private IFile createdFile;

	public CreateAdapterProxyTypeChange(final IFolder libFolder, final String typeName, final String adapterTypeName) {
		this.libFolder = libFolder;
		this.typeName = typeName;
		this.adapterTypeName = adapterTypeName;
	}

	@Override
	public String getName() {
		return "Create Adapter Proxy Type: " + typeName; //$NON-NLS-1$
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		if (!libFolder.exists()) {
			libFolder.create(true, true, pm);
		}
		createdFile = libFolder.getFile(typeName + TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT);

		final TypeLibrary tl = TypeLibraryManager.INSTANCE.getTypeLibrary(libFolder.getProject());
		final FBTypeEntry fbEntry = (FBTypeEntry) tl.createTypeEntry(createdFile);

		final AdapterTypeEntry adapterTypeEntry = tl.getAdapterTypeEntry(adapterTypeName);
		final CompositeFBType proxy = buildProxy(adapterTypeEntry, typeName);
		fbEntry.save(proxy, pm);

		return new DeleteResourceChange(fbEntry.getFile().getFullPath(), true);
	}

	@Override
	public Object getModifiedElement() {
		return createdFile;
	}

	private static CompositeFBType buildProxy(final AdapterTypeEntry typeEntry, final String typeName) {
		final var f = LibraryElementFactory.eINSTANCE;
		final AdapterType adapterType = typeEntry.getType();
		final CompositeFBType cfbType = f.createCompositeFBType();
		cfbType.setName(typeName);

		TypeManagementPreferencesHelper.setupIdentification(cfbType, typeEntry.getFile().getProject());
		TypeManagementPreferencesHelper.setupVersionInfo(cfbType, typeEntry.getFile().getProject());

		final var svc = f.createService();
		svc.setLeftInterface(f.createServiceInterface());
		svc.setRightInterface(f.createServiceInterface());
		final var seq = f.createServiceSequence();
		seq.setName("SEQ"); //$NON-NLS-1$
		svc.getServiceSequence().add(seq);
		cfbType.setService(svc);

		cfbType.setInterfaceList(f.createInterfaceList());
		final InterfaceList il = cfbType.getInterfaceList();
		final FBNetwork fbNetwork = f.createFBNetwork();
		cfbType.setFBNetwork(fbNetwork);

		final var cmdSock = new CreateInterfaceElementCommand(adapterType, SOCKET1, il, true, -1);
		cmdSock.execute();
		final AdapterDeclaration socketDecl = (AdapterDeclaration) cmdSock.getCreatedElement();

		final var cmdPlug = new CreateInterfaceElementCommand(adapterType, PLUG1, il, false, -1);
		cmdPlug.execute();
		final AdapterDeclaration plugDecl = (AdapterDeclaration) cmdPlug.getCreatedElement();

		final AdapterFB socketFB = socketDecl.getAdapterFB();
		final AdapterFB plugFB = plugDecl.getAdapterFB();
		if (socketFB != null && socketFB.eContainer() == null) {
			fbNetwork.getNetworkElements().add(socketFB);
		}
		if (plugFB != null && plugFB.eContainer() == null) {
			fbNetwork.getNetworkElements().add(plugFB);
		}

		if (socketFB != null) {
			final var p = f.createPosition();
			p.setX(40);
			p.setY(40);
			socketFB.setPosition(p);
		}
		if (plugFB != null) {
			final var p = f.createPosition();
			p.setX(380);
			p.setY(80);
			plugFB.setPosition(p);
		}

		mirrorAdapterInterfaceBidirectional(adapterType, f, il);

		final var connectionCreateCommands = createConnectionCommands(il, fbNetwork, socketFB, plugFB);

		if (connectionCreateCommands.canExecute()) {
			connectionCreateCommands.execute();
		}
		return cfbType;
	}

	protected static CompoundCommand createConnectionCommands(final InterfaceList proxyIL, final FBNetwork fbNetwork,
			final AdapterFB socketFB, final AdapterFB plugFB) {

		final var cc = new CompoundCommand("Adapter Proxy Wiring"); //$NON-NLS-1$
		if (socketFB == null || plugFB == null) {
			return cc;
		}

		final InterfaceList socketFBInterface = socketFB.getInterface();
		final InterfaceList plugFBInterface = plugFB.getInterface();

		for (final Event proxyIn : proxyIL.getEventInputs()) {
			final String name = proxyIn.getName();

			if (name.startsWith(SOCKET_PREFIX)) {
				final String base = name.substring(SOCKET_PREFIX.length());
				final IInterfaceElement dst = socketFBInterface.getInput(List.of(base));
				if (dst != null) {
					addConnection(cc, fbNetwork, proxyIn, dst);
				}
			} else if (name.startsWith(PLUG_PREFIX)) {
				final String base = name.substring(PLUG_PREFIX.length());
				final IInterfaceElement dst = plugFBInterface.getInput(List.of(base));
				if (dst != null) {
					addConnection(cc, fbNetwork, proxyIn, dst);
				}
			}
		}
		bridgeAllOutputs(cc, fbNetwork, socketFBInterface, plugFBInterface, proxyIL, SOCKET_PREFIX);

		bridgeAllOutputs(cc, fbNetwork, plugFBInterface, socketFBInterface, proxyIL, PLUG_PREFIX);

		return cc;
	}

	private static void bridgeAllOutputs(final CompoundCommand cc, final FBNetwork fbNetwork,
			final InterfaceList fromIF, final InterfaceList toIF, final InterfaceList proxyIL,
			final String proxyPrefix) {

		fromIF.getAllOutputs().forEach(out -> {
			final String base = out.getName();

			final IInterfaceElement toIn = toIF.getInput(List.of(base));
			if (toIn != null) {
				addConnection(cc, fbNetwork, out, toIn);
			}

			final IInterfaceElement proxyOut = proxyIL.getOutput(List.of(proxyPrefix + base));
			if (proxyOut != null) {
				addConnection(cc, fbNetwork, out, proxyOut);
			}
		});
	}

	private static void addConnection(final CompoundCommand cc, final FBNetwork fbNetwork, final IInterfaceElement src,
			final IInterfaceElement dst) {

		if (src == null || dst == null) {
			return;
		}

		var cmd = AbstractConnectionCreateCommand.createCommand(fbNetwork, src, dst);
		if (cmd instanceof final AbstractConnectionCreateCommand acc) {
			acc.setSource(src);
			acc.setDestination(dst);
		}
		if (cmd != null && cmd.canExecute()) {
			cc.add(cmd);
			return;
		}

		cmd = AbstractConnectionCreateCommand.createCommand(fbNetwork, dst, src);
		if (cmd instanceof final AbstractConnectionCreateCommand acc2) {
			acc2.setSource(dst);
			acc2.setDestination(src);
		}
		if (cmd != null && cmd.canExecute()) {
			cc.add(cmd);
		}
	}

	protected static void mirrorAdapterInterfaceBidirectional(final AdapterType at, final LibraryElementFactory f,
			final InterfaceList cfbInterfaceList) {

		final InterfaceList adapterInterfaceList = at.getInterfaceList();

		for (final VarDeclaration v : adapterInterfaceList.getOutputVars()) {
			new CreateInterfaceElementCommand(v.getType(), SOCKET_PREFIX + v.getName(), cfbInterfaceList, false, -1)
					.execute();
		}

		for (final VarDeclaration v : adapterInterfaceList.getInputVars()) {
			new CreateInterfaceElementCommand(v.getType(), PLUG_PREFIX + v.getName(), cfbInterfaceList, false, -1)
					.execute();
		}

		createMirroredEvents(adapterInterfaceList.getEventInputs(), cfbInterfaceList, true, SOCKET_PREFIX,
				cfbInterfaceList.getInputVars(), f);
		createMirroredEvents(adapterInterfaceList.getEventOutputs(), cfbInterfaceList, false, SOCKET_PREFIX,
				cfbInterfaceList.getOutputVars(), f);

		createMirroredEvents(adapterInterfaceList.getEventOutputs(), cfbInterfaceList, true, PLUG_PREFIX,
				cfbInterfaceList.getInputVars(), f);
		createMirroredEvents(adapterInterfaceList.getEventInputs(), cfbInterfaceList, false, PLUG_PREFIX,
				cfbInterfaceList.getOutputVars(), f);

	}

	private static EventType resolveEventType(final Event e) {
		final String typeName = e.getTypeName();
		final String resolved = (typeName != null && !typeName.isBlank()) ? typeName : "Event"; //$NON-NLS-1$
		return EventTypeLibrary.getInstance().getType(resolved);
	}

	private static void createMirroredEvents(final Iterable<? extends Event> srcEvents, final InterfaceList targetIL,
			final boolean createAsInput, final String prefix, final Iterable<? extends VarDeclaration> withVarPool,
			final LibraryElementFactory f) {

		for (final Event src : srcEvents) {
			final var et = resolveEventType(src);

			final var cmd = new CreateInterfaceElementCommand(et, prefix + src.getName(), targetIL, createAsInput, -1);
			cmd.execute();
			final Event created = (Event) cmd.getCreatedElement();

			for (final var w : src.getWith()) {
				if (w.getVariables() == null) {
					continue;
				}

				final String withName = prefix + w.getVariables().getName();
				final VarDeclaration varDecl = findVarByName(withVarPool, withName);
				if (varDecl != null) {
					final var nw = f.createWith();
					nw.setVariables(varDecl);
					created.getWith().add(nw);
				}
			}
		}
	}

	private static VarDeclaration findVarByName(final Iterable<? extends VarDeclaration> vars, final String name) {
		for (final VarDeclaration v : vars) {
			if (name.equals(v.getName())) {
				return v;
			}
		}
		return null;
	}

}
