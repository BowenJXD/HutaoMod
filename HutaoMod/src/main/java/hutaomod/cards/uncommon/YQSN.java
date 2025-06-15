/*
package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.RefundFields;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hutaomod.actions.BounceAction;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;

public class YQSN extends HuTaoCard {
    public static final String ID = YQSN.class.getSimpleName();

    public YQSN() {
        super(ID);
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        int x = energyOnUse + (p.hasRelic(ChemicalX.ID) ? 2 : 0);
        int handYY = compareHandYY();
        if (handYY > 0 || (upgraded && handYY == 0)) {
            addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, 1, false)));
            addToBot(new BounceAction(m, this, x, mon -> {
                addToTop(new CardDamageAction(mon, this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }));
        }
        if (handYY < 0 || (upgraded && handYY == 0)) {
            addToBot(new ApplyPowerAction(p, p, new WeakPower(p, 1, false)));
            for (int i = 0; i < x; i++) {
                addToBot(new GainBlockAction(p, p, block));
            }
        }
        RefundFields.refund.set(this, yyTime > 0 ? energyOnUse : 0);
        p.energy.use(EnergyPanel.totalCount);
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        int yyTime = checkYinYang(false);
        int handYY = compareHandYY();
        if (handYY > 0) {
            glowColor = WHITE_BORDER_GLOW_COLOR;
        } else if (handYY < 0) {
            glowColor = BLACK_BORDER_GLOW_COLOR;
        } else if (upgraded && yyTime > 0) {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        } else {
            glowColor = ORANGE_BORDER_GLOW_COLOR;
        }
    }
}
*/
