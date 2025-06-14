package hutaomod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.PetalEffect;
import com.megacrit.cardcrawl.vfx.scene.WatcherVictoryEffect;
import hutaomod.characters.HuTao;
import hutaomod.effects.PetalFallingEffect;

import java.util.ArrayList;

public class VictoryPatch {
    @SpirePatch(clz = VictoryScreen.class, method = "updateVfx")
    public static class VictoryScreenPatch {
        @SpirePostfixPatch
        public static void Postfix(VictoryScreen _inst, ArrayList<AbstractGameEffect> ___effect) {
            if (AbstractDungeon.player.chosenClass == HuTao.PlayerColorEnum.HUTAO) {
                if (___effect.size() < 40 && MathUtils.random(5) == 0)
                    ___effect.add(new PetalEffect());

                /*boolean createdEffect = false;

                for(AbstractGameEffect e : ___effect) {
                    if (e instanceof PetalFallingEffect) {
                        createdEffect = true;
                        break;
                    }
                }
                
                if (!createdEffect) {
                    ___effect.add(new PetalFallingEffect());
                }*/
            }
        }
    }
}
