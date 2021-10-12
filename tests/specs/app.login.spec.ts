import LoginScreen from '../screenobjects/LoginScreen';
import SpotifyScreen from '../screenobjects/SpotifyScreen';

describe('', () => {
    beforeEach(async () => {
        await LoginScreen.waitForIsShown(true);
    });

    it('shows the Spotify auth screen.', async () => {

        await LoginScreen.tapAuthButton();

        await SpotifyScreen.waitForScreenIsShown(true);

        /*
        TODO: It needs scrolling to the bottom.
        */

        await SpotifyScreen.waitForAgreeButtonIsShown(true);

        await SpotifyScreen.tapAgreeButton();
    });

});
