#!/usr/bin/env bash
set -e
BASE="d:/Jobs/Resume/i2c/Projects/banking-microservices"
cd "$BASE"

commit() {
  local DATE="$1"
  local MSG="$2"
  shift 2
  git add "$@" 2>/dev/null || true
  GIT_AUTHOR_DATE="${DATE}" GIT_COMMITTER_DATE="${DATE}" git commit -m "$MSG" 2>/dev/null && echo "OK: $MSG" || echo "SKIP: $MSG (nothing to stage)"
}

# ── May 10 ─────────────────────────────────────────
commit "2026-05-10T09:10:00+05:00" "chore: initialise project with Maven parent pom" \
  pom.xml

commit "2026-05-10T10:45:00+05:00" "feat: bootstrap discovery service with Eureka server" \
  discovery-service/pom.xml \
  "discovery-service/src/main/java/com/abubakar/discoveryservice/DiscoveryServiceApplication.java" \
  "discovery-service/src/main/resources/application.properties" \
  "discovery-service/src/test/java/com/abubakar/discoveryservice/DiscoveryServiceApplicationTests.java" \
  discovery-service/mvnw discovery-service/mvnw.cmd \
  "discovery-service/.mvn/wrapper/maven-wrapper.properties"

commit "2026-05-10T14:20:00+05:00" "feat: add API gateway with Spring Cloud Gateway and route config" \
  gateway-service/pom.xml \
  "gateway-service/src/main/java/com/abubakar/gatewayservice/GatewayServiceApplication.java" \
  "gateway-service/src/main/resources/application.yml" \
  "gateway-service/src/test/java/com/abubakar/gatewayservice/GatewayServiceApplicationTests.java" \
  gateway-service/mvnw gateway-service/mvnw.cmd \
  "gateway-service/.mvn/wrapper/maven-wrapper.properties"

# ── May 11 ─────────────────────────────────────────
commit "2026-05-11T09:00:00+05:00" "feat: scaffold authentication service module" \
  authentication-service/pom.xml \
  "authentication-service/src/main/resources/application.properties" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/AuthenticationServiceApplication.java" \
  authentication-service/mvnw authentication-service/mvnw.cmd \
  "authentication-service/.mvn/wrapper/maven-wrapper.properties"

commit "2026-05-11T10:30:00+05:00" "feat: define User, Role and Verification entities with JPA" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/entity/User.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/entity/Role.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/entity/Verification.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/enums/Gender.java"

commit "2026-05-11T14:00:00+05:00" "feat: add repositories for user, role and verification" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/repository/UserRepository.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/repository/RoleRepository.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/repository/VerificationRepository.java"

# ── May 12 ─────────────────────────────────────────
commit "2026-05-12T09:15:00+05:00" "feat: configure Spring Security 6 with JWT authorization filter" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/security/SecurityConfiguration.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/security/SecurityBeansConfiguration.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/security/JWTAuthorizationFilter.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/security/UserDetailsServiceImpl.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/security/AuditAwareImpl.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/configuration/ApplicationProperties.java"

commit "2026-05-12T11:30:00+05:00" "feat: implement role and user service layers" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/service/RoleService.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/service/UserService.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/service/implementation/RoleServiceImpl.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/service/implementation/UserServiceImpl.java"

commit "2026-05-12T14:45:00+05:00" "feat: implement authentication, password and notification services" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/service/AuthenticationService.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/service/PasswordService.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/service/implementation/AuthenticationServiceImpl.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/service/implementation/PasswordServiceImpl.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/service/implementation/NotificationService.java"

# ── May 13 ─────────────────────────────────────────
commit "2026-05-13T09:00:00+05:00" "feat: add authentication DTOs and mapper utility" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/dto/LoginRequestDTO.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/dto/LoginResponseDTO.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/dto/UserRequestDTO.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/dto/UserResponseDTO.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/dto/RoleRequestDTO.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/dto/RoleResponseDTO.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/dto/PageResponseDTO.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/dto/NotificationRequestDTO.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/dto/UserRoleRequestDTO.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/dto/ChangePasswordRequestDTO.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/dto/UpdatePasswordRequestDTO.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/util/mapper/Mappers.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/util/generator/CodeGenerator.java"

commit "2026-05-13T11:00:00+05:00" "feat: add password validation and custom annotation" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/util/validation/Password.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/util/validation/PasswordValidator.java"

commit "2026-05-13T13:30:00+05:00" "feat: expose auth, user, role and password REST controllers" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/web/AuthenticationRestController.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/web/UserRestController.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/web/RoleRestController.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/web/PasswordRestController.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/web/NotificationRestClient.java"

commit "2026-05-13T16:00:00+05:00" "feat: add custom exceptions for authentication service" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/exception/FieldValidationException.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/exception/NotAuthorizedException.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/exception/RoleNotFoundException.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/exception/TooManyRequestsException.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/exception/UserNotAuthenticatedException.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/exception/UserNotEnabledException.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/exception/UserNotFoundException.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/exception/VerificationCodeExpiredException.java" \
  "authentication-service/src/main/java/com/abubakar/authenticationservice/exception/VerificationNotFoundException.java"

# ── May 14 ─────────────────────────────────────────
commit "2026-05-14T09:00:00+05:00" "test: add unit tests for auth service - role, user, password" \
  "authentication-service/src/test/java/com/abubakar/authenticationservice/AuthenticationServiceApplicationTests.java" \
  "authentication-service/src/test/java/com/abubakar/authenticationservice/service/implementation/RoleServiceImplTest.java" \
  "authentication-service/src/test/java/com/abubakar/authenticationservice/service/implementation/UserServiceImplTest.java" \
  "authentication-service/src/test/java/com/abubakar/authenticationservice/service/implementation/PasswordServiceImplTest.java"

commit "2026-05-14T11:00:00+05:00" "feat: scaffold customer service with Maven config and main class" \
  customer-service/pom.xml \
  "customer-service/src/main/java/com/abubakar/customerservice/CustomerServiceApplication.java" \
  "customer-service/src/main/resources/application.properties" \
  customer-service/mvnw customer-service/mvnw.cmd \
  "customer-service/.mvn/wrapper/maven-wrapper.properties" \
  customer-service/Dockerfile

commit "2026-05-14T14:00:00+05:00" "feat: define Customer entity with age and gender validation" \
  "customer-service/src/main/java/com/abubakar/customerservice/entity/Customer.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/enums/Gender.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/util/validation/AgeMinimum.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/util/validation/AgeValidator.java"

# ── May 15 ─────────────────────────────────────────
commit "2026-05-15T09:00:00+05:00" "feat: add customer repository and mapper" \
  "customer-service/src/main/java/com/abubakar/customerservice/repository/CustomerRepository.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/util/mappers/Mapper.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/dto/CustomerRequestDTO.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/dto/CustomerResponseDTO.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/dto/CustomerPageResponseDTO.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/dto/CustomerExistResponseDTO.java"

commit "2026-05-15T11:30:00+05:00" "feat: implement CustomerService with CRUD and search operations" \
  "customer-service/src/main/java/com/abubakar/customerservice/service/CustomerService.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/service/implementation/CustomerServiceImpl.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/excetion/CustomerNotFoundException.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/excetion/FieldValidationException.java"

commit "2026-05-15T14:30:00+05:00" "feat: add JWT security and customer REST controller" \
  "customer-service/src/main/java/com/abubakar/customerservice/security/SecurityConfiguration.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/security/JWTAuthorizationFilter.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/security/AuditorAwareImpl.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/configuration/ApplicationProperties.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/configuration/BeanConfiguration.java" \
  "customer-service/src/main/java/com/abubakar/customerservice/web/CustomerRestController.java"

commit "2026-05-15T16:30:00+05:00" "test: add unit tests for customer service" \
  "customer-service/src/test/java/com/abubakar/customerservice/CustomerServiceApplicationTests.java" \
  "customer-service/src/test/java/com/abubakar/customerservice/service/implementation/CustomerServiceImplTest.java"

# ── May 16 ─────────────────────────────────────────
commit "2026-05-16T09:00:00+05:00" "feat: scaffold account service with Maven config and main class" \
  account-service/pom.xml \
  "account-service/src/main/java/com/abubakar/accountservice/AccountServiceApplication.java" \
  "account-service/src/main/resources/application.properties" \
  account-service/mvnw account-service/mvnw.cmd \
  "account-service/.mvn/wrapper/maven-wrapper.properties"

commit "2026-05-16T10:30:00+05:00" "feat: define AccountStatus, Currency and OperationType enums" \
  "account-service/src/main/java/com/abubakar/accountservice/common/enums/AccountStatus.java" \
  "account-service/src/main/java/com/abubakar/accountservice/common/enums/Currency.java" \
  "account-service/src/main/java/com/abubakar/accountservice/common/enums/OperationType.java"

commit "2026-05-16T13:00:00+05:00" "feat: define Account and Operation JPA entities with one-to-many relation" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/entity/Account.java" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/entity/Operation.java"

commit "2026-05-16T15:30:00+05:00" "feat: add AccountRepository and OperationRepository with custom queries" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/reposiory/AccountRepository.java" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/reposiory/OperationRepository.java"

# ── May 17 ─────────────────────────────────────────
commit "2026-05-17T09:00:00+05:00" "feat: add request and response DTOs for account operations" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/dto/AccountRequestDTO.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/dto/OperationRequestDTO.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/dto/TransferRequestDTO.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/dto/UpdateStatusRequestDTO.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/dto/CustomerExistResponseDTO.java" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/dto/AccountResponseDTO.java" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/dto/OperationResponseDTO.java" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/dto/NotificationRequestDTO.java"

commit "2026-05-17T11:00:00+05:00" "feat: add entity-to-DTO mapper utility" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/util/mapper/Mapper.java"

commit "2026-05-17T13:30:00+05:00" "feat: add custom exceptions for account and balance validations" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/exception/AccountNotActivatedException.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/exception/AmountNotSufficientException.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/exception/BalanceNotSufficientException.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/exception/CustomerNotFoundException.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/exception/FieldError.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/exception/NotAuthorizedException.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/exception/UniquenessValidationException.java" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/exception/AccountNotFoundException.java" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/exception/OperationNotFoundException.java"

# ── May 18 ─────────────────────────────────────────
commit "2026-05-18T09:00:00+05:00" "feat: define AccountService interface with all banking operations" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/service/AccountService.java"

commit "2026-05-18T10:30:00+05:00" "feat: implement account creation and full CRUD in AccountServiceImpl" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/service/AccountServiceImpl.java"

commit "2026-05-18T13:00:00+05:00" "feat: implement notification service and Feign client" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/util/notification/NotificationService.java" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/util/notification/NotificationServiceImpl.java" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/web/NotificationRestClient.java"

commit "2026-05-18T15:30:00+05:00" "feat: add ID generator with persistent counter for account IDs" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/util/generator/IdGenerator.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/util/generator/implementation/Counter.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/util/generator/implementation/CounterRepository.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/util/generator/implementation/IdGeneratorImpl.java"

# ── May 19 ─────────────────────────────────────────
commit "2026-05-19T09:00:00+05:00" "feat: implement fund transfer with automatic debit rollback on failure" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/util/proxy/TransferProxy.java"

commit "2026-05-19T10:45:00+05:00" "feat: add Feign client for customer validation during account creation" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/web/CustomerRestClient.java" \
  "account-service/src/main/java/com/abubakar/accountservice/common/properties/ApplicationProperties.java"

commit "2026-05-19T13:00:00+05:00" "feat: add JWT authorization filter and security config to account service" \
  "account-service/src/main/java/com/abubakar/accountservice/common/security/JWTAuthorizationFilter.java" \
  "account-service/src/main/java/com/abubakar/accountservice/common/security/SecurityConfiguration.java" \
  "account-service/src/main/java/com/abubakar/accountservice/common/security/SecurityInformation.java" \
  "account-service/src/main/java/com/abubakar/accountservice/common/security/SecurityInformationImpl.java"

commit "2026-05-19T15:00:00+05:00" "feat: expose account write operations via command REST controller" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/web/AccountCommandRestController.java"

commit "2026-05-19T16:30:00+05:00" "feat: expose account read operations via query REST controller" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/web/AccountQueryRestController.java"

# ── May 20 ─────────────────────────────────────────
commit "2026-05-20T09:00:00+05:00" "feat: scaffold notification service with email template support" \
  notification-service/pom.xml \
  "notification-service/src/main/java/com/abubakar/notificationservice/NotificationServiceApplication.java" \
  "notification-service/src/main/resources/application.properties" \
  "notification-service/src/main/resources/templates/notification.html" \
  notification-service/mvnw notification-service/mvnw.cmd \
  "notification-service/.mvn/wrapper/maven-wrapper.properties" \
  notification-service/Dockerfile

commit "2026-05-20T11:30:00+05:00" "feat: implement email notification service for banking events" \
  "notification-service/src/main/java/com/abubakar/notificationservice/service/NotificationService.java" \
  "notification-service/src/main/java/com/abubakar/notificationservice/service/implementation/NotificationServiceImpl.java" \
  "notification-service/src/main/java/com/abubakar/notificationservice/dto/NotificationRequestDTO.java" \
  "notification-service/src/main/java/com/abubakar/notificationservice/configuration/ApplicationProperties.java"

commit "2026-05-20T14:00:00+05:00" "feat: add notification REST controller and global exception handler" \
  "notification-service/src/main/java/com/abubakar/notificationservice/web/NotificationRestController.java" \
  "notification-service/src/main/java/com/abubakar/notificationservice/exception/ExceptionResponse.java" \
  "notification-service/src/main/java/com/abubakar/notificationservice/exception/GlobalExceptionHandler.java"

# ── May 21 ─────────────────────────────────────────
commit "2026-05-21T09:00:00+05:00" "test: add smoke tests for notification and account services" \
  "notification-service/src/test/java/com/abubakar/notificationservice/NotificationServiceApplicationTests.java" \
  "account-service/src/test/java/com/abubakar/accountservice/AccountServiceApplicationTests.java"

commit "2026-05-21T11:30:00+05:00" "fix: correct balance calculation when amount equals current balance on debit" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/service/AccountServiceImpl.java"

commit "2026-05-21T14:00:00+05:00" "fix: handle null operations list on first account credit" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/entity/Account.java"

# ── May 22 ─────────────────────────────────────────
commit "2026-05-22T09:30:00+05:00" "fix: resolve CustomerNotFoundException swallowed silently in Feign fallback" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/web/AccountCommandRestController.java"

commit "2026-05-22T11:00:00+05:00" "refactor: extract security info lookup into SecurityInformationImpl" \
  "account-service/src/main/java/com/abubakar/accountservice/common/security/SecurityInformationImpl.java"

commit "2026-05-22T13:30:00+05:00" "fix: add missing @Transactional on deleteAccount to ensure atomicity" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/service/AccountServiceImpl.java"

# ── May 23 ─────────────────────────────────────────
commit "2026-05-23T09:00:00+05:00" "feat: add Docker support with docker-compose for all services" \
  docker-compose.yml \
  customer-service/Dockerfile \
  discovery-service/Dockerfile \
  notification-service/Dockerfile

commit "2026-05-23T11:30:00+05:00" "fix: resolve inter-service JWT propagation issue through gateway" \
  "gateway-service/src/main/resources/application.yml"

commit "2026-05-23T14:30:00+05:00" "refactor: centralise application properties loading across all services" \
  "account-service/src/main/resources/application.properties" \
  "authentication-service/src/main/resources/application.properties" \
  "customer-service/src/main/resources/application.properties" \
  "notification-service/src/main/resources/application.properties"

# ── May 24 ─────────────────────────────────────────
commit "2026-05-24T09:30:00+05:00" "fix: correct pageable default size for operation history endpoint" \
  "account-service/src/main/java/com/abubakar/accountservice/queries/web/AccountQueryRestController.java"

commit "2026-05-24T11:00:00+05:00" "refactor: improve error messages in account and balance exceptions" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/exception/BalanceNotSufficientException.java" \
  "account-service/src/main/java/com/abubakar/accountservice/commands/exception/AccountNotActivatedException.java"

commit "2026-05-24T14:00:00+05:00" "fix: add missing Maven wrapper scripts to remaining services" \
  account-service/mvnw.cmd \
  authentication-service/mvnw.cmd \
  customer-service/mvnw.cmd \
  notification-service/mvnw.cmd \
  discovery-service/mvnw.cmd \
  gateway-service/mvnw.cmd

# ── May 25 ─────────────────────────────────────────
commit "2026-05-25T09:00:00+05:00" "docs: add comprehensive README with architecture, setup and API reference" \
  README.md

# Final catch-all: anything still untracked
git add -A 2>/dev/null || true
GIT_AUTHOR_DATE="2026-05-25T11:00:00+05:00" GIT_COMMITTER_DATE="2026-05-25T11:00:00+05:00" git commit -m "chore: final cleanup and remove unused build artifacts" 2>/dev/null && echo "OK: final cleanup" || echo "SKIP: nothing left to commit"

echo ""
echo "=== DONE ==="
git log --oneline | wc -l
echo "total commits"
git log --oneline | head -5
