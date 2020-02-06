
# rn-power-optimization-prompt

This plugin should allow your React Native application to show a dialog to users prompting them to "exclude" your application from power optimization settings such as Huawei's "Protected Apps" system or OPPO's "Data Saving" system. By accepting the prompt the user is taken directly to the relevant settings screen where they can "exclude" their application.

This only concerns a few Android phones from specific manufacturers.
These are the currently supported manufacturers:
- HUAWEI
- OPPO
- ASUS
- XIAOMI
- LETV
- VIVO
- SAMSUNG
- HTC

There is no guarantee that this plugin will work with all versions of all phones made by these manufacturers.

# Notes & limitations
This plugin cannot detect if the user has already "excluded" your application. The prompt dialog will be shown under the following conditions:

1. The device the application is running on is a relevant device concerned by this module
   AND
2. "Do not show again" checkbox hasn't been checked yet

It is not necessary to check for `Platform.OS === 'android'` as the module wrapper does this for you already

## Getting started

npm users:
`$ npm install rn-power-optimization-prompt`

yarn users:
`$ yarn add rn-power-optimization-prompt`

### Mostly automatic installation

`$ react-native link rn-power-optimization-prompt`

Using react native link should be enough most of the time. In case there is any trouble with this plugin after linking, please check the manual installation guide and make sure everything is in order.

### Manual installation

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.killserver.PowerOptimizationPromptPackage;` to the imports at the top of the file
  - Add `new PowerOptimizationPromptPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':rn-power-optimization-prompt'
  	project(':rn-power-optimization-prompt').projectDir = new File(rootProject.projectDir, 	'../node_modules/rn-power-optimization-prompt/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':rn-power-optimization-prompt')
  	```


## Usage
```javascript
import PowerOptimizationPrompt from 'rn-power-optimization-prompt';

const config = {
  title: "Power Optimization", //The title of the prompt dialog
  text: "Please check that your app is excluded from any power saving features", //The text of the prompt dialog
  doNotShowAgainText: "Do not show again", //The text to be shown next to the "don't show again" checkbox
  positiveText: "Power Optimization", /*The positive button text. When clicked, this button will take the user to the screen where they can "exclude" your application from any power optimization settings, depending on the device*/
  negativeText: "Cancel" //The negative button text
};

PowerOptimizationPrompt(config);
```
  
