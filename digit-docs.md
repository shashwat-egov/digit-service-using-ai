# DIGIT Backend Developer Guide

## Introduction

Backend Developer Guide
DIGIT backend development guide
This guide provides detailed steps for developers to create a new microservice on top
of DIGIT. At the end of this guide, you will be able to run the sample module provided
(code provided), test it out locally and also deploy it using CI/CD to your DIGIT
environment.
Steps to create a microservice:
• Set up your development environment
• Develop the registries, services, and APIs for a voter registration module that were
described in the Design Guide
• Integrate with an existing DIGIT environment and re-use a lot of the common
services using Kubernetes port forwarding
• Test the new module and debug
• Build and deploy the new service in the DIGIT environment
The guide is divided into multiple sections for ease of use. Click on the section cards
below to follow the development steps.

## Section 1: Create Project

Learn all about the development pre-
The ﬁrst step is to create and conﬁgure a
requisites, design inputs, and environment
spring boot project
setup

## Section 3: Integrate with other DIGIT

The next step is to integrate the Persister
services
service and Kafka to enable read/write
Steps on how to integrate with other key
from the DB
DIGIT services

## Section 5: Advanced Integrations

Learn how to integrate the billing and
Learn how to integrate advanced services
payment services to the module
to the built module

## Section 7: Build & Deploy Instructions

Test run the built application in the local
Deploy and run the modules
environment
Access the sample module here . Download and run this in the local environment.

## Section 0: Prep

This section provides the complete details of the system pre-requisites, prepping and
setting up the system for development.
Follow the steps in the docs resources below:
Development Pre-requisites
Design Inputs
Development Environment Setup

Development Pre-requisites
Checklist
Before starting development on top of DIGIT make sure:
To check out the Development Pre-requisites Training Resources page to learn how
to deploy the required tools.
DIGIT code from DIGIT-OSS, egov-mdms-data, conﬁgs etc.. has been forked from
GitHub under your organization’s umbrella account.
You have access to the forked repositories from your GitHub user account. Note
that the user account is different from the organization's account.
Access a DIGIT environment - FQDN, Access keys etc..The DIGIT environment can
be a single VM setup for a development environment. Please refer to these docs
for the installation of DIGIT.
Install DIGIT in a development environment (Single VM setup) by following the
quick setup guide .
It is recommended to deploy the following services in the development
environment -
• User
• MDMS
• Localization
• Id-Gen
• URL-shortener
• Workﬂow
Some of the above services can also be port-forwarded using Kubernetes to bypass
user authentication.
Make sure the following DIGIT services are running in the local environment:
• Persister
• Indexer (optional)

• PDF Service
CD/CI has been set up for DIGIT. This is a pre-requisite to deploying new DIGIT
modules. CD/CI processes will be used to build and deploy the new module that
will be developed.
Design phase artefacts
In addition, knowledge of the following technologies is required for developing on
DIGIT: (Refer to the DIGIT Pre-requisites tutorials for help with installing and
deploying these tools)
• Java/J2EE
• Spring Boot
• REST APIs and related concepts like path parameters, headers, JSON etc.
• Git
• PostgreSQL
• Kafka

Design Inputs
Artefacts required before beginning the development phase
The outputs of the design phase are the inputs to the development phase.
The docs below provide the steps and the resources required to build and design the
module.
High Level Design
Low Level Design

High Level Design
Available design artifacts
API Specs
Find below the Open API speciﬁcations deﬁned for this guide:
Birth Registration
Process Workflow Diagram
Basic workﬂow/swimlane diagram
Registries
As a part of this guide, we are going to build a single birth registry. We will re-use the
user registry from the DIGIT core. This will capture the mother and father details and
store them in the user registry. The baby's details will remain in the birth registry.
Services

A single birth service will manage the registry.
System Architecture Diagram
Images in red show the new birth registration service modules

Low Level Design
This page describes all the low level design artifacts available for the service
DB Schema Diagram
The DB tables provide the registry maps as given below:
Table Name
Description
eg_bt_registration
This table holds the baby's information
eg_bt_address
This table holds the applicant address who
applied for the birth registration.

Development Environment Setup
Overview
Follow the steps outlined on this page to setup the DIGIT development environment.
Steps
To setup the DIGIT development environment -
1. Run the Kafka and PostgreSQL on the development machine and re-use other
services from the DIGIT development environment. The following tools are required
for development:
• Install Git
Git for windows
Git for Linux
• Install JDK17
JDK17 for windows
JDK17 for Linux
• Install IDE - To create SpringBoot/Java applications it is recommended to use
IntelliJ IDE. IntelliJ can be downloaded from the following links -
IntelliJ for windows
IntelliJ for Linux
• Install the Lombok plugins for IntelliJ as we use Lombok annotations in this
module.
• Install Kafka (version 3.2.0 which is the latest version) - To install and run
Kafka locally, follow the following links -
Kafka for windows
Kafka for Linux
• Install Postman - To install Postman, follow the following links -

Postman for windows
Postman for Linux
• Install Kubectl - Kubectl is the tool that we use to interact with services
deployed on our sandbox environment -
kubectl for windows
https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/
• Install aws-iam-authenticator - (if the DIGIT development environment is in
AWS) - https://docs.aws.amazon.com/eks/latest/userguide/install-aws-iam-
authenticator.html
• Install PostgreSQL v14 locally
2. Add conﬁguration - Post installation of Kubectl, add the following conﬁguration in
the Kubernetes conﬁg ﬁle to enable access to resources in our sandbox
environment. Kubernetes conﬁg ﬁle is available in the user's home directory (which
varies from OS to OS).
For example, on a Mac, it is available at: /Users/xyz/.kube/config
Once the conﬁg ﬁle is created, add the following content to it.
Note: Replace the placeholder keys and tokens in the snippet below with your AWS-
speciﬁc keys. Contact your system administrator for help with this.

apiVersion: v1
clusters:
- cluster:
certificate-authority-data:
LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUN5RENDQWJDZ0F3SUJBZ0lCQUR
BTkJna3Foa2lHOXcwQkFRc0ZBREFWTVJNd0VRWURWUVFERXdwcmRXSmwKY201bGRHVn
pNQjRYRFRJd01EVXhNekV6TlRReE5sb1hEVE13TURVeE1URXpOVFF4Tmxvd0ZURVRNQ
kVHQTFVRQpBeE1LYTNWaVpYSnVaWFJsY3pDQ0FTSXdEUVlKS29aSWh2Y05BUUVCQlFB
RGdnRVBBRENDQVFvQ2dnRUJBTkJyClN6aHJjdDNORE1VZVF5TENTYWhwbEgyajJ1bkd
YSWk1QThJZjF6OTgwNEZpSjZ6OS9qUHVpY3FjaTB1VURJQnUKS3hjdVFJRkozMG1MRW
g3RGNiQlh2dDRnUlppZWtlZzVZNGxDT2NlTWZFZkFHY01KdDE1RVVCUFVzdlYyclRMc
Qp6a0ovRzVRUUFXMmhwREJLaFBoblZJTktYN1YzOU9tMUtuTklTbllPWERsZ1g3dW9W
a3I1OFhzREFHWEVsdC9uClpyc3laM2pkMWplWS8rMXlQQzlxbkorT0QwZlRQVGdCV1h
MQlFwMHZKdHVzNE1JV2JLdkhlcUZ5eWtGd2V5MmoKSzk5eU1Yb0oraUpCaFJvWGllU3
ZrNnFYdG44S2l4bVJtOXZPQk1hcWpuNkwwTjc3UWNCNjVRaHNKb0tWKzBiMQp5VVpJT
HVTWWVTY0Yra3h6TzFVQ0F3RUFBYU1qTUNFd0RnWURWUjBQQVFIL0JBUURBZ0trTUE4
R0ExVWRFd0VCCi93UUZNQU1CQWY4d0RRWUpLb1pJaHZjTkFRRUxCUUFEZ2dFQkFNdnF
3THl6d2RUL05OWlkvanNzb0lmQmIyNDgKZ3oxSHRuSXJ4UGhaY3RrYjBSMExxeTYzRF
ZBMFNSN0MrWk90aTNNd3BHMkFSVHVzdG1vYm9HV3poUXlXRk16awpVMVNIZSt6S3poe
GcweUpjUjliZnlxM1ZtQVVCZlQyTVV5cVl2OVg0aWxpbmV0SURQaFBuWnlPMERQTHJI
TGoyCkcxZy8vWmZYbmFCT2k3dlZLSXFXUUR6RlltWGkwME9vOEVoalVyMU5sQ3FISnF
1dUo3TlRWQWk1cXA0Qm1xWU8KUTBrbTVxTVVHbG9ZdkNmN1lHQWREWTVnWGg4dzFVMV
daNWNub0Q4WWc3aEtlSjRMRzRram1adlNucGZrS3VxNApiVDdUSjEwUEZlWFJkek8xa
2FkQ3VMQSttUlg3OEd5WEw0UTZnOFdPUlhOVDYzdXN3MnlpMXVVN1lMTT0KLS0tLS1F
TkQgQ0VSVElGSUNBVEUtLS0tLQo=
server: https://3201E325058272AA0990C04346DA6E82.yl4.ap-south-
1.eks.amazonaws.com
name: eks_egov-dev
contexts:
- context:
cluster: eks_egov-dev
namespace: egov
user: eks_egov-dev
name: dev
current-context: dev
kind: Config
preferences: {}
users:
- name: eks_egov-dev
user:
exec:
apiVersion: client.authentication.k8s.io/v1alpha1
args:
- token
- -i
- egov-dev
command: aws-iam-authenticator
env:

- name: AWS_ACCESS_KEY
value: {AWS_ACCESS_KEY_PLACEHOLDER}
- name: AWS_SECRET_ACCESS_KEY
value: {AWS_SECRET_ACCESS_KEY_PLACEHOLDER}
- name: AWS_REGION
value: {AWS_REGION_PLACEHOLDER}
Once the conﬁguration ﬁle is created, access the pods running in the environment by
typing: kubectl get pods
This lists all the pods in the development environment.
In case you get an error stating “Error: You must be logged in to the server
(Unauthorized)”, add sudo before the command. For example, “sudo kubectl get pods”.
That should resolve the error.

## Section 1: Create Project

This section showcases how to create a basic Spring Boot project using an API spec
and conﬁgure the database.
Follow the steps detailed below:
Step 1: Create Project
Step 2: Create Database
Generate project stub using API specs-
Create database tables
prepare Swagger contracts
Step 3: Conﬁgure Application Properties
Step 4: Import Core Models
Conﬁgure and customize the application
Import dependent services models for
properties
integration
Step 5: Implement Repository Layer
Step 6: Create Validation Layers
Perform business logic and conﬁgure data
Add business data validation logic
stores
Step 7: Implement Service Layer
Step 8: Build Web Layer
Create the service layer required to process
Implement web controller layer to manage
business requests
incoming REST requests

Generate Project Using API Specs
Generate Project Stub
Overview
This page provides detailed steps for generating projects using the given API
speciﬁcations.
Steps
Build A Microservice
1. Prepare Swagger contracts that detail all the APIs that the service is going to
expose for external consumption. eGov uses a customised Swagger Codegen
tool.
2. Download the jar ﬁle and make sure it is available in the classpath. Use the
Swagger Codegen tool
to generate client SDKs using these Swagger contracts.
Refer to the following tutorials to understand the creation of Swagger contracts -
OpenAPI 3.0 Tutorial| Swagger Tutorial For Beginners | Design REST API Using Swagger
Editor
Generate API Skeleton
1. Use the generic command below to create an API skeleton for any Swagger
contract:
java -jar codegen-2.0-SNAPSHOT-jar-with-dependencies.jar
-l -t -u {CONTRACT_PATH } -a project_name -b digit

The following sequence is used to generate the API skeleton using codegen jar:
1. Navigate to the folder where you have downloaded the codegen jar.
2. Execute the following command:
java -jar codegen-2.0-SNAPSHOT-jar-with-dependencies.jar -l -t -u
https://raw.githubusercontent.com/egovernments/Digit-
Core/master/tutorials/backend-developer-guide/btr-services/birth-
registration-api-spec.yaml -a birth-registration -b digit
OR
Download the contract available here and save it in a ﬁle locally. Run the following
command to generate the skeleton code from the contract.
java -jar codegen-2.0-SNAPSHOT-jar-with-dependencies.jar -l -t -u
file:///{ABSOLUTE_PATH_OF_FILE} -a birth-registration -b digit
2. Rename the output folder to birth-registration.
3.
Import it in Eclipse or VS Code.
4. Update the spring-boot-starter-parent to 3.2.2 in pom.xml.
5. Perform a maven update once the spring boot version is updated.
6. Put a slash in front of server.contextPath and add this property to the
application.properties ﬁle which helps request handlers to serve requests -
server.contextPath=/birth
server.servlet.context-path=/birth
8. Add the below external dependencies to pom.xml:

<dependency>
<groupId>org.flywaydb</groupId>
<artifactId>flyway-core</artifactId>
<version>9.22.3</version>
</dependency>
<dependency>
<groupId>org.postgresql</groupId>
<artifactId>postgresql</artifactId>
<version>42.7.1</version>
</dependency>
<dependency>
<groupId>org.egov</groupId>
<artifactId>mdms-client</artifactId>
<version>2.9.0-SNAPSHOT</version>
</dependency>

Create Database
Overview
Once PostgreSQL (v10) has been installed and the basic setup is done, we use Flyway
to create the tables.
Steps
Enable Flyway Migration
1. Conﬁgure the below properties in the application.properties ﬁle to enable ﬂyway
migration:
spring.flyway.url=jdbc:postgresql://localhost:5432/birthregn
spring.flyway.user=birth
spring.flyway.password=birth #REPLACE
spring.flyway.table=public
spring.flyway.baseline-on-migrate=true
spring.flyway.outOfOrder=true
spring.flyway.locations=classpath:/db/migration/main
spring.flyway.enabled=true
2. Add the Flyway SQL scripts in the following structure under
resources/db/migration/main :

3. Add the migration ﬁles to the main folder. Follow the speciﬁed nomenclature while
naming the ﬁle. The ﬁle name should be in the following format:
V[YEAR][MONTH][DAY][HR][MIN][SEC]__modulecode_ …_ddl.sql
Example: V20180920110535__tl_tradelicense_ddl.sql
For this sample service, use the following SQL script to create the required tables.

CREATE TABLE eg_bt_registration(
id character varying(64),
tenantId character varying(64),
applicationNumber character varying(64),
babyFirstName character varying(64),
babyLastName character varying(64),
fatherId character varying(64),
motherId character varying(64),
doctorName character varying(64),
hospitalName character varying(64),
placeOfBirth character varying(64),
timeOfBirth bigint,
createdBy character varying(64),
lastModifiedBy character varying(64),
createdTime bigint,
lastModifiedTime bigint,
CONSTRAINT uk_eg_bt_registration UNIQUE (id)
);
CREATE TABLE eg_bt_address(
id character varying(64),
tenantId character varying(64),
type character varying(64),
address character varying(256),
city character varying(64),
pincode character varying(64),
registrationId character varying(64),
createdBy character varying(64),
lastModifiedBy character varying(64),
createdTime bigint,
lastModifiedTime bigint,
CONSTRAINT uk_eg_bt_address PRIMARY KEY (id),
CONSTRAINT fk_eg_bt_address FOREIGN KEY (registrationId)
REFERENCES eg_bt_registration (id)
ON UPDATE CASCADE
ON DELETE CASCADE
);

Configure Application Properties
Overview
The application.properties ﬁle is already populated with default values. Read on to
learn how to customise and add extra values for your application (if required).
Steps
Kafka topics for the module that need to be added are detailed here.
There are three ways to access services:
a. Run the code locally.
b. Access the service in a DIGIT environment.
c. Access the service locally via port forwarding. This bypasses Zuul's authentication and
authorization.
Wherever the localhost is in the URL, the Kubernetes port forwarding has been set up
from the development environment to the speciﬁed port. In your setup, modify the URLs
for the various services depending on whether you are using them from an environment
or running them locally or accessing them via port-forwarding.
For example, if no port forwarding has been done, you will have to provide the FQDN of
your DIGIT install instead of localhost. Also, without port forwarding, you will have to
update the auth tokens in your .aws proﬁle ﬁle periodically.
Include all the necessary service host URLs and API endpoints in the
"application.properties" ﬁle.
This guide speciﬁcally references the User, Localisation, HRMS, IDGen, MDMS, and
Workﬂow services that are operational within the DIGIT development environment.
Configure Application Properties

1. Add the following properties to the application.properties ﬁle.
#Localization config
egov.localization.host=https://dev.digit.org
egov.localization.workDir.path=/localization/messages/v1
egov.localization.context.path=/localization/messages/v1
egov.localization.search.endpoint=/_search
egov.localization.statelevel=true
#mdms urls
egov.mdms.host=https://dev.digit.org
egov.mdms.search.endpoint=/egov-mdms-service/v1/_search
#hrms urls
egov.hrms.host=https://dev.digit.org
egov.hrms.search.endpoint=/egov-hrms/employees/_search
#User config
#egov.user.host=https://dev.digit.org
egov.user.host=http://localhost:8284/
egov.user.context.path=/user/users
egov.user.create.path=/_createnovalidate
egov.user.search.path=/user/_search
egov.user.update.path=/_updatenovalidate
#Idgen Config
egov.idgen.host=http://localhost:8285/
egov.idgen.path=egov-idgen/id/_generate
#Workflow config
is.workflow.enabled=true
egov.workflow.host=http://localhost:8280
egov.workflow.transition.path=/egov-workflow-v2/egov-
wf/process/_transition
egov.workflow.businessservice.search.path=/egov-workflow-v2/egov-
wf/businessservice/_search
egov.workflow.processinstance.search.path=/egov-workflow-v2/egov-
wf/process/_search
The following properties must be added for conﬁguring the database and Kafka
server. Make sure to use the default values to tune in the Kafka server that can be
overwritten during deployment.
2.
Include the following properties in the "application.properties" ﬁle to conﬁgure the
database and Kafka for development purposes once you have completed adding

the external dependencies to the "pom.xml" ﬁle and reloading the Maven changes.
#DATABASE CONFIGURATION
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/birthregn
spring.datasource.username=birth
spring.datasource.password=birth
Configure Kafka
# KAFKA SERVER CONFIGURATIONS
kafka.config.bootstrap_server_config=localhost:9092
spring.kafka.consumer.value-
deserializer=org.egov.tracer.kafka.deserializer.HashMapDeserializer
spring.kafka.consumer.key-
deserializer=org.apache.kafka.common.serialization.StringDeserializ
er
spring.kafka.consumer.group-id=
{PLACEHOLDER_PUT_KAFKA_CONSUMER_NAME}
spring.kafka.producer.key-
serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-
serializer=org.springframework.kafka.support.serializer.JsonSeriali
zer
spring.kafka.listener.missing-topics-fatal=false
spring.kafka.consumer.properties.spring.json.use.type.headers=false
# KAFKA CONSUMER CONFIGURATIONS
kafka.consumer.config.auto_commit=true
kafka.consumer.config.auto_commit_interval=100
kafka.consumer.config.session_timeout=15000
kafka.consumer.config.auto_offset_reset=earliest
# KAFKA PRODUCER CONFIGURATIONS
kafka.producer.config.retries_config=0
kafka.producer.config.batch_size_config=16384
kafka.producer.config.linger_ms_config=1
kafka.producer.config.buffer_memory_config=33554432
Kafka Topics For Module

Create & Update Kafka Topics For The Module
1. Append Kafka conﬁgurations as per the speciﬁc requirements of the DIGIT
services. Each module may use different conﬁgurations to manage its topics.
# Birth registration Kafka config
btr.kafka.create.topic=save-bt-application
btr.kafka.update.topic=update-bt-application
btr.default.offset=0
btr.default.limit=10
btr.search.max.limit=50

Import Core Models
Overview
The pom.xml typically includes most of the dependencies listed below at project
generation time. Review and ensure that all of these dependencies are present.
Update the pom.xml in case any are missing.
Steps
Models/POJOs of the dependent service can be imported from the digit-core-models
library (work on creating the library is ongoing). These models are used to integrate
with the dependent services.
<dependency>
<groupId>org.egov.services</groupId>
<artifactId>tracer</artifactId>
<version>2.9.0-SNAPSHOT</version>
</dependency>
These are pre-written libraries which contain tracer support, common models like
MDMS, Auth and Auth and the capability to raise custom exceptions.
Once these core models are imported, it is safe to delete the RequestInfo, and
ResponseInfo classes from the models folder and use the ones present in the common
contract that is imported.
Import Core Models
Before starting development, create/update the following classes as given below.
1. Delete the following classes which have been generated from codegen -

-Address
-AuditDetails
-Document
-Error
-ErrorResponse
-RequestInfo
-RequestInfoWrapper
-ResponseInfo
-Role
-User
-Workﬂow
After this import the above classes from egov common-service library as follow-\
import org.egov.common.contract.models.Address;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.contract.request.Role;
import org.egov.common.contract.request.User;
import org.egov.common.contract.workflow.*;
2. Create the BirthApplicationSearchRequest POJO with the following content -

package digit.web.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;
/**
* BirthApplicationSearchCriteria
*/
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BirthApplicationSearchRequest {
@JsonProperty("RequestInfo")
@Valid
private RequestInfo requestInfo = null;
@JsonProperty("BirthApplicationSearchCriteria")
@Valid
private BirthApplicationSearchCriteria
birthApplicationSearchCriteria = null;
}
3. Update BirthApplicationAddress pojo

package digit.web.models;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import org.egov.common.contract.models.Address;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;
/**
* BirthApplicationAddress
*/
@Validated
@jakarta.annotation.Generated(value =
"org.egov.codegen.SpringBootCodegen", date = "2024-05-
03T11:52:56.302336279+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BirthApplicationAddress {
@JsonProperty("id")
@Valid
private String id = null;
@JsonProperty("tenantId")
@NotNull
@Size(min=2,max=64) private String tenantId = null;
@JsonProperty("applicationNumber")
private String applicationNumber = null;
@JsonProperty("applicantAddress")
@Valid
private Address applicantAddress = null;

}
4. Create the BTRConﬁguration class within the conﬁguration package. The
MainConﬁguration class should already exist inside the conﬁg package.

package digit.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import
org.springframework.http.converter.json.MappingJackson2HttpMessage
Converter;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BTRConfiguration {
@Value("${app.timezone}")
private String timeZone;
@PostConstruct
public void initialize() {
TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
}
@Bean
@Autowired
public MappingJackson2HttpMessageConverter
jacksonConverter(ObjectMapper objectMapper) {
MappingJackson2HttpMessageConverter converter = new
MappingJackson2HttpMessageConverter();
converter.setObjectMapper(objectMapper);
return converter;
}
// User Config
@Value("${egov.user.host}")
private String userHost;
@Value("${egov.user.context.path}")

private String userContextPath;
@Value("${egov.user.create.path}")
private String userCreateEndpoint;
@Value("${egov.user.search.path}")
private String userSearchEndpoint;
@Value("${egov.user.update.path}")
private String userUpdateEndpoint;
//Idgen Config
@Value("${egov.idgen.host}")
private String idGenHost;
@Value("${egov.idgen.path}")
private String idGenPath;
//Workflow Config
@Value("${egov.workflow.host}")
private String wfHost;
@Value("${egov.workflow.transition.path}")
private String wfTransitionPath;
@Value("${egov.workflow.businessservice.search.path}")
private String wfBusinessServiceSearchPath;
@Value("${egov.workflow.processinstance.search.path}")
The core models are imported.
private String wfProcessInstanceSearchPath;
@Value("${is.workflow.enabled}")
private Boolean isWorkflowEnabled;
// BTR Variables
@Value("${btr.kafka.create.topic}")
private String createTopic;
@Value("${btr.kafka.update.topic}")
private String updateTopic;
@Value("${btr.default.offset}")
private Integer defaultOffset;
@Value("${btr.default.limit}")
private Integer defaultLimit;

Implement Repository Layer
@Value("${btr.search.max.limit}")
private Integer maxLimit;
Overview
//MDMS
@Value("${egov.mdms.host}")
private String mdmsHost;
Methods in the service layer, upon performing all the business logic, call methods in
the repository layer to persist or lookup data i.e. it interacts with the conﬁgured data
@Value("${egov.mdms.search.endpoint}")
private String mdmsEndPoint;
store. For executing the queries, JdbcTemplate class is used. JdbcTemplate takes care
of the creation and release of resources such as creating and closing the connection
//HRMS
etc. All database operations namely insert, update, search and delete can be
@Value("${egov.hrms.host}")
performed on the database using methods of JdbcTemplate class.
private String hrmsHost;
@Value("${egov.hrms.search.endpoint}")
private String hrmsEndPoint;
Steps
@Value("${egov.url.shortner.host}")
private String urlShortnerHost;
On DIGIT the create and update operations are handled asynchronously.
@Value("${egov.url.shortner.endpoint}")
private String urlShortnerEndpoint;
The persister service listens on the topic to which service applications are pushed for
insertion and updation. Persister then takes care of executing insert and update
@Value("${egov.sms.notification.topic}")
private String smsNotificationTopic;
operations on the database without clogging the application’s threads.
}
The execution of search queries on the database returns applications as per the
search parameters provided by the user.
Implement Repository Layer
1. Create packages - Add the querybuilder and rowmapper packages within the
repository folder.
2. Create a class - by the name of BirthApplicationQueryBuilder in querybuilder
folder and annotate it with @Component annotation.
Insert the following content in BirthApplicationQueryBuilder class -

package digit.repository.querybuilder;
import digit.web.models.BirthApplicationSearchCriteria;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import java.util.List;
@Component
public class BirthApplicationQueryBuilder {
private static final String BASE_BTR_QUERY = " SELECT btr.id as
bid, btr.tenantid as btenantid, btr.applicationnumber as
bapplicationnumber, btr.babyfirstname as bbabyfirstname,
btr.babylastname as bbabylastname, btr.fatherid as bfatherid,
btr.motherid as bmotherid, btr.doctorname as bdoctorname,
btr.hospitalname as bhospitalname, btr.placeofbirth as
bplaceofbirth, btr.timeofbirth as btimeofbirth, btr.createdby as
bcreatedby, btr.lastmodifiedby as blastmodifiedby, btr.createdtime
as bcreatedtime, btr.lastmodifiedtime as blastmodifiedtime, ";
private static final String ADDRESS_SELECT_QUERY = " add.id as
aid, add.tenantid as atenantid, add.type as atype, add.address as
aaddress, add.city as acity, add.pincode as apincode,
add.registrationid as aregistrationid ";
private static final String FROM_TABLES = " FROM
eg_bt_registration btr LEFT JOIN eg_bt_address add ON btr.id =
add.registrationid ";
private final String ORDERBY_CREATEDTIME = " ORDER BY
btr.createdtime DESC ";
public String
getBirthApplicationSearchQuery(BirthApplicationSearchCriteria
criteria, List<Object> preparedStmtList){
StringBuilder query = new StringBuilder(BASE_BTR_QUERY);
query.append(ADDRESS_SELECT_QUERY);
query.append(FROM_TABLES);
if(!ObjectUtils.isEmpty(criteria.getTenantId())){
addClauseIfRequired(query, preparedStmtList);
query.append(" btr.tenantid = ? ");
preparedStmtList.add(criteria.getTenantId());
}
if(!ObjectUtils.isEmpty(criteria.getStatus())){
addClauseIfRequired(query, preparedStmtList);

query.append(" btr.status = ? ");
preparedStmtList.add(criteria.getStatus());
}
5. Create a class - by the name of BirthApplicationRowMapper within the
if(!CollectionUtils.isEmpty(criteria.getIds())){
rowmapper package and annotate it with @Component.
addClauseIfRequired(query, preparedStmtList);
query.append(" btr.id IN (
Add the following content to the class.
").append(createQuery(criteria.getIds())).append(" ) ");
addToPreparedStatement(preparedStmtList,
criteria.getIds());
}
if(!ObjectUtils.isEmpty(criteria.getApplicationNumber())){
addClauseIfRequired(query, preparedStmtList);
query.append(" btr.applicationnumber = ? ");
preparedStmtList.add(criteria.getApplicationNumber());
}
// order birth registration applications based on their
createdtime in latest first manner
query.append(ORDERBY_CREATEDTIME);
return query.toString();
}
private void addClauseIfRequired(StringBuilder query,
List<Object> preparedStmtList){
if(preparedStmtList.isEmpty()){
query.append(" WHERE ");
}else{
query.append(" AND ");
}
}
private String createQuery(List<String> ids) {
StringBuilder builder = new StringBuilder();
int length = ids.size();
for (int i = 0; i < length; i++) {
builder.append(" ?");
if (i != length - 1)
builder.append(",");
}
return builder.toString();
}
private void addToPreparedStatement(List<Object>
preparedStmtList, List<String> ids) {
ids.forEach(id -> {
preparedStmtList.add(id);
});
}

}
}

package digit.repository.rowmapper;
import digit.web.models.*;
import org.egov.common.contract.models.Address;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.User;
import org.egov.common.contract.user.enums.AddressType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
@Component
public class BirthApplicationRowMapper implements
ResultSetExtractor<List<BirthRegistrationApplication>> {
public List<BirthRegistrationApplication> extractData(ResultSet
rs) throws SQLException, DataAccessException {
Map<String,BirthRegistrationApplication>
birthRegistrationApplicationMap = new LinkedHashMap<>();
while (rs.next()){
String uuid = rs.getString("bapplicationnumber");
BirthRegistrationApplication
birthRegistrationApplication =
birthRegistrationApplicationMap.get(uuid);
if(birthRegistrationApplication == null) {
Long lastModifiedTime =
rs.getLong("blastModifiedTime");
if (rs.wasNull()) {
lastModifiedTime = null;
}
User father =
User.builder().uuid(rs.getString("bfatherid")).build();
User mother =
User.builder().uuid(rs.getString("bmotherid")).build();
AuditDetails auditdetails = AuditDetails.builder()
.createdBy(rs.getString("bcreatedBy"))
.createdTime(rs.getLong("bcreatedTime"))
.lastModifiedBy(rs.getString("blastModifiedBy"))

.lastModifiedTime(lastModifiedTime)
.build();
birthRegistrationApplication =
BirthRegistrationApplication.builder()
.applicationNumber(rs.getString("bapplicationnumber"))
.tenantId(rs.getString("btenantid"))
.id(rs.getString("bid"))
.babyFirstName(rs.getString("bbabyfirstname"))
.babyLastName(rs.getString("bbabylastname"))
.father(father)
.mother(mother)
6. Create a class - by the name of BirthRegistrationRepository within the repository
.doctorName(rs.getString("bdoctorname"))
folder and annotate it with @Repository annotation.
.hospitalName(rs.getString("bhospitalname"))
.placeOfBirth(rs.getString("bplaceofbirth"))
Add the following content to the class.
.timeOfBirth(rs.getInt("btimeofbirth"))
.auditDetails(auditdetails)
.build();
}
addChildrenToProperty(rs, birthRegistrationApplication);
birthRegistrationApplicationMap.put(uuid,
birthRegistrationApplication);
}
return new ArrayList<>
(birthRegistrationApplicationMap.values());
}
private void addChildrenToProperty(ResultSet rs,
BirthRegistrationApplication birthRegistrationApplication)
throws SQLException {
addAddressToApplication(rs, birthRegistrationApplication);
}
private void addAddressToApplication(ResultSet rs,
BirthRegistrationApplication birthRegistrationApplication) throws
SQLException {
Address address = Address.builder()
.tenantId(rs.getString("atenantid"))
.address(rs.getString("aaddress"))
.city(rs.getString("acity"))
.pinCode(rs.getString("apincode"))
.build();
BirthApplicationAddress birthApplicationAddress=
BirthApplicationAddress.builder()
.id(rs.getString("aid"))
t
tId(
tSt i (" t
tid"))

.tenantId(rs.getString("atenantid"))
.applicantAddress(address)
package digit.repository;
.build();
import digit.repository.querybuilder.BirthApplicationQueryBuilder;
import digit.repository.rowmapper.BirthApplicationRowMapper;
birthRegistrationApplication.setAddress(birthApplicationAddress);
import digit.web.models.BirthApplicationSearchCriteria;
import digit.web.models.BirthRegistrationApplication;
}
import lombok.extern.slf4j.Slf4j;
}
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Repository
public class BirthRegistrationRepository {
@Autowired
private BirthApplicationQueryBuilder queryBuilder;
@Autowired
private JdbcTemplate jdbcTemplate;
@Autowired
private BirthApplicationRowMapper rowMapper;
public
List<BirthRegistrationApplication>getApplications(BirthApplicationS
earchCriteria searchCriteria){
List<Object> preparedStmtList = new ArrayList<>();
String query =
queryBuilder.getBirthApplicationSearchQuery(searchCriteria,
preparedStmtList);
log.info("Final query: " + query);
return jdbcTemplate.query(query,
preparedStmtList.toArray(), rowMapper);
}
}
The repository layer is implemented.

Create Validation & Enrichment Layers
Overview
Find the steps to create the Validation Layer and Enrichment Layer on DIGIT.
Validation Layer
All business validation logic should be added to this class. For example, verifying the
values against the master data, ensuring non-duplication of data etc.
Steps
Follow the steps below to create the validation layer.
1. Create a package called validators. This ensures the validation logic is separate so
that the code is easy to navigate through and readable.
2. Create a class by the name of BirthApplicationValidator
3. Annotate the class with @Component annotation and insert the following content
in the class -

package digit.validators;
import digit.repository.BirthRegistrationRepository;
import digit.web.models.BirthApplicationSearchCriteria;
import digit.web.models.BirthRegistrationApplication;
import digit.web.models.BirthRegistrationRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
@Component
public class BirthApplicationValidator {
@Autowired
private BirthRegistrationRepository repository;
public void validateBirthApplication(BirthRegistrationRequest
birthRegistrationRequest) {
birthRegistrationRequest.getBirthRegistrationApplications().forEach(appl
ication -> {
if(ObjectUtils.isEmpty(application.getTenantId()))
throw new CustomException("EG_BT_APP_ERR", "tenantId is
mandatory for creating birth registration applications");
});
}
public BirthRegistrationApplication
validateApplicationExistence(BirthRegistrationApplication
birthRegistrationApplication) {
return
repository.getApplications(BirthApplicationSearchCriteria.builder().appl
icationNumber(birthRegistrationApplication.getApplicationNumber()).build
()).get(0);
}
}
NOTE: For the sake of simplicity the above-mentioned validations are implemented.
Required validations will vary on a case-to-case basis.
Enrichment Layer

This layer enriches the request. System-generated values like id, auditDetails etc. are
generated and added to the request. In the case of this module, since the applicant is
the parent of a baby and a child cannot be a user of the system directly, both the
parents' details are captured in the User table. The user ids of the parents are then
enriched in the application.
Steps
Follow the steps below to create the enrichment layer.
1. Create a package under DIGIT by the name of enrichment. This ensures the
enrichment code is separate from business logic so that the codebase is easy to
navigate through and readable.
2. Create a class by the name of BirthApplicationEnrichment
3. Annotate the class with @Component and add the following methods to the class
-

package digit.enrichment;
import digit.service.UserService;
import digit.util.IdgenUtil;
import digit.util.UserUtil;
import digit.web.models.*;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.User;
import org.egov.common.contract.user.UserDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;
@Component
@Slf4j
public class BirthApplicationEnrichment {
@Autowired
private IdgenUtil idgenUtil;
@Autowired
private UserService userService;
@Autowired
private UserUtil userUtils;
public void enrichBirthApplication(BirthRegistrationRequest
birthRegistrationRequest) {
List<String> birthRegistrationIdList =
idgenUtil.getIdList(birthRegistrationRequest.getRequestInfo(),
birthRegistrationRequest.getBirthRegistrationApplications().get(0).getTe
nantId(), "btr.registrationid", "",
birthRegistrationRequest.getBirthRegistrationApplications().size());
Integer index = 0;
for(BirthRegistrationApplication application :
birthRegistrationRequest.getBirthRegistrationApplications()){
// Enrich audit details
AuditDetails auditDetails =
AuditDetails.builder().createdBy(birthRegistrationRequest.getRequestInfo
().getUserInfo().getUuid()).createdTime(System.currentTimeMillis()).last
ModifiedBy(birthRegistrationRequest.getRequestInfo().getUserInfo().getUu
id()).lastModifiedTime(System.currentTimeMillis()).build();
application.setAuditDetails(auditDetails);
// Enrich UUID
application.setId(UUID.randomUUID().toString());

// Enrich application number from IDgen
application.setApplicationNumber(birthRegistrationIdList.get(index++));
// Enrich registration Id
application.getAddress().setApplicationNumber(application.getId());
// Enrich address UUID
application.getAddress().setId(UUID.randomUUID().toString());
}
}
public void
enrichBirthApplicationUponUpdate(BirthRegistrationRequest
birthRegistrationRequest) {
// Enrich lastModifiedTime and lastModifiedBy in case of update
NOTE: For the sake of simplicity the above-mentioned enrichment methods are
birthRegistrationRequest.getBirthRegistrationApplications().get(0).getAu
implemented. Required enrichment will vary on a case-to-case basis.
ditDetails().setLastModifiedTime(System.currentTimeMillis());
birthRegistrationRequest.getBirthRegistrationApplications().get(0).getAu
ditDetails().setLastModifiedBy(birthRegistrationRequest.getRequestInfo()
.getUserInfo().getUuid());
}
public void
enrichFatherApplicantOnSearch(BirthRegistrationApplication application)
{
UserDetailResponse fatherUserResponse =
userService.searchUser(userUtils.getStateLevelTenant(application.getTena
ntId()),application.getFather().getUuid(),null);
User fatherUser = fatherUserResponse.getUser().get(0);
log.info(fatherUser.toString());
User fatherApplicant = User.builder()
.mobileNumber(fatherUser.getMobileNumber())
.id(fatherUser.getId())
.name(fatherUser.getName())
.userName((fatherUser.getUserName()))
.type(fatherUser.getType())
.roles(fatherUser.getRoles())
.uuid(fatherUser.getUuid()).build();
application.setFather(fatherApplicant);
}
public void
i h
h
li
h( i h
i
i
li
i
li
i
)

enrichMotherApplicantOnSearch(BirthRegistrationApplication application)
Implement Service Layer
{
UserDetailResponse motherUserResponse =
userService.searchUser(userUtils.getStateLevelTenant(application.getTena
ntId()),application.getMother().getUuid(),null);
User motherUser = motherUserResponse.getUser().get(0);
Overview
log.info(motherUser.toString());
User motherApplicant = User.builder()
The service layer performs business logic on the RequestData and prepares the
.mobileNumber(motherUser.getMobileNumber())
.id(motherUser.getId())
Response to be returned back to the client.
.name(motherUser.getName())
.userName((motherUser.getUserName()))
.type(motherUser.getType())
Steps
.roles(motherUser.getRoles())
.uuid(motherUser.getUuid()).build();
application.setMother(motherApplicant);
Follow the steps below to create the service layer.
}
}
1. Create a new package called Service.
2. Create a new class in this folder by the name BirthRegistrationService.
3. Annotate the class with @Service annotation.
4. Add the following content to the class -

package digit.service;
import digit.enrichment.BirthApplicationEnrichment;
import digit.kafka.Producer;
import digit.repository.BirthRegistrationRepository;
import digit.validators.BirthApplicationValidator;
import digit.web.models.BirthApplicationSearchCriteria;
import digit.web.models.BirthRegistrationApplication;
import digit.web.models.BirthRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
@Slf4j
public class BirthRegistrationService {
@Autowired
private BirthApplicationValidator validator;
@Autowired
private BirthApplicationEnrichment enrichmentUtil;
@Autowired
private UserService userService;
@Autowired
private WorkflowService workflowService;
@Autowired
private BirthRegistrationRepository birthRegistrationRepository;
@Autowired
private Producer producer;
public List<BirthRegistrationApplication>
registerBtRequest(BirthRegistrationRequest birthRegistrationRequest)
{
// Validate applications
validator.validateBirthApplication(birthRegistrationRequest);

// Enrich applications
enrichmentUtil.enrichBirthApplication(birthRegistrationRequest);
// Enrich/Upsert user in upon birth registration
userService.callUserService(birthRegistrationRequest);
//
// Initiate workflow for the new application
workflowService.updateWorkflowStatus(birthRegistrationRequest);
// Push the application to the topic for persister to listen
and persist
producer.push("save-bt-application",
birthRegistrationRequest);
// Return the response back to user
return
birthRegistrationRequest.getBirthRegistrationApplications();
}
public List<BirthRegistrationApplication>
searchBtApplications(RequestInfo requestInfo,
BirthApplicationSearchCriteria birthApplicationSearchCriteria) {
// Fetch applications from database according to the given
search criteria
List<BirthRegistrationApplication> applications =
birthRegistrationRepository.getApplications(birthApplicationSearchCr
iteria);
// If no applications are found matching the given criteria,
return an empty list
if(CollectionUtils.isEmpty(applications))
return new ArrayList<>();
// Enrich mother and father of applicant objects
applications.forEach(application -> {
NOTE: At this point, your IDE must be showing a lot of errors but do not worry we will add
all dependent layers as we progress through this guide and the errors will go away.
enrichmentUtil.enrichFatherApplicantOnSearch(application);
enrichmentUtil.enrichMotherApplicantOnSearch(application);
});
// Otherwise return the found applications
return applications;
}
bli Bi thR i t ti A li ti

Build The Web Layer
public BirthRegistrationApplication
updateBtApplication(BirthRegistrationRequest
birthRegistrationRequest) {
Implementing the controller layer in Spring
// Validate whether the application that is being requested
for update indeed exists
BirthRegistrationApplication existingApplication =
validator.validateApplicationExistence(birthRegistrationRequest.getB
Overview
irthRegistrationApplications().get(0));
existingApplication.setWorkflow(birthRegistrationRequest.getBirthReg
The web/controller layer handles all the incoming REST requests to a service.
istrationApplications().get(0).getWorkflow());
log.info(existingApplication.toString());
Setup Request Handler In The Controller Layer
birthRegistrationRequest.setBirthRegistrationApplications(Collection
s.singletonList(existingApplication));
Follow the steps below to set up the request handler in the controller layer.
// Enrich application upon update
enrichmentUtil.enrichBirthApplicationUponUpdate(birthRegistrationReq
1. Make a call to the method in the Service Layer and get the response back from it.
uest);
2. Build the responseInfo.
3. Build the ﬁnal response to be returned to the client.
workflowService.updateWorkflowStatus(birthRegistrationRequest);
// Just like create request, update request will be handled
asynchronously by the persister
producer.push("update-bt-application",
birthRegistrationRequest);
return
Sample request handler in controller layer
birthRegistrationRequest.getBirthRegistrationApplications().get(0);
The controller class reﬂects the below content -
}
}

package digit.web.controllers;
import digit.service.BirthRegistrationService;
import digit.util.ResponseInfoFactory;
import digit.web.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
@jakarta.annotation.Generated(value =
"org.egov.codegen.SpringBootCodegen", date = "2024-03-
07T11:10:12.732364039+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("")
public class BirthApiController{
private final ObjectMapper objectMapper;
private final HttpServletRequest request;
private BirthRegistrationService birthRegistrationService;
@Autowired
private ResponseInfoFactory responseInfoFactory;
@Autowired
public BirthApiController(ObjectMapper objectMapper,
HttpServletRequest request, BirthRegistrationService
birthRegistrationService) {
this.objectMapper = objectMapper;

this.request = request;
this.birthRegistrationService = birthRegistrationService;
}
@RequestMapping(value="/registration/v1/_create", method =
RequestMethod.POST)
public ResponseEntity<BirthRegistrationResponse>
v1RegistrationCreatePost(@ApiParam(value = "Details for the new
Birth Registration Application(s) + RequestInfo meta data."
,required=true ) @Valid @RequestBody BirthRegistrationRequest
birthRegistrationRequest) {
List<BirthRegistrationApplication> applications =
birthRegistrationService.registerBtRequest(birthRegistrationRequest
);
ResponseInfo responseInfo =
responseInfoFactory.createResponseInfoFromRequestInfo(birthRegistra
tionRequest.getRequestInfo(), true);
BirthRegistrationResponse response =
BirthRegistrationResponse.builder().birthRegistrationApplications(a
pplications).responseInfo(responseInfo).build();
NOTE: At this point, your IDE must be showing a lot of errors but do not worry we will add
return new ResponseEntity<>(response, HttpStatus.OK);
all dependent layers as we progress through this guide and the errors will go away.
}
@RequestMapping(value="/v1/registration/_search", method =
The web layer is now setup.
RequestMethod.POST)
public ResponseEntity<BirthRegistrationResponse>
v1RegistrationSearchPost(@ApiParam(value = "Details for the new
Birth Registration Application(s) + RequestInfo meta data."
,required=true ) @Valid @RequestBody BirthApplicationSearchRequest
birthApplicationSearchRequest) {
List<BirthRegistrationApplication> applications =
birthRegistrationService.searchBtApplications(birthApplicationSearc
hRequest.getRequestInfo(),
birthApplicationSearchRequest.getBirthApplicationSearchCriteria());
ResponseInfo responseInfo =
responseInfoFactory.createResponseInfoFromRequestInfo(birthApplicat
ionSearchRequest.getRequestInfo(), true);
BirthRegistrationResponse response =
BirthRegistrationResponse.builder().birthRegistrationApplications(a
pplications).responseInfo(responseInfo).build();
return new ResponseEntity<>(response,HttpStatus.OK);
}
@RequestMapping(value="/registration/v1/_update", method =
RequestMethod.POST)
public ResponseEntity<BirthRegistrationResponse>
v1RegistrationUpdatePost(@ApiParam(value = "Details for the new (s)
+ RequestInfo meta data." ,required=true ) @Valid @RequestBody
i h
i
i
bi h
i
i
) {

st);
and Kafka.

## Section 2: Integrate Persister & Kafka

BirthRegistrationRequest birthRegistrationRequest) {
BirthRegistrationApplication application =
birthRegistrationService.updateBtApplication(birthRegistrationReque
This section contains information on steps for integrating with the Persister Service
ResponseInfo responseInfo =
responseInfoFactory.createResponseInfoFromRequestInfo(birthRegistra
Steps to integrate Persister Service and Kafka:
tionRequest.getRequestInfo(), true);
BirthRegistrationResponse response =
BirthRegistrationResponse.builder().birthRegistrationApplications(C
Add Kafka Conﬁguration
ollections.singletonList(application)).responseInfo(responseInfo).b
Implement Kafka Producer & Consumer
uild();
return new ResponseEntity<>(response, HttpStatus.OK);
}
Add Persister Conﬁguration
Enable Signed Audit
}

Add Kafka Configuration
Overview
Follow the steps detailed below to add the Kafka conﬁgurations.
Steps
Add the below code in application.properties to conﬁgure Kafka.
# BTR config
btr.kafka.create.topic=save-bt-application
btr.kafka.update.topic=update-bt-application
btr.default.offset=0
btr.default.limit=10
btr.search.max.limit=50

Implement Kafka Producer & Consumer
Overview
Follow the steps detailed below to implement Kafka Producer & Consumer.
Producer
Producer classes help in pushing data from the application to Kafka topics. DIGIT has
a custom implementation of KafkaTemplate class in the tracer library called
CustomKafkaTemplate. This implementation of the Producer class does not change
across services of DIGIT.
Steps
1. Access the producer implementation details here - Producer Implementation.
The Codegen jar already has created a Producer class. We will continue using it.
2. Make sure the tracer dependency version in the pom.xml is 2.9.0-SNAPSHOT.
Consumer
For our guide, we will be implementing a notiﬁcation consumer in the following
section.
Once an application is created/requested or progresses further in the workﬂow,
notiﬁcations can be triggered as each of these events is pushed onto Kafka topics
which can be listened to and an sms/email/in-app notiﬁcation can be sent to the
concerned user(s).
For our guide, we will be implementing a notiﬁcation consumer which will listen to the
topic on which birth registration applications are created. Create a customised

message and send it to the notiﬁcation service (sms/email) to trigger notiﬁcations to
the concerned users.
Sending SMS notiﬁcations to the customer:
Once an application is created/updated the data is pushed on Kafka topic. We trigger
notiﬁcations by consuming data from this topic. Whenever any message is consumed the
service will call the localisation service to fetch the SMS template. It will then replace the
placeholders in the SMS template with the values in the message it consumed.
(For example, It will replace the {NAME} placeholder with the owner name from the data
consumed). Once the SMS text is ready, the service pushes this data on the notiﬁcation
topic. SMS service consumes data from notiﬁcation topic and triggers SMS.
Steps
1. Open Kafka/NotificationConsumer.java and paste the following code:

package digit.kafka;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.service.NotificationService;
import digit.web.models.BirthRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import java.util.HashMap;
@Component
@Slf4j
public class NotificationConsumer {
@Autowired
private ObjectMapper mapper;
@Autowired
private NotificationService notificationService;
@KafkaListener(topics = {"${btr.kafka.create.topic}"})
public void listen(final HashMap<String, Object> record,
@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
try {
BirthRegistrationRequest request =
mapper.convertValue(record, BirthRegistrationRequest.class);
//log.info(request.toString());
notificationService.prepareEventAndSend(request);
} catch (final Exception e) {
log.error("Error while listening to value: " + record +
" on topic: " + topic + ": ", e);
}
}
}

2. Create a POJO by the name of SMSRequest in the web.models package and add
the below content to it:
package digit.web.models;
import lombok.*;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SMSRequest {
private String mobileNumber;
private String message;
}
3. Create a class by the name of NotificationService under service folder to
handle preparation of customised messages and pushing the notiﬁcations.
Add the below content to it -

package digit.service;
import digit.config.BTRConfiguration;
import digit.kafka.Producer;
import digit.web.models.BirthRegistrationApplication;
import digit.web.models.BirthRegistrationRequest;
import digit.web.models.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class NotificationService {
@Autowired
private Producer producer;
@Autowired
private BTRConfiguration config;
@Autowired
private RestTemplate restTemplate;
private static final String smsTemplate = "Dear {FATHER_NAME}
and {MOTHER_NAME} your birth registration application has been
successfully created on the system with application number -
{APPNUMBER}.";
public void prepareEventAndSend(BirthRegistrationRequest
request){
List<SMSRequest> smsRequestList = new ArrayList<>();
request.getBirthRegistrationApplications().forEach(application -> {
SMSRequest smsRequestForFather =
SMSRequest.builder().mobileNumber(
application.getFather().getMobileNumber()).message(getCustomMessage
(smsTemplate, application)).build();
SMSRequest smsRequestForMother =
SMSRequest.builder().mobileNumber(application.getMother().getMobile
Number()).message(getCustomMessage(smsTemplate,
application)).build();
smsRequestList.add(smsRequestForFather);
smsRequestList.add(smsRequestForMother);
});

for (SMSRequest smsRequest : smsRequestList) {
producer.push(config.getSmsNotificationTopic(),
smsRequest);
log.info("Messages: " + smsRequest.getMessage());
}
}
private String getCustomMessage(String template,
BirthRegistrationApplication application) {
template = template.replace("{APPNUMBER}",
application.getApplicationNumber());
template = template.replace("{FATHER_NAME}",
application.getFather().getName());
template = template.replace("{MOTHER_NAME}",
application.getMother().getName());
return template;
}
}

Add Persister Configuration
Overview
This page provides the steps on how to add persister conﬁguration.
The persister conﬁguration is written in a YAML format. The INSERT and UPDATE
queries for each table are added in prepared statement format, followed by the
jsonPaths of values that have to be inserted/updated.
For example, for a table named studentinfo with id, name, age, and marks ﬁelds, the
following conﬁguration will get the persister ready to insert data into studentinfo table
-
serviceMaps:
serviceName: student-management-service
mappings:
- version: 1.0
description: Persists student details in studentinfo table
fromTopic: save-student-info
isTransaction: true
queryMaps:
- query: INSERT INTO studentinfo( id, name, age, marks)
VALUES (?, ?, ?, ?);
basePath: Students.*
jsonMaps:
- jsonPath: $.Students.*.id
- jsonPath: $.Students.*.name
- jsonPath: $.Students.*.age
- jsonPath: $.Students.*.marks
Steps

Add Persister Configuration
1. Fork the conﬁgs repo . Ignore if done already. Clone conﬁgs repo in the local
environment.
git clone -o upstream https://github.com/<your_configs_repo>/configs
2. Persister conﬁgurations for all modules are present under the egov-persister
folder. Create a ﬁle by the name of btr-persister.yml (or any other name) under
the egov-persister folder.
3. Add the below content to it -

serviceMaps:
serviceName: btr-services
mappings:
- version: 1.0
description: Persists birth details in tables
fromTopic: save-bt-application
isTransaction: true
queryMaps:
- query: INSERT INTO
eg_bt_registration(id,tenantid,applicationnumber,babyfirstname,baby
lastname,fatherid,motherid,doctorname,hospitalname,placeofbirth,tim
eofbirth,createdby,lastmodifiedby,createdtime, lastmodifiedtime)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?);
basePath: BirthRegistrationApplications.*
jsonMaps:
- jsonPath: $.BirthRegistrationApplications.*.id
- jsonPath: $.BirthRegistrationApplications.*.tenantId
- jsonPath:
$.BirthRegistrationApplications.*.applicationNumber
- jsonPath:
$.BirthRegistrationApplications.*.babyFirstName
- jsonPath:
$.BirthRegistrationApplications.*.babyLastName
- jsonPath:
$.BirthRegistrationApplications.*.father.uuid
- jsonPath:
$.BirthRegistrationApplications.*.mother.uuid
- jsonPath:
$.BirthRegistrationApplications.*.doctorName
- jsonPath:
$.BirthRegistrationApplications.*.hospitalName
- jsonPath:
$.BirthRegistrationApplications.*.placeOfBirth
- jsonPath:
$.BirthRegistrationApplications.*.timeOfBirth
- jsonPath:

$.BirthRegistrationApplications.*.auditDetails.createdBy
- jsonPath:
$.BirthRegistrationApplications.*.auditDetails.lastModifiedBy
- jsonPath:
$.BirthRegistrationApplications.*.auditDetails.createdTime
- jsonPath:
$.BirthRegistrationApplications.*.auditDetails.lastModifiedTime
- query: INSERT INTO eg_bt_address(id, tenantid, type,
address, city, pincode, registrationid, createdby, lastmodifiedby,
createdtime, lastmodifiedtime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,
?, ?);
basePath: BirthRegistrationApplications.*
jsonMaps:
- jsonPath:
$.BirthRegistrationApplications.*.address.id
- jsonPath:
$.BirthRegistrationApplications.*.address.tenantId
Run Persister Locally
- jsonPath:
1.
Import all the core-services projects as Maven projects into your IDE. It is assumed
$.BirthRegistrationApplications.*.address.applicantAddress.type
that you have already cloned the DIGIT code locally.
- jsonPath:
2. Modify the application.properties ﬁle in the egov-persister project and set the
$.BirthRegistrationApplications.*.address.applicantAddress.address
following property:
- jsonPath:
$.BirthRegistrationApplications.*.address.applicantAddress.city
egov.persist.yml.repo.path=file:///Users/subha/Code/configs/egov-
persister/btr-persister.yml
- jsonPath:
$.BirthRegistrationApplications.*.address.applicantAddress.pincode
- jsonPath:
Note: You can set a comma-separated list of ﬁles as the value of the above property. If
$.BirthRegistrationApplications.*.address.applicationNumber
you are running multiple services locally, then this has to be a comma-separated list of
persister conﬁg ﬁles. Make sure you always give the absolute path.
- jsonPath:
$.BirthRegistrationApplications.*.auditDetails.createdBy
3. Make sure the Spring DB conﬁgurations and Flyway conﬁg reﬂect the same
- jsonPath:
database as what has been set in the module itself. Otherwise, we will see failures
$.BirthRegistrationApplications.*.auditDetails.lastModifiedBy
in the persister code.
- jsonPath:
$.BirthRegistrationApplications.*.auditDetails.createdTime
- jsonPath:
$ i h
i
i
li
i
di
il l
difi d i

$.BirthRegistrationApplications.*.auditDetails.lastModifiedTime
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/birthregistration
- version: 1.0
description: Update birth registration applications in table
spring.datasource.username=yourusername
fromTopic: update-bt-application
spring.datasource.password=yourpassword
isTransaction: true
queryMaps:
- query: UPDATE eg_bt_registration SET tenantid =
4. Make sure the Kafka is running locally. Now, go ahead and run the
?,babyFirstName = ?, timeOfBirth = ? WHERE id=?;
EgovPersistApplication from the IDE. Check the console to make sure it is listening
basePath: BirthRegistrationApplications.*
to the right topics as conﬁgured in your module's application.properties ﬁle.
jsonMaps:
- jsonPath: $.BirthRegistrationApplications.*.tenantId
The persister is now ready for use.
- jsonPath:
$.BirthRegistrationApplications.*.babyFirstName
Deploy Persister Configuration
- jsonPath:
$.BirthRegistrationApplications.*.timeOfBirth
- jsonPath: $.BirthRegistrationApplications.*.id
Note: Below steps are for when you deploy your code to the DIGIT env, not for local
development. You may choose to do this when you build and deploy.
1. Push the code to the appropriate branch from which your environment will read it.
2. Navigate to your fork of the DIGIT-DevOps repository. Under the deploy-as-
code/helm/environments directory, ﬁnd the deployment helm chart that was used
to deploy DIGIT.
3.
In the deployment helm chart (which was used to set up the DIGIT environment),
ﬁnd "egov-persister". Find the "persist-yml-path" property and add the path to
your new persister ﬁle here.
In the snippet below, file:///work-dir/configs/egov-persister/birth-module-
developer-guide.yml

egov-persister:
replicas: 6
persist-yml-path: "file:///work-dir/configs/egov-
persister/privacy-audit.yml,file:///work-dir/configs/egov-
persister/pgr-migration-batch.yml,file:///work-dir/configs/egov-
persister/pgr-services-persister.yml,file:///work-dir/configs/egov-
persister/pdf-filestoreid-update.yml,file:///work-dir/configs/egov-
persister/chatbot.yml,file:///work-dir/configs/egov-persister/pt-
mutation-calculator-persister.yml,file:///work-dir/configs/egov-
persister/apportion-persister.yml,file:///work-dir/configs/egov-
persister/property-services-registry.yml,file:///work-
dir/configs/egov-persister/billing-services-
persist.yml,file:///work-dir/configs/egov-persister/egf-
bill.yaml,file:///work-dir/configs/egov-persister/egov-user-event-
persister.yml,file:///work-dir/configs/egov-persister/egov-workflow-
v2-persister.yml,file:///work-dir/configs/egov-
persister/firenoc_persiter.yaml,file:///work-dir/configs/egov-
persister/hrms-employee-persister.yml,file:///work-dir/configs/egov-
persister/pdf-generator.yml,file:///work-dir/configs/egov-
persister/pg-service-persister.yml,file:///work-dir/configs/egov-
persister/pgr.v3.yml,file:///work-dir/configs/egov-
persister/property-services.yml,file:///work-dir/configs/egov-
persister/pt-calculator-v2-persister.yml,file:///work-
dir/configs/egov-persister/pt-drafts.yml,file:///work-
dir/configs/egov-persister/pt-persist.yml,file:///work-
dir/configs/egov-persister/tl-billing-slab-
persister.yml,file:///work-dir/configs/egov-persister/tl-
calculation-persister.yml,file:///work-dir/configs/egov-
persister/tradelicense.yml,file:///work-dir/configs/egov-
persister/uploader-persister.yml,file:///work-dir/configs/egov-
persister/collection-migration-persister.yml,file:///work-
dir/configs/egov-persister/water-persist.yml,file:///work-
dir/configs/egov-persister/water-meter.yml,file:///work-
dir/configs/egov-persister/assessment-persister.yml,file:///work-
dir/configs/egov-persister/sewerage-persist.yml,file:///work-
dir/configs/egov-persister/bpa-persister.yml,file:///work-
dir/configs/egov-persister/property-services-migration-temp-
config.yml,file:///work-dir/configs/egov-persister/assessment-
persister-migration-temp.yml,file:///work-dir/configs/egov-
persister/migration-batch-count-persister.yml,file:///work-
dir/configs/egov-persister/land-persister.yml,file:///work-
dir/configs/egov-persister/noc-persister.yml,file:///work-
dir/configs/egov-persister/fsm-persister.yaml,file:///work-
dir/configs/egov-persister/vehicle-persister.yaml,file:///work-
dir/configs/egov-persister/vendor-persister.yaml,file:///work-
dir/configs/egov-persister/fsm-calculator-
persister.yaml,file:///work-dir/configs/egov-
persister/echallan.yml,file:///work-dir/configs/egov-persister/egov-

document-upload-persister.yml,file:///work-dir/configs/egov-
persister/egov-survey-service-persister.yml,file:///work-
dir/configs/egov-persister/firenoc-calculator-
persister.yml,file:///work-dir/configs/egov-persister/bulk-bill-
generation-audit.yml,file:///work-dir/configs/egov-persister/nss-
persister.yml,file:///work-dir/configs/egov-persister/birth-
death.yml,file:///work-dir/configs/egov-persister/bulk-bill-
generator-ws.yml,file:///work-dir/configs/egov-persister/bulk-bill-
generator-sw.yml,file:///work-dir/configs/egov-persister/audit-
service-persister.yml,file:///work-dir/configs/egov-persister/birth-
module-developer-guide.yml"
4. Raise a PR to the appropriate branch of the DevOps repo (master, in egov case)
which was forked/used to create the deployment. Once that is merged, restart the
indexer service in your environment so it will pick up this new conﬁg for the
module.

Enable Signed Audit
Integration with signed audit
Overview
Enabling signed audit for a module ensures that all transactions - creates, updates,
deletes - are recorded in a digitally signed fashion. Learn more about the signed audit
service here.
Enabled signed audit is optional but highly recommended to ensure data security.
Steps
Enable Signed Audit
1. Add the following lines of code to the birth registration persister after the
fromTopic attribute under mappings :
isTransaction: true
isAuditEnabled: true
module: BTR
objecIdJsonPath: $.id
tenantIdJsonPath: $.tenantId
transactionCodeJsonPath: $.applicationNumber
auditAttributeBasePath: $.BirthRegistrationApplications

## Section 3: Integrate Microservices

Integration with other DIGIT services
Overview
A separate class should be created for integrating with each dependent microservice.
Only one method from that class should be called from the main service class for
integration.
This guide showcases the steps to integrate our microservices with other
microservices like
1.
IdGen Service
2. User Service
3. MDMS Service
4. Workﬂow Service
5. URL Shortening Service
Common Resources
For interacting with other microservices, we can create and implement the following
ServiceRequestRepository class under repository package -

package digit.repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.ServiceCallException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
@Repository
@Slf4j
public class ServiceRequestRepository {
private ObjectMapper mapper;
private RestTemplate restTemplate;
@Autowired
public ServiceRequestRepository(ObjectMapper mapper, RestTemplate
restTemplate) {
this.mapper = mapper;
this.restTemplate = restTemplate;
}
public Object fetchResult(StringBuilder uri, Object request) {
mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,
false);
Object response = null;
try {
response = restTemplate.postForObject(uri.toString(),
request, Map.class);
}catch(HttpClientErrorException e) {
log.error("External Service threw an Exception: ",e);
throw new ServiceCallException(e.getResponseBodyAsString());
}catch(Exception e) {
log.error("Exception while fetching from searcher: ",e);
}
return response;
}

}

Integrate IDGen Service
Describes how to integrate with DIGIT's ID Gen service
Overview
This page provides the steps to integrate with the IDGen Service. Each application
needs to have a unique ID. The IDGen service generates these unique IDs. ID format
can be customised via conﬁguration in MDMS.
Steps
1. Add the ID format that needs to be generated in this ﬁle - Id Format Mdms File .
The following conﬁg has been added for this module:
{
"format": "PB-BTR-[cy:yyyy-MM-dd]-[SEQ_EG_BTR_ID]",
"idname": "btr.registrationid"
}
2. Restart the IDGen service and MDMS service and port-forward IDGen service to
port 8285:
kubectl port-forward <IDGEN_SERVICE_POD_NAME> 8285:8080
Note that you can set the ID format in the application.properties ﬁle of IDGen service and
run the service locally if you don't have access to a DIGIT environment.
3. Hit the below curl to verify that the format is added properly. The "ID" name needs
to match exactly with what was added in MDMS.

curl --location --request POST 'http://localhost:8285/egov-
idgen/id/_generate' \
--header 'Content-Type: application/json' \
--data-raw '{
"RequestInfo": {
"apiId": "string",
"ver": "string",
"ts": null,
"action": "string",
"did": "string",
"key": "string",
"msgId": "string",
"authToken": "6456b2cf-49ca-47c7-b7b6-c179f19614c7",
"correlationId": "e721639b-c095-40b3-86e2-acecb2cb6efb",
"userInfo": {
"id": 23299,
"uuid": "e721639b-c095-40b3-86e2-acecb2cb6efb",
"userName": "9337682030",
"name": "Abhilash Seth",
"type": "EMPLOYEE",
"mobileNumber": "9337682030",
"emailId": "abhilash.seth@gmail.com",
"roles": [
{
"id": 281,
"name": "Employee"
}
]
}
},
"idRequests": [
{
"tenantId": "pb.amritsar",
"idName": "btr.registrationid"
}
]
}'
4. Once veriﬁed, we can call the ID generation service from within our application
and generate the registrationId. \
5.
In the BirthApplicationEnrichment class, update the enrichBirthApplication
method as shown below:

public void enrichBirthApplication(BirthRegistrationRequest
birthRegistrationRequest) {
List<String> birthRegistrationIdList =
idgenUtil.getIdList(birthRegistrationRequest.getRequestInfo(),
birthRegistrationRequest.getBirthRegistrationApplications().get(0).getTe
nantId(), "btr.registrationid", "",
birthRegistrationRequest.getBirthRegistrationApplications().size());
Integer index = 0;
for(BirthRegistrationApplication application :
birthRegistrationRequest.getBirthRegistrationApplications()){
// Enrich audit details
AuditDetails auditDetails =
AuditDetails.builder().createdBy(birthRegistrationRequest.getRequestInfo
().getUserInfo().getUuid()).createdTime(System.currentTimeMillis()).last
ModifiedBy(birthRegistrationRequest.getRequestInfo().getUserInfo().getUu
id()).lastModifiedTime(System.currentTimeMillis()).build();
application.setAuditDetails(auditDetails);
// Enrich UUID
application.setId(UUID.randomUUID().toString());
// Enrich application number from IDgen
application.setApplicationNumber(birthRegistrationIdList.get(index++));
// Enrich registration Id
application.getAddress().setApplicationNumber(application.getId());
// Enrich address UUID
application.getAddress().setId(UUID.randomUUID().toString());
}
}
6. Make sure below ID generation host conﬁguration is present in the
application.properties ﬁle. Make sure to ﬁll in the correct values for the host.
#Idgen Config
egov.idgen.host=http://localhost:8285/ #REPLACE
egov.idgen.path=egov-idgen/id/_generate

Integrate User Service
Overview
The User Service provides the capabilities of creating a user, searching for a user and
retrieving the details of a user. This module will search for a user and if not found,
create that user with the user service.
DIGIT's user service masks PII that gets stored in the database using the Encryption
Service.
Steps
1. Create a class by the name of UserService under service folder and add the
following content to it:
UserService.java

package digit.service;
import digit.config.BTRConfiguration;
import digit.util.UserUtil;
import digit.web.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.common.contract.user.CreateUserRequest;
import org.egov.common.contract.user.UserDetailResponse;
import org.egov.common.contract.user.UserSearchRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
@Slf4j
public class UserService {
private UserUtil userUtils;
private BTRConfiguration config;
@Autowired
public UserService(UserUtil userUtils, BTRConfiguration
config) {
this.userUtils = userUtils;
this.config = config;
}
/**
* Calls user service to enrich user from search or upsert
user
* @param request
*/
public void callUserService(BirthRegistrationRequest
request){
request.getBirthRegistrationApplications().forEach(application

-> {
if(!StringUtils.isEmpty(application.getFather().getUuid()))
enrichUser(application,
request.getRequestInfo());
else {
User user = createFatherUser(application);
application.getFather().setUuid(upsertUser(user,
request.getRequestInfo()).getUuid());
}
});
request.getBirthRegistrationApplications().forEach(application
-> {
if(!StringUtils.isEmpty(application.getMother().getUuid()))
enrichUser(application,
request.getRequestInfo());
else {
User user = createMotherUser(application);
application.getMother().setUuid(upsertUser(user,
request.getRequestInfo()).getUuid());
}
});
}
private User createFatherUser(BirthRegistrationApplication
application){
User father = application.getFather();
User user =
User.builder().userName(father.getUserName())
.name(father.getName())
.userName((father.getUserName()))
.mobileNumber(father.getMobileNumber())
.emailId(father.getEmailId())
.tenantId(father.getTenantId())
.type(father.getType())
.roles(father.getRoles())
.build();
return user;
}
private User createMotherUser(BirthRegistrationApplication
application){
User mother = application.getMother();

User user =
User.builder().userName(mother.getUserName())
.name(mother.getName())
2. Update the code in userUtil
.userName((mother.getUserName()))
.mobileNumber(mother.getMobileNumber())
.emailId(mother.getEmailId())
.tenantId(mother.getTenantId())
UserUtil.java
.type(mother.getType())
.roles(mother.getRoles())
.build();
return user;
}
private User upsertUser(User user, RequestInfo
requestInfo){
String tenantId = user.getTenantId();
User userServiceResponse = null;
// Search on mobile number as user name
UserDetailResponse userDetailResponse =
searchUser(userUtils.getStateLevelTenant(tenantId),null,
user.getUserName());
if (!userDetailResponse.getUser().isEmpty()) {
User userFromSearch =
userDetailResponse.getUser().get(0);
log.info(userFromSearch.toString());
if(!user.getUserName().equalsIgnoreCase(userFromSearch.getUser
Name())){
userServiceResponse =
updateUser(requestInfo,user,userFromSearch);
}
else userServiceResponse =
userDetailResponse.getUser().get(0);
}
else {
userServiceResponse =
createUser(requestInfo,tenantId,user);
}
// Enrich the accountId
// user.setId(userServiceResponse.getUuid());
return userServiceResponse;
}
private void enrichUser(BirthRegistrationApplication
application, RequestInfo requestInfo){
String accountIdFather =

String accountIdFather =
application.getFather().getUuid();
String accountIdMother =
application.getMother().getUuid();
String tenantId = application.getTenantId();
UserDetailResponse userDetailResponseFather =
searchUser(userUtils.getStateLevelTenant(tenantId),accountIdFa
ther,null);
UserDetailResponse userDetailResponseMother =
searchUser(userUtils.getStateLevelTenant(tenantId),accountIdMo
ther,null);
if(userDetailResponseFather.getUser().isEmpty())
throw new CustomException("INVALID_ACCOUNTID","No
user exist for the given accountId");
else
application.getFather().setUuid(userDetailResponseFather.getUs
er().get(0).getUuid());
if(userDetailResponseMother.getUser().isEmpty())
throw new CustomException("INVALID_ACCOUNTID","No
user exist for the given accountId");
else
application.getMother().setUuid(userDetailResponseMother.getUs
er().get(0).getUuid());
}
/**
* Creates the user from the given userInfo by calling
user service
* @param requestInfo
* @param tenantId
* @param userInfo
* @return
*/
private User createUser(RequestInfo requestInfo,String
tenantId, User userInfo) {
userUtils.addUserDefaultFields(userInfo.getMobileNumber(),tena
ntId, userInfo);
StringBuilder uri = new
StringBuilder(config.getUserHost())
.append(config.getUserContextPath())
.append(config.getUserCreateEndpoint());

package digit.util;
CreateUserRequest user = new
CreateUserRequest(requestInfo, userInfo);
import com.fasterxml.jackson.databind.ObjectMapper;
log.info(user.getUser().toString());
import digit.config.Configuration;
UserDetailResponse userDetailResponse =
import static digit.config.ServiceConstants.*;
userUtils.userCall(user, uri);
import org.egov.common.contract.request.Role;
import org.egov.common.contract.request.User;
return userDetailResponse.getUser().get(0);
import org.egov.common.contract.user.UserDetailResponse;
import org.egov.common.contract.user.enums.UserType;
}
import digit.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
/**
import org.springframework.beans.factory.annotation.Autowired;
* Updates the given user by calling user service
import org.springframework.stereotype.Component;
* @param requestInfo
* @param user
import java.text.ParseException;
* @param userFromSearch
import java.text.SimpleDateFormat;
* @return
import java.util.*;
*/
private User updateUser(RequestInfo requestInfo,User
@Component
user,User userFromSearch) {
public class UserUtil {
userFromSearch.setName(user.getName());
@Autowired
private ObjectMapper mapper;
StringBuilder uri = new
StringBuilder(config.getUserHost())
@Autowired
.append(config.getUserContextPath())
private ServiceRequestRepository serviceRequestRepository;
.append(config.getUserUpdateEndpoint());
@Autowired
private Configuration configs;
UserDetailResponse userDetailResponse =
userUtils.userCall(new CreateUserRequest(requestInfo,
userFromSearch), uri);
@Autowired
public UserUtil(ObjectMapper mapper,
return userDetailResponse.getUser().get(0);
ServiceRequestRepository serviceRequestRepository) {
this.mapper = mapper;
}
this.serviceRequestRepository =
serviceRequestRepository;
/**
}
* calls the user search API based on the given accountId
and userName
/**
* @param stateLevelTenant
* Returns UserDetailResponse by calling user service with
* @param accountId
given uri and object
* @param userName
* @param userRequest Request object for user service
* @return
* @param uri The address of the endpoint
*/
* @return Response from user service as parsed as
public UserDetailResponse searchUser(String
userDetailResponse
stateLevelTenant, String accountId, String userName){
*/
UserSearchRequest userSearchRequest =new

public UserDetailResponse userCall(Object userRequest,
UserSearchRequest();
StringBuilder uri) {
userSearchRequest.setActive(false);
String dobFormat = null;
// userSearchRequest.setUserType("CITIZEN");
userSearchRequest.setTenantId(stateLevelTenant);
if(uri.toString().contains(configs.getUserSearchEndpoint())
|| uri.toString().contains(configs.getUserUpdateEndpoint()))
if(StringUtils.isEmpty(accountId) &&
dobFormat=DOB_FORMAT_Y_M_D;
StringUtils.isEmpty(userName))
else
return null;
if(uri.toString().contains(configs.getUserCreateEndpoint()))
dobFormat = DOB_FORMAT_D_M_Y;
if(!StringUtils.isEmpty(accountId))
try{
LinkedHashMap responseMap =
userSearchRequest.setUuid(Collections.singletonList(accountId)
(LinkedHashMap)serviceRequestRepository.fetchResult(uri,
);
userRequest);
parseResponse(responseMap,dobFormat);
if(!StringUtils.isEmpty(userName))
UserDetailResponse userDetailResponse =
userSearchRequest.setUserName(userName);
mapper.convertValue(responseMap,UserDetailResponse.class);
return userDetailResponse;
StringBuilder uri = new
}
StringBuilder(config.getUserHost()).append(config.getUserSearc
catch(IllegalArgumentException e)
hEndpoint());
{
return userUtils.userCall(userSearchRequest,uri);
throw new
CustomException(ILLEGAL_ARGUMENT_EXCEPTION_CODE,OBJECTMAPPER_U
}
NABLE_TO_CONVERT);
}
/**
}
* calls the user search API based on the given list of
user uuids
* @param uuids
/**
* @return
* Parses date formats to long for all users in
*/
responseMap
private Map<String,User> searchBulkUser(List<String>
* @param responseMap LinkedHashMap got from user api
uuids){
response
*/
UserSearchRequest userSearchRequest =new
UserSearchRequest();
public void parseResponse(LinkedHashMap responseMap,
userSearchRequest.setActive(false);
String dobFormat){
userSearchRequest.setUserType("CITIZEN");
List<LinkedHashMap> users =
(List<LinkedHashMap>)responseMap.get(USER);
String format1 = DOB_FORMAT_D_M_Y_H_M_S;
if(!CollectionUtils.isEmpty(uuids))
if(users!=null){
userSearchRequest.setUuid(uuids);
users.forEach( map -> {
map.put(CREATED_DATE,dateTolong((String)map.get(CREATED_DATE),
StringBuilder uri = new
format1));
StringBuilder(config.getUserHost()).append(config.getUserSearc
hEndpoint());
if((String)map.get(LAST_MODIFIED_DATE)!=null)
UserDetailResponse userDetailResponse =
userUtils.userCall(userSearchRequest,uri);
(
i
d
l
il
((
i )
(
()

map.put(LAST_MODIFIED_DATE,dateTolong((String)map.get(LAST_MOD
List<User> users = userDetailResponse.getUser();
IFIED_DATE),format1));
if((String)map.get(DOB)!=null)
if(CollectionUtils.isEmpty(users))
Changes to BirthApplicationEnrichment.java
throw new CustomException("USER_NOT_FOUND","No
map.put(DOB,dateTolong((String)map.get(DOB),dobFormat));
user found for the uuids");
Add the below methods to the enrichment class we created. When we search for an
if((String)map.get(PWD_EXPIRY_DATE)!=null)
Map<String,User> idToUserMap =
application, the code below will search for the users associated with the application
users.stream().collect(Collectors.toMap(User::getUuid,
and add in their details to the response object.
map.put(PWD_EXPIRY_DATE,dateTolong((String)map.get(PWD_EXPIRY_
Function.identity()));
DATE),format1));
}
return idToUserMap;
);
}
enrichFatherApplicantOnSearch
}
}
}
/**
* Converts date to long
* @param date date to be parsed
* @param format Format of the date
* @return Long value of date
*/
private Long dateTolong(String date,String format){
SimpleDateFormat f = new SimpleDateFormat(format);
Date d = null;
try {
d = f.parse(date);
} catch (ParseException e) {
throw new
CustomException(INVALID_DATE_FORMAT_CODE,INVALID_DATE_FORMAT_M
ESSAGE);
}
return d.getTime();
}
/**
* enriches the userInfo with statelevel tenantId and
other fields
* The function creates user with username as mobile
number.
* @param mobileNumber
* @param tenantId
* @param userInfo
*/
public void addUserDefaultFields(String
mobileNumber,String tenantId, User userInfo){
Role role = getCitizenRole(tenantId);
userInfo.setMobileNumber(mobileNumber);
userInfo.setTenantId(getStateLevelTenant(tenantId));
userInfo setType("CITIZEN");

userInfo.setType( CITIZEN );
}
public void
enrichFatherApplicantOnSearch(BirthRegistrationApplication
/**
application) {
* Returns role object for citizen
UserDetailResponse fatherUserResponse =
* @param tenantId
userService.searchUser(userUtils.getStateLevelTenant(applicati
* @return
on.getTenantId()),application.getFather().getUuid(),null);
*/
User fatherUser = fatherUserResponse.getUser().get(0);
private Role getCitizenRole(String tenantId){
log.info(fatherUser.toString());
Role role = Role.builder().build();
User fatherApplicant = User.builder()
role.setCode(CITIZEN_UPPER);
.mobileNumber(fatherUser.getMobileNumber())
role.setName(CITIZEN_LOWER);
.id(fatherUser.getId())
role.setTenantId(getStateLevelTenant(tenantId));
.name(fatherUser.getName())
return role;
.userName((fatherUser.getUserName()))
}
.type(fatherUser.getType())
.roles(fatherUser.getRoles())
public String getStateLevelTenant(String tenantId){
.uuid(fatherUser.getUuid()).build();
return tenantId.split("\\.")[0];
application.setFather(fatherApplicant);
}
}
}
public void
enrichMotherApplicantOnSearch(BirthRegistrationApplication
application) {
UserDetailResponse motherUserResponse =
userService.searchUser(userUtils.getStateLevelTenant(applicati
on.getTenantId()),application.getMother().getUuid(),null);
User motherUser = motherUserResponse.getUser().get(0);
log.info(motherUser.toString());
User motherApplicant = User.builder()
.mobileNumber(motherUser.getMobileNumber())
.id(motherUser.getId())
.name(motherUser.getName())
.userName((motherUser.getUserName()))
.type(motherUser.getType())
.roles(motherUser.getRoles())
.uuid(motherUser.getUuid()).build();
application.setMother(motherApplicant);
}
Changes to BirthRegistrationService.java
Add in a userService object:

@Autowired
private UserService userService;
And enhance the following two methods in BirthRegistrationService.java:
registerBtRequest
public List<BirthRegistrationApplication>
registerBtRequest(BirthRegistrationRequest
birthRegistrationRequest) {
// Validate applications
validator.validateBirthApplication(birthRegistrationRequest);
// Enrich applications
enrichmentUtil.enrichBirthApplication(birthRegistrationRequest
);
// Enrich/Upsert user in upon birth registration
userService.callUserService(birthRegistrationRequest);
//
// Initiate workflow for the new application
workflowService.updateWorkflowStatus(birthRegistrationRequest)
;
// Push the application to the topic for persister to
listen and persist
producer.push("save-bt-application",
birthRegistrationRequest);
// Return the response back to user
return
birthRegistrationRequest.getBirthRegistrationApplications();
}
searchBtApplications

public List<BirthRegistrationApplication>
searchBtApplications(RequestInfo requestInfo,
BirthApplicationSearchCriteria birthApplicationSearchCriteria)
{
// Fetch applications from database according to the
given search criteria
List<BirthRegistrationApplication> applications =
birthRegistrationRepository.getApplications(birthApplicationSe
archCriteria);
// If no applications are found matching the given
criteria, return an empty list
if(CollectionUtils.isEmpty(applications))
return new ArrayList<>();
// Enrich mother and father of applicant objects
applications.forEach(application -> {
enrichmentUtil.enrichFatherApplicantOnSearch(application);
enrichmentUtil.enrichMotherApplicantOnSearch(application);
});
// Otherwise return the found applications
return applications;
}
3. Add the following properties in application.properties ﬁle:
Note: If you're port-forwarding using k8s, use "localhost". Otherwise, if you have a valid
auth token, provide the hostname here.
#User config
egov.user.host=http://localhost:8284/
egov.user.context.path=/user/users
egov.user.create.path=/_createnovalidate
egov.user.search.path=/user/_search
egov.user.update.path=/_updatenovalidate

Add MDMS Configuration
Overview
MDMS data is the master data used by the application. New modules with master
data need to be conﬁgured inside the /data/<tenant>/ folder of the MDMS repo.
Each tenant should have a unique ID and sub-tenants can be conﬁgured in the format
state.cityA, state.cityB etc..Further hierarchies are also possible with tenancy.
If you've conﬁgured the DIGIT environment with a tenant and CITIZEN/EMPLOYEE roles,
you have sufficient data to run this module locally. Conﬁguring role-action mapping is
only necessary during app deployment in the DIGIT environment and won't be needed for
local application execution.
Refer to MDMS docs for more information. To learn about how to design the MDMS, refer
to the design guide.
In the birth registration use case, we use the following master data:
1. tenantId = "pb"
2. User roles - CITIZEN and EMPLOYEE roles conﬁgured in roles.json (see below
section for more info)
3. Actions - URIs to be exposed via Zuul (see below section for more info)
4. Role-action mapping - for access control (see below section for more info)
Ensure that you add data to the appropriate branch of the MDMS repository. For
example, if you've set up CD/CI to deploy the DEV branch of the repository to the
development environment (default), add the information to the DEV branch. If you're
testing in staging or another environment, make sure to add the master data to the
corresponding branch of MDMS.
Steps

1. Create a folder called "pb" in the data folder of the MDMS repository "DEV"
branch. You will have a new folder path as follows:
<MDMS repo URL path>/data/pb
2. Restart the MDMS service in the development environment where DIGIT is running
once data is added to the MDMS repository. This loads the newly added/updated
MDMS conﬁgs.
A sample MDMS conﬁg ﬁle can be viewed here - Sample MDMS data ﬁle .
API Access Control Configuration
URIs (actions), roles and URI-role mapping will be deﬁned in MDMS. These will apply
when the module is deployed into an environment where Zuul is involved (not while
locally running the app). In this sample app, we have used "pb" as a tenantId. In your
environment, you can choose to deﬁne a new one or work with an existing one.
All folders mentioned below need to be created under the data/pb folder in MDMS.
You can choose to use some other tenantId. Make sure to change the tenant ID
everywhere.
Actions
Actions need to be deﬁned inside the / data/pb/ACCESS-CONTROL-
ACTIONS/actions.json ﬁle. Below are the actions for the birth registration module.
Append this to the bottom of the actions.json ﬁle. Make sure the "id" ﬁeld in the JSON
is incremented. It needs to be unique in your environment.

// Some code
{
"id": 2382,
"name": "Birth registration module create",
"url": "/birth-registration/birth-
services/v1/registration/_create",
"parentModule": "",
"displayName": "Birth registration",
"orderNumber": 0,
"enabled": true,
"serviceCode": "BTR",
"code": "null",
"path": ""
},
{
"id": 2383,
"name": "Birth registration module update",
"url": "/birth-registration/birth-services/v1/registration/_update",
"parentModule": "",
"displayName": "Birth registration",
"orderNumber": 0,
"enabled": true,
"serviceCode": "BTR",
"code": "null",
"path": ""
},
{
"id": 2384,
"name": "Birth registration module search",
"url": "/birth-registration/birth-services/v1/registration/_search",
"parentModule": "",
"displayName": "Birth registration",
"orderNumber": 0,
"enabled": true,
"serviceCode": "BTR",
"code": "null",
"path": ""
}
Note that the IDs in the actions.json conﬁg are generated manually.
Roles Configuration

Roles conﬁg happens at a state level. For birth registration, we need only CITIZEN and
EMPLOYEE roles in the / data/pb/ACCESSCONTROL-ROLES/roles.json ﬁle.Here are
some sample roles
that can be deﬁned in an environment. If these roles are already
present in the ﬁle, then there is no need to add them in again.
Role-action Mapping
Append the below code to the "roleactions" key in the / data/pb/ACCESSCONTROL-
ROLEACTIONS/ roleactions.json. Note that other role-action mappings may already
be deﬁned in your DIGIT environment. So please make sure to append the below. The
actionid refers to the URI ID deﬁned in the actions.json ﬁle.
{
"rolecode": "CITIZEN",
"actionid": 2382,
"actioncode": "",
"tenantId": "pb"
},
{
"rolecode": "CITIZEN",
"actionid": 2383,
"actioncode": "",
"tenantId": "pb"
},
{
"rolecode": "CITIZEN",
"actionid": 2384,
"actioncode": "",
"tenantId": "pb"
}

Integrate MDMS Service
Overview
We will call into MDMS deployed in the sandbox environment. All MDMS conﬁg data
needs to be uploaded into the MDMS repository (DEV branch if you are
deploying/testing in your dev environment).
Steps
Integration with MDMS requires the following steps to be followed:
1. Add a new MDMS ﬁle in MDMS repo. For this guide, a sample MDMS ﬁle has
already been added available here . Copy this ﬁle into your repository.
2. Restart MDMS service after adding the new ﬁle via Jenkins build UI.
3. Once restarted, hit the curl mentioned below to verify that the new ﬁle has been
properly added .

curl --location --request POST 'https://yourserver.digit.org/egov-mdms-
service/v1/_search' \
--header 'Content-Type: application/json' \
--data-raw '{
"RequestInfo": {
"apiId": "asset-services",
"ver": null,
"ts": null,
"action": null,
"did": null,
"key": null,
"msgId": "search with from and to values",
"authToken": "{{devAuth}}"
},
"MdmsCriteria": {
"tenantId": "pb",
"moduleDetails": [
{
"moduleName": "BTR",
"masterDetails": [
{
"name": "RegistrationCharges"
}
]
}
]
}
}'
4. Call the MDMS service post veriﬁcation from within our application and fetch the
required master data. For this, create a Java class by the name of MdmsUtil under
utils folder. Annotate this class with @Component and put the following content in
the class -

package digit.utils;
import com.jayway.jsonpath.JsonPath;
import org.egov.common.contract.request.RequestInfo;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsCriteria;
import org.egov.mdms.model.MdmsCriteriaReq;
import org.egov.mdms.model.ModuleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class MdmsUtil {
@Autowired
private RestTemplate restTemplate;
@Value("${egov.mdms.host}")
private String mdmsHost;
@Value("${egov.mdms.search.endpoint}")
private String mdmsUrl;
public Integer fetchRegistrationChargesFromMdms(RequestInfo
requestInfo, String tenantId) {
StringBuilder uri = new StringBuilder();
uri.append(mdmsHost).append(mdmsUrl);
MdmsCriteriaReq mdmsCriteriaReq =
getMdmsRequestForCategoryList(requestInfo, tenantId);
Object response = new HashMap<>();
Integer rate = 0;
try {
response = restTemplate.postForObject(uri.toString(),
mdmsCriteriaReq, Map.class);
rate = JsonPath.read(response,
"$.MdmsRes.BTR.RegistrationCharges.[0].amount");
}catch(Exception e) {
return null;
}
return rate;

}
private MdmsCriteriaReq getMdmsRequestForCategoryList(RequestInfo
requestInfo, String tenantId) {
MasterDetail masterDetail = new MasterDetail();
masterDetail.setName("RegistrationCharges");
List<MasterDetail> masterDetailList = new ArrayList<>();
masterDetailList.add(masterDetail);
ModuleDetail moduleDetail = new ModuleDetail();
moduleDetail.setMasterDetails(masterDetailList);
moduleDetail.setModuleName("BTR");
List<ModuleDetail> moduleDetailList = new ArrayList<>();
moduleDetailList.add(moduleDetail);
MdmsCriteria mdmsCriteria = new MdmsCriteria();
mdmsCriteria.setTenantId(tenantId.split("\\.")[0]);
mdmsCriteria.setModuleDetails(moduleDetailList);
MdmsCriteriaReq mdmsCriteriaReq = new MdmsCriteriaReq();
mdmsCriteriaReq.setMdmsCriteria(mdmsCriteria);
mdmsCriteriaReq.setRequestInfo(requestInfo);
return mdmsCriteriaReq;
}
}
5. Add the following properties in application.properties ﬁle -
#mdms urls
egov.mdms.host=https://dev.digit.org # REPLACE with your environment
name
egov.mdms.search.endpoint=/egov-mdms-service/v1/_search

Add Workflow Configuration
Overview
Workﬂow conﬁguration should be created based on the business requirements. More
details on extracting workﬂow in the design guide.
Steps
For our guide, we will conﬁgure the following workﬂow which was the output of the
design phase:
Workﬂow states, actions and actors
We will re-use the workﬂow service which is deployed in the development/sandbox
environment.
This guide assumes you can call the development environment workﬂow service directly
with a valid auth token.
A sample curl is posted below. Make sure to replace the server hostname and the
username and password in the below statement:

curl --location --request POST
'https://yourserver.digit.org/user/oauth/token' \
--header 'Authorization: Basic ZWdvdi11c2VyLWNsaWVudDo=' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'scope=read' \
--data-urlencode 'username=<mobile_no_of user>' \
--data-urlencode 'password=<pwd_you_created>' \
--data-urlencode 'tenantId=<your_tenant_id>' \
--data-urlencode 'userType=CITIZEN'
1.
In POSTMan, create a new POST request and paste the below content in the body.
The URL for the request is http://yourserver.digit.org/egov-workflow-
v2/egov-wf/businessservice/_create to create the workﬂow.
Make sure to replace the authToken ﬁeld in the body with appropriate auth token in your
environment. Login to the server as a CITIZEN or EMPLOYEE user (depending on which
one you've created) and obtain the authToken from the response body.
In DIGIT, the API Gateway (Zuul) enriches user information based on the auth token for all
requests that go via the gateway. Port forwarding by-passes the API gateway. In this
case, when accessing a service directly, for a request to be valid, a user has to send the
userInfo JSON inside the RequestInfo object. This is true not just for Workﬂow but for any
service. Sample:
"userInfo": {
"id": 24226,
"uuid": "11b0e02b-0145-4de2-bc42-c97b96264807",
"userName": "sample_user",
"roles": [
{ "name": "Citizen", "code": "CITIZEN"}
]
}
Note that UUID and roles can be dummy place-holder entities in this case for local
testing.
2. Below is the URL and POST body for the business service creation request.

http://yourserver.digit.org/egov-workflow-v2/egov-
wf/businessservice/_create

{
"RequestInfo": {
"apiId": "Rainmaker",
"action": "",
"did": 1,
"key": "",
"msgId": "20170310130900|en_IN",
"requesterId": "",
"ts": 1513579888683,
"ver": ".01",
"authToken": "{{devAuth}}"
},
"BusinessServices": [
{
"tenantId": "pb",
"businessService": "BTR",
"business": "birth-registration",
"businessServiceSla": 432000000,
"states": [
{
"sla": null,
"state": null,
"applicationStatus": null,
"docUploadRequired": true,
"isStartState": true,
"isTerminateState": false,
"isStateUpdatable": true,
"actions": [
{
"action": "APPLY",
"nextState": "APPLIED",
"roles": [
"CITIZEN",
"EMPLOYEE"
]
}
]
},
{
"sla": null,
"state": "APPLIED",
"applicationStatus": "APPLIED",
"docUploadRequired": false,
"isStartState": false,
"isTerminateState": true,
"isStateUpdatable": false,
"actions": [
{

"action": "APPROVE",
"nextState": "APPROVED",
"roles": [
"EMPLOYEE"
]
},
{
"action": "REJECT",
"nextState": "REJECTED",
"roles": [
"EMPLOYEE"
]
Integrate Workflow Service
}
]
},
{
"sla": null,
Overview
"state": "APPROVED",
"applicationStatus": "APPROVED",
"docUploadRequired": false,
The birth registration module follows a simple workﬂow derived from the swimlane
"isStartState": false,
diagrams. Please check the design inputs section for correlation as well as the design
"isTerminateState": false,
guide for info on how the workﬂow conﬁguration is derived.
"isStateUpdatable": false,
"actions": [
{
"action": "PAY",
"nextState": "REGISTRATIONCOMPLETED",
Steps
"roles": [
"SYSTEM_PAYMENT",
Integration with workﬂow service requires the following steps -
"CITIZEN",
"EMPLOYEE"
]
1. Create Workﬂow service - Create a class to transition the workﬂow object across
}
its states. For this, create a class by the name of WorkﬂowService.java under the
]
service directory and annotate it with @Service annotation.
},
{
2. Add the below content to this class -
"sla": null,
"state": "REJECTED",
"applicationStatus": "REJECTED",
"docUploadRequired": false,
"isStartState": false,
"isTerminateState": true,
"isStateUpdatable": false,
"actions": null
},
{
"sla": null,
"state": "REGISTRATIONCOMPLETED",
"
li
i
" "
"

"applicationStatus": "REGISTRATIONCOMPLETED",
"docUploadRequired": false,
"isStartState": false,
"isTerminateState": true,
"isStateUpdatable": false,
"actions": null
}
]
}
]
}

package digit.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.config.BTRConfiguration;
import digit.repository.ServiceRequestRepository;
import digit.web.models.*;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.User;
import org.egov.common.contract.workflow.*;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Component
@Slf4j
public class WorkflowService {
@Autowired
private ObjectMapper mapper;
@Autowired
private ServiceRequestRepository repository;
@Autowired
private BTRConfiguration config;
public void updateWorkflowStatus(BirthRegistrationRequest
birthRegistrationRequest) {
birthRegistrationRequest.getBirthRegistrationApplications().forEach(appl
ication -> {
ProcessInstance processInstance =
getProcessInstanceForBTR(application,
birthRegistrationRequest.getRequestInfo());
ProcessInstanceRequest workflowRequest = new
ProcessInstanceRequest(birthRegistrationRequest.getRequestInfo(),
Collections.singletonList(processInstance));
callWorkFlow(workflowRequest);
});

}
public State callWorkFlow(ProcessInstanceRequest workflowReq) {
ProcessInstanceResponse response = null;
StringBuilder url = new
StringBuilder(config.getWfHost().concat(config.getWfTransitionPath()));
Object optional = repository.fetchResult(url, workflowReq);
response = mapper.convertValue(optional,
ProcessInstanceResponse.class);
return response.getProcessInstances().get(0).getState();
}
private ProcessInstance
getProcessInstanceForBTR(BirthRegistrationApplication application,
RequestInfo requestInfo) {
Workflow workflow = application.getWorkflow();
ProcessInstance processInstance = new ProcessInstance();
processInstance.setBusinessId(application.getApplicationNumber());
processInstance.setAction(workflow.getAction());
processInstance.setModuleName("birth-services");
processInstance.setTenantId(application.getTenantId());
processInstance.setBusinessService("BTR");
processInstance.setDocuments(workflow.getDocuments());
processInstance.setComment(workflow.getComments());
if(!CollectionUtils.isEmpty(workflow.getAssignes())){
List<User> users = new ArrayList<>();
workflow.getAssignes().forEach(uuid -> {
User user = new User();
user.setUuid(uuid);
users.add(user);
});
processInstance.setAssignes(users);
}
return processInstance;
}
public ProcessInstance getCurrentWorkflow(RequestInfo requestInfo,
String tenantId, String businessId) {
RequestInfoWrapper requestInfoWrapper =
f
b ild ()
f (
f ) b ild()

RequestInfoWrapper.builder().requestInfo(requestInfo).build();
StringBuilder url = getSearchURLWithParams(tenantId,
businessId);
Object res = repository.fetchResult(url, requestInfoWrapper);
ProcessInstanceResponse response = null;
try{
response = mapper.convertValue(res,
ProcessInstanceResponse.class);
}
catch (Exception e){
throw new CustomException("PARSING_ERROR","Failed to parse
workflow search response");
}
if(response!=null &&
!CollectionUtils.isEmpty(response.getProcessInstances()) &&
response.getProcessInstances().get(0)!=null)
return response.getProcessInstances().get(0);
return null;
}
private BusinessService
getBusinessService(BirthRegistrationApplication application, RequestInfo
requestInfo) {
String tenantId = application.getTenantId();
StringBuilder url = getSearchURLWithParams(tenantId, "BTR");
RequestInfoWrapper requestInfoWrapper =
RequestInfoWrapper.builder().requestInfo(requestInfo).build();
Object result = repository.fetchResult(url, requestInfoWrapper);
BusinessServiceResponse response = null;
try {
response = mapper.convertValue(result,
BusinessServiceResponse.class);
5. Add workﬂow to BirthRegistrationService.
} catch (IllegalArgumentException e) {
6. Add the below ﬁeld to BirthRegistrationService.java
throw new CustomException("PARSING ERROR", "Failed to parse
response of workflow business service search");
}
@Autowired
if (CollectionUtils.isEmpty(response.getBusinessServices()))
private WorkflowService workflowService;
throw new CustomException("BUSINESSSERVICE_NOT_FOUND", "The
businessService " + "BTR" + " is not found");
6. Transition the workﬂow - Modify the following methods in
return response.getBusinessServices().get(0);
BirthRegistrationService.java as follows. Note that we are adding calls into the
}
workﬂow service in each of these methods.

private StringBuilder getSearchURLWithParams(String tenantId, String
registerBtRequest
businessService) {
StringBuilder url = new StringBuilder(config.getWfHost());
public List<BirthRegistrationApplication>
url.append(config.getWfBusinessServiceSearchPath());
registerBtRequest(BirthRegistrationRequest
url.append("?tenantId=");
birthRegistrationRequest) {
url.append(tenantId);
// Validate applications
url.append("&businessServices=");
url.append(businessService);
validator.validateBirthApplication(birthRegistrationRequest);
return url;
}
// Enrich applications
public ProcessInstanceRequest
enrichmentUtil.enrichBirthApplication(birthRegistrationRequest
getProcessInstanceForBirthRegistrationPayment(BirthRegistrationRequest
);
updateRequest) {
// Enrich/Upsert user in upon birth registration
BirthRegistrationApplication application =
userService.callUserService(birthRegistrationRequest);
updateRequest.getBirthRegistrationApplications().get(0);
//
// Initiate workflow for the new application
ProcessInstance process = ProcessInstance.builder()
.businessService("BTR")
workflowService.updateWorkflowStatus(birthRegistrationRequest)
.businessId(application.getApplicationNumber())
;
.comment("Payment for birth registration processed")
.moduleName("birth-services")
// Push the application to the topic for persister to
.tenantId(application.getTenantId())
listen and persist
.action("PAY")
producer.push("save-bt-application",
.build();
birthRegistrationRequest);
return ProcessInstanceRequest.builder()
// Return the response back to user
.requestInfo(updateRequest.getRequestInfo())
return
.processInstances(Arrays.asList(process))
birthRegistrationRequest.getBirthRegistrationApplications();
.build();
}
}
}
updateBtApplication

public BirthRegistrationApplication
updateBtApplication(BirthRegistrationRequest
birthRegistrationRequest) {
// Validate whether the application that is being
requested for update indeed exists
BirthRegistrationApplication existingApplication =
validator.validateApplicationExistence(birthRegistrationReques
t.getBirthRegistrationApplications().get(0));
existingApplication.setWorkflow(birthRegistrationRequest.getBi
rthRegistrationApplications().get(0).getWorkflow());
log.info(existingApplication.toString());
birthRegistrationRequest.setBirthRegistrationApplications(Coll
ections.singletonList(existingApplication));
// Enrich application upon update
enrichmentUtil.enrichBirthApplicationUponUpdate(birthRegistrat
ionRequest);
workflowService.updateWorkflowStatus(birthRegistrationRequest)
;
// Just like create request, update request will be
handled asynchronously by the persister
producer.push("update-bt-application",
birthRegistrationRequest);
return
birthRegistrationRequest.getBirthRegistrationApplications().ge
t(0);
}
searchBtApplications

public List<BirthRegistrationApplication>
searchBtApplications(RequestInfo requestInfo,
BirthApplicationSearchCriteria birthApplicationSearchCriteria)
{
// Fetch applications from database according to the
given search criteria
List<BirthRegistrationApplication> applications =
birthRegistrationRepository.getApplications(birthApplicationSe
archCriteria);
// If no applications are found matching the given
criteria, return an empty list
if(CollectionUtils.isEmpty(applications))
return new ArrayList<>();
// Enrich mother and father of applicant objects
applications.forEach(application -> {
enrichmentUtil.enrichFatherApplicantOnSearch(application);
enrichmentUtil.enrichMotherApplicantOnSearch(application);
});
// Otherwise return the found applications
return applications;
}
7. Conﬁgure application.properties - Add the following properties to
application.properties ﬁle of the birth registration module. Depending on whether
you are port forwarding or using the service directly on the host, please update the
host name.
#Workflow config
is.workflow.enabled=true
egov.workflow.host=http://localhost:8282
egov.workflow.transition.path=/egov-workflow-v2/egov-
wf/process/_transition
egov.workflow.businessservice.search.path=/egov-workflow-v2/egov-
wf/businessservice/_search
egov.workflow.processinstance.search.path=/egov-workflow-v2/egov-
wf/process/_search

8. Run the workﬂow service locally - the application will call into it to create the
necessary tables in the DB and effect the workﬂow transitions.

Integrate URL Shortener Service
Overview
This page runs through the steps involved in integrating the URL shortening service.
Steps
Utility code to talk to the URL shortener resides here:
/utils/UrlShortenerUtil.java
Add the following properties in application.properties for integration -
#url shortner
egov.url.shortner.host=https://dev.digit.org
egov.url.shortner.endpoint=/egov-url-shortening/shortener

## Section 4: Integrate Billing & Payment

Overview
What is a calculator?
In governments, each region, city or state has custom rates and charges for the same
domain. For example, birth certiﬁcate registration rates may differ from city to city
and the way it is computed can also vary. This cannot be generalised into a platform
service. However, billing is a generic platform service.
The way DIGIT solves for this is to "unbundle" the problem and separate out billing
from calculations. A customisable service called a "calculator" is used for custom
calculation. The calculator then calls the billing service to generate the bill. Each
service/module ships with a "default" calculator which can then be customised for
that city.
Resources
Postman collection of btr-calculator is available here.

Custom Calculator Service
Information on creating a custom calculator service
Overview
This calculator service integrates with the core platform's billing service & generates a
demand. A bill can be fetched based on the demand and presented to the user. This
page provides details about creating a custom calculator service.
Steps
Code for the custom calculator service is here . A separate API spec is written for the
calculator service.
A calculator service typically has three APIs:
1. _calculate - This API returns the calculation for a given service application.
2. getbill or createbill - Creates and returns the bill associated with a particular
application.
3. _search - to search for calculations.
The birth registration service calls the _calculate API to generate a demand for birth
registration charges.

Integrate Calculator Service
Calculating costs for a service and raising demand for bill generation
Overview
Calculation
The calculation class contains the calculation logic for the birth certiﬁcate registration
charges. This can vary from city to city. Based on the application submitted, the
calculator class will calculate the tax/charges and call the billing service to generate
the demand.
What is a demand?
A demand is the official communication sent by a government authority to a citizen
requesting them to pay for a service. A demand leads to a bill. When a bill is paid, a
receipt is generated. A demand can be modiﬁed prior to bill generation.
Steps
For our guide, we are going to create a Calculation Service that will call the calculator
to generate a demand. Follow the steps below -
1. Create a class under service folder by the name of CalculationService
2. Annotate this class with @Service annotation and add the following logic within it
-

package digit.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.config.BTRConfiguration;
import digit.repository.ServiceRequestRepository;
import digit.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class CalculationService {
@Autowired
private BTRConfiguration btrConfiguration;
@Autowired
private ObjectMapper mapper;
@Autowired
private ServiceRequestRepository serviceRequestRepository;
public CalculationRes getCalculation(BirthRegistrationRequest
request){
List<CalculationCriteria> calculationCriteriaList = new
ArrayList<>();
for(BirthRegistrationApplication application :
request.getBirthRegistrationApplications()) {
CalculationCriteria calculationCriteria =
CalculationCriteria.builder()
.birthregistrationapplication(application)
.tenantId(application.getTenantId())
.applicationNumber(application.getApplicationNumber())
.build();
calculationCriteriaList.add(calculationCriteria);
}
CalculationReq calculationReq = CalculationReq.builder()
.requestInfo(request.getRequestInfo())
.calculationCriteria(calculationCriteriaList)
.build();
StringBuilder url = new
StringBuilder().append(btrConfiguration.getBtrCalculatorHost())

.append(btrConfiguration.getBtrCalculatorCalculateEndpoint());
Object response = serviceRequestRepository.fetchResult(url,
calculationReq);
CalculationRes calculationRes = mapper.convertValue(response,
CalculationRes.class);
return calculationRes;
}
}

Payment Back Update
Status of payment
Overview
A demand leads to a bill which then leads to payment by a citizen. Once payment is
done, the application status has to be updated. Since we have a microservices
architecture, the two services can communicate with each other either through API
calls or using events.
The collection service publishes an event on a Kafka topic when payment is collected
for an application. Any microservice that wants to get notiﬁed when payments are
done can subscribe to this topic. Once the service consumes the payment message, it
will check if the payment is done for its service by checking the businessService code.
The application status changes to PAID or triggers a workﬂow transition.
Steps
For our guide, follow the steps below to create payment back update consumer -
1. Create a consumer class by the name of PaymentBackUpdateConsumer.
Annotate it with @Component annotation and add the following content to it -

package digit.kafka;
import digit.service.PaymentUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import java.util.HashMap;
@Component
public class PaymentBackUpdateConsumer {
@Autowired
private PaymentUpdateService paymentUpdateService;
@KafkaListener(topics = {"${kafka.topics.receipt.create}"})
public void listenPayments(final HashMap<String, Object> record,
@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
paymentUpdateService.process(record);
}
}
2. Create a new class by the name of PaymentUpdateService in the service folder
and annotate it with @Service. Put the following content in this class -

package digit.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.repository.BirthRegistrationRepository;
import digit.web.models.*;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
@Slf4j
@Service
public class PaymentUpdateService {
@Autowired
private WorkflowService workflowService;
@Autowired
private ObjectMapper mapper;
@Autowired
private BirthRegistrationRepository repository;
public void process(HashMap<String, Object> record) {
try {
PaymentRequest paymentRequest = mapper.convertValue(record,
PaymentRequest.class);
RequestInfo requestInfo = paymentRequest.getRequestInfo();
List<PaymentDetail> paymentDetails =
paymentRequest.getPayment().getPaymentDetails();
String tenantId = paymentRequest.getPayment().getTenantId();
for (PaymentDetail paymentDetail : paymentDetails) {
updateWorkflowForBirthRegistrationPayment(requestInfo,
tenantId, paymentDetail);
}
} catch (Exception e) {
log.error("KAFKA_PROCESS_ERROR:", e);

}
}
private void updateWorkflowForBirthRegistrationPayment(RequestInfo
requestInfo, String tenantId, PaymentDetail paymentDetail) {
Bill bill = paymentDetail.getBill();
BirthApplicationSearchCriteria criteria =
BirthApplicationSearchCriteria.builder()
.applicationNumber(bill.getConsumerCode())
.tenantId(tenantId)
.build();
List<BirthRegistrationApplication>
birthRegistrationApplicationList = repository.getApplications(criteria);
if (CollectionUtils.isEmpty(birthRegistrationApplicationList))
throw new CustomException("INVALID RECEIPT",
"No applications found for the consumerCode " +
criteria.getApplicationNumber());
Role role =
Role.builder().code("SYSTEM_PAYMENT").tenantId(tenantId).build();
requestInfo.getUserInfo().getRoles().add(role);
birthRegistrationApplicationList.forEach( application -> {
BirthRegistrationRequest updateRequest =
BirthRegistrationRequest.builder().requestInfo(requestInfo)
.birthRegistrationApplications(Collections.singletonList(application)).b
uild();
ProcessInstanceRequest wfRequest =
workflowService.getProcessInstanceForBirthRegistrationPayment(updateRequ
est);
State state = workflowService.callWorkFlow(wfRequest);
});
}
}
3. Create the following POJOs under models folder:

PaymentRequest.java
package digit.web.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
@NotNull
@Valid
@JsonProperty("RequestInfo")
private RequestInfo requestInfo;
@NotNull
@Valid
@JsonProperty("Payment")
private Payment payment;
}
Payment.java

package digit.web.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Payment {
@Size(max = 64)
@JsonProperty("id")
private String id;
@NotNull
@Size(max = 64)
@JsonProperty("tenantId")
private String tenantId;
@JsonProperty("totalDue")
private BigDecimal totalDue;
@NotNull
@JsonProperty("totalAmountPaid")
private BigDecimal totalAmountPaid;
@Size(max = 128)
@JsonProperty("transactionNumber")
private String transactionNumber;
@JsonProperty("transactionDate")
private Long transactionDate;
@NotNull
@JsonProperty("paymentMode")
private String paymentMode;
@JsonProperty("instrumentDate")

private Long instrumentDate;
@Size(max = 128)
@JsonProperty("instrumentNumber")
private String instrumentNumber;
@JsonProperty("instrumentStatus")
private String instrumentStatus;
@Size(max = 64)
@JsonProperty("ifscCode")
private String ifscCode;
@JsonProperty("auditDetails")
PaymentDetail.java
private AuditDetails auditDetails;
@JsonProperty("additionalDetails")
private JsonNode additionalDetails;
@JsonProperty("paymentDetails")
@Valid
private List<PaymentDetail> paymentDetails;
@Size(max = 128)
@NotNull
@JsonProperty("paidBy")
private String paidBy;
@Size(max = 64)
@NotNull
@JsonProperty("mobileNumber")
private String mobileNumber;
@Size(max = 128)
@JsonProperty("payerName")
private String payerName;
@Size(max = 1024)
@JsonProperty("payerAddress")
private String payerAddress;
@Size(max = 64)
@JsonProperty("payerEmail")
private String payerEmail;
@Size(max = 64)
@JsonProperty("payerId")
private String payerId;

@JsonProperty("paymentStatus")
private String paymentStatus;
public Payment addpaymentDetailsItem(PaymentDetail paymentDetail) {
if (this.paymentDetails == null) {
this.paymentDetails = new ArrayList<>();
}
this.paymentDetails.add(paymentDetail);
return this;
}
}

package digit.web.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PaymentDetail {
@Size(max = 64)
@JsonProperty("id")
private String id;
@Size(max = 64)
@JsonProperty("tenantId")
private String tenantId;
@JsonProperty("totalDue")
private BigDecimal totalDue;
@NotNull
@JsonProperty("totalAmountPaid")
private BigDecimal totalAmountPaid;
@Size(max = 64)
@JsonProperty("receiptNumber")
private String receiptNumber;
@Size(max = 64)
@JsonProperty("manualReceiptNumber")
private String manualReceiptNumber;
@JsonProperty("manualReceiptDate")
private Long manualReceiptDate;
@JsonProperty("receiptDate")
private Long receiptDate;
@JsonProperty("receiptType")
private String receiptType;

@JsonProperty("businessService")
private String businessService;
@NotNull
@Size(max = 64)
@JsonProperty("billId")
private String billId;
@JsonProperty("bill")
private Bill bill;
@JsonProperty("additionalDetails")
private JsonNode additionalDetails;
@JsonProperty("auditDetails")
private AuditDetails auditDetails;
}
Bill.java

package digit.web.models;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import org.springframework.util.CollectionUtils;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Bill {
@JsonProperty("id")
private String id;
@JsonProperty("mobileNumber")
private String mobileNumber;
@JsonProperty("paidBy")
private String paidBy;
@JsonProperty("payerName")
private String payerName;
@JsonProperty("payerAddress")
private String payerAddress;
@JsonProperty("payerEmail")
private String payerEmail;
@JsonProperty("payerId")
private String payerId;
@JsonProperty("status")
private StatusEnum status;
@JsonProperty("reasonForCancellation")

private String reasonForCancellation;
@JsonProperty("isCancelled")
private Boolean isCancelled;
@JsonProperty("additionalDetails")
private JsonNode additionalDetails;
@JsonProperty("billDetails")
@Valid
private List<BillDetail> billDetails;
@JsonProperty("tenantId")
private String tenantId;
@JsonProperty("auditDetails")
private AuditDetails auditDetails;
@JsonProperty("collectionModesNotAllowed")
private List<String> collectionModesNotAllowed;
@JsonProperty("partPaymentAllowed")
private Boolean partPaymentAllowed;
@JsonProperty("isAdvanceAllowed")
private Boolean isAdvanceAllowed;
@JsonProperty("minimumAmountToBePaid")
private BigDecimal minimumAmountToBePaid;
@JsonProperty("businessService")
private String businessService;
@JsonProperty("totalAmount")
private BigDecimal totalAmount;
@JsonProperty("consumerCode")
private String consumerCode;
@JsonProperty("billNumber")
private String billNumber;
@JsonProperty("billDate")
private Long billDate;
@JsonProperty("amountPaid")
private BigDecimal amountPaid;

public enum StatusEnum {
ACTIVE("ACTIVE"),
CANCELLED("CANCELLED"),
PAID("PAID"),
EXPIRED("EXPIRED");
private String value;
StatusEnum(String value) {
this.value = value;
BillDetail.java
}
@Override
@JsonValue
public String toString() {
return String.valueOf(value);
}
public static boolean contains(String test) {
for (StatusEnum val : StatusEnum.values()) {
if (val.name().equalsIgnoreCase(test)) {
return true;
}
}
return false;
}
@JsonCreator
public static StatusEnum fromValue(String text) {
for (StatusEnum b : StatusEnum.values()) {
if (String.valueOf(b.value).equals(text)) {
return b;
}
}
return null;
}
}
public Boolean addBillDetail(BillDetail billDetail) {
if (CollectionUtils.isEmpty(billDetails)) {

billDetails = new ArrayList<>();
return billDetails.add(billDetail);
} else {
if (!billDetails.contains(billDetail))
return billDetails.add(billDetail);
else
return false;
}
}
}

package digit.web.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import org.springframework.util.CollectionUtils;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class BillDetail {
@JsonProperty("id")
private String id;
@JsonProperty("tenantId")
private String tenantId;
@JsonProperty("demandId")
private String demandId;
@JsonProperty("billId")
private String billId;
@JsonProperty("amount")
@NotNull
private BigDecimal amount;
@JsonProperty("amountPaid")
private BigDecimal amountPaid;
@NotNull
@JsonProperty("fromPeriod")
private Long fromPeriod;
@NotNull
@JsonProperty("toPeriod")
private Long toPeriod;

@JsonProperty("additionalDetails")
private JsonNode additionalDetails;
@JsonProperty("channel")
private String channel;
BillAccountDetail.java
@JsonProperty("voucherHeader")
private String voucherHeader;
@JsonProperty("boundary")
private String boundary;
@JsonProperty("manualReceiptNumber")
private String manualReceiptNumber;
@JsonProperty("manualReceiptDate")
private Long manualReceiptDate;
@JsonProperty("billAccountDetails")
private List<BillAccountDetail> billAccountDetails;
@NotNull
@JsonProperty("collectionType")
private String collectionType;
@JsonProperty("auditDetails")
private AuditDetails auditDetails;
private String billDescription;
@NotNull
@JsonProperty("expiryDate")
private Long expiryDate;
public Boolean addBillAccountDetail(BillAccountDetail
billAccountDetail) {
if (CollectionUtils.isEmpty(billAccountDetails)) {
billAccountDetails = new ArrayList<>();
return billAccountDetails.add(billAccountDetail);
} else {
if (!billAccountDetails.contains(billAccountDetail))
return billAccountDetails.add(billAccountDetail);
else
return false;
}

}
}
}

package digit.web.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BillAccountDetail {
@Size(max = 64)
@JsonProperty("id")
private String id;
@Size(max = 64)
@JsonProperty("tenantId")
private String tenantId;
@Size(max = 64)
@JsonProperty("billDetailId")
private String billDetailId;
@Size(max = 64)
@JsonProperty("demandDetailId")
private String demandDetailId;
@JsonProperty("order")
private Integer order;
@JsonProperty("amount")
private BigDecimal amount;
@JsonProperty("adjustedAmount")
private BigDecimal adjustedAmount;
@JsonProperty("isActualDemand")
private Boolean isActualDemand;
@Size(max = 64)
@JsonProperty("taxHeadCode")

private String taxHeadCode;
@JsonProperty("additionalDetails")
private JsonNode additionalDetails;
@JsonProperty("auditDetails")
private AuditDetails auditDetails;
}

## Section 5: Other Advanced Integrations

Add Indexer Configuration
Overview
The indexer is designed to perform all the indexing tasks of the DIGIT platform. The
service reads records posted on speciﬁc Kafka topics and picks the corresponding
index conﬁguration from the yaml ﬁle provided by the respective module
conﬁguration. Conﬁgurations are yaml based. A detailed guide to creating indexer
conﬁgs is mentioned in the following document - Indexer Conﬁguration Guide.
Steps
1. Create a new ﬁle under egov-indexer in configs repo by the name of digit-
developer-guide.yml and place the below content into it -

ServiceMaps:
serviceName: Birth Registration Service
version: 1.0.0
mappings:
- topic: save-bt-application
configKey: INDEX
indexes:
- name: btindex-v1
type: _doc
id: $.id
isBulk: true
timeStampField: $.auditDetails.createdTime
jsonPath: $.BirthRegistrationApplications
customJsonMapping:
indexMapping: {"Data":{"birthapplication":{},"history":{}}}
fieldMapping:
- inJsonPath: $
outJsonPath: $.Data.birthapplication
externalUriMapping:
- path: http://localhost:8282/egov-workflow-v2/egov-
wf/process/_search
queryParam:
businessIds=$.applicationNumber,history=true,tenantId=$.tenantId
apiRequest: {"RequestInfo":
{"apiId":"org.egov.pt","ver":"1.0","ts":1502890899493,"action":"asd","di
d":"4354648646","key":"xyz","msgId":"654654","requesterId":"61","authTok
en":"d9994555-7656-4a67-ab3a-a952a0d4dfc8","userInfo":
{"id":1,"uuid":"1fec8102-0e02-4d0a-b283-
cd80d5dab067","type":"EMPLOYEE","tenantId":"pb.amritsar","roles":
[{"name":"Employee","code":"EMPLOYEE","tenantId":"pb.amritsar"}]}}}
uriResponseMapping:
- inJsonPath: $.ProcessInstances
outJsonPath: $.Data.history
- topic: update-bt-application
configKey: INDEX
indexes:
- name: btindex-v1
type: general
id: $.id
isBulk: true
timeStampField: $.auditDetails.createdTime
jsonPath: $.BirthRegistrationApplications
customJsonMapping:
indexMapping: {"Data":{"birthapplication":{},"history":{}}}
fieldMapping:
- inJsonPath: $
outJsonPath: $.Data.birthapplication

externalUriMapping:
- path: http://egov-workflow-v2.egov:8080/egov-workflow-
v2/egov-wf/process/_search
queryParam:
businessIds=$.applicationNumber,history=true,tenantId=$.tenantId
apiRequest: {"RequestInfo":
{"apiId":"org.egov.pt","ver":"1.0","ts":1502890899493,"action":"asd","di
d":"4354648646","key":"xyz","msgId":"654654","requesterId":"61","authTok
en":"d9994555-7656-4a67-ab3a-a952a0d4dfc8","userInfo":
{"id":1,"uuid":"1fec8102-0e02-4d0a-b283-
cd80d5dab067","type":"EMPLOYEE","tenantId":"pb.amritsar","roles":
[{"name":"Employee","code":"EMPLOYEE","tenantId":"pb.amritsar"}]}}}
uriResponseMapping:
- inJsonPath: $.ProcessInstances
outJsonPath: $.Data.history
Note: Follow the steps below when the code is deployed to the DIGIT environment.
These steps are not applicable for deployment in the local environment. You may
choose to follow these when you build and deploy.
Indexer Configuration Deployment
2. Navigate to the forked DIGIT-DevOps repository. Under the deploy-as-
code/helm/environments directory, ﬁnd the deployment helm chart that was used
to deploy DIGIT.
3.
In the deployment helm chart (which was used to set up the DIGIT environment),
ﬁnd "egov-indexer". Find the "egov-indexer-yaml-repo-path" property and add the
path to your new indexer ﬁle here. The code block is shown below for reference:

egov-indexer:
replicas: 1
images:
- egovio/egov-indexer
db_migration_image: egovio/egov-indexer-db
initContainers:
gitSync:
repo: "git@github.com:egovernments/configs"
branch: "DEV"
egov-indexer-yaml-repo-path: "file:///work-dir/configs/egov-
indexer/privacy-audit.yaml,file:///work-dir/configs/egov-
indexer/billingservices-indexer.yml,file:///work-dir/configs/egov-
indexer/collection-indexer.yml,file:///work-dir/configs/egov-
indexer/egov-telemetry-indexer.yml,file:///work-dir/configs/egov-
indexer/egov-uploader-indexer.yml,file:///work-dir/configs/egov-
indexer/error-queue.yml,file:///work-dir/configs/egov-indexer/finance-
rolloutadotpion-indexer.yml,file:///work-dir/configs/egov-
indexer/payment-indexer.yml,file:///work-dir/configs/egov-indexer/pgr-
services.yml,file:///work-dir/configs/egov-indexer/rainmaker-pgr-
indexer.yml,file:///work-dir/configs/egov-indexer/rainmaker-pt-
indexer.yml,file:///work-dir/configs/egov-indexer/rainmaker-tl-
indexer.yml,file:///work-dir/configs/egov-indexer/chatbot-
telemetry.yaml,file:///work-dir/configs/egov-indexer/water-
service.yml,file:///work-dir/configs/egov-indexer/water-services-
meter.yml,file:///work-dir/configs/egov-indexer/sewerage-
service.yml,file:///work-dir/configs/egov-indexer/property-
services.yml,file:///work-dir/configs/egov-indexer/chatbot-telemetry-
v2.yaml,file:///work-dir/configs/egov-indexer/egov-
fsm.yaml,file:///work-dir/configs/egov-indexer/egov-
vehicle.yaml,file:///work-dir/configs/egov-indexer/egov-
vendor.yaml,file:///work-dir/configs/egov-indexer/egov-url-shortening-
indexer.yaml,file:///work-dir/configs/egov-indexer/fire-noc-
service.yml,file:///work-dir/configs/egov-indexer/egov-
echallan.yml,file:///work-dir/configs/egov-indexer/egov-bpa-
indexer.yml,file:///work-dir/configs/egov-indexer/edcr-
indexer.yml,file:///work-dir/configs/egov-indexer/rainmaker-birth-
indexer.yml,file:///work-dir/configs/egov-indexer/rainmaker-death-
indexer.yml"
4. Raise a PR to the DevOps branch which was forked/used to create the
deployment. Once that is merged, restart the indexer service and make sure the
cluster conﬁgs are propagated.

Certificate Generation
Overview
The ﬁnal step in this process is the creation of conﬁgurations to create a voter
registration PDF for the citizens to download. For this, we will make use of DIGIT’s PDF
service which uses PDFMake and Mustache libraries to generate PDF. Detailed
documentation on generating PDFs using PDF service is available here.
Steps
Follow the below steps to set up PDF service locally and generate PDF for our voter
registration service -
1. Clone DIGIT Services repo.
> git clone -o upstream https://github.com/egovernments/DIGIT-OSS
2. Clone Conﬁgs repo.
> git clone -o upstream https://github.com/egovernments/configs
3. Navigate to the DIGIT-Dev repo and open up a terminal. Checkout
DIGIT_DEVELOPER_GUIDE branch.
> cd DIGIT-Dev
> git checkout DIGIT_DEVELOPER_GUIDE
4. Navigate to the conﬁgs folder and under pdf-service data conﬁg. Create ﬁle by the
name of digit-developer-guide.json

> cd configs/pdf-service/data-config
> touch digit-developer-guide.json
5. Add the following content in this newly created data conﬁg ﬁle -

{
"key": "btcertificate",
"DataConfigs": {
"serviceName": "rainmaker-common",
"version": "1.0.0",
"baseKeyPath": "$.BirthRegistrationApplications.*",
"entityIdPath":"$.id",
"isCommonTableBorderRequired": true,
"mappings": [
{
"mappings": [
{
"direct": [
{
"variable": "logoImage",
"url":"https://raw.githubusercontent.com/egovernments/egov-web-
app/master/web/rainmaker/dev-packages/egov-ui-kit-
dev/src/assets/images/pblogo.png",
"type":"image"
},
{
"variable": "applicantName",
"value": {
"path": "$.babyFirstName"
}
},
{
"variable": "applicationNo",
"value": {
"path": "$.applicationNumber"
}
},
{
"variable": "address",
"value": {
"path": "$.address.city"
}
},
{
"variable": "birthIdIssueDate",
"value": {
"path": "$.auditDetails.createdTime"
},
"type": "date"
},
{
"variable": "signedCertificateData",

"value": {
"path": "$.signedCertificate"
}
},
{
"variable": "to",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_RECEIPT_TO"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "municipal_corportaion",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_MUNICIPAL_CORPORATION
"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "corporation_contact",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_LICENSE_CORPORATION_C
ONTACT"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "corporation_website",
"value": {
"
h"

"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_LICENSE_CORPORATION_W
EBSITE"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "corporation_email",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_LICENSE_CORPORATION_E
MAIL"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "application_no",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_APPLICATION_NO"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "reciept_no",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_RECIEPT_NO"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker common"

module : rainmaker-common
}
},
{
"variable": "financial_year",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_FINANCIAL_YEAR"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "trade_name",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_TRADE_NAME"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "trade_owner_name",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_TRADE_OWNER_NAME"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "trade_owner_contact",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_TRADE_OWNER_CONTACT"
},
"type": "label",

yp
,
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "trade_address",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_TRADE_ADDRESS"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "trade_type",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_TRADE_TYPE"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "accessories_label",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_ACCESSORIES_LABEL"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "trade_license_fee",
"value": {

"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_TRADE_LICENSE_FEE"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "license_issue_date",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_LICENSE_ISSUE_DATE"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "license_validity",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_LICENSE_VALIDITY"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
},
{
"variable": "approved_by",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_APPROVED_BY"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
}

},
{
"variable": "commissioner",
"value": {
"path":
"PDF_STATIC_LABEL_CONSOLIDATED_TLCERTIFICATE_COMMISSIONER"
},
"type": "label",
"localisation":{
"required":true,
"prefix": null,
"module":"rainmaker-common"
}
}
]
},
{
"externalAPI": [
{
"path": "http://localhost:8082/egov-mdms-
service/v1/_get",
"queryParam":
"moduleName=tenant&masterName=tenants&tenantId=pb&filter=%5B?
(@.code=='{$.tenantId}')%5D",
"apiRequest": null,
"responseMapping":[
{
"variable":"ulb-address",
"value":"$.MdmsRes.tenant.tenants[0].address"
},
{
"variable":"corporationContact",
"value":"$.MdmsRes.tenant.tenants[0].contactNumber"
},
{
"variable":"corporationWebsite",
"value":"$.MdmsRes.tenant.tenants[0].domainUrl"
},
{
"variable":"corporationEmail",
"value":"$.MdmsRes.tenant.tenants[0].emailId"
}
]
},
{
"path":
"http://localhost:8288/filestore/v1/files/url"

6. Navigate to the format-conﬁg within the pdf service folder. Create a ﬁle by the
http://localhost:8288/filestore/v1/files/url ,
"queryParam":
name of digit-developer-guide.json and add the below content to it -
"tenantId=pb,fileStoreIds=$.tradeLicenseDetail.applicationDocument
s[?(@.documentType== 'OWNERPHOTO')].fileStoreId",
"apiRequest": null,
"requesttype": "GET",
"responseMapping":[
{
"variable":"userpic",
"value":"$.fileStoreIds[0].url",
"type": "image"
}
]
},
{
"path": "http://localhost:8282/egov-workflow-
v2/egov-wf/process/_search",
"queryParam":
"businessIds=$.applicationNumber,history=true,tenantId=$.tenantId"
,
"apiRequest": null,
"responseMapping":[
{
"variable":"approvedBy",
"value":"$.ProcessInstances[?(@.action ==
'APPROVE')].assigner.name"
}
]
}
]
},
{
"qrcodeConfig": [
{
"variable": "qrCode",
"value": "{{signedCertificateData}}"
}
]
}
]
}
]
}
}

{
"key": "vtcertificate",
"config": {
"defaultStyle": {
"font": "Cambay"
},
"content": [
{
"image": "{{qrCode}}",
"absolutePosition" : {
"x" : 480,
"y" : 5
},
"width": 100,
"height": 100
},
{
"style":"noc-head",
"table":{
"widths":[
"*"
],
"body":[
[
{
"image": "{{userpic}}",
"width": 70,
"height": 82,
"alignment": "center",
"margin": [
0,
10,
0,
]
}
],
[
{
"stack": [
{
"text":"{{municipal_corportaion}}",
"style":"receipt-logo-header"
},
{
"text":"{{ulb-address}}",
"style":"receipt-logo-sub-header"
},

{
"style": "noc-head",
"table": {
"widths": [
"*",
"*"
],
"body": [
[
{
"text": "{{corporation_contact}} :
{{corporationContact}} ",
"style": "receipt-sub-address-sub-
header"
}
]
]
},
"layout": "noBorders"
},
{
"style": "noc-head",
"table": {
"widths": [
"*",
"*"
],
"body": [
[
{
"text": "{{corporation_website}} :
{{corporationWebsite}}",
"style": "receipt-sub-website-sub-
header"
}
]
]
},
"layout": "noBorders"
},
{
"style": "noc-head",
"table": {
"widths": [
"*",
"*"
]

],
"body": [
[
{
"text": "{{corporation_email}} :
{{corporationEmail}}",
"style": "receipt-sub-email-sub-
header"
}
]
]
},
"layout": "noBorders"
}
],
"alignment":"left",
"margin":[
0,
10,
0,
]
}
],
[
{
"stack": [
{
"text":"Birth Certificate",
"style":"receipt-sub-logo-header"
},
{
"style":"noc-head",
"table":{
"widths":[
"35%",
"65%"
],
"body":[
[
{
"text":"Applicant Name",
"style":"receipt-sub-logo-sub-header"
},
{
"text":"{{applicantName}}",
"style":"receipt-sub-logo-sub-header"
}

}
]
]
},
"layout":"noBorders"
},
{
"style":"noc-head",
"table":{
"widths":[
"35%",
"65%"
],
"body":[
[
{
"text":"Application Number",
"style":"receipt-sub-logo-sub-header"
},
{
"text":"{{applicationNo}}",
"style":"receipt-sub-logo-sub-header"
}
]
]
},
"layout":"noBorders"
},
{
"style":"noc-head",
"table":{
"widths":[
"35%",
"65%"
],
"body":[
[
{
"text":"Applicant Address",
"style":"receipt-sub-logo-sub-header"
},
{
"text":"{{address}}",
"style":"receipt-sub-logo-sub-header"
}
]
]
},
"layout":"noBorders"

},
{
y
"style":"noc-head",
"table":{
"widths":[
"35%",
"65%"
],
"body":[
[
{
"text":"Certificate Issue Date",
"style":"receipt-sub-logo-sub-header"
},
{
"text":"{{birthIdIssueDate}}",
"style":"receipt-sub-logo-sub-header"
}
]
]
},
"layout":"noBorders"
}
],
"alignment":"left",
"margin":[
0,
10,
0,
]
}
]
]
},
"layout":"noBorders"
},
{
"style":"receipt-approver",
"columns": [
{
"text":[
{
"text":"{{approved_by}} ",
"bold": true
},
{
"text":" {{approvedBy}}",

"bold": false
}
],
"alignment":"left"
},
{
"text":[
{
"text":"{{commissioner}}",
"bold": true
}
],
"alignment":"right"
}
]
}
],
"styles": {
"noc-head": {
"margin": [
-30,
-35,
0,
-2
]
},
"receipt-approver": {
"color": "#000000",
"fontSize": 14,
"letterSpacing": 0.6,
"alignment": "center",
"margin": [
-10,
50,
0,
]
},
"receipt-logo-header": {
"color": "#000000",
"fontSize": 20,
"letterSpacing": 0.74,
"alignment": "center",
"margin": [
0,
0,
0,
]

]
},
"receipt-sub-logo-header": {
"color": "#000000",
"fontSize": 18,
"letterSpacing": 0.74,
"alignment": "center",
"margin": [
0,
5,
0,
]
},
"receipt-logo-sub-header": {
"color": "#484848",
"fontSize": 14,
"letterSpacing": 0.6,
"alignment": "center",
"margin": [
0,
5,
0,
]
},
"receipt-sub-logo-sub-header": {
"color": "#484848",
"fontSize": 14,
7. Open the PDF service (under core-services repository of DIGIT-Dev) on your IDE.
"letterSpacing": 0.6,
"alignment": "left",
Open Environment.js ﬁle and change the following properties to point to the
local conﬁg ﬁles created. For example, in my local setup I have pointed these to
"margin": [
50,
the local ﬁles that I created -
40,
0,
},
]
DATA_CONFIG_URLS: "file:///eGov/configs/pdf-service/data-config/digit-
developer-guide.json",
FORMAT_CONFIG_URLS: "file:///eGov/configs/pdf-service/format-
config/digit-developer-guide.json"
"fontSize": 14,
"receipt-sub-address-sub-header": {
"color": "#484848",
"letterSpacing": 0.1,
8. Make sure that Kafka and Workﬂow services are running locally and port-forward
"alignment": "right",
the following services -
"margin": [
50,
• egov-user to port 8284
30,
-90,
• egov-localization to port 8286
]

]
• egov-ﬁlestore to 8288
},
• egov-mdms to 8082
"receipt-sub-website-sub-header": {
"color": "#484848",
9. PDF service is now ready to be started up. Execute the following commands to
"fontSize": 14,
start it up
"letterSpacing": 0.1,
"alignment": "right",
"margin": [
50,
30,
> npm install
> npm run dev
-120,
10. Once PDF service is up hit the following cURL to look at the created PDF -
]
},
"receipt-sub-email-sub-header": {
"color": "#484848",
"fontSize": 14,
"letterSpacing": 0.1,
"alignment": "right",
"margin": [
50,
30,
-110,
]
}
}
}
}

curl --location --request POST 'http://localhost:8081/pdf-
service/v1/_createnosave?key=vtcertificate&tenantId=pb' \
--header 'authority: dev.digit.org' \
--header 'sec-ch-ua: " Not;A Brand";v="99", "Google Chrome";v="91",
"Chromium";v="91"' \
--header 'accept: application/json' \
--header 'dnt: 1' \
--header 'sec-ch-ua-mobile: ?0' \
--header 'user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36
(KHTML, like Gecko) Chrome/91.0.4472.101 Safari/537.36' \
--header 'content-type: application/json;charset=UTF-8' \
--header 'origin: https://dev.digit.org' \
--header 'sec-fetch-site: same-origin' \
--header 'sec-fetch-mode: cors' \
--header 'sec-fetch-dest: empty' \
--header 'referer: https://dev.digit.org/employee/tradelicence/search-
preview?applicationNumber=PB-TL-2021-07-13-006531&tenantId=pb.amritsar'
\
--header 'accept-language: en-GB,en-US;q=0.9,en;q=0.8' \
--header 'cookie: _ga=GA1.2.1990427088.1605864396;
amplitude_id_fef1e872c952688acd962d30aa545b9edigit.org=eyJkZXZpY2VJZCI6I
jQzYzEyMDE0LTNhNTYtNGRiMS1iNDQzLTA5NWU2Zjc3ZGU2MlIiLCJ1c2VySWQiOm51bGwsI
m9wdE91dCI6ZmFsc2UsInNlc3Npb25JZCI6MTYyMjUyNjY2NTkxMywibGFzdEV2ZW50VGltZ
SI6MTYyMjUyNjcyMTQyNywiZXZlbnRJZCI6MiwiaWRlbnRpZnlJZCI6MSwic2VxdWVuY2VOd
W1iZXIiOjN9' \
--data-raw '{
"RequestInfo": {
"apiId": "Rainmaker",
"ver": ".01",
"action": "_get",
"did": "1",
"key": "",
"msgId": "20170310130900|en_IN",
"requesterId": "",
"authToken": "2c21ee1f-2b26-47bf-8ce8-39cc88b0ca3f",
"responseType": "arraybuffer",
"userInfo": {
"id": 24226,
"uuid": "40dceade-992d-4a8f-8243-19dda76a4171",
"userName": "amr001",
"name": "leela",
"mobileNumber": "9814424443",
"emailId": "leela@llgmail.com",
"locale": null,
"type": "EMPLOYEE",
"roles": [
{
"name": "PT Doc Verifier",

"code": "PT_DOC_VERIFIER",
"tenantId": "pb.amritsar"
},
{
"name": "PT Counter Employee",
"code": "PT_CEMP",
"tenantId": "pb.amritsar"
},
{
"name": "PT Field Inspector",
"code": "PT_FIELD_INSPECTOR",
"tenantId": "pb.amritsar"
},
{
"name": "PT Counter Approver",
"code": "PT_APPROVER",
"tenantId": "pb.amritsar"
},
{
"name": "Property Approver",
"code": "Property Approver",
"tenantId": "pb.amritsar"
},
{
"name": "CSC Collection Operator",
"code": "CSC_COLL_OPERATOR",
"tenantId": "pb.amritsar"
},
{
"name": "Employee",
"code": "EMPLOYEE",
"tenantId": "pb.amritsar"
},
{
"name": "NoC counter employee",
"code": "NOC_CEMP",
"tenantId": "pb.amritsar"
},
{
"name": "TL Counter Employee",
"code": "TL_CEMP",
"tenantId": "pb.amritsar"
},
{
"name": "Anonymous User",
"code": "ANONYMOUS",
"tenantId": "pb"
},
{

{
"name": "TL Field Inspector",
"code": "TL_FIELD_INSPECTOR",
"tenantId": "pb.amritsar"
},
{
"name": "TL Creator",
"code": "TL_CREATOR",
"tenantId": "pb.amritsar"
},
{
"name": "NoC counter Approver",
"code": "NOC_APPROVER",
"tenantId": "pb.amritsar"
},
{
"name": "TL Approver",
"code": "TL_APPROVER",
"tenantId": "pb.amritsar"
},
{
"name": "Super User",
"code": "SUPERUSER",
"tenantId": "pb"
},
{
"name": "BPA Services Approver",
"code": "BPA_APPROVER",
"tenantId": "pb.amritsar"
},
{
"name": "Field Employee",
"code": "FEMP",
"tenantId": "pb.amritsar"
},
{
"name": "Counter Employee",
"code": "CEMP",
"tenantId": "pb.amritsar"
},
{
"name": "NoC Field Inpector",
"code": "NOC_FIELD_INSPECTOR",
"tenantId": "pb.amritsar"
},
{
"name": "Super User",
"code": "SUPERUSER",
"tenantId": "pb amritsar"

tenantId : pb.amritsar
},
{
"name": "Grievance Officer",
"code": "GO",
"tenantId": "pb.amritsar"
},
{
"name": "NoC Doc Verifier",
"code": "NOC_DOC_VERIFIER",
"tenantId": "pb.amritsar"
},
{
"name": "Collection Operator",
"code": "COLL_OPERATOR",
"tenantId": "pb.amritsar"
},
{
"name": "TL doc verifier",
"code": "TL_DOC_VERIFIER",
"tenantId": "pb.amritsar"
}
],
"active": true,
"tenantId": "pb.amritsar"
}
},
"BirthRegistrationApplications": [
{
"id": "49e39cd5-a907-44ab-b654-1ff1bd121251",
"tenantId": "pb.amritsar",
"applicationNumber": "PB-BTR-2022-09-07-000377",
"babyFirstName": "Rahul",
"babyLastName": "Singh",
"fatherMobileNumber": null,
"motherMobileNumber": null,
"doctorName": "Dr. Ram",
"hospitalName": "Fortis",
"placeOfBirth": "Palampur",
"timeOfBirth": 12072001,
"address": {
"tenantId": "pb.amritsar",
"doorNo": "1010",
"latitude": 0.0,
"longitude": 0.0,
"addressId": null,
"addressNumber": "34 GA",
"type": "RESIDENTIAL",
"addressLine1": "KP Layout",

y
,
"addressLine2": "",
"landmark": "Petrol pump",
"city": "Amritsar",
"pincode": "143501",
"detail": "adetail",
"buildingName": "Avigna Residence",
"street": "12th Main",
"locality": null,
"registrationId": "aregistrationid",
"id": "c5b2c706-7f49-4948-b1b4-4554eff54cb0"
},
"fatherOfApplicant": {
"id": "61646440-d184-48f2-92b3-1b50a7babcf3",
"userName": null,
"password": null,
"salutation": null,
"name": null,
"gender": null,
"mobileNumber": null,
"emailId": null,
"altContactNumber": null,
"pan": null,
"aadhaarNumber": null,
"permanentAddress": null,
"permanentCity": null,
"permanentPincode": null,
"correspondenceCity": null,
"correspondencePincode": null,
"correspondenceAddress": null,
"active": null,
"dob": null,
"pwdExpiryDate": null,
"locale": null,
"type": null,
"signature": null,
"accountLocked": null,
"roles": null,
"fatherOrHusbandName": null,
"bloodGroup": null,
"identificationMark": null,
"photo": null,
"createdBy": null,
"createdDate": null,
"lastModifiedBy": null,
"lastModifiedDate": null,
"otpReference": null,
"tenantId": null
},

"motherOfApplicant": {
Note: Follow the steps below when the code is deployed to the DIGIT environment.
"id": "99856298-68ba-479f-aa31-f2f4954102f6",
These steps are not applicable for deployment in the local environment. You may
"userName": null,
choose to follow these when you build and deploy.
"password": null,
"salutation": null,
"name": null,
"gender": null,
Deploy PDF Service
"mobileNumber": null,
"emailId": null,
"altContactNumber": null,
• Navigate to the forked DIGIT-DevOps repository.
"pan": null,
"aadhaarNumber": null,
• Find the deployment helm chart that was used to deploy DIGIT within the deploy-
"permanentAddress": null,
as-code/helm/environments directory.
"permanentCity": null,
• Find "pdf-service"in the deployment helm chart (which was used to set up the
"permanentPincode": null,
"correspondenceCity": null,
DIGIT environment).
"correspondencePincode": null,
• Find the "data-config-urls" property.
"correspondenceAddress": null,
"active": null,
• Add the path to your new PDF conﬁg ﬁle here. For this module, we have added
"dob": null,
file:///work-dir/configs/pdf-service/data-config/digit-developer-
"pwdExpiryDate": null,
"locale": null,
guide.json to the end of the data-config-urls . The code block is shown below
for reference:
"type": null,
"signature": null,
"accountLocked": null,
"roles": null,
"fatherOrHusbandName": null,
"bloodGroup": null,
"identificationMark": null,
"photo": null,
"createdBy": null,
"createdDate": null,
"lastModifiedBy": null,
"lastModifiedDate": null,
"otpReference": null,
"tenantId": null
},
"auditDetails": {
"createdBy": "11b0e02b-0145-4de2-bc42-c97b96264807",
"lastModifiedBy": "11b0e02b-0145-4de2-bc42-
c97b96264807",
"createdTime": 1662545605812,
"lastModifiedTime": 1662545605812
},
"workflow": null
}
]
}'

pdf-service:
replicas: 1
initContainers:
gitSync:
repo: "git@github.com:egovernments/configs"
branch: "DEV"
data-config-urls: "file:///work-dir/configs/pdf-service/data-
config/tradelicense-receipt.json,file:///work-dir/configs/pdf-
service/data-config/property-receipt.json,file:///work-dir/configs/pdf-
service/data-config/property-bill.json,file:///work-dir/configs/pdf-
service/data-config/tradelicense-bill.json,file:///work-dir/configs/pdf-
service/data-config/firenoc-receipt.json,file:///work-dir/configs/pdf-
service/data-config/pt-receipt.json,file:///work-dir/configs/pdf-
service/data-config/tl-receipt.json,file:///work-dir/configs/pdf-
service/data-config/consolidatedbill.json,file:///work-dir/configs/pdf-
service/data-config/consolidatedreceipt.json,file:///work-
dir/configs/pdf-service/data-config/tlapplication.json,file:///work-
dir/configs/pdf-service/data-config/ws-
consolidatedacknowlegment.json,file:///work-dir/configs/pdf-
service/data-config/ws-consolidatedsewerageconnection.json,file:///work-
dir/configs/pdf-service/data-config/tlcertificate.json,file:///work-
dir/configs/pdf-service/data-config/buildingpermit.json,file:///work-
dir/configs/pdf-service/data-
config/ptmutationcertificate.json,file:///work-dir/configs/pdf-
service/data-config/tlrenewalcertificate.json,file:///work-
dir/configs/pdf-service/data-config/bpa-revocation.json,file:///work-
dir/configs/pdf-service/data-config/ws-
applicationsewerage.json,file:///work-dir/configs/pdf-service/data-
config/ws-applicationwater.json,file:///work-dir/configs/pdf-
service/data-config/buildingpermit-low.json,file:///work-
dir/configs/pdf-service/data-config/misc-receipt.json,file:///work-
dir/configs/pdf-service/data-config/ws-
estimationnotice.json,file:///work-dir/configs/pdf-service/data-
config/ws-sanctionletter.json,file:///work-dir/configs/pdf-service/data-
config/ws-bill.json,file:///work-dir/configs/pdf-service/data-config/ws-
onetime-receipt.json,file:///work-dir/configs/pdf-service/data-
config/occupancy-certificate.json, file:///work-dir/configs/pdf-
service/data-config/bill-amendment.json, file:///work-dir/configs/pdf-
service/data-config/bill-amendment-note.json, file:///work-
dir/configs/pdf-service/data-config/fsm-receipt.json, file:///work-
dir/configs/pdf-service/data-config/sewerage-bill-amendment-note.json,
file:///work-dir/configs/pdf-service/data-config/mcollect-bill.json,
file:///work-dir/configs/pdf-service/data-config/mcollect-
challan.json,file:///work-dir/configs/pdf-service/data-config/birth-
certificate-pdf.json, file:///work-dir/configs/pdf-service/data-
config/death-certificate.json,file:///work-dir/configs/pdf-service/data-
config/ws-waterdisconnection.json,file:///work-dir/configs/pdf-
service/data-config/ws-sewagedisconnection.json,file:///work-

dir/configs/pdf-service/data-config/ws-
waterdisconnectionnotice.json,file:///work-dir/configs/pdf-service/data-
config/ws-seweragedisconnectionnotice.json,file:///work-dir/configs/pdf-
service/data-config/ws-sewerageconnectiondetails.json,file:///work-
dir/configs/pdf-service/data-config/ws-waterconnectiondetails-
metered.json,file:///work-dir/configs/pdf-service/data-config/ws-
waterconnectiondetails-nonmetered.json,file:///work-dir/configs/pdf-
service/data-config/digit-developer-guide.json"
• Raise a PR for this to the appropriate branch of DevOps which was forked/used to
create the deployment.
• Restart the PDF service in the k8s cluster, once the PR is merged. It will pick up the
latest conﬁg from the ﬁle above.

## Section 6: Run Final Application

Run the deployed application in the local environment
Overview
It is time to test our completed application! Follow the steps on this page to test and
run the deployed application.
Steps
Before testing our application, we need to run a couple of services locally -
1. To run the persister service locally -
• Run Kafka.
• Open egov-persister folder which should be present under core-services
repository.
• Update the following properties in application.properties ﬁle -
spring.datasource.url=jdbc:postgresql://localhost:5432/{LOCAL_DAT
ABASE_NAME}
egov.persist.yml.repo.path=file://{ABSOLUTE_PATH_OF_PERSISTER_CON
FIG_FILE}
2. To run indexer service locally (optional) -
• Run Kafka.
• Run ElasticSearch.
• Open egov-indexer folder which should be present under core-services
repository.
• Update the following properties in application.properties ﬁle -
egov.indexer.yml.repo.path=file://{ABSOLUTE_PATH_OF_INDEXER_CONFIG_F
ILE}

For example, the path to conﬁg ﬁles would be something like -
egov.indexer.yml.repo.path=file:///Users/nithin/Documents/eGov/egov-
repos/core-services/egov-indexer/src/main/resources/collection-
indexer.yml
To run and test our sample application, follow the below steps -
1. Ensure that Kafka, Persister, Indexer and PDF services are running locally and run
the code of voter-registration-service from DIGIT_DEVELOPER_GUIDE branch (for
consistency).
2. Port-forward the following services -
• egov-user to port 8284 (for e.g. - kubectl port-forward egov-user-
58d6dbf966-8r9gz 8284:8080 )
• egov-localization to port 8286 (for e.g. kubectl port-forward egov-
localization-d7d5ccd49-xz9s9 8286:8080 )
• egov-ﬁlestore to 8288 (for e.g. kubectl port-forward egov-filestore-
86c688bbd6-zzk72 8288:8080 )
• egov-mdms to 8082 (for e.g. kubectl port-forward egov-mdms-service-
c9d4877d7-kd4zp 8082:8080 )
3. Run birth-registration-service that we just created.
4.
Import the postman collection of the APIs that this sample service exposes from
here -Birth Registration Postman Collection
5. Setup environment variables in Postman:
• hostWithPort - Eg. yourserver.digit.org:8080 or yourserver.digit.org if the
service is running on port 80.
• applicationNumber - used in the search/update requests to search for that
speciﬁc application number. Set it post the create birth registration
application call.
In case no workﬂow has been conﬁgured, run the scripts to conﬁgure and search for
workﬂow. Double-check ID gen by running the ID gen script.
a. Hit the _create API request to create a voter registration application.
b. Hit the _search API request to search for the created voter registration applications.

c. Hit _update API request to update your application or transition it further in the
workﬂow by changing the actions.

## Section 7: Build & Deploy Instructions

Overview
Follow the instructions on this page to build and deploy applications on DIGIT.
eGov recommends CD/CI be set up before developing on top of DIGIT. This ensures that
new modules can be developed and deployed in a streamlined way. DIGIT ships with CI
as code as part of the DevOps repository. Run the CI installer to setup DIGIT CD/CI before
developing on DIGIT.
Steps
Step 1: Add entry in build-conﬁg.yaml ﬁle in the master branch of the forked MDMS
repository. This will set up the job pipeline in Jenkins. Make sure to also add the same
conﬁg to the feature branch you are working on. Refer to this example here .
Step 2: Follow the instructions for the persister, indexer and PDF service conﬁguration.
Step 3: Go to the Jenkins build page, select "Job Builder" and click on "Build now". This
will pull conﬁg from build_conﬁg.yaml and identify all modules that need to be built.
Step 4: Once the build is done, go to your Jenkins build page. The service will appear
under the repository path in which it has been added, i.e. if the service is added under
core-services, it will show up in the core-services section on the aforementioned page.
Step 5: Most likely, you will be working on a feature branch for this module and not on
"master". Click on "Build with parameters" for the module and search for the branch
name in the ﬁlter box. Select the feature branch you are working on and then click
"Build". This will make sure that Jenkins builds the module pulling code from the branch
you prefer.

Step 6: Click on "Console Output". If the build pipeline and docker registries have been
set up properly as part of the CD/CI setup, the docker image will be built and pushed
to the registry. The console output will have the docker image ID for the module. Scroll
down to the bottom and copy the following information -
Image name of the build
Step 7: After copying the docker image ID, go to your Jenkins server home page, click
on "Deployments" and scroll to ﬁnd your deployment environment. Deployment
environments have the template of deploy-to-<env name> and get created as part of
the CD/CI setup. If multiple environments have been conﬁgured, you will see multiple
deploy-to-* entries.
Step 8: It is best practice to always test out any new module in the dev environment.
Select the environment you would like to deploy to and click on the "Run" icon on the
right-hand side of the page against the environment. In the Images text box, paste the
copied docker image ID and click "Build". Refer to the screenshot below.

Deployment page
Jenkins will now take care of deploying the new image to the DIGIT environment.
Step 9: Test your new service by testing out the APIs via Postman.

FAQs
A list of FAQs for developers by developers
Q1. Which versions of tools are recommended for use with DIGIT?
A. Git - 2.38.1 (latest version)
Java - JDK 8
Intellij - 2022.2.3(latest version)
Kafka - 3.2.0(latest version)
Postman - v9.4(latest version)
Kubectl - 1.25.3(latest version)
Postgres - v10
Q2. Which IDE is recommended for DIGIT development?
A. Intellij, Eclipse, VS Code or any other preferred IDE can be used. Make
sure that it supports Java development and install the Lombok plugin for
the IDE.
Q3. What are the tools which make development effortless?
A.
Jsonformatter.org is a lightweight tool which is quite handy when it
comes to working with sending postman requests and putting objects to
Kafka topics. Another such site is editor.swagger.io which makes
reading/designing APIs a lot easier and understandable. Use Postman to
test APIs. Use k9s to work with your Kubernetes clusters.

Q4. Is it necessary to get a new auth token every few hours?
A. Auth tokens come with an expire period after which you will have to
refresh it by hitting the oauth2 APIs.
If you don’t want to refresh auth now and again you can port-forward
services and get the same result. There are a couple of things to keep in
mind though.
1. As there are many services to port forward, you must not mix up port
numbers while port forwarding.
2. Port forwarding by-passes the zuul api gateway, hence in this case,
when accessing a service directly, for a request to be valid, a user has
to send the userInfo JSON inside the RequestInfo object.
Sample
"userInfo": {
"id": 24226,
"uuid": "11b0e02b-0145-4de2-bc42-c97b96264807",
"userName": "sample_user",
"roles": [
{
"name": "Citizen",
"code": "CITIZEN"
}
]
}
Q5. What are our options when we get a bad request response in postman?
Answer
You can disconnect the forwarded port and start port forwarding again
with recheck on port numbers. If this doesn’t solve the error, in some cases
like adding mdms conﬁguration, you can restart the pod to get the desired
output.

Q6. I deployed the service in a DIGIT environment. But I am getting a 400 or
500 from Zuul.
Check whether the service is up. Insert appropriate namespace in the
commands below.
kubectl describe service -n <namespace>
Check whether the pod is up.
kubectl describe pod -n egov
Check the ingress and make sure the context path is right.
kubectl describe ingress <service name> -n egov
Q7. My service, pod, ingress are all looking good. But I am still getting a
ZuulRuntimeException. What do I do?
This could be a forwarding issue from Zuul to services. Restart Zuul and
restart your service.
Q8. I made a change to the role action mapping in MDMS. But I am unable
to access the APIs. Getting a 403 unauthorized error.
Restart MDMS so it picks up the role action mapping. Also restart the
access control service. Access control caches the role action mappings for
15 minutes. Hence a hard restart will force the cache to reset.
Q9. My pod is in CrashLoopBackOff. What do I do?
\

