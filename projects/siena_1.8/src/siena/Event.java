//
//  This file is part of Siena, a wide-area event notification system.
//  See http://www.cs.colorado.edu/serl/dot/siena.html
//
//  Author: Antonio Carzaniga <carzanig@cs.colorado.edu>
//  See the file AUTHORS for full details.
//
//  Copyright (C) 1998-1999 University of Colorado
//
//  This program is free software; you can redistribute it and/or
//  modify it under the terms of the GNU General Public License
//  as published by the Free Software Foundation; either version 2
//  of the License, or (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
//  USA, or send email to serl@cs.colorado.edu.
//
//
// $Id: Event.java,v 1.10 2000/06/30 17:59:06 carzanig Exp $
//
package siena;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import tful.arrays.byteArray;

/**
 *  An <code>Event</code> represents an <em>event notification</em>.
 *  It is therefore the primary data entity used in Siena.  A
 *  notification is structured as a set of named and typed attributes.
 *  Attribute names are strings.  A valid attribute name must begin
 *  with a letter (<code>'a'</code>-<code>'z'</code>,
 *  <code>'A'</code>-<code>'Z'</code>) or an underscore character
 *  (<code>'_'</code>), and may contain only letters, underscores,
 *  digits (<code>'0'</code>-<code>'9'</code>), the dot character
 *  (<code>'.'</code>), and the forward slash character
 *  (<code>'/'</code>). Attribute names must be unique within an
 *  <code>Event</code>.
 *
 *  @see AttributeValue
 *
 **/
public class Event {
    private Map attributes;

    /**
     * construct an empty event.
     */
    public Event() {
	attributes = new TreeMap();
    }

    /**
     * returns an iterator over the set of attribute names.
     */
    public Iterator iterator() {
	return attributes.keySet().iterator();
    }

    /**
     * set the value of an attribute.
     * Add the attribute if that is not present.
     * @param name attribute name.
     * @param value String value.  */
    public void putAttribute(String name, String value) {
	attributes.put(name, new AttributeValue(value));
    }

    /**
     * set the value of an attribute.
     * Add the attribute if that is not present.
     * @param name attribute name.
     * @param value byte array value.
     */
    public void testful_putAttribute(String name, byteArray value) {
    	putAttribute(name, value.toArray());
    }
    public void putAttribute(String name, byte[] value) {
	attributes.put(name, new AttributeValue(value));
    }

    /**
     * set the value of an attribute.
     * Add the attribute if that is not present.
     * @param name attribute name.
     * @param value integer value.
     */
    public void putAttribute(String name, long value) {
	attributes.put(name, new AttributeValue(value));
    }

    /**
     * set the value of an attribute.
     * Add the attribute if that is not present.
     * @param name attribute name.
     * @param value double value.
     */
    public void putAttribute(String name, double value) {
	attributes.put(name, new AttributeValue(value));
    }

    /**
     * set the value of an attribute.
     * Add the attribute if that is not present.
     * @param name attribute name.
     * @param value boolean value.
     */
    public void putAttribute(String name, boolean value) {
	attributes.put(name, new AttributeValue(value));
    }

    /**
     * set the value of an attribute.
     * Add the attribute if that is not present.
     * @param name attribute name.
     * @param value value.
     */
    public void putAttribute(String name, AttributeValue value) {
	attributes.put(name, value);
    }
    /**
     * returns the value of an attribute.  Returns <code>null</code> if
     * that attribute does not exist in this event.
     *
     * @param name attribute name.
     */
    public AttributeValue getAttribute(String name) {
	return (AttributeValue)attributes.get(name);
    }

    /**
     * returns the number of attributes in this event.
     *
     * @param name attribute name.
     */
    public int size() {
	return attributes.size();
    }

    /**
     * removes every attribute from this event.
     */
    public void clear() {
	attributes.clear();
    }

    /**
     * returns an iterator for the set of attribute names of this event.
     */
    public Iterator attributeNamesIterator() {
	return attributes.keySet().iterator();
    }

    public String toString() {
	return new String(SENP.encode(this));
    }
}
