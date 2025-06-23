CREATE TABLE eg_cs_census(
  id character varying(64) PRIMARY KEY,
  tenantId character varying(100) NOT NULL,
  hierarchyType character varying(64) NOT NULL,
  boundaryCode character varying(64) NOT NULL,
  type character varying(32) NOT NULL,
  totalPopulation bigint NOT NULL,
  populationByDemographics jsonb,
  effectiveFrom bigint,
  effectiveTo bigint,
  source character varying(128) NOT NULL,
  additionalDetails jsonb,
  createdBy character varying(64),
  lastModifiedBy character varying(64),
  createdTime bigint,
  lastModifiedTime bigint
);
