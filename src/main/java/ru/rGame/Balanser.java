package ru.rGame;

public class Balanser {
    public static final int pointsPerLevel = 3;             //Очков распределения статов на уровень
    public static final int basicHealth = 100;              //Базовое ХП
    public static final int healthPerVitality = 10;         //Прирост ХП за очко выносливости
    public static final int powerStrikeCooldown = 6;        //Откат мощного удара, ходов
    public static final int aimedStrikeCooldown = 6;        //Откат меткого удара, ходов
    public static final int runCooldown = 10;               //Откат попытки сбегания, ходов
    public static final int runChance = 60;                 //Шанс на побег, %
    public static final int vitalityToLowerpscd = 20;       //Количество очков выносливости до очередного уменьшения отката мощного удара
    public static final int dexterityToLowerascd = 20;      //Количество очков ловкости до очередного уменьшения отката меткого удара
    public static final int dodgePerDexterity = 1;          //Шанс на уворот, % (умножается на разницу между ловкостью оппонентов)
    public static final int mapLevels = 10;                 //Количество уровней подземелья
    public static final int levelCapNA = 15;                //Кап уровней игрока без артефакта
    public static final int levelCapArtifacted = 25;        //Кап уровней игрока с артефактом
    public static final int basicDefence = 5;               //Базовая защита (без брони)
    public static final int basicDamage = 10;                //базовый урон (без оружия)
    public static final int damagePerStrMultiplyer = 20;     //Множитель урона за каждое очко силы, %
    public static final int defenceEffectiveness = 70;     //Снижение урона за очки обороны, %
    public static final int defenceStanceMultiplier = 70;   //Прибавка защиты за защитную стойку, %
    public static final int psDodgeChance = 100;            //Прибавка к шансу уворота от мощной атаки, %
    public static final int asDefenceMultiplier = 100;      //Прибавка к эффекту защиты при блокировании меткой атаки, %
    public static final int powerStrikeDamage = 100;        //Прибавка к урону силовой атаки, %
    public static final int aimedStrikeDamage = 100;        //Прибавка к урону меткой атаки, %
    public static final int baseDodgeChance = 30;           //Базовый шанс уворота, %
    public static final int healthPerLvlUp = 40;            //Процент восстановления здоровья при уровне


}
