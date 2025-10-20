# TP3 – API Estudiantes / Carreras

Este repositorio incluye los endpoints REST solicitados y una colección de Postman para facilitar las pruebas.

La colección es el archivo TP3.postman_collection.json que se encuentra dentro de la carpeta TP3.

La variable `{{baseUrl}}` corresponde a la URL base de la aplicación. Si se ejecuta localmente, esta será: `http://localhost:8080`

---

## Endpoints

## a) Alta de estudiante

**POST** `{{baseUrl}}/estudiantes`

**Body (JSON):**  
(Debe coincidir con el modelo `Estudiante.java`)
```json
{
  "numeroDocumento": 99999999,
  "nombre": "Ana",
  "apellido": "Gomez",
  "edad": 25,
  "genero": "Femenino",
  "ciudadResidencia": "Rauch",
  "libretaUniversitaria": 123456
}

```

## b) Matricular estudiante en una carrera
**POST** `{{baseUrl}}/estudiante-carrera/matricular`

**Uso (Params):** Este endpoint usa @RequestParam (parámetros en la URL), no un Body JSON

**Ejemplo de URL completa:**  
{{baseUrl}}/estudiante-carrera/matricular?libretaUniversitaria=123456&idCarrera=1&inscripcion=2024&graduacion=0&antiguedad=1


## c) Recuperar todos los estudiantes (orden simple)
**GET** `{{baseUrl}}/estudiantes`

Nota: El ordenamiento (apellido, asc) ya está implementado por defecto en el repositorio (findAllByOrderByApellidoAsc).

## d) Recuperar un estudiante por libreta universitaria
**GET** `{{baseUrl}}/estudiantes/libreta/{{libreta}}`

Ejemplo: {{baseUrl}}/estudiantes/libreta/34978

## e) Recuperar estudiantes por género
**GET** `{{baseUrl}}/estudiantes/genero/{{genero}}`

Ejemplo: {{baseUrl}}/estudiantes/genero/Male

## f) Carreras con estudiantes inscriptos (orden por cantidad)
**GET** `{{baseUrl}}/estudiante-carrera/carreras-con-inscriptos`

Nota: El ordenamiento (descendente por cantidad de inscriptos) ya está implementado por defecto en el repositorio (ORDER BY COUNT(ec) DESC).

## g) Estudiantes de una carrera, filtrado por ciudad
**GET** `{{baseUrl}}/estudiante-carrera/estudiantes`

Uso (Params): Se deben pasar idCarrera y ciudad como parámetros de consulta.
Ejemplo de URL completa: {{baseUrl}}/estudiante-carrera/estudiantes?idCarrera=1&ciudad=Rauch

## h) Reporte de carreras (inscriptos/egresados por año)
**GET** `{{baseUrl}}/estudiante-carrera/reporte-carreras`

Nota: El ordenamiento (alfabético por carrera, cronológico por año) ya está implementado por defecto en el repositorio (ORDER BY c.carrera ASC, ec.inscripcion ASC).
