package learn.field_agent.domain;

import ch.qos.logback.core.joran.conditional.IfAction;
import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.AgencyAgent;
import learn.field_agent.models.SecurityClearance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityClearanceService {

    private final SecurityClearanceRepository repository;

    public SecurityClearanceService(SecurityClearanceRepository repository) {
        this.repository = repository;
    }

    public List<SecurityClearance> findAll() {
        return repository.findAll();
    }

    public SecurityClearance findById(int securityClearanceId) {
        return repository.findById(securityClearanceId);
    }

    public Result<SecurityClearance> add(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);

        if (!result.isSuccess()) {
            return result;
        }
        if (securityClearance!= null && securityClearance.getSecurityClearanceId() > 0) {
            result.addMessage("securityClearanceId must be set for `add` operation", ResultType.INVALID);
            return result;
        }


        securityClearance = repository.add(securityClearance);
        result.setPayload(securityClearance);
        return result;
    }

    public Result<SecurityClearance> update(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);
        if (!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() <= 0) {
            result.addMessage("securityClearanceId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(securityClearance)) {
            String msg = String.format("securityClearanceId: %s, not found", securityClearance.getSecurityClearanceId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    public Result<SecurityClearance> deleteById(int securityClearanceId) {
        Result<SecurityClearance> result = new Result<>();

        if (repository.countClearancesInAgencyAgent(securityClearanceId) >= 1) {
            String msg = String.format("securityClearanceId: %s, is in use", securityClearanceId);
            result.addMessage(msg, ResultType.INVALID);
            return result;
        }

        if (!repository.deleteById(securityClearanceId)) {
            String msg = String.format("securityClearanceId: %s, not found", securityClearanceId);
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        return result;
    }

    private Result<SecurityClearance> validate(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = new Result<>();

        if (securityClearance == null) {
            result.addMessage("security clearance cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(securityClearance.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
            return result;
        }


        List<SecurityClearance> securityClearances = repository.findAll();

        for (SecurityClearance existingSecClear : securityClearances) {
            if (securityClearance.getName().equals(existingSecClear.getName())) {
                result.addMessage("name cannot be duplicated", ResultType.INVALID);
                return result;
            }
        }

        return result;
    }

}
