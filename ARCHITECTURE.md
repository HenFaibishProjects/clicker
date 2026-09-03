# Clicker Architecture

The system receives ad events such as impressions, clicks, and registrations.

The main goal is to:
- receive events quickly
- process them safely
- avoid duplicate counting
- keep fast counters
- keep raw events for analytics

## GKE

Runs the Spring Boot application.

Why:
- easy to scale
- can run multiple instances
- good for a long-running backend service

## Load Balancer

Sends requests to the available Spring Boot instances.

Why:
- distributes traffic
- helps us scale horizontally

## Spring Boot

Handles:
- REST API
- publishing events
- consuming events
- deduplication logic
- aggregation logic
- metrics API

Why:
- one service is enough for the first version
- no need to create more microservices without a real reason

## Pub/Sub

Used as a message queue between receiving the event and processing it.

Why:
- the API does not need to wait for processing
- handles traffic spikes
- supports retries
- separates ingestion from processing

Simple explanation:

"The API receives the event and puts it in Pub/Sub.
The subscriber processes it later."

## Redis

Used to prevent duplicate events.

Why:
- very fast
- good for checking if an eventId already exists
- supports atomic operations
- supports TTL, so old event IDs can expire

Simple explanation:

"Before processing an event, I check Redis to make sure I did not already process the same event."

## Bigtable

Stores the counters:
- impressions
- clicks
- registrations

Why:
- very fast writes
- very fast key-based reads
- good for frequent counter updates
- designed for large scale

Simple explanation:

"Bigtable stores the current counters that the application needs to read quickly."

## BigQuery

Stores the raw events.

Why:
- good for analytics
- good for SQL queries on large amounts of data
- good for reports and historical analysis

Simple explanation:

"BigQuery keeps the history. Bigtable keeps the current counters."

## Why Bigtable and BigQuery?

They solve different problems.

Bigtable:
- real-time counters
- fast reads and updates

BigQuery:
- raw data
- analytics
- reports

Simple explanation:

"I use Bigtable for serving and BigQuery for analytics."

## Main Flow

Client
→ Load Balancer
→ Spring Boot API
→ Pub/Sub
→ Subscriber
→ Redis
→ Bigtable
→ BigQuery

## Metrics Flow

Client
→ Metrics API
→ BigTable

## Explain
Pub/Sub: "I use it to decouple the API from the processing and to handle traffic spikes."

Redis: "I use it for fast deduplication by eventId."

Bigtable: "I use it for high-throughput real-time counters."

BigQuery: "I use it for raw events, analytics and historical reports."