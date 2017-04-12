docker-compose down
docker rmi -f ricardojob/db-cliente
docker rmi -f ricardojob/cliente
docker volume remove postgres-volume-cliente
