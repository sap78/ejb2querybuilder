/*
 * Created on Aug 8, 2005
 *
 */
package com.upulgodage.util.ejb.finder.clause;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.upulgodage.util.ejb.finder.exception.ClauseException;
import com.upulgodage.util.logging.DebugUtils;

/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public class CustomClause extends WhereClause {
	private static Log log = LogFactory.getLog(CustomClause.class);

    public CustomClause(String query, Collection args) throws ClauseException {
	    final String logPrefix = "cusCl";
	    log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_BEGIN);

        if(query == null) {
            String errorMessage = logPrefix + " query is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        expression = "(" + query.replaceAll("\\?([1-9]+)", paramStandin + "$1") + ")";
        if(args != null)
        	objects.addAll(args);

        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_END + " exp=" + expression + " osz=" + objects.size());
    }
}

//!end
