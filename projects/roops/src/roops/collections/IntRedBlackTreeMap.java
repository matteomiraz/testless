/*****************************************************************************
 * University of Illinois/NCSA Open Source License
 *
 * Copyright (c) 2010 Rohan Sharma
 * All rights reserved.
 *
 * Developed by:  Rohan Sharma <sharma.rohan1990@gmail.com>
 *                University of Illinois at Urbana-Champaign
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal with the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimers.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimers in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the names of Rohan Sharma, University of Illinois at Urbana-
 *    Champaign, nor the names of its contributors may be used to endorse
 *    or promote products derived from this Software without specific prior
 *    written permission.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL
 * THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *****************************************************************************/

/**
 * A map implementation following the specification for the java.util.TreeMap
 * class but using primitive values of type "int" as keys.  The implementation
 * is an adaptation of red black trees described in the book "Introduction to
 * Algorithms" by Cormen, Leiserson, Rivest, and Stein.  The following code
 * can be used for testing research purposes.
 *
 * @author Rohan Sharma <sharma.rohan1990@gmail.com>
 * 
 */

/**
 * Goal 39 seems to be the most difficult branch, and goals 2 and 9 are other
 * difficult branches (provided by Andrea Arcuri <arcuri@simula.no>).
 */

package roops.collections;

import roops.util.RoopsArray; @roops.util.BenchmarkClass
public class IntRedBlackTreeMap {

    private int size;

    public class Node {
        int key;
        /*@ nullable @*/ Object value;
        /*@ nullable @*/ Node parent;
        /*@ nullable @*/ Node left;
        /*@ nullable @*/ Node right;
        /*@ nullable @*/ boolean color;
    }

    private /*@ nullable @*/ Node root;

    @roops.util.NrOfGoals(50)
    @roops.util.BenchmarkMethod static
    public void putTest(IntRedBlackTreeMap tree, Integer key, Object value) {
        tree.put(key, value);
    }

    public void put(Integer key, /*@ nullable @*/ Object value) {
        Node x = new Node();
        x.key = key;
        x.value = value;
        treeInsert(x);
        size++;
    }

    public Object remove(Integer key) {
        Node ret = treeDelete(findNode(key));
        if (ret == null)
            return null;
        size--;
        return ret.value; 
    }

    public int getSize() {
        return size;
    }

    private boolean getColor(/*@ nullable @*/ Node x) {
        // null nodes are colored black
        return x == null ? true : x.color;
    }

    private Node findNode(Integer key) {
        Node cur = root;
        while (cur != null && key.compareTo(cur.key) != 0)
            if (key.compareTo(cur.key) < 0) {
                {roops.util.Goals.reached(0);}
                cur = cur.left;
            }
            else {
                {roops.util.Goals.reached(1);}
                cur = cur.right;
            }
        return cur;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            {roops.util.Goals.reached(2);}
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            {roops.util.Goals.reached(3);}
            root = y;
        }
        else if (x == x.parent.left) {
            {roops.util.Goals.reached(4);}
            x.parent.left = y;
        }
        else {
            {roops.util.Goals.reached(5);}
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) {
            {roops.util.Goals.reached(6);}
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            {roops.util.Goals.reached(7);}
            root = y;
        }
        else if (x == x.parent.right) {
            {roops.util.Goals.reached(8);}
            x.parent.right = y;
        }
        else {
            {roops.util.Goals.reached(9);}
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    private void treeInsert(Node z) {
        Node y = null;
        Node x = root;
        while (x != null) {
            y = x;
            if (((Integer) z.key).compareTo(x.key) < 0) {
                {roops.util.Goals.reached(10);}
                x = x.left;
            }
            else if (((Integer) z.key).compareTo(x.key) == 0) {
                {roops.util.Goals.reached(11);}
                x.value = z.value;
                size--;
                return;
            } else {
                {roops.util.Goals.reached(12);}
                x = x.right;
            }
        }
        z.parent = y;
        if (y == null) {
            {roops.util.Goals.reached(13);}
            root = z;
        }
        else if (((Integer) z.key).compareTo(y.key) < 0) {
            {roops.util.Goals.reached(14);}
            y.left = z;
        }
        else {
            {roops.util.Goals.reached(15);}
            y.right = z;
        }
        z.left = null;
        z.right = null;
        z.color = false;

        treeInsertFix(z);
    }

    private void treeInsertFix(Node z) {
        while (getColor(z.parent) == false) {
            if (z.parent == z.parent.parent.left) {
                {roops.util.Goals.reached(16);}
                Node y = z.parent.parent.right;
                if (getColor(y) == false) {
                    {roops.util.Goals.reached(17);}
                    z.parent.color = true;
                    y.color = true;
                    z.parent.parent.color = false;
                    z = z.parent.parent;
                } else {
                    {roops.util.Goals.reached(18);}
                    if (z == z.parent.right) {
                        {roops.util.Goals.reached(19);}
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = true;
                    z.parent.parent.color = false;
                    rightRotate(z.parent.parent);
                }
            } else {
                {roops.util.Goals.reached(20);}
                Node y = z.parent.parent.left;
                if (getColor(y) == false) {
                    {roops.util.Goals.reached(21);}
                    z.parent.color = true;
                    y.color = true;
                    z.parent.parent.color = false;
                    z = z.parent.parent;
                } else {
                    {roops.util.Goals.reached(22);}
                    if (z == z.parent.left) {
                        {roops.util.Goals.reached(23);}
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = true;
                    z.parent.parent.color = false;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = true;
    }

    private Node treeDelete(Node z) {
        if (z == null) {
            {roops.util.Goals.reached(24);}
            return null;
        }
        Node x, y;
        if (z.left == null || z.right == null) {
            {roops.util.Goals.reached(25);}
            y = z;
        }
        else {
            {roops.util.Goals.reached(26);}
            y = getIOS(z);
        }
        if (y.left != null) {
            {roops.util.Goals.reached(27);}
            x = y.left;
        }
        else {
            {roops.util.Goals.reached(29);}
            x = y.right;
        }
        if (x != null) {
            {roops.util.Goals.reached(30);}
            x.parent = y.parent;
        }
        if (y.parent == null) {
            {roops.util.Goals.reached(31);}
            root = x;
        }
        else if (y == y.parent.left) {
            {roops.util.Goals.reached(32);}
            y.parent.left = x;
        }
        else {
            {roops.util.Goals.reached(33);}
            y.parent.right = x;
        }
        if (y != z) {
            {roops.util.Goals.reached(34);}
            z.key = y.key;
            z.value = y.value;
        }
        if (getColor(y) == true) {
            if (x == null) {
                {roops.util.Goals.reached(35);}
                treeDeleteFix(y);
            }
            else {
                {roops.util.Goals.reached(36);}
                treeDeleteFix(x);
            }
        }
        return y;
    }

    private void treeDeleteFix(Node x) {
        while (x.parent != null && getColor(x) == true) {
            if (x == x.parent.left || x.parent.left == null) {
                {roops.util.Goals.reached(37);}
                Node w = x.parent.right;
                if (w == null) {
                    {roops.util.Goals.reached(38);}
                    return;
                }
                if (getColor(w) == false) {
                    {roops.util.Goals.reached(39);}
                    w.color = true;
                    x.parent.color = false;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (getColor(w.left) == true && getColor(w.right) == true) {
                    {roops.util.Goals.reached(40);}
                    w.color = false;
                    x = x.parent;
                } else {
                    {roops.util.Goals.reached(41);}
                    if (getColor(w.right) == true) {
                        {roops.util.Goals.reached(42);}
                        w.left.color = true;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = true;
                    if (w.right != null) {
                        {roops.util.Goals.reached(43);}
                        w.right.color = true;
                    }
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                {roops.util.Goals.reached(44);}
                Node w = x.parent.left;
                if (w == null) {
                    {roops.util.Goals.reached(45);}
                    return;
                }
                if (getColor(w) == false) {
                    {roops.util.Goals.reached(46);}
                    w.color = true;
                    x.parent.color = false;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (getColor(w.right) == true && getColor(w.left) == true) {
                    {roops.util.Goals.reached(47);}
                    w.color = false;
                    x = x.parent;
                } else {
                    {roops.util.Goals.reached(48);}
                    if (getColor(w.left) == true) {
                        {roops.util.Goals.reached(49);}
                        w.right.color = true;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = true;
                    if (w.left != null) {
                        {roops.util.Goals.reached(50);}
                        w.left.color = true;
                    }
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = true;
    }

    private Node getIOS(Node z) {
        Node x, y = null;
        x = z.right;
        while (x != null) {
            y = x;
            x = x.left;
        }
        return y;
    }
}
/* end roops.util */

