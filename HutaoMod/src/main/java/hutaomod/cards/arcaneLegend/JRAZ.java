package hutaomod.cards.arcaneLegend;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.external.signature.utils.SignatureHelper;
import hutaomod.modcore.CustomEnum;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.arcaneLegends.JRAZPower;

public class JRAZ extends HuTaoCard {
    public static final String ID = JRAZ.class.getSimpleName();
    
    public JRAZ() {
        super(ID);
        tags.add(CustomEnum.ARCANE_LEGEND);
        tags.add(CardTags.HEALING);
        isInnate = true;
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
        addToBot(new ApplyPowerAction(p, p, new JRAZPower()));
    }
}
