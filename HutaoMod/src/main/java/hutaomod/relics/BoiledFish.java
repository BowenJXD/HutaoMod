package hutaomod.relics;

import basemod.BaseMod;
import basemod.interfaces.PostCampfireSubscriber;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import hutaomod.subscribers.SubscriptionManager;

public class BoiledFish extends HuTaoRelic implements PostCampfireSubscriber {
    public static final String ID = BoiledFish.class.getSimpleName();
    
    int restCountCache = 0;
    
    public BoiledFish() {
        super(ID, RelicTier.SHOP);
        BaseMod.subscribe(this);
    }

    @Override
    public void onEnterRestRoom() {
        super.onEnterRestRoom();
        restCountCache = CardCrawlGame.metricData.campfire_rested;
    }

    @Override
    public boolean receivePostCampfire() {
        if (SubscriptionManager.checkSubscriber(this) && CardCrawlGame.metricData.campfire_rested > restCountCache) {
            restCountCache = CardCrawlGame.metricData.campfire_rested;
            flash();
            return false;
        }
        return true;
    }
}
