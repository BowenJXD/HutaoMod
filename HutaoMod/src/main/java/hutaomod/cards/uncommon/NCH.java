package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;

public class NCH extends HuTaoCard {
    public static final String ID = NCH.class.getSimpleName();

    public NCH() {
        super(ID);
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(m, p, new BloodBlossomPower(m, p, this.magicNumber)));
        for (int i = yyTime; i > 0; i--) {
            ModHelper.addToBotAbstract(() -> {
                int bbCount = ModHelper.getPowerCount(m, BloodBlossomPower.POWER_ID);
                addToTop(new GainBlockAction(p, p, bbCount));
            });
        }
    }
}
