**Order Processing API**

###This API exposes the following endpoints

### Order API

|   HTTP Verb   |      URL                                                       |   Description                                                                 |
| ------------- | ---------------------------------------------------------------|-------------------------------------------------------------------------------|
|     `GET`     |`http://localhost:8082/orders/order/status/{orderRequestId}`| Obtains order status corresponding to the provided order request ID.          |
|    `POST`     |        `http://localhost:8082/orders/publish/order`            | Publishes the order to kafka and stores the order details in Redis DB.       |  

### JWT TOKEN API to secure Order API
|   HTTP Verb   |      URL                                                       |   Description                                                                 |
| ------------- | ---------------------------------------------------------------|-------------------------------------------------------------------------------|
|    `POST`     |        `http://localhost:8082/user?user=XXXXX&password=YYYY`            | Obtains JWT token for given username and password      |  


## static assets
* json file
    * It contains sample order request body.
    * order-processor-api/src/main/resources/request.json

## Configuration
* Spring boot application port : 8082
* kafka Credentials
    * bootstrap-servers
    * default-topic
    * consumer-group-id
* Redis Credentials
    * host
    * port
    * db key

## Deployment Instructions 

* **Without Docker-Compose**
    * Zookeeper setup on-prem
    * Default Zookeeper port in zoo.cfg file (Default port 2181).
    * Run Zookeeper by opening a new cmd and type zkserver, Zookeeper is up and running on port 2181!
    * kafka setup on-prem
        *  If your Zookeeper is running on some other machine or cluster you can edit “zookeeper.connect:2181” to your custom IP and port. For this demo we are using same machine so no need to change.
           Also Kafka port & broker.id are configurable in this file. Leave other settings as it is.
           Your Kafka will run on default port 9092 & connect to zookeeper’s default port which is 2181.
        * Go to your Kafka installation directory C:\kafka_2.11-2.0.0
        * Now type .\bin\windows\kafka-server-start.bat .\config\server.properties and press Enter , everything wnet fine, kafka is up and running.
        * creating topics
            * kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
    * Redis Setup on-prem
        * Run redis-server
    * Run the gradle build, jar will be generated under build/lib/order-processor-api-0.0.1-SNAPSHOT.jar  or run application as sprintboot
    * hit /user?user=XXXXX&password=YYYY api to get the jwt token  
    * hit /publish/order with request body and header authorization with above token, message will be published to kafka and using /order/status/{orderRequestId} api with authorization header to get the status of that order
    
    
    
* **With Docker-Compose**
    * Run the gradle build, jar will be generated under build/lib/order-processor-api-0.0.1-SNAPSHOT.jar
    * Docker file
        * Entrypoint to run spring boot application jar
    * Docker-Compose
        * docker-compose up --build will generate 4 containers for Web(order process api), kafka, zookeeper and redis
    * After running above command, all clusters will be up and running,  hit /user?user=XXXXX&password=YYYY api to get the jwt token
    * hit /publish/order with request body and header authorization with above token, message will be published to kafka and using /order/status/{orderRequestId} api with authorization header to get the status of that order

|   Application  |      Port                                                       |   Description                                                                 |
| ------------- | ---------------------------------------------------------------|-------------------------------------------------------------------------------|
|    `web`     |        `8082`            | Order Processing API     |
|    `Kafka`     |        `9092`            | Kafka    |
|    `Zookeeper`     |        `2181`            | Zookeeper     |
|    `Redis`     |        `6379`            | Redis     |

