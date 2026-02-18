# Backend Interview Preparation - Comprehensive Table of Contents

## A. Backend Interview Foundations

### A.1 HTTP & REST Fundamentals
**Purpose:** Master protocol-level understanding for API design questions  
**Deliverables:** HTTP methods, status codes, headers, REST constraints explanation  
**Interview Outcome:** Answer "Design a REST API" and explain HTTP deeply

#### A.1.1 HTTP Protocol Deep Dive
**Purpose:** Understand request/response cycle, connection management  
**Deliverables:** Explain persistent connections, HTTP/1.1 vs HTTP/2, pipelining  
**Interview Outcome:** Discuss performance implications in system design

#### A.1.2 REST Architectural Constraints
**Purpose:** Know Richardson Maturity Model and REST principles  
**Deliverables:** Explain statelessness, cacheability, uniform interface, HATEOAS  
**Interview Outcome:** Justify API design decisions in interviews

#### A.1.3 HTTP Status Codes Mastery
**Purpose:** Use correct status codes for every scenario  
**Deliverables:** 2xx, 3xx, 4xx, 5xx meanings with real examples from your projects  
**Interview Outcome:** Answer "What status code for X scenario?"

#### A.1.4 HTTP Headers & Cookies
**Purpose:** Understand authentication, caching, CORS headers  
**Deliverables:** Authorization, Content-Type, Cache-Control, Set-Cookie usage  
**Interview Outcome:** Explain JWT flow and session management

### A.2 API Design Best Practices
**Purpose:** Design production-grade APIs matching resume claims  
**Deliverables:** Versioning, pagination, filtering, sorting, error responses  
**Interview Outcome:** Design complete API for given business requirement

#### A.2.1 URL Structure & Naming Conventions
**Purpose:** Build consistent, intuitive API endpoints  
**Deliverables:** Resource naming, plural vs singular, nested resources  
**Interview Outcome:** Defend endpoint design choices

#### A.2.2 Request/Response Design Patterns
**Purpose:** Structure payloads for maintainability  
**Deliverables:** DTO patterns, envelope responses, partial updates (PATCH vs PUT)  
**Interview Outcome:** Design request/response for complex scenarios

#### A.2.3 API Versioning Strategies
**Purpose:** Handle breaking changes in production  
**Deliverables:** URI versioning, header versioning, content negotiation  
**Interview Outcome:** Explain how Invoticks/Jariya handle API evolution

#### A.2.4 Pagination & Filtering
**Purpose:** Match resume claim of scalable APIs  
**Deliverables:** Offset vs cursor pagination, query parameters, sorting  
**Interview Outcome:** Design paginated endpoint for 100K+ records

### A.3 Backend Architecture Patterns
**Purpose:** Understand layered architecture in your projects  
**Deliverables:** Controller-Service-Repository pattern, separation of concerns  
**Interview Outcome:** Explain Invoticks architecture with confidence

#### A.3.1 Layered Architecture
**Purpose:** Structure Spring Boot applications properly  
**Deliverables:** Presentation, business, data layers with responsibilities  
**Interview Outcome:** Draw architecture diagram for your projects

#### A.3.2 Dependency Injection & IoC
**Purpose:** Core Spring concept used everywhere  
**Deliverables:** Constructor vs field injection, bean scopes, component scanning  
**Interview Outcome:** Explain DI benefits and implementation

#### A.3.3 DTO vs Entity Separation
**Purpose:** Prevent data exposure and maintain clean boundaries  
**Deliverables:** Mapping strategies, ModelMapper vs manual mapping  
**Interview Outcome:** Justify why Invoticks uses separate DTOs

## B. Java Core + OOP + Best Practices

### B.1 Java Fundamentals Deep Dive
**Purpose:** Eliminate knowledge gaps in core Java  
**Deliverables:** Data types, operators, control flow, exception handling  
**Interview Outcome:** Ace Java coding questions and tricky scenarios

#### B.1.1 String Handling & Immutability
**Purpose:** Understand String pool, performance implications  
**Deliverables:** String vs StringBuilder vs StringBuffer, intern() method  
**Interview Outcome:** Optimize string operations in code reviews

#### B.1.2 Collections Framework Mastery
**Purpose:** Choose right data structure for every problem  
**Deliverables:** List, Set, Map implementations, time complexity, when to use each  
**Interview Outcome:** Justify collection choices in Invoticks/Jariya

#### B.1.3 Generics & Type Safety
**Purpose:** Write type-safe, reusable code  
**Deliverables:** Generic classes, methods, wildcards, type erasure  
**Interview Outcome:** Explain generic DTOs and response wrappers

#### B.1.4 Exception Handling Best Practices
**Purpose:** Match resume claim of global exception handling  
**Deliverables:** Checked vs unchecked, custom exceptions, try-with-resources  
**Interview Outcome:** Design exception hierarchy for Invoticks

### B.2 Object-Oriented Programming
**Purpose:** Core interview topic with design questions  
**Deliverables:** 4 pillars explanation with code examples  
**Interview Outcome:** Design classes for business requirements

#### B.2.1 Encapsulation & Data Hiding
**Purpose:** Protect data integrity in domain models  
**Deliverables:** Access modifiers, getters/setters, immutable objects  
**Interview Outcome:** Explain entity design in your projects

#### B.2.2 Inheritance & Composition
**Purpose:** Choose appropriate code reuse strategy  
**Deliverables:** Inheritance hierarchies, composition over inheritance principle  
**Interview Outcome:** Defend design decisions in ShopSphere/TaskFlow

#### B.2.3 Polymorphism & Abstraction
**Purpose:** Write flexible, extensible code  
**Deliverables:** Interfaces, abstract classes, method overriding, dynamic dispatch  
**Interview Outcome:** Design payment processing system with multiple gateways

#### B.2.4 SOLID Principles
**Purpose:** Interview favorite topic for senior roles  
**Deliverables:** SRP, OCP, LSP, ISP, DIP with Spring Boot examples  
**Interview Outcome:** Refactor code to follow SOLID in live coding

### B.3 Java 8+ Features
**Purpose:** Modern Java used in production  
**Deliverables:** Streams, lambdas, Optional, functional interfaces  
**Interview Outcome:** Write clean, functional-style code in interviews

#### B.3.1 Lambda Expressions & Functional Interfaces
**Purpose:** Simplify code with functional programming  
**Deliverables:** Predicate, Function, Consumer, Supplier usage  
**Interview Outcome:** Refactor loops to streams in code review

#### B.3.2 Stream API Mastery
**Purpose:** Process collections efficiently  
**Deliverables:** map, filter, reduce, collect, parallel streams  
**Interview Outcome:** Optimize data processing in Jariya content pipeline

#### B.3.3 Optional & Null Safety
**Purpose:** Eliminate NullPointerExceptions  
**Deliverables:** Optional creation, chaining, orElse patterns  
**Interview Outcome:** Handle null safely in API responses

#### B.3.4 Date/Time API
**Purpose:** Handle dates correctly in financial systems  
**Deliverables:** LocalDate, LocalDateTime, ZonedDateTime, formatting  
**Interview Outcome:** Explain timestamp handling in Invoticks

### B.4 Concurrency & Multithreading
**Purpose:** Match resume claim of async execution  
**Deliverables:** Thread creation, synchronization, concurrent collections  
**Interview Outcome:** Design thread-safe components

#### B.4.1 Thread Fundamentals
**Purpose:** Understand Java threading model  
**Deliverables:** Thread vs Runnable, thread lifecycle, daemon threads  
**Interview Outcome:** Explain background job execution

#### B.4.2 Synchronization & Locks
**Purpose:** Prevent race conditions  
**Deliverables:** synchronized keyword, ReentrantLock, volatile  
**Interview Outcome:** Fix concurrency bugs in live coding

#### B.4.3 Executor Framework
**Purpose:** Manage thread pools efficiently  
**Deliverables:** ExecutorService, thread pool types, CompletableFuture  
**Interview Outcome:** Justify async processing in Jariya

#### B.4.4 Concurrent Collections
**Purpose:** Thread-safe data structures  
**Deliverables:** ConcurrentHashMap, CopyOnWriteArrayList, BlockingQueue  
**Interview Outcome:** Choose right collection for concurrent scenarios

### B.5 Memory Management & Performance
**Purpose:** Optimize applications as claimed in resume  
**Deliverables:** Heap vs stack, garbage collection, memory leaks  
**Interview Outcome:** Explain 94% latency reduction in Jariya

#### B.5.1 JVM Memory Model
**Purpose:** Understand where objects live  
**Deliverables:** Heap structure, stack frames, method area  
**Interview Outcome:** Debug memory issues in production

#### B.5.2 Garbage Collection
**Purpose:** Tune GC for performance  
**Deliverables:** GC algorithms, generations, tuning flags  
**Interview Outcome:** Explain GC strategy for high-traffic APIs

#### B.5.3 Common Performance Pitfalls
**Purpose:** Avoid mistakes that slow down apps  
**Deliverables:** String concatenation, unnecessary object creation, boxing  
**Interview Outcome:** Identify and fix performance issues in code review

## C. Spring Boot Core

### C.1 Spring Framework Fundamentals
**Purpose:** Understand foundation of Spring Boot  
**Deliverables:** IoC container, bean lifecycle, configuration  
**Interview Outcome:** Explain how Spring powers Invoticks/Jariya

#### C.1.1 Inversion of Control & Dependency Injection
**Purpose:** Core Spring concept used everywhere  
**Deliverables:** Constructor injection, @Autowired, @Qualifier  
**Interview Outcome:** Explain DI implementation in your projects

#### C.1.2 Bean Lifecycle & Scopes
**Purpose:** Manage object creation and destruction  
**Deliverables:** Singleton, prototype, request scopes, @PostConstruct  
**Interview Outcome:** Choose appropriate scope for components

#### C.1.3 Configuration Approaches
**Purpose:** Configure Spring applications properly  
**Deliverables:** Java config, annotations, application.properties/yml  
**Interview Outcome:** Explain Invoticks configuration strategy

### C.2 Spring Boot Auto-Configuration
**Purpose:** Understand convention over configuration  
**Deliverables:** Starter dependencies, auto-configuration classes, @Conditional  
**Interview Outcome:** Customize auto-configuration in projects

#### C.2.1 Spring Boot Starters
**Purpose:** Quick setup with minimal configuration  
**Deliverables:** web, data-jpa, security starters usage  
**Interview Outcome:** Justify dependency choices in pom.xml

#### C.2.2 Externalized Configuration
**Purpose:** Environment-specific settings  
**Deliverables:** Profiles, property sources, @ConfigurationProperties  
**Interview Outcome:** Explain dev/staging/prod configuration

### C.3 Building REST APIs
**Purpose:** Core of backend development  
**Deliverables:** Controllers, request mapping, validation, serialization  
**Interview Outcome:** Build complete CRUD API in live coding

#### C.3.1 Controller Design
**Purpose:** Handle HTTP requests properly  
**Deliverables:** @RestController, @RequestMapping, path variables, request params  
**Interview Outcome:** Design controller for invoice management

#### C.3.2 Request Handling & Data Binding
**Purpose:** Parse and validate incoming data  
**Deliverables:** @RequestBody, @RequestParam, @PathVariable, @ModelAttribute  
**Interview Outcome:** Handle complex request scenarios

#### C.3.3 Response Handling
**Purpose:** Return data with correct format and status  
**Deliverables:** ResponseEntity, @ResponseStatus, custom response wrappers  
**Interview Outcome:** Design consistent API response structure

#### C.3.4 Content Negotiation
**Purpose:** Support multiple formats  
**Deliverables:** JSON, XML, custom converters  
**Interview Outcome:** Explain serialization in Invoticks APIs

### C.4 Data Access with Spring Data JPA
**Purpose:** Match resume claim of database design  
**Deliverables:** Entities, repositories, queries, relationships  
**Interview Outcome:** Design data layer for business requirements

#### C.4.1 Entity Mapping
**Purpose:** Map Java objects to database tables  
**Deliverables:** @Entity, @Table, @Column, primary keys, generated values  
**Interview Outcome:** Design 40-table schema as in Invoticks

#### C.4.2 Relationship Mapping
**Purpose:** Model complex business relationships  
**Deliverables:** @OneToMany, @ManyToOne, @ManyToMany, fetch types, cascading  
**Interview Outcome:** Design relationships for invoice-client-inventory

#### C.4.3 Spring Data Repositories
**Purpose:** Eliminate boilerplate data access code  
**Deliverables:** JpaRepository, query methods, custom queries  
**Interview Outcome:** Build repository layer for Invoticks

#### C.4.4 JPQL & Native Queries
**Purpose:** Write complex database queries  
**Deliverables:** @Query, named queries, pagination, projections  
**Interview Outcome:** Optimize queries as claimed in Jariya

#### C.4.5 Specifications & Criteria API
**Purpose:** Build dynamic queries  
**Deliverables:** JpaSpecificationExecutor, predicates, filtering  
**Interview Outcome:** Implement advanced search in admin panel

## D. Spring Boot Advanced

### D.1 Transaction Management
**Purpose:** Ensure data consistency in financial systems  
**Deliverables:** @Transactional, isolation levels, propagation  
**Interview Outcome:** Handle complex transactional scenarios

#### D.1.1 Declarative Transactions
**Purpose:** Manage transactions with annotations  
**Deliverables:** @Transactional placement, rollback rules, read-only  
**Interview Outcome:** Explain transaction boundaries in Invoticks

#### D.1.2 Transaction Propagation
**Purpose:** Control transaction behavior across methods  
**Deliverables:** REQUIRED, REQUIRES_NEW, NESTED, NOT_SUPPORTED  
**Interview Outcome:** Design multi-service transaction flow

#### D.1.3 Isolation Levels
**Purpose:** Prevent concurrent transaction issues  
**Deliverables:** READ_COMMITTED, REPEATABLE_READ, phantom reads  
**Interview Outcome:** Choose isolation for financial operations

#### D.1.4 Common Transaction Pitfalls
**Purpose:** Avoid transaction-related bugs  
**Deliverables:** Proxy limitations, checked exceptions, lazy loading  
**Interview Outcome:** Debug transaction issues in code review

### D.2 Asynchronous Processing
**Purpose:** Match resume claim of async execution  
**Deliverables:** @Async, thread pools, CompletableFuture  
**Interview Outcome:** Design async workflows for Jariya

#### D.2.1 @Async Configuration
**Purpose:** Enable non-blocking operations  
**Deliverables:** @EnableAsync, custom executors, return types  
**Interview Outcome:** Explain async email sending in TaskFlow

#### D.2.2 CompletableFuture Integration
**Purpose:** Compose async operations  
**Deliverables:** thenApply, thenCompose, combining futures  
**Interview Outcome:** Chain async calls in API layer

#### D.2.3 Error Handling in Async Methods
**Purpose:** Catch exceptions in background tasks  
**Deliverables:** exceptionally(), handle(), async error propagation  
**Interview Outcome:** Handle failed async operations gracefully

### D.3 Validation
**Purpose:** Ensure data integrity at API boundary  
**Deliverables:** Bean Validation, custom validators, group validation  
**Interview Outcome:** Implement comprehensive validation strategy

#### D.3.1 Bean Validation Annotations
**Purpose:** Declarative input validation  
**Deliverables:** @NotNull, @Size, @Email, @Pattern, custom messages  
**Interview Outcome:** Validate invoice creation request

#### D.3.2 Custom Validators
**Purpose:** Business rule validation  
**Deliverables:** ConstraintValidator interface, custom annotations  
**Interview Outcome:** Validate complex business constraints

#### D.3.3 Validation Groups
**Purpose:** Different validation for create vs update  
**Deliverables:** Group interfaces, @Validated usage  
**Interview Outcome:** Implement context-specific validation

### D.4 Global Exception Handling
**Purpose:** Match resume claim of global exception handling  
**Deliverables:** @ControllerAdvice, exception hierarchy, error responses  
**Interview Outcome:** Design exception handling for Invoticks

#### D.4.1 @ControllerAdvice Implementation
**Purpose:** Centralized exception handling  
**Deliverables:** @ExceptionHandler, error response DTOs, logging  
**Interview Outcome:** Handle all exception types consistently

#### D.4.2 Custom Exception Hierarchy
**Purpose:** Business-specific errors  
**Deliverables:** ResourceNotFoundException, ValidationException, BusinessException  
**Interview Outcome:** Design exception types for invoice system

#### D.4.3 Error Response Standards
**Purpose:** Consistent error format for clients  
**Deliverables:** Error codes, timestamps, field errors, trace IDs  
**Interview Outcome:** Define error response contract

### D.5 Caching
**Purpose:** Match resume claim of caching implementation  
**Deliverables:** Spring Cache abstraction, Redis, cache strategies  
**Interview Outcome:** Explain caching in Jariya optimization

#### D.5.1 Spring Cache Abstraction
**Purpose:** Add caching with annotations  
**Deliverables:** @Cacheable, @CachePut, @CacheEvict, cache managers  
**Interview Outcome:** Cache frequently accessed data

#### D.5.2 Cache Strategies
**Purpose:** Choose right caching approach  
**Deliverables:** Cache-aside, write-through, TTL, eviction policies  
**Interview Outcome:** Design caching for Quran content

#### D.5.3 Distributed Caching with Redis
**Purpose:** Share cache across instances  
**Deliverables:** Redis configuration, serialization, cache sync  
**Interview Outcome:** Explain Redis usage in Jariya

### D.6 Pagination & Sorting
**Purpose:** Handle large datasets efficiently  
**Deliverables:** Pageable, Sort, Page responses  
**Interview Outcome:** Implement pagination for 2K+ content pieces

#### D.6.1 Pageable & Sort
**Purpose:** Accept pagination parameters  
**Deliverables:** Pageable interface, Sort.by(), default values  
**Interview Outcome:** Design paginated endpoint

#### D.6.2 Page Response Structure
**Purpose:** Return paginated data with metadata  
**Deliverables:** Page<T>, totalElements, totalPages, number  
**Interview Outcome:** Build consistent paginated responses

### D.7 File Upload & Storage
**Purpose:** Match resume claim of file upload pipeline  
**Deliverables:** MultipartFile, storage strategies, security  
**Interview Outcome:** Design document storage for Invoticks

#### D.7.1 File Upload Handling
**Purpose:** Accept file uploads securely  
**Deliverables:** MultipartFile, size limits, validation  
**Interview Outcome:** Implement invoice attachment upload

#### D.7.2 Storage Strategies
**Purpose:** Store files reliably  
**Deliverables:** Filesystem, S3, database BLOBs, CDN  
**Interview Outcome:** Choose storage for financial documents

#### D.7.3 File Security
**Purpose:** Prevent malicious uploads  
**Deliverables:** Content type validation, virus scanning, access control  
**Interview Outcome:** Secure file upload pipeline

## E. Database & SQL Mastery

### E.1 SQL Fundamentals
**Purpose:** Write efficient queries for interviews  
**Deliverables:** SELECT, INSERT, UPDATE, DELETE, filtering, sorting  
**Interview Outcome:** Solve SQL coding questions

#### E.1.1 Basic Queries
**Purpose:** Retrieve and manipulate data  
**Deliverables:** WHERE, ORDER BY, LIMIT, DISTINCT  
**Interview Outcome:** Write queries for business requirements

#### E.1.2 Aggregate Functions
**Purpose:** Summarize data  
**Deliverables:** COUNT, SUM, AVG, MIN, MAX, GROUP BY, HAVING  
**Interview Outcome:** Generate reports as in Invoticks

#### E.1.3 Subqueries
**Purpose:** Complex filtering and calculations  
**Deliverables:** Scalar, row, table subqueries, IN, EXISTS  
**Interview Outcome:** Write nested queries efficiently

### E.2 Joins & Relationships
**Purpose:** Query related data across tables  
**Deliverables:** INNER, LEFT, RIGHT, FULL joins, self-joins  
**Interview Outcome:** Design queries for 40-table schema

#### E.2.1 Join Types
**Purpose:** Understand when to use each join  
**Deliverables:** INNER JOIN, LEFT JOIN, RIGHT JOIN, FULL OUTER JOIN  
**Interview Outcome:** Retrieve invoice with client and items

#### E.2.2 Complex Join Scenarios
**Purpose:** Multi-table queries  
**Deliverables:** Multiple joins, join conditions, join order  
**Interview Outcome:** Query data across invoice-client-inventory-reports

#### E.2.3 Self-Joins & Hierarchies
**Purpose:** Handle recursive relationships  
**Deliverables:** Employee-manager, category-subcategory  
**Interview Outcome:** Model user role hierarchy

### E.3 Database Design & Normalization
**Purpose:** Match resume claim of normalized schema  
**Deliverables:** 1NF, 2NF, 3NF, BCNF, denormalization  
**Interview Outcome:** Design 40-table schema from scratch

#### E.3.1 Normal Forms
**Purpose:** Eliminate redundancy and anomalies  
**Deliverables:** 1NF to BCNF with examples  
**Interview Outcome:** Normalize invoice system schema

#### E.3.2 ER Modeling
**Purpose:** Design database visually  
**Deliverables:** Entities, relationships, cardinality, ER diagrams  
**Interview Outcome:** Draw ER diagram for Invoticks

#### E.3.3 When to Denormalize
**Purpose:** Trade-off for performance  
**Deliverables:** Read-heavy scenarios, aggregation tables, materialized views  
**Interview Outcome:** Justify denormalization decisions

### E.4 Indexing & Query Optimization
**Purpose:** Match resume claim of query optimization  
**Deliverables:** Index types, EXPLAIN, optimization techniques  
**Interview Outcome:** Reduce query time from 5s to 300ms

#### E.4.1 Index Fundamentals
**Purpose:** Speed up queries with indexes  
**Deliverables:** B-tree, hash, composite indexes, index selection  
**Interview Outcome:** Design indexes for invoice queries

#### E.4.2 Query Execution Plans
**Purpose:** Understand how queries execute  
**Deliverables:** EXPLAIN, EXPLAIN ANALYZE, reading plans  
**Interview Outcome:** Optimize slow queries in Jariya

#### E.4.3 Query Optimization Techniques
**Purpose:** Write faster queries  
**Deliverables:** Avoid SELECT *, index hints, query rewriting  
**Interview Outcome:** Reduce database calls from 200+ to 30

#### E.4.4 Common Performance Anti-Patterns
**Purpose:** Avoid query mistakes  
**Deliverables:** N+1 queries, missing indexes, SELECT * abuse  
**Interview Outcome:** Identify and fix performance issues

### E.5 Transactions & ACID Properties
**Purpose:** Ensure data consistency  
**Deliverables:** Atomicity, Consistency, Isolation, Durability  
**Interview Outcome:** Explain transaction handling in financial system

#### E.5.1 ACID Properties
**Purpose:** Understand transactional guarantees  
**Deliverables:** ACID explanation with real examples  
**Interview Outcome:** Justify transaction usage in Invoticks

#### E.5.2 Isolation Levels & Phenomena
**Purpose:** Prevent concurrent access issues  
**Deliverables:** Dirty reads, non-repeatable reads, phantom reads  
**Interview Outcome:** Choose isolation level for scenarios

#### E.5.3 Deadlocks & Resolution
**Purpose:** Handle concurrent transaction conflicts  
**Deliverables:** Deadlock causes, detection, prevention strategies  
**Interview Outcome:** Debug deadlock in invoice processing

### E.6 Advanced SQL Topics
**Purpose:** Handle complex querying scenarios  
**Deliverables:** Window functions, CTEs, recursive queries  
**Interview Outcome:** Solve advanced SQL interview questions

#### E.6.1 Window Functions
**Purpose:** Analytics and ranking  
**Deliverables:** ROW_NUMBER, RANK, DENSE_RANK, LAG, LEAD  
**Interview Outcome:** Generate leaderboard as in Jariya

#### E.6.2 Common Table Expressions (CTEs)
**Purpose:** Readable complex queries  
**Deliverables:** WITH clause, recursive CTEs  
**Interview Outcome:** Simplify multi-step queries

#### E.6.3 Stored Procedures & Functions
**Purpose:** Database-side logic  
**Deliverables:** Procedure creation, parameters, calling from Spring  
**Interview Outcome:** When to use stored procedures

### E.7 Database-Specific Features
**Purpose:** Leverage MySQL/PostgreSQL capabilities  
**Deliverables:** JSON columns, full-text search, partitioning  
**Interview Outcome:** Explain database choices in projects

#### E.7.1 PostgreSQL Advanced Features
**Purpose:** Use Postgres in Jariya effectively  
**Deliverables:** JSONB, array types, full-text search  
**Interview Outcome:** Justify Postgres for content platform

#### E.7.2 MySQL Optimization
**Purpose:** Tune MySQL in Invoticks  
**Deliverables:** Storage engines, query cache, replication  
**Interview Outcome:** Explain MySQL configuration

## F. Spring Security

### F.1 Security Fundamentals
**Purpose:** Understand security concepts  
**Deliverables:** Authentication vs authorization, security principles  
**Interview Outcome:** Design secure APIs

#### F.1.1 Authentication vs Authorization
**Purpose:** Core security distinction  
**Deliverables:** Who you are vs what you can do  
**Interview Outcome:** Explain security in Invoticks

#### F.1.2 Common Security Threats
**Purpose:** Protect against attacks  
**Deliverables:** XSS, CSRF, SQL injection, CORS  
**Interview Outcome:** Secure APIs against vulnerabilities

### F.2 JWT Authentication
**Purpose:** Match resume claim of JWT implementation  
**Deliverables:** JWT structure, generation, validation  
**Interview Outcome:** Implement authentication from scratch

#### F.2.1 JWT Structure & Claims
**Purpose:** Understand token format  
**Deliverables:** Header, payload, signature, standard claims  
**Interview Outcome:** Explain JWT in Invoticks

#### F.2.2 Access & Refresh Tokens
**Purpose:** Match resume claim of token-based auth  
**Deliverables:** Access token short-lived, refresh token long-lived  
**Interview Outcome:** Design token refresh flow

#### F.2.3 JWT Generation & Validation
**Purpose:** Implement JWT handling  
**Deliverables:** Token creation, signature verification, expiry handling  
**Interview Outcome:** Code JWT service in live coding

#### F.2.4 Token Storage & Security
**Purpose:** Store tokens safely  
**Deliverables:** HttpOnly cookies vs localStorage, XSS prevention  
**Interview Outcome:** Justify token storage strategy

### F.3 Spring Security Configuration
**Purpose:** Configure security in Spring Boot  
**Deliverables:** SecurityFilterChain, authentication managers  
**Interview Outcome:** Build security config for projects

#### F.3.1 Security Filter Chain
**Purpose:** Define security rules  
**Deliverables:** HttpSecurity, authorizeRequests, formLogin  
**Interview Outcome:** Configure authentication and authorization

#### F.3.2 Custom Authentication Filter
**Purpose:** Intercept requests for JWT validation  
**Deliverables:** OncePerRequestFilter, JWT extraction, SecurityContext  
**Interview Outcome:** Implement JWT filter from scratch

#### F.3.3 Authentication Providers
**Purpose:** Validate credentials  
**Deliverables:** UserDetailsService, PasswordEncoder, authentication logic  
**Interview Outcome:** Implement custom authentication

### F.4 Role-Based Access Control (RBAC)
**Purpose:** Match resume claim of RBAC implementation  
**Deliverables:** Roles, authorities, method security  
**Interview Outcome:** Design 8-role system as in Invoticks

#### F.4.1 Roles vs Authorities
**Purpose:** Model permissions correctly  
**Deliverables:** Role hierarchy, granted authorities  
**Interview Outcome:** Design permission model

#### F.4.2 Method-Level Security
**Purpose:** Protect service methods  
**Deliverables:** @PreAuthorize, @PostAuthorize, @Secured, SpEL  
**Interview Outcome:** Secure business logic methods

#### F.4.3 URL-Based Authorization
**Purpose:** Protect endpoints  
**Deliverables:** antMatchers, hasRole, hasAuthority, access control  
**Interview Outcome:** Configure 25+ protected endpoints

#### F.4.4 Dynamic Authorization
**Purpose:** Runtime permission checks  
**Deliverables:** Custom security expressions, permission evaluators  
**Interview Outcome:** Implement granular permissions

### F.5 Session Management
**Purpose:** Match resume claim of session handling  
**Deliverables:** Session creation, invalidation, concurrent sessions  
**Interview Outcome:** Design session strategy

#### F.5.1 Stateless Authentication
**Purpose:** Scale horizontally  
**Deliverables:** JWT for stateless auth, no server-side sessions  
**Interview Outcome:** Explain stateless architecture

#### F.5.2 Session Storage Options
**Purpose:** Share sessions across instances  
**Deliverables:** Redis, JDBC session storage  
**Interview Outcome:** Choose session storage strategy

### F.6 Password Security
**Purpose:** Store passwords safely  
**Deliverables:** Hashing, salting, BCrypt  
**Interview Outcome:** Implement secure password handling

#### F.6.1 Password Encoding
**Purpose:** Never store plain text passwords  
**Deliverables:** BCryptPasswordEncoder, strength parameter  
**Interview Outcome:** Explain password storage in Invoticks

#### F.6.2 Password Reset Flow
**Purpose:** Secure password recovery  
**Deliverables:** Reset tokens, email verification, expiry  
**Interview Outcome:** Design password reset feature

### F.7 OAuth 2.0 & Social Login
**Purpose:** Third-party authentication  
**Deliverables:** OAuth flow, Google/GitHub login  
**Interview Outcome:** Implement social authentication

#### F.7.1 OAuth 2.0 Flows
**Purpose:** Understand authorization flows  
**Deliverables:** Authorization code, implicit, client credentials  
**Interview Outcome:** Explain OAuth in system design

#### F.7.2 Spring Security OAuth Integration
**Purpose:** Add social login  
**Deliverables:** OAuth2 client, provider configuration  
**Interview Outcome:** Integrate Google login

### F.8 Security Best Practices
**Purpose:** Production-ready security  
**Deliverables:** HTTPS, CORS, CSRF protection, security headers  
**Interview Outcome:** Audit security in projects

#### F.8.1 CORS Configuration
**Purpose:** Allow cross-origin requests safely  
**Deliverables:** CorsConfiguration, allowed origins/methods  
**Interview Outcome:** Configure CORS for React frontend

#### F.8.2 CSRF Protection
**Purpose:** Prevent cross-site request forgery  
**Deliverables:** CSRF tokens, stateless CSRF  
**Interview Outcome:** Enable/disable CSRF appropriately

#### F.8.3 Security Headers
**Purpose:** Browser security protections  
**Deliverables:** X-Frame-Options, X-XSS-Protection, CSP  
**Interview Outcome:** Configure security headers

## G. Microservices Basics

### G.1 Microservices Architecture
**Purpose:** Understand distributed systems  
**Deliverables:** Monolith vs microservices, benefits, challenges  
**Interview Outcome:** Discuss architecture trade-offs

#### G.1.1 Monolith vs Microservices
**Purpose:** Know when to use each  
**Deliverables:** Pros/cons, when to split monolith  
**Interview Outcome:** Justify Invoticks architecture

#### G.1.2 Service Decomposition
**Purpose:** Split system into services  
**Deliverables:** Business capabilities, bounded contexts  
**Interview Outcome:** Decompose invoice system into services

### G.2 Inter-Service Communication
**Purpose:** Services need to talk  
**Deliverables:** REST, messaging, service discovery  
**Interview Outcome:** Design service communication

#### G.2.1 Synchronous Communication
**Purpose:** REST calls between services  
**Deliverables:** RestTemplate, WebClient, circuit breakers  
**Interview Outcome:** Design service-to-service REST calls

#### G.2.2 Asynchronous Messaging
**Purpose:** Decouple services with events  
**Deliverables:** RabbitMQ, Kafka basics, message patterns  
**Interview Outcome:** Design event-driven architecture

#### G.2.3 Service Discovery
**Purpose:** Services find each other dynamically  
**Deliverables:** Eureka, client-side vs server-side discovery  
**Interview Outcome:** Explain service registry pattern

### G.3 API Gateway Pattern
**Purpose:** Single entry point for clients  
**Deliverables:** Routing, authentication, rate limiting  
**Interview Outcome:** Design gateway for microservices

#### G.3.1 Gateway Responsibilities
**Purpose:** Understand gateway role  
**Deliverables:** Request routing, load balancing, authentication delegation  
**Interview Outcome:** Explain API gateway benefits

#### G.3.2 Spring Cloud Gateway
**Purpose:** Implement gateway in Spring  
**Deliverables:** Routes, filters, predicates  
**Interview Outcome:** Configure gateway for services

### G.4 Distributed Data Management
**Purpose:** Each service owns its data  
**Deliverables:** Database per service, data consistency patterns  
**Interview Outcome:** Handle distributed transactions

#### G.4.1 Database Per Service
**Purpose:** Service autonomy and independence  
**Deliverables:** Separate databases, data duplication  
**Interview Outcome:** Justify separate databases

#### G.4.2 Saga Pattern
**Purpose:** Distributed transactions without 2PC  
**Deliverables:** Choreography vs orchestration, compensating transactions  
**Interview Outcome:** Design saga for order processing

#### G.4.3 CQRS & Event Sourcing
**Purpose:** Separate read/write models  
**Deliverables:** Command vs query, event store  
**Interview Outcome:** Explain CQRS basics for interviews

### G.5 Resilience Patterns
**Purpose:** Handle failures gracefully  
**Deliverables:** Circuit breaker, retry, fallback, timeout  
**Interview Outcome:** Design resilient microservices

#### G.5.1 Circuit Breaker Pattern
**Purpose:** Prevent cascading failures  
**Deliverables:** Resilience4j, states (closed/open/half-open)  
**Interview Outcome:** Implement circuit breaker

#### G.5.2 Retry & Timeout
**Purpose:** Handle transient failures  
**Deliverables:** Exponential backoff, max retries, timeout configuration  
**Interview Outcome:** Configure retry logic

#### G.5.3 Bulkhead Pattern
**Purpose:** Isolate resources  
**Deliverables:** Thread pool isolation, semaphores  
**Interview Outcome:** Prevent resource exhaustion

### G.6 Observability
**Purpose:** Monitor distributed systems  
**Deliverables:** Logging, metrics, tracing  
**Interview Outcome:** Debug microservices issues

#### G.6.1 Distributed Tracing
**Purpose:** Track requests across services  
**Deliverables:** Trace ID, span ID, Sleuth basics  
**Interview Outcome:** Trace request flow

#### G.6.2 Centralized Logging
**Purpose:** Aggregate logs from all services  
**Deliverables:** Log correlation, structured logging  
**Interview Outcome:** Design logging strategy

#### G.6.3 Health Checks & Metrics
**Purpose:** Monitor service health  
**Deliverables:** Actuator endpoints, custom health indicators  
**Interview Outcome:** Implement health monitoring

## H. System Design

### H.1 System Design Fundamentals
**Purpose:** Core concepts for design interviews  
**Deliverables:** Requirements gathering, capacity estimation, constraints  
**Interview Outcome:** Approach system design questions methodically

#### H.1.1 Requirements Analysis
**Purpose:** Clarify what to build  
**Deliverables:** Functional vs non-functional requirements, constraints  
**Interview Outcome:** Ask right questions in design interviews

#### H.1.2 Capacity Estimation
**Purpose:** Calculate system needs  
**Deliverables:** Traffic, storage, bandwidth calculations  
**Interview Outcome:** Estimate resources for 8K MAU system

#### H.1.3 API Design First
**Purpose:** Define interfaces before implementation  
**Deliverables:** Endpoint design, request/response formats  
**Interview Outcome:** Design API contract for system

### H.2 Scalability Concepts
**Purpose:** Match resume claim of scalable systems  
**Deliverables:** Horizontal vs vertical scaling, load balancing  
**Interview Outcome:** Design systems that scale

#### H.2.1 Horizontal vs Vertical Scaling
**Purpose:** Know scaling strategies  
**Deliverables:** When to scale out vs up, stateless design  
**Interview Outcome:** Justify scaling approach

#### H.2.2 Load Balancing
**Purpose:** Distribute traffic across servers  
**Deliverables:** Round-robin, least connections, sticky sessions  
**Interview Outcome:** Design load balancing strategy

#### H.2.3 Caching Strategies
**Purpose:** Reduce database load  
**Deliverables:** Cache layers, CDN, cache invalidation  
**Interview Outcome:** Design caching for Jariya-like system

#### H.2.4 Database Scaling
**Purpose:** Handle large data volumes  
**Deliverables:** Read replicas, sharding, partitioning  
**Interview Outcome:** Scale database for millions of records

### H.3 Database Design in System Design
**Purpose:** Choose right data store  
**Deliverables:** SQL vs NoSQL, data modeling, replication  
**Interview Outcome:** Justify database choice

#### H.3.1 SQL vs NoSQL
**Purpose:** Select appropriate database  
**Deliverables:** Use cases, trade-offs, CAP theorem  
**Interview Outcome:** Choose MySQL vs MongoDB for scenario

#### H.3.2 Database Replication
**Purpose:** High availability and read scaling  
**Deliverables:** Master-slave, master-master replication  
**Interview Outcome:** Design replication strategy

#### H.3.3 Database Sharding
**Purpose:** Distribute data across databases  
**Deliverables:** Sharding keys, consistent hashing  
**Interview Outcome:** Shard database for global system

### H.4 Common System Design Patterns
**Purpose:** Reusable design solutions  
**Deliverables:** Patterns for common problems  
**Interview Outcome:** Apply patterns in design interviews

#### H.4.1 Rate Limiting
**Purpose:** Prevent abuse and ensure fairness  
**Deliverables:** Token bucket, leaky bucket, sliding window  
**Interview Outcome:** Design rate limiter for API

#### H.4.2 Consistent Hashing
**Purpose:** Distribute data evenly  
**Deliverables:** Hash ring, virtual nodes  
**Interview Outcome:** Use for cache distribution

#### H.4.3 Message Queues
**Purpose:** Asynchronous processing  
**Deliverables:** Producer-consumer, queue vs topic  
**Interview Outcome:** Design async workflow with queues

#### H.4.4 Content Delivery Network (CDN)
**Purpose:** Serve static content faster  
**Deliverables:** Edge locations, cache invalidation  
**Interview Outcome:** Design CDN strategy for Jariya

### H.5 Backend-Specific Design Problems
**Purpose:** Practice common backend designs  
**Deliverables:** Solutions to typical interview questions  
**Interview Outcome:** Solve design questions in 45 minutes

#### H.5.1 Design URL Shortener
**Purpose:** Classic design interview question  
**Deliverables:** Hash generation, storage, redirection  
**Interview Outcome:** Complete design with trade-offs

#### H.5.2 Design Rate Limiter
**Purpose:** API throttling system  
**Deliverables:** Algorithms, distributed rate limiting  
**Interview Outcome:** Implement rate limiting for Invoticks

#### H.5.3 Design Notification System
**Purpose:** Email/SMS/push notifications  
**Deliverables:** Queue-based processing, priority handling  
**Interview Outcome:** Design notification for TaskFlow

#### H.5.4 Design File Storage System
**Purpose:** Document upload and retrieval  
**Deliverables:** Chunking, deduplication, metadata storage  
**Interview Outcome:** Design storage matching Invoticks resume claim

#### H.5.5 Design Leaderboard System
**Purpose:** Real-time ranking  
**Deliverables:** Sorted sets, Redis, score updates  
**Interview Outcome:** Design leaderboard as in Jariya

#### H.5.6 Design Search Autocomplete
**Purpose:** Typeahead suggestions  
**Deliverables:** Trie, caching, ranking  
**Interview Outcome:** Implement search for admin CMS

#### H.5.7 Design E-commerce Inventory System
**Purpose:** Similar to ShopSphere project  
**Deliverables:** Stock management, concurrency control  
**Interview Outcome:** Design inventory matching resume

#### H.5.8 Design Multi-tenant SaaS Platform
**Purpose:** Isolation and data separation  
**Deliverables:** Database per tenant, shared database strategies  
**Interview Outcome:** Explain ShopSphere multi-tenancy

### H.6 Performance & Optimization
**Purpose:** Match resume claim of performance optimization  
**Deliverables:** Bottleneck identification, optimization strategies  
**Interview Outcome:** Explain 94% latency reduction

#### H.6.1 Performance Bottlenecks
**Purpose:** Identify slow components  
**Deliverables:** Database, network, CPU profiling  
**Interview Outcome:** Analyze performance issues

#### H.6.2 Database Query Optimization
**Purpose:** Match resume claim of optimization  
**Deliverables:** Indexing, query rewriting, N+1 elimination  
**Interview Outcome:** Reduce 200+ calls to 30

#### H.6.3 Connection Pooling
**Purpose:** Reuse database connections  
**Deliverables:** HikariCP configuration, pool sizing  
**Interview Outcome:** Explain connection pooling in Jariya

#### H.6.4 Async Processing
**Purpose:** Non-blocking operations  
**Deliverables:** @Async, CompletableFuture, message queues  
**Interview Outcome:** Improve concurrency by 3x

### H.7 Reliability & Availability
**Purpose:** Build fault-tolerant systems  
**Deliverables:** Redundancy, failover, disaster recovery  
**Interview Outcome:** Design highly available system

#### H.7.1 High Availability Design
**Purpose:** Minimize downtime  
**Deliverables:** Redundancy, health checks, auto-recovery  
**Interview Outcome:** Match resume claim of zero-downtime deployment

#### H.7.2 Backup & Recovery
**Purpose:** Protect against data loss  
**Deliverables:** Backup strategies, RPO/RTO, restore procedures  
**Interview Outcome:** Explain automated backup in Jariya

#### H.7.3 Monitoring & Alerting
**Purpose:** Detect issues proactively  
**Deliverables:** Metrics, dashboards, alert thresholds  
**Interview Outcome:** Design monitoring for production system

## I. DevOps & Deployment Readiness

### I.1 Docker Fundamentals
**Purpose:** Match resume claim of Docker-based CI/CD  
**Deliverables:** Containerization, images, containers  
**Interview Outcome:** Dockerize Spring Boot application

#### I.1.1 Docker Basics
**Purpose:** Understand containerization  
**Deliverables:** Images vs containers, Docker architecture  
**Interview Outcome:** Explain Docker benefits

#### I.1.2 Dockerfile Creation
**Purpose:** Build application images  
**Deliverables:** Multi-stage builds, layer optimization, base images  
**Interview Outcome:** Write Dockerfile for Invoticks

#### I.1.3 Docker Compose
**Purpose:** Multi-container applications  
**Deliverables:** docker-compose.yml, service dependencies, networking  
**Interview Outcome:** Define services for app + database + redis

#### I.1.4 Docker Best Practices
**Purpose:** Production-ready containers  
**Deliverables:** Image size reduction, security, .dockerignore  
**Interview Outcome:** Optimize Docker images

### I.2 CI/CD with GitHub Actions
**Purpose:** Match resume claim of CI/CD pipelines  
**Deliverables:** Automated testing, building, deployment  
**Interview Outcome:** Design deployment pipeline

#### I.2.1 GitHub Actions Basics
**Purpose:** Automate workflows  
**Deliverables:** Workflow files, triggers, jobs, steps  
**Interview Outcome:** Create CI pipeline for projects

#### I.2.2 Build & Test Automation
**Purpose:** Run tests on every commit  
**Deliverables:** Maven/Gradle builds, unit tests, integration tests  
**Interview Outcome:** Implement automated testing

#### I.2.3 Docker Image Building in CI
**Purpose:** Build and push images  
**Deliverables:** Build image, push to registry, tagging strategy  
**Interview Outcome:** Automate Docker builds

#### I.2.4 Deployment Automation
**Purpose:** Match resume claim of zero-downtime deployment  
**Deliverables:** Deploy to VPS/AWS, rolling updates, health checks  
**Interview Outcome:** Implement automated deployment

### I.3 AWS Basics
**Purpose:** Match resume claim of AWS deployment  
**Deliverables:** EC2, RDS, S3, IAM  
**Interview Outcome:** Deploy Spring Boot on AWS

#### I.3.1 EC2 Fundamentals
**Purpose:** Virtual servers in cloud  
**Deliverables:** Instance types, AMIs, security groups  
**Interview Outcome:** Deploy application on EC2

#### I.3.2 RDS for Databases
**Purpose:** Managed database service  
**Deliverables:** RDS setup, backups, read replicas  
**Interview Outcome:** Use RDS for production database

#### I.3.3 S3 for File Storage
**Purpose:** Object storage for files  
**Deliverables:** Buckets, access policies, SDK integration  
**Interview Outcome:** Store files in S3 instead of filesystem

#### I.3.4 Auto-Scaling & Load Balancing
**Purpose:** Match resume claim of auto-scaling  
**Deliverables:** Auto Scaling Groups, ELB configuration  
**Interview Outcome:** Design scalable AWS architecture

#### I.3.5 CloudWatch Monitoring
**Purpose:** Match resume claim of monitoring  
**Deliverables:** Metrics, logs, alarms  
**Interview Outcome:** Set up monitoring for Jariya

### I.4 Linux & Server Management
**Purpose:** Deploy on VPS servers  
**Deliverables:** Linux commands, server setup, troubleshooting  
**Interview Outcome:** Deploy and maintain applications

#### I.4.1 Linux Command Line
**Purpose:** Navigate and manage servers  
**Deliverables:** File operations, permissions, process management  
**Interview Outcome:** Troubleshoot deployed applications

#### I.4.2 Server Setup & Configuration
**Purpose:** Prepare server for deployment  
**Deliverables:** Java installation, firewall, reverse proxy  
**Interview Outcome:** Set up production server

#### I.4.3 Application Deployment
**Purpose:** Run Spring Boot in production  
**Deliverables:** Systemd service, environment variables, logs  
**Interview Outcome:** Deploy Invoticks on VPS

#### I.4.4 Log Management
**Purpose:** Debug production issues  
**Deliverables:** Log rotation, centralized logging, log analysis  
**Interview Outcome:** Troubleshoot using logs

### I.5 Environment Management
**Purpose:** Separate dev/staging/production  
**Deliverables:** Configuration per environment, secrets management  
**Interview Outcome:** Explain environment strategy

#### I.5.1 Configuration Management
**Purpose:** Environment-specific settings  
**Deliverables:** Spring profiles, externalized config  
**Interview Outcome:** Manage configs for multiple environments

#### I.5.2 Secrets Management
**Purpose:** Secure sensitive data  
**Deliverables:** Environment variables, AWS Secrets Manager  
**Interview Outcome:** Handle database passwords securely

### I.6 Database Migrations
**Purpose:** Match resume claim of Flyway migrations  
**Deliverables:** Version-controlled schema changes  
**Interview Outcome:** Manage database evolution

#### I.6.1 Flyway Fundamentals
**Purpose:** Database version control  
**Deliverables:** Migration scripts, naming conventions, rollbacks  
**Interview Outcome:** Implement migrations for 40-table schema

#### I.6.2 Migration Best Practices
**Purpose:** Safe schema changes in production  
**Deliverables:** Backward compatibility, testing migrations  
**Interview Outcome:** Deploy schema changes without downtime

## J. DSA (LeetCode-Style)

### J.1 Arrays & Strings
**Purpose:** Most common interview questions  
**Deliverables:** Two pointers, sliding window, prefix sum  
**Interview Outcome:** Solve 80% of array/string problems

#### J.1.1 Two Pointers Technique
**Purpose:** Optimize array traversal  
**Deliverables:** Opposite direction, same direction patterns  
**Interview Outcome:** Solve remove duplicates, container with most water

#### J.1.2 Sliding Window
**Purpose:** Subarray problems  
**Deliverables:** Fixed size, variable size windows  
**Interview Outcome:** Find longest substring, max sum subarray

#### J.1.3 String Manipulation
**Purpose:** Common string problems  
**Deliverables:** Palindrome, anagram, substring search  
**Interview Outcome:** Solve string rotation, pattern matching

### J.2 Hash Tables
**Purpose:** O(1) lookups for optimization  
**Deliverables:** HashMap usage, frequency counting  
**Interview Outcome:** Optimize brute force solutions

#### J.2.1 HashMap Patterns
**Purpose:** Count, group, detect duplicates  
**Deliverables:** Frequency map, complement lookup  
**Interview Outcome:** Solve two sum, group anagrams

#### J.2.2 HashSet for Uniqueness
**Purpose:** Fast duplicate detection  
**Deliverables:** Contains checks, set operations  
**Interview Outcome:** Find first non-repeating character

### J.3 Linked Lists
**Purpose:** Pointer manipulation skills  
**Deliverables:** Traversal, reversal, cycle detection  
**Interview Outcome:** Solve linked list problems

#### J.3.1 Basic Operations
**Purpose:** Insert, delete, search  
**Deliverables:** Node manipulation, edge cases  
**Interview Outcome:** Implement linked list operations

#### J.3.2 Two Pointer in Lists
**Purpose:** Fast/slow pointer technique  
**Deliverables:** Find middle, detect cycle, nth from end  
**Interview Outcome:** Solve cycle detection, palindrome list

#### J.3.3 List Reversal
**Purpose:** Reverse entire or partial list  
**Deliverables:** Iterative and recursive reversal  
**Interview Outcome:** Reverse linked list in K groups

### J.4 Stacks & Queues
**Purpose:** LIFO/FIFO data structures  
**Deliverables:** Stack/queue usage, monotonic stack  
**Interview Outcome:** Solve bracket matching, next greater element

#### J.4.1 Stack Applications
**Purpose:** Backtracking, expression evaluation  
**Deliverables:** Valid parentheses, infix to postfix  
**Interview Outcome:** Evaluate expressions, undo operations

#### J.4.2 Queue Applications
**Purpose:** BFS, task scheduling  
**Deliverables:** Circular queue, deque  
**Interview Outcome:** Implement queue using stacks

#### J.4.3 Monotonic Stack/Queue
**Purpose:** Next greater/smaller element  
**Deliverables:** Monotonic increasing/decreasing  
**Interview Outcome:** Stock span, sliding window maximum

### J.5 Trees & Binary Trees
**Purpose:** Hierarchical data structures  
**Deliverables:** Traversals, BST operations, path problems  
**Interview Outcome:** Solve tree interview questions

#### J.5.1 Tree Traversals
**Purpose:** DFS and BFS patterns  
**Deliverables:** Inorder, preorder, postorder, level-order  
**Interview Outcome:** Implement all traversals iteratively

#### J.5.2 Binary Search Trees
**Purpose:** Sorted tree operations  
**Deliverables:** Insert, delete, search, validate BST  
**Interview Outcome:** Implement BST operations

#### J.5.3 Tree Path Problems
**Purpose:** Root-to-leaf paths  
**Deliverables:** Path sum, max path sum, LCA  
**Interview Outcome:** Find all paths with given sum

### J.6 Recursion & Backtracking
**Purpose:** Problem-solving technique  
**Deliverables:** Base case, recursive case, backtracking template  
**Interview Outcome:** Solve combinatorial problems

#### J.6.1 Recursion Fundamentals
**Purpose:** Break down problems  
**Deliverables:** Factorial, fibonacci, power  
**Interview Outcome:** Write recursive solutions

#### J.6.2 Backtracking Template
**Purpose:** Generate all possibilities  
**Deliverables:** Permutations, combinations, subsets  
**Interview Outcome:** Generate all valid solutions

#### J.6.3 Backtracking Applications
**Purpose:** Constraint satisfaction  
**Deliverables:** N-Queens, sudoku solver, word search  
**Interview Outcome:** Solve backtracking problems

### J.7 Sorting & Searching
**Purpose:** Fundamental algorithms  
**Deliverables:** Binary search, merge sort, quick sort  
**Interview Outcome:** Implement and apply sorting/searching

#### J.7.1 Binary Search
**Purpose:** O(log n) search in sorted arrays  
**Deliverables:** Standard binary search, variations  
**Interview Outcome:** Find element, first/last occurrence

#### J.7.2 Sorting Algorithms
**Purpose:** Understand complexity trade-offs  
**Deliverables:** Quick sort, merge sort, heap sort  
**Interview Outcome:** Implement O(n log n) sorting

#### J.7.3 Search Variations
**Purpose:** Binary search on answer  
**Deliverables:** Search in rotated array, peak element  
**Interview Outcome:** Solve search problems

### J.8 Dynamic Programming
**Purpose:** Optimization problems  
**Deliverables:** Memoization, tabulation, state transitions  
**Interview Outcome:** Solve DP interview questions

#### J.8.1 1D DP
**Purpose:** Linear DP problems  
**Deliverables:** Climbing stairs, house robber, coin change  
**Interview Outcome:** Identify and solve 1D DP

#### J.8.2 2D DP
**Purpose:** Grid-based DP  
**Deliverables:** Unique paths, longest common subsequence  
**Interview Outcome:** Build DP table for 2D problems

#### J.8.3 DP Patterns
**Purpose:** Recognize common patterns  
**Deliverables:** Knapsack, LIS, edit distance  
**Interview Outcome:** Apply DP templates

### J.9 Graphs (Optional - if time permits)
**Purpose:** Advanced data structure  
**Deliverables:** DFS, BFS, shortest path  
**Interview Outcome:** Solve basic graph problems

#### J.9.1 Graph Representations
**Purpose:** Store graph data  
**Deliverables:** Adjacency matrix, adjacency list  
**Interview Outcome:** Choose appropriate representation

#### J.9.2 Graph Traversals
**Purpose:** Explore all nodes  
**Deliverables:** DFS, BFS implementation  
**Interview Outcome:** Detect cycles, connected components

## K. Behavioral + HR + Resume Alignment

### K.1 Tell Me About Yourself
**Purpose:** Strong 2-minute pitch aligned with resume  
**Deliverables:** Present-Past-Future structure using resume  
**Interview Outcome:** Deliver confident introduction

#### K.1.1 Structure Your Story
**Purpose:** Coherent narrative from resume  
**Deliverables:** Current role → Background → Why this company  
**Interview Outcome:** 2-minute pitch highlighting key achievements

#### K.1.2 Highlight Key Achievements
**Purpose:** Use resume numbers naturally  
**Deliverables:** 94% improvement, 100+ APIs, 8K users in story  
**Interview Outcome:** Make achievements memorable

#### K.1.3 Connect to Target Role
**Purpose:** Show fit for position  
**Deliverables:** Align Invoticks/Jariya experience to job requirements  
**Interview Outcome:** Explain why you're perfect candidate

### K.2 STAR Method for Behavioral Questions
**Purpose:** Structure answers with examples  
**Deliverables:** Situation-Task-Action-Result framework  
**Interview Outcome:** Answer behavioral questions confidently

#### K.2.1 STAR Framework
**Purpose:** Organize behavioral responses  
**Deliverables:** 4-part structure with concrete examples  
**Interview Outcome:** Give clear, compelling answers

#### K.2.2 Prepare Stories from Resume
**Purpose:** Mine resume for STAR examples  
**Deliverables:** 10-15 stories from Invoticks/Jariya/projects  
**Interview Outcome:** Have story for any question

#### K.2.3 Common Behavioral Questions
**Purpose:** Prepare for frequent questions  
**Deliverables:** Conflict, failure, leadership, teamwork stories  
**Interview Outcome:** Answer with relevant examples

### K.3 Technical Experience Questions
**Purpose:** Deep dive into resume claims  
**Deliverables:** Detailed explanations of projects  
**Interview Outcome:** Defend every resume line

#### K.3.1 Project Deep Dives
**Purpose:** Explain Invoticks/Jariya in detail  
**Deliverables:** Architecture, challenges, decisions, results  
**Interview Outcome:** Discuss projects for 30+ minutes

#### K.3.2 Technology Choices
**Purpose:** Justify tech stack decisions  
**Deliverables:** Why Kotlin vs Java, MySQL vs Postgres, AWS vs VPS  
**Interview Outcome:** Explain technology trade-offs

#### K.3.3 Challenges & Solutions
**Purpose:** Show problem-solving ability  
**Deliverables:** 94% improvement story, 3x capacity increase details  
**Interview Outcome:** Demonstrate impact

### K.4 Salary & Compensation
**Purpose:** Negotiate effectively  
**Deliverables:** Research, range preparation, negotiation tactics  
**Interview Outcome:** Get fair compensation

#### K.4.1 Salary Research
**Purpose:** Know your market value  
**Deliverables:** Research for backend roles in your location  
**Interview Outcome:** State realistic expectations

#### K.4.2 Negotiation Strategy
**Purpose:** Maximize offer  
**Deliverables:** Delay tactics, multiple offers, counteroffer script  
**Interview Outcome:** Negotiate 10-20% higher

### K.5 Questions to Ask Interviewers
**Purpose:** Show interest and assess fit  
**Deliverables:** 10+ thoughtful questions  
**Interview Outcome:** Ask insightful questions

#### K.5.1 Technical Questions
**Purpose:** Understand tech stack and practices  
**Deliverables:** Architecture, deployment, code review questions  
**Interview Outcome:** Assess technical environment

#### K.5.2 Team & Culture Questions
**Purpose:** Evaluate work environment  
**Deliverables:** Team size, collaboration, growth opportunities  
**Interview Outcome:** Determine cultural fit

#### K.5.3 Role-Specific Questions
**Purpose:** Clarify expectations  
**Deliverables:** Day-to-day, projects, success metrics  
**Interview Outcome:** Understand what success looks like

## L. Resume Truth-Matching Plan

### L.1 Invoticks Deep Knowledge
**Purpose:** Back every Invoticks claim with deep knowledge  
**Deliverables:** Detailed understanding of all features  
**Interview Outcome:** Discuss Invoticks confidently for 1 hour

#### L.1.1 100+ REST APIs
**Purpose:** Explain API design and implementation  
**Deliverables:** List key endpoints, design patterns, versioning  
**Interview Outcome:** Describe API architecture in detail

#### L.1.2 JWT Authentication Implementation
**Purpose:** Defend access/refresh token claim  
**Deliverables:** Token generation, validation, refresh flow code  
**Interview Outcome:** Implement JWT from scratch

#### L.1.3 RBAC with 8 Roles & 25+ Endpoints
**Purpose:** Explain permission system  
**Deliverables:** Role hierarchy, permission matrix, enforcement  
**Interview Outcome:** Design and code RBAC system

#### L.1.4 40-Table Database Schema
**Purpose:** Justify normalized design claim  
**Deliverables:** ER diagram, relationships, normalization decisions  
**Interview Outcome:** Defend schema design choices

#### L.1.5 Flyway Migrations
**Purpose:** Explain database versioning  
**Deliverables:** Migration strategy, scripts, rollback plan  
**Interview Outcome:** Demonstrate migration knowledge

#### L.1.6 File Upload Pipeline
**Purpose:** Defend secure storage claim  
**Deliverables:** Upload handling, validation, storage strategy  
**Interview Outcome:** Implement file upload feature

#### L.1.7 Docker CI/CD Pipeline
**Purpose:** Explain deployment automation  
**Deliverables:** GitHub Actions workflow, Docker setup, zero-downtime  
**Interview Outcome:** Write CI/CD pipeline from scratch

### L.2 Jariya Deep Knowledge
**Purpose:** Back every Jariya claim with expertise  
**Deliverables:** Complete understanding of optimizations  
**Interview Outcome:** Explain Jariya technical decisions

#### L.2.1 8K+ MAU System Design
**Purpose:** Justify scalability claims  
**Deliverables:** Architecture for concurrent users, load handling  
**Interview Outcome:** Design system for 8K MAU

#### L.2.2 Referral System
**Purpose:** Explain commission calculation  
**Deliverables:** Multi-tier logic, payout processing  
**Interview Outcome:** Implement referral system

#### L.2.3 Admin CMS with Multi-language
**Purpose:** Defend 2K+ content management claim  
**Deliverables:** Content model, language support, CMS features  
**Interview Outcome:** Design content management system

#### L.2.4 94% Latency Reduction
**Purpose:** Critical optimization claim - must defend  
**Deliverables:** Before/after metrics, optimization techniques used  
**Interview Outcome:** Explain step-by-step how you achieved this

#### L.2.5 200+ to 30 Database Calls Reduction
**Purpose:** Defend query optimization claim  
**Deliverables:** N+1 elimination, query batching, eager loading  
**Interview Outcome:** Show exact optimization techniques

#### L.2.6 3x Concurrent User Capacity
**Purpose:** Explain capacity improvement  
**Deliverables:** Caching, connection pooling, async execution  
**Interview Outcome:** Detail each optimization contributing to 3x

#### L.2.7 AWS Deployment
**Purpose:** Defend AWS infrastructure claim  
**Deliverables:** Services used, auto-scaling, monitoring, backups  
**Interview Outcome:** Describe complete AWS setup

### L.3 Side Projects Knowledge
**Purpose:** Discuss ShopSphere and TaskFlow confidently  
**Deliverables:** Architecture, features, tech choices  
**Interview Outcome:** Explain projects without hesitation

#### L.3.1 ShopSphere Multi-tenancy
**Purpose:** Defend multi-tenant claim  
**Deliverables:** Tenant isolation strategy, data separation  
**Interview Outcome:** Implement multi-tenant architecture

#### L.3.2 TaskFlow Notification Engine
**Purpose:** Explain async notifications  
**Deliverables:** Email integration, queue-based processing  
**Interview Outcome:** Design notification system

### L.4 Technical Skills Verification
**Purpose:** Prove every skill listed in resume  
**Deliverables:** Hands-on competence in each technology  
**Interview Outcome:** Code in any listed technology

#### L.4.1 Kotlin Proficiency
**Purpose:** Defend Kotlin as primary language  
**Deliverables:** Kotlin-specific features, when to use vs Java  
**Interview Outcome:** Write Kotlin code fluently

#### L.4.2 Spring Boot Expertise
**Purpose:** Core framework competence  
**Deliverables:** All Spring Boot concepts covered in sections C & D  
**Interview Outcome:** Build Spring Boot app from scratch

#### L.4.3 Database Skills
**Purpose:** MySQL + PostgreSQL knowledge  
**Deliverables:** Query optimization, schema design, both databases  
**Interview Outcome:** Write complex queries on whiteboard

#### L.4.4 Security Implementation
**Purpose:** JWT, RBAC, session management expertise  
**Deliverables:** Implement security features from scratch  
**Interview Outcome:** Code complete authentication system

#### L.4.5 DevOps Skills
**Purpose:** Docker, GitHub Actions, VPS, AWS knowledge  
**Deliverables:** Deploy application end-to-end  
**Interview Outcome:** Set up complete deployment pipeline

#### L.4.6 Tools Mastery
**Purpose:** Git, Swagger, Flyway practical usage  
**Deliverables:** Use tools effectively in workflow  
**Interview Outcome:** Demonstrate tool proficiency

#### L.4.7 React/Next.js Understanding
**Purpose:** Listed in resume - must have basic knowledge  
**Deliverables:** Frontend basics, REST API consumption  
**Interview Outcome:** Discuss frontend-backend integration

### L.5 Metric Defense Strategy
**Purpose:** Confidently defend all numbers in resume  
**Deliverables:** Evidence and explanation for each metric  
**Interview Outcome:** Answer "how did you measure this?"

#### L.5.1 100+ APIs Breakdown
**Purpose:** Explain what APIs were built  
**Deliverables:** Categorize APIs by domain, list major endpoints  
**Interview Outcome:** Describe API categories and counts

#### L.5.2 8K+ MAU Evidence
**Purpose:** Justify user count claim  
**Deliverables:** How users were tracked, analytics tools used  
**Interview Outcome:** Explain user tracking methodology

#### L.5.3 94% Improvement Calculation
**Purpose:** Show measurement methodology  
**Deliverables:** Before/after metrics, measurement tools, timeline  
**Interview Outcome:** Walk through optimization process

#### L.5.4 200+ to 30 Calls Evidence
**Purpose:** Prove database optimization claim  
**Deliverables:** Query logs, profiling results, optimization steps  
**Interview Outcome:** Show exactly how calls were reduced

#### L.5.5 3x Capacity Measurement
**Purpose:** Justify concurrent user improvement  
**Deliverables:** Load testing results, metrics before/after  
**Interview Outcome:** Demonstrate load testing approach

#### L.5.6 2K+ Content Pieces
**Purpose:** Defend content volume claim  
**Deliverables:** Content types, database counts, CMS features  
**Interview Outcome:** Describe content management scale

### L.6 Project Timeline & Context
**Purpose:** Remember project details and timelines  
**Deliverables:** When features were built, team size, duration  
**Interview Outcome:** Answer context questions naturally

#### L.6.1 Invoticks Timeline
**Purpose:** Know project phases and milestones  
**Deliverables:** Initial release, major features, current state  
**Interview Outcome:** Describe project evolution

#### L.6.2 Jariya Timeline
**Purpose:** Optimization timeline and stages  
**Deliverables:** Initial version, when optimizations happened, impact  
**Interview Outcome:** Explain development progression

#### L.6.3 Team & Collaboration
**Purpose:** Describe working environment  
**Deliverables:** Team size, role, collaboration process  
**Interview Outcome:** Discuss teamwork naturally

### L.7 Gap Identification & Filling
**Purpose:** Find resume claims you can't defend yet  
**Deliverables:** List weak areas, create learning plan  
**Interview Outcome:** No weak spots in resume knowledge

#### L.7.1 Knowledge Audit
**Purpose:** Test yourself on every resume line  
**Deliverables:** Can you explain/code each claim? List gaps  
**Interview Outcome:** Identify what needs deep learning

#### L.7.2 Priority Gap Closing
**Purpose:** Fix critical knowledge gaps first  
**Deliverables:** Focus on most-asked topics in interviews  
**Interview Outcome:** Close 80% of gaps in 2 weeks

## M. Mock Interviews + Revision + Progress Tracking

### M.1 Mock Interview Practice
**Purpose:** Simulate real interview pressure  
**Deliverables:** Practice sessions with feedback  
**Interview Outcome:** Perform well under pressure

#### M.1.1 Technical Mock Interviews
**Purpose:** Practice coding and system design  
**Deliverables:** 10+ mock technical rounds  
**Interview Outcome:** Solve problems in 45 minutes

#### M.1.2 Behavioral Mock Interviews
**Purpose:** Practice storytelling and communication  
**Deliverables:** 5+ behavioral practice sessions  
**Interview Outcome:** Answer behavioral questions smoothly

#### M.1.3 Resume Deep-Dive Mocks
**Purpose:** Defend every resume claim  
**Deliverables:** Grilling sessions on projects  
**Interview Outcome:** Discuss projects for 1 hour without hesitation

#### M.1.4 Mock Interview Sources
**Purpose:** Find practice partners  
**Deliverables:** Pramp, Interviewing.io, peers, mentors  
**Interview Outcome:** Schedule weekly mock interviews

### M.2 Self-Assessment & Feedback
**Purpose:** Track improvement and identify weaknesses  
**Deliverables:** Rubric-based self-evaluation  
**Interview Outcome:** Know your interview readiness level

#### M.2.1 Recording & Review
**Purpose:** Analyze your performance  
**Deliverables:** Record mocks, review for improvements  
**Interview Outcome:** Identify filler words, unclear explanations

#### M.2.2 Feedback Integration
**Purpose:** Improve based on critique  
**Deliverables:** List feedback points, action plan  
**Interview Outcome:** Fix recurring mistakes

### M.3 Topic Revision Strategy
**Purpose:** Retain knowledge long-term  
**Deliverables:** Spaced repetition schedule  
**Interview Outcome:** Remember concepts during interviews

#### M.3.1 Daily Revision
**Purpose:** Keep fundamentals fresh  
**Deliverables:** 30-min daily review of weak topics  
**Interview Outcome:** Quick recall of concepts

#### M.3.2 Weekly Comprehensive Review
**Purpose:** Consolidate learning  
**Deliverables:** Sunday deep review of week's topics  
**Interview Outcome:** Solidify understanding

#### M.3.3 Flashcards & Notes
**Purpose:** Quick reference material  
**Deliverables:** Anki cards for key concepts, cheat sheets  
**Interview Outcome:** Rapid review before interviews

### M.4 Progress Tracking System
**Purpose:** Measure preparation effectiveness  
**Deliverables:** Metrics and milestones  
**Interview Outcome:** Know when you're interview-ready

#### M.4.1 Topic Completion Tracker
**Purpose:** Monitor learning progress  
**Deliverables:** Spreadsheet with TOC sections, status  
**Interview Outcome:** Visual progress tracking

#### M.4.2 Problem-Solving Tracker
**Purpose:** Track LeetCode/coding progress  
**Deliverables:** Problems solved by category, difficulty  
**Interview Outcome:** 150+ problems solved

#### M.4.3 Mock Interview Scores
**Purpose:** Quantify interview readiness  
**Deliverables:** Score each mock, track improvement  
**Interview Outcome:** 80%+ success rate in mocks

#### M.4.4 Weekly Reflection
**Purpose:** Adjust strategy based on progress  
**Deliverables:** Weekly review: what worked, what didn't  
**Interview Outcome:** Optimize learning approach

## N. 30/60/90-Day Timeline + Weekly Milestones

### N.1 Days 1-30: Foundation Building
**Purpose:** Fill fundamental knowledge gaps  
**Deliverables:** Core Java, Spring Boot, SQL, basics  
**Interview Outcome:** Pass technical screening

#### Week 1: Backend Fundamentals + Java Core
**Purpose:** HTTP, REST, OOP, collections  
**Deliverables:** Sections A.1-A.3, B.1-B.2 complete  
**Interview Outcome:** Answer fundamental questions

#### Week 2: Java Advanced + Spring Boot Core
**Purpose:** Concurrency, memory, DI, REST APIs  
**Deliverables:** Sections B.3-B.5, C.1-C.3 complete  
**Interview Outcome:** Build basic Spring Boot API

#### Week 3: Spring Data JPA + Database Fundamentals
**Purpose:** Entity mapping, repositories, SQL  
**Deliverables:** Sections C.4, E.1-E.2 complete  
**Interview Outcome:** Design database and queries

#### Week 4: Spring Boot Advanced + Database Design
**Purpose:** Transactions, validation, normalization, indexing  
**Deliverables:** Sections D.1-D.4, E.3-E.4 complete  
**Interview Outcome:** Handle complex data scenarios

### N.2 Days 31-60: Specialization + Resume Alignment
**Purpose:** Deep dive into resume claims  
**Deliverables:** Security, optimization, DevOps  
**Interview Outcome:** Defend all resume projects

#### Week 5: Spring Security + JWT
**Purpose:** Authentication, authorization, RBAC  
**Deliverables:** Section F complete  
**Interview Outcome:** Implement security from scratch

#### Week 6: Performance Optimization + Caching
**Purpose:** Match 94% improvement claim  
**Deliverables:** Sections D.5-D.7, E.4, H.6 complete  
**Interview Outcome:** Explain Jariya optimizations

#### Week 7: Docker + CI/CD + AWS
**Purpose:** Deployment pipeline knowledge  
**Deliverables:** Section I complete  
**Interview Outcome:** Deploy application end-to-end

#### Week 8: System Design Fundamentals
**Purpose:** Architecture interview preparation  
**Deliverables:** Sections H.1-H.4 complete  
**Interview Outcome:** Design scalable systems

### N.3 Days 61-90: Interview Mastery + Job Search
**Purpose:** Practice, polish, and apply  
**Deliverables:** Mocks, DSA, behavioral, applications  
**Interview Outcome:** Land interviews and offers

#### Week 9: System Design Practice + DSA Start
**Purpose:** Backend design problems + arrays/strings  
**Deliverables:** Sections H.5, J.1-J.2 complete  
**Interview Outcome:** Solve design + coding questions

#### Week 10: DSA Core Topics
**Purpose:** Most common coding questions  
**Deliverables:** Sections J.3-J.6 complete  
**Interview Outcome:** Solve 60% of LeetCode medium

#### Week 11: Advanced DSA + Behavioral Prep
**Purpose:** DP, graphs, STAR stories  
**Deliverables:** Sections J.7-J.9, K complete  
**Interview Outcome:** Handle all interview types

#### Week 12: Mock Interviews + Resume Polish + Applications
**Purpose:** Final preparation and job applications  
**Deliverables:** Sections L, M, P complete, 20+ mocks  
**Interview Outcome:** Interview-ready, actively interviewing

### N.4 Daily Schedule Template
**Purpose:** Consistent daily routine  
**Deliverables:** Time-blocked schedule  
**Interview Outcome:** Maximize learning efficiency

#### Morning Block (2-3 hours)
**Purpose:** Fresh mind for theory  
**Deliverables:** New concepts, reading, notes  
**Interview Outcome:** Learn 1-2 new topics daily

#### Afternoon Block (2-3 hours)
**Purpose:** Hands-on practice  
**Deliverables:** Coding, building features, DSA problems  
**Interview Outcome:** Apply learned concepts

#### Evening Block (1-2 hours)
**Purpose:** Revision and mock practice  
**Deliverables:** Flashcards, previous topics, mock questions  
**Interview Outcome:** Retain knowledge

### N.5 Milestone Checkpoints
**Purpose:** Validate progress at key points  
**Deliverables:** Assessment at 30/60/90 days  
**Interview Outcome:** Know if you're on track

#### Day 30 Checkpoint
**Purpose:** Foundation validation  
**Deliverables:** Can build CRUD API with security and database  
**Interview Outcome:** Pass technical screening

#### Day 60 Checkpoint
**Purpose:** Specialization validation  
**Deliverables:** Can explain all resume projects, deploy apps  
**Interview Outcome:** Handle technical deep-dive rounds

#### Day 90 Checkpoint
**Purpose:** Interview readiness  
**Deliverables:** 80% mock success, portfolio ready, applying  
**Interview Outcome:** Getting interview offers

## O. Portfolio & Project Strengthening

### O.1 GitHub Profile Optimization
**Purpose:** Professional developer presence  
**Deliverables:** Clean repos, READMEs, commits  
**Interview Outcome:** Impress with GitHub profile

#### O.1.1 Profile Setup
**Purpose:** Complete professional profile  
**Deliverables:** Bio, pinned repos, activity graph  
**Interview Outcome:** Strong first impression

#### O.1.2 Repository Organization
**Purpose:** Showcase best projects  
**Deliverables:** Pin Invoticks-like, Jariya-like, ShopSphere, TaskFlow  
**Interview Outcome:** Easy navigation for recruiters

#### O.1.3 README Best Practices
**Purpose:** Document projects professionally  
**Deliverables:** Features, tech stack, setup, screenshots  
**Interview Outcome:** Self-explanatory projects

#### O.1.4 Commit History
**Purpose:** Show development process  
**Deliverables:** Clean commits, meaningful messages  
**Interview Outcome:** Demonstrate good practices

### O.2 Production-Ready Project Features
**Purpose:** Go beyond basic CRUD  
**Deliverables:** Advanced features matching resume  
**Interview Outcome:** Discuss impressive implementations

#### O.2.1 Invoticks-Like Project
**Purpose:** Invoice management showcase  
**Deliverables:** 100+ APIs, JWT, RBAC, 40-table schema, Flyway  
**Interview Outcome:** Reference in interviews

#### O.2.2 Jariya-Like Project
**Purpose:** Content platform with optimizations  
**Deliverables:** Multi-language CMS, caching, query optimization  
**Interview Outcome:** Demonstrate performance skills

#### O.2.3 ShopSphere Enhancement
**Purpose:** Production-ready inventory system  
**Deliverables:** Multi-tenancy, analytics, real-time features  
**Interview Outcome:** Show architectural skills

#### O.2.4 TaskFlow Enhancement
**Purpose:** Workflow automation polish  
**Deliverables:** Notification engine, async processing, API design  
**Interview Outcome:** Highlight async expertise

### O.3 Deployment & Live Demos
**Purpose:** Show working applications  
**Deliverables:** Deployed projects with live URLs  
**Interview Outcome:** Demo during interviews

#### O.3.1 Deploy on Free Tiers
**Purpose:** Host projects at no cost  
**Deliverables:** Railway, Render, Vercel, AWS free tier  
**Interview Outcome:** Share live links with recruiters

#### O.3.2 API Documentation
**Purpose:** Professional API docs  
**Deliverables:** Swagger UI live, Postman collections  
**Interview Outcome:** Showcase API design

#### O.3.3 Demo Videos
**Purpose:** Quick project overviews  
**Deliverables:** 2-3 minute Loom videos for each project  
**Interview Outcome:** Show projects asynchronously

### O.4 Code Quality Standards
**Purpose:** Clean, maintainable code  
**Deliverables:** Best practices, patterns, testing  
**Interview Outcome:** Pass code review scrutiny

#### O.4.1 Code Organization
**Purpose:** Proper package structure  
**Deliverables:** Controller-service-repository layers  
**Interview Outcome:** Show architectural understanding

#### O.4.2 Design Patterns
**Purpose:** Apply patterns appropriately  
**Deliverables:** Factory, Strategy, Builder, Singleton  
**Interview Outcome:** Discuss pattern usage

#### O.4.3 Testing
**Purpose:** Show quality consciousness  
**Deliverables:** Unit tests, integration tests, coverage  
**Interview Outcome:** Demonstrate testing skills

#### O.4.4 Code Documentation
**Purpose:** Readable, maintainable code  
**Deliverables:** JavaDoc, comments, clear naming  
**Interview Outcome:** Professional code standards

### O.5 Technical Blog/Portfolio Site
**Purpose:** Demonstrate expertise publicly  
**Deliverables:** Blog posts on optimizations, tutorials  
**Interview Outcome:** Stand out from other candidates

#### O.5.1 Blog Topics from Resume
**Purpose:** Write about your achievements  
**Deliverables:** "How I Reduced API Latency by 94%", "Building RBAC"  
**Interview Outcome:** Share during interviews

#### O.5.2 Portfolio Website
**Purpose:** Personal brand hub  
**Deliverables:** Projects, skills, blog, contact  
**Interview Outcome:** Professional online presence

## P. Job Application Strategy & Interview Pipeline

### P.1 Job Search Strategy
**Purpose:** Apply to right companies efficiently  
**Deliverables:** Target companies, application tracking  
**Interview Outcome:** Maximize interview opportunities

#### P.1.1 Company Research
**Purpose:** Target companies matching your profile  
**Deliverables:** 50+ companies list, tech stacks, culture  
**Interview Outcome:** Apply to best-fit companies

#### P.1.2 Job Board Strategy
**Purpose:** Find relevant openings  
**Deliverables:** LinkedIn, AngelList, company sites, referrals  
**Interview Outcome:** Consistent application pipeline

#### P.1.3 Application Tracking
**Purpose:** Manage application pipeline  
**Deliverables:** Spreadsheet tracking applications, statuses  
**Interview Outcome:** Organized job search

### P.2 Resume & Cover Letter
**Purpose:** Get past ATS and recruiters  
**Deliverables:** Optimized resume, tailored letters  
**Interview Outcome:** Higher response rate

#### P.2.1 Resume ATS Optimization
**Purpose:** Pass applicant tracking systems  
**Deliverables:** Keywords, formatting, quantified achievements  
**Interview Outcome:** Resume reaches human reviewers

#### P.2.2 Tailoring for Each Role
**Purpose:** Match job requirements  
**Deliverables:** Highlight relevant projects and skills  
**Interview Outcome:** 3x response rate

#### P.2.3 Cover Letter Template
**Purpose:** Personalized introductions  
**Deliverables:** Framework for quick customization  
**Interview Outcome:** Stand out when cover letter required

### P.3 Networking & Referrals
**Purpose:** Bypass application black hole  
**Deliverables:** LinkedIn connections, referrals  
**Interview Outcome:** 10x higher interview rate

#### P.3.1 LinkedIn Optimization
**Purpose:** Professional network building  
**Deliverables:** Complete profile, connections, engagement  
**Interview Outcome:** Recruiter inbound messages

#### P.3.2 Referral Strategy
**Purpose:** Internal recommendations  
**Deliverables:** Reach out to employees, alumni network  
**Interview Outcome:** Fast-track to interviews

#### P.3.3 Community Engagement
**Purpose:** Build developer network  
**Deliverables:** Stack Overflow, Reddit, Discord, local meetups  
**Interview Outcome:** Opportunities through network

### P.4 Interview Pipeline Management
**Purpose:** Handle multiple interview processes  
**Deliverables:** Scheduling, preparation, follow-ups  
**Interview Outcome:** Maximize offers simultaneously

#### P.4.1 Interview Scheduling
**Purpose:** Optimize timing  
**Deliverables:** Cluster interviews, delay offers strategically  
**Interview Outcome:** Multiple offers for negotiation

#### P.4.2 Company-Specific Preparation
**Purpose:** Research each interviewer  
**Deliverables:** Company tech, recent news, interviewer background  
**Interview Outcome:** Tailored, impressive answers

#### P.4.3 Follow-Up Strategy
**Purpose:** Stay top of mind  
**Deliverables:** Thank you emails, check-ins  
**Interview Outcome:** Professional impression

### P.5 Interview Performance
**Purpose:** Execute on preparation  
**Deliverables:** Strong performance across rounds  
**Interview Outcome:** Advance to offer stage

#### P.5.1 Technical Rounds
**Purpose:** Solve problems correctly  
**Deliverables:** Think aloud, test code, optimize  
**Interview Outcome:** Pass coding rounds

#### P.5.2 System Design Rounds
**Purpose:** Design scalable systems  
**Deliverables:** Requirements, diagram, trade-offs, scaling  
**Interview Outcome:** Pass architecture rounds

#### P.5.3 Behavioral Rounds
**Purpose:** Show culture fit  
**Deliverables:** STAR stories, enthusiasm, questions  
**Interview Outcome:** Pass HR/manager rounds

#### P.5.4 Resume Deep Dive
**Purpose:** Defend all claims confidently  
**Deliverables:** Detailed project explanations, metrics justification  
**Interview Outcome:** No red flags on resume

### P.6 Offer Evaluation & Negotiation
**Purpose:** Maximize compensation and fit  
**Deliverables:** Offer comparison, negotiation tactics  
**Interview Outcome:** Best possible offer accepted

#### P.6.1 Offer Components
**Purpose:** Understand total compensation  
**Deliverables:** Base, bonus, equity, benefits evaluation  
**Interview Outcome:** Compare offers fairly

#### P.6.2 Negotiation Tactics
**Purpose:** Increase offer value  
**Deliverables:** Counteroffer script, competing offers leverage  
**Interview Outcome:** 10-20% bump from initial offer

#### P.6.3 Decision Framework
**Purpose:** Choose right opportunity  
**Deliverables:** Growth, tech, culture, compensation scoring  
**Interview Outcome:** Accept best long-term fit

### P.7 Continuous Application While Preparing
**Purpose:** Don't wait until "ready"  
**Deliverables:** Apply throughout 90 days  
**Interview Outcome:** Real interview practice, earlier offers

#### P.7.1 Week 1-4: Apply to Practice Companies
**Purpose:** Low-stakes interview experience  
**Deliverables:** 10 applications to companies you're less interested in  
**Interview Outcome:** Real interview feedback

#### P.7.2 Week 5-8: Apply to Target Companies
**Purpose:** Aim for desired roles  
**Deliverables:** 20 applications to companies you want  
**Interview Outcome:** Serious interview pipeline

#### P.7.3 Week 9-12: Apply Aggressively
**Purpose:** Maximize opportunities  
**Deliverables:** 30+ applications, leverage network  
**Interview Outcome:** Multiple offers to choose from

---

## FINAL NOTES

### How to Use This TOC
1. **Copy any section** and ask another AI: "Expand this section with detailed study plan, resources, practice questions, and code examples"
2. **Track progress** by marking sections complete in a spreadsheet
3. **Follow the 90-day timeline** but adjust pace based on your learning speed
4. **Prioritize L section (Resume Truth-Matching)** - this is critical
5. **Do NOT skip M section (Mock Interviews)** - practice is everything

### Critical Success Factors
- **Honest self-assessment**: Know what you don't know
- **Depth over breadth**: Master fundamentals before advanced topics
- **Practice over theory**: Build, code, solve problems daily
- **Mock interviews**: Simulate pressure early and often
- **Resume defense**: Every claim must be backed by deep knowledge

### Red Flags to Avoid
- ❌ Claiming knowledge you don't have
- ❌ Memorizing answers without understanding
- ❌ Skipping hands-on practice
- ❌ Avoiding weak areas
- ❌ Waiting until "perfect" to apply

### Success Metrics
- ✅ Can build any resume project from scratch
- ✅ 80%+ mock interview success rate
- ✅ Solve 150+ LeetCode problems
- ✅ Explain 94% improvement step-by-step
- ✅ Design system for 8K+ users confidently
- ✅ Deploy application with CI/CD pipeline
- ✅ Discuss any resume topic for 30+ minutes

**You have 90 days. Start with Section A.1 tomorrow.**