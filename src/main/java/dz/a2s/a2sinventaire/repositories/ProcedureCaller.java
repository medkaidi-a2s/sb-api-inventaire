package dz.a2s.a2sinventaire.repositories;

import dz.a2s.a2sinventaire.dto.auth.ParamMeta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ProcedureCaller {

    @PersistenceContext
    private EntityManager entityManager;

    public Map<String, Object> callProcedure(
            String procedureName,
            List<ParamMeta> params
    ) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(procedureName);

        Map<String, Object> result = new HashMap<>();

        if (params != null) {
            params.forEach((item) -> query.registerStoredProcedureParameter(item.getName(), item.getType(), item.getMode()));

            params.forEach((item) -> {
                if (item.getMode() == ParameterMode.IN) query.setParameter(item.getName(), item.getValue());
            });

            query.execute();

            params.forEach((item) -> {
                if (item.getMode() == ParameterMode.OUT) result.put(item.getName(), query.getOutputParameterValue(item.getName()));
            });
        }

        return result;
    }
}
