package hutaomod.cards.rare;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.ConditionalDrawAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;
import hutaomod.powers.debuffs.SiPower;

public class DYZS extends HuTaoCard {
    public static final String ID = DYZS.class.getSimpleName();

    public DYZS() {
        super(ID);
        exhaust = true;
        GraveField.grave.set(this, true);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        trigger();
    }
    
    void trigger() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new RemoveSpecificPowerAction(p, p, SiPower.POWER_ID));
        addToBot(new MakeTempCardInHandAction(new HutaoA(), si));
        int drawCount = BaseMod.MAX_HAND_SIZE - p.hand.size() - si;
        if (drawCount > 0) {
            addToBot(new DrawCardAction(drawCount));
        }
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        if (upgraded) trigger();
    }
}
