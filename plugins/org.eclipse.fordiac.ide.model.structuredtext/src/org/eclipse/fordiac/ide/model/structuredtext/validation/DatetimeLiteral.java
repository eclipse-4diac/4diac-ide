package org.eclipse.fordiac.ide.model.structuredtext.validation;

import java.text.MessageFormat;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class DatetimeLiteral {

	public enum Type {
		INVALID, T, LT, D, LD, TOD, LTOD, DT, LDT
	}

	private String data;

	private Type type = Type.INVALID;

	private boolean isNegative = false;

	private Long year = null;
	private Long month = null;
	private Long day = null;
	private Long hour = null;
	private Long minute = null;
	private Long second = null;
	private Long millisecond = null;
	private Long microsecond = null;
	private Long nanosecond = null;

	static final long KILO = 1000;
	static final long NS_PER_US = KILO;
	static final long US_PER_MS = KILO;
	static final long MS_PER_S = KILO;
	static final long S_PER_M = 60; // This may be wrong when using UTC, the last minute of the day can have 59, 60
	// or 61 seconds
	static final long M_PER_H = 60;
	static final long H_PER_D = 24;

	static final long MAX_KILO = 999;
	static final long MAX_S = S_PER_M - 1;
	static final long MAX_M = M_PER_H - 1;
	static final long MAX_H = H_PER_D - 1;

	static final String UNDERSCORE = "_"; //$NON-NLS-1$
	static final String EMPTY = ""; //$NON-NLS-1$

	static final long BASE_TEN = 10;
	static final long MAX_COUNT_DIGITS_NS = 9; // This limit is due to the defined resolution: nanoseconds //
												// (100000000 nanoseconds are 1 second); allow maximum 9 digits
												// otherwise digits would be dropped and precision would not match
	static final long MAX_COUNT_DIGITS = 19; // This limit is due to the defined maximum bitsize of time&date types: 64
												// bits limit you to 19 digits in base ten

	static final String LITERAL_SEPERATOR = "#"; //$NON-NLS-1$

	static final String MINUS = "-"; //$NON-NLS-1$
	static final String UNIT_D = "d"; //$NON-NLS-1$
	static final String UNIT_H = "h"; //$NON-NLS-1$
	static final String UNIT_M = "m"; //$NON-NLS-1$
	static final String UNIT_S = "s"; //$NON-NLS-1$
	static final String UNIT_MS = "ms"; //$NON-NLS-1$
	static final String UNIT_US = "us"; //$NON-NLS-1$
	static final String UNIT_NS = "ns"; //$NON-NLS-1$

	static final String DATE_SEPERATOR = "-"; //$NON-NLS-1$

	static final String TIMEOFDAY_SEPERATOR = ":"; //$NON-NLS-1$
	static final String TIMEOFDAY_SEPERATOR_DECIMALS = "."; //$NON-NLS-1$

	static final String TWO_DIGIT_NUMBER = "%02d"; //$NON-NLS-1$

	// prepare the "cheap" regex'es for splitting the string apart
	static final String DIGIT_SEPERATOR = "(_)?"; //$NON-NLS-1$
	static final String UNSIGNED_INT_REGEX = "[0-9](" + DIGIT_SEPERATOR + "[0-9])*"; //$NON-NLS-1$
	static final String DECIMAL_REGEX = "\\." + UNSIGNED_INT_REGEX; //$NON-NLS-1$
	static final String FIXPOINT_REGEX = UNSIGNED_INT_REGEX + "(" + DECIMAL_REGEX + ")?"; //$NON-NLS-1$ //$NON-NLS-2$
	static final String UNIT_REGEX = "(ns|us|ms|s|m|h|d)"; //$NON-NLS-1$
	static final String FIXPOINT_WITH_UNIT_REGEX = FIXPOINT_REGEX + UNIT_REGEX;

	// Validate that all symbols are in the right place with the full regex
	// translated from EBNF (expensive!)
	static final String NANOSECONDS_REGEX = "(" + FIXPOINT_REGEX + UNIT_NS + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	static final String MICROSECONDS_REGEX = "((" + FIXPOINT_REGEX + UNIT_US + ")|(" + UNSIGNED_INT_REGEX + UNIT_US //$NON-NLS-1$ //$NON-NLS-2$
			+ DIGIT_SEPERATOR + ")?" + NANOSECONDS_REGEX + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	static final String MILLISECONDS_REGEX = "((" + FIXPOINT_REGEX + UNIT_MS + ")|(" + UNSIGNED_INT_REGEX + UNIT_MS //$NON-NLS-1$ //$NON-NLS-2$
			+ DIGIT_SEPERATOR + ")?" + MICROSECONDS_REGEX + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	static final String SECONDS_REGEX = "((" + FIXPOINT_REGEX + UNIT_S + ")|(" + UNSIGNED_INT_REGEX + UNIT_S //$NON-NLS-1$ //$NON-NLS-2$
			+ DIGIT_SEPERATOR + ")?" + MILLISECONDS_REGEX + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	static final String MINUTES_REGEX = "((" + FIXPOINT_REGEX + UNIT_M + ")|(" + UNSIGNED_INT_REGEX + UNIT_M //$NON-NLS-1$ //$NON-NLS-2$
			+ DIGIT_SEPERATOR + ")?" + SECONDS_REGEX + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	static final String HOURS_REGEX = "((" + FIXPOINT_REGEX + UNIT_H + ")|(" + UNSIGNED_INT_REGEX + UNIT_H //$NON-NLS-1$ //$NON-NLS-2$
			+ DIGIT_SEPERATOR + ")?" + MINUTES_REGEX + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	static final String DAYS_REGEX = "((" + FIXPOINT_REGEX + UNIT_D + ")|(" + UNSIGNED_INT_REGEX + UNIT_D //$NON-NLS-1$ //$NON-NLS-2$
			+ DIGIT_SEPERATOR + ")?" + HOURS_REGEX + ")"; //$NON-NLS-1$ //$NON-NLS-2$

	static final String INTERVAL_REGEX = "((" + DAYS_REGEX + ")|(" + HOURS_REGEX + ")|(" + MINUTES_REGEX + ")|(" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			+ SECONDS_REGEX + ")|(" + MILLISECONDS_REGEX + ")|(" + MICROSECONDS_REGEX + ")|(" + NANOSECONDS_REGEX //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			+ "))"; //$NON-NLS-1$

	static final String DURATION_VALUE_REGEX = "(\\+|-)?" + INTERVAL_REGEX; //$NON-NLS-1$

	static final String DATE_VALUE_REGEX = UNSIGNED_INT_REGEX + DATE_SEPERATOR + UNSIGNED_INT_REGEX + DATE_SEPERATOR
			+ UNSIGNED_INT_REGEX;

	static final String TIMEOFDAY_VALUE_REGEX = UNSIGNED_INT_REGEX + TIMEOFDAY_SEPERATOR + UNSIGNED_INT_REGEX
			+ TIMEOFDAY_SEPERATOR + FIXPOINT_REGEX;

	static final String DATETIME_VALUE_REGEX = DATE_VALUE_REGEX + DATE_SEPERATOR + TIMEOFDAY_VALUE_REGEX;

	static final String BEGINNING_OF_STRING = "^"; //$NON-NLS-1$
	static final String END_OF_STRING = "$"; //$NON-NLS-1$

	static final Pattern durationValuePattern = Pattern
			.compile(BEGINNING_OF_STRING + DURATION_VALUE_REGEX + END_OF_STRING);
	static final Pattern dateValuePattern = Pattern.compile(BEGINNING_OF_STRING + DATE_VALUE_REGEX + END_OF_STRING);
	static final Pattern timeofdayValuePattern = Pattern
			.compile(BEGINNING_OF_STRING + TIMEOFDAY_VALUE_REGEX + END_OF_STRING);
	static final Pattern datetimeValuePattern = Pattern
			.compile(BEGINNING_OF_STRING + DATETIME_VALUE_REGEX + END_OF_STRING);

	static final Pattern fixpointWithUnitPattern = Pattern.compile(FIXPOINT_WITH_UNIT_REGEX);
	static final Pattern unsignedIntPattern = Pattern.compile(UNSIGNED_INT_REGEX);
	static final Pattern decimalPattern = Pattern.compile(DECIMAL_REGEX);
	static final Pattern unitPattern = Pattern.compile(UNIT_REGEX);

	public DatetimeLiteral(String string) {
		final String literal = string;

		determineType(literal);

		final String[] dataSplit = literal.toLowerCase().split(LITERAL_SEPERATOR);
		if (dataSplit.length != 2) {
			type = Type.INVALID;
			return;
		}

		this.data = dataSplit[1];

		switch (type) {
		case TOD:
		case LTOD:
			parseTOD();
			break;
		case DT:
		case LDT:
			parseDT();
			break;
		case T:
		case LT:
			parseT();
			break;
		case D:
		case LD:
			parseD();
			break;
		default:
			break;
		}

	}

	private void determineType(final String literal) {
		final String uppercase = literal.toUpperCase();

		if (uppercase.startsWith("TOD" + LITERAL_SEPERATOR) //$NON-NLS-1$
				|| uppercase.startsWith("TIME_OF_DAY" + LITERAL_SEPERATOR)) { //$NON-NLS-1$
			type = Type.TOD;
		} else if (uppercase.startsWith("LTOD" + LITERAL_SEPERATOR) //$NON-NLS-1$
				|| uppercase.startsWith("LTIME_OF_DAY" + LITERAL_SEPERATOR)) { //$NON-NLS-1$
			type = Type.LTOD;
		} else if (uppercase.startsWith("DT" + LITERAL_SEPERATOR) //$NON-NLS-1$
				|| uppercase.startsWith("DATE_AND_TIME" + LITERAL_SEPERATOR)) { //$NON-NLS-1$
			type = Type.DT;
		} else if (uppercase.startsWith("LDT" + LITERAL_SEPERATOR) //$NON-NLS-1$
				|| uppercase.startsWith("LDATE_AND_TIME" + LITERAL_SEPERATOR)) { //$NON-NLS-1$
			type = Type.LDT;
		} else if (uppercase.startsWith("T" + LITERAL_SEPERATOR) // //$NON-NLS-1$
				|| uppercase.startsWith("TIME" + LITERAL_SEPERATOR)) { //$NON-NLS-1$
			type = Type.T;
		} else if (uppercase.startsWith("LT" + LITERAL_SEPERATOR) //$NON-NLS-1$
				|| uppercase.startsWith("LTIME" + LITERAL_SEPERATOR)) { //$NON-NLS-1$
			type = Type.LT;
		} else if (uppercase.startsWith("D" + LITERAL_SEPERATOR) // //$NON-NLS-1$
				|| uppercase.startsWith("DATE" + LITERAL_SEPERATOR)) { //$NON-NLS-1$
			type = Type.D;
		} else if (uppercase.startsWith("LD" + LITERAL_SEPERATOR) //$NON-NLS-1$
				|| uppercase.startsWith("LDATE" + LITERAL_SEPERATOR)) { //$NON-NLS-1$
			type = Type.LD;
		}
	}

	public boolean isValid() {
		return type != Type.INVALID;
	}

	private void parseTOD() {
		final int INDEX_HOUR = 0;
		final int INDEX_MINUTE = 1;
		final int INDEX_SECOND = 2;
		final int INDEX_NANOSECOND = 3;

		final int LENGTH_WITHOUT_NANOSECOND = INDEX_SECOND + 1;
		final int LENGTH_WITH_NANOSECOND = INDEX_NANOSECOND + 1;

		if (!timeofdayValuePattern.matcher(data).matches()) {
			type = Type.INVALID;
			return;
		}

		String[] matches = unsignedIntPattern.matcher(data).results().map(MatchResult::group).toArray(String[]::new);

		if ((matches.length != LENGTH_WITHOUT_NANOSECOND) && (matches.length != LENGTH_WITH_NANOSECOND)) {
			type = Type.INVALID;
			return;
		}

		hour = Long.parseLong(matches[INDEX_HOUR].replace(UNDERSCORE, EMPTY));
		minute = Long.parseLong(matches[INDEX_MINUTE].replace(UNDERSCORE, EMPTY));
		second = Long.parseLong(matches[INDEX_SECOND].replace(UNDERSCORE, EMPTY));

		if (matches.length == LENGTH_WITH_NANOSECOND) {
			String decimals = matches[INDEX_NANOSECOND].replace(UNDERSCORE, EMPTY);
			int places = decimals.length();

			if (places > MAX_COUNT_DIGITS_NS) {
				type = Type.INVALID;
				return;
			}

			int divider = (int) Math.pow(BASE_TEN, places);

			nanosecond = (Long.parseLong(decimals) * MS_PER_S * US_PER_MS * NS_PER_US) / divider;
		}

		validateTOD();

	}

	private void validateTOD() {
		if ((hour < 0) || (hour > MAX_H)) {
			type = Type.INVALID;
			return;
		}

		if ((minute < 0) || (minute > MAX_M)) {
			type = Type.INVALID;
			return;
		}

		if ((second < 0) || (second > MAX_S)) {
			type = Type.INVALID;
		}
	}

	private void parseDT() {
		final int INDEX_YEAR = 0;
		final int INDEX_MONTH = 1;
		final int INDEX_DAY = 2;
		final int INDEX_HOUR = 3;
		final int INDEX_MINUTE = 4;
		final int INDEX_SECOND = 5;
		final int INDEX_NANOSECOND = 6;

		final int LENGTH_WITHOUT_NANOSECONDS = INDEX_SECOND + 1;
		final int LENGTH_WITH_NANOSECONDS = INDEX_NANOSECOND + 1;

		if (!datetimeValuePattern.matcher(data).matches()) {
			type = Type.INVALID;
			return;
		}

		String[] matches = unsignedIntPattern.matcher(data).results().map(MatchResult::group).toArray(String[]::new);

		if ((matches.length != LENGTH_WITHOUT_NANOSECONDS) && (matches.length != LENGTH_WITH_NANOSECONDS)) {
			type = Type.INVALID;
			return;
		}

		year = Long.parseLong(matches[INDEX_YEAR].replace(UNDERSCORE, EMPTY));
		month = Long.parseLong(matches[INDEX_MONTH].replace(UNDERSCORE, EMPTY));
		day = Long.parseLong(matches[INDEX_DAY].replace(UNDERSCORE, EMPTY));
		validateD();
		if (type == Type.INVALID) {
			return;
		}

		hour = Long.parseLong(matches[INDEX_HOUR].replace(UNDERSCORE, EMPTY));
		minute = Long.parseLong(matches[INDEX_MINUTE].replace(UNDERSCORE, EMPTY));
		second = Long.parseLong(matches[INDEX_SECOND].replace(UNDERSCORE, EMPTY));

		if (matches.length == LENGTH_WITH_NANOSECONDS) {
			String decimals = matches[INDEX_NANOSECOND].replace(UNDERSCORE, EMPTY);
			int places = decimals.length();

			if (places > MAX_COUNT_DIGITS_NS) {
				type = Type.INVALID;
				return;
			}

			int divider = (int) Math.pow(BASE_TEN, places);

			nanosecond = (Long.parseLong(decimals) * MS_PER_S * US_PER_MS * NS_PER_US) / divider;
		}

		validateTOD();

	}

	private void parseT() {

		if (!durationValuePattern.matcher(data).matches()) {
			type = Type.INVALID;
			return;
		}
		// at this point we know that the values and their units are in the right order
		// and that the string is basically valid
		// we now need to find the values and check if they match the allowed ranges

		if (data.startsWith(MINUS)) {
			isNegative = true;
		}

		String[] matches = fixpointWithUnitPattern.matcher(data).results().map(MatchResult::group)
				.toArray(String[]::new);

		for (String match : matches) {
			String[] value = unsignedIntPattern.matcher(match).results().map(MatchResult::group).toArray(String[]::new);
			String[] decimal = decimalPattern.matcher(match).results().map(MatchResult::group).toArray(String[]::new);
			String[] unit = unitPattern.matcher(match).results().map(MatchResult::group).toArray(String[]::new);

			if (decimal.length > 1) {
				type = Type.INVALID;
				return;
			}

			long intValue = Long.parseLong(value[0].replace(UNDERSCORE, EMPTY));
			String decimals = decimal.length == 1 ? decimal[0].substring(1).replace(UNDERSCORE, EMPTY) : "0"; //$NON-NLS-1$
			long intDecimal = Long.parseLong(decimals);
			int places = decimals.length();

			if (places > MAX_COUNT_DIGITS) {
				type = Type.INVALID;
				return;
			}

			long divider = (int) Math.pow(BASE_TEN, places);

			switch (unit[0]) {
			case UNIT_D:
				day = intValue;
				fillFromNs((intDecimal * H_PER_D * M_PER_H * S_PER_M * MS_PER_S * US_PER_MS * NS_PER_US) / divider);
				break;
			case UNIT_H:
				if (null != hour) {
					type = Type.INVALID;
					return;
				}
				hour = intValue;
				fillFromNs((intDecimal * M_PER_H * S_PER_M * MS_PER_S * US_PER_MS * NS_PER_US) / divider);
				break;
			case UNIT_M:
				if (null != minute) {
					type = Type.INVALID;
					return;
				}
				minute = intValue;
				fillFromNs((intDecimal * S_PER_M * MS_PER_S * US_PER_MS * NS_PER_US) / divider);
				break;
			case UNIT_S:
				if (null != second) {
					type = Type.INVALID;
					return;
				}
				second = intValue;
				fillFromNs((intDecimal * MS_PER_S * US_PER_MS * NS_PER_US) / divider);
				break;
			case UNIT_MS:
				if (null != millisecond) {
					type = Type.INVALID;
					return;
				}
				millisecond = intValue;
				fillFromNs((intDecimal * US_PER_MS * NS_PER_US) / divider);
				break;
			case UNIT_US:
				if (null != microsecond) {
					type = Type.INVALID;
					return;
				}
				microsecond = intValue;
				fillFromNs((intDecimal * NS_PER_US) / divider);
				break;
			case UNIT_NS:
				nanosecond = intValue;
				if (intDecimal != 0) {
					type = Type.INVALID;
					return;
				}
				break;
			default:
				type = Type.INVALID;
				break;
			}

		}

		if ((//
		(null != nanosecond) && //
				((null != microsecond) || (null != millisecond) || (null != second) || (null != minute)
						|| (null != hour) || (null != day)) //
				&& (nanosecond > MAX_KILO)) || //
				(//
				(null != microsecond) && //
						((null != millisecond) || (null != second) || (null != minute) || (null != hour)
								|| (null != day)) //
						&& (microsecond > MAX_KILO))
				|| //
				(//
				(null != millisecond) && //
						((null != second) || (null != minute) || (null != hour) || (null != day)) //
						&& (millisecond > MAX_KILO))
				|| //
				(//
				(null != second) && //
						((null != minute) || (null != hour) || (null != day)) //
						&& (second > MAX_S))
				|| //
				(//
				(null != minute) && //
						((null != hour) || (null != day)) //
						&& (minute > MAX_M))
				|| //
				(//
				(null != hour) && //
						((null != day)) //
						&& (hour > MAX_H))//
		) {
			type = Type.INVALID;
		}

	}

	private void fillFromNs(long nanoseconds) {
		long nanosecondTemp = nanoseconds;
		long temp = nanosecondTemp / (M_PER_H * S_PER_M * MS_PER_S * US_PER_MS * NS_PER_US);
		if (temp != 0) {
			if (null != hour) {
				type = Type.INVALID;
				return;
			}
			hour = temp;
		}
		nanosecondTemp = nanosecondTemp % (M_PER_H * S_PER_M * MS_PER_S * US_PER_MS * NS_PER_US);
		temp = nanosecondTemp / (S_PER_M * MS_PER_S * US_PER_MS * NS_PER_US);
		if (temp != 0) {
			if (null != minute) {
				type = Type.INVALID;
				return;
			}
			minute = temp;
		}
		nanosecondTemp = nanosecondTemp % (S_PER_M * MS_PER_S * US_PER_MS * NS_PER_US);
		temp = nanosecondTemp / (MS_PER_S * US_PER_MS * NS_PER_US);
		if (temp != 0) {
			if (null != second) {
				type = Type.INVALID;
				return;
			}
			second = temp;
		}
		nanosecondTemp = nanosecondTemp % (MS_PER_S * US_PER_MS * NS_PER_US);
		temp = nanosecondTemp / (US_PER_MS * NS_PER_US);
		if (temp != 0) {
			if (null != millisecond) {
				type = Type.INVALID;
				return;
			}
			millisecond = temp;
		}
		nanosecondTemp = nanosecondTemp % (US_PER_MS * NS_PER_US);
		temp = nanosecondTemp / (NS_PER_US);
		if (temp != 0) {
			if (null != microsecond) {
				type = Type.INVALID;
				return;
			}
			microsecond = temp;
		}
		temp = nanosecondTemp % (NS_PER_US);
		if (temp != 0) {
			if (null != nanosecond) {
				type = Type.INVALID;
				return;
			}
			nanosecond = temp;
		}
	}

	private String toInterval() {
		StringBuilder s = new StringBuilder();
		if (isNegative) {
			s.append(MINUS);
		}
		if ((null != day) && (day != 0)) {
			s.append(day);
			s.append(UNIT_D);
		}
		if ((null != hour) && (hour != 0)) {
			s.append(hour);
			s.append(UNIT_H);
		}
		if ((null != minute) && (minute != 0)) {
			s.append(minute);
			s.append(UNIT_M);
		}
		if ((null != second) && (second != 0)) {
			s.append(second);
			s.append(UNIT_S);
		}
		if ((null != millisecond) && (millisecond != 0)) {
			s.append(millisecond);
			s.append(UNIT_MS);
		}
		if ((null != microsecond) && (microsecond != 0)) {
			s.append(microsecond);
			s.append(UNIT_US);
		}
		if ((null != nanosecond) && (nanosecond != 0)) {
			s.append(nanosecond);
			s.append(UNIT_NS);
		}
		return s.toString();
	}

	private void parseD() {
		final int INDEX_YEAR = 0;
		final int INDEX_MONTH = 1;
		final int INDEX_DAY = 2;

		final int LENGTH = INDEX_DAY + 1;

		if (!dateValuePattern.matcher(data).matches()) {
			type = Type.INVALID;
			return;
		}

		String[] matches = unsignedIntPattern.matcher(data).results().map(MatchResult::group).toArray(String[]::new);

		if (matches.length != LENGTH) {
			type = Type.INVALID;
			return;
		}

		year = Long.parseLong(matches[INDEX_YEAR].replace(UNDERSCORE, EMPTY));
		month = Long.parseLong(matches[INDEX_MONTH].replace(UNDERSCORE, EMPTY));
		day = Long.parseLong(matches[INDEX_DAY].replace(UNDERSCORE, EMPTY));

		validateD();

	}

	private void validateD() {
		// This is valid under the assumption of gregorian calender

		final long DAYS_FEB_LEAPYEAR = 29;
		final long DAYS_FEB_NON_LEAPYEAR = 28;

		final long DAYS_LONG_MONTH = 31;
		final long DAYS_SHORT_MONTH = 30;

		final long QUATERNARY = 4;
		final long CENTURY = 100;
		final long QUATERNARYCENTURY = 400;

		if (day < 1) {
			type = Type.INVALID;
			return;
		}

		switch (month.intValue()) {
		case 2:
			boolean isLeapYear = ((((year % QUATERNARY) == 0) && ((year % CENTURY) != 0))
					|| ((year % QUATERNARYCENTURY) == 0));
			if (day > (isLeapYear ? DAYS_FEB_LEAPYEAR : DAYS_FEB_NON_LEAPYEAR)) {
				type = Type.INVALID;
			}
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (day > DAYS_LONG_MONTH) {
				type = Type.INVALID;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if (day > DAYS_SHORT_MONTH) {
				type = Type.INVALID;
			}
			break;
		default:
			type = Type.INVALID;
		}
	}

	private String toDate() {
		StringBuilder s = new StringBuilder();
		s.append(year);
		s.append(DATE_SEPERATOR);
		s.append(String.format(TWO_DIGIT_NUMBER, month));
		s.append(DATE_SEPERATOR);
		s.append(String.format(TWO_DIGIT_NUMBER, day));
		return s.toString();
	}

	private String toTimeOfDay() {
		StringBuilder s = new StringBuilder();
		s.append(String.format(TWO_DIGIT_NUMBER, hour));
		s.append(TIMEOFDAY_SEPERATOR);
		s.append(String.format(TWO_DIGIT_NUMBER, minute));
		s.append(TIMEOFDAY_SEPERATOR);
		s.append(String.format(TWO_DIGIT_NUMBER, second));
		if ((null != nanosecond) && (nanosecond != 0)) {
			s.append(TIMEOFDAY_SEPERATOR_DECIMALS);
			s.append(nanosecond);
		}
		return s.toString();
	}

	@Override
	public String toString() {
		switch (type) {
		case T:
			return MessageFormat.format("CIEC_TIME(\"T#{0}\")", toInterval()); //$NON-NLS-1$
		case LT:
			return MessageFormat.format("CIEC_TIME(\"LT#{0}\")", toInterval()); //$NON-NLS-1$
		case D:
			return MessageFormat.format("CIEC_DATE(\"D#{0}\")", toDate()); //$NON-NLS-1$
		case LD:
			return MessageFormat.format("CIEC_DATE(\"LD#{0}\")", toDate()); //$NON-NLS-1$
		case TOD:
			return MessageFormat.format("CIEC_TIME_OF_DAY(\"TOD#{0}\")", toTimeOfDay()); //$NON-NLS-1$
		case LTOD:
			return MessageFormat.format("CIEC_TIME_OF_DAY(\"LTOD#{0}\")", toTimeOfDay()); //$NON-NLS-1$
		case DT:
			return MessageFormat.format("CIEC_DATE_AND_TIME(\"DT#{0}-{1}\")", toDate(), toTimeOfDay()); //$NON-NLS-1$
		case LDT:
			return MessageFormat.format("CIEC_DATE_AND_TIME(\"LDT#{0}-{1}\")", toDate(), toTimeOfDay()); //$NON-NLS-1$
		default:
			return "INVALID()"; //$NON-NLS-1$
		}
	}

}
