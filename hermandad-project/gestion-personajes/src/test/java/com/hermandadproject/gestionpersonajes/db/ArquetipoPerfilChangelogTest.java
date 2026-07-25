package com.hermandadproject.gestionpersonajes.db;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class ArquetipoPerfilChangelogTest {

    private static final List<String> EXPECTED_CODES = List.of(
            "CONCILIADOR",
            "TRADICIONALISTA",
            "RENOVADOR",
            "INFLUYENTE",
            "DEVOTO",
            "GESTOR",
            "POPULAR",
            "AMBICIOSO",
            "CONFLICTIVO",
            "DISCRETO",
            "CARISMATICO",
            "ESTRATEGA"
    );

    @Test
    void creaTablasRestriccionesYRelacionesEsperadas() throws IOException {
        String changelog = readChangelog();

        assertThat(changelog).contains("tableName: arquetipos_perfil");
        assertThat(changelog).contains("tableName: perfiles_personaje");
        assertThat(changelog).contains("pk_arquetipos_perfil");
        assertThat(changelog).contains("uk_arquetipos_perfil_codigo");
        assertThat(changelog).contains("pk_perfiles_personaje");
        assertThat(changelog).contains("uk_perfiles_personaje_personaje");
        assertThat(changelog).contains("fk_perfiles_personaje_personaje");
        assertThat(changelog).contains("fk_perfiles_personaje_arquetipo");
        assertThat(changelog).contains("referencedTableName: personajes");
        assertThat(changelog).contains("referencedTableName: arquetipos_perfil");
    }

    @Test
    void insertaDoceArquetiposActivosConUuidGeneradosAutomaticamente() throws IOException {
        String changelog = readChangelog();

        assertThat(EXPECTED_CODES).allSatisfy(code -> assertThat(changelog).contains("value: " + code));
        assertThat(countMatches(changelog, "name: codigo, value:")).isEqualTo(12);
        assertThat(countMatches(changelog, "name: activo, valueBoolean: true")).isEqualTo(12);
        assertThat(countMatches(changelog, "name: id, valueComputed: UUID()"))
                .isEqualTo(12);
        assertThat(changelog).doesNotContainPattern(
                "name: id, value: [0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"
        );
        assertThat(EXPECTED_CODES).doesNotHaveDuplicates();
    }

    @Test
    void todosLosValoresDeAtributosEstanEntreCeroYCien() throws IOException {
        List<Integer> values = extract(readChangelog(), "valueNumeric: ([0-9]+)").stream()
                .map(Integer::parseInt)
                .toList();

        assertThat(values).hasSize(204);
        assertThat(values).allSatisfy(value -> assertThat(value).isBetween(0, 100));
    }

    @Test
    void changelogMasterIncluyeArquetiposYPerfiles() throws IOException {
        String master = Files.readString(Path.of("src/main/resources/db/changelog/db.changelog-master.yaml"));

        assertThat(master).contains("db/changelog/004-crear-arquetipos-y-perfiles.yaml");
    }

    private String readChangelog() throws IOException {
        return Files.readString(Path.of("src/main/resources/db/changelog/004-crear-arquetipos-y-perfiles.yaml"));
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
