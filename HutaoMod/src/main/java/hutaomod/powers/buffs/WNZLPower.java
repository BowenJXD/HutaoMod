package hutaomod.powers.buffs;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.BuffPower;
import hutaomod.powers.debuffs.SiPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.PreCachedIntGetSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.CacheManager;

public class WNZLPower extends BuffPower implements CheckYinYangSubscriber, PreCachedIntGetSubscriber {
    public static final String POWER_ID = HuTaoMod.makeID(WNZLPower.class.getSimpleName());
    
    public WNZLPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        updateDescription();
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
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (HuTaoCard.isYang(card)) {
            addToTop(new RemoveSpecificPowerAction(owner, owner, SiPower.POWER_ID));
            remove(1);
        }
    }

    @Override
    public int checkYinYang(HuTaoCard card, int yyTime, boolean onUse) {
        if (SubscriptionManager.checkSubscriber(this) && yyTime == 0 && HuTaoCard.isYang(card)) {
            yyTime++;
            if (onUse) remove(1);
        }
        return yyTime;
    }

    @Override
    public int preCachedIntGet(CacheManager.Key key, int amount) {
        if (SubscriptionManager.checkSubscriber(this) && key == CacheManager.Key.PLAYER_SI) {
            return amount * 2;
        }
        return amount;
    }
}
