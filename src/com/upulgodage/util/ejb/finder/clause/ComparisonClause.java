/*
 * Created on May 17, 2005
 */
package com.upulgodage.util.ejb.finder.clause;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.upulgodage.util.ejb.finder.exception.ClauseException;
import com.upulgodage.util.logging.DebugUtils;

/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public class ComparisonClause extends WhereClause {
	private static Log log = LogFactory.getLog(ComparisonClause.class);

	public static String EQUAL = " = ";
	public static String NOT_EQUAL = " <> ";
	public static String GREATER_THAN = " > ";
	public static String GREATER_THAN_OR_EQUAL = " >= ";
	public static String LESS_THAN = " < ";
	public static String LESS_THAN_OR_EQUAL = " <= ";


    public ComparisonClause(String attributeName, Object value, String comparison) throws ClauseException {
	    final String logPrefix = "compCl";
        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_BEGIN);

        if(attributeName == null) {
            String errorMessage = logPrefix + " attributeName is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        if(value == null) {
            String errorMessage = logPrefix + " value is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        if(comparison == null) {
            String errorMessage = logPrefix + " comparison is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        expression = "(" + attributeName + comparison + Clause.paramStandin + "1)";
        objects.add(value);

        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_END + " exp=" + expression + " osz=" + objects.size());
    }
}

//!end
