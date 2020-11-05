package kz.technodom.storage.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

@Component
public class CustomAuthenticationManager {

    LdapAuthenticationProvider provider = null;

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationManager.class);

    @Autowired
    private final LdapContextSource ldapContextSource;

    public CustomAuthenticationManager(LdapContextSource ldapContextSource) {
        this.ldapContextSource = ldapContextSource;
    }

    public Authentication authenticate(Authentication authentication) {
        BindAuthenticator bindAuth = new BindAuthenticator(ldapContextSource);
        FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch("", "(sAMAccountName={0})", ldapContextSource);
        try {
            bindAuth.setUserSearch(userSearch);
            bindAuth.afterPropertiesSet();
        } catch (Exception ex) {
            log.error("crashed out in Custom Authenticator");
            java.util.logging.Logger.getLogger(CustomAuthenticationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        provider = new LdapAuthenticationProvider(bindAuth);

        provider.setUserDetailsContextMapper(new UserDetailsContextMapper() {
            @Override
            public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection <? extends GrantedAuthority> clctn) {
                Collection <GrantedAuthority> grantedAuthorities = new ArrayList <>();
                grantedAuthorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
                grantedAuthorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.USER));
                return new org.springframework.security.core.userdetails.User(username, "1", grantedAuthorities);
            }

            @Override
            public void mapUserToContext(UserDetails ud, DirContextAdapter dca) {

            }
        });
        return provider.authenticate(authentication);
    }
}
