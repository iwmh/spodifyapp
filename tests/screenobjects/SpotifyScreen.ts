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

}

export default new SpotifyScreen();
