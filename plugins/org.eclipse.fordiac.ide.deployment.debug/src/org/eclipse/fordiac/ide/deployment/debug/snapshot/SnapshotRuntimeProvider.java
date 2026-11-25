/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
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
package org.eclipse.fordiac.ide.deployment.debug.snapshot;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FB;

/**
 * Provides a runtime for the internal function blocks that the evaluator
 * cannot execute itself, such as service interface function blocks.
 *
 * @see FBSnapshotValidator#getRuntimeFBs
 */
@FunctionalInterface
public interface SnapshotRuntimeProvider {

	/** Rejects the launch, because starting a runtime is not supported yet. */
	SnapshotRuntimeProvider NONE = (runtimeFBs, launch, monitor) -> {
		throw new CoreException(Status.error(MessageFormat.format(Messages.SnapshotRuntimeProvider_NoRuntime,
				runtimeFBs.stream().map(fb -> fb.getName() + " (" + fb.getTypeName() + ")") //$NON-NLS-1$ //$NON-NLS-2$
						.collect(Collectors.joining(", "))))); //$NON-NLS-1$
	};

	/**
	 * Provide a runtime for the internal function blocks before the launch
	 * starts. An implementation starts the runtime, points the launch
	 * configuration to it and stops it when the launch terminates.
	 *
	 * @param runtimeFBs the internal function blocks that need a runtime
	 * @param launch     the launch
	 * @param monitor    the progress monitor
	 * @throws CoreException if no runtime can be provided, the status message
	 *                       explains why
	 */
	void provide(List<FB> runtimeFBs, ILaunch launch, IProgressMonitor monitor) throws CoreException;
}
