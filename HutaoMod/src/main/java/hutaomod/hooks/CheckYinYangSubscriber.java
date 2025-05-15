package hutaomod.hooks;

import hutaomod.cards.HuTaoCard;

public interface CheckYinYangSubscriber extends IHuTaoSubscriber {
    boolean checkYinYang(HuTaoCard card);
}
