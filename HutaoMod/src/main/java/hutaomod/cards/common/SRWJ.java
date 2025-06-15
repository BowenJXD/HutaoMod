/*
package hutaomod.cards.common;

import basemod.BaseMod;
import basemod.interfaces.OnPlayerDamagedSubscriber;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.ClairvoirAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.ModHelper;
import hutaomod.utils.RelicEventHelper;

import java.util.ArrayList;
import java.util.Objects;

public class SRWJ extends HuTaoCard implements OnPlayerDamagedSubscriber, SpawnModificationCard {
    public static final String ID = SRWJ.class.getSimpleName();
    
    int baseBlockCache;
    
    public SRWJ() {
        super(ID);
        tags.add(CardTags.HEALING);
        selfRetain = true;
        baseBlockCache = baseBlock;
        exhaust = true;
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
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new GainBlockAction(p, p, block + (upgraded ? si : 0)));
        addToBot(new HealAction(p, p, magicNumber));
        baseBlockCache = baseBlock;
        magicNumber = baseMagicNumber;
        isBlockModified = false;
        isMagicNumberModified = false;
    }

    @Override
    public int receiveOnPlayerDamaged(int i, DamageInfo damageInfo) {
        if (SubscriptionManager.checkSubscriber(this) 
                && damageInfo.type == DamageInfo.DamageType.HP_LOSS 
                && damageInfo.owner == AbstractDungeon.player) {
            baseBlock++;
            magicNumber++;
            isBlockModified = true;
            isMagicNumberModified = true;
        }
        return i;
    }
}
*/
