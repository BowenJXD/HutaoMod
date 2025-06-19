package hutaomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import hutaomod.characters.HuTao;
import hutaomod.modcore.CustomEnum;
import hutaomod.utils.GeneralUtil;

import java.util.List;
import java.util.stream.Collectors;

public class RewardPatch {
    @SpirePatch(clz = CombatRewardScreen.class, method = "setupItemReward")
    public static class SetupItemRewardPatch {
        public static void Postfix(CombatRewardScreen __inst) {
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && AbstractDungeon.player.masterDeck.group.stream().noneMatch(c -> c.hasTag(CustomEnum.ARCANE_LEGEND))) {
                RewardItem ri = new RewardItem();
                List<AbstractCard> cards = CardLibrary.getCardList(HuTao.PlayerLibraryEnum.HUTAO_RED).stream().filter(c -> c.hasTag(CustomEnum.ARCANE_LEGEND)).collect(Collectors.toList());
                
                for (int i = 0; i < Math.min(ri.cards.size(), cards.size()); i++) {
                    AbstractCard card = GeneralUtil.getRandomElement(cards, AbstractDungeon.cardRng);
                    if (card != null) {
                        cards.remove(card);
                        ri.cards.set(i, card.makeCopy());
                    }
                }
                
                __inst.rewards.add(ri);
            }
        }
    }
}
