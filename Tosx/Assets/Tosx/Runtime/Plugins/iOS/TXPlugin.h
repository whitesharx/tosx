#ifndef TX_PLUGIN_H
#define TX_PLUGIN_H

#import <UIKit/UIKit.h>

#import "TXResult.h"
#import "TXSettings.h"

void DisplayAppleImpl(const char* displaySettingsJson);
void DisplayAppleWithControllerImpl(const char* displaySettingsJson, UIViewController* controller);
void UnitySendMessageImpl(const char* resultJson);

#endif /* TX_PLUGIN_H */
