package hutaomod.cards.special;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;

public class JZWB extends HuTaoCard {
    public static final String ID = JZWB.class.getSimpleName();

    public JZWB() {
        super(ID);
        exhaust = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY)));
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.NONE));
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, magicNumber)));
    }
}
