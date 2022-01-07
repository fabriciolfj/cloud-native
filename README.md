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
```

# Spring gateway
- Projeto spring, como o próprio nome salienta, cujo objetivo é ser um gateway para suas aplicações.
- Além de fazer parte dos patterns de microservices, a adoção de um gateway traz inúmeros benefícios, entre eles:
  - fornece um ponto de entrada para o sistema
  - efetua o encaminhamento das chamadas para o serviço responsável, atravez das rotas
  - podemos centralizar nele algumas preocupações transversais, como segurança, monitoramento e resiliência.

## Rotas
- É identificada por um ID único, uma coleção de predicados dedicindo se devem seguir a rota, um URI para encaminhamento da solicitação se os predicados permitirem, e uma coleção de filtros aplicados antes ou depois de encaminhar a solicitação ao downstream.

## Predicates
- Corresponde a qualquer coisa da solicitação HTTP, incluindo path, host, headers, parâmetros de consulta, cookies e body.

## Filter
- Modifica a solicitação ou resposta HTTP antes ou depois de encaminhar a mesma para serviço downstream.

### Retry filter
- Podemos configurar o gateway para efetuar uma retentativa, diante falha, ao serviço downstream. Para isso:
  - configurar o tipo de erro/exceção que ocorrerá a retentativa.
  - o time entre as retentativas

## Circuit breaker
- Uma forma de mantermos nossos serviços resilientes, ou seja, diante a falhas mante-lo em funcionamento
- Este recurso vem incluso no spring cloud gateway, chamado circuit breaker.
- Tal conceito vem de componentes eletrícos, e seu uso é bem similar.
- Temos 3 estados: circuito aberto, semi aberto e fechado. Exemplo:
  - Quando um serviço está apresentando falha, o circuito é aberto e executa um outro recurso (fallback).
  - Enquanto o circuito estiver aberto, o recurso original não será chamado.
  - Tempos em tempos, este verifica se o serviço original está operante, caso positivo, o circuito é fechado, caso negativo, circuito manten-se aberto.
- Precisa de um bean Customizer dentro do gateway, para o circuitbreaker funcionar, alem das configurações no application.yml

```
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
        .circuitBreakerConfig(CircuitBreakerConfig
                .custom()
                .slidingWindowSize(20) //numero de janelas para registar as chamdas no estado fechado
                .permittedNumberOfCallsInHalfOpenState(5) //quantidade e chamadas no estado semi aberto
                .failureRateThreshold(50) //percentual de falhas sobre as janelas, para abrir o circuito
                .waitDurationInOpenState(Duration.ofSeconds(6)) //tempo de espera antes de passar para o semiaberto
                .build()
        ).timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(TIMEOUT)
                        .build())
        .build());
    }
```    
- Obs: o tempo limite configurado no customizer, terá precedência sobre o tempo limite de resposta definido netty timeout application.yml

### Combinando vários padrões de resiliência
- Quando se combina vários padrões de resiliência, a sequência fica o seguinte:
  - 1 TimeLimiter: tempo limite de resposta ao cliente.
  - 2 Filtro circuitBreaker
  - 3 Retry (retentativas)

### Rate limiting
- um padrão usado para controlar a taxa de tráfego enviada ou recebida.

### Client-side
- limitadores de taxa do lado do clinte, para restringir o número de solicitações enviadas a um serviço em um determinado periodo.

### Server-side
- limitadores de taxa do lado do servidor, são para restringir o número de solicitações recebidas por um serviço (ou cliente), em um determinado período.
- padrão indicado para gateway, afim de evitar ataques ddos

## Projeto
- Para testes, utilizei a ferramenta https://httpd.apache.org/docs/2.4/programs/ab.html ou utilize o choco para facilitar choco install apache-httpd

## Implantação kubernetes
- Instale o redis pelo helm
```
helm repo add bitnami https://charts.bitnami.com/bitnami
```
```
helm install polardb-redis bitnami/redis --set cluster.enabled=false --set password=admin
```

## Controle de requisição
- Dentro do spring gateway, existe o recurso para controlar o número de requisições, seja por usuário ou sessão.
- Para este projeto utilizei um cache distribuído (redis) para tal controle.
- Para adicionar dentro do cluster kubernetes, usei o helm:

```
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install polardb-redis bitnami/redis --set cluster.enabled=false --set image.tag=6 --set password=admin
```

### RequestRateLimiter
- A implementação do requestRateLimiter no redis, é baseada no algoritimo bucket de token, ou seja, o usuário recebe um bucket de token (cada bucket tem uma capacidade máxima). Cada solicitação que o usuário efetuar, um token é retirado do bucket, caso não tenha mais, as requisições serão negadas até que o tempo limite mude e receba um novo bucket.

### Session data redis
- Para um gerenciador de sessões, podemos fazer uso do spring session project e um cache distribuído como redis.

### Segurança
- Neste projeto utilizamos o keycloak, que é responsável pela autenticidade, emissão do token, em nome do usuario final, vinculado a um cliente (app)
- Cada usuario tem uma função, e esta é utilizada na etapa de autorização (RBAC - controle de acesso baseado em função)

#### Token relay
- Padrão onde um token e repassado a serviços downstream, por exemplo:
  - gateway recebe um token válido e repassa o mesmo ao serviço alvo.
    - obs: no gateway lê o token id apenas, os aplicativos downstream lê o token de acesso. 

#### Tipos de token
- token id - representa um evento de autenticação, e inclui informações sobre o usuário
- token de acesso - representa a autorização concedida ao cliente oauth2, para acessar os dados .

#### Formato de tokens
- token opaco: não é enriquecido de informações, e toda vez o servidor de recurso, precisa ir ao keycloak (no nosso caso), para buscar informações do token
- token jwt: é um token enriquecido, onde possui informações relevantes.

#### Processo de validade do token
- Ao receber o token, o servidor de recurso valida-o no emissor, utilizando uma chave publica provida pelo mesmo
- a chave publica é em formato jwk
- no caso do spring security temos esse exemplo de configuração: 
```
  spring.security:
    oauth2:
      resourceserver:
        jwt:  
          jwk-set-uri: ${KEYCLOAK_URL:http://localhost:8080}/auth/realms/PolarBookshop/protocol/openid-connect/certs
```

#### Segurança por meio de roles
- Spring security associa cada usuário autenticado a uma lista de objetos grantedAuthority (autoridades concedidas ao usuário).
- essas autoridades concedidas pode sem permissões, funções ou mesmo escopos e vêm de fontes diferentes.
- esses objetos ficam armazenados no outro objeto chamado Authentication e  este fica no SecurityContext.

#### Kustomize
- Concorrente do helm, ele gerencia, processa e gera manifestos do kubernetes
- precisa de um arquivo de configuração chamado kustomization.yml
- Para aplicar, muda um pouco o apply, fica na seguinte forma:
```
kubectl apply -k k8s
```

#### Observabilidade com grafana/loki/fluentbit
- loki: armazena os logs
- fluent bit: coleta
- grafana: visualização do log

#### Rastreamento distribuído
- cada transação e identificada por um id de rastreamento
- cada etapa do processamento da solicitação é chamada de span
- para envio do rastreamento podemos utilizar o MDC nativo do java ou spring sleuth, que abstrai tal recurso.
