package com.hermandadproject.gestionpersonajes.db;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class RolPersonajeChangelogTest {

    private static final List<String> EXPECTED_CODES = List.of(
            "HERMANO_MAYOR",
            "MAYORDOMO",
            "CAPATAZ",
            "COSTALERO",
            "PERIODISTA",
            "INFLUENCER_COFRADE"
    );

    @Test
    void creaTablaRelacionesRestriccionesEIndiceEsperados() throws IOException {
        String changelog = readChangelog();

        assertThat(changelog).contains("tableName: roles_personaje");
        assertThat(changelog).contains("pk_roles_personaje");
        assertThat(changelog).contains("fk_roles_personaje_colectivo");
        assertThat(changelog).contains("uk_roles_personaje_colectivo_codigo");
        assertThat(changelog).contains("rol_personaje_id");
        assertThat(changelog).contains("fk_personajes_rol_personaje");
        assertThat(changelog).contains("idx_personajes_rol_personaje");
    }

    @Test
    void insertaRolesInicialesActivosConUuidFijosYUnicos() throws IOException {
        String changelog = readChangelog();

        assertThat(EXPECTED_CODES).allSatisfy(code -> assertThat(changelog).contains("'" + code + "'"));
        assertThat(countMatches(changelog, "UNION ALL SELECT")).isEqualTo(135);
        assertThat(changelog).contains("TRUE, CURRENT_TIMESTAMP");
        assertThat(extract(changelog, "(50000000-0000-0000-0000-[0-9]{12})"))
                .doesNotHaveDuplicates()
                .hasSize(136);
    }

    @Test
    void evitaDuplicadosPorColectivoYCodigo() throws IOException {
        String changelog = readChangelog();

        assertThat(changelog).contains("rp.colectivo_id = colectivos.id");
        assertThat(changelog).contains("rp.codigo = r_new.codigo");
        assertThat(changelog).contains("columnNames: colectivo_id, codigo");
    }

    @Test
    void todosLosRolesTienenColectivoCodigoNombreYDescripcion() throws IOException {
        String changelog = readChangelog();

        assertThat(countMatches(changelog, " AS colectivo_codigo")).isEqualTo(1);
        assertThat(countMatches(changelog, " AS codigo")).isEqualTo(1);
        assertThat(countMatches(changelog, " AS nombre")).isEqualTo(1);
        assertThat(countMatches(changelog, " AS descripcion")).isEqualTo(1);
        assertThat(countMatches(changelog, "UNION ALL SELECT") + 1).isEqualTo(136);
    }

    @Test
    void changelogMasterIncluyeRolesPersonaje() throws IOException {
        String master = Files.readString(Path.of("src/main/resources/db/changelog/db.changelog-master.yaml"));

        assertThat(master).contains("db/changelog/005-crear-roles-personaje.yaml");
    }

    private String readChangelog() throws IOException {
        return Files.readString(Path.of("src/main/resources/db/changelog/005-crear-roles-personaje.yaml"));
    }

    private int countMatches(String text, String value) {
        return text.split(Pattern.quote(value), -1).length - 1;
    }

    private List<String> extract(String text, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(text);
        return matcher.results()
                .map(result -> result.group(1))
                .toList();
    }
}
