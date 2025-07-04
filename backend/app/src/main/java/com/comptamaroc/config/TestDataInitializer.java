package com.comptamaroc.config;

import com.comptamaroc.core.entity.Role;
import com.comptamaroc.core.entity.User;
import com.comptamaroc.core.repository.UserRepository;
import com.comptamaroc.core.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TestDataInitializer(UserRepository userRepository, 
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            initializeRoles();
        }
        
        if (userRepository.count() == 0) {
            initializeUsers();
        }
    }

    private void initializeRoles() {
        // Create roles
        Role adminRole = new Role("ADMIN", "System administrator with full access");
        Role managerRole = new Role("MANAGER", "Manager with limited administrative access");
        Role accountantRole = new Role("ACCOUNTANT", "Accountant with financial data access");
        Role userRole = new Role("USER", "Regular user with basic access");

        roleRepository.save(adminRole);
        roleRepository.save(managerRole);
        roleRepository.save(accountantRole);
        roleRepository.save(userRole);

        System.out.println("✅ Test roles initialized");
    }

    private void initializeUsers() {
        // Get admin role
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        // Create admin user
        User adminUser = new User();
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setEmail("admin@comptamaroc.com");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setRole(adminRole);
        adminUser.setIsActive(true);

        userRepository.save(adminUser);

        // Create test accountant
        Role accountantRole = roleRepository.findByName("ACCOUNTANT")
                .orElseThrow(() -> new RuntimeException("Accountant role not found"));

        User accountantUser = new User();
        accountantUser.setFirstName("Hassan");
        accountantUser.setLastName("Alami");
        accountantUser.setEmail("hassan@comptamaroc.com");
        accountantUser.setPassword(passwordEncoder.encode("accountant123"));
        accountantUser.setRole(accountantRole);
        accountantUser.setIsActive(true);

        userRepository.save(accountantUser);

        System.out.println("✅ Test users initialized");
        System.out.println("   👤 Admin: admin@comptamaroc.com / admin123");
        System.out.println("   👤 Accountant: hassan@comptamaroc.com / accountant123");
    }
}
