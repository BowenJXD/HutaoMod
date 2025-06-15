package hutaomod.cards.rare;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.ConditionalDrawAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;
import hutaomod.modcore.CustomEnum;
import hutaomod.powers.debuffs.SiPower;
import hutaomod.utils.ModHelper;

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
