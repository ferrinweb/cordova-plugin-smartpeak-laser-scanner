var exec = require("cordova/exec");

// open and listen laser scanner service
exports.listen = function(success, error) {
    exec(success, error, "LaserScannerUtil", "listen", []);
};

// close laser scanner service
exports.close = function(success, error) {
    exec(success, error, "LaserScannerUtil", "close", []);
};

// get laser scanner result history
exports.history = function(success, error) {
    exec(success, error, "LaserScannerUtil", "history", []);
};

// exec laser scan by function
exports.scan = function(success, error) {
    exec(success, error, "LaserScannerUtil", "scan", []);
};