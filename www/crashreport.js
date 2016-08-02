/**
 * JavaScript interface to abstract
 * the usage of the crashreport.
 *
 * @module com/dff/cordova/plugins/crashreport
 */

'use strict';

var cordova = require('cordova');
var channel = require('cordova/channel');

var feature = "CrashReport";
var self = {};

var actions = [
    "onLog",
    "onCrash",
    "throwUncaughtException",
    "throwUncaughtExceptionOnUi",
    "throwUncaughtExceptionOnThreadPool"
];

function createActionFunction (action) {
    return function (success, error, args) {
        cordova.exec(success, error, feature, action, [args]);
    }
}

actions.forEach(function (action) {
    self[action] = createActionFunction(action);
});

channel.onCordovaReady.subscribe(function () {
    self.onCrash(function (crashReport) {
        console.error(self.feature, JSON.stringify(crashReport, null, 4));
    }, function (error) {
        console.error(JSON.stringify(error, null, 4));
    });
});

module.exports = self;