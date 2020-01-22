peticion properties resolved JSON

	curl -X GET -H "Content-Type: application/json" http://localhost:9000/api


actualizacion properties

	curl -X POST -H "Content-Type: application/json" -d '{"properties":[{"key":"ejemplo.path.1","value":"[12,21,21,52]"},{"key":"ejemplo.path.2","value":"texto de prueba 2"}]}' http://localhost:9000/api/register	