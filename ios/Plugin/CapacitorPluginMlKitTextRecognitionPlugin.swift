import Foundation
import Capacitor
import MLKitVision
import MLKitTextRecognition

@objc(CapacitorPluginMlKitTextRecognitionPlugin)
public class CapacitorPluginMlKitTextRecognitionPlugin: CAPPlugin {
    @objc func detectText(_ call: CAPPluginCall) {
        guard let encodedImage = call.getString("base64Image") else {
            call.reject("No image is given!")
            return
        }
        let rotation = call.getInt("rotation") ?? 0

        // Remove prefix if present
        let base64Image: String
        if encodedImage.hasPrefix("data:image/jpeg;base64,") {
            base64Image = encodedImage.replacingOccurrences(of: "data:image/jpeg;base64,", with: "")
        } else if encodedImage.hasPrefix("data:image/png;base64,") {
            base64Image = encodedImage.replacingOccurrences(of: "data:image/png;base64,", with: "")
        } else {
            base64Image = encodedImage
        }

        guard let dataDecoded = Data(base64Encoded: base64Image, options: .ignoreUnknownCharacters) else {
            call.reject("Unable to decode base64 string")
            return
        }

        guard let image = UIImage(data: dataDecoded) else {
            call.reject("Unable to parse image")
            return
        }

        let latinOptions = TextRecognizerOptions()
        let textRecognizer = TextRecognizer.textRecognizer(options: latinOptions)
        let visionImage = VisionImage(image: image)
        visionImage.orientation = visionImageOrientation(rotation: rotation)

        textRecognizer.process(visionImage) { result, error in
            guard error == nil, let result = result else {
                call.reject("Error on processing image: \(error?.localizedDescription ?? "Unknown error")")
                return
            }

            var linesArray = [String]()
            for textBlock: TextBlock in result.blocks {
                for line: TextLine in textBlock.lines {
                    linesArray.append(line.text)
                }
            }

            call.resolve([
                "text": result.text,
                "blocks": linesArray
            ])
        }
    }

    public func visionImageOrientation(rotation: Int) -> UIImage.Orientation {
        switch rotation {
        case 0:
            return .up
        case 90:
            return .left
        case 180:
            return .down
        case 270:
            return .right
        default:
            return .up
        }
    }
}
