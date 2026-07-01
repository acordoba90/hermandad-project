# Diagrama de entidades: gestion-usuarios y gestion-hermandades

```mermaid
erDiagram
    %% Modulo gestion-usuarios
    USERS {
        CHAR36 id PK
        VARCHAR_100 username
        VARCHAR_150 email UK
        VARCHAR_255 password_hash
        DATETIME created_at
        DATETIME updated_at
        DATETIME fecha_inicio_vigencia
        DATETIME fecha_fin_vigencia
    }

    %% Modulo gestion-hermandades
    HERMANDADES {
        CHAR36 id PK
        CHAR36 user_id FK
        VARCHAR_150 name
        VARCHAR_100 city
        INT foundation_year
        VARCHAR_30 estado
        INT prestigio
        INT popularidad
        INT devocion
        INT solemnidad
        CHAR36 uuid_tipo_hermandad FK
        CHAR36 uuid_carisma_principal FK
        DECIMAL_12_2 money
        INT prestige
        INT devotion
        INT internal_satisfaction
        DATETIME created_at
        DATETIME updated_at
    }

    TIPO_HERMANDAD {
        CHAR36 uuid PK
        VARCHAR_50 codigo UK
        VARCHAR_150 nombre
        VARCHAR_500 descripcion
        INT nivel
        BOOLEAN activo
        BOOLEAN puede_estacion_penitencia
        BOOLEAN puede_cultos_externos
        BOOLEAN puede_tener_sede_canonica
        BOOLEAN puede_tener_paso
        INT prestigio_base
        INT orden
    }

    CARISMA_HERMANDAD {
        CHAR36 uuid PK
        VARCHAR_50 codigo UK
        VARCHAR_150 nombre
        VARCHAR_800 descripcion
        BOOLEAN activo
        INT orden
        INT prestigio_base
        INT popularidad_base
        INT solemnidad_base
        INT devocion_base
        INT impacto_economico_base
    }

    HERMANDAD_CARISMA_SECUNDARIO {
        CHAR36 uuid_hermandad PK,FK
        CHAR36 uuid_carisma PK,FK
    }

    ECONOMIA_HERMANDAD {
        CHAR36 uuid PK
        CHAR36 uuid_hermandad FK,UK
        DECIMAL_12_2 saldo_actual
        DECIMAL_12_2 ingresos_mensuales
        DECIMAL_12_2 gastos_mensuales
        DECIMAL_12_2 deuda_actual
        DECIMAL_12_2 patrimonio_estimado
        INT nivel_estabilidad_economica
        DATE fecha_ultima_actualizacion
    }

    MOVIMIENTO_ECONOMICO_HERMANDAD {
        CHAR36 uuid PK
        CHAR36 uuid_hermandad FK
        VARCHAR_20 tipo_movimiento
        VARCHAR_50 categoria
        VARCHAR_200 concepto
        VARCHAR_800 descripcion
        DECIMAL_12_2 importe
        DATE fecha_movimiento
        DATETIME fecha_registro
    }

    USERS ||--o{ HERMANDADES : "fk_hermandades_users"
    TIPO_HERMANDAD ||--o{ HERMANDADES : "fk_hermandades_tipo_hermandad"
    CARISMA_HERMANDAD |o--o{ HERMANDADES : "fk_hermandades_carisma_principal"
    HERMANDADES ||--o{ HERMANDAD_CARISMA_SECUNDARIO : "fk_hcs_hermandad"
    CARISMA_HERMANDAD ||--o{ HERMANDAD_CARISMA_SECUNDARIO : "fk_hcs_carisma"
    HERMANDADES ||--o| ECONOMIA_HERMANDAD : "fk_economia_hermandad_hermandad"
    HERMANDADES ||--o{ MOVIMIENTO_ECONOMICO_HERMANDAD : "fk_mov_eco_hermandad"
```

## Relaciones

| Relacion | Cardinalidad | Clave |
| --- | --- | --- |
| `users` -> `hermandades` | Un usuario puede tener 0..N hermandades; cada hermandad pertenece a 1 usuario. | `hermandades.user_id` -> `users.id` |
| `tipo_hermandad` -> `hermandades` | Un tipo puede clasificar 0..N hermandades; cada hermandad tiene 1 tipo obligatorio. | `hermandades.uuid_tipo_hermandad` -> `tipo_hermandad.uuid` |
| `carisma_hermandad` -> `hermandades` como carisma principal | Un carisma puede ser principal en 0..N hermandades; cada hermandad puede tener 0..1 carisma principal. | `hermandades.uuid_carisma_principal` -> `carisma_hermandad.uuid` |
| `hermandades` -> `hermandad_carisma_secundario` | Una hermandad puede tener 0..N carismas secundarios. | `hermandad_carisma_secundario.uuid_hermandad` -> `hermandades.id` |
| `carisma_hermandad` -> `hermandad_carisma_secundario` | Un carisma puede aparecer como secundario en 0..N hermandades. | `hermandad_carisma_secundario.uuid_carisma` -> `carisma_hermandad.uuid` |
| `hermandades` -> `economia_hermandad` | Una hermandad puede tener 0..1 registro economico; cada economia pertenece a 1 hermandad. | `economia_hermandad.uuid_hermandad` -> `hermandades.id` |
| `hermandades` -> `movimiento_economico_hermandad` | Una hermandad puede tener 0..N movimientos economicos; cada movimiento pertenece a 1 hermandad. | `movimiento_economico_hermandad.uuid_hermandad` -> `hermandades.id` |

## Notas

- La relacion entre `hermandades` y `users` cruza los modulos: `gestion-hermandades` guarda `user_id` como `UUID`, no como asociacion JPA a `UserEntity`, pero Liquibase define la FK `fk_hermandades_users`.
- `hermandad_carisma_secundario` es la tabla intermedia de la relacion N..M entre `hermandades` y `carisma_hermandad`.
- `economia_hermandad.uuid_hermandad` tiene una restriccion unica, por eso modela una relacion 1 a 0..1 desde `hermandades`.
