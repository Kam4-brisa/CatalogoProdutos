package com.example.CatalogoProdutos.config;

import com.example.CatalogoProdutos.model.Usuario;
import com.example.CatalogoProdutos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Este código será executado uma vez quando a aplicação iniciar
    @Bean
    public CommandLineRunner initialData(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se já existe um admin para não criar duplicado
            if (usuarioRepository.findByEmail("admin@email.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setEmail("admin@email.com");
                admin.setCpf("123456789");
                admin.setTelefone("47988887777");
                admin.setSenha(passwordEncoder.encode("admin"));
                usuarioRepository.save(admin);
                System.out.println("Usuário 'admin@email.com' criado com sucesso!");
            }
        };
    }
}