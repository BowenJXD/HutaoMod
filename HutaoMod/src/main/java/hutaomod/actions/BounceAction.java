package hutaomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hutaomod.cards.HuTaoCard;
import hutaomod.utils.ModHelper;

import java.util.function.Consumer;

/**
 * TODO: check if bouncing on one vulnerable enemy affects other enemies
 */
public class BounceAction extends AbstractGameAction {
    private AbstractCard card;
    private Consumer<AbstractMonster> callback;

    public BounceAction(AbstractMonster target, AbstractCard card, int usesLeft, Consumer<AbstractMonster> callback) {
        this.card = card;
        this.callback = callback;
        this.setValues(target, AbstractDungeon.player, usesLeft);
    }

    public void update() {
        this.isDone = true;
        if (!ModHelper.check(target)) {
            target = ModHelper.betterGetRandomMonster();
        }
        if (this.amount > 0 && target != null) {
            this.card.calculateCardDamage((AbstractMonster) target);
            callback.accept((AbstractMonster) target);
            if (this.amount > 1) {
                addToBot(new BounceAction(ModHelper.betterGetRandomMonster(), this.card, this.amount - 1, this.callback));
            }
        }
    }
}
