package hutaomod.powers.arcaneLegends;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.PowerPower;
import hutaomod.subscribers.PreCachedIntGetSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.CacheManager;

public class CDZSPower extends PowerPower implements PreCachedIntGetSubscriber {
    public static final String ID = HuTaoMod.makeID(CDZSPower.class.getSimpleName());
    
    public CDZSPower() {
        super(ID);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        SubscriptionManager.subscribe(this, true);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        SubscriptionManager.unsubscribe(this);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.HP_LOSS) {
            stackPower(damageAmount);
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        reducePower(amount);
        super.onAfterUseCard(card, action);
    }

    @Override
    public int preCachedIntGet(CacheManager.Key key, int amount) {
        if (SubscriptionManager.checkSubscriber(this) && key == CacheManager.Key.PLAYER_SI) {
            return amount + this.amount;
        }
        return amount;
    }
}
