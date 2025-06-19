package hutaomod.cards.rare;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;
import hutaomod.powers.debuffs.SiPower;

public class DYZS extends HuTaoCard {
    public static final String ID = DYZS.class.getSimpleName();

    public DYZS() {
        super(ID);
        exhaust = true;
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        addToBot(new BloodBurnAction(magicNumber));
        addToBot(new RemoveSpecificPowerAction(p, p, SiPower.POWER_ID));
        addToBot(new MakeTempCardInHandAction(new HutaoA(upgraded), si));
        int drawCount = BaseMod.MAX_HAND_SIZE - p.hand.size() - si;
        if (drawCount > 0) {
            addToBot(new DrawCardAction(drawCount));
        }
    }
}
