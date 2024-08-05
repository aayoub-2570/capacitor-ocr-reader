import { registerPlugin } from '@capacitor/core';
const CapacitorPluginMlKitTextRecognition = registerPlugin('CapacitorPluginMlKitTextRecognition', {
    web: () => import('./web').then(m => new m.CapacitorPluginMlKitTextRecognitionWeb()),
});
export * from './definitions';
export { CapacitorPluginMlKitTextRecognition };
//# sourceMappingURL=index.js.map