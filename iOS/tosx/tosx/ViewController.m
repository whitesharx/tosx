#import "ViewController.h"
#import "Plugin/TXPlugin.h"

@interface ViewController ()
@end

@implementation ViewController
- (void)viewDidLoad {
  [super viewDidLoad];
}

- (void)viewDidAppear:(BOOL)animated {
  [super viewDidAppear:animated];
  
  NSString* title = @"Warning!";
  NSString* message = @"By tapping \"OK\" you accept our {_tos_var} and confirm that you've read {_pp_var}.";
  NSString* tosText = @"Terms of Service";
  NSString* ppText = @"Privacy Policy";
  NSString* tosUrl = @"https://say.games/terms-of-use";
  NSString* ppUrl = @"https://say.games/privacy-policy";
  
  TXSettings* settings = [[TXSettings alloc] initWithTitleText:title
                                             messageTextFormat:message
                                            termsOfServiceText:tosText
                                             privacyPolicyText:ppText
                                             termsOfServiceUrl:tosUrl
                                              privacyPolicyUrl:ppUrl
                                                    actionText:@"Ok"];
  
  NSString* settingsJson = [settings asJSONStringOrError:nil];
  DisplayAppleWithControllerImpl([settingsJson cStringUsingEncoding:NSUTF8StringEncoding], self);
}

- (void)displayAlertWithCustomTextView {
    UIAlertController* alert = nil;
    alert = [UIAlertController alertControllerWithTitle:@"Warning!"
                                                message:nil
                                         preferredStyle:UIAlertControllerStyleAlert];
  
  UIAlertAction* action = nil;
  action = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault
                                         handler:^(UIAlertAction * action) {}];
  
  [alert addAction:action];
  
  UITextView* textView = [[UITextView alloc] init];
  textView.text = @"this is message";
  textView.autoresizingMask = (UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight);
 
  UIViewController* viewController = [[UIViewController alloc] init];
  
  textView.frame = viewController.view.frame;
  [viewController.view addSubview:textView];
  
  [alert setValue:viewController forKey:@"contentViewController"];

  [self presentViewController:alert animated:YES completion:nil];
}


- (void)didTapOnText:(UITapGestureRecognizer *)sender {
  
}
@end
