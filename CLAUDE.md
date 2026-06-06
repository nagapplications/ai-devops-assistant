# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Spring Boot 4.0.6 / Java 17 web service being built as an **AI assistant for DevOps workflows**, with AWS Bedrock as the LLM backend. The current code is scaffolding only — a `BedrockRuntimeClient` bean, an empty `BedrockService` stub where the assistant logic will live, and a `/whoami` STS endpoint used to verify AWS auth. `TestController` is throwaway smoke-test scaffolding, not a pattern to extend.

Base package is `com.naglabs.ai_devops_assistant` (underscored — the hyphenated form `com.naglabs.ai-devops-assistant` is not a valid Java package, see `HELP.md`).

## Commands

Use the Maven wrapper, not a system `mvn`:

- Run the app: `./mvnw spring-boot:run`
- Run all tests: `./mvnw test`
- Run a single test: `./mvnw test -Dtest=AiDevopsAssistantApplicationTests` (append `#methodName` for one method)
- Build a jar: `./mvnw clean package` (output under `target/`)
- Build an OCI image: `./mvnw spring-boot:build-image`

The default port is Spring Boot's 8080. Smoke check after starting: `curl http://localhost:8080/whoami` — returns the STS caller ARN.

## AWS configuration

- Region is **hardcoded to `eu-west-2`** in both `AwsConfig` (Bedrock) and `TestController` (STS). Change both if the deployment region moves.
- Credentials use the AWS SDK default provider chain — there is no profile or key wiring in code. The runtime environment must supply credentials (env vars, `~/.aws/credentials`, instance profile, etc.) before `/whoami` or any Bedrock call will succeed.
- Dependencies pin `software.amazon.awssdk:bedrockruntime` and `:sts` to `2.32.18` independently of the Spring Boot BOM.

## Lombok

Lombok is declared `optional` and wired explicitly as an annotation-processor path on both `default-compile` and `default-testCompile` executions in `pom.xml`. If you add modules or change the compiler plugin config, keep the `annotationProcessorPaths` entries — without them Lombok annotations compile to no-ops and Spring wiring breaks at runtime.

The `spring-boot-maven-plugin` is configured to exclude Lombok from the repackaged jar.
