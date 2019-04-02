# INSTALLATION
Clone repository and run mvn install. Executable JAR file with embeded TOMCAT will be generated in target directory.

By default, it runs on port 4000.

# REST Endpoints

## Return all mobile numbers from the database
* URL 'GET /msisdns'
## Return all mobile numbers that match the search criteria
* URL 'GET /msisdns/{msisdnOrPartOfIt}
## Add a mobile number to the database
* URL 'POST /msisdns'
* BODY
{
	"msisdn":"22221231231",
	"customerIdOwner": 123,
	"customerIdUser":234,
	"serviceType": "MOBILE_PREPAID",
	"serviceStartDate":123123312312
}
## Change a mobile number plan from prepaid to postpaid or vice versa
* URL 'PUT /msisdns'
* BODY
{
  "id":1
	"msisdn":"22221231231",
	"customerIdOwner": 123,
	"customerIdUser":234,
	"serviceType": "MOBILE_POSTPAID",
	"serviceStartDate":123123312312
}
## Delete a mobile number from the database
* URL 'DELETE /msisdns'
* BODY
{
	"id":1
}
## Assign different owners / users of a service.
* URL 'PUT /msisdns'
* BODY
{
  "id":1
	"msisdn":"22221231231",
	"customerIdOwner": 666,
	"customerIdUser":555,
	"serviceType": "MOBILE_POSTPAID",
	"serviceStartDate":123123312312
}
