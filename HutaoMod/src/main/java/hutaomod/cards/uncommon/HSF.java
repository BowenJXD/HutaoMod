package hutaomod.cards.uncommon;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.subscribers.SubscriptionManager;

public class HSF extends HuTaoCard implements OnPlayerDamagedSubscriber {
    public static final String ID = HSF.class.getSimpleName();

    public HSF() {
        super(ID);
        selfRetain = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
    }

    @Override
    public void onEnterHand() {
        super.onEnterHand();
        BaseMod.subscribe(this);
    }

    @Override
    public void onLeaveHand() {
        super.onLeaveHand();
        BaseMod.unsubscribe(this);
    }

    @Override
    public int receiveOnPlayerDamaged(int i, DamageInfo damageInfo) {
        if (SubscriptionManager.checkSubscriber(this)) {
            if (damageInfo.type != DamageInfo.DamageType.HP_LOSS && upgraded) {
                i--;
            }
            return Math.max(--i, 0);
        }
        return i;
    }
}
