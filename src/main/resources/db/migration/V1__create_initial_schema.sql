-- =========================================================
-- ENUMS
-- =========================================================

CREATE TYPE user_status AS ENUM (
    'ACTIVE',
    'PENDING',
    'SUSPENDED',
    'DEACTIVATED',
    'LOCKED'
);

CREATE TYPE customer_type AS ENUM (
    'PF',
    'PJ'
);

CREATE TYPE customer_status AS ENUM (
    'ACTIVE',
    'PENDING',
    'SUSPENDED',
    'DEACTIVATED',
    'LOCKED'
);

CREATE TYPE customer_role AS ENUM (
    'OWNER',
    'ADMIN',
    'FINANCIAL_MANAGER',
    'VIEWER'
);

CREATE TYPE account_type AS ENUM (
    'CHECKING',
    'SAVINGS',
    'SALARY'
);

CREATE TYPE account_status AS ENUM (
    'ACTIVE',
    'PENDING',
    'SUSPENDED',
    'DEACTIVATED',
    'LOCKED'
);

CREATE TYPE transaction_type AS ENUM (
    'TRANSFER',
    'DEPOSIT',
    'WITHDRAWAL'
);

CREATE TYPE transaction_status AS ENUM (
    'PENDING',
    'PROCESSING',
    'COMPLETED',
    'FAILED',
    'CANCELLED'
);


-- =========================================================
-- USER
-- Quem acessa/autentica no Pulse
-- =========================================================

CREATE TABLE tbl_user (
                          id_user BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

                          email VARCHAR(255) NOT NULL UNIQUE,
                          password_hash VARCHAR(255) NOT NULL,

                          status user_status NOT NULL DEFAULT 'PENDING',

                          created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          deleted_at TIMESTAMPTZ
);


-- =========================================================
-- CUSTOMER
-- Titular financeiro representado dentro do Pulse
-- =========================================================

CREATE TABLE tbl_customer (
                              id_customer BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

                              type customer_type NOT NULL,
                              status customer_status NOT NULL DEFAULT 'PENDING',

                              created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              deleted_at TIMESTAMPTZ
);


-- =========================================================
-- CUSTOMER - PESSOA FÍSICA
-- Dados específicos de um titular PF
-- =========================================================

CREATE TABLE tbl_customer_pf (
                                 id_customer_pf BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

                                 customer_id BIGINT NOT NULL UNIQUE,

                                 full_name VARCHAR(255) NOT NULL,
                                 email VARCHAR(255) NOT NULL,
                                 phone VARCHAR(20),

                                 rg VARCHAR(30),
                                 birth_date DATE NOT NULL,
                                 cpf VARCHAR(11) NOT NULL UNIQUE,

                                 CONSTRAINT fk_customer_pf_customer
                                     FOREIGN KEY (customer_id)
                                         REFERENCES tbl_customer(id_customer)
                                         ON DELETE CASCADE
);


-- =========================================================
-- CUSTOMER - PESSOA JURÍDICA
-- Dados específicos de um titular PJ
-- =========================================================

CREATE TABLE tbl_customer_pj (
                                 id_customer_pj BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

                                 customer_id BIGINT NOT NULL UNIQUE,

                                 legal_name VARCHAR(255) NOT NULL,
                                 trade_name VARCHAR(255),

                                 cnpj VARCHAR(14) NOT NULL UNIQUE,

                                 email VARCHAR(255) NOT NULL,
                                 phone VARCHAR(20),

                                 CONSTRAINT fk_customer_pj_customer
                                     FOREIGN KEY (customer_id)
                                         REFERENCES tbl_customer(id_customer)
                                         ON DELETE CASCADE
);


-- =========================================================
-- USER <-> CUSTOMER
-- Determina quais titulares um usuário pode representar
-- =========================================================

CREATE TABLE tbl_user_customer (
                                   user_id BIGINT NOT NULL,
                                   customer_id BIGINT NOT NULL,

                                   role customer_role NOT NULL,

                                   created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                   CONSTRAINT pk_user_customer
                                       PRIMARY KEY (user_id, customer_id),

                                   CONSTRAINT fk_user_customer_user
                                       FOREIGN KEY (user_id)
                                           REFERENCES tbl_user(id_user)
                                           ON DELETE CASCADE,

                                   CONSTRAINT fk_user_customer_customer
                                       FOREIGN KEY (customer_id)
                                           REFERENCES tbl_customer(id_customer)
                                           ON DELETE CASCADE
);


-- =========================================================
-- ACCOUNT
-- Conta financeira pertencente a um Customer
-- =========================================================

CREATE TABLE tbl_account (
                             id_account BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

                             customer_id BIGINT NOT NULL,

                             available_balance NUMERIC(19, 2) NOT NULL DEFAULT 0,
                             ledger_balance NUMERIC(19, 2) NOT NULL DEFAULT 0,

                             bank_name VARCHAR(255) NOT NULL,
                             bank_code VARCHAR(10) NOT NULL,

                             branch_number VARCHAR(10) NOT NULL,
                             branch_check_digit VARCHAR(5),

                             account_number VARCHAR(20) NOT NULL,
                             account_check_digit VARCHAR(5),

                             type account_type NOT NULL,
                             status account_status NOT NULL DEFAULT 'PENDING',

                             CONSTRAINT fk_account_customer
                                 FOREIGN KEY (customer_id)
                                     REFERENCES tbl_customer(id_customer),

                             CONSTRAINT chk_account_available_balance
                                 CHECK (available_balance >= 0),

                             CONSTRAINT chk_account_ledger_balance
                                 CHECK (ledger_balance >= 0)
);


-- =========================================================
-- TRANSACTIONS
-- Movimentações financeiras
-- =========================================================

CREATE TABLE tbl_transactions (
                                  id_transaction BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

                                  source_account_id BIGINT,
                                  destination_account_id BIGINT,

                                  amount NUMERIC(19, 2) NOT NULL,
                                  currency VARCHAR(3) NOT NULL DEFAULT 'BRL',

                                  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                  type transaction_type NOT NULL,
                                  status transaction_status NOT NULL DEFAULT 'PENDING',

                                  description VARCHAR(255),

                                  end_to_end_id VARCHAR(255) NOT NULL,

                                  CONSTRAINT fk_transaction_source_account
                                      FOREIGN KEY (source_account_id)
                                          REFERENCES tbl_account(id_account),

                                  CONSTRAINT fk_transaction_destination_account
                                      FOREIGN KEY (destination_account_id)
                                          REFERENCES tbl_account(id_account),

                                  CONSTRAINT chk_transaction_amount_positive
                                      CHECK (amount > 0),

                                  CONSTRAINT chk_transaction_accounts_by_type
                                      CHECK (
                                          (
                                              type = 'TRANSFER'
                                                  AND source_account_id IS NOT NULL
                                                  AND destination_account_id IS NOT NULL
                                                  AND source_account_id <> destination_account_id
                                              )
                                              OR
                                          (
                                              type = 'DEPOSIT'
                                                  AND source_account_id IS NULL
                                                  AND destination_account_id IS NOT NULL
                                              )
                                              OR
                                          (
                                              type = 'WITHDRAWAL'
                                                  AND source_account_id IS NOT NULL
                                                  AND destination_account_id IS NULL
                                              )
                                          )
);