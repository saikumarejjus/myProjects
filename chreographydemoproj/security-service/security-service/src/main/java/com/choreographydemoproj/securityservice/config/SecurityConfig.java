package com.choreographydemoproj.securityservice.config;


 import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
 import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
 import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
 import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
 import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
 import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.config.ConfigurableBeanFactory;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.ComponentScan;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.context.annotation.Scope;
 import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.annotation.web.builders.WebSecurity;
 import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
 import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

//// Defines all annotations that are needed to integrate Keycloak in Spring Security
//@KeycloakConfiguration
//class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
//
//    @Autowired
//    RestAccessDeniedHandler restAccessDeniedHandler;
//
//    @Autowired
//    CustomKeycloakAuthenticationHandler customKeycloakAuthenticationHandler;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//    }
//
//    // Disable default role prefix ROLE_
//    @Autowired
//    public void configureGlobal( AuthenticationManagerBuilder auth) throws Exception {
//        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
//        auth.authenticationProvider(keycloakAuthenticationProvider);
//    }
//
//    // Use Spring Boot property files instead of default keycloak.json
//    @Bean
//    public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
//        return new KeycloakSpringBootConfigResolver();
//    }
//
//    // Register authentication strategy for public or confidential applications
//    @Bean
//    @Override
//    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
//    }
//
//    //Keycloak auth exception handler
//    @Bean
//    @Override
//    protected KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter() throws Exception {
//        KeycloakAuthenticationProcessingFilter filter = new KeycloakAuthenticationProcessingFilter(this.authenticationManagerBean());
//        filter.setSessionAuthenticationStrategy(this.sessionAuthenticationStrategy());
//        filter.setAuthenticationFailureHandler(customKeycloakAuthenticationHandler);
//        return filter;
//    }
//
//
//    @Override
//    public void init(WebSecurity builder) throws Exception {
//
//    }
//
//    @Override
//    public void configure(WebSecurity builder) throws Exception {
//
//        super.configure(http);
//        http.csrf().disable().cors().disable()
//                .authorizeRequests()
//                .antMatchers("/login", "/random").permitAll()
//                .antMatchers("/visitor").hasRole("visitor")
//                .antMatchers("/admin").hasRole("admin")
//                .anyRequest()
//                .authenticated();
//
//        //Custom error handler
//        http.exceptionHandling().accessDeniedHandler(restAccessDeniedHandler);
//
//    }
//}

@KeycloakConfiguration
@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter
{
    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    /**
     * Defines the session authentication strategy.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(buildSessionRegistry());
    }

    @Bean
    protected SessionRegistry buildSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        super.configure(http);
        http
                .authorizeRequests()
                .antMatchers("/customers*").hasRole("USER")
                .antMatchers("/admin*").hasRole("ADMIN")
                .anyRequest().permitAll();
    }

    private KeycloakAuthenticationProvider getKeycloakAuthenticationProvider() {
        KeycloakAuthenticationProvider authenticationProvider = keycloakAuthenticationProvider();
        SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
        mapper.setConvertToUpperCase(true);
        authenticationProvider.setGrantedAuthoritiesMapper(mapper);

        return authenticationProvider;
    }

    @Autowired
    public KeycloakClientRequestFactory keycloakClientRequestFactory;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public KeycloakRestTemplate keycloakRestTemplate() {
        return new KeycloakRestTemplate(keycloakClientRequestFactory);
    }

    @Bean
    public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
            KeycloakAuthenticationProcessingFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(
            KeycloakPreAuthActionsFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean keycloakAuthenticatedActionsFilterBean(
            KeycloakAuthenticatedActionsFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean keycloakSecurityContextRequestFilterBean(
            KeycloakSecurityContextRequestFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    @Override
    @ConditionalOnMissingBean(HttpSessionManager.class)
    protected HttpSessionManager httpSessionManager() {
        return new HttpSessionManager();
    }


    @Override
    public void init(WebSecurity builder) throws Exception {

    }

    @Override
    public void configure(WebSecurity builder) throws Exception {

    }
}
