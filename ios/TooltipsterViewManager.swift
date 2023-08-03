import React
import EasyTipView

@objc(TooltipsterViewManager)
class TooltipsterViewManager: RCTViewManager {
    
    override func view() -> (TooltipsterView) {
        var preferences = EasyTipView.Preferences()
        preferences.drawing.backgroundColor = .systemBlue
        preferences.animating.dismissOnTap = false
        return TooltipsterView(preferences: preferences)
    }
    
    @objc override static func requiresMainQueueSetup() -> Bool {
        return false
    }
    
    @objc(setPadding:withObj:)
    func setPadding(view: TooltipsterView, obj: Dictionary<String, Int>) {
        view.padding = obj
    }
    
    @objc(setMargin:withObj:)
    func setMargin(view: TooltipsterView, obj: Dictionary<String, Int>) {
        view.margin = obj
    }
}

class TooltipsterView : UIView, EasyTipViewDelegate {
    required init(preferences: EasyTipView.Preferences) {
        self.preferences = preferences
        super.init(frame: .zero)
        
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private var anchorView: UIView?
    private var templateView: UIView?
    private var preferences: EasyTipView.Preferences
    
    @objc var onDismissed: RCTDirectEventBlock?
    @objc var onClick: RCTDirectEventBlock?
    
    @objc var anchorRef: NSNumber? {
        didSet {
            if oldValue != anchorRef {
                anchorView = RCTBridge.current().uiManager.view(forReactTag: anchorRef)
            }
        }
    }
    
    @objc var templateRef: NSNumber? {
        didSet {
            if oldValue != templateRef {
                templateView = RCTBridge.current().uiManager.view(forReactTag: templateRef)
                if visible && anchorView != nil {
                    showTooltip()
                }
            }
            
        }
    }
    
    @objc var useTemplate = false // since visible is set before templateRef, and it doesn't know if there's a template to show at the time it's set, we need this flag to make sure we use template if renderTemplate is provided
    
    @objc var visible: Bool = false {
        didSet {
            if (!useTemplate && visible && anchorView != nil) {
                showTooltip()
            }
        }
    }
    
    @objc var animation: String? {
        didSet {
            if animation == "FADE" {
                preferences.animating.dismissTransform = CGAffineTransform(translationX: 0, y: 0)
                preferences.animating.showInitialTransform = CGAffineTransform(translationX: 0, y: 0)
            } else {
                preferences.animating.dismissTransform = CGAffineTransform(scaleX: 0.1, y: 0.1)
                preferences.animating.showInitialTransform = CGAffineTransform(scaleX: 0, y: 0)
            }
        }
    }
    
    @objc var arrowSize: NSNumber? {
        didSet {
            preferences.drawing.arrowWidth = CGFloat(truncating: arrowSize ?? 10)
            preferences.drawing.arrowHeight = CGFloat(truncating: arrowSize ?? 10)
        }
    }
    
    @objc var position: String? {
        didSet {
            switch position {
            case "top":
                self.preferences.drawing.arrowPosition = .bottom
            case "right":
                self.preferences.drawing.arrowPosition = .left
            case "bottom":
                self.preferences.drawing.arrowPosition = .top
            case "left":
                self.preferences.drawing.arrowPosition = .right
            default:
                self.preferences.drawing.arrowPosition = .any
            }
        }
    }
    
    @objc var text: String?
    
    @objc var textAlign: String? {
        didSet {
            switch textAlign {
            case "left":
                self.preferences.drawing.textAlignment = .left
            case "right":
                self.preferences.drawing.textAlignment = .right
            default:
                self.preferences.drawing.textAlignment = .center
            }
        }
    }
    
    @objc var textLineHeight: NSNumber?
    
    @objc var textColor: NSNumber? {
        didSet {
            preferences.drawing.foregroundColor = textColor != nil ? RCTConvert.uiColor(textColor) : .white
        }
    }
    
    @objc var fontSize: NSNumber? {
        didSet {
            preferences.drawing.font = setupFont()
        }
    }
    
    @objc var fontWeight: String? {
        didSet {
            preferences.drawing.font = setupFont()
        }
    }
    
    @objc var cornerRadius: NSNumber? {
        didSet {
            preferences.drawing.cornerRadius = CGFloat(truncating: cornerRadius ?? 5)
        }
    }
    
    @objc var bgColor: NSNumber? {
        didSet {
            preferences.drawing.backgroundColor = bgColor != nil ? RCTConvert.uiColor(bgColor) : .systemBlue
        }
    }
    
    @objc var maxWidth: NSNumber? {
        didSet {
            preferences.positioning.maxWidth = CGFloat(truncating: maxWidth ?? 200)
        }
    }
    
    @objc var dismissOnClick = false {
        didSet {
            preferences.animating.dismissOnTap = dismissOnClick
        }
    }
    
    var padding: [String: Int]? {
        didSet {
            preferences.positioning.contentInsets.top = CGFloat(padding?["top"] ?? 10)
            preferences.positioning.contentInsets.left = CGFloat(padding?["left"] ?? 10)
            preferences.positioning.contentInsets.bottom = CGFloat(padding?["bottom"] ?? 10)
            preferences.positioning.contentInsets.right = CGFloat(padding?["right"] ?? 10)
        }
    }
    
    var margin: [String: Int]? {
        didSet {
            preferences.positioning.bubbleInsets.top = CGFloat(margin?["top"] ?? 1)
            preferences.positioning.bubbleInsets.left = CGFloat(margin?["left"] ?? 1)
            preferences.positioning.bubbleInsets.bottom = CGFloat(margin?["bottom"] ?? 1)
            preferences.positioning.bubbleInsets.right = CGFloat(margin?["right"] ?? 1)
        }
    }
    
    private func showTooltip() {
        // TODO: shadow doesn't work well in android
        let tipView: EasyTipView
        
        if useTemplate {
            tipView = EasyTipView(contentView: self.templateView ?? UIView(),
                                  preferences: preferences,
                                  delegate: self)
        } else {
            if textLineHeight != nil && Int(truncating: textLineHeight!) > 0 {
                let attrText = attributedStringWithCustomLineHeight(text: text ?? "")
                tipView = EasyTipView(text: attrText,
                                      preferences: preferences,
                                      delegate: self)
            } else {
                tipView = EasyTipView(text: text ?? "",
                                      preferences: preferences,
                                      delegate: self)
            }
        }
        
        let superView = RCTPresentedViewController()!.view
        //                tipView.show(forView: anchorView!, withinSuperview: superView)
        tipView.show(forView: anchorView!)
        TapToDismissEasyTip().set(easyTipView: tipView, superView: superView!)
    }
    
    func easyTipViewDidTap(_ tipView: EasyTipView) {
        if onClick != nil {
            onClick!([AnyHashable : Any]())
        }
    }
    
    func easyTipViewDidDismiss(_ tipView: EasyTipView) {
        if onDismissed != nil {
            onDismissed!([AnyHashable : Any]())
        }
    }
    
    private func setupFont() -> UIFont {
        let size = CGFloat(truncating: fontSize ?? 15)
        let font: UIFont
        switch fontWeight {
            case "BOLD":
                font = UIFont.systemFont(ofSize: size, weight: .bold)
            case "BOLD_ITALIC":
                let fontDescriptor = UIFont.systemFont(ofSize: size, weight: .bold).fontDescriptor.withSymbolicTraits([.traitBold, .traitItalic])
                font = UIFont(descriptor: fontDescriptor!, size: size)
                
            case "ITALIC":
                font = UIFont.italicSystemFont(ofSize: size)
            default:
                font = UIFont.systemFont(ofSize: size, weight: .regular)
        }
        
        return font
    }
    
    private func attributedStringWithCustomLineHeight(text: String) -> NSAttributedString {
        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.lineSpacing = CGFloat(truncating: textLineHeight!) - preferences.drawing.font.lineHeight
        let attributes: [NSAttributedString.Key: Any] = [
            .font: preferences.drawing.font,
            .paragraphStyle: paragraphStyle,
            .foregroundColor: textColor != nil ? RCTConvert.uiColor(textColor)! : .white
        ]
        return NSAttributedString(string: text, attributes: attributes)
    }
}
