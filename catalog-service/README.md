# Aplicação utilizando spring boot, seguindo os 12 fatores cloud native
- Explicação de cada fator: https://cloud.google.com/solutions/twelve-factor-app-development-on-gcp?hl=pt-br
- Abaixo demais repositórios que faz parte da malha de serviços:
```
https://github.com/fabriciolfj/dispacher-service
https://github.com/fabriciolfj/order-service
https://github.com/fabriciolfj/spring-gateway
```

### Kubernetes
- microk8s
- helm
```
Postgress
helm repo add bitnami https://charts.bitnami.com/bitnami

helm install polardb-catalog bitnami/postgresql --set postgresqlUsername=admin --set postgresqlPassword=admin --set postgresqlDatabase=polardb_catalog --set image.tag=13 --set service.port=5432
helm install polardb-order-postgresql bitnami/postgresql --set postgresqlUsername=admin --set postgresqlPassword=admin --set postgresqlDatabase=postgres --set image.tag=13 --set service.port=5432

helm uninstall polardb-catalog

Rabbitmq

helm repo add bitnami https://charts.bitnami.com/bitnami

helm install polarmq-broker bitnami/rabbitmq --set image.tag=3.8 --set auth.username=user --set auth.password=password

```
- skaffold: é uma ferramenta desenvolvida pelo google que "lida com o fluxo de trabalho para construir, enviar e implantar seu aplicativo, permitindo que você se concentre mais no código".
- Etapas que skaffold automatiza:
  - package do aplicativo spring boot, como uma imagem docker
  - upload da imagem para o cluster kubernetes
  - criar implantação de um banco de dados, no nosso caso o postgres, a partir de um chart helm.
  - criar todos os manifestos do kubernetes
  - expõe porta de aplicativos dentro do kubernetes, para acesso externo.
  - coleta de logs.

### Skaffold
- Iniciando (execute na raiz do projeto):
```
skaffold init --XXenableBuildpacksInit
```
- Gerará o arquivo skaffold.yml
- Podemos personaliza-lo.
- Por fim execute o comando abaixo (--port-forward vai redirecionar a porta para sua maquina local, e está será printada no console):
```
skaffold dev --port-forward
```
- Para debug, utiliza-se o comando abaixo (coloque na sua idea a porta exposta, costuma-se ser localhost:5005)
```
skaffold debug --port--forward
```

### Octant
- Para visualizar os manifestos em kubernetes, em uma dash web.

## Spring cloud function
- Basea-se a comunicação em eventos, orientada a funções: supplier(fornecedor), function(processamento) e consumer(consumidor)
- Pode ser exposta via rest ou combinada com um middleware (rabgitmq), para integrar com eventos.
- Exemplo de expor as funções via rest:
```
spring.cloud.function.definition: rest
```
- obs: spring  cloud function é serverless, ou seja, não utiliza servidor, com isso podemos implementar no aws lambda por exemplo.

#### Supplier
- é uma função apenas de saída, sem entrada, conhecida também como produtor, editor ou fonte

### Function
- possui entrada e saída, também conhecida como processador.

### Consumer
- função com entrada, mas sem saída, também conhecida como assinante ou sink.

## Spring cloud stream
- é uma estrutura para criar aplicativos escal-avies, orientados a eventos e streaming.
- binders: são canais canais de comunicação, ou seja, que se comunicam com o sistema de mensagens externo.
- exchanges: utiliza-se as rotas para entregar as mensagens a fila de destino
- bindings: representa as rotas (que são vinculadas a exchanges)
- convenção de configuração:

```
<functionName> + - in  - + <index>
<functionName> + - out - + <index>
