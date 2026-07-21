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
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.provider.ILibraryProvider.LibraryDescriptor;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public class LibraryResolver {

	private static final String PLUGIN_ID = "org.eclipse.fordiac.ide.library"; //$NON-NLS-1$
	private static final String PROJECT_CAUSE = "The project manifest"; //$NON-NLS-1$
	private static final String INCLUDED_CAUSE = "Your planned change"; //$NON-NLS-1$

	public record ResolveResult(Map<String, ResolveNode> resolveNodes, Map<String, DependencyNode> dependencyNodes,
			IStatus status) {
		public String getMessage() {
			if (status().isOK()) {
				return "OK"; //$NON-NLS-1$
			}
			if (status().isMultiStatus()) {
				return Arrays.stream(status().getChildren()).map(IStatus::getMessage)
						.collect(Collectors.joining(System.lineSeparator()));
			}
			return status().getMessage();
		}
	}

	public static ResolveResult resolveDependencies(final Map<String, VersionRange> projectDependencies,
			final Map<String, List<LibraryDescriptor>> availableLibraries, final Set<LibraryDescriptor> includedLibs,
			final Set<String> excludedLibs) {

		final Map<String, DependencyNode> dependencyNodes = createDependencyNodes(projectDependencies, includedLibs);
		final Map<String, ResolveNode> resolveNodes = createResolveNodes(includedLibs);
		final Queue<String> queue = new ArrayDeque<>(dependencyNodes.keySet());

		final MultiStatus status = new MultiStatus(PLUGIN_ID, IStatus.OK, "Library Resolve Status", null); //$NON-NLS-1$

		while (!queue.isEmpty() && status.isOK()) {
			final String symbolicName = queue.poll();
			final DependencyNode dependencyNode = dependencyNodes.get(symbolicName);

			if (dependencyNode == null) {
				continue;
			}

			if (validateNode(symbolicName, dependencyNode, excludedLibs, status)) {
				final ResolveNode resolveNode = resolve(symbolicName, dependencyNode, resolveNodes.get(symbolicName),
						availableLibraries);

				resolveNodes.put(symbolicName, resolveNode);

				if (resolveNode.getError() != null) {
					status.add(Status.error(resolveNode.getError()));
				} else {
					addDependencies(symbolicName, resolveNode, dependencyNodes, queue);
				}
			}
		}

		return new ResolveResult(Collections.unmodifiableMap(resolveNodes),
				Collections.unmodifiableMap(dependencyNodes), status);
	}

	public static Stream<LibraryDescriptor> deriveImportSet(final ResolveResult result,
			final List<LibraryDescriptor> linked) {
		final Map<String, Version> linkedVersions = linked.stream()
				.collect(Collectors.toMap(LibraryDescriptor::symbolicName, LibraryDescriptor::version));

		return result.resolveNodes().entrySet().stream()
				.filter(entry -> result.dependencyNodes().containsKey(entry.getKey()))
				.map(entry -> toLibraryDescriptor(entry.getValue()))
				.filter(lib -> !lib.version().equals(linkedVersions.get(lib.symbolicName())));
	}

	public static Stream<LibraryDescriptor> deriveRemoveSet(final ResolveResult result,
			final List<LibraryDescriptor> linked) {
		return linked.stream().filter(f -> !result.dependencyNodes().containsKey(f.symbolicName()));
	}

	private static LibraryDescriptor toLibraryDescriptor(final ResolveNode node) {
		return new LibraryDescriptor(node.getSymbolicName(), node.getVersion(), node.getDependencies());
	}

	private static boolean validateNode(final String symbolicName, final DependencyNode dependencyNode,
			final Set<String> excludedLibs, final MultiStatus status) {
		if (excludedLibs.contains(symbolicName)) {
			status.add(Status.error(MessageFormat.format("Library {0} cannot be removed because:{1}{2}", //$NON-NLS-1$
					symbolicName, System.lineSeparator(), dependencyNode.getCauseMessage())));
			return false;
		}

		if (!dependencyNode.isValid()) {
			status.add(Status.error(MessageFormat.format("""
					Library ''{0}'' cannot be selected because its version requirements conflict.{1}\
					{2}{1}{1}\
					Select a version that satisfies all requirements, or update the dependent libraries.""", //$NON-NLS-1$
					dependencyNode.getSymbolicName(), System.lineSeparator(), dependencyNode.getCauseMessage())));
			return false;
		}

		return true;
	}

	private static void addDependencies(final String symbolicName, final ResolveNode resolveNode,
			final Map<String, DependencyNode> dependencyNodes, final Queue<String> queue) {
		resolveNode.getDependencies().forEach((dependencyName, range) -> {
			final DependencyNode dependencyNode = dependencyNodes.computeIfAbsent(dependencyName, DependencyNode::new);

			if (dependencyNode.putCause(symbolicName, range)) {
				queue.add(dependencyName);
			}
		});
	}

	private static ResolveNode resolve(final String symbolicName, final DependencyNode dependencyNode,
			final ResolveNode includedNode, final Map<String, List<LibraryDescriptor>> availableLibraries) {

		if (includedNode != null) {
			return validateIncludedNode(symbolicName, dependencyNode, includedNode);
		}

		return resolveAvailable(symbolicName, dependencyNode, availableLibraries);
	}

	private static ResolveNode validateIncludedNode(final String symbolicName, final DependencyNode dependencyNode,
			final ResolveNode includedNode) {
		if (dependencyNode.getRange().includes(includedNode.getVersion())) {
			return includedNode;
		}

		return new ResolveNode(symbolicName,
				MessageFormat.format("Included library {0} with version {1} does not satisfy required range {2}", //$NON-NLS-1$
						symbolicName, includedNode.getVersion(),
						VersionComparator.formatVersionRange(dependencyNode.getRange())));
	}

	private static ResolveNode resolveAvailable(final String symbolicName, final DependencyNode dependencyNode,
			final Map<String, List<LibraryDescriptor>> availableLibraries) {

		return availableLibraries.getOrDefault(symbolicName, Collections.emptyList()).stream()
				.filter(lib -> dependencyNode.getRange().includes(lib.version()))
				.max(Comparator.comparing(LibraryDescriptor::version)).map(LibraryResolver::toResolveNode)
				.orElseGet(() -> createNotAvailableNode(symbolicName, dependencyNode));
	}

	private static ResolveNode toResolveNode(final LibraryDescriptor descriptor) {
		final ResolveNode resolveNode = new ResolveNode(descriptor.symbolicName(), descriptor.version(), null, null,
				true);
		resolveNode.getDependencies().putAll(descriptor.dependencies());
		return resolveNode;
	}

	private static ResolveNode createNotAvailableNode(final String symbolicName, final DependencyNode dependencyNode) {
		return new ResolveNode(symbolicName,
				MessageFormat.format(Messages.ErrorMarkerLibNotAvailable, symbolicName,
						VersionComparator.formatVersionRange(dependencyNode.getRange()),
						String.join(", ", dependencyNode.getCauses().keySet()))); //$NON-NLS-1$
	}

	private static Map<String, DependencyNode> createDependencyNodes(
			final Map<String, VersionRange> projectDependencies, final Set<LibraryDescriptor> includedLibs) {

		final Map<String, DependencyNode> dependencyNodes = new HashMap<>();

		projectDependencies.forEach((symbolicName, range) -> dependencyNodes.put(symbolicName,
				new DependencyNode(symbolicName, PROJECT_CAUSE, range)));

		includedLibs.forEach(library -> dependencyNodes.computeIfAbsent(library.symbolicName(), DependencyNode::new)
				.putCause(INCLUDED_CAUSE, VersionComparator.parseVersionRange(library.version().toString())));

		return dependencyNodes;
	}

	private static Map<String, ResolveNode> createResolveNodes(final Set<LibraryDescriptor> includedLibs) {
		return includedLibs.stream()
				.collect(Collectors.toMap(LibraryDescriptor::symbolicName, LibraryResolver::toResolveNode));
	}
}