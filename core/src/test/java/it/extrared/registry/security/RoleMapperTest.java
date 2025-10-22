package it.extrared.registry.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class RoleMapperTest {

    @Inject RoleMapper roleMapper;

    @Test
    @RunOnVertxContext
    public void testRoleMapping1(UniAsserter asserter) {
        AuthenticationRequestContext context = Mockito.mock(AuthenticationRequestContext.class);
        asserter.assertThat(
                () -> roleMapper.augment(buildIdentityWith("ext_admin", "ext_eo"), context),
                si -> {
                    assertEquals(4, si.getRoles().size());
                    assertTrue(si.getRoles().contains(Roles.ADMIN.name()));
                    assertTrue(si.getRoles().contains(Roles.ECONOMIC_OPERATOR.name()));
                });
    }

    @Test
    @RunOnVertxContext
    public void testRoleMapping2(UniAsserter asserter) {
        AuthenticationRequestContext context = Mockito.mock(AuthenticationRequestContext.class);
        asserter.assertThat(
                () -> roleMapper.augment(buildIdentityWith("other_eo"), context),
                si -> {
                    assertEquals(2, si.getRoles().size());
                    assertTrue(si.getRoles().contains(Roles.ECONOMIC_OPERATOR.name()));
                });
    }

    private SecurityIdentity buildIdentityWith(String... roles) {
        QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder();
        for (String r : roles) builder.addRole(r);
        builder.setAnonymous(false);
        builder.setPrincipal(new QuarkusPrincipal("test-user"));
        return builder.build();
    }
}
