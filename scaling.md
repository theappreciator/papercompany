# Scaling

### Considerations at all levels of scaling:
- Are microservices in use and how are they routed?
- How many public facing endpoints are needed, and what's the strategy for more than one?
- Data contracts between ends need to in place with a strategy for application versioning.
- Where are the product's users geographically, what are their expectations, and what are the SLAs in place?


## Scaling as a first pass:

A first pass at scaling may look like:

1. Assumption: a single instance is running the application.
2. Load test the application to measure baseline performance.
3. Add Java level patterns for thread-safety into operations.  This can be done with, for example, locks and synchronization.  This will solve for concurrency on this single instance.
4. Vertically scale single instance to increase performance.
5. Load test the application to measure new performance.
6. Improvements in performance will be constrained by resources available to a single instance.

Additionally, a caching layer can be introduced.  Considerations will need to be made on:
- Does the cache layer live inside or outside of the application?
    - On a single instance inside will work, but will affect future work to scale to multiple servers.
    - Outside the instance sets up success for future work, but may be additional work/cost relative to the right now needs.
- Cache timeout policy
- Cache size
- Cache key strategy
- Optimizing for highly requested data
- Cache invalidation strategy

## Next step in scaling:

When a single application instance is not the right answer, a next pass at scaling may look like:

1. Assumption: multiple instances are running the application
2. Assumption: routing/balancing is in place to manage distributing requests across the server pool
3. Assumption: a caching strategy is in place outside of the application layer
4. Horizontally scale out to add performance
    - If using microservices, evaluate which need scaling best on analytics and usage.
5. Rely on versioning for database operations.
    1. Updates will only match on the version number, and fail otherwise.
    2. Retry logic can be built into the application layer.  Be cautious about performance since the retry may need to get refreshed data to retry the operation.
    3. Versioning guarantees that the data in the write operation from any single node/thread is from the most current state of the application, thus, data doesn't become out of sync.
    4. Versioning is built into JPA.

A distributed data strategy should also be introduced
- Will need master/slave strategy for optimizing read/write operations
- Will need a data consistency strategy across all nodes

## Scale even larger:

1. Multiple geographic presences:
    - Evaluate geographic density of userbase to determine best locations for endpoints.
    - Will need a strategy for how users connect to their closest/"best" endpoint.
    - Will need a strategy for data distribution and consistency across locations.
2. Message queue architecture
    - Will need a strategy for controlling flow.  Does each operation know about the previous/next, or is the controller managing the entire process?
    - Will need a message timeout strategy.
3. A multi-cloud strategy
    - Diversifying across multiple vendors would increase fault tolerance
    - Will need a strategy for routing users through the best available services