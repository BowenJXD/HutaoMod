package hutaomod.cards.uncommon;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.ModHelper;

public class SYAZ extends HuTaoCard implements CheckYinYangSubscriber {
    public static final String ID = SYAZ.class.getSimpleName();
    
    public SYAZ() {
        super(ID);
        selfRetain = true;
        exhaust = true;
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void onEnterHand() {
        super.onEnterHand();
        SubscriptionManager.unsubscribe(this);
        SubscriptionManager.subscribe(this);
    }

    @Override
    public void onLeaveHand() {
        super.onLeaveHand();
        SubscriptionManager.unsubscribe(this);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new CardDamageAction(m, damage * (int) Math.pow(2, yyTime), this, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public int checkYinYang(HuTaoCard card, int yyTime, boolean onUse) {
        if (SubscriptionManager.checkSubscriber(this) && onUse && yyTime > 0) {
            baseDamage++;
        }
        return yyTime;
    }
}
