import LoginScreen from '../screenobjects/LoginScreen';
import SpotifyScreen from '../screenobjects/SpotifyScreen';

describe('', () => {
    beforeEach(async () => {
        await LoginScreen.waitForIsShown(true);
    });

    it('shows the Spotify auth screen.', async () => {

        await LoginScreen.tapAuthButton();

        await SpotifyScreen.waitForScreenIsShown(true);

    });
});
