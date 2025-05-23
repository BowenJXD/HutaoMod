package hutaomod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import hutaomod.actions.CardDamageAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.utils.CacheManager;

public class DMEM extends HuTaoCard {
    public static final String ID = DMEM.class.getSimpleName();

    public DMEM() {
        super(ID);
        exhaust = true;
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        isInnate = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        if (yyTime > 0)
            addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, magicNumber * yyTime)));
    }
}
