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
package org.eclipse.fordiac.ide.test.fb.interpreter.infra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.fordiac.ide.export.forte_ng.st.StructuredTextSupportFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerProcessor;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup;
import org.eclipse.fordiac.ide.model.eval.EvaluatorThreadPoolExecutor;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluatorFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup;
import org.junit.jupiter.api.Test;

public class EvaluatorExecutorLifecycleTest {

	@Test
	@SuppressWarnings("static-method")
	public void testSharedExecutorAndCacheCleanup() throws ReflectiveOperationException {
		STAlgorithmStandaloneSetup.doSetup();
		StructuredTextEvaluatorFactory.register();
		FBEvaluatorFactory.register();
		StructuredTextSupportFactory.register();
		GlobalConstantsStandaloneSetup.doSetup();
		STFunctionStandaloneSetup.doSetup();

		clearCaches();
		try {
			final FBTransaction first = process(AbstractInterpreterTest.loadFBType("E_SR"), "S"); //$NON-NLS-1$ //$NON-NLS-2$
			final FBTransaction second = process(AbstractInterpreterTest.loadFBType("E_CTU"), "R"); //$NON-NLS-1$ //$NON-NLS-2$
			assertTrue(first.getExceptions().isEmpty());
			assertTrue(second.getExceptions().isEmpty());
			assertEquals(1, first.getOutputEventOccurrences().size());
			assertEquals(1, second.getOutputEventOccurrences().size());

			final Map<?, ?> cache = evaluatorCache();
			assertEquals(2, cache.size());
			final EvaluatorThreadPoolExecutor executor = executorOf(cache.get("E_SR")); //$NON-NLS-1$
			assertSame(executor, executorOf(cache.get("E_CTU"))); //$NON-NLS-1$

			final AtomicBoolean resourceClosed = new AtomicBoolean();
			executor.getSharedResources().put("test", () -> resourceClosed.set(true)); //$NON-NLS-1$
			clearCaches();

			assertTrue(cache.isEmpty());
			assertTrue(executor.isTerminated());
			assertTrue(resourceClosed.get());

			process(AbstractInterpreterTest.loadFBType("E_SR"), "S"); //$NON-NLS-1$ //$NON-NLS-2$
			assertNotSame(executor, executorOf(cache.get("E_SR"))); //$NON-NLS-1$
		} finally {
			clearCaches();
		}
	}

	@Test
	@SuppressWarnings("static-method")
	public void testIdleProcessorClosesSharedResources() throws ReflectiveOperationException {
		final EventManagerProcessor processor = new EventManagerProcessor(EventManagerFactory.createFrom(List.of()), null);
		try {
			final EvaluatorThreadPoolExecutor executor = currentExecutor();
			final AtomicBoolean resourceClosed = new AtomicBoolean();
			executor.getSharedResources().put("test", () -> resourceClosed.set(true)); //$NON-NLS-1$

			assertTrue(processor.processOne(OptionalLong.empty()).isEmpty());
			assertTrue(executor.isTerminated());
			assertTrue(resourceClosed.get());
		} finally {
			clearCaches();
			final Method setter = interpreterClass("ServiceInterfaceFBTypeDefaultInterpreter") //$NON-NLS-1$
					.getMethod("setOutputInitO", boolean.class); //$NON-NLS-1$
			setter.setAccessible(true);
			setter.invoke(null, true);
		}
	}

	private static FBTransaction process(final FBType fbType, final String eventName) {
		final var event = fbType.getInterfaceList().getEventInputs().stream()
				.filter(input -> input.getName().equals(eventName)).findFirst().orElseThrow();
		final FBTransaction transaction = TransactionFactory.createFrom(event, RuntimeFactory.createFrom(fbType));
		EventManagerUtils.processFbTransaction(transaction);
		return transaction;
	}

	private static Map<?, ?> evaluatorCache() throws ReflectiveOperationException {
		final Field field = interpreterClass("DefaultRunFBType").getDeclaredField("evaluatorCache"); //$NON-NLS-1$ //$NON-NLS-2$
		field.setAccessible(true);
		return (Map<?, ?>) field.get(null);
	}

	private static EvaluatorThreadPoolExecutor executorOf(final Object cacheEntry) throws ReflectiveOperationException {
		final Method accessor = cacheEntry.getClass().getDeclaredMethod("executor"); //$NON-NLS-1$
		accessor.setAccessible(true);
		return (EvaluatorThreadPoolExecutor) accessor.invoke(cacheEntry);
	}

	private static EvaluatorThreadPoolExecutor currentExecutor() throws ReflectiveOperationException {
		final Method accessor = interpreterClass("DefaultRunFBType").getDeclaredMethod("getEvaluatorExecutor"); //$NON-NLS-1$ //$NON-NLS-2$
		accessor.setAccessible(true);
		return (EvaluatorThreadPoolExecutor) accessor.invoke(null);
	}

	private static void clearCaches() throws ReflectiveOperationException {
		final Method method = interpreterClass("DefaultRunFBType").getMethod("clearCaches"); //$NON-NLS-1$ //$NON-NLS-2$
		method.setAccessible(true);
		method.invoke(null);
	}

	private static Class<?> interpreterClass(final String name) throws ClassNotFoundException {
		return EventManagerUtils.class.getClassLoader().loadClass("org.eclipse.fordiac.ide.fb.interpreter." + name); //$NON-NLS-1$
	}
}
