/*******************************************************************************
 * Copyright (c) 2019 TU Wien, ACIN
 * 				 2019-2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Peter Gsellmann - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Changed analysis result to key value pairs
 *   Lisa Sonnleithner - Adjustments to change calculation method to average
 *******************************************************************************/
package org.eclipse.fordiac.ide.metrics.analyzers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.metrics.Messages;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.CaseStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ForStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IfStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RepeatStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Statement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StatementList;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.WhileStatement;
import org.eclipse.fordiac.ide.model.structuredtext.util.StructuredTextParseUtil;

public class CyclomaticComplexity extends AbstractCodeMetricAnalyzer {
	static final String[] CONDITIONS = { FordiacKeywords.IF, FordiacKeywords.FOR, FordiacKeywords.WHILE,
			FordiacKeywords.REPEAT };

	List<MetricResult> metrics = new ArrayList<>();
	double ccapp = 0.0;

	private static String formatResultName(final String name) {
		return MessageFormat.format(Messages.CyclomaticNumber, name);
	}

	@Override
	public void calculateMetrics(final INamedElement element) {
		super.calculateMetrics(element);
		final ComplexityData cData = (ComplexityData) this.data;
		metrics.add(0, new MetricResult(formatResultName(element.getName()), cData.cc));
		metrics = removeDuplicateResults(metrics);
	}

	@Override
	public List<MetricResult> getResults() {
		return metrics;
	}

	@Override
	protected MetricData analyzeBFB(final BasicFBType basicFBType) {
		final ComplexityData data = new ComplexityData();
		final ECC ecc = basicFBType.getECC();

		double ccfb = (ecc.getECTransition().size() - ecc.getECState().size() + 2);

		for (final ECState state : ecc.getECState()) {
			for (final ECAction action : state.getECAction()) {
				if (null != action.getAlgorithm()) {
					ccfb += analyzeAlgorithm(action.getAlgorithm());
				}
			}
		}

		data.cc += ccfb;
		metrics.add(new MetricResult(formatResultName(basicFBType.getName()), ccfb));
		return data;
	}

	public static double analyzeAlgorithm(final Algorithm alg) {
		if (!(alg instanceof STAlgorithm)) {
			return -1;
		}

		final var ast = StructuredTextParseUtil.parse((STAlgorithm) alg, null);
		return ast != null ? //
				calculateCyclomaticComplexity(ast.getStatements())//
				: -1;
	}

	private static double calculateCyclomaticComplexity(final StatementList list) {
		return 1 + countForks(list);
	}

	private static double countForks(final StatementList list) {
		// calculate the cyclocmatic complexity by counting forks
		return list.getStatements().stream().mapToDouble(CyclomaticComplexity::dispatchCountStatements).sum();
	}

	private static double dispatchCountStatements(final Statement stmt) {
		if (stmt instanceof IfStatement) {
			final IfStatement ifStmt = (IfStatement) stmt;

			double elseCount = 0;
			if (ifStmt.getElse() != null) {
				elseCount = countForks(ifStmt.getElse().getStatements());
			}

			return 1 + countForks(ifStmt.getStatments()) + //
					elseCount + //
					ifStmt.getElseif().size() + //
					ifStmt.getElseif().stream().mapToDouble(v -> countForks(v.getStatements()))
					.sum();
		}
		if (stmt instanceof ForStatement) {
			final ForStatement forStmt = (ForStatement) stmt;
			return 1 + countForks(forStmt.getStatements());
		}
		if (stmt instanceof WhileStatement) {
			final WhileStatement whileStmt = (WhileStatement) stmt;
			return 1 + countForks(whileStmt.getStatements());
		}
		if (stmt instanceof RepeatStatement) {
			final RepeatStatement repeatStmt = (RepeatStatement) stmt;
			return 1 + countForks(repeatStmt.getStatements());
		}
		if (stmt instanceof CaseStatement) {
			final CaseStatement caseStmt = (CaseStatement) stmt;
			double elseCount = 0;
			if (caseStmt.getElse() != null) {
				elseCount = countForks(caseStmt.getElse().getStatements());
			}
			return 1 + elseCount + //
					caseStmt.getCase().size() + //
					caseStmt.getCase().stream().mapToDouble(v -> countForks(v.getStatements()))
					.sum();
		}
		return 0;
	}

	@Override
	protected MetricData analyzeCFB(final CompositeFBType compositeFBType) {
		final MetricData data = analyzeFBNetwork(compositeFBType.getFBNetwork(), true);
		metrics.add(new MetricResult(formatResultName(compositeFBType.getName()), ((ComplexityData) data).cc));
		return data;

	}

	@Override
	protected MetricData createDataType() {
		return new ComplexityData();
	}

	@Override
	protected MetricData analyzeSFB(final SimpleFBType simpleFBType) {
		final ComplexityData data = new ComplexityData();

		data.cc += analyzeAlgorithm(simpleFBType.getAlgorithm());
		metrics.add(new MetricResult(formatResultName(simpleFBType.getName()), data.cc));
		return data;
	}

}
