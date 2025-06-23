package digit.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.service.CensusService;
import digit.util.ResponseInfoFactory;
import digit.web.models.*;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/census")
public class V1ApiController {

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private CensusService censusService;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    public V1ApiController(ObjectMapper objectMapper, HttpServletRequest request, CensusService censusService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.censusService = censusService;
    }

    @RequestMapping(value="/_create", method = RequestMethod.POST)
    public ResponseEntity<CensusResponse> create(@ApiParam(value = "Census create request" ,required=true )  @Valid @RequestBody CensusRequest censusRequest){
        List<Census> censusList = censusService.createCensus(censusRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(censusRequest.getRequestInfo(), true);
        CensusResponse response = CensusResponse.builder().census(censusList).responseInfo(responseInfo).build();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value="/_search", method = RequestMethod.POST)
    public ResponseEntity<CensusResponse> search(@RequestBody RequestInfoWrapper requestInfoWrapper, @Valid @ModelAttribute CensusSearchCriteria criteria){
        List<Census> censusList = censusService.searchCensus(criteria);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true);
        CensusResponse response = CensusResponse.builder().census(censusList).responseInfo(responseInfo).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/_update", method = RequestMethod.POST)
    public ResponseEntity<CensusResponse> update(@ApiParam(value = "Census update request" ,required=true )  @Valid @RequestBody CensusRequest censusRequest){
        List<Census> censusList = censusService.updateCensus(censusRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(censusRequest.getRequestInfo(), true);
        CensusResponse response = CensusResponse.builder().census(censusList).responseInfo(responseInfo).build();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
