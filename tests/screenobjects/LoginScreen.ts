import AppScreen from './AppScreen';

class LoginScreen extends AppScreen {
    constructor () {
        super('auth');
    }

    async tapAuthButton(){
        const selector = 'UiSelector().text("auth")'
        const authButton = await $(`android=${selector}`)
        await authButton.click();
    }

}

export default new LoginScreen();
