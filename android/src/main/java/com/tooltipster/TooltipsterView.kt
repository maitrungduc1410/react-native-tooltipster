package com.tooltipster

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.UIManagerModule
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.facebook.react.views.view.ReactViewGroup
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation

class TooltipsterView (private val mContext: ReactContext): ReactViewGroup(mContext) {
  private var anchorView: View? = null
  private var anchorRef: Int? = null
//  private var templateView: View? = null
//  private var templateRef: Int? = null
//  private var useTemplate = false
  private var builder: Balloon.Builder? = null
  var hideCallback = false
  var clickCallback = false
  private var position: String? = null

  init {
    val lifecycleOwner = mContext.currentActivity as LifecycleOwner?

    builder = Balloon.Builder(mContext)
      .setCornerRadius(5f)
      .setBackgroundColor(Color.parseColor(TooltipsterViewManager.DEFAULT_BACKGROUND_COLOR))
      .setPadding(10)
      .setMargin(1)
      .setArrowSize(10)
      .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
      .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
      .setOnBalloonClickListener {
        if (clickCallback) {
          mContext.getJSModule(RCTEventEmitter::class.java)
            .receiveEvent(this.id, "onClick", null)
        }
      }
      .setOnBalloonDismissListener {
        if (hideCallback) {
          mContext.getJSModule(RCTEventEmitter::class.java)
            .receiveEvent(this.id, "onDismissed", null)
        }
      }
      .setLifecycleOwner(lifecycleOwner)
  }

  fun setOnDismissed(value: Boolean) {
    this.hideCallback = value
  }

  fun setOnClick(value: Boolean) {
    this.clickCallback = value
  }

  fun setAnchorRef(anchorRef: Int) {
    if (this.anchorRef == anchorRef) {
      return
    }

    this.anchorRef = anchorRef

    val uiManager: UIManagerModule? = mContext.getNativeModule(
      UIManagerModule::class.java)
    if (uiManager != null) {
      anchorView = uiManager.resolveView(anchorRef)
    }
  }

//  fun setTemplateRef(templateRef: Int) {
//    if (this.templateRef == templateRef) {
//      return
//    }
//
//    this.templateRef = templateRef
//
//    val uiManager: UIManagerModule? = mContext.getNativeModule(UIManagerModule::class.java)
//    if (uiManager != null) {
//      templateView = uiManager.resolveView(templateRef)
//
//      if (anchorView != null) {
//        showTooltip()
//      }
//    }
//  }
//
//  fun setUseTemplate(useTemplate: Boolean) {
//    this.useTemplate = useTemplate
//  }

  fun setVisible(visible: Boolean) {
    if (visible && anchorView != null) {
      showTooltip()
    }
  }

  fun setAnimation(value: String?) {
    when (value) {
      "FADE" -> builder!!.setBalloonAnimation(BalloonAnimation.FADE)
      else -> {
        builder!!.setBalloonAnimation(BalloonAnimation.OVERSHOOT)
      }
    }
  }

  fun setArrowSize(value: Int) {
    builder!!.setArrowSize(value)
  }

  fun setArrowPositionRules(value: String?) {
    when (value) {
      "ALIGN_BUBBLE" -> builder!!.setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
      else -> {
        builder!!.setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
      }
    }
  }

  fun setPosition(value: String?) {
    when (value) {
      "top" -> builder!!.setArrowOrientation(ArrowOrientation.BOTTOM)
      "right" -> builder!!.setArrowOrientation(ArrowOrientation.START)
      "bottom" -> builder!!.setArrowOrientation(ArrowOrientation.TOP)
      "left" -> builder!!.setArrowOrientation(ArrowOrientation.END)
      else -> {
        builder!!.setArrowOrientation(ArrowOrientation.BOTTOM)
      }
    }

    position = value ?: "bottom"
  }

  fun setText(value: String?) {
    builder!!.setText(value ?: "")
  }

  fun setTextAlign(value: String?) {
    when (value) {
      "left" -> builder!!.setTextGravity(Gravity.LEFT)
      "right" -> builder!!.setTextGravity(Gravity.RIGHT)
      else -> {
        builder!!.setTextGravity(Gravity.CENTER)
      }
    }
  }

  fun setTextLineHeight(value: Int?) {
    if (value != null && value > 0) {
      builder!!.setTextLineSpacing(value.toFloat())
    }
  }

  fun setTextColor(value: Int) {
    builder!!.setTextColor(value)
  }

  fun setFontSize(value: Int?) {
    builder!!.setTextSize(value?.toFloat() ?: 12f)
  }

  fun setFontWeight(value: String?) {
    when (value) {
      "BOLD" -> builder!!.setTextTypeface(Typeface.BOLD)
      "BOLD_ITALIC" -> builder!!.setTextTypeface(Typeface.BOLD_ITALIC)
      "ITALIC" -> builder!!.setTextTypeface(Typeface.ITALIC)
      else -> {
        builder!!.setTextTypeface(Typeface.NORMAL)
      }
    }
  }

  fun setCornerRadius(value: Float) {
    builder!!.setCornerRadius(value)
  }

  fun setBgColor(value: Int) {
    builder!!.setBackgroundColor(value)
  }

  fun setMaxWidth(value: Int) {
    builder!!.setMaxWidth(value)
  }

  fun setDismissOnClick(value: Boolean) {
    builder!!.setDismissWhenClicked(value)
  }

  fun setPadding(value: ReadableMap?) {
    if (value != null) {
      // readable .get... will immediately throw error if null that's why we need to have if check for each direction
      if (value.hasKey("top")) {
        builder!!.setPaddingTop(value.getInt("top"))
      } else {
        builder!!.setPaddingTop(10)
      }

      if (value.hasKey("right")) {
        builder!!.setPaddingRight(value.getInt("right"))
      } else {
        builder!!.setPaddingRight(10)
      }

      if (value.hasKey("bottom")) {
        builder!!.setPaddingBottom(value.getInt("bottom"))
      } else {
        builder!!.setPaddingBottom(10)
      }

      if (value.hasKey("left")) {
        builder!!.setPaddingLeft(value.getInt("left"))
      } else {
        builder!!.setPaddingLeft(10)
      }
    }
  }

  fun setMargin(value: ReadableMap?) {
    if (value != null) {
      // readable.get... will immediately throw error if null that's why we need to have if check for each direction
      if (value.hasKey("top")) {
        builder!!.setMarginTop(value.getInt("top"))
      } else {
        builder!!.setMarginTop(1)
      }

      if (value.hasKey("right")) {
        builder!!.setMarginRight(value.getInt("right"))
      } else {
        builder!!.setMarginRight(1)
      }

      if (value.hasKey("bottom")) {
        builder!!.setMarginBottom(value.getInt("bottom"))
      } else {
        builder!!.setMarginBottom(1)
      }

      if (value.hasKey("left")) {
        builder!!.setMarginLeft(value.getInt("left"))
      } else {
        builder!!.setMarginLeft(1)
      }
    }
  }


  private fun showTooltip() {
    // At the moment, there's no way to support custom layout which takes in a React component
    // we only <Text> it works"
    // but if the templateView is a <View> (with/without children) inside, it'll show error "A catalyst view must have an explicit width and height given to it. This should normally happen as part of the standard catalyst UI framework."
    // this is because in Balloon code, it calls "this.binding.root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)" to measure layout size
    // even if we try to set width/height for the <View> explicitly, with collapsable={false}
    // even if we try to remove templateView from its parent (using native code) or trying to show the balloon in delay (setTimeout)
    // tried nesting templateView in a <Text> and again doesn't work

    // (templateView as ReactViewGroup).measure(templateView!!.width, templateView!!.height)
    // val balloon = if (useTemplate)  builder!!.setLayout(templateView ?: View(this.anchorView!!.context)).build() else builder!!.build()
    val balloon = builder!!.build()
    when (position) {
      "top" -> balloon.showAlignTop(anchorView!!)
      "right" -> balloon.showAlignRight(anchorView!!)
      "bottom" -> balloon.showAlignBottom(anchorView!!)
      "left" -> balloon.showAlignLeft(anchorView!!)
      else -> {
        balloon.showAlignBottom(anchorView!!)
      }
    }
  }
}
