package hutaomod.cards.uncommon;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
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
        addToTop(new VFXAction(new ShowCardBrieflyEffect(makeStatEquivalentCopy(),
                Settings.WIDTH * MathUtils.random(0.2f, 0.8f),
                Settings.HEIGHT * MathUtils.random(0.3f, 0.7f))));
    }
}
