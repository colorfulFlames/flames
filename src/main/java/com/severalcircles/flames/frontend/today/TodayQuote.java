/*
 * Copyright (c) 2025 Several Circles.
 */

package com.severalcircles.flames.frontend.today;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class TodayQuote {
    private String message;
    private double emotion;
    private String author;
    private Date inst;
    private int seed;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getEmotion() {
        return emotion;
    }

    public void setEmotion(double emotion) {
        this.emotion = emotion;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getInst() {
        return inst;
    }

    public void setInst(Date inst) {
        this.inst = inst;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public TodayQuote(String message, double emotion, String author, Date inst, int seed) {
        this.message = message;
        this.emotion = emotion;
        this.author = author;
        this.inst = inst;
        this.seed = seed;
    }
    TodayQuote(String msg) {
        this(msg, 0.0, "", new Date(), 0);
    }

    public TodayQuote() {
        this(Today.defaultQuote.message(), Today.defaultQuote.emotion(), Today.defaultQuote.author(), Today.defaultQuote.inst, Today.defaultQuote.seed());
    }

    public String message() {
        return message;
    }

    public double emotion() {
        return emotion;
    }

    public String author() {
        return author;
    }
    public int seed() {
        return seed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TodayQuote) obj;
        return Objects.equals(this.message, that.message) &&
                Double.doubleToLongBits(this.emotion) == Double.doubleToLongBits(that.emotion) &&
                Objects.equals(this.author, that.author) &&
                Objects.equals(this.inst, that.inst) &&
                this.seed == that.seed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, emotion, author, inst, seed);
    }

    @Override
    public String toString() {
        return "TodayQuote[" +
                "message=" + message + ", " +
                "emotion=" + emotion + ", " +
                "author=" + author + ", " +
                "inst=" + inst + ", " +
                "seed=" + seed + ']';
    }

}
