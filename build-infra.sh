helm install polardb-catalog --set postgresqlPassword=admin --set postgresqlUsername=admin,postgresqlDatabase=polardb_catalog bitnami/postgresql
helm install polardb-order --set postgresqlPassword=admin --set postgresqlUsername=admin --set  postgresqlDatabase=polardb_order bitnami/postgresql
helm install rabbitmq --set auth.username=user --set auth.password=user bitnami/rabbitmq