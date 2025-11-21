/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - adapted from Xtext ResourceRelocationProcessor
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import static org.eclipse.xtext.ide.refactoring.RefactoringIssueAcceptor.Severity.ERROR;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.resource.MoveResourceChange;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceChange;
import org.eclipse.xtext.ide.refactoring.IResourceRelocationStrategy;
import org.eclipse.xtext.ide.refactoring.ResourceRelocationChange;
import org.eclipse.xtext.ide.refactoring.ResourceRelocationContext;
import org.eclipse.xtext.ide.serializer.IChangeSerializer;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.ui.refactoring2.ChangeConverter;
import org.eclipse.xtext.ui.refactoring2.LtkIssueAcceptor;
import org.eclipse.xtext.ui.refactoring2.ResourceURIConverter;
import org.eclipse.xtext.ui.refactoring2.participant.ResourceRelocationStrategyRegistry;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.resource.LiveScopeResourceSetInitializer;

import com.google.inject.Inject;
import com.google.inject.Provider;

@SuppressWarnings("restriction")
public class STCoreResourceRelocationProcessor {
	private static final Logger LOG = Logger.getLogger(STCoreResourceRelocationProcessor.class);

	@Inject
	private IResourceSetProvider resourceSetProvider;

	@Inject
	private LiveScopeResourceSetInitializer liveScopeResourceSetInitializer;

	@Inject
	private LtkIssueAcceptor issues;

	@Inject
	private ResourceURIConverter resourceURIConverter;

	@Inject
	private Provider<IChangeSerializer> changeSerializerProvider;

	@Inject
	private ResourceRelocationStrategyRegistry strategyRegistry;

	@Inject
	private ChangeConverter.Factory changeConverterFactory;

	private final List<ResourceRelocationChange> uriChanges = new ArrayList<>();

	private final Set<IResource> excludedResources = new HashSet<>();

	private IProject project;

	public Change createChange(final String name, final ResourceRelocationContext.ChangeType type,
			final IProgressMonitor pm) throws OperationCanceledException {
		if (uriChanges.isEmpty()) {
			return null;
		}

		final SubMonitor subMonitor = SubMonitor.convert(pm);
		// Declaring the task and its effort in 'SubMonitor.convert(...)' doesn't yield
		// the expected UI updates
		// so let's do it separately; the total effort of '5' is chosen for weighting
		// the subsequent efforts
		subMonitor.beginTask(Messages.STCoreResourceRelocationProcessor_TaskName, 5);

		final IChangeSerializer changeSerializer = changeSerializerProvider.get();
		final ResourceSet resourceSet = resourceSetProvider.get(project);

		final ResourceRelocationContext context = new STCoreResourceRelocationContext(type, uriChanges, issues,
				changeSerializer, resourceSet);
		final boolean persistedIndexUsageRequested = isPersistedIndexUsageRequested(context);

		initializeResourceSet(persistedIndexUsageRequested, context);
		executeParticipants(context, subMonitor.split(1));

		final ChangeConverter changeConverter = changeConverterFactory.create(name, //
				it -> (!(it instanceof MoveResourceChange || it instanceof RenameResourceChange)
						|| !excludedResources.contains(it.getModifiedElement())),
				issues);

		final SubMonitor modificationApplicationMonitor = subMonitor.split(4); // remaining effort is assigned to
																				// 'changeSerializer's work
		changeSerializer.setProgressMonitor(modificationApplicationMonitor);
		changeSerializer.applyModifications(changeConverter);
		modificationApplicationMonitor.done();
		return changeConverter.getChange();
	}

	/**
	 * @since 2.18
	 */
	protected boolean isPersistedIndexUsageRequested(final ResourceRelocationContext context) {
		final List<? extends IResourceRelocationStrategy> strategies = strategyRegistry.getStrategies();
		return strategies.stream().anyMatch(strategy -> strategy.requiresUsageOfPersistedIndex(context));
	}

	/**
	 * @since 2.18
	 */
	protected void initializeResourceSet(final boolean persistedIndexUsageRequested,
			final ResourceRelocationContext context) {
		if (persistedIndexUsageRequested) {
			context.getResourceSet().getLoadOptions().put(ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS,
					Boolean.TRUE);
		} else {
			liveScopeResourceSetInitializer.initialize(context.getResourceSet());
		}
	}

	protected void executeParticipants(final ResourceRelocationContext context, final SubMonitor monitor) {
		final List<? extends IResourceRelocationStrategy> strategies = strategyRegistry.getStrategies();
		if (context.getChangeType() == ResourceRelocationContext.ChangeType.COPY) {
			context.getChangeSerializer().setUpdateRelatedFiles(false);
		}

		monitor.setWorkRemaining(strategies.size());

		for (final IResourceRelocationStrategy strategy : strategies) {
			try {
				monitor.split(1);
				strategy.applyChange(context);
			} catch (final OperationCanceledException t) {
				issues.add(ERROR, Messages.STCoreResourceRelocationProcessor_CancelationMessage, t);
				LOG.error(t.getMessage(), t);
				break;
			}
		}
	}

	public void addChangedResource(final IResource resource, final IPath fromPath, final IPath toPath) {
		if (project == null) {
			project = resource.getProject();
		}

		if (fromPath.isPrefixOf(resource.getFullPath())) {
			final URI oldURI = resourceURIConverter.toURI(resource);
			final URI newURI = resourceURIConverter
					.toURI(toPath.append(resource.getFullPath().removeFirstSegments(fromPath.segmentCount())));
			excludedResources.add(resource);
			if (resource instanceof IFile) {
				final ResourceRelocationChange uriChange = new ResourceRelocationChange(oldURI, newURI, true);
				uriChanges.add(uriChange);
			} else if (resource instanceof final IContainer container) {
				final ResourceRelocationChange uriChange = new ResourceRelocationChange(oldURI, newURI, false);
				uriChanges.add(uriChange);

				try {
					for (final IResource member : container.members()) {
						addChangedResource(member, fromPath, toPath);
					}
				} catch (final CoreException e) {
					throw new RuntimeException(e); // NOSONAR
				}
			}
		}
	}

	public LtkIssueAcceptor getIssues() {
		return issues;
	}
}
