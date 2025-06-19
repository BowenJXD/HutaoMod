package hutaomod.cards.arcaneLegend;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.external.signature.utils.SignatureHelper;
import hutaomod.modcore.CustomEnum;
import hutaomod.modcore.HuTaoMod;

public class YWHY extends HuTaoCard {
    public static final String ID = YWHY.class.getSimpleName();
    
    public YWHY() {
        super(ID);
        tags.add(CustomEnum.ARCANE_LEGEND);
        tags.add(CardTags.HEALING);
        isInnate = true;
        exhaust = true;
        try {
            SignatureHelper.unlock(HuTaoMod.makeID(ID), true);
            SignatureHelper.enable(HuTaoMod.makeID(ID), true);
        } catch (Exception ignored) {}
    }

    @Override
    public boolean alwaysUseSignature() {
        return true;
    }
    
    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new GainEnergyAction(magicNumber));
    }
}
