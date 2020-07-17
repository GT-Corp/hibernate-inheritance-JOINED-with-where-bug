
This bug is related to : https://hibernate.atlassian.net/browse/HHH-13712?jql=text%20~%20%22Inheritance%20count%20where%22

# Whats the issue?
If you run the http://localhost:8080/subobj, the select query generates fine. But the count query doesn't contain JOIN statement so making the ' where ( subobject0_1_.DELETED = 0)' clause invalid.
```sql
Hibernate: select subobject0_.id as id1_1_, subobject0_1_.deleted as deleted2_1_, subobject0_.age as age1_0_, subobject0_.name as name2_0_ from sub_object subobject0_ inner join super subobject0_1_ on subobject0_.id=subobject0_1_.id where ( subobject0_1_.DELETED = 0) limit ?

Hibernate: select count(subobject0_.id) as col_0_0_ from sub_object subobject0_ where ( subobject0_1_.DELETED = 0)
```

Which results in following exception:
```
org.h2.jdbc.JdbcSQLSyntaxErrorException: Column "SUBOBJECT0_1_.DELETED" not found; SQL statement:
select count(subobject0_.id) as col_0_0_ from sub_object subobject0_ where ( subobject0_1_.DELETED = 0) [42122-200]
```

# How to reproduce:
Run the app and open  http://localhost:8080/subobj on your browser


# Notes:

- It appears the issue is only with   InheritanceType.JOINED. Other types works fine (see ``bug.entity.Super.java``)

- No issue when hibernate.version = 5.4.4.Final

- Stackoverflow question: https://stackoverflow.com/questions/62826016/spring-data-jpa-page-does-not-work-when-the-entity-has-a-soft-delete-and-is-in

- Related JIRA issue https://hibernate.atlassian.net/browse/HHH-13712?jql=text%20~%20%22Inheritance%20count%20where%22. Looks like its not fixed for JOINED with @Where

- AST for both issues
 
 AST for select query
 ```
 2020-07-17 13:46:53.856 DEBUG 15860 --- [nio-8080-exec-1] o.h.h.internal.ast.QueryTranslatorImpl   : --- SQL AST ---
  \-[SELECT] QueryNode: 'SELECT'  querySpaces (super,sub_object)
     +-[SELECT_CLAUSE] SelectClause: '{select clause}'
     |  +-[ALIAS_REF] IdentNode: 'subobject0_.id as id1_1_' {alias=generatedAlias0, className=bug.entity.SubObject, tableAlias=subobject0_}
     |  \-[SQL_TOKEN] SqlFragment: 'subobject0_1_.deleted as deleted2_1_, subobject0_.age as age1_0_, subobject0_.name as name2_0_'
     +-[FROM] FromClause: 'from' FromClause{level=1, fromElementCounter=1, fromElements=1, fromElementByClassAlias=[generatedAlias0], fromElementByTableAlias=[subobject0_], fromElementsByPath=[], collectionJoinFromElementsByPath=[], impliedElements=[]}
     |  \-[FROM_FRAGMENT] FromElement: 'sub_object subobject0_ inner join super subobject0_1_ on subobject0_.id=subobject0_1_.id' FromElement{explicit,not a collection join,not a fetch join,fetch non-lazy properties,classAlias=generatedAlias0,role=null,tableName=sub_object,tableAlias=subobject0_,origin=null,columns={,className=bug.entity.SubObject}}
     \-[WHERE] SqlNode: 'WHERE'
        \-[FILTERS] SqlNode: '{filter conditions}'
           \-[SQL_TOKEN] SqlFragment: '( subobject0_1_.DELETED = 0)'
```           

AST for count query

```
2020-07-17 13:46:53.895 DEBUG 15860 --- [nio-8080-exec-1] o.h.h.internal.ast.QueryTranslatorImpl   : --- SQL AST ---
 \-[SELECT] QueryNode: 'SELECT'  querySpaces (super,sub_object)
    +-[SELECT_CLAUSE] SelectClause: '{select clause}'
    |  +-[COUNT] CountNode: 'count'
    |  |  \-[ALIAS_REF] IdentNode: 'subobject0_.id' {alias=generatedAlias0, className=bug.entity.SubObject, tableAlias=subobject0_}
    |  \-[SELECT_COLUMNS] SqlNode: ' as col_0_0_'
    +-[FROM] FromClause: 'from' FromClause{level=1, fromElementCounter=1, fromElements=1, fromElementByClassAlias=[generatedAlias0], fromElementByTableAlias=[subobject0_], fromElementsByPath=[], collectionJoinFromElementsByPath=[], impliedElements=[]}
    |  \-[FROM_FRAGMENT] FromElement: 'sub_object subobject0_' FromElement{explicit,not a collection join,not a fetch join,fetch non-lazy properties,classAlias=generatedAlias0,role=null,tableName=sub_object,tableAlias=subobject0_,origin=null,columns={,className=bug.entity.SubObject}}
    \-[WHERE] SqlNode: 'WHERE'
       \-[FILTERS] SqlNode: '{filter conditions}'
          \-[SQL_TOKEN] SqlFragment: '( subobject0_1_.DELETED = 0)'           
```