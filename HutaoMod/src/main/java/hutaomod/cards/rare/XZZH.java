package hutaomod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.modcore.CustomEnum;
import hutaomod.powers.powers.BBLPower;

public class XZZH extends HuTaoCard {
    public static final String ID = XZZH.class.getSimpleName();

    public XZZH() {
        super(ID);
        exhaust = true;
        tags.add(CustomEnum.YIN_YANG);
    }

    @Override   
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(magicNumber));
        int energyGain = magicNumber;
        addToBot(new GainEnergyAction(energyGain));
        int draw = magicNumber * yyTime; 
        addToBot(new DrawCardAction(draw));
    }
}
