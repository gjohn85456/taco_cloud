package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import tacos.User;
import tacos.data.UserRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        List<UserDetails> userList = new ArrayList<>();
//        userList.add(new User("buzz", encoder.encode("password"),
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//        userList.add(new User(
//                "woody", encoder.encode("password"),
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//        return new InMemoryUserDetailsManager(userList);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequests ->
                                authorizeHttpRequests
                                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                                        .requestMatchers("/design", "/orders").hasRole("USER")
                                        .requestMatchers("/", "/**").permitAll()
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(csrf-> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .formLogin(formLogin -> formLogin.loginPage("/login").permitAll());
        return http.build();


//        return http.authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/design", "/orders").hasRole("USER")
//                        .requestMatchers("/", "/**").permitAll()
//                        .anyRequest().authenticated()).build();

    }
}
