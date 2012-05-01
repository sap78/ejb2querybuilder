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
public class NotClause extends WhereClause {
	private static Log log = LogFactory.getLog(NotClause.class);

    public NotClause(WhereClause whereCriterion) throws ClauseException {
	    final String logPrefix = "notCl";
        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_BEGIN);

        if(whereCriterion == null) {
            String errorMessage = logPrefix + " whereCriterion is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        expression = "( NOT(" + whereCriterion.getExpression() + "))";
        objects.addAll(whereCriterion.getObjects());

        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_END + " exp=" + expression + " osz=" + objects.size());
    }
}

//!end
