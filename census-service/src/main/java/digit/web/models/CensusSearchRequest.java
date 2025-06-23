package digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

import jakarta.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CensusSearchRequest {
    @JsonProperty("RequestInfo")
    @Valid
    private RequestInfo requestInfo;

    @JsonProperty("CensusSearchCriteria")
    @Valid
    private CensusSearchCriteria censusSearchCriteria;
}
