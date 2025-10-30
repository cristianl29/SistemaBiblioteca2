# Sistema Biblioteca - segunda entrega de Cristian Monsalve Lopera

Proyecto (paquete con JWT, validaciones, pruebas, Docker, Postman examples).


## Ejecutar local (sin Docker)
1. Tener Java 17+ y Maven.
2. Ejecutar:
   ```bash
   mvn spring-boot:run
   ```
3. Swagger UI: http://localhost:8080/swagger-ui/index.html
4. H2 console: http://localhost:8080/h2-console (jdbc url: jdbc:h2:mem:biblioteca, user sa)

## Docker
Construir y ejecutar con Docker Compose:
```bash
docker-compose build
docker-compose up
```
API en http://localhost:8080

## Tests
Ejecutar:
```bash
mvn test
```

## Postman y curl
Importa `Biblioteca_API.postman_collection.json` en Postman o usa `curl_examples.txt`.
