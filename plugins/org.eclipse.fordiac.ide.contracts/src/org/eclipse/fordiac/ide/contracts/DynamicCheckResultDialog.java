/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.fordiac.ide.Utils;
import org.eclipse.fordiac.ide.contractSpec.Unit;
import org.eclipse.fordiac.ide.contracts.DynamicCheckResult.RuleData;
import org.eclipse.fordiac.ide.contracts.dialogs.ContractCheckResultDialog;
import org.eclipse.fordiac.ide.ui.utils.ContractScanner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DynamicCheckResultDialog extends ContractCheckResultDialog {

	private static final Color AXIS_COLOR = new Color(0, 0, 0);
	private static final Color INTERVAL_COLOR = new Color(255, 149, 14);
	private static final Color EVENT_COLOR = new Color(1, 34, 105);
	private static final Color ISSUE_COLOR = new Color(255, 0, 0);
	private static final Color FULFILL_COLOR = new Color(26, 196, 34);
	private static final int LINE_HEIGHT = 25;
	private static final int LINE_PAD = 5;
	private static final int DIAGRAM_PAD = 10;
	private static final int MARKER_SIZE = 8;
	private static final int MAX_RULES = 15;

	private final DynamicCheckResult result;
	private final double diagramMax;
	private StyledText[] ruleTexts;
	private Button rulesUpBtn;
	private Button rulesDownBtn;
	private Label displayRangeLbl;
	private Canvas canvas;
	private Rectangle diagramArea;

	private CInterval oldRange;
	private CInterval displayRange;
	private Unit displayUnit;
	private int firstRuleIdx = 0;
	private boolean dragging = false;
	private double dragStartNs = 0;
	private double dragNs = 0;

	public DynamicCheckResultDialog(final DynamicCheckResult result, final boolean networkCheck, final Shell shell) {
		super(result.system(), networkCheck, shell);
		this.result = result;

		double upperBound = 0;
		double lastEvent = 0;
		if (!result.eventOccurrences().isEmpty()) {
			// set default range to try showing the 10 first events
			final int idx = Math.min(10, result.eventOccurrences().size() - 1);
			upperBound = result.eventOccurrences().get(idx).timestampNs();
			lastEvent = result.eventOccurrences().getLast().timestampNs();
		}
		for (final RuleData ruleData : result.rules()) {
			if (!ruleData.markers().isEmpty() && ruleData.markers().getLast().timestampNs() > lastEvent) {
				lastEvent = ruleData.markers().getLast().timestampNs();
			}
		}

		// no events, upper bound is just 10ms
		if (lastEvent == 0) {
			lastEvent = 10e6;
		}
		if (upperBound == 0) {
			upperBound = 10e6;
		}

		displayRange = new CInterval('[', 0, upperBound, ']');
		displayUnit = Utils.getFittingUnit(upperBound);
		// max diagram is last event + some padding
		diagramMax = lastEvent + Utils.getInNs(1, displayUnit);
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		super.createCustomArea(parent);


		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gLayout = new GridLayout(2, false);
		gLayout.horizontalSpacing = 0;
		composite.setLayout(gLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		createRuleList(composite);
		createDiagram(composite);
		final Label lbl = new Label(composite, SWT.NONE); // empty label to use cell
		lbl.setText(""); //$NON-NLS-1$
		createTimeLineNav(composite);

		return dialogArea;
	}

	private void createRuleList(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gLayout = new GridLayout(1, false);
		gLayout.verticalSpacing = 0;
		composite.setLayout(gLayout);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));

		rulesUpBtn = new Button(composite, SWT.NONE);
		rulesUpBtn.setText("^"); //$NON-NLS-1$
		rulesUpBtn.setEnabled(false);
		rulesUpBtn.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		rulesUpBtn.addSelectionListener(listener(e -> navigateRules(-1)));

		ruleTexts = new StyledText[nRules()];
		for (int i = 0; i < ruleTexts.length; i++) {
			final StyledText txt = new StyledText(composite, SWT.SINGLE);
			if (i % 2 == 0) {
				txt.setBackground(new Color(255, 255, 255));
			}
			txt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			txt.setAlignment(SWT.RIGHT);
			txt.setMargins(5, 5, 5, 5);
			txt.setEnabled(false);
			ruleTexts[i] = txt;
		}
		fillRuleList();

		rulesDownBtn = new Button(composite, SWT.NONE);
		rulesDownBtn.setText("v"); //$NON-NLS-1$
		rulesDownBtn.setEnabled(result.rules().size() > MAX_RULES);
		rulesDownBtn.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		rulesDownBtn.addSelectionListener(listener(e -> navigateRules(+1)));
	}

	private void fillRuleList() {
		for (int i = 0; i < nRules(); i++) {
			final RuleData ruleData = result.rules().get(i + firstRuleIdx);
			final ContractRule rule = ruleData.rule();
			final StyledText txt = ruleTexts[i];
			final String ruleString = rule.toString();
			txt.setText(ruleString);
			txt.setStyleRanges(ContractScanner.getStyleRanges(ruleString));
		}
	}

	private void createDiagram(final Composite parent) {
		canvas = new Canvas(parent, SWT.DOUBLE_BUFFERED);
		final GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gData.widthHint = 800;
		gData.heightHint = 10 * LINE_HEIGHT;
		canvas.setLayoutData(gData);
		canvas.addPaintListener(this::drawDiagram);
		canvas.addDragDetectListener(e -> {
			dragging = true;
			dragStartNs = pixel2Ns(e.x);
			oldRange = displayRange;
		});
		canvas.addMouseMoveListener(e -> {
			if (dragging) {
				dragNs = pixel2Ns(e.x, oldRange);
				navigateDiagram(dragStartNs - dragNs, false);
				dragStartNs = dragNs;
			}
		});
		canvas.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(final MouseEvent e) {
				// not needed
			}

			@Override
			public void mouseDown(final MouseEvent e) {
				// not needed
			}

			@Override
			public void mouseUp(final MouseEvent e) {
				dragging = false;
			}
		});
		canvas.addMouseWheelListener(e -> {
			navigateDiagramStep(e.count, true);
		});
	}

	private void createTimeLineNav(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(5, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Button left = new Button(composite, SWT.NONE);
		left.setText("<"); //$NON-NLS-1$
		left.addSelectionListener(listener(e -> navigateDiagramStep(-1, false)));

		displayRangeLbl = new Label(composite, SWT.NONE);
		displayRangeLbl.setAlignment(SWT.CENTER);
		displayRangeLbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		updateDisplayRange();

		final Button right = new Button(composite, SWT.NONE);
		right.setText(">"); //$NON-NLS-1$
		right.addSelectionListener(listener(e -> navigateDiagramStep(+1, false)));
		final Button zoomIn = new Button(composite, SWT.NONE);
		zoomIn.setText("+"); //$NON-NLS-1$
		zoomIn.addSelectionListener(listener(e -> navigateDiagramStep(+1, true)));
		final Button zoomOut = new Button(composite, SWT.NONE);
		zoomOut.setText("-"); //$NON-NLS-1$
		zoomOut.addSelectionListener(listener(e -> navigateDiagramStep(-1, true)));
	}

	private void drawDiagram(final PaintEvent e) {
		e.gc.setAntialias(SWT.ON);
		final Rectangle canvasArea = canvas.getClientArea();
		diagramArea = new Rectangle(canvasArea.x + DIAGRAM_PAD, canvasArea.y + DIAGRAM_PAD,
				canvasArea.width - 2 * DIAGRAM_PAD, canvasArea.height - 2 * DIAGRAM_PAD);

		// draw background
		e.gc.setBackground(new Color(255, 255, 255));
		int linePos = diagramArea.y + LINE_HEIGHT - LINE_PAD;
		final int nFilled = nRules() % 2 == 0 ? nRules() / 2 : nRules() / 2 + 1;
		for (int i = 0; i < nFilled; i++) {
			e.gc.fillRectangle(0, linePos, canvasArea.width, LINE_HEIGHT);
			linePos += LINE_HEIGHT * 2;
		}

		drawRecordedEvents(e.gc, canvasArea.width);
		drawRuleData(e.gc, canvasArea.width);

		// draw time line axis
		e.gc.setForeground(AXIS_COLOR);
		e.gc.drawLine(0, diagramArea.height, canvasArea.width, diagramArea.height);

		double totalNs = displayRange.getDiameter();
		long stepSizeNs = 1; // 1, 2, 5, 10, 20, 50, 100, 200, ...

		while (totalNs / 10 > 10) {
			stepSizeNs *= 10;
			totalNs /= 10;
		}
		if (stepSizeNs / displayRange.getDiameter() < 0.02) {
			stepSizeNs *= 5;
		} else if (stepSizeNs / displayRange.getDiameter() < 0.05) {
			stepSizeNs *= 2;
		}

		// +1 for fence post problem, +1 for integer division truncation
		final int nTicks = (int) (displayRange.getDiameter() / stepSizeNs) + 2;

		final double unitScale = Utils.getInNs(1, displayUnit);
		final long lowest = Math.round(displayRange.getLowerBound() / stepSizeNs) * stepSizeNs;
		for (int i = 0; i < nTicks; i++) {
			final int xPos = ns2Pixel(lowest + i * stepSizeNs);
			e.gc.drawLine(xPos, diagramArea.height, xPos, diagramArea.height - 5);

			final String markerText = String.valueOf((lowest + i * stepSizeNs) / unitScale);
			final Point extent = e.gc.textExtent(markerText);
			e.gc.drawText(markerText, xPos - extent.x / 2, diagramArea.height, true);
		}
	}

	private void drawRecordedEvents(final GC gc, final int maxWidth) {
		gc.setForeground(EVENT_COLOR);
		gc.setBackground(EVENT_COLOR);
		final int[] vertBuf = new int[6];

		for (int i = firstDrawIndex(result.eventOccurrences()); i < result.eventOccurrences().size(); i++) {
			final EventOccurrence eo = result.eventOccurrences().get(i);
			final int xPos = ns2Pixel(eo.timestampNs());
			if (xPos > maxWidth) {
				break; // don't draw out of bounds events (list is sorted)
			}
			gc.drawLine(xPos, diagramArea.y, xPos, diagramArea.height);
			final Point extent = gc.textExtent(eo.eventName());
			gc.drawText(eo.eventName(), xPos - extent.x / 2, 0, true);

			// draw the arrow head
			vertBuf[0] = xPos;
			vertBuf[1] = diagramArea.y;
			vertBuf[2] = xPos - 5;
			vertBuf[3] = diagramArea.y + 15;
			vertBuf[4] = xPos + 5;
			vertBuf[5] = diagramArea.y + 15;
			gc.fillPolygon(vertBuf);
		}
	}

	private void drawRuleData(final GC gc, final int maxWidth) {
		int linePos = diagramArea.y + LINE_HEIGHT - LINE_PAD;
		final int[] vertBuf = new int[6];

		for (int i = 0; i < nRules(); i++) {
			// draw intervals
			final int middlePos = linePos + LINE_HEIGHT / 2;
			final DynamicCheckResult.RuleData ruleData = result.rules().get(i + firstRuleIdx);
			for (int j = firstDrawIndexInterval(ruleData.intervals()); j < ruleData.intervals().size(); j++) {
				final CInterval interval = ruleData.intervals().get(j);

				// draw intervals
				gc.setForeground(INTERVAL_COLOR);
				gc.setBackground(INTERVAL_COLOR);
				gc.setAlpha(128);
				int end = drawInterval(gc, interval, linePos, true);

				final double jitter = ruleData.rule().getJitter();
				if (jitter > 0) {
					final CInterval intervalJitter = interval.addJitter(jitter);
					end = drawInterval(gc, intervalJitter, linePos, false);
				}

				// draw arrows to intervals
				gc.setForeground(EVENT_COLOR);
				gc.setBackground(EVENT_COLOR);
				gc.setAlpha(255);
				if (ruleData.rule().getType() == ContractRule.Type.REPETITION && j != 0) {
					final int start = ns2Pixel(
							interval.getLowerBound() - ruleData.rule().getInterval().getLowerBound());
					end = ns2Pixel(interval.getLowerBound());
					gc.drawLine(start, middlePos, end, middlePos);

					// draw the arrow head
					vertBuf[0] = end - 12;
					vertBuf[1] = middlePos - 4;
					vertBuf[2] = end;
					vertBuf[3] = middlePos;
					vertBuf[4] = end - 12;
					vertBuf[5] = middlePos + 4;
					gc.fillPolygon(vertBuf);
				}

				if (end > maxWidth) {
					break; // don't draw next interval (would be out of bounds)
				}
			}

			// draw markers
			gc.setForeground(ISSUE_COLOR);
			gc.setBackground(FULFILL_COLOR);
			final int markerYPos = linePos + LINE_HEIGHT / 2;
			for (int j = firstDrawIndex(ruleData.markers()); j < ruleData.markers().size(); j++) {
				final EventOccurrence eo = ruleData.markers().get(j);
				final int xPos = ns2Pixel(eo.timestampNs());
				if (xPos > maxWidth) {
					break; // don't draw out of bounds markers (list is sorted)
				}
				final int p = MARKER_SIZE / 2;
				if (eo.type() == EventOccurrence.Type.FULFILL_MARKER) {
					gc.fillOval(xPos - p, markerYPos - p, MARKER_SIZE, MARKER_SIZE);
				} else {
					gc.drawLine(xPos - p, markerYPos + p, xPos + p, markerYPos - p);
					gc.drawLine(xPos + p, markerYPos + p, xPos - p, markerYPos - p);
				}
			}

			linePos += LINE_HEIGHT;
		}
	}

	private int drawInterval(final GC gc, final CInterval interval, final int yPos, final boolean fill) {
		final int start = ns2Pixel(interval.getLowerBound());
		final int end = ns2Pixel(interval.getUpperBound());
		final int width = Math.max(end - start, 1);
		gc.drawRectangle(start, yPos + LINE_PAD, width, LINE_HEIGHT - LINE_PAD * 2);
		if (fill) {
			gc.fillRectangle(start, yPos + LINE_PAD, width, LINE_HEIGHT - LINE_PAD * 2);
		}
		return end;
	}

	private int firstDrawIndex(final List<EventOccurrence> list) {
		final double actualStart = pixel2Ns(-DIAGRAM_PAD);
		final EventOccurrence key = new EventOccurrence("", actualStart); //$NON-NLS-1$

		int firstIndex = Collections.binarySearch(list, key);
		if (firstIndex < 0) {
			// if result <0, binarySearch returns the insertion point (see documentation)
			firstIndex = Math.abs(firstIndex + 1);
		}
		return firstIndex;
	}

	private int firstDrawIndexInterval(final List<CInterval> list) {
		final double actualStart = pixel2Ns(-DIAGRAM_PAD);
		final CInterval key = new CInterval('[', actualStart, actualStart, ']');

		int firstIndex = Collections.binarySearch(list, key,
				(a, b) -> Double.compare(a.getUpperBound(), b.getUpperBound()));
		if (firstIndex < 0) {
			// if result <0, binarySearch returns the insertion point (see documentation)
			firstIndex = Math.abs(firstIndex + 1);
		}
		return firstIndex;
	}

	private void navigateDiagramStep(final int direction, final boolean zoom) {
		if (zoom) {
			final double zoomAmount = Math.max(1, displayRange.getDiameter() / 10);
			navigateDiagram(zoomAmount * Math.signum(direction), zoom);
		} else {
			navigateDiagram(Utils.getInNs(1, displayUnit) * Math.signum(direction), zoom);
		}
	}

	private void navigateDiagram(final double amount, final boolean zoom) {
		if (zoom) {
			CInterval nRange = displayRange.addJitter(-amount);
			if (nRange.getLowerBound() + 1 >= nRange.getUpperBound()) {
				return; // already max zoom
			}
			if (nRange.getLowerBound() < 0) {
				nRange = new CInterval('[', 0, nRange.getUpperBound(), ']');
			}
			if (nRange.getUpperBound() > diagramMax) {
				nRange = new CInterval('[', nRange.getLowerBound(), diagramMax, ']');
			}
			displayRange = nRange;
		} else {
			final double diameter = displayRange.getDiameter();
			final CInterval nRange = displayRange.translate(amount);
			if (nRange.getLowerBound() < 0) {
				displayRange = new CInterval('[', 0, diameter, ']');
			} else if (nRange.getUpperBound() > diagramMax) {
				displayRange = new CInterval('[', diagramMax - diameter, diagramMax, ']');
			} else {
				displayRange = nRange;
			}
		}

		updateDisplayRange();
		canvas.redraw();
	}

	private void navigateRules(final int changeAmount) {
		final int nRules = result.rules().size();
		firstRuleIdx = Math.clamp(firstRuleIdx + changeAmount, 0, nRules - 1);

		rulesUpBtn.setEnabled(firstRuleIdx > 0);
		rulesDownBtn.setEnabled(firstRuleIdx < (nRules - MAX_RULES));

		fillRuleList();
		canvas.redraw();
	}

	@SuppressWarnings("boxing")
	private void updateDisplayRange() {
		displayUnit = Utils.getFittingUnit(displayRange.getDiameter());
		final double unit = Utils.getInNs(1, displayUnit);
		final double lv = displayRange.getLowerBound() / unit;
		final double uv = displayRange.getUpperBound() / unit;
		displayRangeLbl.setText("[%.2f, %.2f]%s".formatted(lv, uv, displayUnit)); //$NON-NLS-1$
	}

	private int nRules() {
		return Math.min(MAX_RULES, result.rules().size());
	}

	private int ns2Pixel(final double ns) {
		return ns2Pixel(ns, displayRange);
	}

	private int ns2Pixel(final double ns, final CInterval range) {
		// pre-clamp ns value to avoid extreme integer values for pixels
		final double pad = displayRange.getDiameter() * 0.1;
		final double nsClamped = Math.clamp(ns, displayRange.getLowerBound() - pad, displayRange.getUpperBound() + pad);

		final double shifted = nsClamped - range.getLowerBound();
		final double percentage = shifted / range.getDiameter();
		return (int) (percentage * diagramArea.width) + diagramArea.x;
	}

	private double pixel2Ns(final int pixel) {
		return pixel2Ns(pixel, displayRange);
	}

	private double pixel2Ns(final int pixel, final CInterval range) {
		final double percentage = (pixel - diagramArea.x) / (double) diagramArea.width;
		final double shifted = percentage * range.getDiameter();
		return shifted + range.getLowerBound();
	}

	private static SelectionListener listener(final Consumer<SelectionEvent> action) {
		return new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				action.accept(e);
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// nothing to do
			}
		};
	}
}
