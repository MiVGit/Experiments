package ru.rGame;

public class Balanser {
    public static int pointsPerLevel = 3;             //Очков распределения статов на уровень
    public static int basicHealth = 100;              //Базовое ХП
    public static int healthPerVitality = 10;         //Прирост ХП за очко выносливости
    public static int powerStrikeCooldown = 6;        //Откат мощного удара, ходов
    public static int aimedStrikeCooldown = 6;        //Откат меткого удара, ходов
    public static int runCooldown = 10;               //Откат попытки сбегания, ходов
    public static int runChance = 60;                 //Шанс на побег, %
    public static int vitalityToLowerpscd = 20;       //Количество очков выносливости до очередного уменьшения отката мощного удара
    public static int dexterityToLowerascd = 20;      //Количество очков ловкости до очередного уменьшения отката меткого удара
    public static int dodgePerDexterity = 1;          //Шанс на уворот, % (умножается на разницу между ловкостью оппонентов)
    public static int mapLevels = 10;                 //Количество уровней подземелья
    public static int levelCapNA = 15;                //Кап уровней игрока без артефакта
    public static int levelCapArtifacted = 25;        //Кап уровней игрока с артефактом
    public static int basicDefence = 5;               //Базовая защита (без брони)
    public static int basicDamage = 10;                //базовый урон (без оружия)
    public static int damagePerStrMultiplyer = 20;     //Множитель урона за каждое очко силы, %
    public static int defenceEffectiveness = 70;     //Снижение урона за очки обороны, %
    public static int defenceStanceMultiplier = 70;   //Прибавка защиты за защитную стойку, %
    public static int psDodgeChance = 100;            //Прибавка к шансу уворота от мощной атаки, %
    public static int asDefenceMultiplier = 100;      //Прибавка к эффекту защиты при блокировании меткой атаки, %
    public static int powerStrikeDamage = 100;        //Прибавка к урону силовой атаки, %
    public static int aimedStrikeDamage = 100;        //Прибавка к урону меткой атаки, %
    public static int baseDodgeChance = 30;           //Базовый шанс уворота, %
    public static int healthPerLvlUp = 40;            //Процент восстановления здоровья при уровне


}
