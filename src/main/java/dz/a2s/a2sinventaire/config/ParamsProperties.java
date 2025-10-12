package dz.a2s.a2sinventaire.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "params")
public class ParamsProperties {

    private Map<String, Integer> authorizations;

    public Map<String, Integer> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(Map<String, Integer> authorizations) {
        this.authorizations = authorizations;
    }

}
