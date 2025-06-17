package hutaomod.cards.arcaneLegend;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.external.signature.utils.SignatureHelper;
import hutaomod.modcore.CustomEnum;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.arcaneLegends.GHSYXLPower;

public class GHSYXL extends HuTaoCard implements StartupCard {
    public static final String ID = GHSYXL.class.getSimpleName();
    
    public GHSYXL() {
        super(ID);
        tags.add(CustomEnum.ARCANE_LEGEND);
        isInnate = true;
    }

    @Override
    public void onMove(CardGroup group, boolean in) {
        super.onMove(group, in);
        if (group.type == CardGroup.CardGroupType.MASTER_DECK && in)
            SignatureHelper.enable(HuTaoMod.makeID(ID), true);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ApplyPowerAction(p, p, new GHSYXLPower()));
    }

    @Override
    public boolean atBattleStartPreDraw() {
        return true;
    }
}
