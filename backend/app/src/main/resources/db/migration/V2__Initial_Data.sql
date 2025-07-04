-- Flyway migration script V2__Initial_Data.sql
-- Insert initial data for Moroccan Accounting Platform

-- Insert default roles
INSERT INTO roles (name, description) VALUES
    ('ADMIN', 'System administrator with full access'),
    ('MANAGER', 'Manager with limited administrative access'),
    ('ACCOUNTANT', 'Accountant with financial data access'),
    ('USER', 'Regular user with basic access');

-- Insert Moroccan VAT rates (as of 2024)
INSERT INTO vat_rates (rate_name, rate_percentage, vat_type, effective_date, description, tax_code) VALUES
    ('Standard Rate', 20.00, 'STANDARD', '2024-01-01', 'Standard VAT rate for most goods and services', 'TVA20'),
    ('Reduced Rate 1', 14.00, 'REDUCED_1', '2024-01-01', 'Reduced rate for hotels and restaurants', 'TVA14'),
    ('Reduced Rate 2', 10.00, 'REDUCED_2', '2024-01-01', 'Reduced rate for processed food products', 'TVA10'),
    ('Reduced Rate 3', 7.00, 'REDUCED_3', '2024-01-01', 'Reduced rate for medicine and books', 'TVA7'),
    ('Super Reduced', 5.50, 'SUPER_REDUCED', '2024-01-01', 'Super reduced rate for specific items', 'TVA5.5'),
    ('Exempt', 0.00, 'EXEMPT', '2024-01-01', 'Exempt from VAT', 'EXEMPT'),
    ('Zero Rate', 0.00, 'ZERO_RATE', '2024-01-01', 'Zero rate VAT', 'ZERO');

-- Insert basic Chart of Accounts following Moroccan PCMN (Plan Comptable Marocain Normalisé)
INSERT INTO chart_of_accounts (account_code, account_name, account_type, account_category, pcmn_code, description) VALUES
    -- Assets (Actif)
    ('2000', 'Immobilisations Incorporelles', 'ASSET', 'FIXED_ASSETS', '20', 'Intangible fixed assets'),
    ('2100', 'Immobilisations Corporelles', 'ASSET', 'FIXED_ASSETS', '21', 'Tangible fixed assets'),
    ('2300', 'Immobilisations en Cours', 'ASSET', 'FIXED_ASSETS', '23', 'Assets under construction'),
    ('3000', 'Stocks de Marchandises', 'ASSET', 'CURRENT_ASSETS', '30', 'Merchandise inventory'),
    ('3100', 'Matières et Fournitures', 'ASSET', 'CURRENT_ASSETS', '31', 'Raw materials and supplies'),
    ('3400', 'Produits Finis', 'ASSET', 'CURRENT_ASSETS', '34', 'Finished goods'),
    ('4111', 'Clients', 'ASSET', 'CURRENT_ASSETS', '4111', 'Accounts receivable - customers'),
    ('4456', 'TVA Récupérable', 'ASSET', 'CURRENT_ASSETS', '4456', 'VAT recoverable'),
    ('5161', 'Caisse', 'ASSET', 'CASH_AND_EQUIVALENTS', '5161', 'Cash on hand'),
    ('5141', 'Banque', 'ASSET', 'CASH_AND_EQUIVALENTS', '5141', 'Bank accounts'),
    
    -- Liabilities (Passif)
    ('4411', 'Fournisseurs', 'LIABILITY', 'CURRENT_LIABILITIES', '4411', 'Accounts payable - suppliers'),
    ('4455', 'TVA à Payer', 'LIABILITY', 'CURRENT_LIABILITIES', '4455', 'VAT payable'),
    ('4430', 'CNSS', 'LIABILITY', 'CURRENT_LIABILITIES', '4430', 'Social security payable'),
    ('1600', 'Emprunts', 'LIABILITY', 'LONG_TERM_LIABILITIES', '16', 'Long-term loans'),
    
    -- Equity (Capitaux Propres)
    ('1111', 'Capital Social', 'EQUITY', 'CAPITAL', '1111', 'Share capital'),
    ('1140', 'Réserves', 'EQUITY', 'RESERVES', '1140', 'Reserves'),
    ('1191', 'Report à Nouveau', 'EQUITY', 'RETAINED_EARNINGS', '1191', 'Retained earnings'),
    
    -- Revenue (Produits)
    ('7110', 'Ventes de Marchandises', 'REVENUE', 'OPERATING_REVENUE', '7110', 'Sales of merchandise'),
    ('7120', 'Ventes de Produits Finis', 'REVENUE', 'OPERATING_REVENUE', '7120', 'Sales of finished products'),
    ('7500', 'Produits Financiers', 'REVENUE', 'FINANCIAL_REVENUE', '75', 'Financial income'),
    
    -- Expenses (Charges)
    ('6110', 'Achats de Marchandises', 'EXPENSE', 'OPERATING_EXPENSES', '6110', 'Purchases of merchandise'),
    ('6120', 'Achats de Matières', 'EXPENSE', 'OPERATING_EXPENSES', '6120', 'Purchases of raw materials'),
    ('6170', 'Personnel', 'EXPENSE', 'OPERATING_EXPENSES', '6170', 'Personnel expenses'),
    ('6300', 'Impôts et Taxes', 'EXPENSE', 'TAX_EXPENSES', '63', 'Taxes and duties'),
    ('6700', 'Charges Financières', 'EXPENSE', 'FINANCIAL_EXPENSES', '67', 'Financial expenses');

-- Insert sample products
INSERT INTO products (name, description, sku, category, unit_price, cost_price, stock_quantity, min_stock_level) VALUES
    ('Ordinateur Portable', 'Laptop computer for business use', 'LAPTOP001', 'Electronics', 8500.00, 6800.00, 10, 2),
    ('Bureau en Bois', 'Wooden office desk', 'DESK001', 'Furniture', 2500.00, 1800.00, 5, 1),
    ('Chaise de Bureau', 'Ergonomic office chair', 'CHAIR001', 'Furniture', 1200.00, 900.00, 15, 3),
    ('Imprimante Laser', 'Black and white laser printer', 'PRINTER001', 'Electronics', 3200.00, 2400.00, 8, 2),
    ('Papier A4', 'White A4 printing paper (500 sheets)', 'PAPER001', 'Office Supplies', 45.00, 35.00, 100, 20);

-- Insert a default admin user (password should be changed in production)
-- Password is 'admin123' hashed with BCrypt
INSERT INTO users (first_name, last_name, email, password, role_id) 
SELECT 'Admin', 'User', 'admin@comptamaroc.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRdvX4OQzj6Jh/sNe0C9WFH/Z1i', r.id
FROM roles r WHERE r.name = 'ADMIN';
