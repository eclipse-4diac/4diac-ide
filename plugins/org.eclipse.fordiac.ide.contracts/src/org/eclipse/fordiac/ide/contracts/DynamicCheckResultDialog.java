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
	private static final Color AXIS_LIGHT_COLOR = new Color(200, 200, 200);
	private static final Color INTERVAL_COLOR = new Color(255, 149, 14);
	private static final Color EVENT_COLOR = new Color(1, 34, 105);
	private static final Color ISSUE_COLOR = new Color(207, 8, 8);
	private static final Color FULFILL_COLOR = new Color(27, 176, 11);
	private static final int LINE_HEIGHT = 25;
	private static final int LINE_PAD = 5;
	private static final int DIAGRAM_PAD = 10;
	private static final int MARKER_SIZE = 8;
	private static final int MAX_RULES = 15;

	private final DynamicCheckResult result;
	private final double diagramMax;
	private final int[] triangleVertBuf;
	private StyledText[] ruleTexts;
	private Label[] ruleTextLabels;
	private Button rulesUpBtn;
	private Button rulesDownBtn;
	private Label displayRangeLbl;
	private Label rulePageLabel;
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
		triangleVertBuf = new int[6];

		double upperBound = 0;
		double lastEvent = 0;
		for (final RuleData ruleData : result.rules()) {
			if (!ruleData.markers().isEmpty()) {
				if (ruleData.markers().getLast().timestampNs() > lastEvent) {
					lastEvent = ruleData.markers().getLast().timestampNs();
				}
				// try to set default range to show first ~5 events
				final int idx = Math.min(5, ruleData.markers().size() - 1);
				upperBound = Math.max(upperBound, ruleData.markers().get(idx).timestampNs());
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
		rulePageLabel = new Label(composite, SWT.NONE);
		rulePageLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		updateRulePageLabel();
		createTimeLineNav(composite);

		return dialogArea;
	}

	private void createRuleList(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gLayout = new GridLayout(2, false);
		gLayout.verticalSpacing = 0;
		gLayout.horizontalSpacing = 0;
		composite.setLayout(gLayout);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));

		rulesUpBtn = new Button(composite, SWT.NONE);
		rulesUpBtn.setText("^"); //$NON-NLS-1$
		rulesUpBtn.setEnabled(false);
		GridData gData = new GridData(SWT.FILL, SWT.TOP, true, false);
		gData.horizontalSpan = 2;
		rulesUpBtn.setLayoutData(gData);
		rulesUpBtn.addSelectionListener(listener(e -> navigateRules(-1)));

		ruleTexts = new StyledText[nRules()];
		ruleTextLabels = new Label[nRules()];
		for (int i = 0; i < ruleTexts.length; i++) {
			final Label lbl = new Label(composite, SWT.SINGLE);
			lbl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			lbl.setForeground(new Color(128, 128, 128));
			ruleTextLabels[i] = lbl;

			final StyledText txt = new StyledText(composite, SWT.SINGLE);
			txt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			txt.setMargins(5, 5, 5, 5);
			txt.setEnabled(false);
			ruleTexts[i] = txt;

			if (i % 2 == 0) {
				lbl.setBackground(new Color(255, 255, 255));
				txt.setBackground(new Color(255, 255, 255));
			}
		}
		fillRuleList();

		rulesDownBtn = new Button(composite, SWT.NONE);
		rulesDownBtn.setText("v"); //$NON-NLS-1$
		rulesDownBtn.setEnabled(result.rules().size() > MAX_RULES);
		gData = new GridData(SWT.FILL, SWT.TOP, true, false);
		gData.horizontalSpan = 2;
		rulesDownBtn.setLayoutData(gData);
		rulesDownBtn.addSelectionListener(listener(e -> navigateRules(+1)));
	}

	private void fillRuleList() {
		String lastName = "."; //$NON-NLS-1$
		for (int i = 0; i < nRules(); i++) {
			final RuleData ruleData = result.rules().get(i + firstRuleIdx);
			final ContractRule rule = ruleData.rule();

			final String name = rule.getOwner().getName();
			if (lastName.equals(name)) {
				ruleTextLabels[i].setText("\""); //$NON-NLS-1$
			} else {
				ruleTextLabels[i].setText(name);
				lastName = name;
			}

			final StyledText txt = ruleTexts[i];
			final String ruleString = rule.toString();
			txt.setText(ruleString);
			txt.setStyleRanges(ContractScanner.getStyleRanges(ruleString));
		}
		updateRulePageLabel();

		if (ruleTexts.length > 0) {
			ruleTexts[0].getParent().layout();
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
		canvas.addMouseWheelListener(e -> navigateDiagramStep(e.count, true));
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

		// === draw background
		e.gc.setBackground(new Color(255, 255, 255));
		int linePos = diagramArea.y + LINE_HEIGHT - LINE_PAD;
		final int nFilled = nRules() % 2 == 0 ? nRules() / 2 : nRules() / 2 + 1;
		for (int i = 0; i < nFilled; i++) {
			e.gc.fillRectangle(0, linePos, canvasArea.width, LINE_HEIGHT);
			linePos += LINE_HEIGHT * 2;
		}

		// === draw time line axis
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
			e.gc.setForeground(AXIS_LIGHT_COLOR);
			e.gc.drawLine(xPos, diagramArea.height, xPos, DIAGRAM_PAD);
			e.gc.setForeground(AXIS_COLOR);
			e.gc.drawLine(xPos, diagramArea.height, xPos, diagramArea.height - 5);

			final String markerText = String.valueOf((lowest + i * stepSizeNs) / unitScale);
			final Point extent = e.gc.textExtent(markerText);
			e.gc.drawText(markerText, xPos - extent.x / 2, diagramArea.height, true);
		}

		// === draw rule data
		linePos = diagramArea.y + LINE_HEIGHT - LINE_PAD;
		for (int i = 0; i < nRules(); i++) {
			final DynamicCheckResult.RuleData ruleData = result.rules().get(i + firstRuleIdx);

			drawRuleIntervals(e.gc, ruleData, linePos, canvasArea.width);
			drawRuleMarkers(e.gc, ruleData, linePos, canvasArea.width);
			linePos += LINE_HEIGHT;
		}
	}

	private void drawRuleIntervals(final GC gc, final RuleData ruleData, final int linePos, final int maxWidth) {
		final int middlePos = linePos + LINE_HEIGHT / 2;
		final double jitter = ruleData.rule().getJitter();
		for (int j = firstDrawIndexInterval(ruleData.intervals(), jitter); j < ruleData.intervals().size(); j++) {
			final CInterval interval = ruleData.intervals().get(j);

			// draw intervals
			gc.setForeground(INTERVAL_COLOR);
			gc.setBackground(INTERVAL_COLOR);
			gc.setAlpha(128);
			int start = drawInterval(gc, interval, linePos, true);

			if (jitter > 0) {
				final CInterval intervalJitter = interval.addJitter(jitter);
				start = drawInterval(gc, intervalJitter, linePos, false);
			}

			// draw arrows to intervals
			gc.setForeground(EVENT_COLOR);
			gc.setBackground(EVENT_COLOR);
			gc.setAlpha(255);

			switch (ruleData.rule().getType()) {
			case REPETITION:
				if (j == 0) {
					break; // don't draw arrow to first interval (offset)
				}
				//$FALL-THROUGH$ to actual arrow drawing (same for reaction/repetition)
			case REACTION, CAUSAL_REACTION:
				drawArrowLR(gc, interval.getLowerBound() - ruleData.rule().getInterval().getLowerBound(),
						interval.getLowerBound(), middlePos);
				break;
			case AGE, CAUSAL_AGE:
				// TODO: backwards arrow for ages
				break;
			default:
				break; // no arrows to draw for otherwise
			}
			if (start > maxWidth) {
				break; // don't draw next interval (would be out of bounds)
			}
		}
	}

	private void drawRuleMarkers(final GC gc, final RuleData ruleData, final int linePos, final int maxWidth) {
		final int markerYPos = linePos + LINE_HEIGHT / 2;
		final int p = MARKER_SIZE / 2;
		for (int j = firstDrawIndex(ruleData.markers()); j < ruleData.markers().size(); j++) {
			final EventOccurrence eo = ruleData.markers().get(j);
			final int xPos = ns2Pixel(eo.timestampNs());
			if (xPos > maxWidth) {
				break; // don't draw out of bounds markers (list is sorted)
			}

			switch (eo.type()) {
			case RECORDED -> {
				gc.setForeground(AXIS_COLOR);
				gc.setBackground(switch (eo.state()) {
				case NOT_SET -> EVENT_COLOR;
				case FULFILLING -> FULFILL_COLOR;
				case ISSUE -> ISSUE_COLOR;
				});
				drawArrowHeadUD(gc, xPos, linePos + LINE_HEIGHT, MARKER_SIZE, -LINE_HEIGHT / 2 - 2);
				final String txt = eo.getShortName();
				final Point extent = gc.textExtent(txt);
				gc.drawText(txt, xPos - extent.x / 2, linePos, true);
			}
			case MISSED_MARKER -> {
				gc.setForeground(ISSUE_COLOR);
				gc.drawLine(xPos - p, markerYPos + p, xPos + p, markerYPos - p);
				gc.drawLine(xPos + p, markerYPos + p, xPos - p, markerYPos - p);
			}
			default -> {
				// nothing to draw for other types
			}
			}
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
		return start;
	}

	private void drawArrowLR(final GC gc, final double start, final double end, final int y) {
		final int startP = ns2Pixel(start);
		final int endP = ns2Pixel(end);
		gc.drawLine(startP, y, endP, y);
		final int markerAndHalf = MARKER_SIZE + MARKER_SIZE / 2;
		if (start < end) {
			drawArrowHeadLR(gc, endP - markerAndHalf, y, MARKER_SIZE, markerAndHalf);
		} else {
			drawArrowHeadLR(gc, endP - markerAndHalf, y, MARKER_SIZE, -markerAndHalf);
		}
	}

	private void drawArrowHeadUD(final GC gc, final int x, final int y, final int baseWidth, final int height) {
		triangleVertBuf[0] = x - baseWidth / 2;
		triangleVertBuf[1] = y;
		triangleVertBuf[2] = x;
		triangleVertBuf[3] = y + height;
		triangleVertBuf[4] = x + baseWidth / 2;
		triangleVertBuf[5] = y;
		gc.fillPolygon(triangleVertBuf);
	}

	private void drawArrowHeadLR(final GC gc, final int x, final int y, final int baseWidth, final int length) {
		triangleVertBuf[0] = x;
		triangleVertBuf[1] = y + baseWidth / 2;
		triangleVertBuf[2] = x + length;
		triangleVertBuf[3] = y;
		triangleVertBuf[4] = x;
		triangleVertBuf[5] = y - baseWidth / 2;
		gc.fillPolygon(triangleVertBuf);
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

	private int firstDrawIndexInterval(final List<CInterval> list, final double jitter) {
		final double actualStart = pixel2Ns(-DIAGRAM_PAD) - jitter;
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

	@SuppressWarnings("boxing")
	private void updateRulePageLabel() {
		if (rulePageLabel != null) {
			rulePageLabel.setText("%d-%d / %d".formatted(firstRuleIdx + 1, //$NON-NLS-1$
					firstRuleIdx + nRules(), result.rules().size()));
		}
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
