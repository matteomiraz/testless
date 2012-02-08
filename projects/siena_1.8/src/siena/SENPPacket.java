//
//  This file is part of Siena, a wide-area event notification system.
//  See http://www.cs.colorado.edu/serl/dot/siena.html
//
//  Author: Antonio Carzaniga <carzanig@cs.colorado.edu>
//  See the file AUTHORS for full details.
//
//  Copyright (C) 1998-2000 University of Colorado
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
// $Id: SENPPacket.java,v 1.2 2000/04/24 00:02:39 carzanig Exp $
//
package siena;


public class SENPPacket {
    public byte		version;
    public byte		method;
    public byte		ttl;
    public byte[]	to;
    public byte[]	id;
    public byte[]	handler;

    public Event	event;
    public Filter	filter;

    public SENPPacket() {
	version = SENP.ProtocolVersion;
	method = SENP.NOP;
	ttl = SENP.DefaultTtl;
    }

    public SENPPacket shallowCopy() {
	SENPPacket res = new SENPPacket();
	res.version = version;
	res.method = method;
	res.ttl = ttl;
	res.to = to;
	res.id = id;
	res.handler = handler;

	res.filter = filter;
	res.event = event;

	return res;
    }

    public String toString() {
	return new String(SENP.encode(this));
    }

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public byte getMethod() {
		return method;
	}

	public void setMethod(byte method) {
		this.method = method;
	}

	public byte getTtl() {
		return ttl;
	}

	public void setTtl(byte ttl) {
		this.ttl = ttl;
	}

	public byte[] getTo() {
		return to;
	}

	public void setTo(byte[] to) {
		this.to = to;
	}

	public byte[] getId() {
		return id;
	}

	public void setId(byte[] id) {
		this.id = id;
	}

	public byte[] getHandler() {
		return handler;
	}

	public void setHandler(byte[] handler) {
		this.handler = handler;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public tful.arrays.byteArray testful_getId()  { return new tful.arrays.byteArray (getId()); }
	public tful.arrays.byteArray testful_getHandler()  { return new tful.arrays.byteArray (getHandler()); }
	public tful.arrays.byteArray testful_getTo()  { return new tful.arrays.byteArray (getTo()); }
	public void testful_setTo(tful.arrays.byteArray p0)  { setTo(p0.toArray() ); }
	public void testful_setId(tful.arrays.byteArray p0)  { setId(p0.toArray() ); }
	public void testful_setHandler(tful.arrays.byteArray p0)  { setHandler(p0.toArray() ); }

}