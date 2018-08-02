/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester;

import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.swt.widgets.Control;


public interface IFBTestConfiguration {

	Control getControl();

	void newTestConf(List<TestElement> variables, List<String> values, List<ValuedVarDecl> resultVars,
			Map<String, Object> params);

}
