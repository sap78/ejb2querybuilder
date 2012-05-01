/*
 * Created on Aug 3, 2005
 */
package com.upulgodage.util.ejb.finder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.upulgodage.util.ejb.finder.clause.AndClause;
import com.upulgodage.util.ejb.finder.clause.AscOrderClause;
import com.upulgodage.util.ejb.finder.clause.BetweenClause;
import com.upulgodage.util.ejb.finder.clause.Clause;
import com.upulgodage.util.ejb.finder.clause.ComparisonClause;
import com.upulgodage.util.ejb.finder.clause.CustomClause;
import com.upulgodage.util.ejb.finder.clause.DescOrderClause;
import com.upulgodage.util.ejb.finder.clause.EqualBeanClause;
import com.upulgodage.util.ejb.finder.clause.InClause;
import com.upulgodage.util.ejb.finder.clause.LikeClause;
import com.upulgodage.util.ejb.finder.clause.NotClause;
import com.upulgodage.util.ejb.finder.clause.NullClause;
import com.upulgodage.util.ejb.finder.clause.OrClause;
import com.upulgodage.util.ejb.finder.clause.OrderClause;
import com.upulgodage.util.ejb.finder.clause.WhereClause;
import com.upulgodage.util.ejb.finder.exception.ClauseException;
import com.upulgodage.util.logging.DebugUtils;

/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public class JbossFinderHelper implements FinderHelper {
	private static Log log = LogFactory.getLog(JbossFinderHelper.class);

	public FinderParam createFinderParam(String selectFromClause, Collection whereClauses, Collection orderClauses, Map paramMap) {
        final String logPrefix = "find";
        Collection whereClause = new ArrayList();
        Collection orderClause = new ArrayList();
        int countArgs = 1;
        Collection whereObjects = new ArrayList();
        StringBuffer dynamicSql = new StringBuffer();

        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_BEGIN);

        String finalJoinType = null;
        if(paramMap != null) {
            finalJoinType = (String) paramMap.get(FinderGlobal.FINAL_JOIN_TYPE_KEY);
            if(finalJoinType == null) { // default
                finalJoinType = FinderGlobal.FINAL_JOIN_TYPE_AND;
                log.debug(logPrefix + " def join=" + finalJoinType);
            } else {
            	log.debug(logPrefix + " join=" + finalJoinType);
            }


        } else {
            // no paramMap set default values
            finalJoinType = FinderGlobal.FINAL_JOIN_TYPE_AND;
            log.debug(logPrefix + " def2 join=" + finalJoinType);
        }

        // selectFromClause
        dynamicSql.append(selectFromClause);

        // whereClauses
        if(whereClauses != null) {
            Iterator iterClauses = whereClauses.iterator();
            while(iterClauses.hasNext()) {
                WhereClause selectedClause = (WhereClause) iterClauses.next();
                if(selectedClause == null) {
                    String errorMessage = logPrefix + " clause is null";
                    log.error(errorMessage);
                    //!throw
                    throw new NullPointerException(errorMessage);
                }
                Collection objects = selectedClause.getObjects();
                String expression = selectedClause.getExpression();

                for(int i = 0; i < objects.size(); i++) {
                    String paramFinder = Clause.paramStandin + (i+1) + "([^0-9])";
                    String paramReplacer = "?" + countArgs + "$1";
                    expression = expression.replaceAll(paramFinder, paramReplacer);
                    countArgs++;
                }
                whereClause.add(expression);
                whereObjects.addAll(objects);
            }
        }
        log.debug(logPrefix + " whereClauses" + DebugUtils.LOG_SYMBOL_PASS);

        // orderClauses
        if(orderClauses != null) {
            Iterator iterOrderClauses = orderClauses.iterator();
            while(iterOrderClauses.hasNext()) {
                OrderClause selectedClause = (OrderClause) iterOrderClauses.next();
                String expression = selectedClause.getExpression();
                orderClause.add(expression);
            }
        }
        log.debug(logPrefix + " orderClauses" + DebugUtils.LOG_SYMBOL_PASS);

        // append where expressions
        Iterator iterWhereClause = whereClause.iterator();
        if(iterWhereClause.hasNext()) {
            dynamicSql.append(" WHERE ");
            dynamicSql.append(iterWhereClause.next());
        }
        while(iterWhereClause.hasNext()) {
            dynamicSql.append(finalJoinType);
            dynamicSql.append(iterWhereClause.next());
        }
        log.debug(logPrefix + " whereClause" + DebugUtils.LOG_SYMBOL_PASS);

        // append order expressions
        Iterator iterOrderClause = orderClause.iterator();
        if(iterOrderClause.hasNext()) {
            dynamicSql.append(" ORDER BY ");
            dynamicSql.append(iterOrderClause.next());
        }
        while(iterOrderClause.hasNext()) {
            dynamicSql.append(", ");
            dynamicSql.append(iterOrderClause.next());
        }
        log.debug(logPrefix + " orderClause" + DebugUtils.LOG_SYMBOL_PASS);


        // paramMap
        if (paramMap != null) {
            // offset limit
            Integer offset = (Integer) paramMap.get(FinderGlobal.FIND_PARAM_OFFSET);
            Integer limit = (Integer) paramMap.get(FinderGlobal.FIND_PARAM_LIMIT);

            if((limit != null) && (offset != null)) {
                String offsetLimit = " OFFSET ?" + countArgs + " LIMIT ?" + (countArgs+1);
                countArgs += 2;
                dynamicSql.append(offsetLimit);
                whereObjects.add(offset);
                whereObjects.add(limit);

            }

            // append query
            String appendQuery = (String) paramMap.get(FinderGlobal.FIND_PARAM_APPEND_QUERY);
            if(appendQuery != null) {
                dynamicSql.append(" " + appendQuery);
            }

        } else { // paramMap is null
            // paramMap defaults
        }
        log.debug(logPrefix + " paramMap" + DebugUtils.LOG_SYMBOL_PASS);

        // final sql
        log.info(logPrefix + " sql=" + dynamicSql.toString());

        // total objects
        Object[] args = whereObjects.toArray();
        StringBuffer strArgs = new StringBuffer();
        for(int i = 0; i < args.length; i++) {
            strArgs.append((i+1) + "=" + args[i] + "; ");
        }
        log.info(logPrefix + " args " + strArgs);

        FinderParam FinderParam = new FinderParam();
        FinderParam.setArgs(args);
        FinderParam.setSql(dynamicSql.toString());

        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_END);
        //!return
        return FinderParam;
	}


	public WhereClause newEqualClause(String attributeName, Object value) throws ClauseException {
		return new ComparisonClause(attributeName, value, ComparisonClause.EQUAL);
	}

	public WhereClause newNotEqualClause(String attributeName, Object value) throws ClauseException {
		return new ComparisonClause(attributeName, value, ComparisonClause.NOT_EQUAL);
	}

	public WhereClause newGreaterThanClause(String attributeName, Object value) throws ClauseException {
		return new ComparisonClause(attributeName, value, ComparisonClause.GREATER_THAN);
	}

	public WhereClause newGreaterThanOrEqualClause(String attributeName, Object value) throws ClauseException {
		return new ComparisonClause(attributeName, value, ComparisonClause.GREATER_THAN_OR_EQUAL);
	}

	public WhereClause newLessThanClause(String attributeName, Object value) throws ClauseException {
		return new ComparisonClause(attributeName, value, ComparisonClause.LESS_THAN);
	}

	public WhereClause newLessThanOrEqualClause(String attributeName, Object value) throws ClauseException {
		return new ComparisonClause(attributeName, value, ComparisonClause.LESS_THAN_OR_EQUAL);
	}

	public WhereClause newEqualBeanClause(String prefix, Object bean) throws ClauseException {
		return new EqualBeanClause(prefix, bean);
	}

	public WhereClause newNotClause(WhereClause whereClause) throws ClauseException {
		return new NotClause(whereClause);
	}

	public WhereClause newNullClause(String attributeName) throws ClauseException {
		return new NullClause(attributeName);
	}

	public WhereClause newNotNullClause(String attributeName) throws ClauseException {
		return new NullClause(attributeName, false);
	}

	public WhereClause newInClause(String attributeName, Collection values) throws ClauseException {
		return new InClause(attributeName, values);
	}

	public WhereClause newNotInClause(String attributeName, Collection values) throws ClauseException {
		return new InClause(attributeName, values, false);
	}

	public WhereClause newLikeClause(String attributeName, Object value) throws ClauseException {
		return new LikeClause(attributeName, value);
	}

	public WhereClause newNotLikeClause(String attributeName, Object value) throws ClauseException {
		return new LikeClause(attributeName, value, false);
	}

	public WhereClause newLikeClause(String attributeName, Object value, String escapeChar) throws ClauseException {
		return new LikeClause(attributeName, value, true, escapeChar);
	}

	public WhereClause newNotLikeClause(String attributeName, Object value, String escapeChar) throws ClauseException {
		return new LikeClause(attributeName, value, false, escapeChar);
	}

	public WhereClause newBetweenClause(String attributeName, Object low, Object high) throws ClauseException {
		return new BetweenClause(attributeName, low, high);
	}

	public WhereClause newNotBetweenClause(String attributeName, Object low, Object high) throws ClauseException {
		return new BetweenClause(attributeName, low, high, false);
	}

	public WhereClause newOrClause(Collection clauses) throws ClauseException {
		return new OrClause(clauses);
	}

	public WhereClause newAndClause(Collection clauses) throws ClauseException {
		return new AndClause(clauses);
	}

	public WhereClause newCustomClause(String query, Collection args) throws ClauseException {
		return new CustomClause(query, args);
	}

	public OrderClause newAscOrderClause(String field) throws ClauseException {
		return new AscOrderClause(field);
	}

	public OrderClause newDescOrderClause(String field) throws ClauseException {
		return new DescOrderClause(field);
	}

}

//!end
