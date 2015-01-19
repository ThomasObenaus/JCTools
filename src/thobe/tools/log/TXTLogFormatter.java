/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */
package thobe.tools.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * @author Thomas Obenaus
 * @source TXTLogFormatter.java
 * @date 26.03.2012
 */
public class TXTLogFormatter extends SimpleFormatter {
	private static DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	public TXTLogFormatter() {
	}

	@Override
	public String format(LogRecord record) {

		StringBuilder sb = new StringBuilder();

		String dateStr = formatter.format(new Date(record.getMillis()));
		sb.append(dateStr + " ");
		if (record.getLoggerName() != null) 
		{
			sb.append(record.getLoggerName() + " ");
		}
		sb.append("[" + record.getLevel().getLocalizedName() + "] ");
		sb.append(formatMessage(record)).append(LINE_SEPARATOR);

		if (record.getThrown() != null) {
			try {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				record.getThrown().printStackTrace(pw);
				pw.close();
				sb.append(sw.toString());
			} catch (Exception ex) {
				// ignore
			}
		}

		return sb.toString();
	}

}
