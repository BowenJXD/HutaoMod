package hutaomod.relics;

import basemod.BaseMod;
import basemod.interfaces.PostCampfireSubscriber;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import hutaomod.subscribers.SubscriptionManager;

public class BoiledFish extends HuTaoRelic implements PostCampfireSubscriber {
    public static final String ID = BoiledFish.class.getSimpleName();
    
    public BoiledFish() {
        super(ID, RelicTier.SHOP);
        BaseMod.subscribe(this);
    }

    @Override
    public void onEnterRestRoom() {
        super.onEnterRestRoom();
        beginLongPulse();
    }

    @Override
    public boolean receivePostCampfire() {
        if (SubscriptionManager.checkSubscriber(this) && pulse) {
            flash();
            stopPulse();
            return false;
        }
        return true;
    }
}
