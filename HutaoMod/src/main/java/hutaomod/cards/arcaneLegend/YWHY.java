package hutaomod.cards.arcaneLegend;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.external.signature.utils.SignatureHelper;
import hutaomod.modcore.CustomEnum;
import hutaomod.modcore.HuTaoMod;

public class YWHY extends HuTaoCard implements StartupCard {
    public static final String ID = YWHY.class.getSimpleName();
    
    public YWHY() {
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
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {}

    @Override
    public boolean atBattleStartPreDraw() {
        return true;
    }
}
