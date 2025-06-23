CREATE TABLE eg_census (
    id VARCHAR(64) PRIMARY KEY,
    tenantId VARCHAR(64) NOT NULL,
    hierarchyType VARCHAR(64) NOT NULL,
    boundaryCode VARCHAR(64) NOT NULL,
    type VARCHAR(32) NOT NULL,
    totalPopulation BIGINT NOT NULL,
    populationByDemographics JSONB,
    effectiveFrom BIGINT,
    effectiveTo BIGINT,
    source VARCHAR(64) NOT NULL,
    additionalDetails JSONB,
    createdBy VARCHAR(64),
    lastModifiedBy VARCHAR(64),
    createdTime BIGINT,
    lastModifiedTime BIGINT
);
CREATE UNIQUE INDEX idx_eg_census_id ON eg_census(id);
