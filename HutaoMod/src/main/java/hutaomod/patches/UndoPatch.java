package hutaomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import savestate.powers.PowerState;
import undobutton.patches.UnknownPowersPatches;

import java.lang.reflect.Constructor;

public class UndoPatch {
    @SpirePatch(
            clz = UnknownPowersPatches.forPowerPatch.class,
            method = "tryToSaveUnknownPower",
            requiredModId = "undothespire"
    )
    public static class forPowerPatch {
        public static SpireReturn<PowerState> Prefix(final AbstractPower power) {
            return SpireReturn.Return(new PowerState(power) {
                public AbstractPower loadPower(AbstractCreature targetAndSource) {
                    return getPower(power, this.amount, targetAndSource);
                }
            });
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
