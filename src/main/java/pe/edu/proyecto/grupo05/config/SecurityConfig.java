package pe.edu.proyecto.grupo05.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/maintenance/login").permitAll()//establece rutas accesibles sin logeo
                        .requestMatchers("/maintenance/start").hasAnyRole("Admin")//accesible solo para Admin
                        .requestMatchers("/maintenance/create").hasAnyRole("Admin")
                        .requestMatchers("/maintenance/create-confirm").hasAnyRole("Admin")
                        .requestMatchers("/maintenance/detail/{id}").hasAnyRole("Admin")
                        .requestMatchers("/maintenance/edit/{id}").hasAnyRole("Admin")
                        .requestMatchers("/maintenance/edit-confirm").hasAnyRole("Admin")
                        .requestMatchers("/maintenance/delete/{id}").hasAnyRole("Admin")
                        .requestMatchers("/maintenance/clienteinicio").hasAnyRole("Admin, Cliente")
                        .requestMatchers("/maintenance/clientecompra").hasAnyRole("Admin, Cliente")

                        .requestMatchers("/maintenance-zapatilla/**").permitAll()

                        .anyRequest().authenticated()
                )

                //redireccionar a una pagina de error en caso no tenga permisos
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((req, resp, e) -> {
                            resp.sendRedirect("/maintenance/restricted");
                        })
                )

                // configurar el formulario de inicio de sesion
                .formLogin(form -> form
                        .loginPage("/maintenance/login")
                        .defaultSuccessUrl("/maintenance/start",true)
                        .permitAll()
                )

                // configurar el formulario de cerrar sesion
                .logout(logout -> logout
                        .logoutUrl("/maintenance/logout")
                        .logoutSuccessUrl("/maintenance/login?logout")
                        .permitAll()
                )
                // Desactivar CSRF temporalmente para rutas específicas (como Postman)
                    .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/maintenance-zapatilla/**")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //@Bean
    //public UserDetailsService userDetailsService() {
    //    return username -> User.builder()
    //            .username("pepito")
    //            .password(passwordEncoder().encode("123456"))
    //            .roles("ADMIN")
    //            .build();
    //}
}
