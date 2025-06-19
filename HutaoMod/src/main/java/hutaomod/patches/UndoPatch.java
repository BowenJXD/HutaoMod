package hutaomod.patches;

import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.google.gson.JsonObject;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hutaomod.modcore.HuTaoMod;
import hutaomod.modifiers.DieyingModifier;
import savestate.AbstractCardModifierState;
import savestate.StateFactories;
import savestate.powers.PowerState;

import java.lang.reflect.Constructor;

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

            return powerToApply;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create power: " + powerClassToApply.getName(), e);
        }
    }
}
