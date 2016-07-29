/**
 * JavaScript interface to abstract
 * the usage of the ToughpadApi.
 *
 * @module com/dff/cordova/plugins/ToughpadApi
 */

'use strict';

var cordova = require('cordova');
var channel = require('cordova/channel');

var feature = "CrashReport";
var self = {};

var actions = ["onLog", "onCrash", "throwDeadObjectException"];

function createActionFunction (action) {
    return function (success, error, args) {
        cordova.exec(success, error, feature, action, [args]);
    }
}

actions.forEach(function (action) {
    self[action] = createActionFunction(action);
});

module.exports = self;