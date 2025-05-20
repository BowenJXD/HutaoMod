package hutaomod.subscribers;

import hutaomod.powers.HuTaoPower;

public interface PrePowerTriggerSubscriber extends IHuTaoSubscriber {
    void prePowerTrigger(HuTaoPower power);
}
