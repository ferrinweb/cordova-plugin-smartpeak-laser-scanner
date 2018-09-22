# cordova-plugin-smartpeak-laser-scanner
A cordova laser barcode scanner plugin for smartpeak PDA.

## Info
PDA of smartpeak company is based on android@5.1, and therefore the plugin only test on this device.

You can get information about the device from this page: [Rugged Android data collection terminal](http://en.smartpeak.cn/android/13e36aa5-13d5-9811-e550-c24cc9a2b173.shtml)

## Usage
```
// plugin install
cordova plugin add cordova-plugin-smartpeak-laser-scanner
```
```javascript
// init plugin, when deviceReady event fired
LaserScannerUtil.listen(data => {
  console.info('scan listening...', data)
}, error => {
  console.info('init or scan error: ', error)
})

// close scanner
LaserScannerUtil.close(() => {
  console.info('scanner closed.')
}, error => {
  console.info('scanner error: ', error)
})

// exec a laser scan by function
LaserScannerUtil.scan(data => {
  console.info('scan result: ', data)
}, error => {
  console.info('scan error: ', error)
})

// get laser scanner result history
LaserScannerUtil.history(results => {
  console.info('scan history: ', results)
}, error => {
  console.info('scan error: ', error)
})
```
## Lisence
MIT Lisence.

