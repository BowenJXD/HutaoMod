package hutaomod.powers.arcaneLegends;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.subscribers.PreCachedIntGetSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.CacheManager;
import hutaomod.utils.GAMManager;

public class CDZSPower extends PowerPower implements PreCachedIntGetSubscriber {
    public static final String POWER_ID = HuTaoMod.makeID(CDZSPower.class.getSimpleName());
    
    public CDZSPower() {
        super(POWER_ID);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        SubscriptionManager.subscribe(this, true);
        GAMManager.addParallelAction(POWER_ID, action -> {
            if (action instanceof BloodBurnAction) {
                addToBot(new ApplyPowerAction(owner, owner, new CDZSPower(), action.amount, true));
            }
            return !SubscriptionManager.checkSubscriber(this);
        });
    }

    @Override
    public void onRemove() {
        super.onRemove();
        SubscriptionManager.unsubscribe(this);
        GAMManager.removeParallelAction(POWER_ID);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        reducePower(amount);
    }

    @Override
    public int preCachedIntGet(CacheManager.Key key, int amount) {
        if (SubscriptionManager.checkSubscriber(this) && key == CacheManager.Key.PLAYER_SI) {
            return amount + this.amount;
        }
        return amount;
    }
}
