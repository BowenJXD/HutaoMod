package hutaomod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.actions.BloodBurnAction;
import hutaomod.actions.ScrayAction;
import hutaomod.cards.HuTaoCard;
import hutaomod.cards.base.HutaoA;
import hutaomod.modcore.HuTaoMod;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.powers.debuffs.SiPower;
import hutaomod.utils.GAMManager;
import hutaomod.utils.ModHelper;

import java.util.Objects;

public class TSSC extends HuTaoCard {
    public static final String ID = TSSC.class.getSimpleName(); 

    public TSSC() {
        super(ID);
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m, int yyTime) {
        if (GAMManager.getInstance().currentCard instanceof HutaoA) {
            ModHelper.findCardsInGroup(c -> c.hasTag(CardTags.STARTER_STRIKE), p.drawPile)
                    .stream().findAny().ifPresent(r -> {
                        addToBot(new MoveCardsAction(p.hand, p.drawPile, c -> c == r.card));
                        addToBot(new ReduceCostForTurnAction(r.card, 1));
                    });
        } else if (upgraded) {
            addToBot(new MakeTempCardInHandAction(new HutaoA()));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if (GAMManager.getInstance().currentCard instanceof HutaoA && AbstractDungeon.player.drawPile.findCardById(HuTaoMod.makeID(HutaoA.ID)) != null) {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        } else {
            glowColor = ORANGE_BORDER_GLOW_COLOR;
        }
    }
}
