package hutaomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.TimeWarpPower;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;
import hutaomod.modcore.HuTaoMod;
import hutaomod.utils.CacheManager;

import java.util.Objects;

public class EndTurnButtonActionPatch {
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(HuTaoMod.makeID("EndTurnButtonAction"));
    public static String[] TEXT = uiStrings.TEXT;
    public static float SHOW_X = 1640.0F * Settings.xScale;
    public static float SHOW_Y = 310.0F * Settings.yScale; // 210
    public static String lastMentionKey;

    @SpirePatch(clz = PressEndTurnButtonAction.class, method = "update")
    public static class PressEndTurnButtonActionUpdatePatch {
        public static SpireReturn<Void> Prefix(PressEndTurnButtonAction __inst) {
            if (checkMention()) {
                __inst.isDone = true;
                SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = EndTurnButton.class, method = "disable", paramtypez = boolean.class)
    public static class NewQueueCardActionUpdatePatch {
        public static SpireReturn<Void> Prefix(EndTurnButton __inst, boolean isEnemyTurn) {
            try {
                if (checkMention() && isEnemyTurn && AbstractDungeon.getMonsters().monsters.stream().noneMatch(m -> m.hasPower(TimeWarpPower.POWER_ID))) {
                    return SpireReturn.Return();
                }
            } catch (Exception e) {}
            return SpireReturn.Continue();
        }
    }

    static boolean checkMention() {
        boolean result = false;
        String mentionKey = String.valueOf(AbstractDungeon.getCurrRoom().hashCode()) + GameActionManager.turn;
        if (CacheManager.getBool(CacheManager.Key.DYING)) {
            if (!Objects.equals(mentionKey, lastMentionKey)) {
                AbstractDungeon.ftue = new FtueTip(TEXT[0], TEXT[1], SHOW_X, SHOW_Y, FtueTip.TipType.NO_FTUE);
                result = true;
            }
        }
        lastMentionKey = mentionKey;
        return result;
    }
}
