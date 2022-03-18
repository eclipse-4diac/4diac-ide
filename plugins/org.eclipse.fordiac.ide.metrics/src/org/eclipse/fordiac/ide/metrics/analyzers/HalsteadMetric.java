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
import java.util.Arrays;
import java.util.List;

import org.eclipse.fordiac.ide.metrics.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AssignmentStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryExpression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.CaseStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Expression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ForStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IfStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RepeatStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Statement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StatementList;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryExpression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.WhileStatement;
import org.eclipse.fordiac.ide.model.structuredtext.util.StructuredTextParseUtil;

public class HalsteadMetric extends AbstractCodeMetricAnalyzer {

	@Override
	protected HalsteadData analyzeBFB(final BasicFBType basicFBType) {
		final HalsteadData data = new HalsteadData();
		final ECC ecc = basicFBType.getECC();
		for (final ECTransition trans : ecc.getECTransition()) {
			if (!data.transCond.contains(trans.getConditionExpression())) {
				data.uniqueTrans += 1;
			}
			data.transCond.add(trans.getConditionExpression());
		}

		for (final ECState state : ecc.getECState()) {
			for (final ECAction action : state.getECAction()) {
				analyzeAction(action, data);
			}
		}

		data.uniqueOperands += data.uniqueTrans;
		data.uniqueOperator += data.uniqueTrans;
		data.operators +=data. actions.size() + data.transCond.size();
		data.operands += (data.event.size() + data.alg.size() + data.transCond.size());

		for (final int element : data.opCount) {
			if (element >= 1.0) {
				data.uniqueOperatorST++;
			}
		}
		data.n2Major = data.operands + data.operandST;
		data.n2 = data.uniqueOperands + data.operandST;
		data.n1Major = data.operators + data.operatorST;
		data.n1 = data.uniqueOperator + data.uniqueOperatorST;
		return data;
	}

	private static void analyzeAction(final ECAction action, final HalsteadData data) {
		final String actionCount = MessageFormat.format(Messages.HalsteadNumberOfActions,
				Integer.valueOf(data.actionCount));
		if (!data.actions.contains(actionCount)) {
			data.uniqueOperator += 1;
		}
		data.actions.add(actionCount);

		if (null != action.getOutput()) {
			if (!data.event.contains(action.getOutput().getName())) {
				data.uniqueOperands += 1;
			}
			data.event.add(action.getOutput().getName());
		}

		if (null != action.getAlgorithm()) {
			analyzeAlgorithm(action.getAlgorithm(), data);
		}
		data.actionCount++;
	}

	private static void analyzeAlgorithm(final Algorithm algorithm, final HalsteadData data) {
		if (!data.alg.contains(algorithm.getName())) {
			data.alg.add(algorithm.getName());
			data.uniqueOperands += 1;
		}

		if (!(algorithm instanceof STAlgorithm)) {
			return;
		}
		final var ast = StructuredTextParseUtil.parse((STAlgorithm) algorithm, null);
		if (ast != null) {
			calculateHalstead(ast.getStatements(), data);
		}

	}

	private static void calculateHalstead(final StatementList list, final HalsteadData data) {
		for (final Statement stmt : list.getStatements()) {
			if (stmt instanceof AssignmentStatement) {
				final var assignStmt = (AssignmentStatement) stmt;
				handleOperator(":=", data); //$NON-NLS-1$
				calculateHalstead(assignStmt.getExpression(), data);
			}
			if (stmt instanceof CaseStatement) {
				final var caseStmt = (CaseStatement) stmt;
				calculateHalstead(caseStmt.getExpression(), data);
				for (final var caseElement : caseStmt.getCase()) {
					calculateHalstead(caseElement.getStatements(), data);
				}
				final var elseStmt = caseStmt.getElse();
				if (elseStmt != null) {
					calculateHalstead(elseStmt.getStatements(), data);
				}
			}
			if (stmt instanceof IfStatement) {
				final var ifStmt = (IfStatement) stmt;
				calculateHalstead(ifStmt.getExpression(), data);
				calculateHalstead(ifStmt.getStatments(), data);
				for (final var elsifStmt : ifStmt.getElseif()) {
					calculateHalstead(elsifStmt.getExpression(), data);
					calculateHalstead(elsifStmt.getStatements(), data);
				}
				final var elseStmt = ifStmt.getElse();
				if(elseStmt != null) {
					calculateHalstead(elseStmt.getStatements(), data);
				}
			}
			if (stmt instanceof ForStatement) {
				final var forStmt = (ForStatement) stmt;
				calculateHalstead(forStmt.getFrom(), data);
				calculateHalstead(forStmt.getBy(), data);
				calculateHalstead(forStmt.getTo(), data);
				calculateHalstead(forStmt.getStatements(), data);
			}
			if (stmt instanceof RepeatStatement) {
				final var repeatStmt = (RepeatStatement) stmt;
				calculateHalstead(repeatStmt.getExpression(), data);
				calculateHalstead(repeatStmt.getStatements(), data);
			}
			if (stmt instanceof WhileStatement) {
				final var whileStmt = (WhileStatement) stmt;
				calculateHalstead(whileStmt.getExpression(), data);
				calculateHalstead(whileStmt.getStatements(), data);
			}
		}
	}

	private static void calculateHalstead(final Expression expr, final HalsteadData data) {
		if (expr instanceof BinaryExpression) {
			final var binExpr = (BinaryExpression) expr;
			handleOperator(binExpr.getOperator().getLiteral(), data);
			calculateHalstead(binExpr.getLeft(), data);
			calculateHalstead(binExpr.getRight(), data);
		}
		if (expr instanceof UnaryExpression) {
			final var unaryExpr = (UnaryExpression) expr;
			handleOperator(unaryExpr.getOperator().getLiteral(), data);
			calculateHalstead(unaryExpr.getExpression(), data);
		}
	}

	private static void handleOperator(final String op, final HalsteadData data) {
		final int operatorIndex = Arrays.asList(HalsteadData.ST_OPERATORS).indexOf(op);
		if (operatorIndex >= 0) {
			data.operatorST++;
			data.opCount[operatorIndex]++;
			data.operandST += HalsteadData.ST_OPERANDS_WEIGHT[operatorIndex];
		}
	}

	@Override
	public List<MetricResult> getResults() {

		final List<MetricResult> results = new ArrayList<>();

		final HalsteadData hData= (HalsteadData) this.data;

		results.add(new MetricResult(Messages.HalsteadDisctinctOperatorsN1,hData.n1));
		results.add(new MetricResult(Messages.HalsteadDisctinctOperatorsN2, hData.n2));
		results.add(new MetricResult(Messages.HalsteadTotalNumberOfOperatorsN1, hData.n1Major));
		results.add(new MetricResult(Messages.HalsteadTotalNumberOfOperatorsN2, hData.n2Major));

		final double nMAjor = hData.n1Major + hData.n2Major;
		final double n = hData.n1 + hData.n2;
		final double nHat = (hData.n1 * Math.log(hData.n1) / Math.log(2) + hData.n2 * Math.log(hData.n2) / Math.log(2));
		final double pr = nHat / nMAjor;
		final double v = nMAjor * Math.log(n) / Math.log(2);
		final double d = hData.n1 / 2 * hData.n2Major / hData.n2;
		final double e = d * v;

		results.add(new MetricResult(Messages.HalsteadProgramLength, nMAjor));
		results.add(new MetricResult(Messages.HalsteadProgramVocabulary, n));
		results.add(new MetricResult(Messages.HalsteadEstimatedLength, nHat));
		results.add(new MetricResult(Messages.HalsteadPurityRatio, pr));
		results.add(new MetricResult(Messages.HalsteadProgramVolume, v));
		results.add(new MetricResult(Messages.HalsteadDifficulty, d));
		results.add(new MetricResult(Messages.HalsteadProgramEffort, e));

		return results;
	}

	@Override
	protected MetricData createDataType() {

		return new HalsteadData();
	}

	@Override
	protected MetricData analyzeSFB(final SimpleFBType simpleFBType) {
		final HalsteadData data = new HalsteadData();

		simpleFBType.getAlgorithm().forEach(alg -> analyzeAlgorithm(alg, data));
		return data;
	}

}
