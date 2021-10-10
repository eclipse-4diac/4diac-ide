/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.metrics.analyzers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.export.forte_ng.st.STAlgorithmFilter;
import org.eclipse.fordiac.ide.metrics.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryExpression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryOperator;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.CaseStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ContinueStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ElseClause;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ElseIfClause;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ExitStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Expression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ForStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IfStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RepeatStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Statement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StatementList;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryExpression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryOperator;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.WhileStatement;

public class CognitiveComplexity extends AbstractCodeMetricAnalyzer {

	List<MetricResult> metrics = new ArrayList<>();
	double ccapp = 0;

	private static String formatResultName(final String name) {
		return MessageFormat.format(Messages.CognitiveComplexity, name);
	}

	private static String formatResultName(final String name, final String sub) {
		return formatResultName(MessageFormat.format("{0}.{1}", name, sub)); //$NON-NLS-1$
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

		double ccfb = 0;

		for (final ECState state : ecc.getECState()) {
			for (final ECAction action : state.getECAction()) {
				if (null != action.getAlgorithm()) {
					final double ccalg = analyzeAlgorithm(action.getAlgorithm());
					ccfb += ccalg;
					metrics.add(new MetricResult(formatResultName(basicFBType.getName(), action.getAlgorithm().getName()),
							ccalg));
				}
			}
		}

		data.cc += ccfb;
		metrics.add(new MetricResult(formatResultName(basicFBType.getName()), ccfb));
		return data;
	}

	public static double analyzeAlgorithm(final Algorithm algorithm) {
		final STAlgorithmFilter filter = new STAlgorithmFilter();

		final List<String> errors = new ArrayList<>();

		final var ast = filter.parse((STAlgorithm) algorithm, errors);

		return errors.isEmpty() ? parseStatements(ast.getStatements()) : -1.0;
	}

	private static double parseStatements(final StatementList list) {
		return parseStatements(list, 0.0);
	}

	private static double parseStatements(final StatementList list, final double nesting) {
		double cc = 0;

		for (final var stmt : list.getStatements()) {
			cc += handleBranching(stmt, nesting);
			cc += handleLoops(stmt, nesting);
			cc += handleFlowStatements(stmt);
		}
		return cc;
	}

	private static double handleBranching(final Statement stmt, final double nesting) {
		double cc = 0;
		if (stmt instanceof IfStatement) {
			cc += 1 + nesting + //
					parseExpression(((IfStatement) stmt).getExpression()) + //
					parseStatements(((IfStatement) stmt).getStatments(), nesting + 1);

			for (final ElseIfClause elif : ((IfStatement) stmt).getElseif()) {
				cc += nesting + parseExpression(elif.getExpression()) + //
						parseStatements(elif.getStatements(), nesting + 1);
			}

			final ElseClause el = ((IfStatement) stmt).getElse();
			if (el != null) {
				cc += nesting + parseStatements(el.getStatements(), nesting + 1);
			}
		}
		if (stmt instanceof CaseStatement) {
			cc++;

			for(final var element : ((CaseStatement) stmt).getCase()) {
				cc += nesting + parseStatements(element.getStatements(), nesting + 1);
			}

			final ElseClause el = ((CaseStatement) stmt).getElse();
			if (el != null) {
				cc += nesting + parseStatements(el.getStatements(), nesting + 1);
			}
		}
		return cc;
	}

	private static double handleLoops(final Statement stmt, final double nesting) {
		if (stmt instanceof WhileStatement) {
			return 1 + nesting + //
					parseExpression(((WhileStatement) stmt).getExpression()) + //
					parseStatements(((WhileStatement) stmt).getStatements(), nesting + 1);
		}
		if (stmt instanceof RepeatStatement) {
			return 1 + nesting + //
					parseExpression(((RepeatStatement) stmt).getExpression()) + //
					parseStatements(((RepeatStatement) stmt).getStatements(), nesting + 1);
		}
		if (stmt instanceof ForStatement) {
			return 1 + nesting + //
					parseStatements(((ForStatement) stmt).getStatements(), nesting + 1);
		}
		return 0;
	}

	private static double handleFlowStatements(final Statement stmt) {
		if (stmt instanceof ExitStatement || stmt instanceof ContinueStatement) {
			return 1;
		}
		return 0;
	}

	private static double parseExpression(final Expression expr) {
		return parseExpression(expr, null);
	}

	private static double parseExpression(final Expression expr,
			final BinaryOperator oldOp) {
		if (expr instanceof BinaryExpression) {
			final BinaryExpression binExpr = (BinaryExpression) expr;
			final BinaryOperator newOp = binExpr.getOperator();
			switch(newOp) {
			case AND:
			case OR:
			case XOR:
				if (newOp != oldOp) {
					return 1
							+ parseExpression(binExpr.getLeft(), newOp)
							+ parseExpression(binExpr.getRight(), newOp);
				}
				break;
			default:
			}
		} else if (expr instanceof UnaryExpression) {
			final UnaryExpression unaryExpr = (UnaryExpression) expr;
			final UnaryOperator op = unaryExpr.getOperator();
			if (op == UnaryOperator.NOT) {
				return 1 + parseExpression(unaryExpr.getExpression(), null);
			}
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

		double ccfb = 0;

		final double ccalg = analyzeAlgorithm(simpleFBType.getAlgorithm());
		ccfb += ccalg;
		metrics.add(new MetricResult(
				formatResultName(simpleFBType.getName(), simpleFBType.getAlgorithm().getName()), ccalg));

		data.cc += ccfb;
		metrics.add(new MetricResult(formatResultName(simpleFBType.getName()), ccfb));
		return data;
	}

}
