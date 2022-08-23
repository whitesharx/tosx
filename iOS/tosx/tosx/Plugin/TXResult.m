#import "TXResult.h"

NSString* const kAcceptResultType = @"accept";
NSString* const kDismissResultType = @"dismiss";
NSString* const kViewTermsOfUseResultType = @"view_tos";
NSString* const kViewPrivacyPolicyResultType = @"view_pp";

@implementation TXResult
+ (instancetype)resultWithJSONString:(NSString *)aString
                               error:(NSError **)anError {
  NSData *jsonData = [aString dataUsingEncoding:NSUTF8StringEncoding];
  NSDictionary<NSString *, NSString *> *jsonDict = nil;
  
  jsonDict = [NSJSONSerialization JSONObjectWithData:jsonData
                                             options:kNilOptions
                                               error:anError];
  
  return [[self alloc] initWithResult:jsonDict[@"result"]];
}

- (instancetype)initWithResult:(NSString *)aResult {
  self = [super init];
  
  if (self) {
    _result = [aResult copy];
  }
  
  return self;
}

- (NSString *)asJSONStringOrError:(NSError **)anError {
  NSDictionary<NSString*, NSString*> *dict = @{
    @"result": self.result
  };
  
  NSData *jsonData = [NSJSONSerialization dataWithJSONObject:dict
                                                     options:kNilOptions
                                                       error:anError];
  
  return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
}
@end
