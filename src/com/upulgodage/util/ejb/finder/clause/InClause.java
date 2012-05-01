/*
 * Created on Apr 18, 2005
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
public class InClause extends WhereClause {
	private static Log log = LogFactory.getLog(InClause.class);

    public InClause(String attributeName, Collection values) throws ClauseException {
    	this(attributeName, values, true);
    }


    public InClause(String attributeName, Collection values, boolean positive) throws ClauseException {
	    final String logPrefix = "inCl";
        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_BEGIN);

        if(attributeName == null) {
            String errorMessage = logPrefix + " attributeName is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        if(values == null) {
            String errorMessage = logPrefix + " values is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        if(values.isEmpty()) {
            String errorMessage = logPrefix + " values is empty";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        StringBuffer exp = new StringBuffer();
        exp.append("");
        exp.append(attributeName);

        if(positive == true) {
        	// do nothing
        } else {
        	exp.append(" NOT");
        }

        exp.append(" IN (");
        for(int i = 0; i < values.size(); i++) {
            if(i > 0) {
                exp.append(", ");
            }
            	exp.append(Clause.paramStandin);
            	exp.append(i+1);
            }
        exp.append(")");

        expression = exp.toString();
        objects.addAll(values);

        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_END + " exp=" + expression + " osz=" + objects.size());
    }
}

//!end
