# Open Host Service | DDD Strategic Pattern Example

[![Pattern](https://img.youtube.com/vi/yZfnzOBJeHA/0.jpg)](https://www.youtube.com/watch?v=yZfnzOBJeHA)

## Que es un Open Host Service

Open Host Service (OHS) es un patron estrategico de Domain-Driven Design que define un protocolo bien documentado y estable para que otros Bounded Contexts consuman las capacidades de tu dominio sin acoplarse a su modelo interno.

En terminos de Eric Evans (Blue Book, Chapter 14):

> Define un protocolo que da acceso a tu subsistema como un conjunto de servicios. Abre el protocolo para que todos los que necesiten integrarse contigo puedan usarlo.

La idea central es que cuando un Bounded Context tiene multiples consumidores, en lugar de crear traducciones ad-hoc para cada uno (lo cual genera N capas de traduccion y N puntos de mantenimiento), se publica una API unificada que actua como contrato publico. Este contrato es la "puerta de entrada" al dominio.

### Donde encaja en el Context Map

En un Context Map, OHS aparece del lado **upstream** (proveedor). Es la estrategia que un equipo elige cuando su Bounded Context sera consumido por multiples downstream y necesita ofrecer estabilidad sin frenar su evolucion interna.

Se suele combinar con **Published Language** (PL): OHS define el mecanismo de exposicion (la API, el servicio), mientras que PL define el formato compartido (los DTOs, el schema, el contrato). Juntos forman el patron clasico **OHS/PL**.

```
+------------------+         +-------------------+
|  Downstream A    |         |  Downstream B     |
|  (Consumer)      |         |  (Consumer)       |
+--------+---------+         +---------+---------+
         |                             |
         +----------+    +------------+
                    |    |
              +-----v----v------+
              |  Open Host      |
              |  Service (API)  |  <-- Protocolo publico y estable
              +---------+-------+
                        |
              +---------v-------+
              |  Published      |
              |  Language (DTOs)|  <-- Lenguaje compartido (contrato)
              +---------+-------+
                        |
              +---------v-------+
              |  Domain Model   |  <-- Modelo interno protegido
              +-----------------+
```

### Diferencia con otros patrones de integracion

| Patron | Relacion | Cuando usarlo |
|---|---|---|
| **Shared Kernel** | Simetrica | Dos equipos co-mantienen un subconjunto del modelo. Alto acoplamiento, requiere coordinacion constante. |
| **Customer/Supplier** | Asimetrica | El upstream planifica considerando las necesidades del downstream. Relacion 1:1 o con pocos consumidores conocidos. |
| **Conformist** | Asimetrica | El downstream adopta el modelo del upstream tal cual. Sin capa de traduccion. |
| **Anti-Corruption Layer** | Asimetrica | El downstream se protege del modelo upstream con una capa de traduccion propia. |
| **Open Host Service** | Asimetrica | El upstream publica un protocolo estandarizado para multiples consumidores. El upstream controla la estabilidad del contrato. |

La diferencia clave: OHS es la decision del **upstream** de formalizar su contrato. ACL es la decision del **downstream** de protegerse. Ambos pueden coexistir: un upstream expone un OHS y un downstream igualmente coloca un ACL si necesita transformar el Published Language a su propio Ubiquitous Language.

### Por que importa a nivel de arquitectura

1. **Desacoplamiento dirigido**: el modelo de dominio evoluciona libremente. La capa OHS absorbe el costo de traduccion en un unico punto, no en cada consumidor.
2. **Contrato versionable**: al ser explicito, se puede versionar (`/v1/`, `/v2/`), deprecar y evolucionar sin romper consumidores existentes.
3. **Ownership claro**: el equipo upstream es dueno del contrato. No hay negociaciones N:1 con cada consumidor.
4. **Escalabilidad organizacional**: nuevos equipos downstream se integran leyendo la documentacion del OHS, sin necesidad de coordinacion directa.

### Cuando NO usar Open Host Service

- Si solo tenes un consumidor, Customer/Supplier es mas simple y directo.
- Si los consumidores necesitan representaciones radicalmente distintas del mismo dato, un unico OHS puede no ser suficiente (considerar multiples OHS o BFF).
- Si no tenes ownership sobre el upstream y no podes formalizar el contrato.

## Estructura del proyecto

Este proyecto implementa el patron OHS en el dominio de autorizaciones de pago:

```
src/main/java/
|
+-- domain/
|   +-- model/
|   |   +-- Authorization.java          # Agregado raiz
|   |   +-- AuthorizationId.java        # Value Object (identidad)
|   |   +-- AuthorizationStatus.java    # Value Object (estado)
|   |   +-- Money.java                  # Value Object (monto + moneda)
|   +-- port/
|       +-- AuthorizationOutputPort.java # Puerto de salida (repositorio)
|
+-- application/
|   +-- usecase/
|       +-- CreateAuthorizationUseCase.java
|       +-- GetAuthorizationUseCase.java
|
+-- openhost/                            # <-- Capa OHS
|   +-- api/
|   |   +-- AuthorizationController.java # Punto de entrada (protocolo)
|   +-- dto/
|   |   +-- AuthorizationResponseDTO.java # Published Language
|   +-- mapper/
|       +-- AuthorizationMapper.java     # Traduccion Domain -> PL
|
+-- infrastructure/
    +-- persistence/
        +-- InMemoryAutorization.java    # Adaptador del puerto de salida
```

### Mapeo al patron

| Concepto DDD | Implementacion |
|---|---|
| **Open Host Service** | `AuthorizationController` - expone el protocolo REST versionado (`/v1/authorizations`) |
| **Published Language** | `AuthorizationResponseDTO` - estructura publica estable, desacoplada del modelo de dominio |
| **Traduccion OHS -> PL** | `AuthorizationMapper` - convierte el agregado `Authorization` al DTO publico |
| **Modelo de Dominio** | `Authorization`, `Money`, `AuthorizationStatus` - protegidos de los consumidores |
| **Puerto de salida** | `AuthorizationOutputPort` - abstraccion que desacopla el dominio de la persistencia |

El punto critico esta en el paquete `openhost/`: esta capa **no** expone el modelo de dominio directamente. El `AuthorizationMapper` traduce del agregado `Authorization` al `AuthorizationResponseDTO`, asegurando que cambios internos en el dominio (por ejemplo, renombrar campos, agregar logica al `Money`, cambiar la estructura del `AuthorizationId`) no rompan a ningun consumidor.

Esto es exactamente lo que OHS resuelve: **un unico punto de traduccion controlado por el upstream, en lugar de N traducciones ad-hoc en cada downstream.**
