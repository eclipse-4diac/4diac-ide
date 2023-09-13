/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit.helper;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.model.data.provider.FordiacEditPlugin;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.swt.widgets.Display;

public class InitialValueRefreshJob extends Job {

	private final AtomicReference<IInterfaceElement> interfaceElementRef;
	private final Consumer<String> consumer;
	private final boolean initialOrDefaultValue;
	private final boolean runConsumerInUIThread;
	private final AtomicBoolean interruptible = new AtomicBoolean();

	public InitialValueRefreshJob(final IInterfaceElement interfaceElement, final Consumer<String> consumer) {
		this(interfaceElement, consumer, true);
	}

	public InitialValueRefreshJob(final IInterfaceElement interfaceElement, final Consumer<String> consumer,
			final boolean initialOrDefaultValue) {
		this(interfaceElement, consumer, initialOrDefaultValue, true);
	}

	public InitialValueRefreshJob(final IInterfaceElement interfaceElement, final Consumer<String> consumer,
			final boolean initialOrDefaultValue, final boolean runConsumerInUIThread) {
		super(generateName(interfaceElement));
		this.interfaceElementRef = new AtomicReference<>(interfaceElement);
		this.consumer = consumer;
		this.initialOrDefaultValue = initialOrDefaultValue;
		this.runConsumerInUIThread = runConsumerInUIThread;
		setPriority(Job.SHORT);
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		final IInterfaceElement interfaceElement = interfaceElementRef.get();
		if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
		interruptible.set(true);
		final String value = initialOrDefaultValue // use initial and default or only default value?
				? InitialValueHelper.getInitialOrDefaultValue(interfaceElement)
				: InitialValueHelper.getDefaultValue(interfaceElement);
		interruptible.set(false);
		if (monitor.isCanceled() || Thread.interrupted()) {
			return Status.CANCEL_STATUS;
		}
		if (runConsumerInUIThread) {
			Display.getDefault().asyncExec(() -> consumer.accept(value));
		} else {
			consumer.accept(value);
		}
		return Status.OK_STATUS;
	}

	@Override
	protected void canceling() {
		if (interruptible.get()) {
			final Thread thread = getThread();
			if (thread != null) {
				thread.interrupt();
			}
		}
	}

	public void refresh() {
		cancel();
		schedule();
	}

	public IInterfaceElement getInterfaceElement() {
		return interfaceElementRef.get();
	}

	public void setInterfaceElement(final IInterfaceElement interfaceElement) {
		this.interfaceElementRef.set(interfaceElement);
		setName(generateName(interfaceElement));
	}

	private static String generateName(final IInterfaceElement interfaceElement) {
		return MessageFormat.format(FordiacEditPlugin.INSTANCE.getString("_UI_InitialValueRefreshJob_name"), //$NON-NLS-1$
				interfaceElement != null ? interfaceElement.getQualifiedName() : "null"); //$NON-NLS-1$
	}
}
