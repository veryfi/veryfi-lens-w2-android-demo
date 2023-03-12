package com.veryfi.lens.w2.demo

import android.content.Intent
import android.content.res.Configuration.*
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.veryfi.lens.VeryfiLens
import com.veryfi.lens.helpers.DocumentType
import com.veryfi.lens.helpers.VeryfiLensCredentials
import com.veryfi.lens.helpers.VeryfiLensSettings
import com.veryfi.lens.w2.demo.databinding.ActivityMainBinding
import com.veryfi.lens.w2.demo.databinding.FragmentBarSelectionBinding
import com.veryfi.lens.w2.demo.databinding.FragmentColorPickerBinding
import com.veryfi.lens.w2.demo.databinding.FragmentEditTextBinding
import com.veryfi.lens.w2.demo.helpers.ThemeHelper
import com.veryfi.lens.w2.demo.logs.LogsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val veryfiLensSettings = VeryfiLensSettings()
    private var autoLightDetectionIsOn = veryfiLensSettings.autoLightDetectionIsOn
    private var stitchIsOn = veryfiLensSettings.stitchIsOn
    private var allowSubmitUndetectedDocsIsOn = veryfiLensSettings.allowSubmitUndetectedDocsIsOn
    private var autoSubmitDocumentOnCapture = veryfiLensSettings.autoSubmitDocumentOnCapture
    private var backupDocsToGallery = veryfiLensSettings.backupDocsToGallery
    private var returnStitchedPDF = veryfiLensSettings.returnStitchedPDF
    private var closeCameraOnSubmit = veryfiLensSettings.closeCameraOnSubmit
    private var locationServicesIsOn = veryfiLensSettings.locationServicesIsOn
    private var originalImageMaxSizeInMB = veryfiLensSettings.originalImageMaxSizeInMB
    private var autoRotateIsOn = veryfiLensSettings.autoRotateIsOn
    private var autoDocDetectionAndCropIsOn = veryfiLensSettings.autoDocDetectionAndCropIsOn
    private var blurDetectionIsOn = veryfiLensSettings.blurDetectionIsOn
    private var autoSkewCorrectionIsOn = veryfiLensSettings.autoSkewCorrectionIsOn
    private var autoCropGalleryIsOn = veryfiLensSettings.autoCropGalleryIsOn
    private var primaryColor = veryfiLensSettings.primaryColor ?: "#FF005AC1"
    private var primaryDarkColor = veryfiLensSettings.primaryDarkColor ?: "#FFADC6FF"
    private var secondaryColor = veryfiLensSettings.secondaryColor ?: "#FFDBE2F9"
    private var secondaryDarkColor = veryfiLensSettings.secondaryDarkColor ?: "#FF3F4759"
    private var accentColor = veryfiLensSettings.accentColor ?: "#FF005AC1"
    private var accentDarkColor = veryfiLensSettings.accentDarkColor ?: "#FFDBE2F9"
    private var docDetectFillUIColor = veryfiLensSettings.docDetectFillUIColor ?: "#9653BF8A"
    private var submitButtonBackgroundColor = veryfiLensSettings.submitButtonBackgroundColor
    private var submitButtonBorderColor = veryfiLensSettings.submitButtonBorderColor
    private var submitButtonFontColor = veryfiLensSettings.submitButtonFontColor
    private var docDetectStrokeUIColor = veryfiLensSettings.docDetectStrokeUIColor ?: "#00000000"
    private var submitButtonCornerRadius = veryfiLensSettings.submitButtonCornerRadius
    private var manualCropIsOn = veryfiLensSettings.manualCropIsOn
    private var moreMenuIsOn = veryfiLensSettings.moreMenuIsOn
    private var moreSettingsMenuIsOn = veryfiLensSettings.moreSettingsMenuIsOn
    private var galleryIsOn = veryfiLensSettings.galleryIsOn
    private var dictateIsOn = veryfiLensSettings.dictateIsOn
    private var emailCCIsOn = veryfiLensSettings.emailCCIsOn
    private var emailCCDomain = veryfiLensSettings.emailCCDomain
    private var rotateDocIsOn = veryfiLensSettings.rotateDocIsOn
    private var shieldProtectionIsOn = veryfiLensSettings.shieldProtectionIsOn
    private var autoDeleteAfterProcessing = veryfiLensSettings.autoDeleteAfterProcessing
    private var boostModeIsOn = veryfiLensSettings.boostModeIsOn
    private var boundingBoxesIsOn = veryfiLensSettings.boundingBoxesIsOn
    private var detectBlurResponseIsOn = veryfiLensSettings.detectBlurResponseIsOn
    private var isProduction = veryfiLensSettings.isProduction
    private var confidenceDetailsIsOn = veryfiLensSettings.confidenceDetailsIsOn
    private var parseAddressIsOn = veryfiLensSettings.parseAddressIsOn
    private var externalId = veryfiLensSettings.externalId ?: ""
    private var gpuIsOn = veryfiLensSettings.gpuIsOn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ThemeHelper.setBackgroundColorToStatusBar(this)
        initVeryfiSettings()
        setUpClickEvents()
    }

    override fun onStart() {
        super.onStart()
        initVeryfiLogo()
    }

    private fun initVeryfiLogo() {
        val logo = when (resources?.configuration?.uiMode?.and(UI_MODE_NIGHT_MASK)) {
            UI_MODE_NIGHT_YES -> R.drawable.ic_veryfi_logo_white
            UI_MODE_NIGHT_NO -> R.drawable.ic_veryfi_logo_black
            else -> R.drawable.ic_veryfi_logo_black
        }
        binding.veryfiLogo.setImageResource(logo)
    }

    private fun initVeryfiSettings() {
        with(binding.settingsGeneralCard) {
            switchLight.isChecked = autoLightDetectionIsOn
            switchStitch.isChecked = stitchIsOn
            switchSubmitUndetectedDocs.isChecked = allowSubmitUndetectedDocsIsOn
            switchSubmitDocumentsCapture.isChecked = autoSubmitDocumentOnCapture
            switchBackupDocs.isChecked = backupDocsToGallery
            switchStitchedPdf.isChecked = returnStitchedPDF
            switchCloseCameraSubmit.isChecked = closeCameraOnSubmit
            switchLocationServices.isChecked = locationServicesIsOn
            txtOriginalImageSize.text = originalImageMaxSizeInMB.toString()
        }
        with(binding.settingsImageProcessingCard) {
            switchAutoRotate.isChecked = autoRotateIsOn
            switchAutoDocDetection.isChecked = autoDocDetectionAndCropIsOn
            switchBlur.isChecked = blurDetectionIsOn
            switchSkew.isChecked = autoSkewCorrectionIsOn
            switchAutoCropGallery.isChecked = autoCropGalleryIsOn
            switchGpu.isChecked = gpuIsOn
        }
        with(binding.settingsUiCard) {
            imgPrimaryColor.setBgColor(primaryColor)
            imgPrimaryDarkColor.setBgColor(primaryDarkColor)
            imgSecondaryColor.setBgColor(secondaryColor)
            imgSecondaryDarkColor.setBgColor(secondaryDarkColor)
            imgAccentColor.setBgColor(accentColor)
            imgAccentDarkColor.setBgColor(accentDarkColor)
            imgDetectFillColor.setBgColor(docDetectFillUIColor)
            imgSubmitBackgroundColor.setBgColor(submitButtonBackgroundColor)
            imgSubmitBorderColor.setBgColor(submitButtonBorderColor)
            imgSubmitFontColor.setBgColor(submitButtonFontColor)
            imgDocDetectStrokeColor.setBgColor(docDetectStrokeUIColor)
            txtCornerRadius.text = submitButtonCornerRadius.toString()
            switchManualCrop.isChecked = manualCropIsOn
            switchMoreMenu.isChecked = moreMenuIsOn
            switchMoreSettingsMenu.isChecked = moreSettingsMenuIsOn
            switchGallery.isChecked = galleryIsOn
            switchDictate.isChecked = dictateIsOn
            switchEmailCc.isChecked = emailCCIsOn
            txtCcDomain.text = emailCCDomain
            switchRotateDoc.isChecked = rotateDocIsOn
            switchShieldProtection.isChecked = shieldProtectionIsOn
        }
        with(binding.settingsApiCard) {
            switchAutoDeleteProcessing.isChecked = autoDeleteAfterProcessing
            switchBoostMode.isChecked = boostModeIsOn
            switchBoundingBoxes.isChecked = boundingBoxesIsOn
            switchDetectBlurResponse.isChecked = detectBlurResponseIsOn
            switchIsProduction.isChecked = isProduction
            switchConfidenceDetails.isChecked = confidenceDetailsIsOn
            switchParseAddress.isChecked = parseAddressIsOn
            txtExternalId.text = externalId.ifEmpty { getString(R.string.settings_na_value) }
        }
    }

    private fun setUpClickEvents() {
        binding.btnScan.setOnClickListener { setVeryfiSettings() }
        with(binding.settingsGeneralCard) {
            switchLight.onChangeListener { autoLightDetectionIsOn = it }
            switchStitch.onChangeListener { stitchIsOn = it }
            switchSubmitUndetectedDocs.onChangeListener { allowSubmitUndetectedDocsIsOn = it }
            switchSubmitDocumentsCapture.onChangeListener { autoSubmitDocumentOnCapture = it }
            switchBackupDocs.onChangeListener { backupDocsToGallery = it }
            switchStitchedPdf.onChangeListener { returnStitchedPDF = it }
            switchCloseCameraSubmit.onChangeListener { closeCameraOnSubmit = it }
            switchLocationServices.onChangeListener { locationServicesIsOn = it }
            txtOriginalImageSize.setOnClickListener {
                showDialogWithSlider(originalImageMaxSizeInMB ?: 0.0f, 1, txtOriginalImageSize)
            }
        }
        with(binding.settingsImageProcessingCard) {
            switchAutoRotate.onChangeListener { autoRotateIsOn = it }
            switchAutoDocDetection.onChangeListener { autoDocDetectionAndCropIsOn = it }
            switchBlur.onChangeListener { blurDetectionIsOn = it }
            switchSkew.onChangeListener { autoSkewCorrectionIsOn = it }
            switchAutoCropGallery.onChangeListener { autoCropGalleryIsOn = it }
            switchGpu.onChangeListener { gpuIsOn = it }
        }
        with(binding.settingsUiCard) {
            imgPrimaryColor.showDialogWhenClicked(primaryColor, 0)
            imgPrimaryDarkColor.showDialogWhenClicked(primaryDarkColor, 1)
            imgSecondaryColor.showDialogWhenClicked(secondaryColor, 2)
            imgSecondaryDarkColor.showDialogWhenClicked(secondaryDarkColor, 3)
            imgAccentColor.showDialogWhenClicked(accentColor, 4)
            imgAccentDarkColor.showDialogWhenClicked(accentDarkColor, 5)
            imgDetectFillColor.showDialogWhenClicked(docDetectFillUIColor, 6)
            imgSubmitBackgroundColor.showDialogWhenClicked(submitButtonBackgroundColor, 7)
            imgSubmitBorderColor.showDialogWhenClicked(submitButtonBorderColor, 8)
            imgSubmitFontColor.showDialogWhenClicked(submitButtonFontColor, 9)
            imgDocDetectStrokeColor.showDialogWhenClicked(docDetectStrokeUIColor, 10)
            txtCornerRadius.setOnClickListener {
                showDialogWithSlider(submitButtonCornerRadius.toFloat(), 0, txtCornerRadius)
            }
            switchManualCrop.onChangeListener { manualCropIsOn = it }
            switchMoreMenu.onChangeListener { moreMenuIsOn = it }
            switchMoreSettingsMenu.onChangeListener { moreSettingsMenuIsOn = it }
            switchGallery.onChangeListener { galleryIsOn = it }
            switchDictate.onChangeListener { dictateIsOn = it }
            switchEmailCc.onChangeListener { emailCCIsOn = it }
            switchRotateDoc.onChangeListener { rotateDocIsOn = it }
            switchShieldProtection.onChangeListener { shieldProtectionIsOn = it }
            txtCcDomain.setOnClickListener {
                showDialogWithTextField(emailCCDomain, 0, txtCcDomain)
            }
        }
        with(binding.settingsApiCard) {
            switchAutoDeleteProcessing.onChangeListener { autoDeleteAfterProcessing = it }
            switchBoostMode.onChangeListener { boostModeIsOn = it }
            switchBoundingBoxes.onChangeListener { boundingBoxesIsOn = it }
            switchDetectBlurResponse.onChangeListener { detectBlurResponseIsOn = it }
            switchIsProduction.onChangeListener { isProduction = it }
            switchConfidenceDetails.onChangeListener { confidenceDetailsIsOn = it }
            switchParseAddress.onChangeListener { parseAddressIsOn = it }
            txtExternalId.setOnClickListener {
                showDialogWithTextField(externalId, 1, txtExternalId)
            }
        }
    }

    private fun View.showDialog(color: String?, typeColor: Int) {
        val layout = FragmentColorPickerBinding.inflate(layoutInflater)
        val colorPickerView = layout.colorPicker
        colorPickerView.color = Color.parseColor(color)

        MaterialAlertDialogBuilder(context).setView(layout.root)
            .setTitle(getString(R.string.settings_set_color_title))
            .setPositiveButton(getString(R.string.btn_ok)) { dialog, _ ->
                val colorSelected = "#".plus(formatColor(colorPickerView.color))
                when (typeColor) {
                    0 -> primaryColor = colorSelected
                    1 -> primaryDarkColor = colorSelected
                    2 -> secondaryColor = colorSelected
                    3 -> secondaryDarkColor = colorSelected
                    4 -> accentColor = colorSelected
                    5 -> accentDarkColor = colorSelected
                    6 -> docDetectFillUIColor = colorSelected
                    7 -> submitButtonBackgroundColor = colorSelected
                    8 -> submitButtonBorderColor = colorSelected
                    9 -> submitButtonFontColor = colorSelected
                    10 -> docDetectStrokeUIColor = colorSelected
                }
                setBgColor(colorSelected)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.btn_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showDialogWithSlider(value: Float, type: Int, textView: TextView) {
        val layout = FragmentBarSelectionBinding.inflate(layoutInflater)
        val barSliderView = layout.barSlider
        barSliderView.value = value
        val titleDialog = when (type) {
            0 -> {
                barSliderView.valueFrom = 0.0f
                barSliderView.valueTo = 30.0f
                barSliderView.stepSize = 1.0f
                getString(R.string.settings_set_submit_button_corner_radius)
            }
            1 -> {
                barSliderView.valueFrom = 0.0f
                barSliderView.valueTo = 10.0f
                barSliderView.stepSize = 0.5f
                getString(R.string.settings_set_original_image_size)
            }
            else -> ""
        }

        MaterialAlertDialogBuilder(this).setView(layout.root)
            .setTitle(titleDialog)
            .setPositiveButton(getString(R.string.btn_ok)) { dialog, _ ->
                when (type) {
                    0 -> submitButtonCornerRadius = barSliderView.value.toInt()
                    1 -> originalImageMaxSizeInMB = barSliderView.value
                }
                textView.text = barSliderView.value.toString()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.btn_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showDialogWithTextField(value: String, type: Int, textView: TextView) {
        val layout = FragmentEditTextBinding.inflate(layoutInflater)
        val textFieldLayoutView = layout.textFieldLayout
        val textFieldView = layout.textField
        textFieldView.setText(value)
        val titleDialog = when (type) {
            0 -> {
                textFieldLayoutView.hint = getString(R.string.settings_hint_cc_domain)
                getString(R.string.settings_set_email_cc_domain)
            }
            1 -> {
                textFieldLayoutView.hint = getString(R.string.settings_hint_external_id)
                getString(R.string.settings_set_external_id)
            }
            else -> ""
        }

        MaterialAlertDialogBuilder(this).setView(layout.root)
            .setTitle(titleDialog)
            .setPositiveButton(getString(R.string.btn_ok)) { dialog, _ ->
                val text = textFieldView.text.toString()
                when (type) {
                    0 -> emailCCDomain = text
                    1 -> externalId = text
                }
                textView.text = text
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.btn_cancel)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun setVeryfiSettings() {
        veryfiLensSettings.autoLightDetectionIsOn = autoLightDetectionIsOn
        veryfiLensSettings.stitchIsOn = stitchIsOn
        veryfiLensSettings.allowSubmitUndetectedDocsIsOn = allowSubmitUndetectedDocsIsOn
        veryfiLensSettings.autoSubmitDocumentOnCapture = autoSubmitDocumentOnCapture
        veryfiLensSettings.backupDocsToGallery = backupDocsToGallery
        veryfiLensSettings.returnStitchedPDF = returnStitchedPDF
        veryfiLensSettings.closeCameraOnSubmit = closeCameraOnSubmit
        veryfiLensSettings.locationServicesIsOn = locationServicesIsOn
        veryfiLensSettings.originalImageMaxSizeInMB = originalImageMaxSizeInMB
        veryfiLensSettings.autoRotateIsOn = autoRotateIsOn
        veryfiLensSettings.autoDocDetectionAndCropIsOn = autoDocDetectionAndCropIsOn
        veryfiLensSettings.blurDetectionIsOn = blurDetectionIsOn
        veryfiLensSettings.autoSkewCorrectionIsOn = autoSkewCorrectionIsOn
        veryfiLensSettings.autoCropGalleryIsOn = autoCropGalleryIsOn
        veryfiLensSettings.primaryColor = primaryColor
        veryfiLensSettings.primaryDarkColor = primaryDarkColor
        veryfiLensSettings.secondaryColor = secondaryColor
        veryfiLensSettings.secondaryDarkColor = secondaryDarkColor
        veryfiLensSettings.accentColor = accentColor
        veryfiLensSettings.accentDarkColor = accentDarkColor
        veryfiLensSettings.docDetectFillUIColor = docDetectFillUIColor
        veryfiLensSettings.submitButtonBackgroundColor = submitButtonBackgroundColor
        veryfiLensSettings.submitButtonBorderColor = submitButtonBorderColor
        veryfiLensSettings.submitButtonFontColor = submitButtonFontColor
        veryfiLensSettings.docDetectStrokeUIColor = docDetectStrokeUIColor
        veryfiLensSettings.submitButtonCornerRadius = submitButtonCornerRadius
        veryfiLensSettings.manualCropIsOn = manualCropIsOn
        veryfiLensSettings.moreMenuIsOn = moreMenuIsOn
        veryfiLensSettings.moreSettingsMenuIsOn = moreSettingsMenuIsOn
        veryfiLensSettings.galleryIsOn = galleryIsOn
        veryfiLensSettings.dictateIsOn = dictateIsOn
        veryfiLensSettings.emailCCIsOn = emailCCIsOn
        veryfiLensSettings.emailCCDomain = emailCCDomain
        veryfiLensSettings.rotateDocIsOn = rotateDocIsOn
        veryfiLensSettings.shieldProtectionIsOn = shieldProtectionIsOn
        veryfiLensSettings.autoDeleteAfterProcessing = autoDeleteAfterProcessing
        veryfiLensSettings.boostModeIsOn = boostModeIsOn
        veryfiLensSettings.boundingBoxesIsOn = boundingBoxesIsOn
        veryfiLensSettings.detectBlurResponseIsOn = detectBlurResponseIsOn
        veryfiLensSettings.isProduction = isProduction
        veryfiLensSettings.confidenceDetailsIsOn = confidenceDetailsIsOn
        veryfiLensSettings.parseAddressIsOn = parseAddressIsOn
        veryfiLensSettings.gpuIsOn = gpuIsOn
        veryfiLensSettings.externalId = externalId
        veryfiLensSettings.documentTypes = arrayListOf(DocumentType.W2)
        veryfiLensSettings.showDocumentTypes = true

        val veryfiLensCredentials = VeryfiLensCredentials()
        veryfiLensCredentials.clientId = CLIENT_ID
        veryfiLensCredentials.username = AUTH_USERNAME
        veryfiLensCredentials.apiKey = AUTH_APIKEY
        veryfiLensCredentials.url = URL

        VeryfiLens.configure(this.application, veryfiLensCredentials, veryfiLensSettings) {}
        startActivity(Intent(this, LogsActivity::class.java))
    }

    private fun formatColor(color: Int): String {
        return String.format("%08x", color)
    }

    private fun View.setBgColor(color: String?) {
        setBackgroundColor(Color.parseColor(color))
    }

    private fun Switch.onChangeListener(block: (Boolean) -> Unit) {
        setOnCheckedChangeListener { _, isChecked -> block(isChecked) }
    }

    private fun ShapeableImageView.showDialogWhenClicked(color: String?, typeColor: Int) {
        setOnClickListener { it.showDialog(color, typeColor) }
    }

    companion object {
        const val CLIENT_ID = BuildConfig.CLIENT_ID
        const val AUTH_USERNAME = BuildConfig.USERNAME
        const val AUTH_APIKEY = BuildConfig.API_KEY
        const val URL = BuildConfig.URL
    }
}