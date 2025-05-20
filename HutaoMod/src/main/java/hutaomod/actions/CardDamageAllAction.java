package hutaomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hutaomod.cards.HuTaoCard;
import hutaomod.utils.ModHelper;

import java.lang.annotation.ElementType;
import java.util.function.Consumer;

public class CardDamageAllAction extends AbstractGameAction {
    public int[] damage;
    private AbstractCard card;
    private Consumer<CardDamageAction.CallbackInfo> callback;
    private Consumer<CardDamageAction.CallbackInfo> modifier;
    private int baseDamage;
    private boolean firstFrame;
    private boolean utilizeBaseDamage;

    public CardDamageAllAction(AbstractCreature source, int[] amount, DamageInfo.DamageType type,
                                    AbstractGameAction.AttackEffect effect, boolean isFast) {
        this.firstFrame = true;
        this.utilizeBaseDamage = false;
        this.source = source;
        this.damage = amount;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        if (isFast) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FAST;
        }
    }

    public CardDamageAllAction(AbstractCreature source, int[] amount, DamageInfo.DamageType type,
                                    AbstractGameAction.AttackEffect effect) {
        this(source, amount, type, effect, false);
    }

    public CardDamageAllAction(HuTaoCard card, int baseDamage, AttackEffect effect) {
        this(AbstractDungeon.player, (int[]) null, card.damageTypeForTurn, effect);
        this.card = card;
        this.baseDamage = baseDamage;
        this.utilizeBaseDamage = true;
    }

    public CardDamageAllAction(HuTaoCard card, AttackEffect effect) {
        this(AbstractDungeon.player, card.multiDamage, card.damageTypeForTurn, effect);
        this.card = card;
    }

    public CardDamageAllAction setCallback(Consumer<CardDamageAction.CallbackInfo> callback) {
        this.callback = callback;
        return this;
    }

    public CardDamageAllAction setModifier(Consumer<CardDamageAction.CallbackInfo> modifier) {
        this.modifier = modifier;
        return this;
    }

    public void update() {
        if (this.firstFrame) {
            if (this.utilizeBaseDamage) {
                this.damage = DamageInfo.createDamageMatrix(this.baseDamage);
            }

            this.firstFrame = false;
        }

        this.tickDuration();
        if (this.isDone) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                p.onDamageAllEnemies(this.damage);
            }

            for (int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); i++) {
                AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                if (ModHelper.check(m)) {
                    CardDamageAction action = new CardDamageAction(
                            m,
                            new DamageInfo(
                                    this.source,
                                    this.damage[i],
                                    this.damageType
                            ),
                            card,
                            this.attackEffect
                    ).callback(callback).modifier(modifier).instant(true);

                    action.update();
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1F));
            }
        }
    }
}
