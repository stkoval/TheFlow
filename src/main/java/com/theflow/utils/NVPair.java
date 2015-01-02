/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.utils;

import java.util.Objects;

/**
 *
 * @author Stas
 */
public class NVPair<T, V> {
    private T left;
    private V right;
    
    public NVPair(T left, V right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public V getRight() {
        return right;
    }

    public void setRight(V right) {
        this.right = right;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.left);
        hash = 73 * hash + Objects.hashCode(this.right);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NVPair<?, ?> other = (NVPair<?, ?>) obj;
        if (!Objects.equals(this.left, other.left)) {
            return false;
        }
        return true;
    }
    
    
}
