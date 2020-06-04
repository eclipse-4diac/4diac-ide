package org.eclipse.fordiac.ide.model.structuredtext.validation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatetimeLiteral {

	public enum Type {
		INVALID, T, LT, D, LD, TOD, LTOD, DT, LDT
	}

	private String literal = "";

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

	static final long kilo = 1000;
	static final long nsPerUs = kilo;
	static final long usPerMs = kilo;
	static final long msPerS = kilo;
	static final long sPerM = 60; // This may be wrong when using UTC, the last minute of the day can have 59, 60
	// or 61 seconds
	static final long mPerH = 60;
	static final long hPerD = 24;

	// prepare the "cheap" regex'es for splitting the string apart
	static final String UNSIGNED_INT_REGEX = "[0-9](_?[0-9])*";
	static final String DECIMAL_REGEX = "\\." + UNSIGNED_INT_REGEX;
	static final String FIXPOINT_REGEX = UNSIGNED_INT_REGEX + "(" + DECIMAL_REGEX + ")?";
	static final String UNIT_REGEX = "(ns|us|ms|s|m|h|d)";
	static final String FIXPOINT_WITH_UNIT_REGEX = FIXPOINT_REGEX + UNIT_REGEX;

	// Validate that all symbols are in the right place with the full regex
	// translated from EBNF (expensive!)
	static final String NANOSECONDS_REGEX = "(" + FIXPOINT_REGEX + "ns" + ")";
	static final String MICROSECONDS_REGEX = "((" + FIXPOINT_REGEX + "us" + ")|(" + UNSIGNED_INT_REGEX + "us(_)?)?"
			+ NANOSECONDS_REGEX + ")";
	static final String MILLISECONDS_REGEX = "((" + FIXPOINT_REGEX + "ms" + ")|(" + UNSIGNED_INT_REGEX + "ms(_)?)?"
			+ MICROSECONDS_REGEX + ")";
	static final String SECONDS_REGEX = "((" + FIXPOINT_REGEX + "s" + ")|(" + UNSIGNED_INT_REGEX + "s(_)?)?"
			+ MILLISECONDS_REGEX + ")";
	static final String MINUTES_REGEX = "((" + FIXPOINT_REGEX + "m" + ")|(" + UNSIGNED_INT_REGEX + "m(_)?)?"
			+ SECONDS_REGEX + ")";
	static final String HOURS_REGEX = "((" + FIXPOINT_REGEX + "h" + ")|(" + UNSIGNED_INT_REGEX + "h(_)?)?"
			+ MINUTES_REGEX + ")";
	static final String DAYS_REGEX = "((" + FIXPOINT_REGEX + "d" + ")|(" + UNSIGNED_INT_REGEX + "d(_)?)?" + HOURS_REGEX
			+ ")";

	static final String INTERVAL_REGEX = "((" + DAYS_REGEX + ")|(" + HOURS_REGEX + ")|(" + MINUTES_REGEX + ")|("
			+ SECONDS_REGEX + ")|(" + MILLISECONDS_REGEX + ")|(" + MICROSECONDS_REGEX + ")|(" + NANOSECONDS_REGEX
			+ "))";

	static final String DURATION_VALUE_REGEX = "(\\+|-)?" + INTERVAL_REGEX;

	static final String DATE_VALUE_REGEX = UNSIGNED_INT_REGEX + "-" + UNSIGNED_INT_REGEX + "-" + UNSIGNED_INT_REGEX;

	static final String TIMEOFDAY_VALUE_REGEX = UNSIGNED_INT_REGEX + ":" + UNSIGNED_INT_REGEX + ":" + FIXPOINT_REGEX;

	static final String DATETIME_VALUE_REGEX = DATE_VALUE_REGEX + "-" + TIMEOFDAY_VALUE_REGEX;

	static final Pattern durationValuePattern = Pattern.compile("^" + DURATION_VALUE_REGEX + "$");
	static final Pattern dateValuePattern = Pattern.compile("^" + DATE_VALUE_REGEX + "$");
	static final Pattern timeofdayValuePattern = Pattern.compile("^" + TIMEOFDAY_VALUE_REGEX + "$");
	static final Pattern datetimeValuePattern = Pattern.compile("^" + DATETIME_VALUE_REGEX + "$");

	static final Pattern fixpointWithUnitPattern = Pattern.compile(FIXPOINT_WITH_UNIT_REGEX);
	static final Pattern unsignedIntPattern = Pattern.compile(UNSIGNED_INT_REGEX);
	static final Pattern decimalPattern = Pattern.compile(DECIMAL_REGEX);
	static final Pattern unitPattern = Pattern.compile(UNIT_REGEX);

	public DatetimeLiteral(String string) {
		literal = string;

		final String uppercase = literal.toUpperCase();

		if (uppercase.startsWith("TOD#") || uppercase.startsWith("TIME_OF_DAY#")) {
			type = Type.TOD;
		} else if (uppercase.startsWith("LTOD#") || uppercase.startsWith("LTIME_OF_DAY#")) {
			type = Type.LTOD;
		} else if (uppercase.startsWith("DT#") || uppercase.startsWith("DATE_AND_TIME#")) {
			type = Type.DT;
		} else if (uppercase.startsWith("LDT#") || uppercase.startsWith("LDATE_AND_TIME#")) {
			type = Type.LDT;
		} else if (uppercase.startsWith("T#") || uppercase.startsWith("TIME#")) {
			type = Type.T;
		} else if (uppercase.startsWith("LT#") || uppercase.startsWith("LTIME#")) {
			type = Type.LT;
		} else if (uppercase.startsWith("D#") || uppercase.startsWith("DATE#")) {
			type = Type.D;
		} else if (uppercase.startsWith("LD#") || uppercase.startsWith("LDATE#")) {
			type = Type.LD;
		}

		final String[] dataSplit = literal.toLowerCase().split("#");
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

	public boolean isValid() {
		return type != Type.INVALID;
	}

	private void parseTOD() {
		if (!timeofdayValuePattern.matcher(data).matches()) {
			type = Type.INVALID;
			return;
		}

		Matcher m = unsignedIntPattern.matcher(data);
		List<String> matchList = new ArrayList<>();
		while (m.find()) {
			matchList.add(m.group());
		}
		String[] matches = new String[matchList.size()];
		matches = matchList.toArray(matches);

		if ((matches.length != 3) && (matches.length != 4)) {
			type = Type.INVALID;
			return;
		}

		hour = Long.parseLong(matches[0].replace("_", ""));
		minute = Long.parseLong(matches[1].replace("_", ""));
		second = Long.parseLong(matches[2].replace("_", ""));

		if (matches.length == 4) {
			String decimals = matches[3].replace("_", "");
			int places = decimals.length();

			if (places > 9) { // This limit is due to the defined resolution: nanoseconds
				type = Type.INVALID;
				return;
			}

			int divider = (int) Math.pow(10, places);

			nanosecond = (Long.parseLong(decimals) * msPerS * usPerMs * nsPerUs) / divider;
		}

		validateTOD();

	}

	private void validateTOD() {
		if ((hour < 0) || (hour > 23)) {
			type = Type.INVALID;
			return;
		}

		if ((minute < 0) || (minute > 59)) {
			type = Type.INVALID;
			return;
		}

		if ((second < 0) || (second > 59)) {
			type = Type.INVALID;
		}
	}

	private void parseDT() {
		if (!datetimeValuePattern.matcher(data).matches()) {
			type = Type.INVALID;
			return;
		}

		Matcher m = unsignedIntPattern.matcher(data);
		List<String> matchList = new ArrayList<>();
		while (m.find()) {
			matchList.add(m.group());
		}
		String[] matches = new String[matchList.size()];
		matches = matchList.toArray(matches);

		if ((matches.length != 6) && (matches.length != 7)) {
			type = Type.INVALID;
			return;
		}

		year = Long.parseLong(matches[0].replace("_", ""));
		month = Long.parseLong(matches[1].replace("_", ""));
		day = Long.parseLong(matches[2].replace("_", ""));
		validateD();
		if (type == Type.INVALID) {
			return;
		}

		hour = Long.parseLong(matches[3].replace("_", ""));
		minute = Long.parseLong(matches[4].replace("_", ""));
		second = Long.parseLong(matches[5].replace("_", ""));

		if (matches.length == 7) {
			String decimals = matches[6].replace("_", "");
			int places = decimals.length();

			if (places > 9) { // This limit is due to the defined resolution: nanoseconds
				type = Type.INVALID;
				return;
			}

			int divider = (int) Math.pow(10, places);

			nanosecond = (Long.parseLong(decimals) * msPerS * usPerMs * nsPerUs) / divider;
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

		if (data.startsWith("-")) {
			isNegative = true;
		}

		Matcher m = fixpointWithUnitPattern.matcher(data);
		List<String> matchList = new ArrayList<>();
		while (m.find()) {
			matchList.add(m.group());
		}
		String[] matches = new String[matchList.size()];
		matches = matchList.toArray(matches);

		for (String match : matches) {
			m = unsignedIntPattern.matcher(match);
			matchList = new ArrayList<>();
			while (m.find()) {
				matchList.add(m.group());
			}
			String[] value = new String[matchList.size()];
			value = matchList.toArray(value);

			m = decimalPattern.matcher(match);
			matchList = new ArrayList<>();
			while (m.find()) {
				matchList.add(m.group());
			}
			String[] decimal = new String[matchList.size()];
			decimal = matchList.toArray(decimal);

			m = unitPattern.matcher(match);
			matchList = new ArrayList<>();
			while (m.find()) {
				matchList.add(m.group());
			}
			String[] unit = new String[matchList.size()];
			unit = matchList.toArray(unit);

			if (decimal.length > 1) {
				type = Type.INVALID;
				return;
			}

			long intValue = Long.parseLong(value[0].replace("_", ""));
			String decimals = decimal.length == 1 ? decimal[0].substring(1).replace("_", "") : "0";
			long intDecimal = Long.parseLong(decimals);
			int places = decimals.length();

			if (places > 12) { // This limit is arbitrary
				type = Type.INVALID;
				return;
			}

			long divider = (int) Math.pow(10, places);

			switch (unit[0]) {
			case "d":
				day = intValue;
				fillFromNs((intDecimal * hPerD * mPerH * sPerM * msPerS * usPerMs * nsPerUs) / divider);
				break;
			case "h":
				if (null != hour) {
					type = Type.INVALID;
					return;
				}
				hour = intValue;
				fillFromNs((intDecimal * mPerH * sPerM * msPerS * usPerMs * nsPerUs) / divider);
				break;
			case "m":
				if (null != minute) {
					type = Type.INVALID;
					return;
				}
				minute = intValue;
				fillFromNs((intDecimal * sPerM * msPerS * usPerMs * nsPerUs) / divider);
				break;
			case "s":
				if (null != second) {
					type = Type.INVALID;
					return;
				}
				second = intValue;
				fillFromNs((intDecimal * msPerS * usPerMs * nsPerUs) / divider);
				break;
			case "ms":
				if (null != millisecond) {
					type = Type.INVALID;
					return;
				}
				millisecond = intValue;
				fillFromNs((intDecimal * usPerMs * nsPerUs) / divider);
				break;
			case "us":
				if (null != microsecond) {
					type = Type.INVALID;
					return;
				}
				microsecond = intValue;
				fillFromNs((intDecimal * nsPerUs) / divider);
				break;
			case "ns":
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
				&& (nanosecond > 999)) || //
				(//
				(null != microsecond) && //
						((null != millisecond) || (null != second) || (null != minute) || (null != hour)
								|| (null != day)) //
						&& (microsecond > 999))
				|| //
				(//
				(null != millisecond) && //
						((null != second) || (null != minute) || (null != hour) || (null != day)) //
						&& (millisecond > 999))
				|| //
				(//
				(null != second) && //
						((null != minute) || (null != hour) || (null != day)) //
						&& (second > 59))
				|| //
				(//
				(null != minute) && //
						((null != hour) || (null != day)) //
						&& (minute > 59))
				|| //
				(//
				(null != hour) && //
						((null != day)) //
						&& (hour > 23))//
		) {
			type = Type.INVALID;
		}

	}

	private void fillFromNs(long nanoseconds) {
		long nanosecondTemp = nanoseconds;
		long temp = nanosecondTemp / (mPerH * sPerM * msPerS * usPerMs * nsPerUs);
		if (temp != 0) {
			if (null != hour) {
				type = Type.INVALID;
				return;
			}
			hour = temp;
		}
		nanosecondTemp = nanosecondTemp % (mPerH * sPerM * msPerS * usPerMs * nsPerUs);
		temp = nanosecondTemp / (sPerM * msPerS * usPerMs * nsPerUs);
		if (temp != 0) {
			if (null != minute) {
				type = Type.INVALID;
				return;
			}
			minute = temp;
		}
		nanosecondTemp = nanosecondTemp % (sPerM * msPerS * usPerMs * nsPerUs);
		temp = nanosecondTemp / (msPerS * usPerMs * nsPerUs);
		if (temp != 0) {
			if (null != second) {
				type = Type.INVALID;
				return;
			}
			second = temp;
		}
		nanosecondTemp = nanosecondTemp % (msPerS * usPerMs * nsPerUs);
		temp = nanosecondTemp / (usPerMs * nsPerUs);
		if (temp != 0) {
			if (null != millisecond) {
				type = Type.INVALID;
				return;
			}
			millisecond = temp;
		}
		nanosecondTemp = nanosecondTemp % (usPerMs * nsPerUs);
		temp = nanosecondTemp / (nsPerUs);
		if (temp != 0) {
			if (null != microsecond) {
				type = Type.INVALID;
				return;
			}
			microsecond = temp;
		}
		temp = nanosecondTemp % (nsPerUs);
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
			s.append("-");
		}
		if ((null != day) && (day != 0)) {
			s.append(day);
			s.append("d");
		}
		if ((null != hour) && (hour != 0)) {
			s.append(hour);
			s.append("h");
		}
		if ((null != minute) && (minute != 0)) {
			s.append(minute);
			s.append("m");
		}
		if ((null != second) && (second != 0)) {
			s.append(second);
			s.append("s");
		}
		if ((null != millisecond) && (millisecond != 0)) {
			s.append(millisecond);
			s.append("ms");
		}
		if ((null != microsecond) && (microsecond != 0)) {
			s.append(microsecond);
			s.append("us");
		}
		if ((null != nanosecond) && (nanosecond != 0)) {
			s.append(nanosecond);
			s.append("ns");
		}
		return s.toString();
	}

	private void parseD() {
		if (!dateValuePattern.matcher(data).matches()) {
			type = Type.INVALID;
			return;
		}

		Matcher m = unsignedIntPattern.matcher(data);
		List<String> matchList = new ArrayList<>();
		while (m.find()) {
			matchList.add(m.group());
		}
		String[] matches = new String[matchList.size()];
		matches = matchList.toArray(matches);

		if (matches.length != 3) {
			type = Type.INVALID;
			return;
		}

		year = Long.parseLong(matches[0].replace("_", ""));
		month = Long.parseLong(matches[1].replace("_", ""));
		day = Long.parseLong(matches[2].replace("_", ""));

		validateD();

	}

	private void validateD() {
		if (day < 1) {
			type = Type.INVALID;
			return;
		}

		switch (month.intValue()) {
		case 2:
			boolean isLeapYear = ((((year % 4) == 0) && ((year % 100) != 0)) || ((year % 400) == 0));
			if (day > (isLeapYear ? 29 : 28)) {
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
			if (day > 31) {
				type = Type.INVALID;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if (day > 30) {
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
		s.append("-");
		s.append(String.format("%02d", month));
		s.append("-");
		s.append(String.format("%02d", day));
		return s.toString();
	}

	private String toTimeOfDay() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("%02d", hour));
		s.append(":");
		s.append(String.format("%02d", minute));
		s.append(":");
		s.append(String.format("%02d", second));
		if ((null != nanosecond) && (nanosecond != 0)) {
			s.append(".");
			s.append(nanosecond);
		}
		return s.toString();
	}

	@Override
	public String toString() {
		switch (type) {
		case T:
			return MessageFormat.format("CIEC_TIME(\"T#{0}\")", toInterval());
		case LT:
			return MessageFormat.format("CIEC_TIME(\"LT#{0}\")", toInterval());
		case D:
			return MessageFormat.format("CIEC_DATE(\"D#{0}\")", toDate());
		case LD:
			return MessageFormat.format("CIEC_DATE(\"LD#{0}\")", toDate());
		case TOD:
			return MessageFormat.format("CIEC_TIME_OF_DAY(\"TOD#{0}\")", toTimeOfDay());
		case LTOD:
			return MessageFormat.format("CIEC_TIME_OF_DAY(\"LTOD#{0}\")", toTimeOfDay());
		case DT:
			return MessageFormat.format("CIEC_DATE_AND_TIME(\"DT#{0}-{1}\")", toDate(), toTimeOfDay());
		case LDT:
			return MessageFormat.format("CIEC_DATE_AND_TIME(\"LDT#{0}-{1}\")", toDate(), toTimeOfDay());
		default:
			return "INVALID()";
		}
	}

}
