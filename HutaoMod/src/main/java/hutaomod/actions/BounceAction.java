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

public class BounceAction extends AbstractGameAction {
    private AbstractCard card;
    private Consumer<AbstractMonster> callback;

    public BounceAction(AbstractCard card, int usesLeft, Consumer<AbstractMonster> callback) {
        this.card = card;
        this.callback = callback;
        this.setValues(null, AbstractDungeon.player, usesLeft);
    }

    public void update() {
        this.isDone = true;
        AbstractMonster monster = ModHelper.betterGetRandomMonster();
        if (this.amount > 0 && monster != null) {
            this.card.calculateCardDamage(monster);
            callback.accept(monster);
            if (this.amount > 1) {
                addToBot(new BounceAction(this.card, this.amount - 1, this.callback));
            }
        }
    }
}
