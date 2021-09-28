package com.severalcircles.flames.data.user;

import com.severalcircles.flames.data.global.GlobalData;

public class UserStats {
    public static final double powerGrowth = 0.75;
    public static final double resistanceGrowth = 0.5;
    public static final double luckGrowth = 0.25;
    public static final double risingGrowth = 0.25;
    public static final double priorityGrowth = 0.15;
    public static final double seniorityGrowth = 0.75;
    public static final double charismaGrowth = 0.5;

    private int exp = 0;
    private int level = 1;

    private int POW = 1;
    private int RES = 1;
    private int LUCK = 1;
    private int RISE = 1;
    private int PTY = 1;
    private int SEN = 1;
    private int CAR = 1;
    public UserStats() {}
    public UserStats(int exp, int level, int POW, int RES, int LUCK, int RISE, int PTY, int SEN, int CAR) {
        this.exp = exp;
        this.level = level;
        this.POW = POW;
        this.RES = RES;
        this.LUCK = LUCK;
        this.RISE = RISE;
        this.PTY = PTY;
        this.SEN = SEN;
        this.CAR = CAR;
    }

    public int getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    public int getPOW() {
        return POW;
    }

    public int getRES() {
        return RES;
    }

    public int getLUCK() {
        return LUCK;
    }

    public int getRISE() {
        return RISE;
    }

    public int getPTY() {
        return PTY;
    }

    public int getSEN() {
        return SEN;
    }

    public int getCAR() {
        return CAR;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean addExp(int amountToAdd) {
        this.exp += amountToAdd;
        return checkLevelUp();
    }
    //TODO: Implement a Level Up DM
    public boolean checkLevelUp() {
        try {
            if ((this.exp + 1) / (GlobalData.averageScore * (level + 1)) >= 1) {
                level++;
                double random = Math.random();
                if (random <= powerGrowth) POW++;
                random = Math.random();
                if (random <= resistanceGrowth) RES++;
                random = Math.random();
                if (random <= luckGrowth) LUCK++;
                random = Math.random();
                if (random <= risingGrowth) RISE++;
                random = Math.random();
                if (random <= priorityGrowth) PTY++;
                random = Math.random();
                if (random <= seniorityGrowth) SEN++;
                random = Math.random();
                if (random <= charismaGrowth) CAR++;
                return true;
            } else return false;
        } catch (ArithmeticException e) {
            return false;
        }
    }
}
