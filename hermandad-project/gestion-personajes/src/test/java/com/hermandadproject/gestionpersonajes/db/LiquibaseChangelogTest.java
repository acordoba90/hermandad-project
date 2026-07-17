package com.hermandadproject.gestionpersonajes.db;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.Test;

class LiquibaseChangelogTest {

    @Test
    void changelogMasterEsValido() throws Exception {
        ClassLoaderResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
        Database database = DatabaseFactory.getInstance()
                .openDatabase("offline:mysql", null, null, null, resourceAccessor);

        try (Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-master.yaml",
                resourceAccessor,
                database
        )) {
            liquibase.validate();
        }
    }
}
