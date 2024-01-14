/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.testinfra;

import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;
import org.junit.jupiter.params.provider.Arguments;

public abstract class FBNetworkTestBase extends CommandTestBase<FBNetworkTestBase.State> {

	private static final DataTypeLibrary dataTypeLib = new DataTypeLibrary();

	// create a state description that fits our purpose
	public static class State extends CommandTestBase.StateBase {

		private static final class IFileMock implements IFile {
			@Override
			public boolean isConflicting(final ISchedulingRule rule) {
				return false;
			}

			@Override
			public boolean contains(final ISchedulingRule rule) {
				return false;
			}

			@Override
			public <T> T getAdapter(final Class<T> adapter) {
				return null;
			}

			@Override
			public void touch(final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setTeamPrivateMember(final boolean isTeamPrivate) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setSessionProperty(final QualifiedName key, final Object value) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setResourceAttributes(final ResourceAttributes attributes) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setReadOnly(final boolean readOnly) {
				// nothing to be done for this mock
			}

			@Override
			public void setPersistentProperty(final QualifiedName key, final String value) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public long setLocalTimeStamp(final long value) throws CoreException {
				return 0;
			}

			@Override
			public void setLocal(final boolean flag, final int depth, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setHidden(final boolean isHidden) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setDerived(final boolean isDerived, final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setDerived(final boolean isDerived) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void revertModificationStamp(final long value) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void refreshLocal(final int depth, final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void move(final IProjectDescription description, final boolean force, final boolean keepHistory,
					final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void move(final IProjectDescription description, final int updateFlags,
					final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void move(final IPath destination, final int updateFlags, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void move(final IPath destination, final boolean force, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public boolean isVirtual() {
				return false;
			}

			@Override
			public boolean isTeamPrivateMember(final int options) {
				return false;
			}

			@Override
			public boolean isTeamPrivateMember() {
				return false;
			}

			@Override
			public boolean isSynchronized(final int depth) {
				return false;
			}

			@Override
			public boolean isPhantom() {
				return false;
			}

			@Override
			public boolean isLocal(final int depth) {
				return false;
			}

			@Override
			public boolean isLinked(final int options) {
				return false;
			}

			@Override
			public boolean isLinked() {
				return false;
			}

			@Override
			public boolean isHidden(final int options) {
				return false;
			}

			@Override
			public boolean isHidden() {
				return false;
			}

			@Override
			public boolean isDerived(final int options) {
				return false;
			}

			@Override
			public boolean isDerived() {
				return false;
			}

			@Override
			public boolean isAccessible() {
				return false;
			}

			@Override
			public IWorkspace getWorkspace() {
				return null;
			}

			@Override
			public int getType() {
				return 0;
			}

			@Override
			public Object getSessionProperty(final QualifiedName key) throws CoreException {
				return null;
			}

			@Override
			public Map<QualifiedName, Object> getSessionProperties() throws CoreException {
				return Collections.emptyMap();
			}

			@Override
			public ResourceAttributes getResourceAttributes() {
				return null;
			}

			@Override
			public URI getRawLocationURI() {
				return null;
			}

			@Override
			public IPath getRawLocation() {
				return null;
			}

			@Override
			public IPath getProjectRelativePath() {
				return null;
			}

			@Override
			public IProject getProject() {
				return null;
			}

			@Override
			public String getPersistentProperty(final QualifiedName key) throws CoreException {
				return null;
			}

			@Override
			public Map<QualifiedName, String> getPersistentProperties() throws CoreException {
				return Collections.emptyMap();
			}

			@Override
			public IPathVariableManager getPathVariableManager() {
				return null;
			}

			@Override
			public IContainer getParent() {
				return null;
			}

			@Override
			public long getModificationStamp() {
				return 0;
			}

			@Override
			public IMarker getMarker(final long id) {
				return null;
			}

			@Override
			public URI getLocationURI() {
				return null;
			}

			@Override
			public IPath getLocation() {
				return null;
			}

			@Override
			public long getLocalTimeStamp() {
				return 0;
			}

			@Override
			public String getFileExtension() {
				return null;
			}

			@Override
			public int findMaxProblemSeverity(final String type, final boolean includeSubtypes, final int depth)
					throws CoreException {
				return 0;
			}

			@Override
			public IMarker[] findMarkers(final String type, final boolean includeSubtypes, final int depth)
					throws CoreException {
				return new IMarker[0];
			}

			@Override
			public IMarker findMarker(final long id) throws CoreException {
				return null;
			}

			@Override
			public boolean exists() {
				return true;  // needed that tests complete correctly
			}

			@Override
			public void deleteMarkers(final String type, final boolean includeSubtypes, final int depth)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void delete(final int updateFlags, final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void delete(final boolean force, final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public IResourceProxy createProxy() {
				return null;
			}

			@Override
			public IMarker createMarker(final String type) throws CoreException {
				return null;
			}

			@Override
			public void copy(final IProjectDescription description, final int updateFlags,
					final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void copy(final IProjectDescription description, final boolean force, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void copy(final IPath destination, final int updateFlags, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void copy(final IPath destination, final boolean force, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void clearHistory(final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void accept(final IResourceVisitor visitor, final int depth, final int memberFlags)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void accept(final IResourceVisitor visitor, final int depth, final boolean includePhantoms)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void accept(final IResourceProxyVisitor visitor, final int depth, final int memberFlags)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void accept(final IResourceProxyVisitor visitor, final int memberFlags) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void accept(final IResourceVisitor visitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setContents(final IFileState source, final boolean force, final boolean keepHistory,
					final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setContents(final InputStream source, final boolean force, final boolean keepHistory,
					final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setContents(final IFileState source, final int updateFlags, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setContents(final InputStream source, final int updateFlags, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setCharset(final String newCharset, final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void setCharset(final String newCharset) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void move(final IPath destination, final boolean force, final boolean keepHistory,
					final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public boolean isReadOnly() {
				return false;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public IFileState[] getHistory(final IProgressMonitor monitor) throws CoreException {
				return new IFileState[0];
			}

			@Override
			public IPath getFullPath() {
				return null;
			}

			@Override
			public int getEncoding() throws CoreException {
				return 0;
			}

			@Override
			public InputStream getContents(final boolean force) throws CoreException {
				return null;
			}

			@Override
			public InputStream getContents() throws CoreException {
				return null;
			}

			@Override
			public IContentDescription getContentDescription() throws CoreException {
				return null;
			}

			@Override
			public String getCharsetFor(final Reader reader) throws CoreException {
				return null;
			}

			@Override
			public String getCharset(final boolean checkImplicit) throws CoreException {
				return null;
			}

			@Override
			public String getCharset() throws CoreException {
				return null;
			}

			@Override
			public void delete(final boolean force, final boolean keepHistory, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void createLink(final URI location, final int updateFlags, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void createLink(final IPath localLocation, final int updateFlags, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void create(final InputStream source, final int updateFlags, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void create(final InputStream source, final boolean force, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void appendContents(final InputStream source, final boolean force, final boolean keepHistory,
					final IProgressMonitor monitor) throws CoreException {
				// nothing to be done for this mock
			}

			@Override
			public void appendContents(final InputStream source, final int updateFlags, final IProgressMonitor monitor)
					throws CoreException {
				// nothing to be done for this mock
			}
		}

		private final FBNetwork net;
		private final FBTypeEntry functionblock;

		public static final String FUNCTIONBLOCK_NAME = "functionblock"; //$NON-NLS-1$

		private static FBTypeEntry createFBType() {
			final BasicFBType fbType = LibraryElementFactory.eINSTANCE.createBasicFBType();
			fbType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
			fbType.setName(FUNCTIONBLOCK_NAME);
			fbType.setECC(LibraryElementFactory.eINSTANCE.createECC());

			return new FBTypeEntryMock(fbType, TypeLibraryManager.INSTANCE.getTypeLibrary(null), new IFileMock());
		}

		public FBNetwork getFbNetwork() {
			return net;
		}

		public FBTypeEntry getFunctionblock() {
			return functionblock;
		}

		public State() {
			net = LibraryElementFactory.eINSTANCE.createFBNetwork();
			functionblock = createFBType();
		}

		private State(final State s) {
			net = EcoreUtil.copy(s.net);
			functionblock = new FBTypeEntryMock(s.functionblock);
			functionblock.setType(EcoreUtil.copy(s.functionblock.getType()));
		}

		@Override
		public Object getClone() {
			return new State(this);
		}
	}

	protected static Collection<Arguments> describeCommand(final String description, final StateInitializer<?> initializer,
			final StateVerifier<?> initialVerifier, final List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands, CommandTestBase::defaultUndoCommand,
				CommandTestBase::defaultRedoCommand);
	}

	protected static void verifyDefaultInitialValues(final State state, final State oldState, final TestFunction t) {
		t.test(!state.getFbNetwork().isCFBTypeNetwork());
		t.test(!state.getFbNetwork().isResourceNetwork());
		t.test(!state.getFbNetwork().isSubApplicationNetwork());
		t.test(state.getFbNetwork().getNetworkElements().isEmpty());
	}

	protected static Collection<Arguments> createCommands(final List<ExecutionDescription<?>> executionDescriptions) {
		final Collection<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(StateVerifier<State>) FBNetworkTestBase::verifyDefaultInitialValues, //
				executionDescriptions //
				));

		return commands;
	}

	public static DataTypeLibrary getDatatypelib() {
		return dataTypeLib;
	}

}
