'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const CapacitorPluginMlKitTextRecognition = core.registerPlugin('CapacitorPluginMlKitTextRecognition', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.CapacitorPluginMlKitTextRecognitionWeb()),
});

class CapacitorPluginMlKitTextRecognitionWeb extends core.WebPlugin {
    // @ts-ignore
    detectText(options) {
        return Promise.reject("Web Plugin Not implemented");
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    CapacitorPluginMlKitTextRecognitionWeb: CapacitorPluginMlKitTextRecognitionWeb
});

exports.CapacitorPluginMlKitTextRecognition = CapacitorPluginMlKitTextRecognition;
//# sourceMappingURL=plugin.cjs.js.map
