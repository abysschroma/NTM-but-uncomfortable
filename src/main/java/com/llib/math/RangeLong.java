package com.llib.math;

import com.llib.math.base.RangeBase;

import java.util.Iterator;

public class RangeLong extends RangeBase implements Iterable<Long> {
    long min;
    long max;
    public RangeLong(long min,long max) {
        this.min = Math.min(min,max);
        this.max = Math.max(min,max);
    }
    public boolean isInRange(int x) {
        if (x > max) return false;
        if (x < min) return false;
        return true;
    }
    public boolean isInRange(long x) {
        if (x > max) return false;
        if (x < min) return false;
        return true;
    }
    public boolean isInRange(float x) {
        if (x > max) return false;
        if (x < min) return false;
        return true;
    }
    public boolean isInRange(double x) {
        if (x > max) return false;
        if (x < min) return false;
        return true;
    }
    public double lerp(double t) {
        return min + (max - min) * t;
    }
    public float lerp(float t) {
        return min + (max - min) * t;
    }

    @Override
    public double doubleMin() {
        return this.min;
    }
    @Override
    public double doubleMax() {
        return this.max;
    }
    public Iterator<Long> iterator() {
        return new Iterator<Long>() {
            long cursor = min;
            @Override
            public boolean hasNext() {
                return cursor <= max;
            }
            @Override
            public Long next() {
                return cursor++;
            }
        };
    }
}