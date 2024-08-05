var capacitorCapacitorPluginMlKitTextRecognition = (function (exports, core) {
    'use strict';

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

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
