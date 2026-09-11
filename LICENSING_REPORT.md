# Oracle JDK to Amazon Corretto Migration — Licensing Report

## Summary

| Attribute | Value |
|---|---|
| Project Name | serverless-test-samples |
| Files Scanned | All `.java`, `pom.xml`, `Dockerfile`, `.sh`, `.yml`, `.yaml`, `.properties` files |
| Oracle API Imports Found | 0 |
| Issues Found | 2 (non-Corretto Docker base images) |
| Issues Fixed | 2 |
| Manual Review Items | 0 |

## Dependency Table

| File | Line | Oracle API | Replacement | Status |
|---|---|---|---|---|
| N/A | — | No Oracle API imports detected | — | Pass |

## Docker Base Image Changes

| File | Before | After | Status |
|---|---|---|---|
| `java-test-samples/fargate-dynamodb-sync/spingboot-with-dynamodb/Dockerfile` | `adoptopenjdk/openjdk11:alpine-jre` | `amazoncorretto:11` | Fixed |
| `java-test-samples/fargate-lambda-stepfunction-async/async-sqs-lambda-stepfunction-dynamodb/Dockerfile` | `openjdk:17-alpine` | `amazoncorretto:17` | Fixed |

## Licensing Cost Estimate

### Oracle Java SE Subscription Costs (per Oracle published pricing)

| Employees | Rate | Monthly | Annual | 5-Year |
|---|---|---|---|---|
| 500 | $15.00/emp/mo | $7,500 | $90,000 | $450,000 |
| 1,000 | $12.00/emp/mo | $12,000 | $144,000 | $720,000 |
| 5,000 | $10.50/emp/mo | $52,500 | $630,000 | $3,150,000 |
| 10,000 | $10.50/emp/mo* | $105,000 | $1,260,000 | $6,300,000 |

\* 10,000-employee rate extrapolated from highest published tier (3,000–9,999). Actual enterprise pricing negotiated with Oracle.

### Amazon Corretto Cost

**$0** — Amazon Corretto is a no-cost, multiplatform, production-ready distribution of OpenJDK with no licensing fees.

## Modified Files List

1. `java-test-samples/fargate-dynamodb-sync/spingboot-with-dynamodb/Dockerfile` — Changed FROM `adoptopenjdk/openjdk11:alpine-jre` to `amazoncorretto:11`
2. `java-test-samples/fargate-lambda-stepfunction-async/async-sqs-lambda-stepfunction-dynamodb/Dockerfile` — Changed FROM `openjdk:17-alpine` to `amazoncorretto:17`

## Manual Review Items

None — no unrecognized Oracle API imports were found.

## Annotation-Processor Upgrades

No annotation-processor upgrades were required. Both projects using Lombok run on Spring Boot 3.x, which manages a compatible Lombok version. The explicitly versioned Lombok dependency (1.18.30 in `async-sqs-lambda-stepfunction-dynamodb/pom.xml`) is already compatible with JDK 11+/17/21.
