# dff-cordova-plugin-crashreport
Reports unhandled exceptions and saves reports in app directory

## Supported platforms

- Android

## Changelog

### 1.1.0
- Directory info added

## Installation
    cordova plugin add https://github.com/dff-solutions/dff-cordova-plugin-crashreport.git
    
## Reporting
### File location
Crash reports are stored in `crashreports` directory within the external files directory of your app.
File name is `crashreport_<datetime>.txt`
E.g.:

- Android: `/storage/sdcard0/Android/data/<package_name>/files/crashreports`
 
### Report format

#### Android
A report on Android has basically the following format. All properties contain more details.
```json
{
  "throwable": {
      "message": "",
      "cause": {},
      "stackTrace": []
    },
  "os": {},
  "pid": 10105,
  "memoryInfo": {},
  "myMemoryState": {},
  "date": "",
  "directories: {}"
  "debugMemoryInfo": {},
  "processErrorStateInfo": [],
  "memoryClass": 96,
  "runningServiceInfo": [],
  "isRunningInTestHarness": false,
  "thread": {},
  "isUserAMonkey": false,
  "lowRamDevice": false,
  "runningAppProcesses": []
}
```
    
## Usage

Plugin is available via global variable `CrashReport`.

### onCrash
The plugin tries to send a plugin result to JavaScript. But in most cases the app will be closed before.
Nevermind the report is always saved as a file.

```js
CrashReport
  .onCrash(function (report) {
      console.log(report);
    }, function (reason) {
      console.error(reason);
    });
```
