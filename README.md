# springdatajpa
Spring data jpa with soft delete, page and inheritance.type=joined


Hello everyone,

Page does not load if used InheritanceType as JOINED table in Spring Data JPA.
Here is a simple example (http://localhost:8080/my-rest/listA) for the issue
https://stackoverflow.com/questions/62826016/spring-data-jpa-page-does-not-work-when-the-entity-has-a-soft-delete-and-is-in

Exception:

2020-07-14 16:19:32.099  INFO 26264 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 9 ms
2020-07-14 16:19:32.383  WARN 26264 --- [nio-8080-exec-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 42122, SQLState: 42S22
2020-07-14 16:19:32.383 ERROR 26264 --- [nio-8080-exec-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : Column "INHERITEDC0_1_.DELETED" not found; SQL statement:
select count(inheritedc0_.id) as col_0_0_ from inherited_classa inheritedc0_ where ( inheritedc0_1_.DELETED = 0) [42122-199]
2020-07-14 16:19:32.398 ERROR 26264 --- [nio-8080-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.dao.InvalidDataAccessResourceUsageException: could not prepare statement; SQL [

select count(inheritedc0_.id) as col_0_0_ 
from inherited_classa inheritedc0_ 
where ( inheritedc0_1_.DELETED = 0)

]; nested exception is org.hibernate.exception.SQLGrammarException: could not prepare statement] with root cause

org.h2.jdbc.JdbcSQLSyntaxErrorException: Column "INHERITEDC0_1_.DELETED" not found; SQL statement:
select count(inheritedc0_.id) as col_0_0_ from inherited_classa inheritedc0_ where ( inheritedc0_1_.DELETED = 0) [42122-199]


