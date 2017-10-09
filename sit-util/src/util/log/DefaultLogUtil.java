package util.log;

import org.apache.log4j.Logger;

/**
 * Default logger<br>
 * 1. INITIAL<br>
 * 2. INTERCEPTOR<br>
 * 3. SELECTOR<br>
 * 4. COMMON<br>
 * 5. FILTER<br>
 * 6. UTIL<br>
 * @author sittipol.m
 *
 */
public class DefaultLogUtil {

	public static final Logger INITIAL = Logger.getLogger(DefaultLoggerName.INITIAL.getValue());
	public static final Logger INTERCEPTOR = Logger.getLogger(DefaultLoggerName.INTERCEPTOR.getValue());
	public static final Logger SELECTOR = Logger.getLogger(DefaultLoggerName.SELECTOR.getValue());
	public static final Logger COMMON = Logger.getLogger(DefaultLoggerName.COMMON.getValue());
	public static final Logger FILTER = Logger.getLogger(DefaultLoggerName.FILTER.getValue());
	public static final Logger UTIL = Logger.getLogger(DefaultLoggerName.UTIL.getValue());
	public static final Logger SERVER_VALIDATION = Logger.getLogger(DefaultLoggerName.SERVER_VALIDATION.getValue());
}