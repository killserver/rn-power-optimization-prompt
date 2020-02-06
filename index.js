
import { NativeModules, Platform } from 'react-native';

const { PowerOptimizationPrompt } = NativeModules;

export default config => {
    if (Platform.OS === 'android') {
        PowerOptimizationPrompt.alertIfCompatibleDevice(config.title, config.text, config.doNotShowAgainText, config.positiveText, config.negativeText);
    }
  };
