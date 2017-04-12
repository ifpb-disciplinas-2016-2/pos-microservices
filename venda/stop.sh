docker-compose down
docker rmi -f ricardojob/db-venda
docker rmi -f ricardojob/venda
docker volume remove postgres-volume-venda
