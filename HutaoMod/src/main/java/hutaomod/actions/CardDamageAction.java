package hutaomod.actions;

import com.evacipated.cardcrawl.mod.stslib.patches.ColoredDamagePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.ModHelper;

import java.util.function.Consumer;

public class CardDamageAction extends AbstractGameAction {
    public DamageInfo info;
    public AbstractCard card;
    
    private Consumer<CallbackInfo> modifier;
    private Consumer<CallbackInfo> callback;

    public boolean doApplyPower = false;
    public boolean instant = false;
    public boolean isSourceNullable = false;
    
    public CardDamageAction(AbstractCreature target, 
                            DamageInfo info, 
                            AbstractCard card, 
                            AbstractGameAction.AttackEffect effect) {
        this.info = info;
        this.card = card;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.1F;
        ColoredDamagePatch.DamageActionColorField.fadeSpeed.set(this, ColoredDamagePatch.FadeSpeed.SLOW);
    }
    
    public CardDamageAction(AbstractCreature target, 
                            AbstractCard card,
                            AbstractGameAction.AttackEffect effect) {
        this(target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), card, effect);
    }
    
    public CardDamageAction(AbstractCreature target, int amount, AbstractCard card,  AbstractGameAction.AttackEffect effect) {
        this(target, new DamageInfo(AbstractDungeon.player, amount, card.damageTypeForTurn), card, effect);
    }
    
    public CardDamageAction modifier(Consumer<CallbackInfo> modifier) {
        this.modifier = modifier;
        return this;
    }
    
    public CardDamageAction callback(Consumer<CallbackInfo> callback) {
        this.callback = callback;
        return this;
    }
    
    public CardDamageAction doApplyPower(boolean doApplyPower) {
        this.doApplyPower = doApplyPower;
        return this;
    }
    
    public CardDamageAction instant(boolean isFast) {
        this.instant = isFast;
        return this;
    }
    
    public CardDamageAction isSourceNullable(boolean isSourceNullable) {
        this.isSourceNullable = isSourceNullable;
        return this;
    }
    
    @Override
    public void update() {
        if ((this.shouldCancelAction() && this.info.type != DamageInfo.DamageType.THORNS)
                || this.target == null) {
            this.isDone = true;
            return;
        }
        // Start of the action
        if (this.duration == 0.1F) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, attackEffect));
        }

        if (this.instant) this.isDone = true;
        
        else this.tickDuration();

        if (!this.isDone) return;

        if (this.doApplyPower) this.applyPowers();
        
        if (this.modifier != null) this.modifier.accept(new CallbackInfo(target, info));
        
        info.output = (int) SubscriptionManager.getInstance().triggerPreCardDamage(this, info.output);
        
        // Apply damage
        this.target.damage(this.info);

        // Callback
        if (callback != null) addToTop(new TriggerCallbackAction(this.callback, new CallbackInfo(target, info)));

        // Check to remove actions except HealAction, GainBlockAction, UseCardAction, TriggerCallbackAction, and DamageAction
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {

            AbstractDungeon.actionManager.actions.removeIf(e -> !(e instanceof HealAction)
                    && !(e instanceof GainBlockAction)
                    && !(e instanceof UseCardAction)
                    && !(e instanceof TriggerCallbackAction)
                    && e.actionType != ActionType.DAMAGE);
        }
        //
    }
    
    public CardDamageAction applyPowers() {
        this.info.applyPowers(this.info.owner, this.target);
        return this;
    }

    @Override
    protected boolean shouldCancelAction() {
        if (this.isSourceNullable) return this.target == null || this.target.isDeadOrEscaped();
        return this.target == null || this.source != null && !ModHelper.check(source) || this.target.isDeadOrEscaped();
    }

    public static class TriggerCallbackAction extends AbstractGameAction {
        private Consumer<CallbackInfo> callback;
        private CallbackInfo info;

        public TriggerCallbackAction(Consumer<CallbackInfo> callback, CallbackInfo info) {
            this.callback = callback;
            this.info = info;
        }

        public void update() {
            if (this.callback != null) this.callback.accept(this.info);
            this.isDone = true;
        }
    }
    
    public static class CallbackInfo {
        public AbstractCreature target;
        public DamageInfo info;
        
        public CallbackInfo(AbstractCreature target, DamageInfo info) {
            this.target = target;
            this.info = info;
        }
    }
}
