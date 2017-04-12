docker-compose down
docker rmi -f ricardojob/db-produto
docker rmi -f ricardojob/produto
docker volume remove produto_postgres-volume-db
