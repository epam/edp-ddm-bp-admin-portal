# business-process-administration-portal

##### Install platform-logger-spring-boot-starter library
##### For local development set active profile 'local'
##### Logger provide stdout in JSON format following predefined layout:

```javascript
{
  "@timestamp": {
    "$resolver": "timestamp",
    "pattern": {
      "format": "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
      "timeZone": "UTC"
    }
  },
  "X-B3-TraceId": {
    "$resolver": "mdc",
    "key": "X-B3-TraceId",
    "stringified": true
  },
  "X-B3-SpanId": {
    "$resolver": "mdc",
    "key": "X-B3-SpanId",
    "stringified": true
  },
  "X-Request-Id": {
    "$resolver": "mdc",
    "key": "x-request-id",
    "stringified": true
  },
  "thread": {
    "$resolver": "thread",
    "field": "name"
  },
  "level": {
    "$resolver": "level",
    "field": "name"
  },
  "class": {
    "$resolver": "source",
    "field": "className"
  },
  "line_number": {
    "$resolver": "source",
    "field": "lineNumber"
  },
  "method": {
    "$resolver": "source",
    "field": "methodName"
  },
  "message": {
    "$resolver": "message",
    "stringified": true
  },
  "exception": {
    "type": {
      "$resolver": "exception",
      "field": "className"
    },
    "message": {
      "$resolver": "exception",
      "field": "message",
      "stringified": true
    },
    "stacktrace": {
      "$resolver": "exception",
      "field": "stackTrace",
      "stringified": true
    }
  }
}
```
