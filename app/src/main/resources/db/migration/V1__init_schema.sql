-- Enable UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Roles table
CREATE TABLE roles (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(20) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Companies table
CREATE TABLE companies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    legal_form VARCHAR(50),
    tax_id VARCHAR(50),
    rc_number VARCHAR(50),
    ice_number VARCHAR(50),
    cnss_number VARCHAR(50),
    address TEXT,
    city VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(50) DEFAULT 'Maroc',
    phone_number VARCHAR(50),
    email VARCHAR(100),
    website VARCHAR(255),
    logo TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Users table
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(50),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    last_login TIMESTAMP,
    company_id UUID REFERENCES companies(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- User roles junction table
CREATE TABLE user_roles (
    user_id UUID NOT NULL REFERENCES users(id),
    role_id UUID NOT NULL REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

-- Clients table
CREATE TABLE clients (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    client_type VARCHAR(20) NOT NULL,
    tax_id VARCHAR(50),
    ice_number VARCHAR(50),
    contact_name VARCHAR(255),
    email VARCHAR(100),
    phone_number VARCHAR(50),
    address TEXT,
    city VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(50) DEFAULT 'Maroc',
    notes TEXT,
    company_id UUID NOT NULL REFERENCES companies(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Suppliers table
CREATE TABLE suppliers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    supplier_type VARCHAR(20) NOT NULL,
    tax_id VARCHAR(50),
    ice_number VARCHAR(50),
    rc_number VARCHAR(50),
    contact_name VARCHAR(255),
    email VARCHAR(100),
    phone_number VARCHAR(50),
    address TEXT,
    city VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(50) DEFAULT 'Maroc',
    payment_terms VARCHAR(100),
    notes TEXT,
    company_id UUID NOT NULL REFERENCES companies(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Products table
CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    sku_code VARCHAR(50),
    type VARCHAR(20) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    cost_price DECIMAL(10, 2),
    vat_rate DECIMAL(5, 2),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    company_id UUID NOT NULL REFERENCES companies(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Invoices table
CREATE TABLE invoices (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    invoice_number VARCHAR(50) NOT NULL,
    invoice_date DATE NOT NULL,
    due_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    client_id UUID REFERENCES clients(id),
    client_name VARCHAR(255),
    subtotal DECIMAL(12, 2) NOT NULL DEFAULT 0,
    vat_amount DECIMAL(12, 2) NOT NULL DEFAULT 0,
    total_amount DECIMAL(12, 2) NOT NULL DEFAULT 0,
    payment_terms VARCHAR(255),
    notes TEXT,
    company_id UUID NOT NULL REFERENCES companies(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Invoice items table
CREATE TABLE invoice_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    invoice_id UUID NOT NULL REFERENCES invoices(id) ON DELETE CASCADE,
    product_id UUID REFERENCES products(id),
    description VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(12, 2) NOT NULL,
    vat_rate DECIMAL(5, 2) NOT NULL,
    vat_amount DECIMAL(12, 2) NOT NULL,
    subtotal DECIMAL(12, 2) NOT NULL,
    total DECIMAL(12, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Bills table (Supplier invoices)
CREATE TABLE bills (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bill_number VARCHAR(50) NOT NULL,
    supplier_reference VARCHAR(100),
    bill_date DATE NOT NULL,
    due_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    supplier_id UUID REFERENCES suppliers(id),
    supplier_name VARCHAR(255),
    subtotal DECIMAL(12, 2) NOT NULL DEFAULT 0,
    vat_amount DECIMAL(12, 2) NOT NULL DEFAULT 0,
    total_amount DECIMAL(12, 2) NOT NULL DEFAULT 0,
    notes TEXT,
    entry_id UUID,
    company_id UUID NOT NULL REFERENCES companies(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Bill items table
CREATE TABLE bill_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    bill_id UUID NOT NULL REFERENCES bills(id) ON DELETE CASCADE,
    product_id UUID REFERENCES products(id),
    description VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(12, 2) NOT NULL,
    vat_rate DECIMAL(5, 2) NOT NULL,
    vat_amount DECIMAL(12, 2) NOT NULL,
    subtotal DECIMAL(12, 2) NOT NULL,
    total DECIMAL(12, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Accounts table (Chart of Accounts)
CREATE TABLE accounts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(20) NOT NULL,
    account_class INTEGER NOT NULL,
    parent_id UUID REFERENCES accounts(id),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    balance DECIMAL(19, 4) NOT NULL DEFAULT 0,
    company_id UUID NOT NULL REFERENCES companies(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0,
    UNIQUE (code, company_id)
);

-- Journals table
CREATE TABLE journals (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    company_id UUID NOT NULL REFERENCES companies(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0,
    UNIQUE (code, company_id)
);

-- Entries table (Journal Entries)
CREATE TABLE entries (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    entry_date DATE NOT NULL,
    entry_number VARCHAR(50) NOT NULL,
    journal_id UUID NOT NULL REFERENCES journals(id),
    description TEXT,
    reference_type VARCHAR(50),
    reference_id UUID,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    company_id UUID NOT NULL REFERENCES companies(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Entry lines table
CREATE TABLE entry_lines (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    entry_id UUID NOT NULL REFERENCES entries(id) ON DELETE CASCADE,
    account_id UUID NOT NULL REFERENCES accounts(id),
    description TEXT,
    debit DECIMAL(19, 4) NOT NULL DEFAULT 0,
    credit DECIMAL(19, 4) NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- TVA declarations table
CREATE TABLE tva_declarations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    declaration_period VARCHAR(7) NOT NULL, -- Format: YYYY-MM
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    due_date DATE NOT NULL,
    filing_date DATE,
    reference_number VARCHAR(50),
    type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    total_collected DECIMAL(15, 2) NOT NULL DEFAULT 0,
    total_deductible DECIMAL(15, 2) NOT NULL DEFAULT 0,
    tva_due DECIMAL(15, 2) NOT NULL DEFAULT 0,
    previous_credit DECIMAL(15, 2) NOT NULL DEFAULT 0,
    net_amount DECIMAL(15, 2) NOT NULL DEFAULT 0,
    company_id UUID NOT NULL REFERENCES companies(id),
    entry_id UUID REFERENCES entries(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0,
    UNIQUE (declaration_period, company_id)
);

-- TVA lines table
CREATE TABLE tva_lines (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    declaration_id UUID NOT NULL REFERENCES tva_declarations(id) ON DELETE CASCADE,
    type VARCHAR(20) NOT NULL,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    base_amount DECIMAL(15, 2) NOT NULL DEFAULT 0,
    rate DECIMAL(5, 2) NOT NULL,
    tva_amount DECIMAL(15, 2) NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Employees table
CREATE TABLE employees (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    cin_number VARCHAR(20),
    cnss_number VARCHAR(20),
    date_of_birth DATE,
    hire_date DATE,
    contract_type VARCHAR(20),
    job_title VARCHAR(100),
    department VARCHAR(100),
    email VARCHAR(100),
    phone_number VARCHAR(50),
    address TEXT,
    basic_salary DECIMAL(10, 2),
    hourly_rate DECIMAL(8, 2),
    bank_name VARCHAR(100),
    bank_account VARCHAR(50),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    company_id UUID NOT NULL REFERENCES companies(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Payrolls table
CREATE TABLE payrolls (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    payroll_period VARCHAR(7) NOT NULL, -- Format: YYYY-MM
    payment_date DATE,
    employee_id UUID NOT NULL REFERENCES employees(id),
    employee_name VARCHAR(200) NOT NULL,
    basic_salary DECIMAL(10, 2) NOT NULL,
    worked_days INTEGER,
    overtime_hours DECIMAL(6, 2),
    overtime_amount DECIMAL(10, 2),
    bonus DECIMAL(10, 2),
    gross_salary DECIMAL(10, 2) NOT NULL,
    cnss_employee DECIMAL(10, 2) NOT NULL,
    cnss_employer DECIMAL(10, 2) NOT NULL,
    amo DECIMAL(10, 2) NOT NULL,
    ir DECIMAL(10, 2) NOT NULL,
    net_salary DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    entry_id UUID REFERENCES entries(id),
    company_id UUID NOT NULL REFERENCES companies(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- CNSS declarations table
CREATE TABLE cnss_declarations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    declaration_period VARCHAR(7) NOT NULL, -- Format: YYYY-MM
    due_date DATE NOT NULL,
    submission_date DATE,
    reference_number VARCHAR(50),
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    total_employees INTEGER,
    total_salary_mass DECIMAL(12, 2) NOT NULL DEFAULT 0,
    total_employee_contribution DECIMAL(12, 2) NOT NULL DEFAULT 0,
    total_employer_contribution DECIMAL(12, 2) NOT NULL DEFAULT 0,
    total_amount DECIMAL(12, 2) NOT NULL DEFAULT 0,
    company_id UUID NOT NULL REFERENCES companies(id),
    entry_id UUID REFERENCES entries(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- CNSS declaration lines table
CREATE TABLE cnss_declaration_lines (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    declaration_id UUID NOT NULL REFERENCES cnss_declarations(id) ON DELETE CASCADE,
    employee_id UUID NOT NULL REFERENCES employees(id),
    employee_name VARCHAR(200) NOT NULL,
    cnss_number VARCHAR(20),
    cin_number VARCHAR(20),
    worked_days INTEGER,
    salary_amount DECIMAL(10, 2) NOT NULL,
    employee_contribution DECIMAL(10, 2) NOT NULL,
    employer_contribution DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Reports table
CREATE TABLE reports (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(50) NOT NULL,
    report_format VARCHAR(20),
    file_url TEXT,
    parameters TEXT,
    last_generated TIMESTAMP,
    company_id UUID NOT NULL REFERENCES companies(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Company settings table
CREATE TABLE company_settings (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    company_id UUID NOT NULL REFERENCES companies(id) UNIQUE,
    default_currency VARCHAR(3) DEFAULT 'MAD',
    default_language VARCHAR(2) DEFAULT 'fr',
    fiscal_year_start_month INTEGER DEFAULT 1,
    fiscal_year_start_day INTEGER DEFAULT 1,
    invoice_prefix VARCHAR(20) DEFAULT 'INV-',
    invoice_next_number INTEGER DEFAULT 1,
    bill_prefix VARCHAR(20) DEFAULT 'BILL-',
    bill_next_number INTEGER DEFAULT 1,
    default_payment_terms INTEGER DEFAULT 30,
    default_vat_rate DECIMAL(5, 2) DEFAULT 20.0,
    enable_multi_currency BOOLEAN DEFAULT FALSE,
    enable_attachments BOOLEAN DEFAULT TRUE,
    logo_url TEXT,
    invoice_notes_template TEXT,
    email_template_invoice TEXT,
    enable_automatic_backups BOOLEAN DEFAULT FALSE,
    backup_frequency VARCHAR(20) DEFAULT 'WEEKLY',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- User preferences table
CREATE TABLE user_preferences (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) UNIQUE,
    preferred_language VARCHAR(2) DEFAULT 'fr',
    preferred_theme VARCHAR(20) DEFAULT 'light',
    dashboard_layout TEXT,
    enable_notifications BOOLEAN DEFAULT TRUE,
    email_notifications BOOLEAN DEFAULT TRUE,
    items_per_page INTEGER DEFAULT 10,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    last_modified_by UUID,
    version BIGINT DEFAULT 0
);

-- Create indexes
CREATE INDEX idx_users_company ON users(company_id);
CREATE INDEX idx_clients_company ON clients(company_id);
CREATE INDEX idx_suppliers_company ON suppliers(company_id);
CREATE INDEX idx_products_company ON products(company_id);
CREATE INDEX idx_invoices_company ON invoices(company_id);
CREATE INDEX idx_invoices_client ON invoices(client_id);
CREATE INDEX idx_bills_company ON bills(company_id);
CREATE INDEX idx_bills_supplier ON bills(supplier_id);
CREATE INDEX idx_accounts_company ON accounts(company_id);
CREATE INDEX idx_accounts_parent ON accounts(parent_id);
CREATE INDEX idx_journals_company ON journals(company_id);
CREATE INDEX idx_entries_company ON entries(company_id);
CREATE INDEX idx_entries_journal ON entries(journal_id);
CREATE INDEX idx_tva_declarations_company ON tva_declarations(company_id);
CREATE INDEX idx_employees_company ON employees(company_id);
CREATE INDEX idx_payrolls_company ON payrolls(company_id);
CREATE INDEX idx_payrolls_employee ON payrolls(employee_id);
CREATE INDEX idx_cnss_declarations_company ON cnss_declarations(company_id);
CREATE INDEX idx_reports_company ON reports(company_id);