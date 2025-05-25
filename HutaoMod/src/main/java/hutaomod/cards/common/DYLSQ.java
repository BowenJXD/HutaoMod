package hutaomod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.BounceAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.buffs.BreathPower;
import hutaomod.powers.debuffs.BloodBlossomPower;

public class DYLSQ extends HuTaoCard {
    public static final String ID = DYLSQ.class.getSimpleName();

    public DYLSQ() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BounceAction(m, this, magicNumber, mon -> {
            addToTop(
                    new GainBlockAction(p, p, si + (upgraded ? block : 0)),
                    new ApplyPowerAction(mon, p, new VulnerablePower(mon, 1, false)),
                    new ApplyPowerAction(mon, p, new BloodBlossomPower(mon, p, 1))
            );
        }));
    }
}
