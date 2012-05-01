/*
 * Created on Apr 18, 2005
 */
package com.upulgodage.util.ejb.finder.clause;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.upulgodage.util.ejb.finder.exception.ClauseException;
import com.upulgodage.util.logging.DebugUtils;


/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public class BetweenClause extends WhereClause {
	private static Log log = LogFactory.getLog(BetweenClause.class);

    public BetweenClause(String attributeName, Object low, Object high) throws ClauseException {
    	this(attributeName, low, high, true);
    }

    public BetweenClause(String attributeName, Object low, Object high, boolean positive) throws ClauseException {
	    final String logPrefix = "betCl";
        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_BEGIN);

        if(attributeName == null) {
            String errorMessage = logPrefix + " attributeName is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        if(low == null) {
            String errorMessage = logPrefix + " low is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        if(high == null) {
            String errorMessage = logPrefix + " high is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        objects.add(low);
        objects.add(high);

        expression = "(" + attributeName
					+ (positive ? "" : " NOT")
					+ " BETWEEN "
        			+ Clause.paramStandin + "1 AND "
        			+ Clause.paramStandin + "2)";

        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_END + " exp=" + expression + " osz=" + objects.size());
    }

}

//!end
