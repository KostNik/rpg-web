package com.web.rpg.model.Characters.characterclasses;

import com.web.rpg.model.Characters.PlayerCharacter;
import com.web.rpg.model.Characters.CharacterFactory;
import com.web.rpg.model.Characters.CharacterNames;
import com.web.rpg.model.Items.Equipment;
import com.web.rpg.model.Items.EquipmentItems;
import com.web.rpg.model.Items.UsingItems;
import com.web.rpg.model.Items.itemsclasses.Item;
import com.web.rpg.model.Items.itemsclasses.weaponsclasses.Weapons;
import com.web.rpg.model.Quests.Quest;
import com.web.rpg.model.abilities.Magic;
import com.web.rpg.model.abilities.MagicClasses;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by pikachu on 13.07.17.
 */
public class Wizard extends PlayerCharacter implements Equipment, UsingItems {

    private Wizard(){

        agility = 10;
        intelligence = 25;
        power = 13;

        multiplierPower = 5;
        multiplierIntelligence = 10;
        multiplierAgility = 2;

        updateStats();

        List<CharacterNames> names = Collections.unmodifiableList(Arrays.asList(CharacterNames.values()));
        name = names.get(random.nextInt(names.size())).toString();
    }


    private boolean expToNextLevelReady(){
        return getExperience() >= expToNextLevel;
    }

    private double getExperience() {
        return experience;
    }

    private boolean setExperience(double experience) {
        if ((this.experience += experience) < Long.MAX_VALUE){
            this.experience += experience;
            changeLevel();
            return false;
        } else return true;
    }

    private boolean changeLevel(){
        if (expToNextLevelReady()) {
            level++;
            expToNextLevel = expToNextLevel * 4;
            setMagicPoint(getMagicPoint() + 1);
            setAgility(getAgility()+1);
            setIntelligence(getIntelligence()+4);
            setPower(getPower()+2);
            updateStats();
            return true;
        } else return false;
    }

    private void updateStats(){
        setAdditionAgility();
        setAdditionIntelligence();
        setAdditionPower();
        setHitPoint(getPower()*getMultiplierPower());
        setDamage(getIntelligence()*getMultiplierIntelligence());
        setManaPoint(getIntelligence()*getMultiplierIntelligence());
    }

    @Override
    public double expToNextLevel() {
        return (expToNextLevel - getExperience());
    }

    @Override
    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    @Override
    public Quest getQuest() {
        return quest;
    }

    @Override
    public void setManaPoint(int mana) {
        if (mana > getMaxManaPoint()) this.mana = getMaxManaPoint();
        else this.mana = mana;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getGold() {
        return gold;
    }

    @Override
    public void setGold(int gold) {
        if (gold < Integer.MAX_VALUE) this.gold = gold;
        else this.gold = Integer.MAX_VALUE;
    }

    @Override
    public int getMagicPoint(){
        return magicPoint;
    }

    @Override
    public void setMagicPoint(int magicPoint) {
        this.magicPoint = magicPoint;
    }

    @Override
    public boolean experienceDrop(double experience){
        return setExperience(experience);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int useMagic(Magic magic) {
        if (magic.getMagicClass().equals(MagicClasses.BUFF)) {
            activateBuff(magic);
            updateStats();
            return 0;
        }
        return super.useMagic(magic);
    }

    @Override
    public int getManaPoint() {
        return mana;
    }

    @Override
    public int getDamage() {
        if (equipmentItems.containsKey(EquipmentItems.HANDS)) return getBaseDamage() + ((Weapons)equipmentItems.get(EquipmentItems.HANDS)).getDamage();
        else return getBaseDamage();
    }

    @Override
    public void setDamage(int damage) {
        this.baseDamage = damage;
    }

    @Override
    public int applyDamage(int damage)  {
        if (count > 0) count--;
        int applyingDamage = damage - getDefence();
        if (applyingDamage < 0) return 0;
        else return applyingDamage;
    }

    @Override
    public int getHitPoint() {
        if (hitPoint < 0) return 0;
        else return hitPoint;
    }

    @Override
    public void setHitPoint(int hitPoint) {
        if (hitPoint >= getMaxHitPoint()) this.hitPoint = getMaxHitPoint();
        else this.hitPoint = hitPoint;
    }

    @Override
    public int getMaxHitPoint() {
        return getPower() * getMultiplierPower();
    }

    @Override
    public int getMaxManaPoint() {
        return getIntelligence() * getMultiplierIntelligence();
    }

    public int getCountOfBigHitPointBottle() {
        return countOfBigHitPointBottle;
    }

    public int getCountOfMiddleHitPointBottle() {
        return countOfMiddleHitPointBottle;
    }

    public int getCountOfSmallHitPointBottle() {
        return countOfSmallHitPointBottle;
    }

    public int getCountOfBigManaPointBottles() {
        return countOfBigFlower;
    }

    public int getCountOfMiddleManaPointBottles() {
        return countOfMiddleFlower;
    }

    public int getCountOfSmallManaPointBottles() {
        return countOfSmallFlower;
    }

    @Override
    public int getCountOfHealingItems() {
        return getCountOfBigHitPointBottle() +
                getCountOfMiddleHitPointBottle() +
                getCountOfSmallHitPointBottle() +
                getCountOfBigManaPointBottles() +
                getCountOfMiddleManaPointBottles() +
                getCountOfSmallManaPointBottles();
    }
    @Override
    public boolean checkHitPointBottle(){
        return getCountOfSmallHitPointBottle() > 0 || getCountOfMiddleHitPointBottle() > 0 || getCountOfBigHitPointBottle() > 0;
    }

    @Override
    public boolean checkManaPointBottle(){
        return getCountOfSmallManaPointBottles() != 0 || getCountOfMiddleManaPointBottles() != 0 || getCountOfBigManaPointBottles() != 0;

    }

    @Override
    public boolean equip(Item item) {
        if (super.equip(item)) {
            updateStats();
            return true;
        } else return false;
    }

    @Override
    public void unEquip() {

    }

    @Override
    public Map<EquipmentItems, Item> showEquipment() {
        return equipmentItems;
    }

    @Override
    public String toString(){
        return "Class: " + this.getClass().getSimpleName() +
                " " + getName() +
                "; HP " + String.valueOf(getHitPoint()) +
                "; MP " + getManaPoint() +
                "; DMG: " + getDamage() +
                "; DEF: " + getDefence() +
                "; Lvl: " + String.valueOf(getLevel()) +
                "; Exp to next level: " + expToNextLevel() +
                "; GOLD: " + getGold();
    }

    public static CharacterFactory characterFactory = Wizard::new;
}