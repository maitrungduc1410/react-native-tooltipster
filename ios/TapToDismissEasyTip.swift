import EasyTipView

internal class TapToDismissEasyTip: UITapGestureRecognizer {
    var easyTipView: EasyTipView? = nil
    var superView: UIView? = nil
    
    func set(easyTipView: EasyTipView, superView: UIView) {
        self.easyTipView = easyTipView
        self.superView = superView
        superView.addGestureRecognizer(self)
        self.addTarget(self, action: #selector(self.dismiss))
    }
    
    func unset() {
        self.superView?.removeGestureRecognizer(self)
    }
    
    @objc func dismiss()  {
        easyTipView?.dismiss(withCompletion: {
            self.superView?.removeGestureRecognizer(self)
        })
    }
}
