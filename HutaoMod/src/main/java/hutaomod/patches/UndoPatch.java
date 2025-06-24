package hutaomod.patches;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.interfaces.ISubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hutaomod.cards.base.HutaoA;
import hutaomod.modcore.HuTaoMod;
import hutaomod.modifiers.BloodCostModifier;
import hutaomod.modifiers.DYBBModifier;
import hutaomod.modifiers.DieyingModifier;
import hutaomod.powers.debuffs.BloodBlossomPower;
import hutaomod.powers.powers.QYGPower;
import hutaomod.powers.powers.WSTPower;
import hutaomod.subscribers.IHuTaoSubscriber;
import hutaomod.subscribers.SubscriptionManager;
import hutaomod.utils.GAMManager;
import savestate.AbstractCardModifierState;
import savestate.CardState;
import savestate.StateFactories;
import savestate.powers.PowerState;
import undobutton.util.MakeUndoable;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UndoPatch {
    @SpirePatch(
            clz = PowerState.class,
            method = "forPower",
            requiredModId = "undothespire"
    )
    public static class forPowerPatch {
        public static SpireReturn<PowerState> Prefix(final AbstractPower power) {
            String id = power.ID;

            for(String prefix : StateFactories.powerPrefixes) {
                if (id.startsWith(prefix)) {
                    id = prefix;
                    break;
                }
            }

            if (!StateFactories.powerByIdMap.containsKey(id)) {
                return SpireReturn.Return(new PowerState(power) {
                    @Override
                    public AbstractPower loadPower(AbstractCreature targetAndSource) {
                        return getPower(power, this.amount, targetAndSource);
                    }
                });
            } else {
                return SpireReturn.Return(StateFactories.powerByIdMap.get(id).factory.apply(power));
            }
        }

        public static AbstractPower getPower(AbstractPower powerToApply, int amount, AbstractCreature creature) {
            Class<? extends AbstractPower> powerClassToApply = powerToApply.getClass();
            try {
                Constructor<?>[] con = powerClassToApply.getDeclaredConstructors();
                int paramCt = con[0].getParameterCount();
                Class[] params = con[0].getParameterTypes();
                Object[] paramz = new Object[paramCt];

                for (int i = 0; i < paramCt; ++i) {
                    Class param = params[i];
                    if (AbstractCreature.class.isAssignableFrom(param)) {
                        paramz[i] = creature;
                    } else if (Integer.TYPE.isAssignableFrom(param)) {
                        paramz[i] = amount;
                    } else if (Boolean.TYPE.isAssignableFrom(param)) {
                        paramz[i] = true;
                    }
                }

                powerToApply = (AbstractPower) con[0].newInstance(paramz);
                if (powerToApply instanceof IHuTaoSubscriber) {
                    SubscriptionManager.subscribe((IHuTaoSubscriber) powerToApply);
                }
                if (powerToApply instanceof ISubscriber) {
                    BaseMod.subscribe((ISubscriber) powerToApply);
                }
                if (powerToApply instanceof BloodBlossomPower) {//
                    ((BloodBlossomPower)powerToApply).source = AbstractDungeon.player;
                }

                return powerToApply;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create power: " + powerClassToApply.getName(), e);
            }
        }
    }
    
    @SpirePatch(
            clz = HuTaoMod.class,
            method = "receivePostInitialize",
            requiredModId = "undothespire"
    )
    public static class receivePostInitializePatch {
        public static void Postfix() {
            StateFactories.cardModifierFactories.put(DieyingModifier.ID, new AbstractCardModifierState.CardModifierStateFactories(
                    DieyingModifierState::new,
                    DieyingModifierState::new,
                    DieyingModifierState::new
            ) );
            StateFactories.cardModifierFactories.put(DYBBModifier.ID, new AbstractCardModifierState.CardModifierStateFactories(
                    DYBBModifierState::new,
                    DYBBModifierState::new,
                    DYBBModifierState::new
            ));
            StateFactories.cardModifierFactories.put(BloodCostModifier.ID, new AbstractCardModifierState.CardModifierStateFactories(
                    BloodCostModifierState::new,
                    BloodCostModifierState::new,
                    BloodCostModifierState::new
            ));
            StateFactories.powerByIdMap.put(QYGPower.POWER_ID, new PowerState.PowerFactories(QYGPowerState::new));
            StateFactories.powerByIdMap.put(WSTPower.POWER_ID, new PowerState.PowerFactories(WSTPowerState::new));
        }
        
        public static class DieyingModifierState extends AbstractCardModifierState {
            public DieyingModifierState(AbstractCardModifier modifier) {
                super(modifier);
            }

            public DieyingModifierState(String jsonString) {
                super(jsonString);
            }

            public DieyingModifierState(JsonObject modifierJson) {
                super(modifierJson);
            }

            @Override
            public AbstractCardModifier loadModifier() {
                return new DieyingModifier();
            }
        }
        
        public static class DYBBModifierState extends AbstractCardModifierState {
            public DYBBModifierState(AbstractCardModifier modifier) {
                super(modifier);
            }

            public DYBBModifierState(String jsonString) {
                super(jsonString);
            }

            public DYBBModifierState(JsonObject modifierJson) {
                super(modifierJson);
            }

            @Override
            public AbstractCardModifier loadModifier() {
                return new DYBBModifier();
            }
        }
        
        public static class BloodCostModifierState extends AbstractCardModifierState {
            public BloodCostModifierState(AbstractCardModifier modifier) {
                super(modifier);
            }

            public BloodCostModifierState(String jsonString) {
                super(jsonString);
            }

            public BloodCostModifierState(JsonObject modifierJson) {
                super(modifierJson);
            }

            @Override
            public AbstractCardModifier loadModifier() {
                return new DYBBModifier();
            }
        }
        
        public static class QYGPowerState extends PowerState {
            int limit;
            int amount2;

            public QYGPowerState(AbstractPower power) {
                super(power);
                if (power instanceof QYGPower) {
                    QYGPower qygPower = (QYGPower) power;
                    this.limit = qygPower.limit;
                    this.amount2 = qygPower.amount2;
                }
            }

            public QYGPowerState(String jsonString) {
                super(jsonString);
                this.limit = powerJson.get("limit").getAsInt();
                this.amount2 = powerJson.get("amount2").getAsInt();
            }

            public QYGPowerState(JsonObject powerJson) {
                super(powerJson);
                this.limit = powerJson.get("limit").getAsInt();
                this.amount2 = powerJson.get("amount2").getAsInt();
            }

            @Override
            public AbstractPower loadPower(AbstractCreature targetAndSource) {
                QYGPower power = new QYGPower(limit);
                power.amount = amount;
                power.amount2 = amount2;
                SubscriptionManager.subscribe(power);
                return power;
            }
        }
        
        public static class WSTPowerState extends PowerState {
            int limit;
            int amount2;

            public WSTPowerState(AbstractPower power) {
                super(power);
                if (power instanceof WSTPower) {
                    WSTPower qygPower = (WSTPower) power;
                    this.limit = qygPower.limit;
                    this.amount2 = qygPower.amount2;
                }
            }

            public WSTPowerState(String jsonString) {
                super(jsonString);
                this.limit = powerJson.get("limit").getAsInt();
                this.amount2 = powerJson.get("amount2").getAsInt();
            }

            public WSTPowerState(JsonObject powerJson) {
                super(powerJson);
                this.limit = powerJson.get("limit").getAsInt();
                this.amount2 = powerJson.get("amount2").getAsInt();
            }

            @Override
            public AbstractPower loadPower(AbstractCreature targetAndSource) {
                WSTPower power = new WSTPower(limit);
                power.amount = amount;
                power.amount2 = amount2;
                SubscriptionManager.subscribe(power);
                return power;
            }
        }
        
        @MakeUndoable(statetype = String[].class)
        public static class GAMActionsState {
            public static void load(String[] keys) {
                if (keys == null || keys.length == 0) return;
                List<String> keyList = Arrays.asList(keys);
                GAMManager gamManager = GAMManager.getInstance();
                gamManager.actionsCache.forEach((key, action) -> {
                    if (keyList.contains(key)) {
                        if (!gamManager.parallelActions.containsKey(key)) {
                            gamManager.parallelActions.put(key, action);
                        }
                    } else {
                        gamManager.parallelActions.remove(key);
                    }
                });
            }
            
            public static String[] save() {
                GAMManager gamManager = GAMManager.getInstance();
                return gamManager.parallelActions.keySet().toArray(new String[0]);
            }
        }
    }
    
    /*@SpirePatch(
            clz = CardState.class,
            method = SpirePatch.CONSTRUCTOR,
            requiredModId = "undothespire"
    )
    public static class cardStatePatch {
        public static void Postfix(CardState __instance, AbstractCard card) {
            if (card instanceof HutaoA) {
                HutaoA hutaoCard = (HutaoA) card;
                if (hutaoCard.yyTime > 0) {
                    __instance.yyTime = hutaoCard.yyTime;
                }
            }
        }
    }*/
}
