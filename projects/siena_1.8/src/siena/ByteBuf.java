package siena;

public class ByteBuf {
    int pos;
    private byte[] buf;

    public ByteBuf(byte [] x) {
	pos = 0;
    }

    public ByteBuf() {
	buf = new byte[SENP.MaxPacketLen];
	pos = 0;
    }

    public void append(byte b) {
	buf[pos++] = b;
    }

    public void append(int x) {
	buf[pos++] = (byte)x;
    }

    public void append(byte[] bytes) {
	for(int i = 0; i < bytes.length; ++i)
	    buf[pos++] = bytes[i];
    }

    public void append(String s) {
	append(s.getBytes());
    }

    public void reset() {
	pos = 0;
    }

    public byte[] bytes() {
	byte[] res = new byte[pos];
	for(int i = 0; i < pos; ++i)
	    res[i] = buf[i];
	return res;
    }

    public ByteBuf(tful.arrays.byteArray p0)  { this(p0.toArray() ); }
    public void testful_append(tful.arrays.byteArray p0)  { append(p0.toArray() ); }
    public tful.arrays.byteArray testful_bytes()  { return new tful.arrays.byteArray (bytes()); }
}