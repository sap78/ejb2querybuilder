/*
 * Created on May 17, 2005
 */
package com.upulgodage.util.ejb.finder.clause;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.upulgodage.util.ejb.finder.exception.ClauseException;
import com.upulgodage.util.logging.DebugUtils;

/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public class EqualBeanClause extends WhereClause {
	private static Log log = LogFactory.getLog(EqualBeanClause.class);

    public EqualBeanClause(String prefix, Object bean) throws ClauseException {
	    final String logPrefix = "eqBCl";
        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_BEGIN);

        if(prefix == null) {
            String errorMessage = logPrefix + " prefix is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        if(bean == null) {
            String errorMessage = logPrefix + " bean is null";
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

        StringBuffer whereClause = new StringBuffer();
        Collection whereObjects = new ArrayList();

        Map attrMap = null;
        try {
            attrMap = PropertyUtils.describe(bean);
        } catch (IllegalAccessException e) {
            String errorMessage = logPrefix + " PropertyUtils.describe(bean) e=" + e;
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        } catch (InvocationTargetException e) {
            String errorMessage = logPrefix + " PropertyUtils.describe(bean) e=" + e;
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        } catch (NoSuchMethodException e) {
            String errorMessage = logPrefix + " PropertyUtils.describe(bean) e=" + e;
            log.error(errorMessage);
            //!throw
            throw new ClauseException(errorMessage);
        }

		Collection excludeAttributeList = new ArrayList();
        String strExcludeAttributeList = System.getProperty("finder.finderHandler.excludeAttributeList");
		if(strExcludeAttributeList != null) {
			String[] s1 = strExcludeAttributeList.split(";");
			Collection s2 = Arrays.asList(s1);
			excludeAttributeList.addAll(s2);
		} else {
	        excludeAttributeList.add("primaryKey");
	        excludeAttributeList.add("class");
		}

        int countArgs = 0;
        Set attrKeySet = attrMap.keySet();
        Iterator iterParamKeySet = attrKeySet.iterator();

        whereClause.append("(");

        boolean isFirst = true;
        while(iterParamKeySet.hasNext()) {
            String key = (String) iterParamKeySet.next();
            Object value = attrMap.get(key);
            if((value != null)
                    && (value instanceof Object)
                    && (!excludeAttributeList.contains(key))) {
                if(isFirst == false) {
                	whereClause.append(JoinClause.JOIN_WITH_AND);
                } else {
                	isFirst = false;
                }

            	whereClause.append("(" + prefix + "." + key + " = " + Clause.paramStandin + (++countArgs) + ")");
                whereObjects.add(value);
            }
        }

        if(countArgs == 0) {
        	whereClause.append("(1 = 1)");
        }

        whereClause.append(")");

        expression = whereClause.toString();
        objects.addAll(whereObjects);

        log.debug(logPrefix + DebugUtils.LOG_SYMBOL_METHOD_END + " exp=" + expression + " osz=" + objects.size());
    }
}

//!end
