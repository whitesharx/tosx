#import "ViewController.h"

#import "Plugin/TXResult.h"

@interface ViewController ()
@end

@implementation ViewController
- (void)viewDidLoad {
  [super viewDidLoad];
}

- (void)viewDidAppear:(BOOL)animated {
  [super viewDidAppear:animated];
  
//  NSLog(@"viewDidAppeer");
//
//  NSMutableAttributedString* str = nil;
//  str = [[NSMutableAttributedString alloc] initWithString:@"Google"];
//
//  [str addAttribute:NSLinkAttributeName
//              value: @"http://www.google.com"
//              range: NSMakeRange(0, str.length)];
//
//
//  UIAlertController* alert = nil;
//  alert = [UIAlertController alertControllerWithTitle:@"Warning!"
//                                              message:@"This is an alert."
//                                       preferredStyle:UIAlertControllerStyleAlert];
//
//  [alert setValue:str forKey:@"attributedMessage"];
//
//  UIAlertAction* action = nil;
//  action = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault
//                                         handler:^(UIAlertAction * action) {}];
//
//  UITapGestureRecognizer* recognizer = nil;
//  recognizer = [recognizer initWithTarget:self action:@selector(didTapOnText:)];
//
//
//
//  [alert addAction:action];
//
//  [self presentViewController:alert animated:YES completion:nil];
  
 //  [self displayAlertWithCustomTextView];
  
  [self runSomeTests];
}

- (void)runSomeTests {
  NSError *error = nil;
  TXResult *result = [[TXResult alloc] initWithResult:kAcceptResultType];
  
  NSLog(@"resultObject.accept: %@", result.result);
  
  NSString *resultJson = [result asJSONStringOrError:&error];
  
  NSLog(@"resultJson: %@", resultJson);
  
  NSString *invalidJson = @"invalid json";
  
  TXResult *newResult = [TXResult resultWithJSONString:resultJson error:&error];
  
  if (error) {
    NSLog(@"error: %@", error);
    return;
  }
  
  NSLog(@"parsed-result: %@", newResult.result);
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
