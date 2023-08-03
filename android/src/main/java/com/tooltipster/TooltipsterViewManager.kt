package com.tooltipster

import android.graphics.Color
import android.view.View
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

// because renderTemplate is not supported for now so we can use SimpleViewManager
// otherwise we need to use ViewGroupManager
class TooltipsterViewManager : SimpleViewManager<TooltipsterView>() {
  companion object {
    const val DEFAULT_BACKGROUND_COLOR = "#1c7bf6"
  }

  override fun getName() = "TooltipsterView"

  override fun createViewInstance(reactContext: ThemedReactContext): TooltipsterView {
    return TooltipsterView(reactContext)
  }

  // setup allowed callback props
  override fun getExportedCustomBubblingEventTypeConstants(): MutableMap<String, Any> {
    return mutableMapOf(
      "onDismissed" to mapOf("phasedRegistrationNames" to mapOf("bubbled" to "onDismissed")),
      "onClick" to mapOf("phasedRegistrationNames" to mapOf("bubbled" to "onClick"))
    )
  }

  @ReactProp(name = "onDismissed")
  fun setOnDismissed(view: TooltipsterView, value: Boolean) {
    view.setOnDismissed(value)
  }

  @ReactProp(name = "onClick")
  fun setOnClick(view: TooltipsterView, value: Boolean) {
    view.setOnClick(value)
  }

  @ReactProp(name = "anchorRef", defaultInt = View.NO_ID)
  fun setAnchorRef(view: TooltipsterView, anchorRef: Int) {
    view.setAnchorRef(anchorRef)
  }

//  @ReactProp(name = "templateRef", defaultInt = View.NO_ID)
//  fun setTemplateRef(view: TooltipsterView, templateRef: Int) {
//    view.setTemplateRef(templateRef)
//  }
//
//  @ReactProp(name = "useTemplate", defaultBoolean = false)
//  fun setUseTemplate(view: TooltipsterView, useTemplate: Boolean) {
//    view.setUseTemplate(useTemplate ?: false)
//  }

  @ReactProp(name = "visible", defaultBoolean = false)
  fun setVisible(view: TooltipsterView, visible: Boolean) {
    view.setVisible(visible)
  }

  @ReactProp(name = "animation")
  fun setAnimation(view: TooltipsterView, value: String?) {
    view.setAnimation(value)
  }

  @ReactProp(name = "arrowSize", defaultInt = 5)
  fun setArrowSize(view: TooltipsterView, value: Int) {
    view.setArrowSize(value)
  }

  @ReactProp(name = "arrowPositionRules")
  fun setArrowPositionRules(view: TooltipsterView, value: String?) {
    view.setArrowPositionRules(value)
  }

  @ReactProp(name = "position")
  fun setPosition(view: TooltipsterView, value: String?) {
    view.setPosition(value)
  }

  @ReactProp(name = "text")
  fun setText(view: TooltipsterView, value: String?) {
    view.setText(value)
  }

  @ReactProp(name = "textAlign")
  fun setTextAlign(view: TooltipsterView, value: String?) {
    view.setTextAlign(value)
  }

  @ReactProp(name = "textLineHeight")
  fun setTextLineHeight(view: TooltipsterView, value: Int?) {
    view.setTextLineHeight(value)
  }

  @ReactProp(name = "textColor", defaultInt = Color.WHITE)
  fun setTextColor(view: TooltipsterView, value: Int) {
    view.setTextColor(value)
  }

  @ReactProp(name = "fontSize", defaultInt = 12)
  fun setFontSize(view: TooltipsterView, value: Int) {
    view.setFontSize(value)
  }

  @ReactProp(name = "fontWeight")
  fun setFontWeight(view: TooltipsterView, value: String) {
    view.setFontWeight(value)
  }

  @ReactProp(name = "cornerRadius", defaultFloat = 5f)
  fun setCornerRadius(view: TooltipsterView, value: Float) {
    view.setCornerRadius(value)
  }

  @ReactProp(name = "bgColor")
  fun setBgColor(view: TooltipsterView, value: Int?) {
    view.setBgColor(value ?: Color.parseColor(DEFAULT_BACKGROUND_COLOR))
  }

  @ReactProp(name = "maxWidth")
  fun setMaxWidth(view: TooltipsterView, value: Int?) {
    view.setMaxWidth(value ?: displaySize.x)
  }

  @ReactProp(name = "dismissOnClick", defaultBoolean = false)
  fun setDismissOnClick(view: TooltipsterView, value: Boolean) {
    view.setDismissOnClick(value)
  }

  @ReactProp(name = "padding")
  fun setPadding(view: TooltipsterView, value: ReadableMap?) {
    view.setPadding(value)
  }

  @ReactProp(name = "margin")
  fun setMargin(view: TooltipsterView, value: ReadableMap?) {
    view.setMargin(value)
  }
}
