# PO
## "Post Office"
### or, a basic public messaging API

This REST API allows to register a login/nickname combination, and use it for posting basic messages into a PostgreSQL database.

### Technologies used

* Gradle for building. It uses an (example project) as a base, which is why the package is named "hello", yet.
* Java and Spring.

### TODO

* DRY it up. Connection string is a glaring magical value and some parts of the code could be split into smaller functions, even if used once or twice.
* "Seed" tokens using nicknames, to prevent token salts from, with a very, very small chance, to repeat.
* Rewrite certain functions to enable them to notify a client whether an error is occured. (Kinda WIP already)