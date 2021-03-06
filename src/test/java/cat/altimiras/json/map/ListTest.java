package cat.altimiras.json.map;

import cat.altimiras.matryoshka.Matryoshka;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.util.Map;

import static cat.altimiras.json.JSONFactory.DEFAULT_INCOMPLETE_KEY_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ListTest {

	@Test
	public void simple() throws Exception {

		Map result = new JSONMapParserImpl(DEFAULT_INCOMPLETE_KEY_NAME).parse("{ \"k\" :[1,2,3] }");
		Matryoshka matryoshka = new Matryoshka(result);

		assertEquals(3, matryoshka.get("k").asList().size());
		assertEquals(1, matryoshka.get("k").asList().get(0).value());
		assertEquals(2, matryoshka.get("k").asList().get(1).value());
		assertEquals(3, matryoshka.get("k").asList().get(2).value());
	}

	@Test
	public void simpleTypes() throws Exception {

		Map result = new JSONMapParserImpl(DEFAULT_INCOMPLETE_KEY_NAME).parse("{ \"k\" :[\"1\",2, false] }");
		Matryoshka matryoshka = new Matryoshka(result);

		assertEquals(3, matryoshka.get("k").asList().size());
		assertEquals("1", matryoshka.get("k").asList().get(0).value());
		assertEquals(2, matryoshka.get("k").asList().get(1).value());
		assertEquals(false, matryoshka.get("k").asList().get(2).value());
	}

	@Test
	public void multipleLists() throws Exception {

		Map result = new JSONMapParserImpl(DEFAULT_INCOMPLETE_KEY_NAME).parse("{ \"k\" :[1,2,3], \"k2\" :[true, false, true] }");
		Matryoshka matryoshka = new Matryoshka(result);

		assertEquals(3, matryoshka.get("k").asList().size());
		assertEquals(1, matryoshka.get("k").asList().get(0).value());
		assertEquals(2, matryoshka.get("k").asList().get(1).value());
		assertEquals(3, matryoshka.get("k").asList().get(2).value());

		assertEquals(3, matryoshka.get("k2").asList().size());
		assertEquals(true, matryoshka.get("k2").asList().get(0).value());
		assertEquals(false, matryoshka.get("k2").asList().get(1).value());
		assertEquals(true, matryoshka.get("k2").asList().get(2).value());
	}

	@Test
	public void multipleListsAfter() throws Exception {

		Map result = new JSONMapParserImpl(DEFAULT_INCOMPLETE_KEY_NAME).parse("{ \"k\" :[1,2,3], \"k2\" :[true, false, true], \"k3\" : \"aaa\" }");
		Matryoshka matryoshka = new Matryoshka(result);

		assertEquals(3, matryoshka.get("k").asList().size());
		assertEquals(1, matryoshka.get("k").asList().get(0).value());
		assertEquals(2, matryoshka.get("k").asList().get(1).value());
		assertEquals(3, matryoshka.get("k").asList().get(2).value());

		assertEquals(3, matryoshka.get("k2").asList().size());
		assertEquals(true, matryoshka.get("k2").asList().get(0).value());
		assertEquals(false, matryoshka.get("k2").asList().get(1).value());
		assertEquals(true, matryoshka.get("k2").asList().get(2).value());

		assertEquals("aaa", matryoshka.get("k3").value());
	}

	@Test
	public void multipleListsBefore() throws Exception {

		Map result = new JSONMapParserImpl(DEFAULT_INCOMPLETE_KEY_NAME).parse("{ \"k3\" : \"aaa\", \"k\" :[1,2,3], \"k2\" :[true, false, true] }");
		Matryoshka matryoshka = new Matryoshka(result);

		assertEquals(3, matryoshka.get("k").asList().size());
		assertEquals(1, matryoshka.get("k").asList().get(0).value());
		assertEquals(2, matryoshka.get("k").asList().get(1).value());
		assertEquals(3, matryoshka.get("k").asList().get(2).value());

		assertEquals(3, matryoshka.get("k2").asList().size());
		assertEquals(true, matryoshka.get("k2").asList().get(0).value());
		assertEquals(false, matryoshka.get("k2").asList().get(1).value());
		assertEquals(true, matryoshka.get("k2").asList().get(2).value());

		assertEquals("aaa", matryoshka.get("k3").value());
	}

	@Test
	public void nestedList1() throws Exception {

		String json = IOUtils.toString(this.getClass().getResourceAsStream("/json/nestedList1.json"), "UTF-8");
		Map result = new JSONMapParserImpl(DEFAULT_INCOMPLETE_KEY_NAME).parse(json);
		Matryoshka matryoshka = new Matryoshka(result);

		assertEquals(4, matryoshka.get("key").asList().size());
		assertEquals("lolo", matryoshka.get("key").asList().get(0).get("n1").value());
		assertEquals(55, matryoshka.get("key").asList().get(0).get("n2").value());
		assertEquals("lala", matryoshka.get("key").asList().get(1).value());
		assertEquals("lele", matryoshka.get("key").asList().get(2).get("n1").value());
		assertEquals(66, matryoshka.get("key").asList().get(2).get("n2").value());
		assertEquals(false, matryoshka.get("key").asList().get(3).get("a").value());
		assertFalse((Boolean) matryoshka.get("key2").value());
	}
}