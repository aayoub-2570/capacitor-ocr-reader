package com.pantrist.ml;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Base64;
import android.util.Log;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.PluginCall;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

@CapacitorPlugin(name = "CapacitorPluginMlKitTextRecognition")
public class CapacitorPluginMlKitTextRecognition extends Plugin {

    @PluginMethod
    public void detectText(PluginCall call) {
        com.google.mlkit.vision.text.TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        String encodedImage = call.getString("base64Image");
        if (encodedImage == null) {
            call.reject("No image is given!");
            Log.e("CapacitorPluginMlKit", "No image is given!");
            return;
        }
        int rotation = call.getInt("rotation", 0);

        InputImage image;
        try {
            // Log the base64 string length and part of the base64 string
            Log.d("CapacitorPluginMlKit", "Base64 image string length: " + encodedImage.length());
            Log.d("CapacitorPluginMlKit", "Base64 image string (first 100 chars): " + encodedImage.substring(0, Math.min(100, encodedImage.length())));

            // Remove prefix if present
            String base64Image = removeBase64Prefix(encodedImage);

            // Log the cleaned base64 string length and part of the cleaned base64 string
            Log.d("CapacitorPluginMlKit", "Cleaned base64 image string length: " + base64Image.length());
            Log.d("CapacitorPluginMlKit", "Cleaned base64 image string (first 100 chars): " + base64Image.substring(0, Math.min(100, base64Image.length())));

            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);

            // Log the decoded data size
            Log.d("CapacitorPluginMlKit", "Decoded data size: " + decodedString.length + " bytes");

            if (decodedString.length == 0) {
                call.reject("Decoded byte array is empty");
                Log.e("CapacitorPluginMlKit", "Decoded byte array is empty");
                return;
            }

            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            if (decodedByte == null) {
                call.reject("Decoded image is null");
                Log.e("CapacitorPluginMlKit", "Decoded image is null");
                return;
            }

            // Log the image dimensions
            Log.d("CapacitorPluginMlKit", "Image dimensions: " + decodedByte.getWidth() + "x" + decodedByte.getHeight());

            image = InputImage.fromBitmap(decodedByte, rotation);
            Log.d("CapacitorPluginMlKit", "InputImage created successfully");

        } catch (IllegalArgumentException e) {
            call.reject("Unable to decode base64 string");
            Log.e("CapacitorPluginMlKit", "Unable to decode base64 string", e);
            return;
        } catch (Exception e) {
            call.reject("Unexpected error during image processing");
            Log.e("CapacitorPluginMlKit", "Unexpected error during image processing", e);
            return;
        }

        recognizer.process(image)
            .addOnSuccessListener(visionText -> {
                JSObject ret = new JSObject();
                ret.put("text", visionText.getText());

                JSArray linesArray = new JSArray();
                for (com.google.mlkit.vision.text.Text.TextBlock block : visionText.getTextBlocks()) {
                    for (com.google.mlkit.vision.text.Text.Line line : block.getLines()) {
                        linesArray.put(line.getText());
                    }
                }
                ret.put("blocks", linesArray);

                call.resolve(ret);
                Log.d("CapacitorPluginMlKit", "Text recognition successful");
            })
            .addOnFailureListener(e -> {
                call.reject("Unable to process image!", e);
                Log.e("CapacitorPluginMlKit", "Unable to process image!", e);
            });
    }

    private String removeBase64Prefix(String encodedImage) {
        if (encodedImage.startsWith("data:image/jpeg;base64,")) {
            return encodedImage.replace("data:image/jpeg;base64,", "");
        } else if (encodedImage.startsWith("data:image/png;base64,")) {
            return encodedImage.replace("data:image/png;base64,", "");
        } else {
            return encodedImage;
        }
    }

    private JSObject parseRectToJsObject(Rect rect) {
        if (rect == null) {
            return null;
        }

        JSObject returnObject = new JSObject();
        returnObject.put("left", rect.left);
        returnObject.put("top", rect.top);
        returnObject.put("right", rect.right);
        returnObject.put("bottom", rect.bottom);
        return returnObject;
    }
}