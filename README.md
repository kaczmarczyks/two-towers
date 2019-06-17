# two towers
Basic spring setup with two datasources and two separate liquibase changelogs.

This example can be used in scenario, when out of many bad options integration through database seems the best. i.e. with legacy system.

### First datasource 
- contains EntityOne. 
- two towers service is an owner of this datasource, so it manages its schema with liquibase changelog.

### Second datasoirce
- contains EntityTwo
- this service needs access to second datasource, but isn't an owner. schema is provided by 3rd party. Liquibase is configured only in test scope, to allow easy testing.

