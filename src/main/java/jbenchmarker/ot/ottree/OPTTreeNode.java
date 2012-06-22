/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbenchmarker.ot.ottree;

import collect.OrderedNode;
import crdt.tree.orderedtree.PositionIdentifier;
import crdt.tree.orderedtree.Positioned;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import jbenchmarker.core.Operation;
import jbenchmarker.ot.soct2.SOCT2;

/**
 *
 * @author Stephane Martin <stephane.martin@loria.fr>
 */
public class OPTTreeNode<T> implements OrderedNode<T> {

    int visibleChildren = 0;
    boolean visible;
    OPTTreeNode<T> father;
    T contains;
    SOCT2<OPTTreeNodeOperation> soct2;
    ArrayList<OPTTreeNode<T>> childrens;

    public boolean isVisible() {
        return visible;
    }

    public List<Integer> viewToModelRecurcive(List<Integer> list){
        LinkedList <Integer> ret = new LinkedList();
        ret.add(viewToModel(list.get(0)));
        if (ret.size()>1){
            ret.addAll(childrens.get(ret.get(0)).viewToModelRecurcive(list.subList(1, list.size()-1)));
        }
        return ret;
    }
    
    public int viewToModel(int positionInView) {
        int positionInchildrens = 0;
        int visibleCharacterCount = 0;

        while (positionInchildrens < this.childrens.size() && (visibleCharacterCount < positionInView || (!this.childrens.get(positionInchildrens).isVisible()))) {
            if (this.childrens.get(positionInchildrens).isVisible()) {
                visibleCharacterCount++;
            }
            positionInchildrens++;
        }

        return positionInchildrens;
    }
    public void remoteApply(Operation op){
        
    }
    public void apply(Operation op) {
        OPTTreeNodeOperation<T> oop = (OPTTreeNodeOperation<T>) op;
        int pos = oop.getPosition();

        if (oop.getType() == OPTTreeNodeOperation.OpType.del) {
            OPTTreeNode c = this.childrens.get(pos);
            if (c.isVisible()) {
                --visibleChildren;
            }
            c.setVisible(false);
        } else if (oop.getType() == OPTTreeNodeOperation.OpType.ins) {
            this.childrens.add(pos, new OPTTreeNode<T>(this, oop.getContain(), (SOCT2<OPTTreeNodeOperation>) soct2.create()));
            ++visibleChildren;
        } else if (oop.getType() == OPTTreeNodeOperation.OpType.transpose) {
        }
    }

    /*
     * public OTTreeNode(OTTreeNode<T> father, T contains) { this.father =
     * father; this.contains = contains; this.visible = true;
    }
     */
    public OPTTreeNode(OPTTreeNode<T> father, T contains, SOCT2<OPTTreeNodeOperation> soct2) {
        this.father = father;
        this.contains = contains;
        this.visible = true;
        this.soct2 = soct2;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /*
     * ----
     */
    @Override
    public int childrenNumber() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public OrderedNode<T> getChild(int p) {
        return childrens.get(viewToModel(p));
    }

    @Override
    public OrderedNode<T> getChild(Positioned<T> p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T getValue() {
        return contains;
    }

    @Override
    public Positioned<T> getPositioned(int p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PositionIdentifier getNewPosition(int p, T element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void add(PositionIdentifier pi, T element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(PositionIdentifier pi, T element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<? extends OrderedNode<T>> getElements() {
        LinkedList ret = new LinkedList();
        for (OPTTreeNode  n:childrens){
            if (n.isVisible()){
                ret.add(n);
            }
        }
        return ret;
    }

    @Override
    public OrderedNode<T> createNode(T elem) {
        throw new UnsupportedOperationException("createNode is not supported yet.");
    }

    @Override
    public void setReplicaNumber(int replicaNumber) {
        soct2.setReplicaNumber(replicaNumber);
    }

  
}