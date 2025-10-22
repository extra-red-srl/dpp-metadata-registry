package it.extrared.registry.mocks;

import io.quarkus.arc.Unremovable;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.function.Function;
import org.mockito.Mockito;

@ApplicationScoped
@Unremovable
public class MockPool extends Pool {

    public MockPool() {
        super(Mockito.mock(io.vertx.sqlclient.Pool.class));
    }

    @Override
    public <T> Uni<T> withTransaction(Function<SqlConnection, Uni<T>> function) {
        return function.apply(Mockito.mock(SqlConnection.class));
    }
}
