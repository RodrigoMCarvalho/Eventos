package br.com.eventos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomLogoutSuccessHandler logoutSuccessHandler;
	
	@Autowired
	private ImplementsUserDetailsService UserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/").permitAll() 
				.antMatchers(HttpMethod.GET, "/cadastrarEvento").hasRole("ADMIN") //ROLE_ADMIN
				.antMatchers(HttpMethod.POST, "/cadastrarEvento").hasRole("ADMIN") //ROLE_ADMIN
				.anyRequest().authenticated()
				.antMatchers("/resources/**", "/webjars/**").permitAll()
				.and().formLogin().loginPage("/login")
				.usernameParameter("login")
				.passwordParameter("senha").permitAll()
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessHandler(logoutSuccessHandler); //para redirecionar após realizar logout no sistema
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication()
		//.withUser("rodrigo").password("{noop}123").roles("ADMIN");  // autenticação em memória
		auth.userDetailsService(UserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());   //autenticação via JPA com criptografia
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**", "/style/**");
	}

}
