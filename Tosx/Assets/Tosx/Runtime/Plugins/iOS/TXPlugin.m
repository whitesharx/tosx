#import "TXPlugin.h"

#ifdef UNITY_DISABLED
extern UIViewController *UnityGetGLViewController(void);
#endif

void DisplayAppleImpl(const char* displaySettingsJson) {
#ifndef UNITY_DISABLED
  DisplayAppleWithControllerImpl(displaySettingsJson, UnityGetGLViewController());
#endif
}

void DisplayAppleWithControllerImpl(const char* displaySettingsJson, UIViewController* controller) {
  NSError* error = nil;
  NSString* jsonString = [NSString stringWithUTF8String:displaySettingsJson];
  TXSettings* settings = [TXSettings settingsWithJSONString:jsonString error:&error];
  
  if (error) {
    NSLog(@"[Tosx]: Args error... %@", error);
    return;
  }
  
  NSAttributedString* message = [settings renderAttributedMessageOrError:&error];
  
  if (error) {
    NSLog(@"[Tosx]: Message render error... %@", error);
    return;
  }
  
  NSLog(@"[Tosx]: Rendered message... %@", message);
  
  UIAlertController* alert = nil;
  alert = [UIAlertController alertControllerWithTitle:settings.titleText
                                              message:nil
                                       preferredStyle:UIAlertControllerStyleAlert];

  UIAlertAction* action = nil;
  action = [UIAlertAction actionWithTitle:settings.actionText
                                    style:UIAlertActionStyleDefault
                                  handler:^(UIAlertAction* action) {
    TXResult* result = [[TXResult alloc] initWithResult:kAcceptResultType];
    NSString* resultJson = [result asJSONStringOrError:nil];

    UnitySendMessageImpl([resultJson cStringUsingEncoding:NSUTF8StringEncoding]);
  }];

  [alert addAction:action];
  
  UITextView* textView = [[UITextView alloc] init];
  textView.attributedText = message;
  textView.autoresizingMask = (UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight);
  textView.scrollEnabled = false;
  textView.userInteractionEnabled = true;
  textView.backgroundColor = [UIColor clearColor];
  textView.dataDetectorTypes = UIDataDetectorTypeLink;
  textView.editable = false;
  textView.selectable = true;
  textView.textAlignment = NSTextAlignmentCenter;
  textView.font = [UIFont systemFontOfSize:13.0f];
  
  if ([[UIDevice currentDevice] systemVersion].floatValue >= 13.0) {
    textView.textColor = [UIColor labelColor];
  } else {
    textView.textColor = [UIColor blackColor];
  }
  
  UIViewController* viewController = [[UIViewController alloc] init];
  textView.frame = viewController.view.frame;
  
  [viewController.view addSubview:textView];
  [alert setValue:viewController forKey:@"contentViewController"];
  [controller presentViewController:alert animated:YES completion:nil];
}

void UnitySendMessageImpl(const char* resultJson) {
  NSLog(@"[Tosx]: UnitySendMessageImpl(%@)", [NSString stringWithUTF8String:resultJson]);
  
#ifndef UNITY_DISABLED
  UnitySendMessage("TosxBridgeObject", "HandleNativeAppleMessage", resultJson);
#endif
}
