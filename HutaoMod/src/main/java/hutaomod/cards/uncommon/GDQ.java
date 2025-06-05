package hutaomod.cards.uncommon;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import hutaomod.actions.BloodBurnAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.utils.GAMManager;
import hutaomod.utils.GeneralUtil;
import hutaomod.utils.ModHelper;
import hutaomod.utils.RelicEventHelper;

import java.util.Objects;

public class GDQ extends HuTaoCard {
    public static final String ID = GDQ.class.getSimpleName();

    public GDQ() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
    }

    @Override
    public void onDieying(boolean in) {
        super.onDieying(in);
        if (upgraded && AbstractDungeon.player.hand.isEmpty()) {
            addToTop(new DrawCardAction(magicNumber));
        }
        addToTop(new SelectCardsInHandAction(GeneralUtil.tryFormat(RelicEventHelper.DISCARD_TEXT, 1), list -> {
            for (AbstractCard card : list) {
                addToTop(new DiscardSpecificCardAction(card));
            }
            if (list.stream().noneMatch(c -> Objects.equals(cardID, c.cardID))) {
                addToTop(new DrawCardAction(magicNumber));
            }
        }));
        addToTop(new VFXAction(new ShowCardBrieflyEffect(makeStatEquivalentCopy(),
                Settings.WIDTH * MathUtils.random(0.2f, 0.8f),
                Settings.HEIGHT * MathUtils.random(0.3f, 0.7f))));
    }
}
