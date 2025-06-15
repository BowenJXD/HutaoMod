package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.effects.PetalFallingEffect;
import hutaomod.powers.debuffs.BloodBlossomPower;

public class XJMX extends HuTaoCard {
    public static final String ID = XJMX.class.getSimpleName();

    public XJMX() {
        super(ID);
        exhaust = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        exhaust = false;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new VFXAction(new PetalFallingEffect()));
        addToBot(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY)));
        addToBot(new CardDamageAction(m, this, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        for (AbstractCard c : p.hand.group) {
            addToBot(new DiscardSpecificCardAction(c));
            if (c.costForTurn > 0) {
                addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, c.costForTurn)));
            }
        }
    }
}
