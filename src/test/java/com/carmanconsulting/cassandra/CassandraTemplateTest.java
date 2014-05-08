package com.carmanconsulting.cassandra;

import com.carmanconsulting.cassandra.entity.Person;
import org.junit.Test;
import static org.junit.Assert.*;

public class CassandraTemplateTest extends CassandraTestCase {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    public void testPojoInsert() {
        Person p = newPerson();
        template.insert(p);
        assertEntityExists(p);
    }

    @Test
    public void testPojoDelete() {
        Person p = newPerson();
        template.insert(p);
        template.delete(p);
        assertEntityDoesNotExist(p);
    }

    private Person newPerson() {
        Person p = new Person();
        p.setFirstName("Hello");
        p.setLastName("Cassandra");
        return p;
    }

    @Test
    public void testPojoUpdate() {
        Person p = newPerson();
        template.insert(p);
        p.setLastName("CassandraXXX");
        template.update(p);
        Person result = findEntity(p);
        assertEquals("CassandraXXX", result.getLastName());
    }


}
