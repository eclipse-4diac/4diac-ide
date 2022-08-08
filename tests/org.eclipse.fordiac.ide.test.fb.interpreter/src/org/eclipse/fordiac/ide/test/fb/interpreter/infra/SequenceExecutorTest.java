package org.eclipse.fordiac.ide.test.fb.interpreter.infra;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.inputgenerator.SequenceExecutor;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.junit.Test;

public class SequenceExecutorTest {

	@Test
	public void test() {
		final FBType fBType = AbstractInterpreterTest.loadFBType("E_CTU"); //$NON-NLS-1$
		assertNotNull(fBType);
		final EList<Transaction> trans = SequenceExecutor.executeRandomSequence(fBType, 3, true);
		assert (trans.size() == 3);
		assert (trans.stream().noneMatch(t -> t.getInputEventOccurrence().isActive()));
		assert (trans.stream().noneMatch(t -> t.getInputEventOccurrence().isIgnored()));

	}

}
