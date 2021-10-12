import AppScreen from './AppScreen';

class SpotifyScreen extends AppScreen {
    constructor () {
        super('');
    }

    async waitForScreenIsShown(isShown = true): Promise<boolean | void> {
        const selector = `UiSelector().textContains("You agree")`
        return await $(`android=${selector}`).waitForDisplayed({
            reverse: !isShown
        });
    }

    async waitForAgreeButtonIsShown(isShown = true): Promise<boolean | void> {
        const selector = `UiSelector().text("AGREE")`
        return await $(`android=${selector}`).waitForDisplayed({
            reverse: !isShown
        });
    }

    async tapAgreeButton(){
        const selector = 'UiSelector().text("AGREE")'
        const agreeButton = await $(`android=${selector}`)
        await agreeButton.click();
    }



}

export default new SpotifyScreen();
