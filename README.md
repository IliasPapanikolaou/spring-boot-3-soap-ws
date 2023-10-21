# Spring Boot SOAP WebServices

## Sample Request

http://localhost:8080/ws

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:gs="http://www.ipap.com/springsoap/gen">
    <soapenv:Header/>
    <soapenv:Body>
        <gs:getCountryRequest>
            <gs:name>Greece</gs:name>
        </gs:getCountryRequest>
    </soapenv:Body>
</soapenv:Envelope>
```

## CLI test

```bash
curl --header "content-type: text/xml" -d @request.xml http://localhost:8080/ws
```

If we have xmllint2
```bash
curl --header "content-type: text/xml" -d @request.xml http://localhost:8080/ws | xmllint --format -
```