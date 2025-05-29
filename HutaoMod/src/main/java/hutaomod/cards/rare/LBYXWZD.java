package hutaomod.cards.rare;

import basemod.interfaces.OnPlayerDamagedSubscriber;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.powers.BBLPower;
import hutaomod.subscribers.PostCardMoveSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.GAMManager;

public class LBYXWZD extends HuTaoCard implements PostCardMoveSubscriber, OnPlayerDamagedSubscriber {
    public static final String ID = LBYXWZD.class.getSimpleName();

    boolean subscribed;
    
    public LBYXWZD() {
        super(ID);
        GraveField.grave.set(this, true);
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new CardDamageAction(m, damage + si, this, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
    
    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if (!subscribed) {
            SubscriptionManager.subscribe(this);
            subscribed = true;
        }
    }

    @Override
    public int receiveOnPlayerDamaged(int i, DamageInfo damageInfo) {
        if (SubscriptionManager.checkSubscriber(this) 
                && damageInfo.type == DamageInfo.DamageType.HP_LOSS) {
            baseDamage += magicNumber;
        }
        return i;
    }

    @Override
    public void postCardMove(CardGroup group, AbstractCard card, boolean in) {
        if (SubscriptionManager.checkSubscriber(this) && group == AbstractDungeon.player.exhaustPile && in) {
            baseDamage += 1;
        }
    }
}
