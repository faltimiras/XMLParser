package cat.altimiras.xml;

import cat.altimiras.xml.pojo.Nested2TestObj;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XMLSelfClosedParserTest {


    @Test
    public void xmlSelfClosedTest() throws Exception {
        String xml = IOUtils.toString(this.getClass().getResourceAsStream("/selfClosedTest.xml"), "UTF-8");
        XMLParser<Nested2TestObj> parser = new XMLParserImpl<>(Nested2TestObj.class);

        Nested2TestObj o = parser.parse(xml);

        assertEquals("title", o.getTitle());
        assertEquals("111", o.getSimpleTestObj1().getElement1());
        assertEquals("222", o.getSimpleTestObj2().getElement2());
    }
}
