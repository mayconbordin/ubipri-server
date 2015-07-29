package utils.db.types;

import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarTypeBase;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

import org.postgis.PGgeometry;
import org.postgis.Point;

/**
 * Ebean scalar type pro PostGIS point.
 */
public class ScalarTypePostGisPoint extends ScalarTypeBase<Point> {
    public ScalarTypePostGisPoint() {
        super(Point.class, true, Types.OTHER);
    }

    @Override
    public Point read(DataReader dataReader) throws SQLException {
        Object object = dataReader.getObject();

        if (object != null) {
            return (Point) ((PGgeometry) object).getGeometry();
        } else {
            return null;
        }
    }

    @Override
    public void bind(DataBind b, Point value) throws SQLException {
        if (value == null) {
            b.setNull(Types.OTHER);
        } else {
            b.setObject(value, Types.OTHER);
        }
    }

    @Override
    public Object toJdbcType(Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Point toBeanType(Object value) {
        return (Point) value;
    }

    public String formatValue(Point v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Point parse(String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isDateTimeCapable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Point readData(DataInput dataInput) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void writeData(DataOutput dataOutput, Point v) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public Point convertFromMillis(long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point jsonRead(JsonParser arg0, JsonToken arg1) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void jsonWrite(JsonGenerator arg0, String arg1, Point arg2)
			throws IOException {
		// TODO Auto-generated method stub
		
	}
}