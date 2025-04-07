# Kafka Absolute Mastery

<div align="center">
  <img src="/api/placeholder/800/200" alt="Kafka Mastery Banner" />

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)
[![GitHub Stars](https://img.shields.io/github/stars/yourusername/kafka-absolute-mastery?style=social)](https://github.com/yourusername/kafka-absolute-mastery)
</div>

## 🚀 Introduction

Welcome to **Kafka Absolute Mastery** - your comprehensive guide to mastering Apache Kafka from fundamentals to advanced implementation patterns. This repository provides practical examples, architectural patterns, and best practices for building robust, scalable, and high-performance event streaming applications.

## 📋 Table of Contents

- [Core Concepts](#core-concepts)
- [Architecture](#architecture)
- [Installation](#installation)
- [Quickstart](#quickstart)
- [Real-World Examples](#real-world-examples)
- [Performance Tuning](#performance-tuning)
- [Security](#security)
- [Advanced Patterns](#advanced-patterns)
- [Monitoring](#monitoring)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## 🧠 Core Concepts

Apache Kafka is a distributed event streaming platform capable of handling trillions of events a day. Here are the foundational concepts:

### Event Streaming Architecture

```mermaid
graph LR
    A[Producers] --> B[Kafka Brokers]
    B --> C[Consumers]
    D[ZooKeeper/KRaft] --- B
    style A fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style B fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style C fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style D fill:#FFADAD,stroke:#FF5A5F,stroke-width:2px
```

### Key Components

- **Topics**: Named feeds of records
- **Partitions**: Units of parallelism within topics
- **Producers**: Applications that publish records to topics
- **Consumers**: Applications that subscribe to topics and process records
- **Consumer Groups**: Groups of consumers that collectively consume all records from a topic
- **Brokers**: Kafka servers that store and serve records
- **ZooKeeper/KRaft**: Coordination service for managing Kafka cluster

## 🏗️ Architecture

### Kafka Cluster Architecture

```mermaid
graph TB
    subgraph "Kafka Cluster"
        B1[Broker 1] --- B2[Broker 2]
        B2 --- B3[Broker 3]
        B3 --- B1
    end
    
    subgraph "Producer Applications"
        P1[Producer 1]
        P2[Producer 2]
    end
    
    subgraph "Consumer Applications"
        C1[Consumer Group 1]
        C2[Consumer Group 2]
    end
    
    P1 --> B1
    P2 --> B2
    B1 --> C1
    B3 --> C2
    
    style B1 fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style B2 fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style B3 fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style P1 fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style P2 fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style C1 fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style C2 fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
```

### Topic and Partition Structure

```mermaid
graph LR
    subgraph "Topic A"
        P0[Partition 0]
        P1[Partition 1]
        P2[Partition 2]
    end
    
    P0 --- R0[Replica 0]
    P0 --- R1[Replica 1]
    P1 --- R2[Replica 0]
    P1 --- R3[Replica 1]
    P2 --- R4[Replica 0]
    P2 --- R5[Replica 1]
    
    style P0 fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style P1 fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style P2 fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style R0 fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style R1 fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style R2 fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style R3 fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style R4 fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style R5 fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
```

## 🛠️ Installation

### Docker Compose

Get started quickly with Docker Compose:

```yaml
version: '3'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - "2181:2181"
  
  kafka:
    image: confluentinc/cp-kafka:latest
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
```

### Kubernetes

For production deployments, we recommend using Kubernetes with Strimzi:

```yaml
apiVersion: kafka.strimzi.io/v1beta2
kind: Kafka
metadata:
  name: kafka-cluster
spec:
  kafka:
    version: 3.4.0
    replicas: 3
    listeners:
      - name: plain
        port: 9092
        type: internal
        tls: false
      - name: tls
        port: 9093
        type: internal
        tls: true
    config:
      offsets.topic.replication.factor: 3
      transaction.state.log.replication.factor: 3
      transaction.state.log.min.isr: 2
      default.replication.factor: 3
      min.insync.replicas: 2
    storage:
      type: jbod
      volumes:
      - id: 0
        type: persistent-claim
        size: 100Gi
        deleteClaim: false
  zookeeper:
    replicas: 3
    storage:
      type: persistent-claim
      size: 20Gi
      deleteClaim: false
  entityOperator:
    topicOperator: {}
    userOperator: {}
```

## 🚦 Quickstart

### Create a Topic

```bash
# Create a topic with 3 partitions and replication factor of 3
kafka-topics.sh --bootstrap-server localhost:9092 \
                --create \
                --topic sample-topic \
                --partitions 3 \
                --replication-factor 3
```

### Produce Messages

```bash
# Produce messages to the topic
kafka-console-producer.sh --bootstrap-server localhost:9092 \
                         --topic sample-topic
```

### Consume Messages

```bash
# Consume messages from the topic
kafka-console-consumer.sh --bootstrap-server localhost:9092 \
                         --topic sample-topic \
                         --from-beginning
```

## 💻 Real-World Examples

### Event-Driven Microservices

```mermaid
flowchart TB
    subgraph "User Service"
        US[User Service]
    end
    
    subgraph "Order Service"
        OS[Order Service]
    end
    
    subgraph "Payment Service"
        PS[Payment Service]
    end
    
    subgraph "Notification Service"
        NS[Notification Service]
    end
    
    subgraph "Kafka Cluster"
        K[Kafka]
    end
    
    US -- User Created --> K
    OS -- Order Placed --> K
    K -- User Created --> OS
    K -- Order Placed --> PS
    PS -- Payment Processed --> K
    K -- Payment Processed --> NS
    K -- Order Placed --> NS
    
    style US fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style OS fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style PS fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style NS fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style K fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
```

### Stream Processing with Kafka Streams

```java
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

StreamsBuilder builder = new StreamsBuilder();

// Create a stream from the input topic
KStream<String, String> inputStream = builder.stream("input-topic");

// Process the stream
KStream<String, String> processedStream = inputStream
    .filter((key, value) -> value != null && value.length() > 0)
    .mapValues(value -> value.toUpperCase());

// Write the processed stream to an output topic
processedStream.to("output-topic");
```

### Real-time Analytics Dashboard

```mermaid
flowchart LR
    subgraph "Data Sources"
        WS[Web Services]
        MC[Mobile Clients]
        IoT[IoT Devices]
    end
    
    subgraph "Ingestion"
        K[Kafka]
    end
    
    subgraph "Processing"
        KS[Kafka Streams]
        SP[Spark Streaming]
    end
    
    subgraph "Storage"
        ES[Elasticsearch]
        TS[TimescaleDB]
    end
    
    subgraph "Visualization"
        G[Grafana]
    end
    
    WS --> K
    MC --> K
    IoT --> K
    K --> KS
    K --> SP
    KS --> ES
    SP --> TS
    ES --> G
    TS --> G
    
    style WS fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style MC fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style IoT fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style K fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style KS fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style SP fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style ES fill:#FFADAD,stroke:#FF5A5F,stroke-width:2px
    style TS fill:#FFADAD,stroke:#FF5A5F,stroke-width:2px
    style G fill:#FFC6FF,stroke:#9F5F80,stroke-width:2px
```

## ⚙️ Performance Tuning

### Producer Optimizations

| Configuration | Description | Recommendation |
|---------------|-------------|----------------|
| `batch.size` | Maximum batch size in bytes | 16384 - 131072 |
| `linger.ms` | Time to wait before sending messages | 5-100 ms |
| `compression.type` | Message compression algorithm | `lz4` or `zstd` |
| `acks` | Acknowledgment level | `all` for durability, `1` for performance |

### Consumer Optimizations

| Configuration | Description | Recommendation |
|---------------|-------------|----------------|
| `fetch.min.bytes` | Minimum data to fetch | 1024 - 65536 |
| `fetch.max.wait.ms` | Maximum fetch wait time | 100 - 500 ms |
| `max.poll.records` | Maximum records per poll | 300 - 500 |
| `auto.commit.interval.ms` | Commit interval | 1000 - 5000 ms |

### Broker Optimizations

| Configuration | Description | Recommendation |
|---------------|-------------|----------------|
| `num.network.threads` | Number of network threads | 3 * (CPU cores) |
| `num.io.threads` | Number of I/O threads | 8 * (CPU cores) |
| `socket.send.buffer.bytes` | Socket send buffer | 1048576 (1MB) |
| `socket.receive.buffer.bytes` | Socket receive buffer | 1048576 (1MB) |

## 🔒 Security

### Authentication Methods

```mermaid
graph TD
    A[Security] --> B[Authentication]
    A --> C[Authorization]
    A --> D[Encryption]
    
    B --> B1[SASL/PLAIN]
    B --> B2[SASL/SCRAM]
    B --> B3[SASL/GSSAPI]
    B --> B4[SSL/TLS]
    B --> B5[OAuth]
    
    C --> C1[ACLs]
    C --> C2[Role-Based Access]
    
    D --> D1[SSL/TLS]
    D --> D2[Encryption at Rest]
    
    style A fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style B fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style C fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style D fill:#FFADAD,stroke:#FF5A5F,stroke-width:2px
```

### SSL/TLS Configuration

```properties
# Broker configuration
listeners=PLAINTEXT://localhost:9092,SSL://localhost:9093
ssl.keystore.location=/path/to/kafka.server.keystore.jks
ssl.keystore.password=keystore-password
ssl.key.password=key-password
ssl.truststore.location=/path/to/kafka.server.truststore.jks
ssl.truststore.password=truststore-password
ssl.client.auth=required

# Client configuration
security.protocol=SSL
ssl.truststore.location=/path/to/client.truststore.jks
ssl.truststore.password=truststore-password
ssl.keystore.location=/path/to/client.keystore.jks
ssl.keystore.password=keystore-password
ssl.key.password=key-password
```

## 🧩 Advanced Patterns

### Event Sourcing

```mermaid
sequenceDiagram
    participant User
    participant Service
    participant CommandHandler
    participant EventStore
    participant Projector
    participant ReadModel
    
    User->>Service: Submit Command
    Service->>CommandHandler: Process Command
    CommandHandler->>EventStore: Store Event
    EventStore->>Kafka: Publish Event
    Kafka->>Projector: Consume Event
    Projector->>ReadModel: Update ReadModel
    ReadModel->>User: Query Result
```

### CQRS (Command Query Responsibility Segregation)

```mermaid
graph TD
    subgraph "Command Side"
        C[Command API]
        CH[Command Handler]
        WD[Write Database]
    end
    
    subgraph "Event Bus"
        K[Kafka]
    end
    
    subgraph "Query Side"
        Q[Query API]
        QH[Query Handler]
        RD[Read Database]
    end
    
    C --> CH
    CH --> WD
    CH --> K
    K --> QH
    QH --> RD
    Q --> QH
    
    style C fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style CH fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style WD fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style K fill:#FFADAD,stroke:#FF5A5F,stroke-width:2px
    style Q fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style QH fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style RD fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
```

### Transactional Outbox Pattern

```mermaid
graph TD
    subgraph "Application"
        APP[Application Service]
        DB[(Database)]
        OB[Outbox Table]
    end
    
    subgraph "Outbox Relay"
        OR[Outbox Relay]
    end
    
    subgraph "Kafka"
        K[Kafka Topic]
    end
    
    APP -- "1. Update Data & Outbox (Single Transaction)" --> DB
    APP --> OB
    OR -- "2. Poll Outbox" --> OB
    OR -- "3. Publish Events" --> K
    OR -- "4. Mark as Published" --> OB
    
    style APP fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style DB fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style OB fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style OR fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style K fill:#FFADAD,stroke:#FF5A5F,stroke-width:2px
```

## 📊 Monitoring

### Metrics to Track

- **Producer Metrics**
    - request-rate
    - error-rate
    - request-latency-avg
    - batch-size-avg
    - record-send-rate

- **Consumer Metrics**
    - records-consumed-rate
    - bytes-consumed-rate
    - fetch-rate
    - consumer-lag

- **Broker Metrics**
    - under-replicated-partitions
    - request-handler-avg-idle-percent
    - request-queue-size
    - network-processor-avg-idle-percent

### Visualization Stack

```mermaid
graph TD
    subgraph "Metrics Collection"
        JMX[JMX]
        PA[Prometheus Agent]
    end
    
    subgraph "Metrics Storage"
        P[Prometheus]
    end
    
    subgraph "Visualization"
        G[Grafana]
    end
    
    subgraph "Alerting"
        A[Alertmanager]
    end
    
    JMX --> PA
    PA --> P
    P --> G
    P --> A
    
    style JMX fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style PA fill:#97C2FC,stroke:#2D6EB5,stroke-width:2px
    style P fill:#FFD2A5,stroke:#FF8C42,stroke-width:2px
    style G fill:#A7E8BD,stroke:#25A18E,stroke-width:2px
    style A fill:#FFADAD,stroke:#FF5A5F,stroke-width:2px
```

## 🔍 Troubleshooting

### Common Issues and Solutions

| Issue | Possible Causes | Solutions |
|-------|----------------|-----------|
| High Consumer Lag | Slow consumer processing<br>Insufficient resources | Increase consumer instances<br>Optimize consumer code<br>Increase hardware resources |
| Under-replicated Partitions | Network issues<br>Broker failures<br>Disk issues | Check network connectivity<br>Restart failed brokers<br>Verify disk space and I/O |
| Producer Timeouts | Network issues<br>Broker overload<br>Full disk | Check network connectivity<br>Add more brokers<br>Verify disk space |
| Uneven Partition Distribution | Poor partition assignment<br>Uneven data distribution | Use custom partitioner<br>Evaluate key distribution<br>Rebalance partitions |

### Debugging Tools

- **Kafka Command Line Tools**:
    - `kafka-topics.sh`: Manage topics
    - `kafka-consumer-groups.sh`: Monitor consumer groups
    - `kafka-dump-log.sh`: Examine log segments
    - `kafka-configs.sh`: View and modify configurations

- **Third-party Tools**:
    - [Conduktor](https://www.conduktor.io/): Kafka GUI for monitoring and management
    - [Kafdrop](https://github.com/obsidiandynamics/kafdrop): Web UI for Kafka
    - [Kafka Exporter](https://github.com/danielqsj/kafka_exporter): Prometheus exporter

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

---

<div align="center">
  <p>Made with ❤️ by <a href="https://github.com/yourusername">Your Name</a></p>
  <p>⭐ Star this repository if it helped you!</p>
</div>