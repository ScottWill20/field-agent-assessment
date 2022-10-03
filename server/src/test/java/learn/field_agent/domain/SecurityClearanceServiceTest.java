package learn.field_agent.domain;

import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SecurityClearanceServiceTest {

    @Autowired
    SecurityClearanceService service;

    @MockBean
    SecurityClearanceRepository repository;

    @Test
    void shouldFindSecret() {
        SecurityClearance expected = makeSecurityClearance();
        when(repository.findById(1)).thenReturn(expected);
        SecurityClearance actual = service.findById(1);
        assertEquals(expected, actual);
    }


    @Test
    void shouldNotAddNull() {
        SecurityClearance securityClearance = null;

        Result<SecurityClearance> result = service.add(securityClearance);

        assertFalse(result.isSuccess());
        assertEquals(1,result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("cannot be null"));
    }

    @Test
    void shouldNotAddWhenDuplicate() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setName("Duplicate");
        when(repository.add(securityClearance)).thenReturn(securityClearance);

        SecurityClearance mockSecClear = makeSecurityClearance();
        mockSecClear.setName("Duplicate");
        Result<SecurityClearance> result = service.add(mockSecClear);

        assertEquals(ResultType.INVALID, result.getType());

        assertNull(result.getPayload());
    }

    @Test
    void shouldAdd() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(0);

        when(repository.add(securityClearance)).thenReturn(securityClearance);
        SecurityClearance SecClear = makeSecurityClearance();
        SecClear.setSecurityClearanceId(0);
        SecClear.setName("Test");

        Result<SecurityClearance> actual = service.add(securityClearance);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(securityClearance, actual.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(0);
        Result<SecurityClearance> actual = service.update(securityClearance);
        assertEquals(ResultType.INVALID, actual.getType());

        securityClearance = makeSecurityClearance();
        securityClearance.setName("Test Updated");
        actual = service.update(securityClearance);
        assertEquals(ResultType.NOT_FOUND, actual.getType());

    }



    SecurityClearance makeSecurityClearance() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setSecurityClearanceId(1);
        securityClearance.setName("Secret");
        return securityClearance;
    }
}
