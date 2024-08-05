import { WebPlugin } from '@capacitor/core';
import type { CapacitorPluginMlKitTextRecognitionPlugin, DetectImageOptions, TextDetectionResult } from './definitions';
export declare class CapacitorPluginMlKitTextRecognitionWeb extends WebPlugin implements CapacitorPluginMlKitTextRecognitionPlugin {
    detectText(options: DetectImageOptions): Promise<TextDetectionResult>;
}
