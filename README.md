# cordova-plugin-seuic-laser-scanner

A cordova laser barcode scanner plugin for SEUIC PDA.

## Info

PDA of Seuic company is based on android@9, and therefore the plugin only test on this device.

You can get information about the device from this page: [Seuic 东集](https://www.seuic.com/search.html?key_word=cruise)

## Usage

```bash
// plugin install
cordova plugin add cordova-plugin-seuic-laser-scanner
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
