package digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class Census {
    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("hierarchyType")
    private String hierarchyType;

    @JsonProperty("boundaryCode")
    private String boundaryCode;

    @JsonProperty("type")
    private String type;

    @JsonProperty("totalPopulation")
    private Long totalPopulation;

    @JsonProperty("populationByDemographics")
    @Valid
    private List<PopulationByDemographic> populationByDemographics;

    @JsonProperty("effectiveFrom")
    private Long effectiveFrom;

    @JsonProperty("effectiveTo")
    private Long effectiveTo;

    @JsonProperty("source")
    private String source;

    @JsonProperty("additionalDetails")
    private Object additionalDetails;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails;
}
