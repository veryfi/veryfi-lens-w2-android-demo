# Veryfi Lens Android — W-2 — settings

Source of truth: Veryfi Lens Android SDK **2.7.0.6**, commit `7f33726c`. Names below were read from that tree. Items not fully confirmed are marked **Unconfirmed**. Do not copy repository passwords, keystores, or Demo-only types into the host app.


Always construct a fresh `VeryfiLensSettings()`. Android has no `VeryfiLens*Settings` subclasses. Pass that instance to `configure`. The SDK mutates it.

```kotlin
import com.veryfi.lens.helpers.DocumentType
import com.veryfi.lens.helpers.VeryfiLensSettings

fun w2Settings() = VeryfiLensSettings().apply {
    ignoreRemoteSettings = true            // default false; dashboard lens_settings overwrite this object during configure
    backupDocsToGallery = false            // default true; copies every scan into the device gallery
    moreMenuIsOn = false                   // default true; stored user prefs then replace host capture flags
    moreSettingsMenuIsOn = false           // default true
    autoCaptureOtherIsOn = false           // default true; OTHER auto-captures even when autoCaptureIsOn is false
    showNoDocumentDetectedWarning = false  // default true
    dictateIsOn = false                    // default true; obsolete voice capture, not a partner feature
    locationServicesIsOn = false           // default false; true raises a runtime location request
    glareDetectionIsOn = false             // default true; the glare gate is a weak algorithmic check

    dataExtractionEngine = VeryfiLensSettings.ExtractionEngine.VeryfiCloudAPI
    documentTypes = arrayListOf(DocumentType.W2)
    showDocumentTypes = false
    autoCaptureIsOn = false
    stitchIsOn = false
    galleryIsOn = true
    showBrowserButton = true              // default false; W-2 PDFs from Files
    autoRotateIsOn = true                 // default false
    cleanBorderIsOn = true                // default false
    returnStitchedPDF = true              // default false
}

```

## Defaults versus this recipe

Android initializers below are the property defaults in `VeryfiLensSettings`.

| Property | Android default | This recipe | Why |
|----------|-----------------|-------------|-----|
| `ignoreRemoteSettings` | false | true | Dashboard `lens_settings` would overwrite this object during configure |
| `backupDocsToGallery` | true | false | Default copies every scan into the device gallery |
| `moreMenuIsOn` | true | false | Stored user prefs would replace host capture flags on later configure calls |
| `moreSettingsMenuIsOn` | true | false | Settings sheet inside More |
| `autoCaptureOtherIsOn` | true | false | `OTHER` auto-captures even when `autoCaptureIsOn` is false |
| `showNoDocumentDetectedWarning` | true | false | Skip the no-document alert |
| `dictateIsOn` | true | false | Obsolete voice control; default is on |
| `glareDetectionIsOn` | true | false | Android default glare gate is a weak check |
| `locationServicesIsOn` | false | false | Leave off unless the host needs lat/lng on the package |

| `documentTypes` | null | `W2` | Only legal type on this artifact |
| `stitchIsOn` | true | false | Single form unless the host asks to stitch |
| `showBrowserButton` | false | true | In-camera file import |
| `autoRotateIsOn` | false | true | Rotate the form |
| `cleanBorderIsOn` | false | true | Clean borders |
| `returnStitchedPDF` | false | true | `img_stitched_pdf_path` |
| `fraudDetectionIsOn` | false | false | W-2 detector forces fraud off anyway |


`getRequiredModels` still adds document-crop models for `W2`. It does not add field-detection models (those are receipt, long receipt, invoice, other). `ImageProcessor` maps `DocumentType.W2` to `W2DetectorMode` and calls `initDocumentDetector(..., isFraudDetectionOn = false)`.

## Suggestions

- Offer `showDocumentBrowser()` for PDFs the user already has, and `uploadImages(uris)` for a share intent.
- If install size matters and other Full types are in scope, compare `veryfi-lens-remote-models-sdk` (allows `W2`, downloads models). Lite cannot host `W2`.
- Measure the installed app.

