package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.powers.debuffs.SiPower;
import hutaomod.utils.CacheManager;
import hutaomod.utils.ModHelper;

public class SLBS extends HuTaoCard {
    public static final String ID = SLBS.class.getSimpleName();

    public SLBS() {
        super(ID);
    }
    
    @Override
    public void upgrade() {
        super.upgrade();
        selfRetain = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        int siCount = ModHelper.getPowerCount(p, SiPower.POWER_ID);
        int bbCount = ModHelper.getPowerCount(m, BloodBlossomPower.POWER_ID);
        int diff = bbCount - siCount;
        if (diff != 0) {
            addToBot(new ApplyPowerAction(p, p, new SiPower(p, diff)));
        }
        addToBot(new GainBlockAction(p, p, bbCount));
    }
}
