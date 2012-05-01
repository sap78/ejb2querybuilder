/*
 * Created on Apr 26, 2005
 */
package com.upulgodage.util.ejb.finder.clause;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.upulgodage.util.ejb.finder.exception.ClauseException;
import com.upulgodage.util.logging.DebugUtils;

/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
class JoinClause extends WhereClause {
	private static Log log = LogFactory.getLog(JoinClause.class);
	public static final String JOIN_WITH_OR = " OR ";
	public static final String JOIN_WITH_AND = " AND ";

    public JoinClause(Collection clauses, String joinWith) throws ClauseException {
	    final String logPrefix = "joinCl";
	    log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_BEGIN);

        if( (!JoinClause.JOIN_WITH_AND.equals(joinWith)) && (!JoinClause.JOIN_WITH_OR.equals(joinWith))) {
            String errorMessage = logPrefix + " joinWith invalid";
            log.error(errorMessage);
            throw new ClauseException();
        }

	    if(clauses.isEmpty()) {
	        expression = "(1 = 1)";
	        //!return
	        return;
	    }

        StringBuffer buf = new StringBuffer();
        buf.append("(");
        Iterator iterClauses = clauses.iterator();

        WhereClause first = (WhereClause) iterClauses.next();
        Collection firstObjects = first.getObjects();
        objects.addAll(firstObjects);
        buf.append(first.getExpression());

        int offset = firstObjects.size() + 1;
        while(iterClauses.hasNext()) {
            WhereClause second = (WhereClause) iterClauses.next();
            Collection secondObjects = second.getObjects();
            objects.addAll(secondObjects);
            String secondExpression = second.getExpression();

            secondExpression = secondExpression.replaceAll(Clause.paramStandin, Clause.paramStandin2);
            for(int i = 0; i < secondObjects.size(); i++) {
                String paramFinder = Clause.paramStandin2 + (i+1) + "([^0-9])";
                String paramReplacer = Clause.paramStandin + offset + "$1";
                secondExpression = secondExpression.replaceAll(paramFinder, paramReplacer);
                offset++;
            }
            buf.append(joinWith + secondExpression);
        }
        buf.append(")");
        expression = buf.toString();
        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_END + " exp=" + expression + " osz=" + objects.size());
    }
}

//!end

