package com.hermandadproject.gestionpersonajes.db;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PersonajesHermanoMayorChangelogTest {

    private static final List<String> PERSONAJE_CODES = List.of(
            "HM_EDUARDO_MARTIN",
            "HM_CARMEN_ROMERO",
            "HM_MANUEL_RUIZ",
            "HM_ALVARO_JIMENEZ",
            "HM_LUCIA_MENDOZA",
            "HM_ISABEL_FERNANDEZ"
    );

    @Test
    void normalizaCampoTextualAntiguoHaciaPerfilYEliminaLaColumna() throws IOException {
        String changelog = Files.readString(Path.of("src/main/resources/db/changelog/006-normalizar-tipo-personaje-en-perfil.yaml"));

        assertThat(changelog).contains("SET pp.arquetipo_origen_id = ap.id");
        assertThat(changelog).contains("UPPER(TRIM(ap.codigo)) = UPPER(TRIM(p.tipo_personaje))");
        assertThat(changelog).contains("UPPER(TRIM(ap.nombre)) = UPPER(TRIM(p.tipo_personaje))");
        assertThat(changelog).contains("columnName: tipo_personaje");
        assertThat(changelog).doesNotContain("arquetipo_perfil_id");
    }

    @Test
    void creaSeisPersonajesGenericosConUuidCalculadoYSinUsuarioNiAvatar() throws IOException {
        String changelog = readPersonajesChangelog();

        assertThat(PERSONAJE_CODES).allSatisfy(code -> assertThat(changelog).contains(code));
        assertThat(changelog).contains("Eduardo Martín");
        assertThat(changelog).contains("Romero Valdés");
        assertThat(changelog).contains("Ruiz Márquez");
        assertThat(changelog).contains("Álvaro");
        assertThat(changelog).contains("Jiménez Pardo");
        assertThat(changelog).contains("Lucía");
        assertThat(changelog).contains("Fernández Lozano");
        assertThat(changelog).contains("Córdoba");
        assertThat(changelog).contains("Málaga");
        assertThat(changelog).contains("Cádiz");
        assertThat(changelog).contains("formación jurídica");
        assertThat(changelog).contains("gestión prudente");
        assertThat(changelog).contains("comunicación");
        assertThat(changelog).contains("estación de penitencia");
        assertThat(changelog).doesNotContain("Eduardo Martin");
        assertThat(changelog).doesNotContain("Alvaro");
        assertThat(changelog).doesNotContain("Lucia");
        assertThat(changelog).doesNotContain("Jimenez");
        assertThat(changelog).doesNotContain("Marquez");
        assertThat(changelog).doesNotContain("Fernandez");
        assertThat(changelog).doesNotContain("Cordoba");
        assertThat(changelog).doesNotContain("Malaga");
        assertThat(changelog).doesNotContain("Cadiz");
        assertThat(changelog).doesNotContain("Ã");
        assertThat(changelog).doesNotContain("?");
        assertThat(changelog).contains("UUID()");
        assertThat(changelog).contains("JUNTA_GOBIERNO");
        assertThat(changelog).contains("HERMANO_MAYOR");
        assertThat(changelog).contains("FALSE");
        assertThat(changelog).doesNotContain("id_usuario");
        assertThat(changelog).doesNotContain("id_avatar");
        assertThat(changelog).doesNotContain("url_avatar");
    }

    @Test
    void creaPerfilesConArquetipoEnPerfilYNoEnPersonaje() throws IOException {
        String changelog = readPersonajesChangelog();

        assertThat(changelog).contains("INSERT INTO perfiles_personaje");
        assertThat(changelog).contains("arquetipo_origen_id");
        assertThat(changelog).contains("TRADICIONALISTA");
        assertThat(changelog).contains("RENOVADOR");
        assertThat(changelog).contains("CONCILIADOR");
        assertThat(changelog).contains("GESTOR");
        assertThat(changelog).contains("JOVEN_PROMETEDOR");
        assertThat(changelog).contains("DEVOTO");
        assertThat(changelog).doesNotContain("arquetipo_perfil_id");
    }

    @Test
    void changelogMasterIncluyeNormalizacionYPersonajesGenericos() throws IOException {
        String master = Files.readString(Path.of("src/main/resources/db/changelog/db.changelog-master.yaml"));

        assertThat(master).contains("db/changelog/006-normalizar-tipo-personaje-en-perfil.yaml");
        assertThat(master).contains("db/changelog/007-cargar-personajes-hermano-mayor.yaml");
    }

    private String readPersonajesChangelog() throws IOException {
        return Files.readString(Path.of("src/main/resources/db/changelog/007-cargar-personajes-hermano-mayor.yaml"));
    }
}
