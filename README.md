Currency exchange REST API

On a regular basis, the API loads data from resources into the internal database using cron job

The API responds in json format. In the file "application.properties" you can specify the frequency with which 
requests will be made, the currency against which the exchange is made, the list of currencies to be exchanged.

This API has 2 methods:

* Request a list of rates for all sources with average market rates.

The request is made in the form

/api/exchangeRate/latest?base={base_currency}&currencies={list_currencies}

{list_currencies} - A list of currencies with a separator " , " existing and present in 
the response of the bank

* Request for issuing a list of average exchange rates for all sources for a period

The request is made in the form

/api/exchangeRate/timerange?base={base_currency}}&currencies={list_currencies}&dateFrom={date from}&dateTo={date_to}

The date is specified in Unix Timestamp format
