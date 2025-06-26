package hutaomod.cards.uncommon;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.subscribers.SubscriptionManager;

public class DMEM extends HuTaoCard implements OnPlayerDamagedSubscriber {
    public static final String ID = DMEM.class.getSimpleName();

    public DMEM() {
        super(ID);
        selfRetain = true;
        BaseMod.subscribe(this);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
    }

    @Override
    public int receiveOnPlayerDamaged(int i, DamageInfo damageInfo) {
        if (SubscriptionManager.checkSubscriber(this) && AbstractDungeon.player.hand.contains(this)) {
            if (damageInfo.type == DamageInfo.DamageType.HP_LOSS || upgraded)
                i--;
        }
        return Math.max(--i, 0);
    }
}
