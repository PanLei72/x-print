package com.x.print.infrastructure.security;

import com.x.print.domain.model.user.User;
import io.jmix.securitydata.user.AbstractDatabaseUserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Primary
@Component("UserRepository")
public class DatabaseUserRepository extends AbstractDatabaseUserRepository<User> {

    @Override
    protected Class<User> getUserClass() {
        return User.class;
    }

    @Override
    protected void initSystemUser(User systemUser) {
        Collection<GrantedAuthority> authorities = getGrantedAuthoritiesBuilder()
                .addResourceRole(FullAccessRole.CODE)
                .build();
        systemUser.setAuthorities(authorities);
    }

    @Override
    protected void initAnonymousUser(User anonymousUser) {
    }
}