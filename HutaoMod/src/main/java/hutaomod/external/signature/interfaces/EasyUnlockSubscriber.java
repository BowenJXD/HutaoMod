package hutaomod.external.signature.interfaces;

import com.megacrit.cardcrawl.screens.GameOverScreen;
import hutaomod.external.signature.utils.EasyUnlock;

public interface EasyUnlockSubscriber {
	default EasyUnlock receiveOnGameOver(GameOverScreen screen) {
		return null;
	}
}
