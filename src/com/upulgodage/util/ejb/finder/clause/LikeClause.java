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
public class LikeClause extends WhereClause {
	private static Log log = LogFactory.getLog(LikeClause.class);

	public LikeClause(String attributeName, Object value) throws ClauseException {
		this(attributeName, value, true);
	}

	public LikeClause(String attributeName, Object value, boolean positive) throws ClauseException {
		this(attributeName, value, true, null);
	}


    public LikeClause(String attributeName, Object value, boolean positive, String escapeChar) throws ClauseException {
	    final String logPrefix = "likeCl";
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

        StringBuffer exp = new StringBuffer();
        exp.append("");
        exp.append("(" + attributeName);

        if(positive == true) {
        	// do nothing
        } else {
        	exp.append(" NOT");
        }

        exp.append(" LIKE " + Clause.paramStandin + "1");

        if(escapeChar != null) {
        	exp.append(" ESCAPE '" + escapeChar + "'");
        }

        exp.append(")");

        expression = exp.toString();
        objects.add(value);

        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_END + " exp=" + expression + " osz=" + objects.size());
    }
}

//!end
