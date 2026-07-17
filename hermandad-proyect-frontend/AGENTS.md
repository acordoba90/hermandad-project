# AGENTS.md — Frontend de Hermandad Project

## 1. Objetivo

Este repositorio contiene el frontend de **Hermandad Project**, un videojuego web de gestión de hermandades y cofradías.

Codex debe realizar cambios respetando la arquitectura existente, las convenciones visuales del proyecto y los contratos definidos por los servicios backend.

Antes de modificar código:

1. Analiza la estructura de la funcionalidad afectada.
2. Revisa componentes, hooks, servicios y estilos similares.
3. Identifica rutas, textos, validaciones y llamadas REST relacionadas.
4. Evita duplicar componentes o crear soluciones paralelas.
5. Limita los cambios al alcance solicitado.
6. Revisa si existen cambios locales del usuario antes de sobrescribir archivos.

---

## 2. Tecnologías principales

El frontend utiliza principalmente:

- React 18.
- Vite.
- Material UI (MUI).
- React Router.
- JavaScript o TypeScript, según la configuración real del repositorio.
- API REST para la comunicación con los servicios backend.

Antes de utilizar una API o funcionalidad concreta, comprueba las versiones declaradas en `package.json`.

No actualices React, Vite, MUI, React Router, dependencias o plugins salvo que se solicite expresamente.

No migres archivos JavaScript a TypeScript, ni TypeScript a JavaScript, salvo petición expresa.

---

## 3. Identidad visual

El proyecto utiliza una estética oscura inspirada en el mundo cofrade.

Valores de referencia:

```text
primary:    #7B1E1E
secondary:  #D4AF37
background: #121212
```

Reglas:

- Reutiliza el tema global de MUI.
- No repitas colores del tema mediante valores hexadecimales si ya están disponibles en `theme.palette`.
- No crees un segundo tema paralelo.
- Mantén una estética sobria, elegante y coherente con Hermandad Project.
- Evita interfaces genéricas que parezcan un panel administrativo convencional.
- Prioriza una apariencia de videojuego de gestión.
- Mantén contraste suficiente y legibilidad.
- No utilices efectos, sombras o animaciones excesivas.
- Respeta los diseños existentes antes de introducir nuevos patrones visuales.

---

## 4. Estructura del proyecto

El frontend organiza las funcionalidades por módulos o `features`.

Antes de crear archivos nuevos:

- Revisa la organización existente.
- Mantén los nombres de carpetas y archivos ya utilizados.
- Coloca cada funcionalidad en su módulo correspondiente.
- No crees carpetas genéricas como `utils2`, `componentsNew` o `misc`.
- No dupliques componentes existentes.
- No muevas archivos fuera del alcance de la tarea.

Ejemplos de áreas existentes o previstas:

```text
src/
├── features/
│   ├── gestionLogin/
│   ├── accountActivation/
│   ├── gestionPersonajes/
│   ├── gestionHermandades/
│   └── ...
├── services/
├── components/
├── context/
├── hooks/
├── routes/
├── theme/
└── assets/
```

Adapta esta estructura a la organización real del repositorio.

---

## 5. Organización de cada feature

Cuando una funcionalidad tenga suficiente complejidad, separa responsabilidades.

Estructura conceptual:

```text
feature/
├── FeaturePage.jsx
├── featureFunctions.js
├── featureTexts.js
├── components/
├── hooks/
└── tests/
```

No fuerces esta estructura si el módulo existente sigue otra convención.

Principios:

- El componente principal coordina la pantalla.
- La lógica reutilizable debe extraerse a hooks o funciones.
- Los textos relevantes deben centralizarse cuando el módulo ya siga ese patrón.
- Todos los estilos del frontend deben centralizarse en `styles/appStyles.js`.
- No crees archivos de estilos dentro de las features.
- Los componentes pequeños y específicos pueden permanecer dentro de la feature.
- Los componentes reutilizables entre varias features deben ubicarse en el directorio común existente.

---

## 6. Componentes React

Los componentes deben:

- Tener una única responsabilidad clara.
- Ser legibles y de tamaño razonable.
- Evitar lógica de negocio compleja dentro del JSX.
- Reutilizar componentes existentes.
- Utilizar nombres descriptivos.
- Mantener una separación clara entre presentación, estado y comunicación con la API.
- Evitar efectos secundarios durante el renderizado.
- No mutar directamente `props` ni estado.
- Utilizar claves estables en listas.
- Evitar índices como `key` cuando exista un identificador estable.
- Gestionar correctamente estados de carga, error, vacío y éxito.
- Limpiar timers, listeners y suscripciones en los efectos.

No crees componentes que solo envuelvan otro componente sin aportar comportamiento o significado.

---

## 7. Hooks

- Utiliza hooks únicamente en componentes React o hooks personalizados.
- Respeta las reglas oficiales de hooks.
- Define correctamente las dependencias de `useEffect`, `useMemo` y `useCallback`.
- No elimines dependencias para silenciar advertencias.
- Evita `useMemo` y `useCallback` cuando no aporten una mejora real.
- Extrae hooks personalizados cuando una lógica sea reutilizable o compleja.
- Los hooks deben comenzar por `use`.
- Evita hooks que mezclen demasiadas responsabilidades.
- No realices llamadas REST directamente desde múltiples componentes si existe una capa de servicios.

---

## 8. Estado y contexto

Utiliza estado local para información exclusiva de un componente o pantalla.

Utiliza contexto únicamente cuando el estado deba compartirse entre áreas relacionadas, por ejemplo:

- Usuario autenticado.
- Datos de la partida actual.
- Personaje seleccionado.
- Hermandad activa.
- Configuración global de interfaz.

Reglas:

- No conviertas todo el estado en global.
- No crees contextos excesivamente grandes.
- Separa contextos con responsabilidades diferentes.
- Evita almacenar datos derivados si pueden calcularse.
- Evita que un contexto provoque renderizados globales innecesarios.
- Mantén las operaciones del contexto documentadas y predecibles.
- No añadas Redux, Zustand u otra librería de estado sin petición expresa.

---

## 9. Comunicación con el backend

Las llamadas REST deben centralizarse en la capa de servicios existente.

Hermandad Project utiliza un archivo o módulo central denominado conceptualmente `mappedService` para:

- Centralizar las URLs de los servicios.
- Definir `requestMapping`.
- Evitar URLs REST dispersas por los componentes.
- Facilitar cambios de entorno.

Reglas:

- No escribas URLs completas directamente en componentes.
- No dupliques endpoints en varios archivos.
- Reutiliza `mappedService` o la abstracción equivalente existente.
- Separa la definición del endpoint de la ejecución de la petición cuando el proyecto ya siga ese patrón.
- Utiliza variables de entorno de Vite para URLs base.
- No incluyas URLs de producción de forma fija.
- No incluyas credenciales ni secretos en el frontend.
- No asumas que una respuesta HTTP correcta siempre contiene todos los campos.
- Gestiona errores de red y respuestas inesperadas.
- Respeta los contratos de los DTOs del backend.

Antes de añadir un endpoint:

1. Revisa si ya existe en `mappedService`.
2. Revisa la convención de nombres.
3. Comprueba el método HTTP.
4. Comprueba el cuerpo y los parámetros.
5. Comprueba los códigos de respuesta esperados.

---

## 10. Variables de entorno

Las variables accesibles desde Vite deben usar el prefijo requerido por el proyecto, normalmente:

```text
VITE_
```

Ejemplo conceptual:

```properties
VITE_API_GESTION_USUARIOS_URL=http://localhost:8080
```

Reglas:

- No incluyas secretos en variables del frontend.
- Todo valor expuesto al frontend debe considerarse público.
- No subas credenciales.
- No dupliques la misma URL en varios archivos.
- Mantén archivos de ejemplo cuando el repositorio ya utilice `.env.example`.
- No sobrescribas archivos locales `.env` del usuario.

---

## 11. Formularios

Los formularios deben:

- Validar en el frontend para mejorar la experiencia.
- Asumir que el backend volverá a validar todos los datos.
- Mostrar mensajes claros junto al campo o en el área adecuada.
- Evitar enviar el formulario varias veces.
- Desactivar el botón principal mientras se procesa la petición.
- Conservar los datos introducidos cuando un error recuperable ocurra.
- Gestionar correctamente `Enter`, foco y navegación por teclado.
- Diferenciar errores de validación, red y negocio.

No utilices mensajes genéricos como “Ha ocurrido un error” cuando exista información funcional más útil.

Para contraseñas:

- No registres el valor.
- No lo almacenes en contexto global.
- No lo conserves más tiempo del necesario.
- Permite mostrar u ocultar el valor cuando el patrón visual existente lo contemple.
- Respeta las reglas comunicadas por el backend.

---

## 12. Material UI y estilos

- Reutiliza componentes de MUI.
- Utiliza el tema global.
- Todos los estilos del frontend están centralizados en:

```text
styles/appStyles.js
```

- Reutiliza y amplía `styles/appStyles.js` cuando sea necesario.
- No crees archivos como `styles.js`, `featureStyles.js`, `componentStyles.js` ni otros ficheros de estilos paralelos.
- No declares estilos extensos directamente dentro de los componentes.
- Evita `sx` con objetos grandes o repetidos dentro del JSX; define esos estilos en `styles/appStyles.js`.
- `sx` puede utilizarse únicamente para ajustes pequeños, dinámicos y no reutilizables.
- No utilices estilos inline mediante el atributo HTML `style` salvo caso excepcional y debidamente justificado.
- No añadas CSS, CSS Modules, Styled Components, Emotion personalizado ni otro sistema de estilos sin petición expresa.
- Evita valores mágicos repetidos.
- Utiliza breakpoints del tema desde los estilos centralizados.
- Mantén consistencia en bordes, espaciados, radios y tipografía.
- No recrees manualmente componentes que MUI ya proporciona.

Cuando se modifique un modal:

- Gestiona apertura y cierre de forma explícita.
- Mantén un título accesible.
- Evita cerrar accidentalmente durante operaciones críticas.
- Gestiona correctamente el botón principal, secundario y estado de carga.

---

## 13. Diseño responsive

Todas las pantallas nuevas deben revisarse al menos para:

- Escritorio.
- Tableta.
- Móvil.

Reglas:

- Evita anchos fijos que provoquen desbordamiento.
- Utiliza `Grid`, `Stack`, `Box` y breakpoints de MUI.
- No ocultes funcionalidades esenciales en móvil.
- Adapta tablas complejas a tarjetas, scroll horizontal controlado u otra solución coherente.
- Mantén botones táctiles con tamaño suficiente.
- Comprueba que los modales sean utilizables en pantallas pequeñas.
- Evita textos cortados sin alternativa.

La versión web debe quedar preparada para una futura adaptación móvil, pero no introduzcas React Native, Capacitor u otra tecnología sin petición expresa.

---

## 14. Accesibilidad

- Utiliza HTML semántico.
- Añade etiquetas a campos.
- Proporciona texto alternativo para imágenes informativas.
- No utilices únicamente el color para comunicar estados.
- Mantén navegación por teclado.
- No elimines el indicador de foco sin ofrecer una alternativa visible.
- Asocia mensajes de error con sus campos.
- Utiliza botones reales para acciones.
- Utiliza enlaces reales para navegación.
- Evita `div` con comportamiento de botón.
- Revisa el contraste.
- Añade atributos ARIA únicamente cuando el HTML semántico no sea suficiente.

---

## 15. Rutas y navegación

- Reutiliza la configuración central de React Router.
- No declares rutas dispersas en componentes.
- Mantén los nombres y rutas existentes.
- No cambies URLs públicas sin petición expresa.
- Utiliza navegación programática únicamente cuando sea necesario.
- Evita `window.location` para navegación interna.
- Conserva parámetros de consulta cuando formen parte del flujo.

Flujos existentes relevantes:

```text
/activar-cuenta?token=<TOKEN_ACTIVACION>
/partida
```

En activación de cuenta:

- El token debe leerse de la URL.
- No debe registrarse en consola.
- Deben gestionarse token válido, inválido, usado y expirado.
- Cuando el token haya expirado, el flujo debe permitir solicitar un nuevo enlace.
- La interfaz debe reflejar claramente el cambio funcional.
- Evita mantener el token en estado global.

---

## 16. Autenticación y seguridad

- No almacenes contraseñas.
- No registres tokens.
- No incluyas cabeceras de autorización en logs.
- Reutiliza el mecanismo de autenticación existente.
- No inventes un segundo sistema de sesión.
- No confíes en datos del usuario guardados únicamente en el cliente.
- No utilices `localStorage` para información sensible sin revisar el patrón existente.
- No desactives protecciones para resolver errores CORS.
- Los permisos del frontend solo mejoran la experiencia; la autorización real corresponde al backend.
- Ocultar un botón no sustituye una comprobación de permisos.
- Limpia el estado de usuario al cerrar sesión.
- Evita mostrar detalles técnicos de errores de autenticación.

Si el proyecto integra Keycloak en el futuro, adapta la solución al mecanismo oficial definido en el repositorio y no mezcles autenticación propia con Keycloak sin una estrategia explícita.

---

## 17. Gestión de errores y mensajes

Centraliza la interpretación de errores cuando sea posible.

Distingue entre:

- Error de validación.
- Recurso no encontrado.
- Conflicto.
- Sesión expirada.
- Acceso denegado.
- Error de red.
- Error interno del servidor.

Reglas:

- No muestres trazas.
- No muestres respuestas HTML del servidor.
- No expongas información interna.
- Utiliza `Snackbar`, `Alert`, mensajes de campo o componentes existentes.
- Evita mostrar el mismo error simultáneamente en varios lugares.
- Limpia los mensajes cuando el usuario reintente la operación.
- No uses `console.error` como única gestión del error.

---

## 18. Textos

- Mantén los textos visibles en castellano.
- Utiliza lenguaje claro y natural.
- Respeta la terminología del dominio cofrade.
- No sustituyas términos específicos por otros genéricos.
- Centraliza los textos cuando el módulo ya utilice un fichero como `featureTexts`.
- Evita textos largos directamente dentro del JSX.
- Mantén consistencia en mayúsculas, tildes y signos.
- No introduzcas un sistema completo de internacionalización sin petición expresa.

Terminología relevante:

- Hermandad.
- Cofradía.
- Hermano Mayor.
- Junta de Gobierno.
- Personaje.
- Colectivo.
- Arquetipo.
- Perfil.
- Sede canónica.
- Estación de penitencia.
- Paso procesional.
- Cortejo procesional.

---

## 19. Imágenes y recursos

- Reutiliza la infraestructura de assets existente.
- No incrustes imágenes grandes en base64 dentro del código.
- Utiliza nombres descriptivos.
- Añade texto alternativo.
- Mantén proporciones.
- Evita deformaciones.
- No añadas recursos sin licencia o procedencia clara.
- No dupliques el mismo archivo en varias carpetas.
- Optimiza el tamaño cuando sea necesario.
- Si un recurso debe tener fondo transparente, utiliza el formato adecuado.
- No hardcodees rutas que dependan del equipo del desarrollador.

---

## 20. Rendimiento

- Evita renderizados innecesarios, pero no optimices prematuramente.
- No cargues grandes módulos si no son necesarios para la ruta actual.
- Utiliza carga diferida cuando el proyecto ya contemple esa estrategia.
- Evita peticiones duplicadas.
- Cancela o ignora respuestas obsoletas cuando el componente se desmonte o cambie la búsqueda.
- No realices operaciones costosas directamente en el JSX.
- Pagina o virtualiza listados grandes cuando sea necesario.
- Optimiza imágenes pesadas.
- No añadas librerías grandes para resolver una necesidad pequeña.

---

## 21. Calidad del código

- Mantén el formato definido por el proyecto.
- Respeta ESLint y Prettier si están configurados.
- No desactives reglas globales para silenciar un único problema.
- No dejes imports sin utilizar.
- No dejes variables no utilizadas.
- No añadas `eslint-disable` sin justificarlo.
- Evita valores mágicos.
- Extrae constantes relevantes.
- Evita duplicación.
- Utiliza nombres que expresen intención.
- No dejes código comentado.
- No dejes `TODO` como sustitución de una implementación solicitada.
- No añadas mocks o placeholders permanentes sin petición expresa.

---

## 22. Documentación

Documenta:

- Componentes públicos o reutilizables.
- Hooks personalizados.
- Funciones con lógica no evidente.
- Contratos de datos complejos.
- Decisiones técnicas relevantes.

Utiliza JSDoc o TSDoc según el lenguaje del archivo.

No añadas comentarios que repitan literalmente el código.

Cuando una función reciba o devuelva estructuras complejas, documenta:

- Parámetros.
- Resultado.
- Posibles errores.
- Efectos secundarios.

---

## 23. Pruebas

Antes de crear pruebas, revisa las herramientas ya configuradas.

Pueden existir:

- Vitest.
- Jest.
- React Testing Library.
- Cypress.
- Playwright.

No añadas un segundo framework de pruebas sin petición expresa.

Las pruebas deben cubrir, según el cambio:

- Renderizado inicial.
- Estados de carga.
- Estados vacíos.
- Validaciones.
- Interacción del usuario.
- Respuestas correctas.
- Errores del backend.
- Navegación.
- Accesibilidad básica.
- Condiciones de autorización visibles.
- Flujos alternativos.

Principios:

- Prueba el comportamiento visible.
- Evita probar detalles internos.
- Usa selectores accesibles.
- Evita depender de textos no relevantes cuando exista un rol más estable.
- No hagas llamadas reales a servicios externos.
- Mantén mocks mínimos y comprensibles.
- No dependas del orden de ejecución.

---

## 24. Validación final

Antes de finalizar una tarea:

1. Revisa el diff completo.
2. Comprueba imports y variables sin utilizar.
3. Ejecuta el linter.
4. Ejecuta las pruebas afectadas.
5. Ejecuta la compilación de producción.
6. Revisa errores de consola.
7. Comprueba estados de carga, error y éxito.
8. Comprueba el comportamiento responsive.
9. Comprueba navegación por teclado.
10. Comprueba que no se hayan añadido secretos.
11. Comprueba que las URLs estén centralizadas.
12. Comprueba que los contratos REST coincidan con el backend.
13. Comprueba que no se hayan modificado archivos ajenos al alcance.
14. Revisa que no se hayan introducido regresiones visuales evidentes.

Comandos orientativos, adaptándolos a `package.json`:

```bash
npm run lint
npm run test
npm run build
```

No inventes comandos que no existan en el proyecto.

Si una comprobación no puede ejecutarse, indícalo expresamente.

---

## 25. Acciones prohibidas sin autorización expresa

Codex no debe:

- Actualizar React, Vite, MUI o dependencias principales.
- Cambiar JavaScript por TypeScript o viceversa.
- Añadir Redux, Zustand u otra librería de estado.
- Añadir un framework CSS alternativo.
- Crear archivos de estilos fuera de `styles/appStyles.js`.
- Sustituir MUI.
- Cambiar el tema general.
- Reescribir toda la arquitectura.
- Cambiar rutas públicas.
- Duplicar endpoints fuera de `mappedService`.
- Introducir URLs de producción fijas.
- Añadir credenciales o secretos.
- Desactivar ESLint o reglas globales.
- Modificar todos los módulos por coherencia si solo uno está afectado.
- Crear componentes o servicios duplicados.
- Realizar refactorizaciones masivas no solicitadas.
- Eliminar código sin comprobar su uso.
- Sobrescribir cambios locales del usuario.
- Afirmar que una prueba se ejecutó cuando no se ejecutó.

---

## 26. Forma de trabajar ante una petición

### Paso 1. Comprensión

- Identifica la feature afectada.
- Identifica el comportamiento actual.
- Identifica el comportamiento esperado.
- Determina rutas, servicios y componentes relacionados.

### Paso 2. Inspección

- Revisa implementaciones similares.
- Revisa `mappedService`.
- Revisa el tema.
- Revisa los estilos.
- Revisa textos y validaciones.
- Revisa pruebas existentes.
- Revisa cambios locales.

### Paso 3. Implementación

- Realiza cambios pequeños y coherentes.
- Reutiliza componentes.
- Centraliza llamadas REST.
- Mantén estados de carga, error y éxito.
- Añade documentación.
- Añade o actualiza pruebas.

### Paso 4. Validación

- Ejecuta lint.
- Ejecuta pruebas.
- Ejecuta build.
- Revisa consola.
- Revisa responsive.
- Revisa accesibilidad.
- Revisa el diff.

### Paso 5. Resumen

Al finalizar, indica:

1. Qué comportamiento se ha implementado.
2. Qué archivos se han creado.
3. Qué archivos se han modificado.
4. Qué rutas o endpoints se han utilizado.
5. Qué decisiones técnicas se han tomado.
6. Qué pruebas y comandos se han ejecutado.
7. El resultado de cada comprobación.
8. Cualquier limitación pendiente.

---

## 27. Criterios de finalización

Una tarea se considera terminada cuando:

- El comportamiento solicitado está implementado.
- La arquitectura existente se ha respetado.
- Las llamadas REST están centralizadas.
- Los estados de carga, error y éxito están gestionados.
- La interfaz es responsive.
- La navegación es accesible.
- Los textos son claros y coherentes.
- No hay código duplicado innecesario.
- No hay errores de lint.
- Las pruebas relevantes pasan.
- El build finaliza correctamente.
- No se han introducido secretos.
- Se proporciona un resumen fiel de las comprobaciones realizadas.
