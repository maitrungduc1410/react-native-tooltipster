#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(TooltipsterViewManager, RCTViewManager)

RCT_EXPORT_VIEW_PROPERTY(onDismissed, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onClick, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(anchorRef, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(templateRef, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(useTemplate, BOOL)
RCT_EXPORT_VIEW_PROPERTY(visible, BOOL)
RCT_EXPORT_VIEW_PROPERTY(animation, NSString)
RCT_EXPORT_VIEW_PROPERTY(arrowSize, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(position, NSString)
RCT_EXPORT_VIEW_PROPERTY(text, NSString)
RCT_EXPORT_VIEW_PROPERTY(textAlign, NSString)
RCT_EXPORT_VIEW_PROPERTY(textLineHeight, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(textColor, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(fontSize, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(fontWeight, NSString)
RCT_EXPORT_VIEW_PROPERTY(cornerRadius, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(bgColor, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(maxWidth, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(dismissOnClick, BOOL)
RCT_CUSTOM_VIEW_PROPERTY(padding, NSDictionary, TooltipsterViewManager) {
    [self performSelector:@selector(setPadding:withObj:) withObject:view withObject:json];
}RCT_CUSTOM_VIEW_PROPERTY(margin, NSDictionary, TooltipsterViewManager) {
    [self performSelector:@selector(setMargin:withObj:) withObject:view withObject:json];
}


@end
