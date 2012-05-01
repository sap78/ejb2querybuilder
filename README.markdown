#EJB2 Query Builder

    For example, writing a query for something like this search page with multiple optional parameters.

    ------------------------------------
    Reference Number: _____
    Date From: _____   To: _____
    Was Printed: []
    [Submit]
    ------------------------------------

    // FinderHelper implementation is the factory for different where clauses
    // If in JBoss, get JBoss EJB-QL specific FinderHelper implementation
    // e.g. JBossFinderHelper
    // Some application servers may not support all the query language syntax
    // e.g. between clause. In that case newBetweenClause() should throw NotSupportedException
    // or build query using relational operators instead of between
    FinderHelper fh = FinderFactory.getFinderHelper();
    
    Collection whereClauses = new ArrayList();
    
    if (isConditionsAreMet == true) {
    
    Collection subWhereClauses = new ArrayList();
    
    // Creates query (u.text1 = $1)
    WhereClause eqText1 = fh.newEqualClause("u.text1", findText1);
    subWhereClauses.add(eqText1);
    
    // Creates query (d.date1 between $1 and $2)
    WhereClause dateRange = fh.newBetweenClause("d.date1", findFromDate, findToDate);
    subWhereClauses.add(dateRange);
    
       
    // Creates query ((u.text1 = $1) and (d.date1 between $2 and $3))
    // Note: $1, $2 change number depending on the position in the final query
    // Without FinderHelper this has to be done by the programmer
    
    // Note: newAndClause() returns WhereClause which can be sent again
    // to newAndClause(), newOrClause() to make complex queries
    // Users handle every clause as a WhereClause, not the specific type
    
    // Note: Even if subWhereClauses is empty because of conditional logic
    // it will generate valid and correct query using (1=1)
    // So client programmer does not have to worry about creating
    
    // AndClause only if there are at least 2 sub-expressions
    // Every scenario is handled by the FinderHelper
    WhereClause andClause = fh.newAndClause(subWhereClauses); 
    
    whereClauses.add(andClause);
    
    }
    
    if (isSearchTextNotEmpty == true) {
    
    // Creates query (u.searchText like $1)
    // Without FinderHelper programmer needs to know
    // which number to use for $? depending on how
    // many $1, $2 etc. were there before
    WhereClause eqSearchText = fh.newLikeClause("u.searchText", findSearchText);
    whereClauses.add(eqSearchText);
    
    }
    
    if (isShowNotPrinted == true) {
    
    // Creates query (wasPrinted is not null)
    WhereClause notPrinted = fh.newNotNullClause("d.wasPrinted");
    whereClauses.add(notPrinted);
    
    }
    
    // Note: Custom arguments are given as $1, $2 by the client programmer
    // After parsing they will be given the proper number
    // depending on the position in the final query statement
    
    Collection customArgs = new ArrayList();
    
    customArgs.add(batchNumber);
    
    customArgs.add(“1_0_0”);
    
    WhereClause releaseInfo = fh.newCustomClause("d.batch = $1 and dt.release = $2 and d.docType = dt", customtArgs);
    whereClauses.add(releaseInfo);
    
    // Get all not-null attributes and make an equal clause using Java reflection
    // Creates query (d.text1 = $1 and d.text4 = $2 and d.number = $3)
    // when anyBean.getText1(), getText4(), getNumber() are not null
    whereClauses.add(fh.newEqualBeanClause(“d”, anyBean));
    
    // Get value objects collection directly
    FinderLocal finder = finderHome.create();
    Collection lightValues = finder.find("SELECT OBJECT(d) FROM Document d, DocumentType dt",
            whereClauses, null, null, "getDocumentLightValue");
        
        
#FinderHelper Client interface
    
    newEqualClause(String attributeName, Object value)
    newNotEqualClause(String attributeName, Object value)
    
    newGreaterThanClause(String attributeName, Object value)
    newGreaterThanOrEqualClause(String attributeName, Object value)
    newLessThanClause(String attributeName, Object value)
    newLessThanOrEqualClause(String attributeName, Object value)
    
    newEqualBeanClause(String prefix, Object bean)
    
    newNotClause(WhereClause whereCriterion)
    
    newNullClause(String attributeName)
    newNotNullClause(String attributeName)
    
    newInClause(String attributeName, Collection values)
    newNotInClause(String attributeName, Collection values)
    
    newLikeClause(String attributeName, Object value)
    newNotLikeClause(String attributeName, Object value)
    
    newLikeClause(String attributeName, Object value, String escapeChar)
    newNotLikeClause(String attributeName, Object value, String escapeChar)
    
    newBetweenClause(String attributeName, Object low, Object high)
    newNotBetweenClause(String attributeName, Object low, Object high)
    
    newOrClause(Collection clauses)
    newAndClause(Collection clauses)
    
    newCustomClause(String query, Collection args)
    
    newAscOrderClause(String field)
    newDescOrderClause(String field)