#import <UIKit/UIKit.h>
#import "TXSettings.h"

NSString* const kTermsOfServicePlaceholder = @"{_tos_var}";
NSString* const kPrivacyPolicyPlaceholder = @"{_pp_var}";

NSString* const kUrlFormat = @"<a href='%@'>%@</a>";

@implementation TXSettings
+ (instancetype)settingsWithJSONString:(NSString *)aString
                                 error:(NSError **)anError {
  NSData *jsonData = [aString dataUsingEncoding:NSUTF8StringEncoding];
  NSDictionary<NSString *, NSString *> *jsonDict = nil;
  
  jsonDict = [NSJSONSerialization JSONObjectWithData:jsonData
                                             options:kNilOptions
                                               error:anError];
  
  return [[self alloc] initWithTitleText:jsonDict[@"titleText"]
                       messageTextFormat:jsonDict[@"messageTextFormat"]
                      termsOfServiceText:jsonDict[@"termsOfServiceText"]
                       privacyPolicyText:jsonDict[@"privacyPolicyText"]
                       termsOfServiceUrl:jsonDict[@"termsOfServiceUrl"]
                        privacyPolicyUrl:jsonDict[@"privacyPolicyUrl"]
                              actionText:jsonDict[@"actionText"]];
}

- (instancetype)initWithTitleText:(NSString *)titleText
                messageTextFormat:(NSString *)messageTextFormat
               termsOfServiceText:(NSString *)termsOfServiceText
                privacyPolicyText:(NSString *)privacyPolicyText
                termsOfServiceUrl:(NSString *)termsOfServiceUrl
                 privacyPolicyUrl:(NSString *)privacyPolicyUrl
                       actionText:(NSString *)actionText {
  self = [super init];
  
  if (self) {
    _titleText = [titleText copy];
    _messageTextFormat = [messageTextFormat copy];
    _termsOfServiceText = [termsOfServiceText copy];
    _privacyPolicyText = [privacyPolicyText copy];
    _termsOfServiceUrl = [termsOfServiceUrl copy];
    _privacyPolicyUrl = [privacyPolicyUrl copy];
    _actionText = [actionText copy];
  }
  
  return self;
}

- (NSString *)asJSONStringOrError:(NSError **)anError {
  NSDictionary<NSString*, NSString*> *dict = @{
    @"titleText": self.titleText,
    @"messageTextFormat": self.messageTextFormat,
    @"termsOfServiceText": self.termsOfServiceText,
    @"privacyPolicyText": self.privacyPolicyText,
    @"termsOfServiceUrl": self.termsOfServiceUrl,
    @"privacyPolicyUrl": self.privacyPolicyUrl,
    @"actionText": self.actionText
  };
  
  NSData *jsonData = [NSJSONSerialization dataWithJSONObject:dict
                                                     options:kNilOptions
                                                       error:anError];
  
  return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
}

- (NSAttributedString *)renderAttributedMessageOrError:(NSError **)anError {
  NSString* tosText = [NSString stringWithFormat:kUrlFormat, self.termsOfServiceUrl, self.termsOfServiceText];
  NSString* ppText = [NSString stringWithFormat:kUrlFormat, self.privacyPolicyUrl, self.privacyPolicyText];
  
  NSString* resultHtml = [[self.messageTextFormat stringByReplacingOccurrencesOfString:kTermsOfServicePlaceholder withString:tosText]
                                                  stringByReplacingOccurrencesOfString:kPrivacyPolicyPlaceholder withString:ppText];
  
  NSData* htmlData = [resultHtml dataUsingEncoding:NSUTF8StringEncoding];
  NSDictionary<NSAttributedStringDocumentReadingOptionKey, id> *opts = nil;
  
  opts = @{
    NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType,
    NSCharacterEncodingDocumentAttribute: @(NSUTF8StringEncoding)
  };
  
  return [[NSAttributedString alloc] initWithData:htmlData
                                          options:opts
                               documentAttributes:nil
                                            error:nil];
}
@end