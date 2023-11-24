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
LaserScannerUtil.init(result => {
  result = JSON.parse(result)
  if (result.text) {
    return console.log(result.text.trim())
  }
  if (result.message) {
    console.log(result.message)
  }
}, e => {
  console.error(e)
})
```

## Lisence

MIT Lisence.
