package hutaomod.cards.uncommon;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import hutaomod.actions.ScrayAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.powers.buffs.EndTurnClairvoirPower;
import hutaomod.powers.debuffs.BloodBlossomPower;

import java.util.Objects;

public class LMSDYTCZFJ extends HuTaoCard {
    public static final String ID = LMSDYTCZFJ.class.getSimpleName();

    public LMSDYTCZFJ() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        addToTop(new ScrayAction(magicNumber));
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EndTurnClairvoirPower(AbstractDungeon.player, 1)));
        addToTop(new ShowCardAction(this));
        addToTop(new VFXAction(new ShowCardBrieflyEffect(makeStatEquivalentCopy(),
                Settings.WIDTH * MathUtils.random(0.2f, 0.8f),
                Settings.HEIGHT * MathUtils.random(0.3f, 0.7f))));
    }
}
