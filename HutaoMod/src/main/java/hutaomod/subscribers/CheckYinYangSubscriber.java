package hutaomod.subscribers;

import hutaomod.cards.HuTaoCard;

public interface CheckYinYangSubscriber extends IHuTaoSubscriber {
    int checkYinYang(HuTaoCard card, int yyTime, boolean onUse);
}
