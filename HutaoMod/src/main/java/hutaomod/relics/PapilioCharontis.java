/*
package hutaomod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.base.HutaoA;
import hutaomod.cards.base.HutaoQ;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.utils.ModHelper;
import hutaomod.utils.RelicEventHelper;

import java.util.Objects;

public class PapilioCharontis extends HuTaoRelic {
    public static final String ID = PapilioCharontis.class.getSimpleName();

    public PapilioCharontis() {
        super(ID, RelicTier.STARTER);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        super.onObtainCard(c);
        AbstractDungeon.player.masterDeck.group.stream().filter(card -> {
            return Objects.equals(card.cardID, c.cardID) && card.canUpgrade();
        }).findAny().ifPresent(card -> {
            ModHelper.addEffectAbstract(() -> RelicEventHelper.upgradeCards(card));
            flash();
            ModHelper.addEffectAbstract(() -> AbstractDungeon.player.masterDeck.group.remove(c));
        });
    }

    @Override
    public void onRest() {
        super.onRest();
        setCounter(counter + 1);
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if (counter >= 1) {
            AbstractDungeon.player.hand.group.stream().filter(c -> {
                return c.tags.contains(AbstractCard.CardTags.STARTER_STRIKE);
            }).findAny().ifPresent(c -> {
                flash();
                c.freeToPlayOnce = true;
            });
        }
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (counter >= 2) {
            if (Objects.equals(c.cardID, HutaoA.ID)) {
                flash();
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new BloodBlossomPower(m, AbstractDungeon.player, 1)));
            }
            if (Objects.equals(c.cardID, HutaoQ.ID)) {
                flash();
                AbstractDungeon.getMonsters().monsters.stream().filter(ModHelper::check).forEach(m2 -> {
                    addToBot(new ApplyPowerAction(m2, AbstractDungeon.player, new BloodBlossomPower(m2, AbstractDungeon.player, 1)));
                });
            }
        }
    }
}
*/
