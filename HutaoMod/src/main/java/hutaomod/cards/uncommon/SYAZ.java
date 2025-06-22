package hutaomod.cards.uncommon;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.RedFireballEffect;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.subscribers.CheckYinYangSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.CardDataCol;
import hutaomod.utils.DataManager;
import hutaomod.utils.ModHelper;

public class SYAZ extends HuTaoCard implements CheckYinYangSubscriber {
    public static final String ID = SYAZ.class.getSimpleName();
    
    public SYAZ() {
        super(ID);
        selfRetain = true;
        tags.add(CustomEnum.YIN_YANG);
        SubscriptionManager.subscribe(this);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        int baseDamageCache = DataManager.getInstance().getCardDataInt(ID, upgraded ? CardDataCol.UpgradeDamage : CardDataCol.Damage);
        addToBot(new VFXAction(new RedFireballEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY, (baseDamage - baseDamageCache) * yyTime / 4)));
        addToBot(new CardDamageAction(m, damage * (int) Math.pow(2, yyTime), this, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        baseDamage = baseDamageCache;
    }

    @Override
    public int checkYinYang(HuTaoCard card, int yyTime, boolean onUse) {
        if (SubscriptionManager.checkSubscriber(this) 
                && AbstractDungeon.player.hand.contains(this) 
                && onUse && yyTime > 0) {
            baseDamage++;
        }
        return yyTime;
    }
}
