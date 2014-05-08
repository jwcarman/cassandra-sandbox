package com.carmanconsulting.cassandra;

import com.carmanconsulting.cassandra.entity.Entity;
import com.datastax.driver.core.querybuilder.Select;
import org.cassandraunit.spring.CassandraUnitDependencyInjectionTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/cassandra-test.xml")
@TestExecutionListeners({CassandraUnitDependencyInjectionTestExecutionListener.class, DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
@EmbeddedCassandra
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class CassandraTestCase {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    @Autowired
    protected CassandraTemplate template;

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------


    protected <T extends Entity> void assertEntityExists(T entity) {
        T result = findEntity(entity);
        assertEquals(entity, result);
    }

    protected <T extends Entity> void assertEntityDoesNotExist(T entity) {
        T result = findEntity(entity);
        assertNull(result);
    }

    @SuppressWarnings("unchecked")
    protected <T extends Entity> T findEntity(T entity) {
        Select selection = select().all().from(tableName(entity));
        selection.where(eq("id", entity.getId()));
        return (T) template.selectOne(selection, entity.getClass());
    }

    private <T> String tableName(T entity) {
        final Table table = entity.getClass().getAnnotation(Table.class);
        return table == null ? entity.getClass().getSimpleName() : table.value();
    }
}
