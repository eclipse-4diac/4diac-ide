/*******************************************************************************
 * Copyright (c) 2019 Profactor GbmH, Johannes Kepler University Linz, HR Agrartechnik
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl - initial API and implementation and/or
 *   								  initial documentation
 *   Moritz Ortmeier - added page limit scaling, paper format and orientation
 *                     selection, printer based margins and robust handling of
 *                     missing printers
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.print;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.ILog;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PrinterGraphics;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A print preview Dialog where the user can specify some print options. Setting
 * / changing the properties of the printer lead to recalculating the "print"
 * area. Therefore, the preview should represent the output of the printer.
 */
public class PrintPreview extends Dialog {

	/**
	 * Standard paper formats the user can pick as the target page size. The
	 * printable area of the real printer is limited to the selected format, so
	 * a larger paper in the printer does not enlarge the page and a smaller one
	 * is never exceeded.
	 */
	private enum PaperFormat {
		A5(148, 210, Messages.PrintPreview_LABEL_PaperFormat_A5), A4(210, 297, Messages.PrintPreview_LABEL_PaperFormat_A4),
		A3(297, 420, Messages.PrintPreview_LABEL_PaperFormat_A3), A2(420, 594, Messages.PrintPreview_LABEL_PaperFormat_A2),
		A1(594, 841, Messages.PrintPreview_LABEL_PaperFormat_A1), A0(841, 1189, Messages.PrintPreview_LABEL_PaperFormat_A0),
		LETTER(215.9, 279.4, Messages.PrintPreview_LABEL_PaperFormat_Letter),
		LEGAL(215.9, 355.6, Messages.PrintPreview_LABEL_PaperFormat_Legal),
		TABLOID(279.4, 431.8, Messages.PrintPreview_LABEL_PaperFormat_Tabloid);

		private static final double MM_PER_INCH = 25.4;

		private final double widthMm;
		private final double heightMm;
		private final String label;

		PaperFormat(final double widthMm, final double heightMm, final String label) {
			this.widthMm = widthMm;
			this.heightMm = heightMm;
			this.label = label;
		}

		String label() {
			return label;
		}

		double widthInch() {
			return widthMm / MM_PER_INCH;
		}

		double heightInch() {
			return heightMm / MM_PER_INCH;
		}
	}

	/** The pages that are printed, both inclusive and starting with 1. */
	private record PageRange(int first, int last) {
		int count() {
			return last - first + 1;
		}
	}

	/** How the scale of the print is determined, in the order of the scale selection. */
	private enum ScaleMode {
		TILE(Messages.PrintPreview_LABEL_Tile), FIT_PAGE(Messages.PrintPreview_LABEL_FitPage),
		FIT_WIDTH(Messages.PrintPreview_LABEL_FitWidth), FIT_HEIGHT(Messages.PrintPreview_LABEL_FitHeight),
		PAGE_LIMIT(Messages.PrintPreview_LABEL_PageLimit);

		private final String label;

		ScaleMode(final String label) {
			this.label = label;
		}

		String label() {
			return label;
		}
	}

	// no MULTILINE: with it ^ does not match the empty string, so replacing a selection
	// (which first deletes it) would be rejected by the verify listeners
	private static final Pattern ONLY_DIGIT_PATTERN = Pattern.compile("^\\d*$"); //$NON-NLS-1$
	/** A positive integer without leading zeros, the empty text is allowed while editing. */
	private static final Pattern POSITIVE_NUMBER_PATTERN = Pattern.compile("^([1-9]\\d*)?$"); //$NON-NLS-1$

	/**
	 * Tolerance subtracted before Math.ceil() when computing page counts, so that
	 * a ratio which is mathematically exactly 1.0 (e.g. content scaled to exactly
	 * fit one page) doesn't get pushed to 2 by floating-point rounding noise from
	 * the scale computation.
	 */
	private static final double PAGE_COUNT_EPSILON = 1e-6;

	private static final int PAGE_LIMIT_SEARCH_STEPS = 40;

	private static final double MAX_PERCENT = 1000;
	/** Smallest percentage that is shown, below this the scale is no longer displayed accurately. */
	private static final double MIN_PERCENT = 0.1;
	private static final int MAX_PERCENT_LENGTH = 6;
	// digits with an optional decimal place, e.g. 0.3 for a scale below 1 %
	private static final Pattern PERCENT_PATTERN = Pattern.compile("^\\d*([.,]\\d?)?$"); //$NON-NLS-1$
	private static final int MAX_PAGE_LIMIT_DIGITS = 3;
	/** Digits of the page number that fit in the field of the current page. */
	private static final int MAX_PAGE_DIGITS = 4;
	/** Printing more pages than this has to be confirmed by the user. */
	private static final int CONFIRM_PAGE_COUNT = 5;

	/**
	 * The current page shown in the print preview. Always starting with 1.
	 */
	private int currentPage = 1;

	/**
	 * The number of pages that would be printed with the current settings.
	 */
	private int numberOfPages = 1;

	private Label numberOfPagesLabel;
	private Text currentPageText;

	private Button printBorder;

	private Combo scaleSelection;
	private Combo combo;
	private Combo orientationCombo;
	private Combo paperFormatCombo;
	private Text pageLimitText;
	/** The last valid page limit, an empty field being retyped keeps it. */
	private int pageLimit = 1;
	private Text percentText;

	private boolean isLandscape = false;

	/** The manually applied scale in percent, only changed by the user or by the page limit. */
	private double appliedPercent = 100;

	private PaperFormat paperFormat = PaperFormat.A4;

	private PrintMargin margin;

	private Printer printer;

	private Canvas canvas;

	private boolean blockCurrentPageUpdate = false;

	private final String printName;
	private final IFigure figure;

	/**
	 * Instantiates a new prints the preview.
	 *
	 * @param shell     the shell
	 * @param viewer    the viewer
	 * @param printName the print name
	 */
	public PrintPreview(final Shell shell, final GraphicalViewer viewer, final String printName) {
		super(shell);
		this.printName = printName;
		final LayerManager lm = (LayerManager) viewer.getEditPartForModel(LayerManager.ID);
		figure = lm.getLayer(LayerConstants.PRINTABLE_LAYERS);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		newShell.setText(Messages.PrintPreview_LABEL_PrintPreview);
		super.configureShell(newShell);
	}

	@Override
	protected int getShellStyle() {
		return SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL;
	}

	@Override
	public boolean close() {
		if (printer != null && !printer.isDisposed()) {
			printer.dispose();
			printer = null;
		}
		return super.close();
	}

	/**
	 * Adds some GUI elements for defining some print options to the specified
	 * composite
	 *
	 * @param composite The container of the elements
	 */
	private void createOptionsGUI(final Composite parent) {
		final GridLayout layout = new GridLayout(8, false);
		layout.marginHeight = 0;
		parent.setLayout(layout);

		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_Scale);
		scaleSelection = new Combo(parent, SWT.READ_ONLY);
		for (final ScaleMode mode : ScaleMode.values()) {
			scaleSelection.add(mode.label());
		}
		scaleSelection.select(0);

		pageLimitText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		pageLimitText.setText("1"); //$NON-NLS-1$
		pageLimitText.addListener(SWT.Verify, verifier(POSITIVE_NUMBER_PATTERN));
		pageLimitText.setTextLimit(MAX_PAGE_LIMIT_DIGITS);
		pageLimitText.setLayoutData(widthForDigits(pageLimitText, MAX_PAGE_LIMIT_DIGITS));
		pageLimitText.addListener(SWT.Modify, ev -> {
			// an empty field is a value that is being retyped, keep the last limit meanwhile
			if (!pageLimitText.getText().isEmpty()) {
				pageLimit = Integer.parseInt(pageLimitText.getText());
				if (margin != null && canvas != null && !canvas.isDisposed()) {
					updatePageNumbers();
					canvas.redraw();
				}
			}
		});
		pageLimitText.addListener(SWT.FocusOut, ev -> {
			// show the limit that is used if the field was left empty
			if (pageLimitText.getText().isEmpty()) {
				pageLimitText.setText(String.valueOf(pageLimit));
			}
		});
		pageLimitText.setEnabled(false);

		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_Pages);

		percentText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		percentText.setText("100"); //$NON-NLS-1$
		percentText.addListener(SWT.Verify, verifier(PERCENT_PATTERN));
		percentText.setTextLimit(MAX_PERCENT_LENGTH);
		percentText.setLayoutData(widthForDigits(percentText, MAX_PERCENT_LENGTH));
		percentText.addListener(SWT.DefaultSelection, _ -> applyPercent());
		percentText.setEnabled(true);
		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_Percent);
		final Button setPercentButton = new Button(parent, SWT.PUSH);
		setPercentButton.setText(Messages.PrintPreview_LABEL_Set);
		setPercentButton.setEnabled(true);
		setPercentButton.addListener(SWT.Selection, _ -> applyPercent());

		scaleSelection.addListener(SWT.Selection, ev -> {
			final ScaleMode mode = getScaleMode();
			pageLimitText.setEnabled(mode == ScaleMode.PAGE_LIMIT);
			// a manual percentage only makes sense where it is applied, the fit modes
			// compute the scale themselves
			final boolean percentApplicable = mode == ScaleMode.TILE || mode == ScaleMode.PAGE_LIMIT;
			percentText.setEnabled(percentApplicable);
			setPercentButton.setEnabled(percentApplicable);
			updatePageNumbers();
			canvas.redraw();
		});

		printBorder = new Button(parent, SWT.CHECK);
		printBorder.setText(Messages.PrintPreview_LABEL_PrintBorder);
		printBorder.setSelection(true);
		printBorder.addListener(SWT.Selection, _ -> canvas.redraw());

		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_Margin);
		combo = new Combo(parent, SWT.READ_ONLY);
		combo.add("0.5"); //$NON-NLS-1$
		combo.add("1.0"); //$NON-NLS-1$
		combo.add("1.5"); //$NON-NLS-1$
		combo.add("2.0"); //$NON-NLS-1$
		combo.add("2.5"); //$NON-NLS-1$
		combo.add("3.0"); //$NON-NLS-1$
		combo.select(1);
		combo.addListener(SWT.Selection, _ -> updatePrinter());
		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_CM);

		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_Orientation);
		orientationCombo = new Combo(parent, SWT.READ_ONLY);
		orientationCombo.add(Messages.PrintPreview_LABEL_Portrait);
		orientationCombo.add(Messages.PrintPreview_LABEL_Landscape);
		orientationCombo.select(0);
		orientationCombo.addListener(SWT.Selection, ev -> {
			isLandscape = orientationCombo.getSelectionIndex() == 1;
			updatePrinter();
		});

		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_PaperFormat);
		paperFormatCombo = new Combo(parent, SWT.READ_ONLY);
		for (final PaperFormat format : PaperFormat.values()) {
			paperFormatCombo.add(format.label());
		}
		paperFormatCombo.select(paperFormat.ordinal());
		paperFormatCombo.addListener(SWT.Selection, ev -> {
			paperFormat = PaperFormat.values()[paperFormatCombo.getSelectionIndex()];
			updatePrinter();
		});
	}

	/** The selected margin in inches, the selection is in cm. */
	private double getMarginInch() {
		return Double.parseDouble(combo.getItem(combo.getSelectionIndex())) / 2.54;
	}

	/** Recalculates the page with the current printer, e.g. after the margin, the orientation or the format changed. */
	private void updatePrinter() {
		setPrinter(printer, getMarginInch());
	}

	/**
	 * Checks which print option (Tile, Fit Page, ...) is selected.
	 *
	 * @return the selected scale mode
	 */
	private ScaleMode getScaleMode() {
		return ScaleMode.values()[scaleSelection.getSelectionIndex()];
	}

	@Override
	protected Control createContents(final Composite parent) {
		parent.setSize(800, 600);

		final Composite composite = (Composite) super.createDialogArea(parent);
		createButtonArea(composite);

		final Label seperator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		seperator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		/* the preview */
		canvas = new Canvas(composite, SWT.NONE);
		final GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 4;
		canvas.setLayoutData(gridData);

		canvas.addPaintListener(e -> {
			// the page that is really printed, so the preview paginates and clips like the output
			final Rectangle printerBounds = new Rectangle(0, 0, margin.getPageWidth(), margin.getPageHeight());
			final Point canvasSize = canvas.getSize();

			double viewScaleFactor = canvasSize.x * 1.0 / printerBounds.width;
			viewScaleFactor = Math.min(viewScaleFactor, canvasSize.y * 1.0 / printerBounds.height);

			final int offsetX = (canvasSize.x - (int) (viewScaleFactor * printerBounds.width)) / 2;
			final int offsetY = (canvasSize.y - (int) (viewScaleFactor * printerBounds.height)) / 2;

			e.gc.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_WHITE));
			// draws the page layout
			e.gc.fillRectangle(offsetX, offsetY, (int) (viewScaleFactor * printerBounds.width),
					(int) (viewScaleFactor * printerBounds.height));

			final int marginOffsetX = offsetX + (int) (viewScaleFactor * margin.getLeft());
			final int marginOffsetY = offsetY + (int) (viewScaleFactor * margin.getTop());

			final double scale = getScale();
			final double previewScaleFactor = viewScaleFactor * scale;

			final Graphics g = new SWTGraphics(e.gc);
			g.scale(previewScaleFactor);
			g.translate((int) (marginOffsetX / previewScaleFactor), (int) (marginOffsetY / previewScaleFactor));

			drawOnePage(scale, g, currentPage);

			g.dispose();

		});
		setPrinter(null, getMarginInch());

		return composite;
	}

	/**
	 * The area of the figure that is printed on a page. The size is the distance to
	 * the origin of the next column and row, not the truncated page size. With a
	 * scaled page size that is not a whole number, the pages therefore connect
	 * without a gap or an overlap.
	 */
	private org.eclipse.draw2d.geometry.Rectangle getClipRectForPage(final int page, final double scale) {
		final org.eclipse.draw2d.geometry.Rectangle bounds = getPrintArea();
		final double scaledPageWidth = margin.getWidth() / scale;
		final double scaledPageHeight = margin.getHeight() / scale;

		final int cols = (int) pagesAlong(bounds.width(), scale, margin.getWidth());
		final int currentColumn = (page - 1) % cols;
		final int currentRow = (page - 1) / cols;

		final int x = pageOrigin(bounds.x, currentColumn, scaledPageWidth);
		final int y = pageOrigin(bounds.y, currentRow, scaledPageHeight);
		return new org.eclipse.draw2d.geometry.Rectangle(x, y,
				pageOrigin(bounds.x, currentColumn + 1, scaledPageWidth) - x,
				pageOrigin(bounds.y, currentRow + 1, scaledPageHeight) - y);
	}

	/** The epsilon keeps floating-point noise from rounding an exact origin down. */
	private static int pageOrigin(final int start, final int index, final double scaledPageSize) {
		return (int) Math.floor(start + index * scaledPageSize + PAGE_COUNT_EPSILON);
	}

	/**
	 * Returns the area of the printed content, i.e. the bounds of the printable
	 * layers.
	 */
	private org.eclipse.draw2d.geometry.Rectangle getPrintArea() {
		return figure.getBounds().getCopy();
	}

	private void updatePageNumbers() {
		if (getScaleMode() == ScaleMode.PAGE_LIMIT) {
			limitPercentToPageLimit();
		}

		final org.eclipse.draw2d.geometry.Rectangle rectangle = getPrintArea();

		final double scale = getScale();
		numberOfPages = (int) pageCount(rectangle.preciseWidth(), rectangle.preciseHeight(), scale, margin.getWidth(),
				margin.getHeight());
		numberOfPagesLabel.setText(String.valueOf(numberOfPages));
		// the label was sized for the first text, without a new layout longer counts are cut off
		numberOfPagesLabel.requestLayout();
		if (currentPage > numberOfPages) {
			setCurrentPage(numberOfPages);
		}
	}

	private double getScale() {
		final org.eclipse.draw2d.geometry.Rectangle printArea = getPrintArea();
		double scale = getDeviceScale();
		// an empty extent (e.g. an empty network) has nothing to fit, dividing by it would
		// result in an infinite scale and no pages
		final double fitWidth = printArea.width > 0 ? margin.getWidth() / (scale * printArea.width)
				: Double.POSITIVE_INFINITY;
		final double fitHeight = printArea.height > 0 ? margin.getHeight() / (scale * printArea.height)
				: Double.POSITIVE_INFINITY;

		switch (getScaleMode()) {
		case FIT_PAGE:
			scale = applyFit(scale, Math.min(fitWidth, fitHeight));
			break;
		case FIT_WIDTH:
			scale = applyFit(scale, fitWidth);
			break;
		case FIT_HEIGHT:
			scale = applyFit(scale, fitHeight);
			break;
		case PAGE_LIMIT: {
			// scale is still the one at 100 %, i.e. the true/unscaled print size
			final double maxFitScale = computePageLimitScale(pageLimit, printArea.width, printArea.height,
					margin.getWidth(), margin.getHeight());
			scale = Math.min(scale * (appliedPercent / 100.0), maxFitScale);
			break;
		}
		case TILE: // keep the default printer scale factor, scaled by the set percentage
			scale *= appliedPercent / 100.0;
			break;
		}

		return scale;
	}

	private static double applyFit(final double scale, final double fitFactor) {
		return Double.isFinite(fitFactor) ? scale * fitFactor : scale;
	}

	/**
	 * The factor that converts display pixels into the device units of the printer,
	 * i.e. the scale that prints the figure in its true physical size.
	 */
	private double getDeviceScale() {
		final Point displayDpi = Display.getCurrent().getDPI();
		final Point printerDpi = (printer != null && !printer.isDisposed()) ? printer.getDPI() : displayDpi;
		return (double) printerDpi.x / displayDpi.x;
	}

	/** Applies the percentage entered by the user, an invalid entry keeps the current one. */
	private void applyPercent() {
		try {
			final double entered = Double.parseDouble(percentText.getText().replace(',', '.'));
			setAppliedPercent(Math.clamp(entered, MIN_PERCENT, MAX_PERCENT));
		} catch (final NumberFormatException e) {
			// not a number (e.g. empty field), show the percentage that is still in use
			setAppliedPercent(appliedPercent);
		}
		updatePageNumbers();
		canvas.redraw();
	}

	private void setAppliedPercent(final double percent) {
		appliedPercent = percent;
		if (percentText != null && !percentText.isDisposed()) {
			percentText.setText(formatPercent(percent));
		}
	}

	/**
	 * Whole percent if it is one above 10, otherwise one decimal place, which is
	 * the precision the field accepts. Shown and applied value stay the same.
	 */
	private static String formatPercent(final double percent) {
		if (percent >= 10 && Math.abs(percent - Math.rint(percent)) < 1e-9) {
			return String.valueOf(Math.round(percent));
		}
		final String text = String.format(Locale.ROOT, "%.1f", percent); //$NON-NLS-1$
		// the field accepts a comma and a dot, show the separator of the user's locale
		final char separator = DecimalFormatSymbols.getInstance(Locale.getDefault(Locale.Category.FORMAT))
				.getDecimalSeparator();
		return separator == ',' ? text.replace('.', ',') : text;
	}

	/**
	 * The page limit can reduce the requested percentage (e.g. going back to 100 %
	 * would need more pages than allowed). The percentage that is effectively used
	 * is kept and shown. Only called when the user changes something, never while
	 * painting, so the field is not rewritten while it is edited.
	 */
	private void limitPercentToPageLimit() {
		// Rounded down to the shown precision, so the shown value is the applied one
		// and still within the page limit. The epsilon keeps floating-point noise from
		// rounding an unchanged percentage down.
		final double effectivePercent = Math.max(MIN_PERCENT,
				Math.floor(getScale() / getDeviceScale() * 1000.0 + PAGE_COUNT_EPSILON) / 10.0);
		if (effectivePercent < appliedPercent - 1e-6) {
			setAppliedPercent(effectivePercent);
		}
	}

	private double computePageLimitScale(final int pageLimit, final double figW, final double figH,
			final double marginW, final double marginH) {
		final int limit = Math.max(1, pageLimit);
		// The largest scale the page limit allows. It is not capped at 100 %, so a
		// percentage above 100 can be used as long as the limit is kept. The
		// automatic reduction only lowers the requested percentage, it never
		// enlarges it. The maximum percentage keeps the range finite, e.g. for an
		// empty figure.
		final double areaFit = Math.sqrt(limit * marginW * marginH / (figW * figH));
		final double upper = Math.min(areaFit, MAX_PERCENT / 100.0 * getDeviceScale());
		if (pageCount(figW, figH, upper, marginW, marginH) <= limit) {
			return upper;
		}
		// The page count only grows with the scale, so the largest scale that still
		// honors the limit can be bisected. In contrast to a fixed step with a lower
		// bound this also works for very large figures. The search starts at 0 as
		// there every figure needs a single page, so the limit is always honored.
		double low = 0;
		double high = upper;
		for (int i = 0; i < PAGE_LIMIT_SEARCH_STEPS; i++) {
			final double mid = (low + high) / 2;
			if (pageCount(figW, figH, mid, marginW, marginH) <= limit) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return low;
	}

	private static double pageCount(final double figW, final double figH, final double scale, final double marginW,
			final double marginH) {
		return pagesAlong(figW, scale, marginW) * pagesAlong(figH, scale, marginH);
	}

	/**
	 * The number of pages needed for an extent along one axis. Used for the page
	 * count as well as for the tiling, otherwise both can disagree on the number of
	 * columns when the extent fits a page exactly.
	 * <p>
	 * The epsilon keeps floating-point noise from turning an exact fit into one
	 * more page. At least one page is counted: without it the epsilon turns a very
	 * small extent into 0 pages, which would print nothing at all.
	 */
	private static double pagesAlong(final double extent, final double scale, final double pageSize) {
		return Math.max(1, Math.ceil(extent * scale / pageSize - PAGE_COUNT_EPSILON));
	}

	/** Layout data that makes the text field wide enough for the given number of characters. */
	private static GridData widthForDigits(final Text text, final int digits) {
		final GC gc = new GC(text);
		try {
			final GridData data = new GridData();
			data.widthHint = text.computeTrim(0, 0, gc.textExtent("0".repeat(digits)).x, 0).width; //$NON-NLS-1$
			return data;
		} finally {
			gc.dispose();
		}
	}

	private void createButtonArea(final Composite parent) {
		final Composite buttonArea = new Composite(parent, SWT.NONE);
		final GridLayout buttonAreaLayout = new GridLayout(7, false);
		buttonAreaLayout.marginHeight = 0;
		buttonArea.setLayout(buttonAreaLayout);
		final GridData buttonLayoutData = new GridData();
		buttonLayoutData.horizontalAlignment = SWT.FILL; /* grow to fill available width */
		buttonArea.setLayoutData(buttonLayoutData);

		final Button buttonPrint = new Button(buttonArea, SWT.PUSH);
		buttonPrint.setText(Messages.PrintPreview_LABEL_Print);
		buttonPrint.addListener(SWT.Selection, _ -> performPrinting());

		new Label(buttonArea, SWT.SEPARATOR | SWT.VERTICAL).setLayoutData(new GridData(GridData.FILL_VERTICAL));
		createPageNavigation(new Composite(buttonArea, SWT.NONE));

		new Label(buttonArea, SWT.SEPARATOR | SWT.VERTICAL).setLayoutData(new GridData(GridData.FILL_VERTICAL));
		createOptionsGUI(new Composite(buttonArea, SWT.NONE));

		new Label(buttonArea, SWT.SEPARATOR | SWT.VERTICAL).setLayoutData(new GridData(GridData.FILL_BOTH));

		final Composite closeArea = new Composite(buttonArea, SWT.NONE);
		final GridLayout closeAreaLayout = new GridLayout(2, false);
		closeAreaLayout.marginHeight = 0;
		closeAreaLayout.marginWidth = 0;
		closeArea.setLayout(closeAreaLayout);

		closeArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(closeArea, SWT.NONE).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Button closeButton = new Button(closeArea, SWT.PUSH);
		closeButton.setText(Messages.PrintPreview_LABEL_Close);
		closeButton.addListener(SWT.Selection, _ -> close());
		closeButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
	}

	private void createPageNavigation(final Composite parent) {
		final GridLayout layout = new GridLayout(6, false);
		layout.marginHeight = 0;
		parent.setLayout(layout);
		final Label pageLabel = new Label(parent, SWT.NONE);
		pageLabel.setText(Messages.PrintPreview_LABEL_Page);

		final Button left = new Button(parent, SWT.ARROW | SWT.LEFT);
		left.addListener(SWT.Selection, _ -> {
			if (currentPage > 1) {
				setCurrentPage(currentPage - 1);
			}
		});

		currentPageText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		currentPageText.setLayoutData(widthForDigits(currentPageText, MAX_PAGE_DIGITS));
		currentPageText.setText(String.valueOf(currentPage));
		currentPageText.addListener(SWT.Modify, _ -> {
			try {
				final int newCurrentPage = Integer.parseInt(currentPageText.getText());
				if (0 < newCurrentPage && newCurrentPage <= numberOfPages) {
					setCurrentPage(newCurrentPage);
				}
			} catch (final NumberFormatException e) {
				// an empty field is a page that is being retyped, keep the current page
			}
		});
		currentPageText.addListener(SWT.Verify, verifier(ONLY_DIGIT_PATTERN));

		final Label of = new Label(parent, SWT.NONE);
		of.setText(Messages.PrintPreview_LABEL_Of);

		numberOfPagesLabel = new Label(parent, SWT.NONE);
		numberOfPagesLabel.setText(String.valueOf(numberOfPages));

		final Button right = new Button(parent, SWT.ARROW | SWT.RIGHT);
		right.addListener(SWT.Selection, _ -> {
			if (currentPage < numberOfPages) {
				setCurrentPage(currentPage + 1);
			}
		});

	}

	/**
	 * Creates a verify listener that only lets a change through if the resulting
	 * text matches the pattern. Every change is checked the same way, whether it is
	 * typed, deleted, pasted or set by the program, so no text that does not match
	 * can get into the field.
	 */
	private static Listener verifier(final Pattern pattern) {
		return ev -> {
			if (!ev.doit) {
				// other verify listeners which are first can already set it
				return;
			}
			final String currentValue = ((Text) ev.widget).getText();
			final String resultingValue = currentValue.substring(0, ev.start) + ev.text + currentValue.substring(ev.end);
			ev.doit = pattern.matcher(resultingValue).matches();
		};
	}

	private void setCurrentPage(final int newCurrentPage) {
		if (!blockCurrentPageUpdate) {
			blockCurrentPageUpdate = true;
			currentPage = newCurrentPage;
			currentPageText.setText(String.valueOf(currentPage));
			canvas.redraw();
			blockCurrentPageUpdate = false;
		}
	}

	private void performPrinting() {
		PrinterData printerData = null;
		try {
			final PrintDialog dialog = new PrintDialog(getShell());
			// Continue with the printer and settings that were selected before, e.g. when
			// printing is repeated. This has to be first, as the scope and the pages are
			// set on the data of the dialog.
			if (printer != null && !printer.isDisposed()) {
				dialog.setPrinterData(printer.getPrinterData());
			}
			// offer the pages of the preview as the range the user can choose
			dialog.setScope(PrinterData.ALL_PAGES);
			dialog.setStartPage(1);
			dialog.setEndPage(numberOfPages);
			// Prompts the printer dialog to let the user select a printer.
			printerData = dialog.open();
		} catch (final SWTError e) {
			rethrowUnlessMissingPrinterBackend(e);
			reportPrintError(Messages.PrintPreview_ERROR_StartingPrintJob, e);
			return;
		}

		if (printerData == null) {
			return;
		}
		// Apply the landscape/portrait orientation chosen in the preview
		printerData.orientation = isLandscape ? PrinterData.LANDSCAPE : PrinterData.PORTRAIT;
		// Loads the printer.
		Printer newPrinter = null;
		try {
			newPrinter = new Printer(printerData);
		} catch (final SWTError e) {
			rethrowUnlessMissingPrinterBackend(e);
			reportPrintError(Messages.PrintPreview_ERROR_StartingPrintJob, e);
			return;
		}
		final int offeredPages = numberOfPages;
		setPrinter(newPrinter, getMarginInch());
		if (printerData.scope == PrinterData.PAGE_RANGE && numberOfPages != offeredPages) {
			// The range could only be chosen from the pages of the previous printer. The
			// preview continues with the chosen printer, the next print offers its pages.
			MessageDialog.openInformation(getShell(), Messages.PrintPreview_INFO_PageCountChanged_Title,
					NLS.bind(Messages.PrintPreview_INFO_PageCountChanged, Integer.valueOf(offeredPages),
							Integer.valueOf(numberOfPages)));
			return;
		}
		final PageRange pages = getPagesToPrint(printerData);
		if (pages.count() > CONFIRM_PAGE_COUNT && !MessageDialog.openQuestion(getShell(),
				Messages.PrintPreview_QUESTION_ManyPages_Title,
				NLS.bind(Messages.PrintPreview_QUESTION_ManyPages, Integer.valueOf(pages.count())))) {
			// the preview continues with the chosen printer, so the settings can be adjusted
			return;
		}
		// print the document, the number of copies is handled by the printer
		if (!print(newPrinter, pages)) {
			// the preview stays open, so the settings can be adjusted and printed again
			return;
		}
		if (printer != null && !printer.isDisposed()) {
			printer.dispose();
			printer = null;
		}
		close();
	}

	/**
	 * The pages the user chose in the print dialog. The printer does not apply the
	 * page range, it has to be done when printing. All pages are printed if no
	 * range was chosen. A range beyond the pages that exist is limited to them.
	 */
	private PageRange getPagesToPrint(final PrinterData printerData) {
		if (printerData.scope != PrinterData.PAGE_RANGE) {
			return new PageRange(1, numberOfPages);
		}
		final int first = Math.clamp(printerData.startPage, 1, numberOfPages);
		final int last = Math.clamp(printerData.endPage, first, numberOfPages);
		return new PageRange(first, last);
	}

	/**
	 * Prints the pages of the figure currently displayed to the specified printer.
	 *
	 * @param printer the printer
	 * @param pages   the pages to print
	 * @return true if the print job was completed, a failure is reported to the user
	 */
	private boolean print(final Printer printer, final PageRange pages) {

		if (!printer.startJob(printName)) {
			reportPrintError(Messages.PrintPreview_ERROR_StartingPrintJob, null);
			return false;
		}

		final GC gc = new GC(printer);
		try {
			final SWTGraphics g = new SWTGraphics(gc);
			final PrinterGraphics graphics = new PrinterGraphics(g, printer);

			graphics.setForegroundColor(figure.getForegroundColor());
			graphics.setBackgroundColor(figure.getBackgroundColor());
			graphics.setFont(figure.getFont());

			final double scale = getScale();

			graphics.scale(scale);
			graphics.translate((int) (margin.getLeft() / scale), (int) (margin.getTop() / scale));

			for (int i = pages.first(); i <= pages.last(); i++) {
				if (!printer.startPage()) {
					printer.cancelJob();
					reportPrintError(Messages.PrintPreview_ERROR_StartingNewPage, null);
					return false;
				}
				graphics.pushState();

				drawOnePage(scale, graphics, i);

				printer.endPage();
				graphics.popState();
			}

			printer.endJob();
			return true;
		} catch (final RuntimeException e) {
			// do not leave a half printed job open, the original error must not get lost
			try {
				printer.cancelJob();
			} catch (final RuntimeException cancelError) {
				e.addSuppressed(cancelError);
			}
			reportPrintError(Messages.PrintPreview_ERROR_Printing, e);
			return false;
		} finally {
			gc.dispose();
		}
	}

	/** Logs a failed print and tells the user, so it does not look like the print worked. */
	private void reportPrintError(final String message, final Throwable cause) {
		ILog.get().error(message, cause);
		MessageDialog.openError(getShell(), Messages.PrintPreviewAction_LABEL_PrintPreview, message);
	}

	/**
	 * SWT reports a missing printing backend (e.g. no printer installed) as an
	 * {@link SWTError} with {@link SWT#ERROR_NO_HANDLES}. That is the only error
	 * that is recoverable here, every other error is passed on.
	 */
	private static void rethrowUnlessMissingPrinterBackend(final SWTError error) {
		if (error.code != SWT.ERROR_NO_HANDLES) {
			throw error;
		}
	}

	/**
	 * Sets target printer.
	 *
	 * @param newPrinter the printer
	 * @param marginSize the margin size
	 */
	void setPrinter(Printer newPrinter, final double marginSize) {
		if (newPrinter == null) {
			try {
				final PrinterData defaultPrinterData = Printer.getDefaultPrinterData();
				if (defaultPrinterData != null) {
					newPrinter = new Printer(defaultPrinterData);
				}
			} catch (final SWTError e) {
				// without a printer the preview is still usable, so this is only logged
				rethrowUnlessMissingPrinterBackend(e);
				ILog.get().error(Messages.PrintPreview_ERROR_DefaultPrinter, e);
			}
		}
		if (null != printer && printer != newPrinter && !printer.isDisposed()) {
			printer.dispose();
		}

		printer = newPrinter;
		margin = PrintMargin.getPrintMargin(newPrinter, marginSize, isLandscape, paperFormat.widthInch(),
				paperFormat.heightInch());
		updatePageNumbers();
		if (canvas != null && !canvas.isDisposed()) {
			canvas.redraw();
		}
	}

	private void drawOnePage(final double scale, final Graphics g, final int pageNumber) {
		final org.eclipse.draw2d.geometry.Rectangle clipRect = getClipRectForPage(pageNumber, scale);

		g.translate(-clipRect.x, -clipRect.y);
		g.setLineStyle(Graphics.LINE_DASH);
		g.setForegroundColor(ColorConstants.black);
		if (printBorder.getSelection()) {
			g.drawRectangle(clipRect);
		}

		g.setLineStyle(Graphics.LINE_SOLID);
		g.clipRect(clipRect);
		figure.paint(g);
	}

}

/**
 * Contains margin information (in pixels) for a print job.
 *
 */
class PrintMargin {
	// Margin to the left side, in pixels
	private final int left;

	// Margins to the right side, in pixels
	private final int right;

	// Margins to the top side, in pixels
	private final int top;

	// Margins to the bottom side, in pixels
	private final int bottom;

	// Width and height of the page the margins are measured on, in pixels
	private final int pageWidth;
	private final int pageHeight;

	private PrintMargin(final int left, final int right, final int top, final int bottom, final int pageWidth,
			final int pageHeight) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.pageWidth = pageWidth;
		this.pageHeight = pageHeight;
	}

	int getPageWidth() {
		return pageWidth;
	}

	int getPageHeight() {
		return pageHeight;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public int getTop() {
		return top;
	}

	public int getBottom() {
		return bottom;
	}

	int getWidth() {
		return right - left;
	}

	int getHeight() {
		return bottom - top;
	}

	/**
	 * Returns the margins for the specified printer and paper format with the same
	 * margin on all four sides.
	 *
	 * @see #getPrintMargin(Printer, double, double, double, double, boolean, double,
	 *      double)
	 *
	 * @param printer         the printer whose DPI and printable area to use, or
	 *                        {@code null} for the display default
	 * @param margin          the margin width on every side, in inches
	 * @param landscape       whether the page is in landscape orientation
	 * @param paperWidthInch  the width of the selected paper format in portrait, in
	 *                        inches
	 * @param paperHeightInch the height of the selected paper format in portrait, in
	 *                        inches
	 * @return the margins, in pixels of the printer (or the display)
	 */
	static PrintMargin getPrintMargin(final Printer printer, final double margin, final boolean landscape,
			final double paperWidthInch, final double paperHeightInch) {
		return getPrintMargin(printer, margin, margin, margin, margin, landscape, paperWidthInch, paperHeightInch);
	}

	/**
	 * Returns the margins inside the page that is printed.
	 * <p>
	 * With a printer the page is the printer's own printable area
	 * ({@link Printer#getClientArea()}), limited to the selected paper format, and
	 * aligned with the requested orientation. Without a printer the full selected
	 * paper format at display resolution is used. The margins are then inset from
	 * that page, so they can never exceed what is really printable.
	 *
	 * @param printer         the printer whose DPI and printable area to use, or
	 *                        {@code null} for the display default
	 * @param marginLeft      the left margin, in inches
	 * @param marginRight     the right margin, in inches
	 * @param marginTop       the top margin, in inches
	 * @param marginBottom    the bottom margin, in inches
	 * @param landscape       whether the page is in landscape orientation
	 * @param paperWidthInch  the width of the selected paper format in portrait, in
	 *                        inches
	 * @param paperHeightInch the height of the selected paper format in portrait, in
	 *                        inches
	 * @return the margins and the page they are measured on, in pixels of the
	 *         printer (or the display)
	 */
	static PrintMargin getPrintMargin(final Printer printer, final double marginLeft, final double marginRight,
			final double marginTop, final double marginBottom, final boolean landscape, final double paperWidthInch,
			final double paperHeightInch) {
		final Point dpi = (printer != null && !printer.isDisposed()) ? printer.getDPI() : Display.getCurrent().getDPI();
		final Rectangle printable;
		if (printer != null && !printer.isDisposed()) {
			printable = limitToFormat(printer.getClientArea(), dpi, landscape, paperWidthInch, paperHeightInch);
		} else {
			final Rectangle full = new Rectangle(0, 0, (int) (paperWidthInch * dpi.x), (int) (paperHeightInch * dpi.y));
			printable = landscape ? new Rectangle(full.y, full.x, full.height, full.width) : full;
		}

		final int leftMargin = printable.x + (int) (marginLeft * dpi.x);
		final int rightMargin = printable.x + printable.width - (int) (marginRight * dpi.x);
		final int topMargin = printable.y + (int) (marginTop * dpi.y);
		final int bottomMargin = printable.y + printable.height - (int) (marginBottom * dpi.y);

		return new PrintMargin(leftMargin, rightMargin, topMargin, bottomMargin, printable.x + printable.width,
				printable.y + printable.height);
	}

	/**
	 * Limits the printer's real printable area to the selected paper format, so
	 * the format controls the pagination and scaling while the result can never
	 * exceed what the printer is able to print. The area of a real printer
	 * already follows the orientation that was set on it, it is only aligned with
	 * the requested orientation here for a printer that was not opened with it
	 * yet (the preview). The orientation is judged by the physical size, as the
	 * horizontal and vertical resolution can differ, and a rotated area keeps its
	 * physical size in the pixels of the other axis.
	 */
	private static Rectangle limitToFormat(final Rectangle clientArea, final Point dpi, final boolean landscape,
			final double paperWidthInch, final double paperHeightInch) {
		int width = clientArea.width;
		int height = clientArea.height;
		final double physicalWidth = clientArea.width / (double) dpi.x;
		final double physicalHeight = clientArea.height / (double) dpi.y;
		if (landscape == (physicalWidth < physicalHeight)) {
			width = (int) Math.round(physicalHeight * dpi.x);
			height = (int) Math.round(physicalWidth * dpi.y);
		}
		final int formatWidth = (int) ((landscape ? paperHeightInch : paperWidthInch) * dpi.x);
		final int formatHeight = (int) ((landscape ? paperWidthInch : paperHeightInch) * dpi.y);
		return new Rectangle(clientArea.x, clientArea.y, Math.min(width, formatWidth), Math.min(height, formatHeight));
	}

	@Override
	public String toString() {
		return "Margin { " + left + ", " + right + "; " + top + ", " + bottom //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ " }"; //$NON-NLS-1$
	}
}
