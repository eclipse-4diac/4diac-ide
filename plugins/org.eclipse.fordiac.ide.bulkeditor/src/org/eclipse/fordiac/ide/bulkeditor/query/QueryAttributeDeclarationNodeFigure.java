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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.bulkeditor.QueryUIPreferenceConstants;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.widgets.AttributeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionProposalProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Text;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.GraphNode;

public class QueryAttributeDeclarationNodeFigure extends QueryNodeFigure {

	private static final Color COLOR_VALUE_BG = QueryUIPreferenceConstants.getDefaultQueryBackground();
	private static final Color COLOR_VALUE_BORDER = QueryUIPreferenceConstants.getValueBorder();
	private static final int MIN_VALUE_WIDTH = 160;

	private final FigureCanvas canvas;
	private final IProject project;
	private Label valueLabel;
	private GridData valueGd;

	public QueryAttributeDeclarationNodeFigure(final EObject element, final FigureCanvas canvas,
			final IProject project) {
		super(element);
		this.canvas = canvas;
		this.project = project;
		add(createBody(element));
	}

	private Figure createBody(final EObject element) {
		final Figure body = new Figure();
		final ToolbarLayout bodyLayout = new ToolbarLayout(false);
		bodyLayout.setStretchMinorAxis(true);
		bodyLayout.setSpacing(1);
		body.setLayoutManager(bodyLayout);
		body.setBorder(new MarginBorder(2, 6, 4, 6));
		body.setOpaque(true);

		body.add(createEditableRow(QueryModelHelper.FEATURE_NAME, element));

		return body;
	}

	private Figure createEditableRow(final String featureName, final EObject element) {
		final Figure row = new Figure();
		final GridLayout gl = new GridLayout(2, false);
		gl.marginHeight = 2;
		gl.marginWidth = 4;
		row.setLayoutManager(gl);

		final Label nameLabel = new Label(featureName + ":"); //$NON-NLS-1$
		row.add(nameLabel);
		gl.setConstraint(nameLabel, new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		final EStructuralFeature feature = element.eClass().getEStructuralFeature(featureName);
		final String initialValue = feature != null && element.eIsSet(feature) ? String.valueOf(element.eGet(feature))
				: ""; //$NON-NLS-1$

		this.valueLabel = new Label(initialValue);
		valueLabel.setOpaque(true);
		valueLabel.setBackgroundColor(COLOR_VALUE_BG);
		valueLabel.setBorder(new LineBorder(COLOR_VALUE_BORDER, 1));
		valueLabel.addMouseListener(new MouseListener.Stub() {
			@Override
			public void mouseDoubleClicked(final MouseEvent me) {
				if (canvas instanceof final Graph graph) {
					graph.setSelection(new GraphItem[0]);
				}
				openDirectEdit(valueLabel, element);
			}
		});
		row.add(valueLabel);
		valueGd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		valueGd.widthHint = computeValueWidth(valueLabel);
		gl.setConstraint(valueLabel, valueGd);

		return row;
	}

	private void openDirectEdit(final Label valueLabel, final EObject element) {
		if (canvas == null || canvas.isDisposed()) {
			return;
		}

		final var figureBounds = valueLabel.getBounds().getCopy();
		valueLabel.translateToAbsolute(figureBounds);

		final Text searchText = new Text(canvas, SWT.BORDER);
		searchText.setText(valueLabel.getText());
		searchText.setBounds(figureBounds.x, figureBounds.y, figureBounds.width, figureBounds.height);
		searchText.selectAll();
		searchText.setFocus();

		attachProposalAdapter(searchText);

		final Runnable commit = () -> {
			if (!searchText.isDisposed()) {
				final String newValue = searchText.getText() != null ? searchText.getText() : ""; //$NON-NLS-1$
				valueLabel.setText(newValue);
				searchText.dispose();
				QueryModelHelper.setAttributeDeclarationName(element, newValue);
				resizeToFit();
			}
		};
		final Runnable cancel = () -> {
			if (!searchText.isDisposed()) {
				searchText.dispose();
			}
		};

		searchText.addListener(SWT.FocusOut, _ -> commit.run());
		searchText.addListener(SWT.KeyDown, e -> {
			if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
				commit.run();
			} else if (e.keyCode == SWT.ESC) {
				cancel.run();
			}
		});
	}

	private void attachProposalAdapter(final Text searchText) {
		final IContentProposalProvider proposalProvider = new TypeSelectionProposalProvider(
				() -> TypeLibraryManager.INSTANCE.getTypeLibrary(project), AttributeSelectionContentProvider.INSTANCE);
		final ContentProposalAdapter proposalAdapter = new ContentProposalAdapter(searchText, new TextContentAdapter(),
				proposalProvider, KeyStroke.getInstance(SWT.CTRL, SWT.SPACE),
				NatTableWidgetFactory.getActivationChars());
		proposalAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
	}

	private void resizeToFit() {
		valueGd.widthHint = computeValueWidth(valueLabel);
		invalidateTree();
		final var prefSize = getPreferredSize();
		setSize(prefSize);
		revalidate();

		if (canvas instanceof final Graph graph) {
			for (final Object obj : graph.getNodes()) {
				if (obj instanceof final GraphNode gn && gn.getData() == getElement()) {
					gn.setSize(prefSize.width, prefSize.height);
					break;
				}
			}
		}
	}

	private static int computeValueWidth(final Label label) {
		return Math.max(MIN_VALUE_WIDTH, label.getPreferredSize().width + 10);
	}
}