# Recommendation service
# Run application via docker
1. Run application by docker-compose
2. Update DB username/password
# API Documents
1. <base-url>/api-docs/
2. <base-url>/swagger-ui/index.html
# Supporting use cases
1. Reads all the prices from the csv files to the mariadb

a. Prepare a CSV similar as data.csv sample file

b. Upload via this API 

POST /api/crypto/upload 

a sample response once upload successfully.
```
{
    "status": {
        "code": "00",
        "message": "Success"
    },
    "data": "Uploaded the file successfully: data.csv"
}
```

2. Exposes an endpoint that will return a descending sorted list of all the cryptos,
   comparing the normalized range (the formula (max-min)/min) is hard coded atm)

GET /api/report/cryptos

```
{
    "status": {
        "code": "00",
        "message": "Success"
    },
    "data": [
        "BTC",
        "ETC"
    ]
}
```

3. Exposes an endpoint that will return the oldest/newest/min/max values for a requested
   crypto

GET /api/report/btc/statistics

```
{
    "status": {
        "code": "00",
        "message": "Success"
    },
    "data": {
        "oldest": 46813.00,
        "newest": 46813.25,
        "min": 46813.00,
        "max": 99468.21
    }
}
```

4. Exposes an endpoint that will return the crypto with the highest normalized range ((max-min)/min formula is hard coded atm) for a
   specific day (also supporting top n of highest by passing param `limit = n`, top lowest not implemented yet)

GET /api/report/cryptos?fromTimestamp=2022-09-12 00:00:00&toTimestamp=2022-09-12 00:00:00&limit=1

```
{
    "status": {
        "code": "00",
        "message": "Success"
    },
    "data": [
        "BTC"
    ]
}
```

5. A negative case for invalid/not supported symbol
ie: `symbol = dat` that not have data/not supported
GET /api/report/dat/statistics

```
{
    "status": {
        "code": null,
        "message": "No data found"
    },
    "data": null
}
```

6. For some cryptos it might be safe to invest, by just checking only one month's time
   frame. However, for some of them it might be more accurate to check six months or even
   a year. Will the recommendation service be able to handle this?

By this request can use this API for analysis 1 month, 6 months or 1 year (just need to provide datetime period)
ie: 

Analysis data of 1 month

GET /api/report/btc/statistics?fromTimestamp=2022-01-01 00:00:00&toTimestamp=2022-01-31 23:59:59

Analysis data of 6 months

GET /api/report/btc/statistics?fromTimestamp=2022-01-01 00:00:00&toTimestamp=2022-06-31 23:59:59

Analysis data of 1 year

GET /api/report/btc/statistics?fromTimestamp=2022-01-01 00:00:00&toTimestamp=2023-01-01 00:00:00

```
{
    "status": {
        "code": "00",
        "message": "Success"
    },
    "data": {
        "oldest": 46813.00,
        "newest": 46813.25,
        "min": 46813.00,
        "max": 99468.21
    }
}
```
