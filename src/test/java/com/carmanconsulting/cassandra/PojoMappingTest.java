package com.carmanconsulting.cassandra;

import com.carmanconsulting.cassandra.entity.Person;
import org.junit.Test;
import static org.junit.Assert.*;

public class PojoMappingTest extends CassandraTestCase {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    public void testDelete() {
        Person p = newPerson();
        template.insert(p);
        template.delete(p);
        assertEntityDoesNotExist(p);
    }

    @Test
    public void testInsert() {
        Person p = newPerson();
        template.insert(p);
        assertEntityExists(p);
    }

    private Person newPerson() {
        Person p = new Person();
        p.setFirstName("Hello");
        p.setLastName("Cassandra");
        return p;
    }

    @Test
    public void testUpdate() {
        Person p = newPerson();
        template.insert(p);
        p.setLastName("CassandraXXX");
        template.update(p);
        Person result = findEntity(p);
        assertEquals("CassandraXXX", result.getLastName());
    }
}
