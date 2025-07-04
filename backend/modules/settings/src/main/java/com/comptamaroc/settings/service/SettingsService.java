package com.comptamaroc.settings.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SettingsService {

    // In a real implementation, these would be stored in database
    private final Map<String, String> applicationSettings = new HashMap<>();
    private final Map<String, UserPreferences> userPreferences = new HashMap<>();

    public SettingsService() {
        // Initialize default application settings
        initializeDefaultSettings();
    }

    /**
     * Get application setting by key
     */
    public String getApplicationSetting(String key) {
        return applicationSettings.get(key);
    }

    /**
     * Set application setting
     */
    public void setApplicationSetting(String key, String value) {
        applicationSettings.put(key, value);
    }

    /**
     * Get user preferences
     */
    public UserPreferences getUserPreferences(String userId) {
        return userPreferences.getOrDefault(userId, getDefaultUserPreferences());
    }

    /**
     * Update user preferences
     */
    public void updateUserPreferences(String userId, UserPreferences preferences) {
        userPreferences.put(userId, preferences);
    }

    /**
     * Get company settings for Morocco-specific configurations
     */
    public CompanySettings getCompanySettings() {
        return new CompanySettings(
            getApplicationSetting("company.name"),
            getApplicationSetting("company.address"),
            getApplicationSetting("company.tax_id"),
            getApplicationSetting("company.cnss_number"),
            getApplicationSetting("company.currency"),
            getApplicationSetting("company.fiscal_year_start")
        );
    }

    /**
     * Update company settings
     */
    public void updateCompanySettings(CompanySettings settings) {
        setApplicationSetting("company.name", settings.getCompanyName());
        setApplicationSetting("company.address", settings.getAddress());
        setApplicationSetting("company.tax_id", settings.getTaxId());
        setApplicationSetting("company.cnss_number", settings.getCnssNumber());
        setApplicationSetting("company.currency", settings.getCurrency());
        setApplicationSetting("company.fiscal_year_start", settings.getFiscalYearStart());
    }

    /**
     * Initialize default application settings
     */
    private void initializeDefaultSettings() {
        applicationSettings.put("company.name", "");
        applicationSettings.put("company.address", "");
        applicationSettings.put("company.tax_id", "");
        applicationSettings.put("company.cnss_number", "");
        applicationSettings.put("company.currency", "MAD");
        applicationSettings.put("company.fiscal_year_start", "01-01");
        applicationSettings.put("invoice.auto_numbering", "true");
        applicationSettings.put("invoice.number_prefix", "INV");
        applicationSettings.put("invoice.default_payment_terms", "30");
        applicationSettings.put("vat.default_rate", "20.00");
        applicationSettings.put("backup.auto_backup", "true");
        applicationSettings.put("backup.frequency", "daily");
    }

    /**
     * Get default user preferences
     */
    private UserPreferences getDefaultUserPreferences() {
        return new UserPreferences(
            "fr", // French as default for Morocco
            "MAD",
            "dd/MM/yyyy",
            "DD/MM/YYYY HH:mm",
            "light",
            true,
            true
        );
    }

    // Data classes
    public static class UserPreferences {
        private final String language;
        private final String currency;
        private final String dateFormat;
        private final String timeFormat;
        private final String theme;
        private final boolean emailNotifications;
        private final boolean dashboardNotifications;

        public UserPreferences(String language, String currency, String dateFormat, String timeFormat,
                             String theme, boolean emailNotifications, boolean dashboardNotifications) {
            this.language = language;
            this.currency = currency;
            this.dateFormat = dateFormat;
            this.timeFormat = timeFormat;
            this.theme = theme;
            this.emailNotifications = emailNotifications;
            this.dashboardNotifications = dashboardNotifications;
        }

        // Getters
        public String getLanguage() { return language; }
        public String getCurrency() { return currency; }
        public String getDateFormat() { return dateFormat; }
        public String getTimeFormat() { return timeFormat; }
        public String getTheme() { return theme; }
        public boolean isEmailNotifications() { return emailNotifications; }
        public boolean isDashboardNotifications() { return dashboardNotifications; }
    }

    public static class CompanySettings {
        private final String companyName;
        private final String address;
        private final String taxId;
        private final String cnssNumber;
        private final String currency;
        private final String fiscalYearStart;

        public CompanySettings(String companyName, String address, String taxId, String cnssNumber,
                             String currency, String fiscalYearStart) {
            this.companyName = companyName;
            this.address = address;
            this.taxId = taxId;
            this.cnssNumber = cnssNumber;
            this.currency = currency;
            this.fiscalYearStart = fiscalYearStart;
        }

        // Getters
        public String getCompanyName() { return companyName; }
        public String getAddress() { return address; }
        public String getTaxId() { return taxId; }
        public String getCnssNumber() { return cnssNumber; }
        public String getCurrency() { return currency; }
        public String getFiscalYearStart() { return fiscalYearStart; }
    }
}
