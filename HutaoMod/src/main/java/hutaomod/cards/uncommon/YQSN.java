package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.RefundFields;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
        addToBot(new BounceAction(m, this, x, mon -> {
            addToTop(new CardDamageAction(mon, this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }));
        if (yyTime > 0) {
            RefundFields.refund.set(this, energyOnUse);
        } else {
            RefundFields.refund.set(this, 0);
        }
        p.energy.use(EnergyPanel.totalCount);
    }
}
