# Pulse

Pulse is an experimental banking backend built to study financial systems, backend architecture, transaction consistency, and distributed systems through practical implementation.

Rather than starting with a complex architecture, Pulse evolves incrementally as new technical and business problems emerge.

## 🎯 Purpose

The main goal of Pulse is to explore backend engineering beyond traditional CRUD applications.

The project focuses on questions such as:

* How should financial accounts and customers be modeled?
* How can monetary operations remain consistent under concurrent requests?
* How can duplicate transactions be prevented?
* How should pending and completed transactions affect account balances?
* How can failures between databases and messaging systems be handled safely?
* How can a financial system become observable and resilient?

The architecture will evolve as these problems are introduced and studied.

## 🏗️ Current Architecture

The first version intentionally starts as a simple modular monolith.

```text
Client
  │
  ▼
Spring Boot
  │
  ▼
PostgreSQL
```

Complex infrastructure will only be introduced when the application has a concrete reason for it.

## 🧠 Domain Model

Pulse currently separates three important concepts:

### User

Represents the person authenticated in the platform.

A user may be authorized to operate on behalf of one or more customers.

### Customer

Represents the financial/legal entity that owns accounts.

A customer may represent:

* an individual (`PF`);
* a legal entity/company (`PJ`).

### Account

Represents a financial account owned by a customer.

An account keeps two balance concepts:

* `available_balance` — money currently available to use;
* `ledger_balance` — consolidated/accounting balance.

### User ↔ Customer

Users and customers have a many-to-many relationship.

This allows scenarios such as:

```text
User: Sadock
   │
   ├── Personal Customer (PF)
   │
   ├── Company A (PJ)
   │
   └── Company B (PJ)
```

It also allows multiple users to operate the same company account with different permissions.

Available roles currently include:

```text
OWNER
ADMIN
FINANCIAL_MANAGER
VIEWER
```

## 💸 Transactions

Pulse models financial transactions such as:

* transfers;
* deposits;
* withdrawals.

Transactions have their own lifecycle:

```text
PENDING
PROCESSING
COMPLETED
FAILED
CANCELLED
```

The database already enforces basic financial invariants, including:

* transaction amount must be greater than zero;
* transfers require different source and destination accounts;
* deposits require a destination account;
* withdrawals require a source account;
* account balances cannot be negative.

## 🛠️ Tech Stack

### Backend

* Java 25
* Spring Boot
* Spring Data JPA
* Hibernate

### Database

* PostgreSQL
* Flyway

### Build

* Maven

## 📁 Project Structure

```text
src/main/java/br/com/sadocktech/pulse_bank
│
├── account
│   ├── model
│   └── repository
│
├── customer
│   ├── model
│   └── repository
│
├── transaction
│   ├── model
│   └── repository
│
└── user
    ├── model
    └── repository
```

## 🗄️ Database Migrations

The database schema is managed using Flyway.

```text
src/main/resources/db/migration/
```

The first migration defines:

* users;
* customers;
* individual customers;
* business customers;
* user/customer permissions;
* accounts;
* transactions;
* PostgreSQL enums;
* foreign keys;
* financial integrity constraints.

## 🧭 Evolution

Pulse is intentionally being developed incrementally.

Future engineering problems to explore include:

```text
Domain modeling
      ↓
Transaction consistency
      ↓
Concurrency
      ↓
Idempotency
      ↓
Automated testing
      ↓
Load testing
      ↓
Event-driven processing
      ↓
Transactional Outbox
      ↓
Caching
      ↓
Distributed services
      ↓
Observability
      ↓
Container orchestration
      ↓
Cloud infrastructure
```

Technologies such as Kafka, Redis, OpenTelemetry, Prometheus, Kubernetes, AWS and Terraform may be introduced when the system reaches problems that justify their use.

## 📚 Learning Philosophy

Pulse follows one main principle:

> Architecture should evolve because of real problems, not because technologies look impressive on a diagram.

Each new architectural decision should therefore answer a concrete limitation discovered in the previous version of the system.

## 🚧 Status

Early development.

Current focus:

* initial domain modeling;
* PostgreSQL schema;
* JPA entities;
* persistence layer.

---

Built as a long-term backend engineering study project.
