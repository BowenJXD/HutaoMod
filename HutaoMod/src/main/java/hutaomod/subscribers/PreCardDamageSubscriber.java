package hutaomod.subscribers;

import hutaomod.actions.CardDamageAction;

public interface PreCardDamageSubscriber extends IHuTaoSubscriber {
    float preCardDamage(CardDamageAction cardDamageAction, float output);
}
