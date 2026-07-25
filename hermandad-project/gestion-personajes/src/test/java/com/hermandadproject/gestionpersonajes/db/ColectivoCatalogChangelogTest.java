package com.hermandadproject.gestionpersonajes.db;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class ColectivoCatalogChangelogTest {

    private static final List<String> EXPECTED_CODES = List.of(
            "JUNTA_GOBIERNO",
            "HERMANOS",
            "CORTEJO_PROCESIONAL",
            "CUERPO_LITURGICO",
            "CUADRILLA_COSTALEROS",
            "BANDA_MUSICA",
            "PRIOSTIA",
            "ADMINISTRACION",
            "GRUPO_JOVEN",
            "ACCION_SOCIAL",
            "CLERO_PARROQUIA",
            "CONSEJO_HERMANDADES",
            "OTRAS_HERMANDADES",
            "AUTORIDADES",
            "MEDIOS_COMUNICACION",
            "REDES_SOCIALES",
            "PUBLICO_DEVOTOS",
            "PROVEEDORES",
            "ARTE_PATRIMONIO",
            "SEGURIDAD_EMERGENCIAS",
            "PROFESIONALES_ASESORES",
            "BENEFACTORES",
            "CANDIDATURAS",
            "PERSONAJES_HISTORICOS",
            "PERSONAJES_CONFLICTO"
    );

    @Test
    void catalogoIncluyeLosVeinticincoCodigosEsperados() throws IOException {
        String changelog = readCatalogChangelog();

        assertThat(changelog).contains("tableName: colectivos");
        assertThat(EXPECTED_CODES).allSatisfy(code -> assertThat(changelog).contains("value: " + code));
        assertThat(countMatches(changelog, "name: codigo")).isEqualTo(25);
    }

    @Test
    void todosLosRegistrosInicialesEstanActivosYTienenFechaCreacion() throws IOException {
        String changelog = readCatalogChangelog();

        assertThat(countMatches(changelog, "name: activo, valueBoolean: true")).isEqualTo(25);
        assertThat(countMatches(changelog, "name: fecha_creacion")).isEqualTo(25);
    }

    @Test
    void codigosSonUnicosYLosUuidSeGeneranAutomaticamente() throws IOException {
        String changelog = readCatalogChangelog();

        assertThat(countMatches(changelog, "name: id, valueComputed: UUID()"))
                .isEqualTo(25);
        assertThat(changelog).doesNotContainPattern(
                "name: id, value: [0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"
        );
        assertThat(EXPECTED_CODES).doesNotHaveDuplicates();
    }

    @Test
    void changelogMasterIncluyeCatalogoInicial() throws IOException {
        String master = Files.readString(Path.of("src/main/resources/db/changelog/db.changelog-master.yaml"));

        assertThat(master).contains("db/changelog/003-cargar-colectivos-iniciales.yaml");
    }

    private String readCatalogChangelog() throws IOException {
        return Files.readString(Path.of("src/main/resources/db/changelog/003-cargar-colectivos-iniciales.yaml"));
    }

    private int countMatches(String text, String value) {
        return text.split(Pattern.quote(value), -1).length - 1;
    }
}
