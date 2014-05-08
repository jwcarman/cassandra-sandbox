package com.carmanconsulting.cassandra;

import com.carmanconsulting.cassandra.entity.Person;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

public class CassandraTemplateTest extends CassandraTestCase {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    public void testPojoMapping() {
        Person p = new Person();
        p.setFirstName("Hello");
        p.setLastName("Cassandra");
        template.insert(p);
        Select select = QueryBuilder.select().all().from("Person");
        final List<Person> people = template.select(select, Person.class);
        assertEquals(1, people.size());
        assertEquals(p, people.get(0));
    }
}
