---
name: veryfi-lens-android-w2-integrate
description: "Integrates the Veryfi Lens Android W-2 SDK (com.veryfi.lens:veryfi-lens-w2-sdk:2.7.0.6): Maven setup, VeryfiCloudAPI, VeryfiLens.configure, VeryfiLensSettings, delegate callbacks, showCamera, showGallery, showDocumentBrowser, and uploadImages for W-2 PDFs. Use when adding W-2 capture or upload on Android."
---

# Veryfi Lens Android — W-2

Walk a host Android app from Gradle to a working first scan. Change the fewest flags. Do not invent SDK APIs. Facts were read from the Android SDK at version `2.7.0.6` (commit `7f33726c`). Anything not confirmed there is marked **Unconfirmed**.

**Coordinate:** `com.veryfi.lens:veryfi-lens-w2-sdk:2.7.0.6`  
**Gradle flavor:** `lensW2`  
**Allowed document types:** `DocumentType.W2` only.

Public entry point: `com.veryfi.lens.VeryfiLens` with `com.veryfi.lens.VeryfiLensDelegate`. Settings type: `com.veryfi.lens.helpers.VeryfiLensSettings`.

This folder is complete by itself. Duplicate guidance across products is intentional.

## Pick a workflow

- **Quick** — the host already asked for this product. Ask nothing else. Apply the settings in [settings.md](settings.md), wire [implementation.md](implementation.md), and state the assumptions below.
- **Guided** — something material is missing. Ask only the unknown questions (at most four, one message), then continue.

### Quick workflow

```
veryfi-lens-android-w2-integrate quick:
- [ ] Confirm this artifact (install.md)
- [ ] Add the Maven repository and this one dependency
- [ ] Permissions (install.md)
- [ ] Credentials + settings from settings.md
- [ ] setDelegate, then configure { ok -> }, then showCamera() only when ok is true
- [ ] Handle the callbacks in callbacks.md
- [ ] Run the conflicts in troubleshooting.md
```

Assumptions when the host was specific about the product and silent on the rest: Lens camera plus optional file browser, `W2`, Cloud API, stitch off, gallery on, fraud left off because the W-2 detector forces it off.

### Guided workflow

1. **Entry** — camera, gallery, Files for PDFs (`showBrowserButton` / `showDocumentBrowser` / `uploadImages(uris)`)?
2. **Pages** — single W-2, or stitch?
3. **Extraction** — Cloud API (default) or decoupled `None`?

Defaults: camera, gallery, browser button on, Cloud API, stitch off.


## Rules

1. `documentTypes = arrayListOf(DocumentType.W2)` and `VeryfiCloudAPI`.
2. `ImageProcessor` initializes the W-2 detector with `isFraudDetectionOn = false` regardless of `fraudDetectionIsOn`.
3. PDFs use `showDocumentBrowser()` or `uploadImages(uris)`. The paths overload ignores PDFs.
4. `uploadImages` is this UI SDK, not Headless.


## Do not

- Copy a repository password, keystore, or credential from the SDK repo or the Demo app. Maven access comes from Veryfi. Keep it in `~/.gradle/gradle.properties` or CI secrets.
- Depend on a second Lens UI flavor, or on `com.veryfi.lens.core:veryfi-lens-core-sdk` (email and obsolete dictation; adds `WRITE_CONTACTS` and `RECORD_AUDIO`).
- Treat Demo types as the client contract: `SettingsHolder`, Demo analyzers, `ImageProxyUtils`, `VeryfiLensHelper`.
- Set `dictateIsOn = true` or call `VeryfiLensCore.showDictation()`.
- Set `anyDocumentType` (deprecated local regex OCR). A new template id is `anyDocumentTemplate`, and only after Veryfi support confirms it.
- Set `useLegacyModels` (default true; no partner contract).
- Quote AAR size as the installed app size. Compare the final app with the SDK linked and removed. [SDK binary sizes](https://docs.veryfi.com/lens/mobile/settings/sdk_binary_sizes/).
- Treat `allowSubmittingScreenshots` (rejects imported screenshots) and `enableScreenshots` (device screenshots of Lens screens, default true) as one switch.
- Promise `fraudDetectionIsOn` on W-2. The detector passes `isFraudDetectionOn = false`.
- Put `W2` on Lite.
- Use `uploadImages(paths)` for a PDF W-2.
- Invent an Any-Documents template for a W-2.


## What to hand back

1. `implementation "com.veryfi.lens:veryfi-lens-w2-sdk:2.7.0.6"`.
2. `w2Settings()`.
3. Delegate for Cloud `data`.
4. Explicit note that on-device fraud is disabled for the W-2 detector.


## Files in this folder

- [install.md](install.md) — Maven coordinate, requirements, permissions, app size
- [implementation.md](implementation.md) — configure, delegate, and launch or camera lifecycle
- [settings.md](settings.md) — `VeryfiLensSettings` for this product, with Android defaults
- [callbacks.md](callbacks.md) — delegate payloads
- [troubleshooting.md](troubleshooting.md) — symptom, cause, fix
- [clarifications.md](clarifications.md) — Unconfirmed items, Any-Documents, fraud, field detection
- [checklist.md](checklist.md) — validation checklist before handing code to the host

