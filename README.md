# Nordeus Challenge
This app parses data given by nordeus in nordeus jobfair challange
and gives two endpoints `GET /game-stats` and `GET /user-stats`.
This endpoints can be used to compute useful information for game or user.


Make sure to configure `application.properties` to setup connection to your PostgreSQL database.

## Usage with Maven
Build and run:
```
./mwnw spring-boot:run
```

After than run `GET /load` to load data before using other endpoints

## Endpoints

### `GET /load`
**Load data endpoint**

This endpoint parses .jsonl data and loads it up into database.
Data from .jsonl will be filtered and cleaned.



### `GET /game-stats`
**Game stats endpoint**

Returns statistics about the game from loaded data, it can be filtered by date or by country.

Request parameters are `country` (optional, 2 uppercase of country ISO 639-1 ) and `date` (optional, yyyy-mm-dd)

Examples:
- `GET /game-stats`
- `GET /game-stats?country=IT`
- `GET /game-stats?date=2010-05-23`
- `GET /game-stats?country=IT&date=2010-05-23`

### `GET /user-stats?user_id={user_id}`
**User stats endpoint**

Returns statistics about the user from data, it can be filtered by date or by country.

Request parameters are `user_id` (required) and `date` (optional, yyyy-mm-dd)

Examples:
- `GET /user-stats?user_id=1623df4e-5bcb-11ed-84c2-8afbeb371208`
- `GET /user-stats?user_id=1623df4e-5bcb-11ed-84c2-8afbeb371208&date=2010-05-10`