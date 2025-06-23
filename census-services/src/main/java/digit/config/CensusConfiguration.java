package digit.config;

import lombok.*;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Import({TracerConfiguration.class})
public class CensusConfiguration {

    @Value("${app.timezone}")
    private String timeZone;

    @Value("${egov.idgen.host}")
    private String idGenHost;

    @Value("${egov.idgen.path}")
    private String idGenPath;

    @Value("${egov.mdms.host}")
    private String mdmsHost;

    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }
}
