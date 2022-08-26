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
  
  UIAlertController* alert = nil;
  alert = [UIAlertController alertControllerWithTitle:@"Warning!"
                                              message:@"This is examle alert to check that syle is intact. Just close it..."
                                       preferredStyle:UIAlertControllerStyleAlert];

  UIAlertAction* action = nil;
  action = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault
                                         handler:^(UIAlertAction * action) {
    [self dismissViewControllerAnimated:YES completion:^{
      [self displayTosxAlert];
    }];
  }];

  [alert addAction:action];
  
  [self presentViewController:alert animated:YES completion:nil];
}

- (void)displayTosxAlert {
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
@end
