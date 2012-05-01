/*
 * Created on Apr 18, 2005
 */
package com.upulgodage.util.ejb.finder.clause;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public abstract class Clause {
    public static final String paramStandin = "~@@~";
    public static final String paramStandin2 = "~##~";
    protected Collection objects = new ArrayList();
    protected String expression;

    public Collection getObjects() {
        return objects;
    }

    public String getExpression() {
        return expression;
    }

    public String toString() {
        Object[] args = objects.toArray();
        StringBuffer strArgs = new StringBuffer();
        for(int i = 0; i < args.length; i++) {
            strArgs.append((i+1) + "=" + args[i] + "; ");
        }
        return "exp=" + expression + " args " + strArgs;
    }
}

//!end
