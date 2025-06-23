package digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class PopulationByDemographic {
    @JsonProperty("demographicVariable")
    private String demographicVariable;

    @JsonProperty("populationDistribution")
    @Valid
    private Object populationDistribution;
}
