package learn.field_agent.data;

import learn.field_agent.models.Alias;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AliasRepository {
    List<Alias> findAllAliasByAgentId(int agentId);

    Alias add(Alias alias);

    boolean update(Alias alias);

    boolean deleteById(int aliasId);

}
