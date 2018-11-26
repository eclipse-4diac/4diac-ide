/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.xml;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

/**
 * The Class XMLPartitionScanner.
 */
public class XMLPartitionScanner extends RuleBasedPartitionScanner {
	
	/** The Constant XML_COMMENT. */
	public static final String XML_COMMENT = "__xml_comment"; //$NON-NLS-1$
	
	/** The Constant XML_TAG. */
	public static final String XML_TAG = "__xml_tag"; //$NON-NLS-1$

	/**
	 * Instantiates a new xML partition scanner.
	 */
	public XMLPartitionScanner() {

		IToken xmlComment = new Token(XML_COMMENT);
		IToken tag = new Token(XML_TAG);

		IPredicateRule[] rules = new IPredicateRule[2];

		rules[0] = new MultiLineRule("<!--", "-->", xmlComment); //$NON-NLS-1$ //$NON-NLS-2$
		rules[1] = new TagRule(tag);

		setPredicateRules(rules);
	}
}
