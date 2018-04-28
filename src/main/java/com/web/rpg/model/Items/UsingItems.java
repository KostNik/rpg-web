package com.web.rpg.model.Items;

import com.web.rpg.model.Items.impl.HealingItems;

import java.util.List;

/**
 * Created by pikachu on 14.07.17.
 */

/**
 * Интерфейс, содержащий набор методов для работы с предметами для восстановления здоровья
 */
public interface UsingItems {
    boolean add(HealingItems item);
    boolean addAll(List<HealingItems> item);
    void use(HealingItems item);
}
