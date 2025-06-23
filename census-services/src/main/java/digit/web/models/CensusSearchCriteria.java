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
public class CensusSearchCriteria {
    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("areaCodes")
    @Valid
    private List<String> areaCodes;
}
