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
public class NullClause extends WhereClause {
	private static Log log = LogFactory.getLog(NullClause.class);

	public NullClause(String attributeName) throws ClauseException {
		this(attributeName, true);
	}

    public NullClause(String attributeName, boolean positive) throws ClauseException {
	    final String logPrefix = "nulCl";
        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_BEGIN);

        if(attributeName == null) {
            String errorMessage = logPrefix + " attributeName is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        expression = "(" + attributeName + " is"
						+ (positive ? "" : " not")
						+ " null)";

        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_END + " exp=" + expression + " osz=" + objects.size());
    }
}

//!end
