package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;
import hutaomod.powers.debuffs.BloodBlossomPower;

public class SRWJ extends HuTaoCard {
    public static final String ID = SRWJ.class.getSimpleName();

    public SRWJ() {
        super(ID);
        GraveField.grave.set(this, true);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        AbstractCard card = AbstractDungeon.player.hand.getRandomCard(AbstractDungeon.cardRandomRng);
        addToTop(new ReduceCostForTurnAction(card, magicNumber));
        AbstractCard hutaoA = new HutaoA();
        if (upgraded) hutaoA.upgrade();
        addToTop(new MakeTempCardInDrawPileAction(hutaoA, magicNumber, true, true));
    }
}
