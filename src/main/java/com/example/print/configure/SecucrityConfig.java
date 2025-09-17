package com.example.print.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import com.example.print.service.SqlUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecucrityConfig
{
    @Bean
    @Order(1)
    public SecurityFilterChain configure(HttpSecurity http) throws Exception 
    {
        http
		.securityContext((securityContext) -> securityContext
            .requireExplicitSave(true)

			.securityContextRepository(securityContextRepository()))

        .securityMatcher("/dados")                            
        .authorizeHttpRequests(authorize -> authorize
            .anyRequest().authenticated()                          
        )
        .formLogin(formLogin -> formLogin                          
            .loginPage("/login")
            .defaultSuccessUrl("/home")
            .successForwardUrl("/home")
            .failureForwardUrl("/test")
            .failureUrl("/test")
            .permitAll()
        )
        .logout(logout -> logout                                   
            .logoutSuccessUrl("/home")
            .permitAll()
        );
    
        return http.build();

    }

	@Bean
	public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception 
    {
		http
			.authorizeHttpRequests(authorize -> authorize
				.anyRequest().permitAll()
			);
		return http.build();
	}

   @Autowired
    private SqlUserDetailsService sqlUserDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(@Qualifier("SqlUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder)
    {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(sqlUserDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());

		ProviderManager providerManager = new ProviderManager(authenticationProvider);
		providerManager.setEraseCredentialsAfterAuthentication(false);

		return providerManager;
	}

    @Bean
    public SecurityContextRepository securityContextRepository() 
    {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
