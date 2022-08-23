#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

extern NSString* const kAcceptResultType;
extern NSString* const kDismissResultType;
extern NSString* const kViewTermsOfUseResultType;
extern NSString* const kViewPrivacyPolicyResultType;

@interface TXResult : NSObject
@property (nonatomic, copy, readonly) NSString* result;

+ (instancetype)resultWithJSONString:(NSString *)aString error:(NSError **)anError;

- (instancetype)init NS_UNAVAILABLE;
- (instancetype)initWithResult:(NSString *)aResult NS_DESIGNATED_INITIALIZER;
- (NSString *)asJSONStringOrError:(NSError **)anError;
@end

NS_ASSUME_NONNULL_END
