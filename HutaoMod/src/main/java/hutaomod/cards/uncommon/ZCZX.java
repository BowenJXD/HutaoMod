package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.BossCrystalImpactEffect;
import hutaomod.actions.CardDamageAction;
import hutaomod.actions.CardDamageAllAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;

public class ZCZX extends HuTaoCard {
    public static final String ID = ZCZX.class.getSimpleName();

    public ZCZX() {
        super(ID);
        isMultiDamage = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new CardDamageAllAction(this, AbstractGameAction.AttackEffect.FIRE).setCallback(ci -> {
            if (!ModHelper.check(ci.target)) return;
            BloodBlossomPower bbPower = (BloodBlossomPower) ci.target.getPower(BloodBlossomPower.POWER_ID);
            if (upgraded) {
                addToTop(new ApplyPowerAction(ci.target, p, new BloodBlossomPower(ci.target, p, magicNumber)));
            }
            if (bbPower != null && !bbPower.upgraded) {
                bbPower.upgraded = true;
                addToTop(new VFXAction(new BossCrystalImpactEffect(ci.target.hb.cX, ci.target.hb.cY)));
            }
        }));
    }
}
