/*
 *  Lindenmayer
 *  Copyright (C) 2007-2015  Christian Lins <christian@lins.me> 
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.lins.lindenmayer.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * A Set which orders its values in the sequence of addition to the Set.
 * @author Christian Lins
 * @param <E>
 */
public class SequentialSet<E> implements Set<E> {
    
    protected static class Element<E> {
        private final E value;
        private final int idx;
        
        public Element(E value, int idx) {
            this.idx = idx;
            this.value = value;
        }
        
        public int getIdx() {
            return this.idx;
        }
        
        public E getValue() {
            return this.value;
        }
    }
    
    protected static class ElementInterator<E> implements Iterator<E> {

        private final Iterator<Element<E>> iter;
        
        protected ElementInterator(Set<Element<E>> container) {
            this.iter = container.iterator();
        }
        
        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override
        public E next() {
            Element<E> e = this.iter.next();
            return e.getValue();
        }
        
    }
            
    private int indices = 0;
    
    protected TreeSet<Element<E>> container = new TreeSet<>(
            Comparator.comparingInt(e -> e.getIdx()));
    
    @Override
    public int size() {
        return this.container.size();
    }

    @Override
    public boolean isEmpty() {
        return this.container.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }

        return this.container.parallelStream()
                .anyMatch(e -> e.getValue().equals(o));
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementInterator<>(this.container);
    }

    @Override
    public Object[] toArray() {
        Object[] array = this.container.toArray();
        for(int n = 0; n < array.length; n++) {
            array[n] = ((Element<E>)array[n]).getValue();
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        Object[] array = toArray();
        return (T[])array;
    }

    @Override
    public boolean add(E e) {
        return this.container.add(new Element(e, indices++));
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends E> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        this.container.clear();
        this.indices = 0;
    }
    
}
