package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V2__insert_example_medication extends BaseJavaMigration {
    @Override
    public void migrate(Context context) {
        /**     INSERT EXAMPLE MEDICATION
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("insert into medications (name, discount) " +
                        "values ('Example medication with null date', true)");
         */
    }
}
