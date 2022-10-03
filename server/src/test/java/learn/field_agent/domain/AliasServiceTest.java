package learn.field_agent.domain;

import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AliasServiceTest {

    @Autowired
    AliasService service;

    @MockBean
    AliasRepository repository;

    @Test
    void shouldNotAddWhenInvalid() {
        Alias alias = makeAlias();
        Result<Alias> result = service.add(alias);
        assertEquals(ResultType.INVALID, result.getType());

        alias.setAliasId(0);
        alias.setName(null);
        result = service.add(alias);
        assertEquals(ResultType.INVALID, result.getType());

    }

    @Test
    void shouldNotAddWhenDuplicateNameNullOrBlankPersona() {
        Alias alias = makeAlias();
        alias.setName("Duplicate");
        when(repository.add(alias)).thenReturn(alias);

        Alias dupeAlias = makeAlias();
        dupeAlias.setName("Duplicate");
        dupeAlias.setPersona(null);
        Result<Alias> result = service.add(dupeAlias);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

        dupeAlias.setName("Duplicate");
        dupeAlias.setPersona("");
        result = service.add(dupeAlias);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

    }

    @Test
    void shouldAdd(){
        Alias alias = makeAlias();
        alias.setAliasId(0);

        when(repository.add(alias)).thenReturn(alias);
        Alias mockAlias = makeAlias();
        mockAlias.setAliasId(0);
        mockAlias.setPersona("Hawkeye");


        Result<Alias> actual = service.add(alias);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(alias, actual.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        Alias alias = makeAlias();
        alias.setAliasId(0);
        Result<Alias> actual = service.update(alias);
        assertEquals(ResultType.INVALID,actual.getType());

        alias = makeAlias();
        alias.setAliasId(10);
        actual = service.update(alias);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Alias alias = makeAlias();
        alias.setAliasId(1);

        when(repository.update(alias)).thenReturn(true);

        Result<Alias> actual = service.update(alias);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }



    Alias makeAlias() {
        Alias alias = new Alias();
        alias.setAliasId(1);
        alias.setName("Hazel Sauven");
        alias.setPersona("Black Widow");
        alias.setAgentId(1);
        return alias;
    }


}
