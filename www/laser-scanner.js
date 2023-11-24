var exec = require("cordova/exec");

// open and listen laser scanner service
exports.init = function (success, error) {
    exec(success, error, "LaserScannerUtil", "init", []);
};
