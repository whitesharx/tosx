#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface TXSettings : NSObject
@property (nonatomic, copy, readonly) NSString *titleText;
@property (nonatomic, copy, readonly) NSString *messageTextFormat;
@property (nonatomic, copy, readonly) NSString *termsOfServiceText;
@property (nonatomic, copy, readonly) NSString *privacyPolicyText;
@property (nonatomic, copy, readonly) NSString *termsOfServiceUrl;
@property (nonatomic, copy, readonly) NSString *privacyPolicyUrl;
@property (nonatomic, copy, readonly) NSString *actionText;

+ (instancetype)settingsWithJSONString:(NSString *)aString error:(NSError **)anError;

- (instancetype)init NS_UNAVAILABLE;

- (instancetype)initWithTitleText:(NSString *)titleText
                messageTextFormat:(NSString *)messageTextFormat
               termsOfServiceText:(NSString *)termsOfServiceText
                privacyPolicyText:(NSString *)privacyPolicyText
                termsOfServiceUrl:(NSString *)termsOfServiceUrl
                 privacyPolicyUrl:(NSString *)privacyPolicyUrl
                       actionText:(NSString *)actionText NS_DESIGNATED_INITIALIZER;

- (NSString *)asJSONStringOrError:(NSError **)anError;
- (NSAttributedString *)renderAttributedMessageOrError:(NSError **)anError;
@end

NS_ASSUME_NONNULL_END
