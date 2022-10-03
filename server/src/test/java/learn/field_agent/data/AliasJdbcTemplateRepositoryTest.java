package learn.field_agent.data;

import learn.field_agent.models.Alias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AliasJdbcTemplateRepositoryTest {



    @Autowired
    AliasJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }


    @Test
    void shouldFindAllAgentAliases() {
        Alias alias = makeAlias();
        List<Alias> aliases = repository.findAllAliasByAgentId(alias.getAgentId());
        assertNotNull(aliases);

        assertTrue(aliases.size() <= 2);
    }
    @Test
    void shouldAdd() {
        Alias alias = makeAlias();
        Alias actual = repository.add(alias);
        assertNotNull(actual);
        assertEquals(1, actual.getAliasId());
    }

    @Test
    void shouldUpdate() {
        Alias alias = makeAlias();
        alias.setAliasId(1);
        Alias actual = repository.add(alias);
        assertTrue(repository.update(actual));

        alias.setAliasId(10);
        assertFalse(repository.update(alias));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(1));
    }


    Alias makeAlias() {
        Alias alias = new Alias();
        alias.setName("Test Testington");
        alias.setPersona("The Tester");
        alias.setAgentId(1);
        return alias;


    }

}
