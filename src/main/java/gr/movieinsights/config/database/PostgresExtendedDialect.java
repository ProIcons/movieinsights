package gr.movieinsights.config.database;

import io.github.jhipster.domain.util.FixedPostgreSQL10Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class PostgresExtendedDialect extends FixedPostgreSQL10Dialect {
    public PostgresExtendedDialect() {
        super();
        registerFunction("array_agg", new SQLFunctionTemplate(StandardBasicTypes.BLOB,"array_agg(?1)"));
    }
}
