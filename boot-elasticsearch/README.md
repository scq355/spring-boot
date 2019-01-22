# boot-elasticsearch
springboot 整合 Elasticsearch

```java
    docker run -e ES_JAVA_OPTS="-Xms256m -Xmx256m" -d -p 9200:9200 -p 9300:9300 --name ES01 5acf0e8da90b
```
springboot默认支持：

- Jest:默认不生效，需要额外的 io.searchbox.client.JestClient 客户端依赖支持

- SpringDataElasticsearch：

       - ElasticsearchAutoConfiguration：TransportClient节点信息，clusterNodes，clusterName

       - ElasticsearchDataAutoConfiguration：ElasticsearchTemplate
       
       - ElasticsearchRepositoriesAutoConfiguration：ElasticsearchRepository
