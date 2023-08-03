import React from 'react';
import { useState, type ReactNode, useEffect, useRef } from 'react';
import {
  processColor,
  type ProcessedColorValue,
  Dimensions,
  View,
  findNodeHandle,
  requireNativeComponent,
  UIManager,
  Platform,
  type ViewStyle,
} from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-tooltipster' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

type DIRECTION = 'top' | 'right' | 'bottom' | 'left';

type TooltipsterProps = {
  animation?: 'FADE' | 'SCALE';
  arrowSize?: number;
  position?: DIRECTION;
  text?: string;
  textAlign?: 'left' | 'right' | 'center';
  textLineHeight?: number;
  textColor?: string;
  fontSize?: number;
  fontWeight?: 'BOLD' | 'BOLD_ITALIC' | 'ITALIC' | 'NORMAL';
  /**
   * Android only
   */
  arrowPositionRules?: 'ALIGN_BUBBLE' | 'ALIGN_ANCHOR';
  cornerRadius?: number;
  bgColor?: string;
  maxWidth?: number;
  padding?: { [key in DIRECTION]?: number };
  margin?: { [key in DIRECTION]?: number };
  dismissOnClick?: boolean;
  children: ReactNode;
  onDismissed?: () => void;
  onClick?: () => void;
  renderTemplate?: () => ReactNode;
};

type TooltipsterNativeRef = {
  setNativeProps(props: {
    anchorRef?: null | number;
    templateRef?: null | number;
  }): void;
};

type TooltipsterNativeProps = Omit<
  TooltipsterProps,
  'textColor' | 'bgColor'
> & {
  visible?: boolean;
  ref?: React.RefObject<TooltipsterNativeRef>;
  style?: ViewStyle;
  textColor?: ProcessedColorValue | null;
  bgColor?: ProcessedColorValue | null;
  useTemplate?: boolean;
};

const ComponentName = 'TooltipsterView';

export const TooltipsterView =
  UIManager.getViewManagerConfig(ComponentName) != null
    ? requireNativeComponent<TooltipsterNativeProps>(ComponentName)
    : () => {
        throw new Error(LINKING_ERROR);
      };

const Tooltipster = (props: TooltipsterProps) => {
  const [visible, setVisible] = useState<boolean>(false);
  const {
    children,
    animation,
    arrowSize,
    arrowPositionRules,
    position,
    text,
    textAlign,
    textLineHeight,
    textColor,
    fontSize,
    fontWeight,
    cornerRadius,
    bgColor,
    maxWidth,
    padding,
    margin,
    dismissOnClick,
    renderTemplate,
    onDismissed,
    onClick,
  } = props;

  const anchorRef = useRef<View>(null);
  const templateRef = useRef<View>(null);
  const tooltipsterRef = useRef<TooltipsterNativeRef>(null);

  useEffect(() => {
    tooltipsterRef.current!.setNativeProps({
      anchorRef: findNodeHandle(anchorRef.current),
    });
  }, []);

  const onTemplateReady = () => {
    tooltipsterRef.current!.setNativeProps({
      templateRef: findNodeHandle(templateRef.current),
    });
  };

  const windowWidth = Dimensions.get('window').width;
  const shouldRenderTemplate = Platform.OS === 'ios' || Platform.OS === 'macos';

  // In order to support custom template (current only support iOS), we have to add all these position relative/absolute, opacity: 0...
  return (
    <View style={{ position: 'relative' }}>
      <View
        style={{
          position: 'absolute', // position absolute doesn't work for TooltipsterView, that's why we need a wrapper View
        }}
      >
        <TooltipsterView
          style={{ opacity: 0 }} // because when calling setNativeProps to native, it may take sometime and may cause "blink" effect, therefore we make this opacity=0 always, (can reproduce by put a delay before showing tooltip in native code)
          visible={visible}
          ref={tooltipsterRef}
          onDismissed={() => {
            setVisible(false);
            onDismissed && onDismissed();
          }}
          onClick={() => {
            onClick && onClick();
          }}
          animation={animation}
          arrowSize={arrowSize}
          arrowPositionRules={arrowPositionRules}
          position={position}
          text={text}
          textAlign={textAlign}
          textLineHeight={textLineHeight}
          textColor={processColor(textColor)}
          fontSize={fontSize}
          fontWeight={fontWeight}
          cornerRadius={cornerRadius}
          bgColor={processColor(bgColor)}
          maxWidth={
            maxWidth
              ? maxWidth
              : Platform.OS === 'ios'
              ? windowWidth - 40
              : windowWidth
          }
          padding={padding}
          margin={margin}
          dismissOnClick={
            dismissOnClick === null || dismissOnClick === undefined
              ? false
              : !!dismissOnClick
          }
          useTemplate={!!renderTemplate && shouldRenderTemplate}
        >
          {visible && renderTemplate && shouldRenderTemplate && (
            <View
              ref={templateRef}
              onLayout={() => {
                onTemplateReady();
              }}
            >
              {/* @ts-ignore */}
              {renderTemplate()}
            </View>
          )}
        </TooltipsterView>
      </View>

      {/* on iOS it'll take a bit time to really close the tooltip, so before showing a new one we need to check if the current on still visible */}
      <View
        ref={anchorRef}
        onTouchStart={() => {
          !visible && setVisible(true);
        }}
      >
        {/* @ts-ignore */}
        {children}
      </View>
    </View>
  );
};

export default Tooltipster;
